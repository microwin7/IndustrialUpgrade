package com.denfop.container;

import com.denfop.tiles.base.TileEntityFisher;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerFisher<T extends TileEntityFisher> extends ContainerFullInv<T> {

    public ContainerFisher(EntityPlayer entityPlayer, T tileEntity1) {
        this(entityPlayer, tileEntity1, 166);

        addSlotToContainer(new SlotInvSlot(tileEntity1.inputslot, 0, 17,
                45));

        for (int i = 0; i < 9; i++) {
            int count = i / 3;
            addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot, i, 65 + (i - (3 * count)) * 18, 27 + count * 18));

        }
    }

    public ContainerFisher(EntityPlayer entityPlayer, T tileEntity1, int height) {
        super(entityPlayer, tileEntity1, height);
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();

        ret.add("energy");
        ret.add("progress");
        ret.add("maxEnergy");
        return ret;
    }
}
