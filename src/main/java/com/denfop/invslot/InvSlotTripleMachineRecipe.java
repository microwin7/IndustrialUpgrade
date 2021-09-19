package com.denfop.invslot;

import com.denfop.api.ITripleMachineRecipeManager;
import com.denfop.tiles.base.TileEntityTripleElectricMachine;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotTripleMachineRecipe extends InvSlotProcessable {
    public final ITripleMachineRecipeManager recipes;

    public InvSlotTripleMachineRecipe(TileEntityInventory base1, String name1, int oldStartIndex1, int count, ITripleMachineRecipeManager recipes) {
        super(base1, name1, oldStartIndex1, count);
        this.recipes = recipes;

    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack == null || !(itemStack.getItem() instanceof ic2.core.item.ItemUpgradeModule);
    }

    protected RecipeOutput getOutput(ItemStack container, ItemStack fill, ItemStack fill1, boolean adjustInput) {

        return recipes.getOutputFor(container, fill, fill1, adjustInput, false);

    }

    protected RecipeOutput getOutputFor(ItemStack input, ItemStack input1, ItemStack input2, boolean adjustInput) {
        return getOutput(input, input1, input2, adjustInput);
    }

    public RecipeOutput process() {
        ItemStack input = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(1);
        ItemStack input2 = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(2);
        if (input == null)
            return null;
        if (input1 == null)
            return null;
        if (input2 == null)
            return null;
        RecipeOutput output = getOutputFor(input2, input1, input, false);
        if (output == null)
            return null;
        List<ItemStack> itemsCopy = new ArrayList<>(output.items.size());
        itemsCopy.addAll(output.items);
        return new RecipeOutput(output.metadata, itemsCopy);
    }

    public void consume() {

        ItemStack input = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(1);
        ItemStack input2 = ((TileEntityTripleElectricMachine) this.base).inputSlotA.get(2);
        getOutputFor(input2, input1, input, true);

        if (input != null && input.stackSize <= 0)
            ((TileEntityTripleElectricMachine) this.base).inputSlotA.put(0, null);
        if (input1 != null && input1.stackSize <= 0)
            ((TileEntityTripleElectricMachine) this.base).inputSlotA.put(1, null);
        if (input2 != null && input2.stackSize <= 0)
            ((TileEntityTripleElectricMachine) this.base).inputSlotA.put(2, null);


    }


}
