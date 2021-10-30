package com.denfop.integration.crafttweaker;

import com.denfop.api.Recipes;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.mods.ic2.IC2RecipeInput;
import minetweaker.mods.ic2.MachineAddRecipeAction;
import modtweaker2.helpers.InputHelper;
import modtweaker2.utils.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@ZenClass("mods.industrialupgrade.MolecularTransformer")
public class CTMolecularTransformer {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient ingredient, double energy) {
        if (ingredient.getAmount() < 0) {
            MineTweakerAPI.logWarning("invalid ingredient: " + ingredient + " - stack size not known");
        } else {
            NBTTagCompound tag = new NBTTagCompound();
            tag.setDouble("energy", energy);
            MineTweakerAPI.apply(new MachineAddRecipeAction("MolecularTransformer", Recipes.molecular,

                    new ItemStack[]{getItemStack(output)}, tag, new IC2RecipeInput(ingredient)));
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

    private static class Remove extends BaseMapRemoval<IRecipeInput, RecipeOutput> {
        protected Remove(Map<IRecipeInput, RecipeOutput> recipes) {
            super("MolecularTransformer", Recipes.molecular.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IRecipeInput, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }

    @ZenMethod
    public static IItemStack[] getOutput(IItemStack input) {
        RecipeOutput output = Recipes.molecular.getOutputFor(MineTweakerMC.getItemStack(input), false);
        if (output == null || output.items.isEmpty())
            return null;
        return MineTweakerMC.getIItemStacks(output.items);
    }
}
