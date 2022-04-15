package com.denfop.invslot;

import com.denfop.IUItem;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotLatheUpgrade extends InvSlot {

    private int stackSizeLimit;

    public InvSlotLatheUpgrade(TileEntityInventory base1, int oldStartIndex1) {
        super(base1, "input4", oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack.getItem().equals(IUItem.lathingprogram);
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
