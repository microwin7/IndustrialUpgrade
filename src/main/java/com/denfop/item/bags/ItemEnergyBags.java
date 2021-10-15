
package com.denfop.item.bags;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.ICustomDamageItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.item.IHandHeldInventory;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

public class ItemEnergyBags extends Item implements IHandHeldInventory, IElectricItem, ICustomDamageItem {
    private final String internalName;
    private final IIcon[] IIconsList = new IIcon[1];
    private final int slots;
    private final int maxstorage;
    private final int getTransferLimit;
    private final ThreadLocal<Boolean> allowDamaging;
    public ItemEnergyBags(String internalName, int slots, int maxstorage, int getTransferLimit) {
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(1);
        setMaxDamage(27);
        this.allowDamaging = new ThreadLocal<>();
        this.internalName = internalName;
        this.slots = slots;
        this.getTransferLimit = getTransferLimit;
        this.maxstorage = maxstorage;
        GameRegistry.registerItem(this, internalName);
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return internalName;
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[0];
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList[0] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + internalName);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {
        if (ElectricItem.manager.canUse(itemStack, 350)) {
            ElectricItem.manager.use(itemStack, 350, entityPlayer);
            if (IC2.platform.isSimulating()) {
                IC2.platform.launchGui(entityPlayer, this.getInventory(entityPlayer, itemStack));
            }
        }
        return itemStack;
    }


    public IHasGui getInventory(EntityPlayer entityPlayer, ItemStack itemStack) {
        return new HandHeldBags(entityPlayer, itemStack, slots);
    }

    public void getSubItems(Item item, CreativeTabs tabs, List items) {
        ItemStack charged = new ItemStack(this, 1);
        ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
        items.add(charged);
        items.add(new ItemStack(this, 1, getMaxDamage()));
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return (itemStack == null) ? null : itemStack.getItem();
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return (itemStack == null) ? null : itemStack.getItem();
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return maxstorage;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 2;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return getTransferLimit;
    }

    public int getCustomDamage(ItemStack stack) {
        return stack.getItemDamage();
    }

    public int getMaxCustomDamage(ItemStack stack) {
        return stack.getMaxDamage();
    }

    public void setCustomDamage(ItemStack stack, int damage) {
        this.allowDamaging.set(Boolean.TRUE);
        stack.setItemDamage(damage);
        this.allowDamaging.set(Boolean.FALSE);
    }

    public boolean applyCustomDamage(ItemStack stack, int damage, EntityLivingBase src) {
        if (src != null) {
            stack.damageItem(damage, src);
            return true;
        }
        return stack.attemptDamageItem(damage, IC2.random);
    }
}
