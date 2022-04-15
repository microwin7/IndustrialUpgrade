package com.denfop.invslot;

import com.denfop.item.modules.ItemEntityModule;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotModules extends InvSlot {
    private int stackSizeLimit;

    public InvSlotModules(TileEntityInventory base1) {
        super(base1, "modules", 20, InvSlot.Access.IO, 4, InvSlot.InvSide.TOP);

        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        if (!(itemStack.getItem() instanceof ItemEntityModule))
            return false;
        if (itemStack.getItemDamage() == 0)
            return false;


        return ItemEntityModule.getMobTypeFromStack(itemStack) != null;
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
