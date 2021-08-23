package com.denfop.invslot;

import com.denfop.api.IDoubleMachineRecipeManager;
import com.denfop.tiles.base.TileEntityDoubleElectricMachine;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotDoubleMachineRecipe extends InvSlotProcessable {

    public final IDoubleMachineRecipeManager recipes;

    public InvSlotDoubleMachineRecipe(TileEntityInventory base1, String name1, int oldStartIndex1, int count, IDoubleMachineRecipeManager recipes) {
        super(base1, name1, oldStartIndex1, count);
       this.recipes=recipes;
    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack == null || !(itemStack.getItem() instanceof ic2.core.item.ItemUpgradeModule);

    }

    protected RecipeOutput getOutput(ItemStack container, ItemStack fill, boolean adjustInput) {

        return recipes.getOutputFor(container, fill, adjustInput, false);

    }

    protected RecipeOutput getOutputFor(ItemStack input, ItemStack input1, boolean adjustInput) {
        return getOutput(input, input1, adjustInput);
    }

    public RecipeOutput process() {
        ItemStack input = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(1);
        if (input == null)
            return null;
        if (input1 == null)
            return null;
        RecipeOutput output = getOutputFor(input, input1, false);
        if (output == null)
            return null;
        List<ItemStack> itemsCopy = new ArrayList<>(output.items.size());
        itemsCopy.addAll(output.items);
        return new RecipeOutput(output.metadata, itemsCopy);
    }

    public void consume() {

        ItemStack input = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(1);
        getOutputFor(input, input1, true);

        if (input != null && input.stackSize <= 1)
            ((TileEntityDoubleElectricMachine) this.base).inputSlotA.put(0,null);
        if (input1 != null && input1.stackSize <= 1)
            ((TileEntityDoubleElectricMachine) this.base).inputSlotA.put(1,null);


    }


}
