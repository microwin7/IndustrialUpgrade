package com.denfop.invslot;

import com.denfop.item.modules.ItemWirelessModule;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotTuner extends InvSlot {

    private int stackSizeLimit;

    public InvSlotTuner(TileEntityInventory base1, String name, int oldStartIndex1) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {

        return itemStack.getItem() instanceof ItemWirelessModule;

    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
