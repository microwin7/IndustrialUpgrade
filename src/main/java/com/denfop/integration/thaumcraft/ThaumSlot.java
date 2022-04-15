package com.denfop.integration.thaumcraft;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.IEssentiaContainerItem;

public class ThaumSlot extends InvSlot {
    private int stackSizeLimit;

    public ThaumSlot(TileEntityInventory base1) {
        super(base1, "modules", 1, InvSlot.Access.I, 1, InvSlot.InvSide.TOP);

        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {


        return itemStack.getItem() instanceof IEssentiaContainerItem;
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
