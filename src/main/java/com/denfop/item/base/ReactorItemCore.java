package com.denfop.item.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ICustomDamageItem;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ReactorItemCore extends Item implements ICustomDamageItem {
    private final int maxDmg;


    public ReactorItemCore(String name, int maxdmg) {
        super();
        this.maxDmg = maxdmg;
        setMaxStackSize(1);
        setMaxDamage(10000);
        setUnlocalizedName(name);
        setNoRepair();
        this.setCreativeTab(IUCore.tabssp3);
        this.setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
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

    public boolean isDamaged(ItemStack stack) {
        return (getDamage(stack) > 1);
    }

    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, List itemList) {
        itemList.add(new ItemStack(this, 1, 1));
    }

    public int getCustomDamage(ItemStack stack) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        return nbt.getInteger("advDmg");
    }

    public int getMaxCustomDamage(ItemStack stack) {
        return this.maxDmg;
    }

    public void setCustomDamage(ItemStack stack, int damage) {
        NBTTagCompound nbt = StackUtil.getOrCreateNbtData(stack);
        nbt.setInteger("advDmg", damage);
        int maxStackDamage = stack.getMaxDamage();
        if (maxStackDamage > 2)
            stack.setItemDamage(1 + (int) Util.map(damage, this.maxDmg, (maxStackDamage - 2)));
    }

    public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
        setCustomDamage(stack, getCustomDamage(stack) + damage);
        return true;
    }
}
