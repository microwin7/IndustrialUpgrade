package com.denfop.invslot;

import com.denfop.tiles.reactors.TileEntityPerNuclearReactor;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotReactor2 extends InvSlot {


    public InvSlotReactor2(TileEntityPerNuclearReactor base1, String name1, int oldStartIndex1, int count) {
        super(base1, name1, oldStartIndex1, Access.IO, count);
        this.setStackSizeLimit(1);
    }

    public boolean accepts(ItemStack itemStack) {
        return ((TileEntityPerNuclearReactor) this.base).isUsefulItem(itemStack, true);
    }

    public int size() {
        return ((TileEntityPerNuclearReactor) this.base).getReactorSize() * 7;
    }

    public ItemStack get(int index) {
        return super.get(this.mapIndex(index));
    }

    public ItemStack get(int x, int y) {
        return super.get(y * 11 + x);
    }

    public void put(int index, ItemStack content) {
        super.put(this.mapIndex(index), content);
    }

    public void put(int x, int y, ItemStack content) {
        super.put(y * 11 + x, content);
    }

    private int mapIndex(int index) {
        int size = this.size();
        int cols = size / 7;
        int remCols;
        int row;
        if (index < size) {
            remCols = index / cols;
            row = index % cols;
            return remCols * 11 + row;
        } else {
            index -= size;
            remCols = 11 - cols;
            row = index / remCols;
            int col = cols + index % remCols;
            return row * 11 + col;
        }
    }
}
