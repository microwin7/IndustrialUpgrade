package com.denfop.utils;

import com.denfop.item.energy.ItemGraviTool;
import ic2.core.item.tool.ItemToolWrench;
import net.minecraft.entity.player.EntityPlayer;

public class CheckHeldItem {
	public static boolean gettrue(EntityPlayer player) {
	if(player.getHeldItem() == null)
		return false;
	
	return player.getHeldItem().getItem() instanceof ItemGraviTool || player.getHeldItem().getItem() instanceof ItemToolWrench;
	
	}
	public static boolean gettrue1(EntityPlayer player) {
		if(player.getHeldItem() == null)
			return false;
		
		return player.getHeldItem().getItem() instanceof ItemGraviTool ||  player.getHeldItem().getItem() instanceof ItemToolWrench;
	}
}
