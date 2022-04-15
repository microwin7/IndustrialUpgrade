package com.denfop.container;

import com.denfop.tiles.base.TileEntityTransformer;
import ic2.core.ContainerFullInv;
import net.minecraft.entity.player.EntityPlayer;

import java.util.List;

public class ContainerTransformer extends ContainerFullInv<TileEntityTransformer> {
    public ContainerTransformer(EntityPlayer entityPlayer, TileEntityTransformer tileEntity1) {
        super(entityPlayer, tileEntity1, 196 + 18 + 5);
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("mode");
        ret.add("inputflow");
        ret.add("outputflow");
        return ret;
    }
}
