package com.denfop.invslot;

import com.denfop.IUItem;
import com.denfop.item.modules.EnumQuarryType;
import com.denfop.item.modules.QuarryModule;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotQuantumQuarry extends InvSlot {

    public final int type;
    public int stackSizeLimit;

    public InvSlotQuantumQuarry(TileEntityInventory base1, int oldStartIndex1, String name, int type) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
        this.type = type;
    }

    public boolean accepts(ItemStack itemStack) {
        if (type == 0) {

            return itemStack.getItem() instanceof QuarryModule && (IUItem.quarry_modules.get(itemStack.getItemDamage()).type != EnumQuarryType.WHITELIST && IUItem.quarry_modules.get(itemStack.getItemDamage()).type != EnumQuarryType.BLACKLIST);
        } else if (type == 1) {
            return itemStack.getItem() instanceof QuarryModule && itemStack.getItemDamage() > 11;

        } else {
            return itemStack.getItem().equals(IUItem.analyzermodule);

        }
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    public void update() {
    }
}
