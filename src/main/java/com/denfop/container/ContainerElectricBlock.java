package com.denfop.container;

import com.denfop.invslot.InvSlotArmor;
import com.denfop.tiles.base.TileEntityElectricBlock;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerElectricBlock extends ContainerFullInv<TileEntityElectricBlock> {

    public ContainerElectricBlock(EntityPlayer entityPlayer, TileEntityElectricBlock tileEntity) {
        super(entityPlayer, tileEntity, 196);
        for (int col = 0; col < 4; col++)
            addSlotToContainer(new InvSlotArmor(entityPlayer.inventory, col, 8 + col * 18, 84));

        addSlotToContainer(new SlotInvSlot(tileEntity.inputslotA, 0, 56, 17));
        addSlotToContainer(new SlotInvSlot(tileEntity.inputslotB, 0, 56, 53));
        addSlotToContainer(new SlotInvSlot(tileEntity.inputslotC, 0, 56 - 36, 17));
        addSlotToContainer(new SlotInvSlot(tileEntity.inputslotC, 1, 56 - 36, 17 + 18));
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energy2");
        ret.add("energy");
        ret.add("personality");
        ret.add("rfeu");
        ret.add("rf");
        ret.add("inputslotA");
        ret.add("inputslotB");
        ret.add("inputslotC");

        ret.add("output_plus");
        return ret;
    }

}
