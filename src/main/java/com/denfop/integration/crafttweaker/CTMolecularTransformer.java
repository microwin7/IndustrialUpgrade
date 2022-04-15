package com.denfop.integration.crafttweaker;

import com.denfop.api.Recipes;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mods.ic2.IC2RecipeInput;
import modtweaker2.helpers.InputHelper;
import modtweaker2.utils.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ZenClass("mods.industrialupgrade.MolecularTransformer")
public class CTMolecularTransformer {


    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient ingredient, double energy) {
        if (ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("energy", energy);
            MineTweakerAPI.apply(new AddMolecularAction(ingredient,

                    new ItemStack[]{getItemStack(output)}, tag, false));
        }
    }

    @ZenMethod
    public static void addOreRecipe(IItemStack output, IIngredient ingredient, double energy) {
        if (ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("energy", energy);
            MineTweakerAPI.apply(new AddMolecularAction(ingredient,

                    new ItemStack[]{getItemStack(output)}, tag, true));
        }
    }

    public static ItemStack getItemStack(IItemStack item) {
        if (item == null) {
            return null;
        } else {
            Object internal = item.getInternal();
            if (!(internal instanceof ItemStack)) {
                MineTweakerAPI.logError("Not a valid item stack: " + item);
            }

            return new ItemStack(((ItemStack) internal).getItem(), item.getAmount(), item.getDamage());
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        LinkedHashMap<IRecipeInput, RecipeOutput> recipes = new LinkedHashMap();

        for (Map.Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : Recipes.molecular.getRecipes().entrySet()) {

            for (ItemStack stack : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (stack.isItemEqual(InputHelper.toStack(output))) {
                    recipes.put(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue());
                }
            }
        }

        MineTweakerAPI.apply(new Remove(recipes));
    }

    @ZenMethod
    public static IItemStack[] getOutput(IItemStack input) {
        RecipeOutput output = Recipes.molecular.getOutputFor(MineTweakerMC.getItemStack(input), false);
        if (output == null || output.items.isEmpty())
            return null;
        return MineTweakerMC.getIItemStacks(output.items);
    }

    private static class AddMolecularAction extends OneWayAction {
        private final IIngredient ingredient;
        private final NBTTagCompound tag;
        private final ItemStack[] output;
        private final boolean oreDictionary;

        public AddMolecularAction(IIngredient ingredient, ItemStack[] output, NBTTagCompound tag, boolean oreDictionary) {
            this.ingredient = ingredient;
            this.tag = tag;
            this.output = output;
            this.oreDictionary = oreDictionary;
        }

        public static ItemStack getItemStack(IItemStack item) {
            if (item == null) {
                return null;
            } else {
                Object internal = item.getInternal();
                if (!(internal instanceof ItemStack)) {
                    MineTweakerAPI.logError("Not a valid item stack: " + item);
                }

                return new ItemStack(((ItemStack) internal).getItem(), item.getAmount(), item.getDamage());
            }
        }

        public void apply() {
            ItemStack stack = new IC2RecipeInput(this.ingredient).getInputs().get(0);
            String ore = OreDictionary.getOreName(OreDictionary.getOreID(stack));
            if (oreDictionary)
                Recipes.molecular.addRecipe(
                        OreDictionary.getOres(ore).isEmpty() ? new IC2RecipeInput(this.ingredient) : new RecipeInputOreDict(ore),
                        tag,
                        true,
                        output);
            else
                Recipes.molecular.addRecipe(
                        new IC2RecipeInput(this.ingredient),
                        tag,
                        true,
                        output);

        }

        public String describe() {
            return "Adding moleculaqr recipe " + this.ingredient + " + " + this.tag + " => " + this.output;
        }

        public Object getOverrideKey() {
            return null;
        }

        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + ((this.ingredient != null) ? this.ingredient.hashCode() : 0);
            hash = 67 * hash + ((this.tag != null) ? this.tag.hashCode() : 0);
            hash = 67 * hash + ((this.output != null) ? Arrays.hashCode(this.output) : 0);
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            CTMolecularTransformer.AddMolecularAction other = (CTMolecularTransformer.AddMolecularAction) obj;
            if (!Objects.equals(this.ingredient, other.ingredient))
                return false;
            if (!Objects.equals(this.tag, other.tag))
                return false;

            return Arrays.equals(this.output, other.output);
        }
    }

    private static class Remove extends BaseMapRemoval<IRecipeInput, RecipeOutput> {
        protected Remove(Map<IRecipeInput, RecipeOutput> recipes) {
            super("MolecularTransformer", Recipes.molecular.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IRecipeInput, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }
}
