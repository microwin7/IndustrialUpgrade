package com.denfop.recipes;

import com.denfop.IUItem;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;

public class CannerRecipe {
    public static void recipe() {
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.proton, 1), 1),
                IUItem.reactorprotonSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 4), 1),
                IUItem.reactortoriySimple);

        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 0), 1),
                IUItem.reactoramericiumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 1), 1),
                IUItem.reactorneptuniumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 2), 1),
                IUItem.reactorcuriumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 3), 1),
                IUItem.reactorcaliforniaSimple);

        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 5), 1),
                IUItem.reactormendeleviumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 6), 1),
                IUItem.reactorberkeliumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 7), 1),
                IUItem.reactoreinsteiniumSimple);
        Recipes.cannerBottle.addRecipe(new RecipeInputItemStack(IC2Items.getItem("fuelRod"), 1),
                new RecipeInputItemStack(new ItemStack(IUItem.radiationresources, 1, 8), 1),
                IUItem.reactoruran233Simple);
    }

}
