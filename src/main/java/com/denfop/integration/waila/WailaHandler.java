package com.denfop.integration.waila;

import com.denfop.api.ITemperature;
import com.denfop.tiles.base.TileEntityElectricBlock;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.Optional.Interface;
import cpw.mods.fml.common.Optional.Method;
import ic2.api.energy.tile.IEnergySink;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

@Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")
public class WailaHandler implements IWailaDataProvider {


    @Method(modid = "Waila")
    public static void callbackRegister(IWailaRegistrar register) {
        register.registerBodyProvider(new WailaHandler(), Block.class);
    }

    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        if (accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                accessor.getPosition().blockZ) instanceof IEnergySink) {
            IEnergySink tile = (IEnergySink) accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                    accessor.getPosition().blockZ);
            currenttip.add(StatCollector.translateToLocal("iu.tier") + ModUtils.getString(tile.getSinkTier())
                    + EnumChatFormatting.RESET);

        }
        if (accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                accessor.getPosition().blockZ) instanceof ITemperature) {
            ITemperature tile = (ITemperature) accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                    accessor.getPosition().blockZ);
            currenttip.add(StatCollector.translateToLocal("iu.temperature") + ModUtils.getString(tile.getTemperature()) + "/" + ModUtils.getString(tile.getMaxTemperature())
                    + EnumChatFormatting.RESET);
            if (tile.isFluidTemperature())
                currenttip.add(StatCollector.translateToLocal(tile.getFluid().getUnlocalizedName()) + ": " + ModUtils.getString(tile.getFluid().amount) + "/" + ModUtils.getString(12000)
                        + EnumChatFormatting.RESET);

        }

        if (accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                accessor.getPosition().blockZ) instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock tile = (TileEntityElectricBlock) accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                    accessor.getPosition().blockZ);
            double energy = tile.energy;
            currenttip.add(StatCollector.translateToLocal("supsolpans.SpecSP.Storage") + " "
                    + ModUtils.getString(energy) + "/" + ModUtils.getString(tile.maxStorage) + EnumChatFormatting.RESET);
            if (tile.movementcharge)
                currenttip.add(StatCollector.translateToLocal("gui.movementcharge") + ": "
                        + ModUtils.Boolean(true) + EnumChatFormatting.RESET);
            if (tile.movementchargeitem)
                currenttip.add(StatCollector.translateToLocal("gui.movementchargeitem") + ": "
                        + ModUtils.Boolean(true) + EnumChatFormatting.RESET);
            if (tile.movementchargeitemrf)
                currenttip.add(StatCollector.translateToLocal("gui.movementchargeitemrf") + ": "
                        + ModUtils.Boolean(true) + EnumChatFormatting.RESET);
            if (tile.movementchargerf)
                currenttip.add(StatCollector.translateToLocal("gui.movementchargerf") + ": "
                        + ModUtils.Boolean(true) + EnumChatFormatting.RESET);
            List<Boolean> Boolean = new ArrayList<>();
            Boolean.add(tile.movementcharge);
            Boolean.add(tile.movementchargeitem);
            Boolean.add(tile.movementchargeitemrf);
            Boolean.add(tile.movementchargerf);
            if (ModUtils.Boolean(Boolean))
                currenttip.add(StatCollector.translateToLocal("gui.movement"));
        }
        if (accessor.getWorld().getTileEntity(accessor.getPosition().blockX, accessor.getPosition().blockY,
                accessor.getPosition().blockZ) instanceof TileEntitySolarPanel) {
            TileEntitySolarPanel tile = (TileEntitySolarPanel) accessor.getWorld().getTileEntity(
                    accessor.getPosition().blockX, accessor.getPosition().blockY, accessor.getPosition().blockZ);
            double energy = tile.storage;
            int solartype = tile.solarType;

            double generating = tile.generating;
            double production = tile.production;
            double tier = tile.machineTire;


            currenttip.add(StatCollector.translateToLocal("gui.SuperSolarPanel.generating") + ": "
                    + ModUtils.getString(generating) + " EU/t" + EnumChatFormatting.RESET);
            currenttip.add(StatCollector.translateToLocal("gui.SuperSolarPanel.maxOutput") + ": "
                    + ModUtils.getString(production) + " EU/t" + EnumChatFormatting.RESET);
            currenttip.add(StatCollector.translateToLocal("iu.tier") + ModUtils.getString(tier)
                    + EnumChatFormatting.RESET);
            String ModulesString6 = I18n.format("iu.moduletype");
            String ModulesString61 = I18n.format("iu.moduletype1");
            String ModulesString62 = I18n.format("iu.moduletype2");
            String ModulesString63 = I18n.format("iu.moduletype3");
            String ModulesString64 = I18n.format("iu.moduletype4");
            String ModulesString65 = I18n.format("iu.moduletype5");
            String ModulesString66 = I18n.format("iu.moduletype6");
            String ModulesString67 = I18n.format("iu.moduletype7");
            if (solartype == 0)
                currenttip.add(ModulesString6 + EnumChatFormatting.RESET);
            if (solartype == 1)
                currenttip.add(ModulesString61 + EnumChatFormatting.RESET);
            if (solartype == 2)
                currenttip.add(ModulesString62 + EnumChatFormatting.RESET);
            if (solartype == 3)
                currenttip.add(ModulesString63 + EnumChatFormatting.RESET);
            if (solartype == 4)
                currenttip.add(ModulesString64 + EnumChatFormatting.RESET);
            if (solartype == 5)
                currenttip.add(ModulesString65 + EnumChatFormatting.RESET);
            if (solartype == 6)
                currenttip.add(ModulesString66 + EnumChatFormatting.RESET);
            if (solartype == 7)
                currenttip.add(ModulesString67 + EnumChatFormatting.RESET);
            currenttip.add(ModUtils.getString(energy) + " / " + ModUtils.getString(tile.maxStorage) + " EU"
                    + EnumChatFormatting.RESET);
        }
        return currenttip;
    }

    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
                                     IWailaConfigHandler config) {
        return currenttip;
    }

    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
                                     int y, int z) {
        return tag;
    }
}
