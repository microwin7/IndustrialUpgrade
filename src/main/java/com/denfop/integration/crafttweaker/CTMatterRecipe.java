package com.denfop.integration.crafttweaker;

import com.denfop.api.Recipes;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import modtweaker2.helpers.InputHelper;
import modtweaker2.utils.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedHashMap;
import java.util.Map;

@ZenClass("mods.industrialupgrade.MatterRecipe")
public class CTMatterRecipe {
    @ZenMethod
    public static void addRecipe(IItemStack output, double matter, double sunmatter, double aquamatter, double nethermatter, double nightmatter, double earthmatter, double endmatter, double aermatter) {

        NBTTagCompound tag = new NBTTagCompound();
        double[] quantitysolid = {matter, sunmatter, aquamatter, nethermatter, nightmatter, earthmatter, endmatter, aermatter};
        for (int i = 0; i < quantitysolid.length; i++)
            ModUtils.SetDoubleWithoutItem(tag, ("quantitysolid_" + i), quantitysolid[i]);

        MineTweakerAPI.apply(new MachineAddRecipeAction("matterrecipe", Recipes.matterrecipe,

                new ItemStack[]{getItemStack(output)}, tag, new RecipeInputItemStack(getItemStack(output))));

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
            super("matterrecipe", Recipes.matterrecipe.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IRecipeInput, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }

    @ZenMethod
    public static IItemStack[] getOutput(IItemStack input) {
        RecipeOutput output = Recipes.matterrecipe.getOutputFor(MineTweakerMC.getItemStack(input), false);
        if (output == null || output.items.isEmpty())
            return null;
        return MineTweakerMC.getIItemStacks(output.items);
    }
}
