package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.energy.tile.IEnergySink;
import ic2.core.block.TileEntityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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

        } else {
            info.add(StatCollector.translateToLocal("mode.panel"));

        }
    }

    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (player.worldObj.isRemote) {
            return false;
        }
        if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IEnergySink && world.getTileEntity(x, y, z) instanceof TileEntityInventory) {
            TileEntityInventory tile = (TileEntityInventory) world.getTileEntity(x, y, z);
            NBTTagCompound nbttagcompound = ModUtils.nbt(stack);
            nbttagcompound.setInteger("Xcoord", tile.xCoord);
            nbttagcompound.setInteger("Ycoord", tile.yCoord);
            nbttagcompound.setInteger("Zcoord", tile.zCoord);
            nbttagcompound.setInteger("tier", ((IEnergySink) tile).getSinkTier());
            nbttagcompound.setInteger("World1", tile.getWorldObj().provider.dimensionId);
            nbttagcompound.setString("World", tile.getWorldObj().provider.getDimensionName());
            nbttagcompound.setString("Name", tile.getInventoryName());
            return true;
        } else if (world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof IEnergySink) {
            TileEntity tile = world.getTileEntity(x, y, z);
            NBTTagCompound nbttagcompound = ModUtils.nbt(stack);
            nbttagcompound.setInteger("Xcoord", tile.xCoord);
            nbttagcompound.setInteger("Ycoord", tile.yCoord);
            nbttagcompound.setInteger("Zcoord", tile.zCoord);
            nbttagcompound.setInteger("tier", ((IEnergySink) tile).getSinkTier());
            nbttagcompound.setInteger("World1", tile.getWorldObj().provider.dimensionId);
            nbttagcompound.setString("World", tile.getWorldObj().provider.getDimensionName());
            nbttagcompound.setString("Name", tile.blockType.getLocalizedName());
            return true;
        }
        return false;
    }
}
