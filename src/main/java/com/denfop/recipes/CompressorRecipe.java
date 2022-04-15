package com.denfop.recipes;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.register.RegisterOreDict;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CompressorRecipe {
    public static final String[] recipe = {"plate", "smalldust", "verysmalldust"};
    public static final String[] recipe1 = {"doubleplate", "dust", "smalldust"};

    public static void recipe() {

        addcompressor(new ItemStack(IUItem.sunnarium, 1, 3), new ItemStack(IUItem.sunnarium, 1, 2));
        addcompressor(new ItemStack(IUItem.Helium), new ItemStack(IUItem.cell_all, 1, 2));

        addcompressor(Ic2Items.carbonFiber, 9, new ItemStack(IUItem.coal_chunk1));
        addcompressor(Ic2Items.carbonPlate, 9, new ItemStack(IUItem.compresscarbon));
        addcompressor(Ic2Items.advancedAlloy, 9, new ItemStack(IUItem.compresscarbonultra));
        addcompressor(Ic2Items.iridiumPlate, 4, new ItemStack(IUItem.compressIridiumplate));
        addcompressor(IUItem.uuMatterCell, 1, new ItemStack(IUItem.neutronium));
        addcompressor(new ItemStack(IUItem.compressIridiumplate), 9, new ItemStack(IUItem.doublecompressIridiumplate));
        addcompressor("doubleplateTungsten", 1, IUItem.cell);
        addcompressor(new ItemStack(IUItem.neutronium), 9, new ItemStack(IUItem.neutroniumingot, 1));
        for (int j = 0; j < recipe.length; j++)
            for (int i = 0; i < RegisterOreDict.itemNames().size(); i++) {

                addcompressor(recipe[j] + RegisterOreDict.itemNames().get(i), 9, recipe1[j] + RegisterOreDict.itemNames().get(i));

            }
        for (int j = 0; j < recipe.length; j++)
            for (int i = 0; i < RegisterOreDict.itemNames1().size(); i++) {
                if (j == 0)
                    addcompressor(recipe[j] + RegisterOreDict.itemNames1().get(i), 9, recipe1[j] + RegisterOreDict.itemNames1().get(i));

            }
        if(!Config.advloaded) {
            addcompressor(Ic2Items.uraniumOre, 1, new ItemStack(IUItem.itemIU, 1, 2));
            addcompressor(IC2Items.getItem("UranFuel"), 1, new ItemStack(IUItem.itemIU, 1, 2));
            addcompressor(IC2Items.getItem("crushedUraniumOre"), 1, new ItemStack(IUItem.itemIU, 1, 2));
        }
    }

    public static void addcompressor(ItemStack input, int n, ItemStack output) {

        Recipes.compressor.addRecipe(new RecipeInputItemStack(input, n), null,
                output);
    }

    public static void addcompressor(String input, int n, ItemStack output) {

        Recipes.compressor.addRecipe(new RecipeInputOreDict(input, n), null,
                output);
    }

    public static void addcompressor(String input, int n, String output) {

        Recipes.compressor.addRecipe(new RecipeInputOreDict(input, n), null,
                OreDictionary.getOres(output).get(0));
    }

    public static void addcompressor(ItemStack input, ItemStack output) {

        Recipes.compressor.addRecipe(new RecipeInputItemStack(input, 1), null,
                output);
    }
}
