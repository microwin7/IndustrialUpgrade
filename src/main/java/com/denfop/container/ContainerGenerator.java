package com.denfop.container;

import com.denfop.tiles.mechanism.TileEntityBaseGenerator;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerGenerator<T extends TileEntityBaseGenerator> extends ContainerFullInv<T> {

    public ContainerGenerator(EntityPlayer entityPlayer, T tileEntity1) {
        super(entityPlayer, tileEntity1, 166);
        this.addSlotToContainer(new SlotInvSlot(tileEntity1.fuelSlot, 0, 65, 53));
        this.addSlotToContainer(new SlotInvSlot(tileEntity1.chargeSlot, 0, 65, 17));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("storage");
        ret.add("fuel");
        return ret;
    }
}
