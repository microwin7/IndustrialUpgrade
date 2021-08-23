
package com.denfop.item;

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

import net.minecraft.item.Item;

public class ItemCore extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ItemCore() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this,"ItemCore");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("advcore");
        this.itemNames.add("hybcore");
        this.itemNames.add("ultcore");
        this.itemNames.add("quacore");
        this.itemNames.add("specore");
        this.itemNames.add("procore");
        this.itemNames.add("sincore");
        this.itemNames.add("admcore");
        this.itemNames.add("phocore");
        this.itemNames.add("neucore");
        this.itemNames.add("barcore");
        this.itemNames.add("adrcore");
        this.itemNames.add("gracore");
        this.itemNames.add("kvrcore");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for(int i = 0; i < itemNames.size();i++)
            this.IIconsList[i] =  IIconRegister.registerIcon(Constants.TEXTURES_MAIN +itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

}
