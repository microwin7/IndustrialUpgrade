package com.denfop.recipemanager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ic2.api.recipe.*;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.LogCategory;
import ic2.core.util.StackUtil;
import ic2.core.util.Tuple.T2;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary.OreRegisterEvent;

import java.util.*;
import java.util.Map.Entry;

public class BasicMachineRecipeManager implements IMachineRecipeManagerExt {
    private final Map<IRecipeInput, RecipeOutput> recipes = new HashMap();
    private final Map<Item, Map<Integer, T2<IRecipeInput, RecipeOutput>>> recipeCache = new IdentityHashMap();
    private final List<T2<IRecipeInput, RecipeOutput>> uncacheableRecipes = new ArrayList();
    private boolean oreRegisterEventSubscribed;

    public BasicMachineRecipeManager() {
    }

    public void addRecipe(IRecipeInput input, NBTTagCompound metadata, ItemStack... outputs) {
        if (!this.addRecipe(input, metadata, true, outputs)) {
            this.displayError("ambiguous recipe: [" + input.getInputs() + " -> " + Arrays.asList(outputs) + "]");
        }

    }

    public boolean addRecipe(IRecipeInput input, NBTTagCompound metadata, boolean overwrite, ItemStack... outputs) {
        return this.addRecipe(input, new RecipeOutput(metadata, outputs), overwrite);
    }

    public RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        if (input == null) {
            return null;
        } else {
            T2<IRecipeInput, RecipeOutput> data = this.getRecipe(input);
            if (data == null) {
                return null;
            } else if (input.stackSize < (data.a).getAmount() || input.getItem().hasContainerItem(input) && input.stackSize != data.a.getAmount()) {
                return null;
            } else {
                if (adjustInput) {
                    if (input.getItem().hasContainerItem(input)) {
                        ItemStack container = input.getItem().getContainerItem(input);
                        input.func_150996_a(container.getItem());
                        input.stackSize = container.stackSize;
                        input.setItemDamage(container.getItemDamage());
                        input.stackTagCompound = container.stackTagCompound;
                    } else {
                        input.stackSize -= (data.a).getAmount();
                    }
                }

                return data.b;
            }
        }
    }

    public Map<IRecipeInput, RecipeOutput> getRecipes() {
        return new AbstractMap<IRecipeInput, RecipeOutput>() {
            public Set<Entry<IRecipeInput, RecipeOutput>> entrySet() {
                return new AbstractSet<Entry<IRecipeInput, RecipeOutput>>() {
                    public Iterator<Entry<IRecipeInput, RecipeOutput>> iterator() {
                        return new Iterator<Entry<IRecipeInput, RecipeOutput>>() {
                            private final Iterator<Entry<IRecipeInput, RecipeOutput>> recipeIt;
                            private IRecipeInput lastInput;

                            {
                                this.recipeIt = BasicMachineRecipeManager.this.recipes.entrySet().iterator();
                            }

                            public boolean hasNext() {
                                return this.recipeIt.hasNext();
                            }

                            public Entry<IRecipeInput, RecipeOutput> next() {
                                Entry<IRecipeInput, RecipeOutput> ret = this.recipeIt.next();
                                this.lastInput = ret.getKey();
                                return ret;
                            }

                            public void remove() {
                                this.recipeIt.remove();
                                BasicMachineRecipeManager.this.removeCachedRecipes(this.lastInput);
                            }
                        };
                    }

                    public int size() {
                        return BasicMachineRecipeManager.this.recipes.size();
                    }
                };
            }

            public RecipeOutput put(IRecipeInput key, RecipeOutput value) {
                BasicMachineRecipeManager.this.addRecipe(key, value, true);
                return null;
            }
        };
    }

    @SubscribeEvent
    public void onOreRegister(OreRegisterEvent event) {
        List<T2<IRecipeInput, RecipeOutput>> datas = new ArrayList();
        Iterator var3 = this.recipes.entrySet().iterator();

        while (var3.hasNext()) {
            Entry<IRecipeInput, RecipeOutput> data = (Entry) var3.next();
            if ((data.getKey()).getClass() == RecipeInputOreDict.class) {
                RecipeInputOreDict recipe = (RecipeInputOreDict) data.getKey();
                if (recipe.input.equals(event.Name)) {
                    datas.add(new T2(data.getKey(), data.getValue()));
                }
            }
        }

        var3 = datas.iterator();

        while (var3.hasNext()) {
            T2<IRecipeInput, RecipeOutput> data = (T2) var3.next();
            this.addToCache(event.Ore, data);
        }

    }

    private T2<IRecipeInput, RecipeOutput> getRecipe(ItemStack input) {
        Map<Integer, T2<IRecipeInput, RecipeOutput>> metaMap = this.recipeCache.get(input.getItem());
        if (metaMap != null) {
            T2<IRecipeInput, RecipeOutput> data = metaMap.get(32767);
            if (data != null) {
                return data;
            }

            int meta = input.getItemDamage();
            data = metaMap.get(meta);
            if (data != null) {
                return data;
            }
        }

        Iterator<T2<IRecipeInput, RecipeOutput>> var5 = this.uncacheableRecipes.iterator();

        T2 data;
        do {
            if (!var5.hasNext()) {
                return null;
            }

            data = var5.next();
        } while (!((IRecipeInput) data.a).matches(input));

        return data;
    }

    private boolean addRecipe(IRecipeInput input, RecipeOutput output, boolean overwrite) {
        if (input == null) {
            this.displayError("The recipe input is null");
            return false;
        } else {
            ListIterator<ItemStack> it = output.items.listIterator();

            ItemStack is;
            while (it.hasNext()) {
                is = it.next();
                if (is == null) {
                    this.displayError("An output ItemStack is null.");
                    return false;
                }

                if (!StackUtil.check(is)) {
                    this.displayError("The output ItemStack " + StackUtil.toStringSafe(is) + " is invalid.");
                    return false;
                }


                it.set(is.copy());
            }

            Iterator<ItemStack> var7 = input.getInputs().iterator();

            while (true) {
                T2 data;
                do {
                    if (!var7.hasNext()) {
                        this.recipes.put(input, output);
                        this.addToCache(input, output);
                        return true;
                    }

                    is = var7.next();
                    data = this.getRecipe(is);
                } while (data == null);

                if (!overwrite) {
                    return false;
                }

                do {
                    this.recipes.remove(data.a);
                    this.removeCachedRecipes((IRecipeInput) data.a);
                    data = this.getRecipe(is);
                } while (data != null);
            }
        }
    }

    private void addToCache(IRecipeInput input, RecipeOutput output) {
        T2<IRecipeInput, RecipeOutput> data = new T2(input, output);
        List<ItemStack> stacks = this.getStacksFromRecipe(input);
        if (stacks != null) {

            for (ItemStack stack : stacks) {
                this.addToCache(stack, data);
            }

            if (input.getClass() == RecipeInputOreDict.class && !this.oreRegisterEventSubscribed) {
                MinecraftForge.EVENT_BUS.register(this);
                this.oreRegisterEventSubscribed = true;
            }
        } else {
            this.uncacheableRecipes.add(data);
        }

    }

    private void addToCache(ItemStack stack, T2<IRecipeInput, RecipeOutput> data) {
        Item item = stack.getItem();
        Map<Integer, T2<IRecipeInput, RecipeOutput>> metaMap = this.recipeCache.computeIfAbsent(item, k -> new HashMap());

        int meta = stack.getItemDamage();
        ((Map) metaMap).put(meta, data);
    }

    private void removeCachedRecipes(IRecipeInput input) {
        List<ItemStack> stacks = this.getStacksFromRecipe(input);
        Iterator it;
        if (stacks != null) {
            it = stacks.iterator();

            while (it.hasNext()) {
                ItemStack stack = (ItemStack) it.next();
                Item item = stack.getItem();
                int meta = stack.getItemDamage();
                Map<Integer, T2<IRecipeInput, RecipeOutput>> map = this.recipeCache.get(item);
                if (map == null) {
                    IC2.log.warn(LogCategory.Recipe, "Inconsistent recipe cache, the entry for the item " + item + "(" + stack + ") is missing.");
                } else {
                    map.remove(meta);
                    if (map.isEmpty()) {
                        this.recipeCache.remove(item);
                    }
                }
            }
        } else {
            it = this.uncacheableRecipes.iterator();

            while (it.hasNext()) {
                T2<IRecipeInput, RecipeOutput> data = (T2) it.next();
                if (data.a == input) {
                    it.remove();
                }
            }
        }

    }

    private List<ItemStack> getStacksFromRecipe(IRecipeInput recipe) {
        if (recipe.getClass() == RecipeInputItemStack.class) {
            return recipe.getInputs();
        } else if (recipe.getClass() == RecipeInputOreDict.class) {
            Integer meta = ((RecipeInputOreDict) recipe).meta;
            if (meta == null) {
                return recipe.getInputs();
            } else {
                List<ItemStack> ret = new ArrayList<>(recipe.getInputs());
                ListIterator<ItemStack> it = ret.listIterator();

                while (it.hasNext()) {
                    ItemStack stack = it.next();
                    if (stack.getItemDamage() != meta) {
                        stack = stack.copy();
                        stack.setItemDamage(meta);
                        it.set(stack);
                    }
                }

                return ret;
            }
        } else {
            return null;
        }
    }

    private void displayError(String msg) {
        if (MainConfig.ignoreInvalidRecipes) {
            IC2.log.warn(LogCategory.Recipe, msg);
        } else {
            throw new RuntimeException(msg);
        }
    }
}
