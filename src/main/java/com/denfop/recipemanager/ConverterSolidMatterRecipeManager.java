package com.denfop.recipemanager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ic2.api.recipe.*;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

import java.util.*;

public class ConverterSolidMatterRecipeManager implements IMachineRecipeManagerExt {
    private final Map<IRecipeInput, RecipeOutput> recipes = new HashMap<>();
    private final Map<Item, Map<Integer, Tuple.T2<IRecipeInput, RecipeOutput>>> recipeCache = new IdentityHashMap<>();
    private final List<Tuple.T2<IRecipeInput, RecipeOutput>> uncacheableRecipes = new ArrayList<>();
    private boolean oreRegisterEventSubscribed;

    public void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs) {
        if (!addRecipe(input, metadata, true, outputs))
            displayError("ambiguous recipe: [" + input.getInputs() + " -> " + Arrays.asList(outputs) + "]");
    }

    public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean overwrite, ItemStack... outputs) {
        return addRecipe(input, new RecipeOutput(metadata, outputs), overwrite);
    }

    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        if (input == null)
            return null;
        Tuple.T2<IRecipeInput, RecipeOutput> data = getRecipe(input);
        if (data == null)
            return null;
        if (input.stackSize >= data.a.getAmount() && (
                !input.getItem().hasContainerItem(input) || input.stackSize == data.a.getAmount())) {
            if (adjustInput)
                if (input.getItem().hasContainerItem(input)) {
                    ItemStack container = input.getItem().getContainerItem(input);
                    input.func_150996_a(container.getItem());
                    input.stackSize = container.stackSize;
                    input.setItemDamage(container.getItemDamage());
                    input.stackTagCompound = container.stackTagCompound;
                } else {
                    input.stackSize -= data.a.getAmount();
                }
            return data.b;
        }
        return null;
    }

    public Map<IRecipeInput, RecipeOutput> getRecipes() {
        return new AbstractMap<IRecipeInput, RecipeOutput>() {
            public Set<Map.Entry<IRecipeInput, RecipeOutput>> entrySet() {
                return new AbstractSet<Map.Entry<IRecipeInput, RecipeOutput>>() {
                    public Iterator<Map.Entry<IRecipeInput, RecipeOutput>> iterator() {
                        return new Iterator<Map.Entry<IRecipeInput, RecipeOutput>>() {
                            private final Iterator<Map.Entry<IRecipeInput, RecipeOutput>> recipeIt = ConverterSolidMatterRecipeManager.this.recipes.entrySet().iterator();
                            private IRecipeInput lastInput;

                            public boolean hasNext() {
                                return this.recipeIt.hasNext();
                            }

                            public Map.Entry<IRecipeInput, RecipeOutput> next() {
                                Map.Entry<IRecipeInput, RecipeOutput> ret = this.recipeIt.next();
                                this.lastInput = ret.getKey();
                                return ret;
                            }

                            public void remove() {
                                this.recipeIt.remove();
                                ConverterSolidMatterRecipeManager.this.removeCachedRecipes(this.lastInput);
                            }
                        };
                    }

                    public int size() {
                        return ConverterSolidMatterRecipeManager.this.recipes.size();
                    }
                };
            }

            public RecipeOutput put(IRecipeInput key, RecipeOutput value) {
                ConverterSolidMatterRecipeManager.this.addRecipe(key, value, true);
                return null;
            }
        };
    }

    @SubscribeEvent
    public void onOreRegister(OreDictionary.OreRegisterEvent event) {
        List<Tuple.T2<IRecipeInput, RecipeOutput>> datas = new ArrayList<>();
        for (Map.Entry<IRecipeInput, RecipeOutput> data : this.recipes.entrySet()) {
            if (data.getKey().getClass() != RecipeInputOreDict.class)
                continue;
            RecipeInputOreDict recipe = (RecipeInputOreDict) data.getKey();
            if (recipe.input.equals(event.Name))
                datas.add(new Tuple.T2(data.getKey(), data.getValue()));
        }
        for (Tuple.T2<IRecipeInput, RecipeOutput> data : datas)
            addToCache(event.Ore, data);
    }

    private Tuple.T2<IRecipeInput, RecipeOutput> getRecipe(ItemStack input) {
        Map<Integer, Tuple.T2<IRecipeInput, RecipeOutput>> metaMap = this.recipeCache.get(input.getItem());
        if (metaMap != null) {
            Tuple.T2<IRecipeInput, RecipeOutput> data = metaMap.get(32767);
            if (data != null)
                return data;
            int meta = input.getItemDamage();
            data = metaMap.get(meta);
            if (data != null)
                return data;
        }
        for (Tuple.T2<IRecipeInput, RecipeOutput> data : this.uncacheableRecipes) {
            if (data.a.matches(input))
                return data;
        }
        return null;
    }

    private boolean addRecipe(IRecipeInput input, RecipeOutput output, boolean overwrite) {
        if (input == null) {
            displayError("The recipe input is null");
            return false;
        }
        for (ListIterator<ItemStack> it = output.items.listIterator(); it.hasNext(); ) {
            ItemStack stack = it.next();
            if (stack == null) {
                displayError("An output ItemStack is null.");
                return false;
            }
            if (!StackUtil.check(stack)) {
                displayError("The output ItemStack " + StackUtil.toStringSafe(stack) + " is invalid.");
                return false;
            }


            it.set(stack.copy());
        }
        label29:
        for (ItemStack is : input.getInputs()) {
            Tuple.T2<IRecipeInput, RecipeOutput> data = getRecipe(is);
            if (data != null) {
                if (overwrite)
                    while (true) {
                        this.recipes.remove(data.a);
                        removeCachedRecipes(data.a);
                        data = getRecipe(is);
                        if (data == null)
                            continue label29;
                    }
                return false;
            }
        }
        this.recipes.put(input, output);
        addToCache(input, output);
        return true;
    }

    private void addToCache(IRecipeInput input, RecipeOutput output) {
        Tuple.T2<IRecipeInput, RecipeOutput> data = new Tuple.T2(input, output);
        List<ItemStack> stacks = getStacksFromRecipe(input);
        if (stacks != null) {
            for (ItemStack stack : stacks)
                addToCache(stack, data);
            if (input.getClass() == RecipeInputOreDict.class && !this.oreRegisterEventSubscribed) {
                MinecraftForge.EVENT_BUS.register(this);
                this.oreRegisterEventSubscribed = true;
            }
        } else {
            this.uncacheableRecipes.add(data);
        }
    }

    private void addToCache(ItemStack stack, Tuple.T2<IRecipeInput, RecipeOutput> data) {
        Item item = stack.getItem();
        Map<Integer, Tuple.T2<IRecipeInput, RecipeOutput>> metaMap = this.recipeCache.computeIfAbsent(item, k -> new HashMap<>());
        int meta = stack.getItemDamage();
        metaMap.put(meta, data);
    }

    private void removeCachedRecipes(IRecipeInput input) {
        List<ItemStack> stacks = getStacksFromRecipe(input);
        if (stacks != null) {
            for (ItemStack stack : stacks) {
                Item item = stack.getItem();
                int meta = stack.getItemDamage();
                Map<Integer, Tuple.T2<IRecipeInput, RecipeOutput>> map = this.recipeCache.get(item);
                if (map == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + "(" + stack + ") is missing.");
                    continue;
                }
                map.remove(meta);
                if (map.isEmpty())
                    this.recipeCache.remove(item);
            }
        } else {
            this.uncacheableRecipes.removeIf(data -> data.a == input);
        }
    }

    private List<ItemStack> getStacksFromRecipe(IRecipeInput recipe) {
        if (recipe.getClass() == RecipeInputItemStack.class)
            return recipe.getInputs();
        if (recipe.getClass() == RecipeInputOreDict.class) {
            Integer meta = ((RecipeInputOreDict) recipe).meta;
            if (meta == null)
                return recipe.getInputs();
            List<ItemStack> ret = new ArrayList<>(recipe.getInputs());
            for (ListIterator<ItemStack> it = ret.listIterator(); it.hasNext(); ) {
                ItemStack stack = it.next();
                if (stack.getItemDamage() != meta) {
                    stack = stack.copy();
                    stack.setItemDamage(meta);
                    it.set(stack);
                }
            }
            return ret;
        }
        return null;
    }

    private void displayError(String msg) {
        if (MainConfig.ignoreInvalidRecipes) {
            IC2.log.warn(LogCategory.Recipe, msg);
        } else {
            throw new RuntimeException(msg);
        }
    }
}
