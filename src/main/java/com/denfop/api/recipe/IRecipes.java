package com.denfop.api.recipe;

import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.List;

public interface IRecipes {

    void addRecipe(String name, BaseMachineRecipe recipe);

    void addRecipeList(String name, List<BaseMachineRecipe> list);

    List<BaseMachineRecipe> getRecipeList(String name);

    void addRecipeManager(String name, int size, boolean consume);

    BaseMachineRecipe getRecipeOutput(String name, boolean adjustInput, ItemStack... stacks);

    BaseMachineRecipe getRecipeOutputFromInstruments(String name, boolean adjustInput, ItemStack... stacks);


    BaseMachineRecipe getRecipeOutput(String name, boolean adjustInput, List<ItemStack> stacks);

    BaseMachineRecipe getRecipeMultiOutput(String name, boolean adjustInput, List<ItemStack> stacks);


    IBaseRecipe getRecipe(String name);

    void removeRecipe(String name, RecipeOutput output);

    BaseMachineRecipe getRecipeOutputFluid(String name, boolean consume, List<ItemStack> list, FluidTank tank);

}
