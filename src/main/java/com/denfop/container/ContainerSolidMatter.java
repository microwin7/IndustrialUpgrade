package com.denfop.container;

import com.denfop.tiles.base.TileMatterGenerator;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerSolidMatter<T extends TileMatterGenerator> extends ContainerFullInv<T> {
    public ContainerSolidMatter(EntityPlayer entityPlayer, T tileEntity1) {
        this(entityPlayer, tileEntity1, 166, 152, 8);
    }

    public ContainerSolidMatter(EntityPlayer entityPlayer, T tileEntity1, int height, int upgradeX, int upgradeY) {
        super(entityPlayer, tileEntity1, height);
        if (tileEntity1.outputSlot != null)
            addSlotToContainer(new SlotInvSlot(tileEntity1.outputSlot,
                    0, 69, 32));
        for (int i = 0; i < 4; i++)
            addSlotToContainer(new SlotInvSlot(tileEntity1.upgradeSlot,
                    i, upgradeX, upgradeY + i * 18));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("progress");
        ret.add("energy");
        return ret;
    }
}
