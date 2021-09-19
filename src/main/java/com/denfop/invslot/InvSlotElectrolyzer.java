package com.denfop.invslot;

import com.denfop.IUItem;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

public class InvSlotElectrolyzer extends InvSlot {

    private final int type;
    private int stackSizeLimit;

    public InvSlotElectrolyzer(TileEntityInventory base1, int oldStartIndex1, String name, int type) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.type = type;
        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        if (type == 0)
            return itemStack.getItem().equals(IUItem.anode);
        if (type == 1)
            return itemStack.getItem().equals(IUItem.cathode);
        return false;
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
