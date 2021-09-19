package com.denfop.recipemanager;

import com.denfop.api.IFluidRecipeManager;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class FluidRecipeManager implements IFluidRecipeManager {
    @Override
    public void addRecipe(FluidStack fluidStack, FluidStack[] output) {

        if (output == null)
            throw new NullPointerException("The recipe output is null");
        if (fluidStack == null)
            throw new NullPointerException("The fluidStack is null");

        this.recipes.put(new IFluidRecipeManager.Input(fluidStack), output);
    }

    @Override
    public FluidStack[] getOutputFor(FluidStack fluidStack, boolean adjustInput, boolean acceptTest) {
        if (fluidStack == null)
            return null;


        for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry : this.recipes.entrySet()) {
            IFluidRecipeManager.Input recipeInput = entry.getKey();
            if (acceptTest) {
                continue;
            }

            if (recipeInput.matches(fluidStack)) {
                if (acceptTest || fluidStack.amount >= recipeInput.fluidStack.amount) {
                    if (adjustInput) {


                        fluidStack.amount -= recipeInput.fluidStack.amount;

                    }
                    return entry.getValue();
                }
                break;
            }
        }
        return null;
    }


    @Override
    public Map<IFluidRecipeManager.Input, FluidStack[]> getRecipes() {
        return this.recipes;
    }

    private final Map<IFluidRecipeManager.Input, FluidStack[]> recipes = new HashMap<>();

}
