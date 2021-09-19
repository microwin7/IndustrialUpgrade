package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemWirelessModule extends Item {

    public ItemWirelessModule() {
        setCreativeTab(IUCore.tabssp);
        this.setUnlocalizedName("WirelessModule");
        this.setTextureName(Constants.TEXTURES_MAIN + "wirelessmodule");
        this.setMaxStackSize(64);
        this.setCreativeTab(IUCore.tabssp1);
        GameRegistry.registerItem(this, "WirelessModule1");
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(itemStack);
        info.add(StatCollector.translateToLocal("iu.modules"));
        info.add(StatCollector.translateToLocal("wirelles"));
        info.add(StatCollector.translateToLocal("iu.Name") + ": " + nbttagcompound.getString("Name"));
        info.add(StatCollector.translateToLocal("iu.World") + ": " + nbttagcompound.getString("World"));

        info.add(StatCollector.translateToLocal("iu.tier") + nbttagcompound.getInteger("tier"));
        info.add(StatCollector.translateToLocal("iu.Xcoord") + ": " + nbttagcompound.getInteger("Xcoord"));
        info.add(StatCollector.translateToLocal("iu.Ycoord") + ": " + nbttagcompound.getInteger("Ycoord"));
        info.add(StatCollector.translateToLocal("iu.Zcoord") + ": " + nbttagcompound.getInteger("Zcoord"));
        if (nbttagcompound.getBoolean("change")) {
            info.add(StatCollector.translateToLocal("mode.storage"));

        }
        if (!nbttagcompound.getBoolean("change")) {
            info.add(StatCollector.translateToLocal("mode.panel"));

        }
    }

}
