package com.denfop.item.armour;


import cofh.api.energy.IEnergyContainerItem;
import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.proxy.CommonProxy;
import com.denfop.utils.Helpers;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;

import java.util.List;

public class ItemLappack extends ItemArmor implements IElectricItem, IMetalArmor, ISpecialArmor {
    private final int maxCharge;

    private final int transferLimit;

    private final int tier;


    private final String name;

    public ItemLappack(String name, ItemArmor.ArmorMaterial armorMaterial, int armortype, int MaxCharge, int Tier, int TransferLimit) {
        super(armorMaterial, IUCore.proxy.addArmor(name), armortype);
        this.maxCharge = MaxCharge;
        this.transferLimit = TransferLimit;
        setCreativeTab(IUCore.tabssp2);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
        this.name = name;
        this.tier = Tier;
        setMaxDamage(27);
    }

    public static int readToolMode(ItemStack itemstack) {
        NBTTagCompound nbttagcompound = ModUtils.nbt(itemstack);
        int toolMode = nbttagcompound.getInteger("toolMode");
        if (toolMode < 0 || toolMode > 1)
            toolMode = 0;
        return toolMode;
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        double absorptionRatio = getBaseAbsorptionRatio() * 0;
        int energyPerDamage = getEnergyPerDamage();
        int damageLimit = (int) ((energyPerDamage > 0) ? (25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage) : 0.0D);
        return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
    }

    public int getEnergyPerDamage() {
        return 10000;
    }

    private double getBaseAbsorptionRatio() {
        return 0.0D;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Constants.TEXTURES + ":" + name);
    }

    @SideOnly(Side.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Constants.TEXTURES + ":" + "textures/armor/" + name + ".png";
    }

    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return (int) Math.round(20.0D * getBaseAbsorptionRatio() * 0);
    }

    public boolean isRepairable() {
        return false;
    }

    public int getItemEnchantability() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs var2, List var3) {
        ItemStack var4 = new ItemStack(this, 1);
        ElectricItem.manager.charge(var4, 2.147483647E9D, 2147483647, true, false);
        var3.add(var4);
        var3.add(new ItemStack(this, 1, getMaxDamage()));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1) {
        return EnumRarity.uncommon;
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    public int getTier(ItemStack itemStack) {
        return this.tier;
    }

    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
    }

    public void saveToolMode(ItemStack itemstack, Integer toolMode) {
        NBTTagCompound nbttagcompound = ModUtils.nbt(itemstack);
        nbttagcompound.setInteger("toolMode", toolMode);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (IC2.keyboard.isModeSwitchKeyDown(player)) {
            int toolMode = readToolMode(itemStack);
            toolMode++;
            if (toolMode > 1)
                toolMode = 0;
            saveToolMode(itemStack, toolMode);
            if (toolMode == 0)
                CommonProxy.sendPlayerMessage(player, EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + " " + EnumChatFormatting.RED + Helpers.formatMessage("iu.message.text.disabled"));
            if (toolMode == 1)
                CommonProxy.sendPlayerMessage(player, EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + " " + EnumChatFormatting.GREEN + Helpers.formatMessage("iu.message.text.enabled"));
        }
        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        int toolMode = readToolMode(par1ItemStack);
        if (toolMode == 0)
            par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + ": " + EnumChatFormatting.RED + Helpers.formatMessage("iu.message.text.disabled"));
        if (toolMode == 1)
            par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + ": " + EnumChatFormatting.GREEN + Helpers.formatMessage("iu.message.text.enabled"));
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData(itemStack);

        byte toggleTimer = nbtData.getByte("toggleTimer");
        if (IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
            toggleTimer = 10;
            int toolMode = readToolMode(itemStack);
            toolMode++;
            if (toolMode > 1)
                toolMode = 0;
            saveToolMode(itemStack, toolMode);
            if (toolMode == 0)
                CommonProxy.sendPlayerMessage(player, EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + " " + EnumChatFormatting.RED + Helpers.formatMessage("iu.message.text.disabled"));
            if (toolMode == 1)
                CommonProxy.sendPlayerMessage(player, EnumChatFormatting.GOLD + Helpers.formatMessage("iu.message.text.powerSupply") + " " + EnumChatFormatting.GREEN + Helpers.formatMessage("iu.message.text.enabled"));
        }
        if (IC2.platform.isSimulating() && toggleTimer > 0) {
            toggleTimer = (byte) (toggleTimer - 1);
            nbtData.setByte("toggleTimer", toggleTimer);
        }
        int toolMode = readToolMode(itemStack);
        boolean ret = false;
        if (toolMode == 1) {

            for (int i = 0; i < player.inventory.armorInventory.length; i++) {

                if (player.inventory.armorInventory[i] != null && player.inventory.armorInventory[i].getItem() instanceof IElectricItem) {
                    if (ElectricItem.manager.getCharge(itemStack) > 0) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.armorInventory[i], ElectricItem.manager.getCharge(itemStack),
                                2147483647, true, false);

                        if (sentPacket > 0.0D) {
                            ElectricItem.manager.discharge(itemStack, sentPacket, Integer.MAX_VALUE, true, false, false);
                            ret = true;

                        }
                    }
                }
                IEnergyContainerItem item;
                if (player.inventory.armorInventory[i] != null
                        && player.inventory.armorInventory[i].getItem() instanceof IEnergyContainerItem) {
                    if (ElectricItem.manager.getCharge(itemStack) > 0) {
                        item = (IEnergyContainerItem) player.inventory.armorInventory[i].getItem();

                        int amountRfCanBeReceivedIncludesLimit = item.receiveEnergy(player.inventory.armorInventory[i], Integer.MAX_VALUE, true);
                        double realSentEnergyRF = Math.min(amountRfCanBeReceivedIncludesLimit, ElectricItem.manager.getCharge(itemStack) * Config.coefficientrf);
                        item.receiveEnergy(player.inventory.armorInventory[i], (int) realSentEnergyRF, false);
                        ElectricItem.manager.discharge(itemStack, realSentEnergyRF / (double) Config.coefficientrf, Integer.MAX_VALUE, true, false, false);
                    }
                }
            }
            for (int j = 0; j < player.inventory.mainInventory.length; j++) {

                if (player.inventory.mainInventory[j] != null
                        && player.inventory.mainInventory[j].getItem() instanceof ic2.api.item.IElectricItem) {
                    if (ElectricItem.manager.getCharge(itemStack) > 0) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.mainInventory[j], ElectricItem.manager.getCharge(itemStack),
                                2147483647, true, false);

                        if (sentPacket > 0.0D) {
                            ElectricItem.manager.discharge(itemStack, sentPacket, Integer.MAX_VALUE, true, false, false);
                            ret = true;

                        }
                    }

                }
                IEnergyContainerItem item;
                if (player.inventory.mainInventory[j] != null
                        && player.inventory.mainInventory[j].getItem() instanceof IEnergyContainerItem) {
                    if (ElectricItem.manager.getCharge(itemStack) > 0) {
                        item = (IEnergyContainerItem) player.inventory.mainInventory[j].getItem();

                        int amountRfCanBeReceivedIncludesLimit = item.receiveEnergy(player.inventory.mainInventory[j], Integer.MAX_VALUE, true);
                        double realSentEnergyRF = Math.min(amountRfCanBeReceivedIncludesLimit, ElectricItem.manager.getCharge(itemStack) * Config.coefficientrf);
                        item.receiveEnergy(player.inventory.mainInventory[j], (int) realSentEnergyRF, false);
                        ElectricItem.manager.discharge(itemStack, realSentEnergyRF / (double) Config.coefficientrf, Integer.MAX_VALUE, true, false, false);
                    }
                }
            }
            if (ret)
                player.inventoryContainer.detectAndSendChanges();
        }
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {

    }


    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }
}
