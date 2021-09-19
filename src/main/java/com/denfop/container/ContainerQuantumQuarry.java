package com.denfop.container;

import com.denfop.tiles.mechanism.TileEntityBaseQuantumQuarry;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerQuantumQuarry<T extends TileEntityBaseQuantumQuarry> extends ContainerFullInv<T> {

    public ContainerQuantumQuarry(EntityPlayer entityPlayer, T tileEntity1) {
        this(entityPlayer, tileEntity1, 166);
        if (tileEntity1.outputSlot != null) {

            for (int j = 0; j < 6; ++j) {

                addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot,
                        j, 30 + 18 * j, 6));
            }
            for (int j = 0; j < 6; ++j) {

                addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot,
                        j + 6, 30 + 18 * j, 6 + 18));
            }
            for (int j = 0; j < 6; ++j) {

                addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot,
                        j + 12, 30 + 18 * j, 6 + 18 + 18));
            }
            for (int j = 0; j < 6; ++j) {

                addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot,
                        j + 18, 30 + 18 * j, 6 + 18 + 18 + 18));
            }
            addSlotToContainer(new SlotInvSlot(tileEntity1.inputslotB, 0, 8,
                    6 + 9));
            addSlotToContainer(new SlotInvSlot(tileEntity1.inputslot, 0, 8,
                    6 + 18 + 9));
            addSlotToContainer(new SlotInvSlot(tileEntity1.inputslotA, 0, 8,
                    6 + 18 + 9 + 18));
        }
    }

    public ContainerQuantumQuarry(EntityPlayer entityPlayer, T tileEntity1, int height) {
        super(entityPlayer, tileEntity1, height);
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energyconsume");
        ret.add("energy");
        ret.add("progress");
        ret.add("getblock");
        ret.add("maxEnergy");
        ret.add("inputslot");
        ret.add("outputSlot");
        return ret;
    }


}
