package com.denfop.api.recipe;

import com.denfop.tiles.mechanism.EnumTypeMachines;
import ic2.api.recipe.RecipeOutput;

public interface IMultiUpdateTick extends IUpdateTick{
    BaseMachineRecipe getRecipeOutput(int slotId);

    void setRecipeOutput(BaseMachineRecipe output,int slotId);

    EnumTypeMachines getType();
}
