package com.denfop.api.recipe;

import ic2.api.recipe.RecipeOutput;

public interface IUpdateTick {
   void onUpdate();

   RecipeOutput getRecipeOutput();

   void setRecipeOutput(RecipeOutput output);
}
