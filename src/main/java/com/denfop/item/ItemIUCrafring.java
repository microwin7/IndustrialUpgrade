package com.denfop.item;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.ArrayList;
import java.util.List;

public class ItemIUCrafring extends Item {
    private final List<String> itemNames;
    private final IIcon[] IIconsList;

    public ItemIUCrafring() {
        this.itemNames = new ArrayList<>();
        this.IIconsList = new IIcon[11];
        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "ssp_crafting_items");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("itemIrradiantUranium");
        this.itemNames.add("itemIrradiantGlassPane");
        this.itemNames.add("itemUranIngot");
        this.itemNames.add("itemMTCore");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList[0] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "IrradiantUranium");
        this.IIconsList[1] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "IrradiantGlassPane");
        this.IIconsList[2] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "UranIngot");
        this.IIconsList[3] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "MTCore");
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);

            itemList.add(stack);
        }
    }


}
