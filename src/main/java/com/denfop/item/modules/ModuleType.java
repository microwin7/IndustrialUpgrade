package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.api.module.IModuleType;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class ModuleType extends Item implements IModuleType {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ModuleType() {
        this.itemNames = new ArrayList<>();
        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module5");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("module51");
        this.itemNames.add("module52");
        this.itemNames.add("module53");
        this.itemNames.add("module54");
        this.itemNames.add("module55");
        this.itemNames.add("module56");
        this.itemNames.add("module57");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        int meta = itemStack.getItemDamage();
        switch (meta) {
            case 0:
                info.add(StatCollector.translateToLocal("aerpanel"));
                info.add(StatCollector.translateToLocal("aerpanel1"));
                break;
            case 1:
                info.add(StatCollector.translateToLocal("earthpanel"));
                info.add(StatCollector.translateToLocal("earthpanel1"));
                break;
            case 2:
                info.add(StatCollector.translateToLocal("netherpanel"));
                break;
            case 3:
                info.add(StatCollector.translateToLocal("endpanel"));
                break;
            case 4:
                info.add(StatCollector.translateToLocal("nightpanel"));
                break;
            case 5:
                info.add(StatCollector.translateToLocal("sunpanel"));
                break;
            case 6:
                info.add(StatCollector.translateToLocal("rainpanel"));
                info.add(StatCollector.translateToLocal("rainpanel1"));
                break;
        }
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

}
