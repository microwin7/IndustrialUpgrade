package com.denfop.invslot;

import com.denfop.api.inv.IInvSlotProcessableMulti;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class InvSlotProcessableMultiSmelting extends InvSlot implements IInvSlotProcessableMulti {
    public InvSlotProcessableMultiSmelting(TileEntityInventory base1, String name1, int count) {
        super(base1, name1, 1, Access.I, count, InvSide.TOP);
    }

    public boolean accepts(ItemStack itemStack) {
        return FurnaceRecipes.smelting().getSmeltingResult(itemStack) != null;
    }

    public RecipeOutput process(int slotId) {
        ItemStack input = this.get(slotId);
        return input == null ? null
                : new RecipeOutput(null,
                FurnaceRecipes.smelting().getSmeltingResult(input).copy());
    }

    public ItemStack get1(int i) {
        return this.get(i);
    }

    public void consume(int slotId) {
        ItemStack stack = this.get(slotId);
        if (stack != null) {
            stack.stackSize--;
            if (stack.stackSize <= 0) {
                this.put(slotId, null);
            }
        }
    }

    public boolean isEmpty(int slotId) {
        return this.get(slotId) == null;
    }
}