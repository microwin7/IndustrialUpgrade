package com.denfop.recipemanager;

import com.denfop.api.IGeneratorRecipemanager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class GeneratorRecipeManager implements IGeneratorRecipemanager {
    private final Map<NBTTagCompound, FluidStack> recipes = new HashMap<>();

    @Override
    public void addRecipe(NBTTagCompound var2, FluidStack output) {

        if (output == null)
            throw new NullPointerException("The recipe output is null");

        this.recipes.put(var2, output);
    }

    @Override
    public Map<NBTTagCompound, FluidStack> getRecipes() {
        return this.recipes;
    }

}
