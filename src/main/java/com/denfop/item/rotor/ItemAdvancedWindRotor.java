package com.denfop.item.rotor;

import com.denfop.IUCore;
import com.denfop.item.base.ReactorItemCore;
import ic2.api.item.IKineticRotor;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemAdvancedWindRotor extends ReactorItemCore implements IKineticRotor {
    private final int maxWindStrength;

    private final int minWindStrength;

    private final int radius;

    private final float efficiency;

    private final ResourceLocation renderTexture;

    private final boolean water;

    public ItemAdvancedWindRotor(String internalName, int Radius, int durability, float efficiency, int minWindStrength,
                                 int maxWindStrength, ResourceLocation RenderTexture) {
        super(internalName, durability);
        setMaxStackSize(1);
        setMaxDamage(durability);
        this.radius = Radius;
        this.efficiency = efficiency;
        this.renderTexture = RenderTexture;
        this.minWindStrength = minWindStrength;
        this.maxWindStrength = maxWindStrength;
        this.water = false;
        this.setCreativeTab(IUCore.tabssp3);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        info.add(StatCollector.translateToLocalFormatted("ic2.itemrotor.wind.info",
                this.minWindStrength, this.maxWindStrength));
        IKineticRotor.GearboxType type = null;
        if ((Minecraft.getMinecraft()).currentScreen != null && (Minecraft
                .getMinecraft()).currentScreen instanceof ic2.core.block.kineticgenerator.gui.GuiWaterKineticGenerator) {
            type = IKineticRotor.GearboxType.WATER;
        } else if ((Minecraft.getMinecraft()).currentScreen != null && (Minecraft
                .getMinecraft()).currentScreen instanceof ic2.core.block.kineticgenerator.gui.GuiWindKineticGenerator) {
            type = IKineticRotor.GearboxType.WIND;
        }
        if (type != null)
            info.add(StatCollector.translateToLocal("ic2.itemrotor.fitsin." + isAcceptedType(itemStack, type)));


        int windStrength = 40;
        int windStrength1 = 60;
        double KU = windStrength * this.getEfficiency(itemStack) * 10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/wind");
        int eu = (int) (KU * 0.25D * (double) ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic"));
        double KU1 = windStrength1 * this.getEfficiency(itemStack) * 10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/kineticgenerator/wind");
        int eu1 = (int) (KU1 * 0.25D * (double) ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic"));

        info.add(StatCollector.translateToLocal("iu.windgenerator") + windStrength + " " + StatCollector.translateToLocal("iu.windgenerator1") + eu);
        info.add(StatCollector.translateToLocal("iu.windgenerator") + windStrength1 + " " + StatCollector.translateToLocal("iu.windgenerator1") + eu1);

    }

    public int getDiameter(ItemStack stack) {
        return this.radius;
    }

    public ResourceLocation getRotorRenderTexture(ItemStack stack) {
        return this.renderTexture;
    }

    public float getEfficiency(ItemStack stack) {
        return this.efficiency;
    }

    public int getMinWindStrength(ItemStack stack) {
        return this.minWindStrength;
    }

    public int getMaxWindStrength(ItemStack stack) {
        return this.maxWindStrength;
    }

    public boolean isAcceptedType(ItemStack stack, IKineticRotor.GearboxType type) {
        return (type == IKineticRotor.GearboxType.WIND || this.water);
    }
}
