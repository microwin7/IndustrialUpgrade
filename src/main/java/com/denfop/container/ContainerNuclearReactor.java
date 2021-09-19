//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop.container;

import com.denfop.tiles.reactors.TileEntityAdvNuclearReactorElectric;
import ic2.core.ContainerBase;
import ic2.core.slot.SlotInvSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import java.util.List;

public class ContainerNuclearReactor extends ContainerBase<TileEntityAdvNuclearReactorElectric> {
    public final short size;

    public ContainerNuclearReactor(EntityPlayer entityPlayer, TileEntityAdvNuclearReactorElectric tileEntity1) {
        super(tileEntity1);
        this.size = tileEntity1.getReactorSize();
        int startX = 26 - 18;
        int startY = 25;

        int col;
        int col1;
        for (col = 0; col < tileEntity1.reactorSlot.size(); col++) {
            col1 = col % this.size;
            int y = col / this.size;
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.reactorSlot, col, startX + 18 * col1, startY + 18 * y));

        }

        for (col = 0; col < 3; col++) {
            for (col1 = 0; col1 < 9; col1++) {
                this.addSlotToContainer(new Slot(entityPlayer.inventory, col1 + col * 9 + 9, 26 + col1 * 18, 161 + col * 18));
            }
        }

        for (col = 0; col < 9; col++) {
            this.addSlotToContainer(new Slot(entityPlayer.inventory, col, 26 + col * 18, 219));
        }

        if (tileEntity1.isFluidCooled()) {
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.coolantinputSlot, 0, 8, 25));
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.hotcoolinputSlot, 0, 188, 25));
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.coolantoutputSlot, 0, 8, 115));
            this.addSlotToContainer(new SlotInvSlot(tileEntity1.hotcoolantoutputSlot, 0, 188, 115));
        }
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("heat");
        ret.add("maxHeat");
        ret.add("EmitHeat");
        ret.add("inputTank");
        ret.add("outputTank");
        ret.add("fluidcoolreactor");
        return ret;
    }
}
