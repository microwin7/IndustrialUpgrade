package com.denfop.recipemanager;

import com.denfop.api.IDoubleMachineRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class UpgradeMachineRecipeManager implements IDoubleMachineRecipeManager {
    private final Map<IDoubleMachineRecipeManager.Input, RecipeOutput> recipes = new HashMap<>();

    public void addRecipe(IRecipeInput container, IRecipeInput fill, NBTTagCompound metadata, ItemStack output) {
        if (container == null)
            throw new NullPointerException("The container recipe input is null");
        if (fill == null)
            throw new NullPointerException("The fill recipe input is null");
        if (output == null)
            throw new NullPointerException("The recipe output is null");
        if (!StackUtil.check(output))
            throw new IllegalArgumentException("The recipe output " + StackUtil.toStringSafe(output) + " is invalid");
        for (IDoubleMachineRecipeManager.Input input : this.recipes.keySet()) {
            for (ItemStack containerStack : container.getInputs()) {
                for (ItemStack fillStack : fill.getInputs()) {
                    if (input.matches(containerStack, fillStack)) {
                        this.recipes.remove(input);
                        this.recipes.put(new IDoubleMachineRecipeManager.Input(container, fill),
                                new RecipeOutput(metadata, output));
                        return;
                    }

                }
            }
        }
        this.recipes.put(new IDoubleMachineRecipeManager.Input(container, fill),
                new RecipeOutput(metadata, output));
    }

    public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, boolean adjustInput, boolean acceptTest) {
        if (acceptTest) {
            if (container == null && fill == null)
                return null;
        } else if (container == null || fill == null) {
            return null;
        }
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : this.recipes.entrySet()) {
            IDoubleMachineRecipeManager.Input recipeInput = entry.getKey();
            if (acceptTest && container == null) {
                if (recipeInput.fill.matches(fill))
                    return entry.getValue();
                continue;
            }
            if (acceptTest && fill == null) {
                if (recipeInput.container.matches(container))
                    return entry.getValue();
                continue;
            }
            if (recipeInput.matches(container, fill)) {
                if (acceptTest || container.stackSize >= recipeInput.container.getAmount() && fill.stackSize >= recipeInput.fill.getAmount()) {
                    if (adjustInput) {

                        container.stackSize -= recipeInput.container.getAmount();
                        fill.stackSize -= recipeInput.fill.getAmount();
                    }
                    return entry.getValue();
                }
                break;
            }

        }
        return null;
    }

    public Map<IDoubleMachineRecipeManager.Input, RecipeOutput> getRecipes() {
        return this.recipes;
    }
}
