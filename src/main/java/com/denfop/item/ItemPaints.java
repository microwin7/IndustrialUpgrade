
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

public class ItemPaints extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ItemPaints() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this,"paints");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("emptybox");
        this.itemNames.add("bluepaint");
        this.itemNames.add("yellowpaint");
        this.itemNames.add("greenpaint");
        this.itemNames.add("redpaint");
        this.itemNames.add("orangepaint");
        this.itemNames.add("darkbluepaint");
        this.itemNames.add("purplepaint");
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
