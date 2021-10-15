package com.denfop.tiles.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileOilBlock extends TileEntity {


    public final int tier;
    public final boolean  active;
    public final int facing;
    public int number;
    public int max;
    public boolean change;

    public TileOilBlock() {
        this.tier = 14;
        this.facing=0;
        this.active=false;

    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        number = nbttagcompound.getInteger("number");
        max = nbttagcompound.getInteger("max");
        change = nbttagcompound.getBoolean("change");
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("number", number);
        nbttagcompound.setBoolean("change", change);
        nbttagcompound.setInteger("max", max);
    }

}
