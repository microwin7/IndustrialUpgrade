package com.denfop.potion;

import com.denfop.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

public class PotionEmitting extends Potion {

    private static final ResourceLocation icons = new ResourceLocation(Constants.MOD_ID, "textures/gui/emitting.png");
    public static PotionEmitting radiation;
    private final List<ItemStack> curativeItems;

    public PotionEmitting(String name, boolean badEffect, int liquidColor, ItemStack... curativeItems) {
        super(badEffect, liquidColor);
        this.curativeItems = Arrays.asList(curativeItems);
        this.setPotionName("iu.potion.emitting");
        this.setIconIndex(6, 0);
        this.setEffectiveness(0.25D);
        ForgeRegistries.POTIONS.register(this.setRegistryName(Constants.MOD_ID, name));
    }

    public static void init() {

    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(icons);
        return super.getStatusIconIndex();
    }

    public void performEffect(EntityLivingBase entity, int amplifier) {


    }

    public boolean isReady(int duration, int amplifier) {

        return false;

    }

    public void applyTo(EntityLivingBase entity, int duration, int amplifier) {

    }

}
