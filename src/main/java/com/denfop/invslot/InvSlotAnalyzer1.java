package com.denfop.invslot;

import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InvSlotAnalyzer1 extends InvSlot {
    private int stackSizeLimit;
    public InvSlotAnalyzer1(TileEntityInventory base1, int oldStartIndex1) {
        super(base1, "input5", oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);

        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        int id = OreDictionary.getOreID(itemStack);
        String name = OreDictionary.getOreName(id);
        return name.startsWith("ore");
    }
    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
