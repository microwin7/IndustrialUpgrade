

package com.denfop.container;

import ic2.core.ContainerFullInv;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public abstract class ContainerBaseGenerationChipMachine1<T extends TileEntityElectricMachine> extends ContainerFullInv<T> {
    public ContainerBaseGenerationChipMachine1(EntityPlayer entityPlayer, T base1, int height) {
        super(entityPlayer, base1, height);

    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("guiChargeLevel");
        ret.add("tier");
        return ret;
    }
}
