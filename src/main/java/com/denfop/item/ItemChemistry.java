package com.denfop.item;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ICustomDamageItem;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemChemistry extends Item implements ICustomDamageItem {

    public ItemChemistry(String name) {
        this.setCreativeTab(IUCore.tabssp3);
        setUnlocalizedName(name);
        setMaxDamage(1000);
        setMaxStackSize(1);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }

    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.values()[0];
    }

    public boolean isDamaged(ItemStack stack) {
        return (getDamage(stack) > 1);
    }

    public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
        setCustomDamage(stack, getCustomDamage(stack) + damage);
        return true;
    }

    public int getCustomDamage(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        return nbt.getInteger("advDmg");
    }

    public int getMaxCustomDamage(ItemStack stack) {
        return 250;
    }

    public void setCustomDamage(ItemStack stack, int damage) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        nbt.setInteger("advDmg", damage);
        int maxStackDamage = stack.getMaxDamage();
        if (maxStackDamage > 2)
            stack.setItemDamage(1 + (int) Util.map(damage, 250, (maxStackDamage - 2)));
    }
}
