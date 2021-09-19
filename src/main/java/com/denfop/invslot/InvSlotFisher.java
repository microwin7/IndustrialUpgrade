package com.denfop.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;

public class InvSlotFisher extends InvSlot {

    private int stackSizeLimit;

    public InvSlotFisher(TileEntityInventory base1, int oldStartIndex1) {
        super(base1, "input2", oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemFishingRod;
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    public void consume(int amount) {
        consume(amount, false, false);
    }

    public void consume(int amount, boolean simulate, boolean consumeContainers) {
        ItemStack ret = null;
        for (int i = 0; i < size(); i++) {
            ItemStack stack = get(i);
            if (stack != null && stack.stackSize >= 1 &&

                    accepts(stack) && (ret == null ||
                    StackUtil.isStackEqualStrict(stack, ret)) && (stack.stackSize == 1 || consumeContainers ||
                    !stack.getItem().hasContainerItem(stack))) {
                int currentAmount = Math.min(amount, stack.stackSize);
                amount -= currentAmount;
                if (!simulate)
                    if (stack.stackSize == currentAmount) {
                        if (!consumeContainers && stack.getItem().hasContainerItem(stack)) {
                            put(i, stack.getItem().getContainerItem(stack));
                        } else {
                            put(i, null);
                        }
                    } else {
                        stack.stackSize -= currentAmount;
                    }
                if (ret == null) {
                    ret = StackUtil.copyWithSize(stack, currentAmount);
                } else {
                    ret.stackSize += currentAmount;
                }
                if (amount == 0)
                    break;
            }
        }
    }
}
