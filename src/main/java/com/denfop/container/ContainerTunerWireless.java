package com.denfop.container;

import com.denfop.tiles.mechanism.TileEntityTunerWireless;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerTunerWireless<T extends TileEntityTunerWireless> extends ContainerFullInv<T> {

    public ContainerTunerWireless(EntityPlayer entityPlayer, T tileEntity1) {
        this(entityPlayer, tileEntity1, 166);


        addSlotToContainer(new SlotInvSlot((tileEntity1).inputslot, 0, 81,
                22));

    }

    public ContainerTunerWireless(EntityPlayer entityPlayer, T tileEntity1, int height) {
        super(entityPlayer, tileEntity1, height);
    }

}
