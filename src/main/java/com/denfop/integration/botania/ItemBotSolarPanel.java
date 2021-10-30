package com.denfop.integration.botania;

import com.denfop.Config;
import com.denfop.IUCore;
import com.denfop.api.IPanel;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ItemBotSolarPanel extends ItemBlock implements IPanel {
    private final List<String> itemNames;

    public ItemBotSolarPanel(final Block b) {
        super(b);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
        this.itemNames = new ArrayList<>();
        this.addItemsNames();
        this.setCreativeTab(IUCore.tabssp);
    }

    public int getMetadata(final int i) {
        return i;
    }

    public String getUnlocalizedName(final ItemStack itemstack) {
        return this.itemNames.get(itemstack.getItemDamage());
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);


            itemList.add(stack);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        if (Config.promt) {

            int meta = itemStack.getItemDamage();
            TileEntitySolarPanel tile = (TileEntitySolarPanel) blockBotSolarPanel.getBlockEntity(meta);
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                info.add(StatCollector.translateToLocal("press.lshift"));


            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                info.add(StatCollector.translateToLocal("supsolpans.iu.GenerationDay.tooltip") + " "
                        + ModUtils.getString(tile.genDay) + " EU/t ");
                info.add(StatCollector.translateToLocal("supsolpans.iu.GenerationNight.tooltip") + " "
                        + ModUtils.getString(tile.genNight) + " EU/t ");

                info.add(StatCollector.translateToLocal("ic2.item.tooltip.Output") + " "
                        + ModUtils.getString(tile.production) + " EU/t ");
                info.add(StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " "
                        + ModUtils.getString(tile.maxStorage) + " EU ");
                info.add(StatCollector.translateToLocal("iu.tier") + ModUtils.getString(tile.tier));
            }
        }
    }

    public void addItemsNames() {
        this.itemNames.add("blockManasteelSolarPanel");
        this.itemNames.add("blockElementumSolarPanel");
        this.itemNames.add("blockTerrasteelSolarPanel");
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(final ItemStack itemstack) {
        final int i = itemstack.getItemDamage();
        switch (i) {
            case 1: {
                return EnumRarity.rare;
            }
            case 2: {
                return EnumRarity.epic;
            }

            default: {
                return EnumRarity.uncommon;
            }
        }
    }
}
