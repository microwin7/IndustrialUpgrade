package com.denfop.api.recipe;

import ic2.api.recipe.RecipeOutput;

public interface IUpdateTick {

    void onUpdate();

    BaseMachineRecipe getRecipeOutput();

    void setRecipeOutput(BaseMachineRecipe output);

}
