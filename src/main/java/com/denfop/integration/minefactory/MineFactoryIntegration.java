package com.denfop.integration.minefactory;

import com.denfop.api.Recipes;
import ic2.api.recipe.RecipeInputItemStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.setup.MFRThings;

public class MineFactoryIntegration {
    public static void init() {
        addrecipe(MFRThings.rawRubberItem, MFRThings.rubberSaplingBlock);
    }

    public static void addrecipe(Item input, Block output) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(input)), null, new ItemStack(Item.getItemFromBlock(output)));
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(Item.getItemFromBlock(output))), null, new ItemStack(input, 2));

    }


}
