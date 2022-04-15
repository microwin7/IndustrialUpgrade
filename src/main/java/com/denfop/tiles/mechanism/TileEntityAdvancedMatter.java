package com.denfop.tiles.mechanism;

import com.denfop.tiles.base.TileEntityMultiMatter;
import net.minecraft.util.StatCollector;

public class TileEntityAdvancedMatter extends TileEntityMultiMatter {

    public TileEntityAdvancedMatter() {
        super(900000F, 8, 8000000);
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockMatter1.name");
    }

}
