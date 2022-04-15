package com.denfop.invslot;

import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotProcessableConverterSolidMatter extends InvSlotProcessable {
    public final IMachineRecipeManager recipeManager;

    public InvSlotProcessableConverterSolidMatter(TileEntityInventory base1, String name1, int oldStartIndex1, int count, IMachineRecipeManager recipeManager1) {
        super(base1, name1, oldStartIndex1, count);
        this.recipeManager = recipeManager1;
    }

    public boolean accepts(ItemStack itemStack) {
        if (itemStack != null &&
                itemStack.getItem() instanceof ic2.core.item.ItemUpgradeModule)
            return false;
        return (getOutputFor(itemStack, false) != null);
    }

    public RecipeOutput process() {
        ItemStack input = get();
        if (input == null)
            return null;
        RecipeOutput output = getOutputFor(input, false);
        if (output == null)
            return null;
        List<ItemStack> itemsCopy = new ArrayList<>(output.items.size());
        for (ItemStack itemStack : output.items)
            itemsCopy.add(itemStack.copy());
        return new RecipeOutput(output.metadata, itemsCopy);
    }

    public void consume() {
        ItemStack input = get();
        if (input == null)
            throw new IllegalStateException("consume from empty slot");
        RecipeOutput output = getOutputFor(input, true);
        if (output == null)
            throw new IllegalStateException("consume without a processing result");
        if (input != null && input.stackSize <= 0)
            put(null);
    }

    protected RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        return this.recipeManager.getOutputFor(input, adjustInput);
    }


}
