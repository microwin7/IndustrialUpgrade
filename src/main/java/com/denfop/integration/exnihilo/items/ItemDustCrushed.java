package com.denfop.integration.exnihilo.items;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
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

public class ItemDustCrushed extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ItemDustCrushed() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "dustcrushed");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        for (int i = 0; i < IUItem.name_mineral1.size(); i++) {
            this.itemNames.add(IUItem.name_mineral1.get(i) + "_dustcrushed");
        }

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            if (meta != 6 && meta != 7 && meta != 11) {
                final ItemStack stack = new ItemStack(this, 1, meta);
                itemList.add(stack);
            }
        }
    }

}
