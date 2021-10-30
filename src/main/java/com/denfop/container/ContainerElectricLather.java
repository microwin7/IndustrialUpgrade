package com.denfop.container;

import com.denfop.tiles.mechanism.TileEntityElectricLather;
import ic2.core.ContainerFullInv;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;

import java.util.ArrayList;
import java.util.List;

public class ContainerElectricLather extends ContainerFullInv<TileEntityElectricLather> {
    public ContainerElectricLather(EntityPlayer player, TileEntityElectricLather base1) {
        super(player, base1, 166);
        this.addSlotToContainer(new SlotInvSlot(base1.toolSlot, 0, 10, 30));
        this.addSlotToContainer(new SlotInvSlot(base1.latheSlot, 0, 10, 12));
        for (int i = 0; i < base1.outputSlot.size(); i++)
            this.addSlotToContainer(new SlotInvSlot(base1.outputSlot, i, 10 + 18 * i, 57));
        this.addSlotToContainer(new SlotInvSlot(base1.inputslot, 0, 151, 57));

    }

    public List<String> getNetworkedFields() {
        List<String> list = new ArrayList();
        list.add("toolSlot");
        list.add("latheSlot");
        list.add("outputSlot");
        list.add("energy");
        list.add("energy");
        return list;
    }
}
