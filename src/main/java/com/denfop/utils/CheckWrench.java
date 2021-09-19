package com.denfop.utils;

import com.denfop.IUItem;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.entity.player.EntityPlayer;

public class CheckWrench {
    public static boolean getwrench(EntityPlayer player) {
        if (player.getHeldItem() == null)
            return false;

        return player.getHeldItem().getItem() instanceof ItemToolWrench || player.getHeldItem().getItem().equals(IUItem.GraviTool);
    }
}
