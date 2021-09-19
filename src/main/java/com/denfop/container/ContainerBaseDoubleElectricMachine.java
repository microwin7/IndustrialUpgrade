package com.denfop.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public abstract class ContainerBaseDoubleElectricMachine<T extends TileEntityElectricMachine> extends ContainerFullInv<T> {
    public ContainerBaseDoubleElectricMachine(EntityPlayer entityPlayer, T base1, int height, int dischargeX, int dischargeY, boolean register) {
        super(entityPlayer, base1, height);
        if (register)
            this.addSlotToContainer(new SlotInvSlot(base1.dischargeSlot, 0, dischargeX, dischargeY));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("guiChargeLevel");
        ret.add("tier");
        return ret;
    }
}
