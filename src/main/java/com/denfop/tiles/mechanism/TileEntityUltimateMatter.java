package com.denfop.tiles.mechanism;

import com.denfop.tiles.base.TileEntityMultiMatter;
import net.minecraft.util.StatCollector;

public class TileEntityUltimateMatter extends TileEntityMultiMatter {

    public TileEntityUltimateMatter() {
        super(700000F, 12, 256000000);
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockMatter3.name");
    }

}
