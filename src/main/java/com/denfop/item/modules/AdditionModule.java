package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class AdditionModule extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public AdditionModule() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module7");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("module71");
        this.itemNames.add("module72");
        this.itemNames.add("module73");
        this.itemNames.add("module74");
        this.itemNames.add("module75");
        this.itemNames.add("module76");
        this.itemNames.add("module77");
        this.itemNames.add("module78");
        this.itemNames.add("module79");
        this.itemNames.add("module80");
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
                info.add(StatCollector.translateToLocal("iu.modules"));
                info.add(StatCollector.translateToLocal("personality"));
                info.add(StatCollector.translateToLocal("personality1"));
                for (int i = 0; i < 9; i++) {
                    NBTTagCompound nbt = ModUtils.nbt(itemStack);
                    String name = "player_" + i;
                    if (!nbt.getString(name).isEmpty())
                        info.add(nbt.getString(name));
                }
                break;
            case 1:
                info.add(StatCollector.translateToLocal("iu.modules"));
                info.add(StatCollector.translateToLocal("transformator"));
                break;
            case 2:
                info.add(StatCollector.translateToLocal("iu.modules"));
                info.add(StatCollector.translateToLocal("transformator1"));
                break;
            case 3:
                info.add(StatCollector.translateToLocal("modulechange"));

                break;
            case 4:
                info.add(StatCollector.translateToLocal("modulerfinfo"));
                info.add(StatCollector.translateToLocal("modulerfinfo1"));
                break;
            case 5:
            case 8:
            case 7:
            case 6:
                info.add(StatCollector.translateToLocal("storagemodul"));
                info.add(StatCollector.translateToLocal("storagemodul1"));
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
