package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.api.module.IModulPanel;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.utils.ModUtils;
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

public class ModuleTypePanel extends Item implements IModulPanel {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ModuleTypePanel() {
        this.itemNames = new ArrayList<>();
        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module6");
    }

    public static EnumSolarPanels getSolarType(int meta) {
        return IUItem.map.get(meta);
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("module61");
        this.itemNames.add("module62");
        this.itemNames.add("module63");
        this.itemNames.add("module64");
        this.itemNames.add("module65");
        this.itemNames.add("module66");
        this.itemNames.add("module67");
        this.itemNames.add("module68");
        this.itemNames.add("module69");
        this.itemNames.add("module70");
        this.itemNames.add("module91");
        this.itemNames.add("module92");
        this.itemNames.add("module93");
        this.itemNames.add("module94");
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {

        EnumSolarPanels solar = getSolarType(itemStack.getItemDamage());

        info.add(StatCollector.translateToLocal("supsolpans.iu.GenerationDay.tooltip") + " " + ModUtils.getString(solar.genday) + " EU/t ");
        info.add(StatCollector.translateToLocal("supsolpans.iu.GenerationNight.tooltip") + " " + ModUtils.getString(solar.gennight)
                + " EU/t ");

        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Output") + " " + ModUtils.getString(solar.producing) + " EU/t ");
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " " + ModUtils.getString(solar.maxstorage) + " EU ");
        info.add(StatCollector.translateToLocal("iu.tier") + ModUtils.getString(solar.tier));
        info.add(StatCollector.translateToLocal("iu.modules1"));
        info.add(StatCollector.translateToLocal("iu.modules2"));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));


    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);

            itemList.add(stack);
        }
    }
}
