package com.denfop.container;

import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.machine.container.ContainerElectricMachine;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerMultiMachine<T extends TileEntityMultiMachine> extends ContainerElectricMachine<T> {

    public ContainerMultiMachine(EntityPlayer entityPlayer, T tileEntity1, int sizeWorkingSlot) {
        super(entityPlayer, tileEntity1, 166, 8, 63);
        for (int i = 0; i < sizeWorkingSlot; i++) {
            int xDisplayPosition1 = 80 + (32 - sizeWorkingSlot) * i - sizeWorkingSlot * 10;
            addSlotToContainer(new SlotInvSlot((InvSlot) tileEntity1.inputSlots, i,
                    xDisplayPosition1, 16));
            addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlots, i,
                    xDisplayPosition1, 60));
        }
        for (int i = 0; i < 4; i++)
            addSlotToContainer(new SlotInvSlot(tileEntity1.upgradeSlot, i, 152, 8 + i * 18));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("guiProgress");
        ret.add("expstorage");
        ret.add("expmaxstorage");
        ret.add("energy2");
        ret.add("maxEnergy2");
        ret.add("energy");

        return ret;
    }

}
