package com.denfop.tiles.base;

import com.denfop.Config;
import com.denfop.IUItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityVein extends TileEntity {


    public final ItemStack block;
    public final int tier;
    public final int facing;
    public final boolean active;
    public  int meta;
    public int number;
    public final int max;
    public boolean change;

    public TileEntityVein() {
        this.number = Config.maxVein;
        this.max = Config.maxVein;
        this.meta = 0;
        this.block = new ItemStack(IUItem.heavyore,1,meta);
        this.tier = 14;

        this.facing=0;
        this.active=false;
    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        number = nbttagcompound.getInteger("number");
       change = nbttagcompound.getBoolean("change");
        meta= nbttagcompound.getInteger("meta");
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("number", number);
        nbttagcompound.setInteger("meta", meta);
        nbttagcompound.setBoolean("change", change);
    }

}
