package com.denfop.integration.adv;

import advsolar.common.AdvancedSolarPanel;
import com.denfop.IUItem;
import com.denfop.recipes.CompressorRecipe;
import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AdvFix {
    public static void init() {
        Map<IRecipeInput, RecipeOutput> recipe =  Recipes.compressor.getRecipes();
        Map<IRecipeInput, RecipeOutput> recipe1 =  new HashMap<>();
        Set<Map.Entry<IRecipeInput, RecipeOutput>> entry = recipe.entrySet();
        for(Map.Entry<IRecipeInput, RecipeOutput> e : entry){
           if(e.getValue().items.get(0).getUnlocalizedName().equals("itemUranIngot"))
               recipe1.put(e.getKey(),e.getValue());
        }
         entry = recipe1.entrySet();
        for(Map.Entry<IRecipeInput, RecipeOutput> e : entry){
                recipe.remove(e.getKey(),e.getValue());
        }

        CompressorRecipe.addcompressor(Ic2Items.uraniumOre, 1, new ItemStack(IUItem.itemIU, 1, 2));
        CompressorRecipe.addcompressor(IC2Items.getItem("UranFuel"), 1, new ItemStack(IUItem.itemIU, 1, 2));
        CompressorRecipe.addcompressor(IC2Items.getItem("crushedUraniumOre"), 1, new ItemStack(IUItem.itemIU, 1, 2));

    }
}
