package com.denfop.recipes;

import com.denfop.IUItem;
import com.denfop.register.RegisterOreDict;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class MetalFormerRecipe {
    public static final String[] recipe = {"ingot", "plate", "ingot"};
    public static final String[] recipe1 = {"plate", "casing", "stik"};

    public static void init() {
        for (int j = 0; j < recipe.length; j++)
            for (int i = 0; i < RegisterOreDict.itemNames().size(); i++) {
                if (j == 0)
                    addmolot(recipe[j] + RegisterOreDict.itemNames().get(i), new ItemStack(IUItem.plate, 1, i), 1);

                if (j != 2 && j != 0)
                    addmolot(recipe[j] + RegisterOreDict.itemNames().get(i), recipe1[j] + RegisterOreDict.itemNames().get(i), 1);
                if (j == 2)
                    addExtruding(recipe[j] + RegisterOreDict.itemNames().get(i), recipe1[j] + RegisterOreDict.itemNames().get(i), 1);

            }
        for (int j = 0; j < recipe.length; j++)
            for (int i = 0; i < RegisterOreDict.itemNames1().size(); i++) {
                if (j != 2)
                    addmolot(recipe[j] + RegisterOreDict.itemNames1().get(i), recipe1[j] + RegisterOreDict.itemNames1().get(i), 1);
            }
    }

    public static void addmolot(String input, String output, int n) {
        ItemStack stack = OreDictionary.getOres(output).get(0);
        stack.stackSize = n;
        if (Recipes.metalformerRolling.getRecipes().get(new RecipeInputOreDict(input, 1)) != null) {
            Recipes.metalformerRolling.getRecipes().remove(new RecipeInputOreDict(input, 1));
            Recipes.metalformerRolling.addRecipe(new RecipeInputOreDict(input, 1), null,
                    stack);
        }

    }

    public static void addmolot(String input, ItemStack output, int n) {

        output.stackSize = n;
        if (Recipes.metalformerRolling.getRecipes().get(new RecipeInputOreDict(input, 1)) != null) {
            Recipes.metalformerRolling.getRecipes().remove(new RecipeInputOreDict(input, 1));
            Recipes.metalformerRolling.addRecipe(new RecipeInputOreDict(input, 1), null,
                    output);
        }
    }

    public static void addExtruding(String input, String output, int n) {
        ItemStack stack = OreDictionary.getOres(output).get(0);
        stack.stackSize = n;
        if (Recipes.metalformerExtruding.getRecipes().get(new RecipeInputOreDict(input, 1)) != null) {
            Recipes.metalformerExtruding.getRecipes().remove(new RecipeInputOreDict(input, 1));
            Recipes.metalformerExtruding.addRecipe(new RecipeInputOreDict(input, 1), null,
                    stack);
        }

    }
}
