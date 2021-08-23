
package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.EnumInfoUpgradeModules;
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

public class UpgradeModule extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;


    public UpgradeModule() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "iu_upgrademodule");
    }



    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("upgrademodule");
        this.itemNames.add("upgrademodule1");
        this.itemNames.add("upgrademodule2");
        this.itemNames.add("upgrademodule3");
        this.itemNames.add("upgrademodule4");
        this.itemNames.add("upgrademodule5");
        this.itemNames.add("upgrademodule6");
        this.itemNames.add("upgrademodule7");
        this.itemNames.add("upgrademodule8");
        this.itemNames.add("upgrademodule9");
        this.itemNames.add("upgrademodule10");
        this.itemNames.add("upgrademodule11");
        this.itemNames.add("upgrademodule12");
        this.itemNames.add("upgrademodule13");
        this.itemNames.add("upgrademodule14");
        this.itemNames.add("upgrademodule15");
        this.itemNames.add("upgrademodule16");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (String itemName : itemNames)
            this.IIconsList[itemNames.indexOf(itemName)] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemName);
    }

    public static EnumInfoUpgradeModules getType(int meta){
        switch (meta){
            case 0:
                return  EnumInfoUpgradeModules.GENDAY;
            case 1:
                return  EnumInfoUpgradeModules.GENNIGHT;
            case 2:
                return  EnumInfoUpgradeModules.PROTECTION;
            case 3:
                return  EnumInfoUpgradeModules.EFFICIENCY;
            case 4:
                return  EnumInfoUpgradeModules.BOWENERGY;
            case 5:
                return  EnumInfoUpgradeModules.SABERENERGY;
            case 6:
                return  EnumInfoUpgradeModules.DIG_DEPTH;
            case 7:
                return  EnumInfoUpgradeModules.FIREPROTECTION;
            case 8:
                return  EnumInfoUpgradeModules.WATER;
            case 9:
                return  EnumInfoUpgradeModules.SPEED;
            case 10:
                return  EnumInfoUpgradeModules.JUMP;
            case 11:
                return  EnumInfoUpgradeModules.BOWDAMAGE;
            case 12:
                return  EnumInfoUpgradeModules.SABERDAMAGE;
            case 13:
                return  EnumInfoUpgradeModules.AOE_DIG;
            case 14:
                return  EnumInfoUpgradeModules.FLYSPEED;
            case 15:
                return  EnumInfoUpgradeModules.STORAGE;
            case 16:
                return  EnumInfoUpgradeModules.ENERGY;
        }
return null;
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }


}
