package com.denfop.container;

import com.denfop.tiles.base.TileEntityKineticGenerator;
import ic2.core.ContainerFullInv;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerKineticGenerator extends ContainerFullInv<TileEntityKineticGenerator> {
    public ContainerKineticGenerator(EntityPlayer entityPlayer, TileEntityKineticGenerator tileEntity1) {
        super(entityPlayer, tileEntity1, 166);
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("EUstorage");
        ret.add("guiproduction");
        return ret;
    }
}
