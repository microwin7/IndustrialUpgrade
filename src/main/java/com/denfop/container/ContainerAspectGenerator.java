package com.denfop.container;

import com.denfop.integration.thaumcraft.TileEntityAspectGenerator;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

public class ContainerAspectGenerator<T extends TileEntityAspectGenerator> extends ContainerFullInv<T> {

    public ContainerAspectGenerator(EntityPlayer entityPlayer, T tileEntity1) {
        this(entityPlayer, tileEntity1, 166);


        addSlotToContainer(new SlotInvSlot((tileEntity1).inputSlot, 0, 81,
                22));

    }

    public ContainerAspectGenerator(EntityPlayer entityPlayer, T tileEntity1, int height) {
        super(entityPlayer, tileEntity1, height);
    }

}
