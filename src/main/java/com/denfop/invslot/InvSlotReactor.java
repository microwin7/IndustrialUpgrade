
package com.denfop.invslot;

import com.denfop.tiles.reactors.TileEntityAdvNuclearReactorElectric;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotReactor extends InvSlot {


    public InvSlotReactor(TileEntityAdvNuclearReactorElectric base1, String name1, int oldStartIndex1, int count) {
        super(base1, name1, oldStartIndex1, Access.IO, count);
        this.setStackSizeLimit(1);
    }

    public boolean accepts(ItemStack itemStack) {
        return ((TileEntityAdvNuclearReactorElectric) this.base).isUsefulItem(itemStack, true);
    }

    public int size() {
        return ((TileEntityAdvNuclearReactorElectric) this.base).getReactorSize() * 6;
    }

    public ItemStack get(int index) {
        return super.get(this.mapIndex(index));
    }

    public ItemStack get(int x, int y) {
        return super.get(y * 10 + x);
    }

    public void put(int index, ItemStack content) {
        super.put(this.mapIndex(index), content);
    }

    public void put(int x, int y, ItemStack content) {
        super.put(y * 10 + x, content);
    }

    private int mapIndex(int index) {
        int size = this.size();
        int cols = size / 6;
        int remCols;
        int row;
        if (index < size) {
            remCols = index / cols;
            row = index % cols;
            return remCols * 10 + row;
        } else {
            index -= size;
            remCols = 10 - cols;
            row = index / remCols;
            int col = cols + index % remCols;
            return row * 10 + col;
        }
    }
}
