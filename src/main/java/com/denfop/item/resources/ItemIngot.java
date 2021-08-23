
package com.denfop.item.resources;


import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.util.IIcon;
import java.util.List;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;

import net.minecraft.item.Item;

public class ItemIngot extends Item {
	public static  List<String> itemNames;
	private IIcon[] IIconsList;

	public ItemIngot() {
		itemNames = new ArrayList<>();

		this.setHasSubtypes(true);
		this.setCreativeTab(IUCore.tabssp3);
		this.setMaxStackSize(64);
		this.addItemsNames();
		GameRegistry.registerItem(this,"iuingot");
	}

	public String getUnlocalizedName(final ItemStack stack) {
		return itemNames.get(stack.getItemDamage());
	}

	public IIcon getIconFromDamage(final int par1) {
		return this.IIconsList[par1];
	}

	public void addItemsNames() {
		for(int i =0; i < IUItem.name_mineral.size(); i++) {
			itemNames.add(IUItem.name_mineral.get(i)+"_ingot");
		}

	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister IIconRegister) {
		this.IIconsList = new IIcon[itemNames.size()];
		for(int i = 0; i < itemNames.size();i++)
			this.IIconsList[i] =  IIconRegister.registerIcon(Constants.TEXTURES_MAIN +itemNames.get(i));

	}

	public void getSubItems( Item item,  CreativeTabs tabs,  List itemList) {
		for (int meta = 0; meta <= itemNames.size() - 1; ++meta) {
			 ItemStack stack = new ItemStack( this, 1, meta);
			itemList.add(stack);
		}
	}


}
