
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

public class ItemCrushed extends Item {
	private final List<String> itemNames;
	private IIcon[] IIconsList;

	public ItemCrushed() {
		this.itemNames = new ArrayList<>();
		
		this.setHasSubtypes(true);
		this.setCreativeTab(IUCore.tabssp3);
		this.setMaxStackSize(64);
		this.addItemsNames();
		GameRegistry.registerItem(this,"IUcrushed");
	}

    public String getUnlocalizedName(final ItemStack stack) {
		return this.itemNames.get(stack.getItemDamage());
	}

	public IIcon getIconFromDamage(final int par1) {
		return this.IIconsList[par1];
	}

	public void addItemsNames() {
		for(int i =0; i < IUItem.name_mineral.size(); i++) {
			this.itemNames.add(IUItem.name_mineral.get(i)+"_crushed");
		}
		
	}

	@SideOnly(Side.CLIENT)
	public void registerIcons(final IIconRegister IIconRegister) {
		this.IIconsList = new IIcon[itemNames.size()];
		for(int i = 0; i < itemNames.size();i++)
			if(i != 4&& i != 5&& i != 13)
			this.IIconsList[i] =  IIconRegister.registerIcon(Constants.TEXTURES_MAIN +itemNames.get(i));
	
	}

	public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
		for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
			if(meta != 4&& meta != 5&& meta != 13) {
			final ItemStack stack = new ItemStack(this, 1, meta);
			itemList.add(stack);
			}
		}
	}

}
