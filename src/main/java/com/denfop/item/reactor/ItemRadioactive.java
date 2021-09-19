package com.denfop.item.reactor;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2Potion;
import ic2.core.item.armor.ItemArmorHazmat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemRadioactive extends Item {
    protected final int radiationLength;

    protected final int amplifier;


    public ItemRadioactive(String name, int radiationLength1, int amplifier1) {
        super();

        this.radiationLength = radiationLength1;
        this.amplifier = amplifier1;
        setUnlocalizedName(name);
        this.setCreativeTab(IUCore.tabssp3);
        this.setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);

    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int slotIndex, boolean isCurrentItem) {
        if (this.radiationLength != 0)
            if (entity instanceof EntityLivingBase) {
                EntityLivingBase entityLiving = (EntityLivingBase) entity;
                if (!ItemArmorHazmat.hasCompleteHazmat(entityLiving))

                    IC2Potion.radiation.applyTo(entityLiving, this.radiationLength, this.amplifier);
            }
    }

    public String getUnlocalizedName() {
        return "iu." + super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }


    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.values()[0];
    }
}
