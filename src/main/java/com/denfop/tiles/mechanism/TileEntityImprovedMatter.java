package com.denfop.tiles.mechanism;

import com.denfop.tiles.base.TileEntityMultiMatter;
import net.minecraft.util.StatCollector;

public class TileEntityImprovedMatter extends TileEntityMultiMatter {

    public TileEntityImprovedMatter() {
        super(800000F, 10, 64000000);
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockMatter2.name");
    }

}
