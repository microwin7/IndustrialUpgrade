
package com.denfop.block.adminpanel;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.block.Block;
import java.util.List;

import com.denfop.IUCore;

import net.minecraft.item.ItemBlock;

public class ItemAdminSolarPanel extends ItemBlock {
	private final List<String> itemNames;

	public ItemAdminSolarPanel(final Block b) {
		super(b);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.itemNames = new ArrayList<>();
		this.addItemsNames();
		this.setCreativeTab(IUCore.tabssp);
	}

	public int getMetadata(final int i) {
		return i;
	}

	public String getUnlocalizedName(final ItemStack itemstack) {
		return this.itemNames.get(itemstack.getItemDamage());
	}

	public void addItemsNames() {
		this.itemNames.add("blockAdministatorSolarPanel");
	}

	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(final ItemStack itemstack) {

		return EnumRarity.epic;
	}
}
