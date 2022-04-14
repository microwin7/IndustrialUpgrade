package com.denfop.tiles.se;

import com.denfop.IUItem;
import com.denfop.tiles.base.TileSolarGeneratorEnergy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

public class TileSolarGenerator extends TileSolarGeneratorEnergy {

    public static final double cof = 1;

    public TileSolarGenerator() {

        super(cof, "blockSolarGeneratorEnergy.name");

    }

    @Override
    protected ItemStack getPickBlock(final EntityPlayer player, final RayTraceResult target) {
        return new ItemStack(IUItem.blockSE);
    }

}
