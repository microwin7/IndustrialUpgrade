package com.denfop.container;

import com.denfop.tiles.base.TileEntityConverterSolidMatter;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerConverterSolidMatter extends ContainerFullInv<TileEntityConverterSolidMatter> {

    public ContainerConverterSolidMatter(EntityPlayer entityPlayer, TileEntityConverterSolidMatter tileEntity) {
        super(entityPlayer, tileEntity, 240 - 16);
        for (int i = 0; i < tileEntity.MatterSlot.size(); i++)
            addSlotToContainer(new SlotInvSlot(tileEntity.MatterSlot, i, 51 + i * 18, 17));
        addSlotToContainer(new SlotInvSlot(tileEntity.inputSlot, 0, 51, 51));
        addSlotToContainer(new SlotInvSlot(tileEntity.outputSlot, 0, 117, 51));


    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energy");
        ret.add("progress");
        ret.add("maxEnergy");
        ret.add("quantitysolid");
        ret.add("guiProgress");
        return ret;
    }

}
