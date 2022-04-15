package com.denfop.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class NBTData {
    public static NBTTagCompound getOrCreateNbtData(final ItemStack itemstack) {
        NBTTagCompound nbttagcompound = itemstack.getTagCompound();
        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            itemstack.setTagCompound(nbttagcompound);
            nbttagcompound.setBoolean("create", true);

        }
        return nbttagcompound;
    }

    public static NBTTagCompound getOrCreateNbtData1(final EntityPlayer player) {
        NBTTagCompound nbttagcompound = player.getEntityData();

        if (nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
        }
        return nbttagcompound;
    }
}
