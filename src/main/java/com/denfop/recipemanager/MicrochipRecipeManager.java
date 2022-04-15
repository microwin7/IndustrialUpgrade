package com.denfop.recipemanager;

import com.denfop.api.IMicrochipFarbricatorRecipeManager;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;

public class MicrochipRecipeManager implements IMicrochipFarbricatorRecipeManager {
    private final Map<IMicrochipFarbricatorRecipeManager.Input, RecipeOutput> recipes = new HashMap<>();

    public void addRecipe(IRecipeInput container, IRecipeInput fill, IRecipeInput container1, IRecipeInput fill1,
                          IRecipeInput fill2, ItemStack output, NBTTagCompound tag) {
        if (container == null)
            throw new NullPointerException("The slot 1 recipe input is null");
        if (fill == null)
            throw new NullPointerException("The slot 2 recipe input is null");
        if (fill1 == null)
            throw new NullPointerException("The slot 3 recipe input is null");
        if (fill2 == null)
            throw new NullPointerException("The slot 4 recipe input is null");
        if (container1 == null)
            throw new NullPointerException("The slot 5 recipe input is null");
        if (output == null)
            throw new NullPointerException("The recipe output is null");
        if (!StackUtil.check(output))
            throw new IllegalArgumentException("The recipe output " + StackUtil.toStringSafe(output) + " is invalid");
        for (IMicrochipFarbricatorRecipeManager.Input input : this.recipes.keySet()) {
            for (ItemStack containerStack : container.getInputs()) {
                for (ItemStack fillStack : fill.getInputs()) {
                    for (ItemStack containerStack1 : container1.getInputs()) {
                        for (ItemStack fillStack1 : fill1.getInputs()) {
                            for (ItemStack fillStack2 : fill2.getInputs()) {

                                if (input.matches(containerStack, fillStack, fillStack1, fillStack2, containerStack1)) {
                                    this.recipes.remove(input);
                                    this.recipes.put(new IMicrochipFarbricatorRecipeManager.Input(container, fill, fill1, fill2, container1),
                                            new RecipeOutput(tag, output));
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
        this.recipes.put(new IMicrochipFarbricatorRecipeManager.Input(container, fill, fill1, fill2, container1),
                new RecipeOutput(tag, output));
    }

    public RecipeOutput getOutputFor(ItemStack container, ItemStack fill, ItemStack container1, ItemStack fill1,
                                     ItemStack fill2, boolean adjustInput, boolean acceptTest) {
        if (container == null || fill == null || container1 == null || fill1 == null || fill2 == null)
            return null;
        for (Map.Entry<IMicrochipFarbricatorRecipeManager.Input, RecipeOutput> entry : this.recipes.entrySet()) {
            IMicrochipFarbricatorRecipeManager.Input recipeInput = entry.getKey();

            if (recipeInput.matches(container, fill, container1, fill1, fill2)) {
                if (acceptTest || container.stackSize >= recipeInput.container.getAmount() && container1.stackSize >= recipeInput.fill1.getAmount() && fill.stackSize >= recipeInput.fill.getAmount() && fill1.stackSize >= recipeInput.fill2.getAmount() && fill2.stackSize >= recipeInput.container1.getAmount()) {
                    if (adjustInput) {
                        container.stackSize -= recipeInput.container.getAmount();
                        container1.stackSize -= recipeInput.fill1.getAmount();
                        fill.stackSize -= recipeInput.fill.getAmount();
                        fill1.stackSize -= recipeInput.fill2.getAmount();
                        fill2.stackSize -= recipeInput.container1.getAmount();
                    }
                    return entry.getValue();
                }
                break;
            }
        }
        return null;
    }

    public Map<IMicrochipFarbricatorRecipeManager.Input, RecipeOutput> getRecipes() {
        return this.recipes;
    }

}
