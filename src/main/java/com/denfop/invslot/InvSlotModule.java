package com.denfop.invslot;

import com.denfop.item.modules.QuarryModule;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class InvSlotModule extends InvSlot {
    private final int type;
    private int stackSizeLimit;

    public InvSlotModule(TileEntityInventory base1, String name, int oldStartIndex1, int type, int count) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, count, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
        this.type = type;
    }

    public boolean accepts(ItemStack itemStack) {
        if (type == 0) {
            int id = OreDictionary.getOreID(itemStack);
            String ore = OreDictionary.getOreName(id);

            return ore.startsWith("ore") || ore.startsWith("gem") || ore.startsWith("ingot") || ore.startsWith("dust") || ore.startsWith("shard");
        } else {
            return itemStack.getItem() instanceof QuarryModule && itemStack.getItemDamage() >= 12;
        }
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
