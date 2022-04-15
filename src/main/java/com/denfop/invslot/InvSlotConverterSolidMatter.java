package com.denfop.invslot;

import com.denfop.item.matter.SolidMatter;
import com.denfop.tiles.base.TileEntityConverterSolidMatter;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.util.StackUtil;
import net.minecraft.item.ItemStack;

public class InvSlotConverterSolidMatter extends InvSlot {

    public InvSlotConverterSolidMatter(TileEntityInventory base1, String string, int oldStartIndex1) {
        super(base1, string, oldStartIndex1, InvSlot.Access.IO, 7, InvSlot.InvSide.TOP);

    }

    public void getmatter() {

        for (int i = 0; i < this.size(); i++) {
            if (get(i) != null) {
                TileEntityConverterSolidMatter tile = (TileEntityConverterSolidMatter) base;
                int meta = get(i).getItemDamage();
                if (tile.quantitysolid[meta] <= 4800) {
                    tile.quantitysolid[meta] += 200;
                    this.consume(i, 1);
                }

            }
        }


    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack.getItem() instanceof SolidMatter;
    }

    public void consume(int content, int amount) {
        consume(content, amount, false, false);
    }

    public void consume(int content, int amount, boolean simulate, boolean consumeContainers) {
        ItemStack ret = null;

        ItemStack stack = get(content);
        if (stack != null && stack.stackSize >= 1 &&

                accepts(stack) && (ret == null ||
                StackUtil.isStackEqualStrict(stack, ret)) && (stack.stackSize == 1 || consumeContainers ||
                !stack.getItem().hasContainerItem(stack))) {
            int currentAmount = Math.min(amount, stack.stackSize);
            amount -= currentAmount;
            if (!simulate)
                if (stack.stackSize == currentAmount) {
                    if (!consumeContainers && stack.getItem().hasContainerItem(stack)) {
                        put(content, stack.getItem().getContainerItem(stack));
                    } else {
                        put(content, null);
                    }
                } else {
                    stack.stackSize -= currentAmount;
                }
            if (ret == null) {
                StackUtil.copyWithSize(stack, currentAmount);
            } else {
                ret.stackSize += currentAmount;
            }
            if (amount == 0) {
            }
        }

    }

}
