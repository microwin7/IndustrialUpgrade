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

@ZenClass("mods.industrialupgrade.Fermer")
public class CTFermer {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container) {
        MineTweakerAPI.apply(new MachineAddRecipeAction("Fermer", Recipes.fermer,

                MineTweakerMC.getItemStacks(output), null, new IC2RecipeInput(container)));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container, int time) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("operationLength", time);
        MineTweakerAPI.apply(new MachineAddRecipeAction("Fermer", Recipes.fermer,

                MineTweakerMC.getItemStacks(output), nbt, new IC2RecipeInput(container)));
    }

    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container, int time, boolean consume) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("operationLength", time);
        nbt.setBoolean("consume", consume);
        MineTweakerAPI.apply(new MachineAddRecipeAction("Fermer", Recipes.fermer,

                MineTweakerMC.getItemStacks(output), nbt, new IC2RecipeInput(container)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        LinkedHashMap<IRecipeInput, RecipeOutput> recipes = new LinkedHashMap();

        for (Map.Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : Recipes.fermer.getRecipes().entrySet()) {

            for (ItemStack stack : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (stack.isItemEqual(InputHelper.toStack(output))) {
                    recipes.put(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue());
                }
            }
        }

        MineTweakerAPI.apply(new CTFermer.Remove(recipes));
    }

    @ZenMethod
    public static IItemStack[] getOutput(IItemStack input) {
        RecipeOutput output = Recipes.molecular.getOutputFor(MineTweakerMC.getItemStack(input), false);
        if (output == null || output.items.isEmpty())
            return null;
        return MineTweakerMC.getIItemStacks(output.items);
    }

    private static class Remove extends BaseMapRemoval<IRecipeInput, RecipeOutput> {
        protected Remove(Map<IRecipeInput, RecipeOutput> recipes) {
            super("Fermer", Recipes.fermer.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IRecipeInput, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }
}
