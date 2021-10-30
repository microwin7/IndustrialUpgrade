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
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class QuarryModule extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;


    public QuarryModule() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module9");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("module81");
        this.itemNames.add("module82");
        this.itemNames.add("module83");
        this.itemNames.add("module84");
        this.itemNames.add("module85");
        this.itemNames.add("module86");
        this.itemNames.add("module87");
        this.itemNames.add("module88");
        this.itemNames.add("module89");
        this.itemNames.add("kar1");
        this.itemNames.add("kar2");
        this.itemNames.add("kar3");

        this.itemNames.add("blackmodule");
        this.itemNames.add("whitemodule");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        this.IIconsList[0] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "per");
        this.IIconsList[1] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "ef");
        this.IIconsList[2] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "ef2");
        this.IIconsList[3] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "ef3");
        this.IIconsList[4] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "ef4");
        this.IIconsList[5] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "ef5");
        this.IIconsList[6] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "for1");
        this.IIconsList[7] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "for2");
        this.IIconsList[8] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "for3");

        this.IIconsList[9] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "kar1");
        this.IIconsList[10] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "kar2");
        this.IIconsList[11] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "kar3");

        this.IIconsList[12] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "blackmodule");
        this.IIconsList[13] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "whitemodule");
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        int meta = itemStack.getItemDamage();
        switch (meta) {
            case 0:
                info.add(StatCollector.translateToLocal("iu.quarry"));
                info.add(StatCollector.translateToLocal("iu.quarry1"));
                break;
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
                info.add(StatCollector.translateToLocal("iu.quarry"));
                info.add(StatCollector.translateToLocal("iu.quarry2"));
                break;
            case 12:
                info.add(StatCollector.translateToLocal("iu.blacklist"));

                for (int i = 0; i < 9; i++) {
                    String l = "number_" + i;

                    String ore = ModUtils.NBTGetString(itemStack, l);
                    if (ore.startsWith("ore") || ore.startsWith("gem") || ore.startsWith("ingot") || ore.startsWith("dust") || ore.startsWith("shard")) {

                        ItemStack stack = OreDictionary.getOres(ore).get(0);
                        info.add(stack.getDisplayName());

                    }


                }
                break;
            case 13:
                info.add(StatCollector.translateToLocal("iu.whitelist"));
                for (int i = 0; i < 9; i++) {
                    String l = "number_" + i;

                    String ore = ModUtils.NBTGetString(itemStack, l);
                    if (ore.startsWith("ore") || ore.startsWith("gem") || ore.startsWith("ingot") || ore.startsWith("dust") || ore.startsWith("shard")) {

                        ItemStack stack = OreDictionary.getOres(ore).get(0);
                        info.add(stack.getDisplayName());

                    }


                }

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
