package com.denfop.item.armour;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.IC2DamageSource;
import ic2.core.IC2Potion;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.LinkedList;

public class ItemArmorAdvHazmat extends ItemArmor implements IMetalArmor, ISpecialArmor {
    private final String armorName;

    public ItemArmorAdvHazmat(String internalName, int type) {
        super(ItemArmor.ArmorMaterial.DIAMOND, IC2.platform.addArmor(internalName), type);
        this.setMaxDamage(64);
        if (this.armorType == 3) {
            MinecraftForge.EVENT_BUS.register(this);
        }
        setNoRepair();
        this.armorName = internalName;
        this.setMaxDamage(ArmorMaterial.DIAMOND.getDurability(this.armorType));
        this.setUnlocalizedName(internalName);
        setCreativeTab(IUCore.tabssp2);
        setMaxStackSize(1);

        GameRegistry.registerItem(this, internalName);

    }

    public static boolean hasCompleteHazmat(EntityLivingBase living) {
        for (int i = 1; i < 5; ++i) {
            ItemStack stack = living.getEquipmentInSlot(i);
            if (stack == null || !(stack.getItem() instanceof ItemArmorAdvHazmat)) {
                return false;
            }
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        String name = this.getUnlocalizedName();
        if (name != null && name.length() > 4) {
        }

        this.itemIcon = iconRegister.registerIcon(Constants.TEXTURES + ":" + name);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        int suffix = this.armorType == 2 ? 2 : 1;
        return Constants.TEXTURES + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
    }

    public String getUnlocalizedName() {
        return super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return this.getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(this.getUnlocalizedName(itemStack) + ".name");
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return false;
    }

    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (this.armorType == 0 && this.hazmatAbsorbs(source) && hasCompleteHazmat(player)) {
            if (source == DamageSource.inFire || source == DamageSource.lava) {
                player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 60, 1));
            }

            return new ArmorProperties(10, 1.0D, 2147483647);
        } else {
            return this.armorType == 3 && source == DamageSource.fall ? new ArmorProperties(10, damage < 8.0D ? 1.0D : 0.875D, (armor.getMaxDamage() - armor.getItemDamage() + 2) * 2 * 25) : new ArmorProperties(0, 0.05D, (armor.getMaxDamage() - armor.getItemDamage() + 2) / 2 * 25);
        }
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        if (!this.hazmatAbsorbs(source) || !hasCompleteHazmat(entity)) {
            int damageTotal = damage * 2;
            if (this.armorType == 3 && source == DamageSource.fall) {
                damageTotal = (damage + 1) / 2;
            }

            stack.damageItem(damageTotal, entity);
        }
    }

    @SubscribeEvent
    public void onEntityLivingFallEvent(LivingFallEvent event) {
        if (IC2.platform.isSimulating() && event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack armor = player.inventory.armorInventory[0];
            if (armor != null && armor.getItem() == this) {
                int fallDamage = (int) event.distance - 3;
                if (fallDamage >= 8) {
                    return;
                }

                int armorDamage = (fallDamage + 1) / 2;
                if (armorDamage <= armor.getMaxDamage() - armor.getItemDamage() && armorDamage >= 0) {
                    armor.damageItem(armorDamage, player);
                    event.setCanceled(true);
                }
            }
        }

    }

    public boolean isRepairable() {
        return true;
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        return 1;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        boolean ret = false;
        if (hasCompleteHazmat(player)) {
            for (PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
                int id = effect.getPotionID();
                if (id == IC2Potion.radiation.id)
                    IC2.platform.removePotion(player, id);


            }
        }
        if (this.armorType == 0) {
            if (player.isBurning() && hasCompleteHazmat(player)) {
                if (this.isInLava(player)) {
                    player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 0, true));
                }

                player.extinguish();
            }

            if (player.getAir() <= 100 && player.inventory.hasItemStack(Ic2Items.airCell)) {
                StackUtil.consumeInventoryItem(player, Ic2Items.airCell);
                player.inventory.addItemStackToInventory(new ItemStack(Ic2Items.cell.getItem()));
                player.setAir(player.getAir() + 150);
                ret = true;
            }
        }

        if (ret) {
            player.inventoryContainer.detectAndSendChanges();
        }

    }

    public boolean isInLava(EntityPlayer player) {
        double var2 = player.posY + 0.02D;
        int var4 = MathHelper.floor_double(player.posX);
        int var5 = MathHelper.floor_float((float) MathHelper.floor_double(var2));
        int var6 = MathHelper.floor_double(player.posZ);
        Block block = player.worldObj.getBlock(var4, var5, var6);
        if (block.getMaterial() != Material.lava && block.getMaterial() != Material.fire) {
            return false;
        } else {
            float var8 = BlockLiquid.getLiquidHeightPercent(player.worldObj.getBlockMetadata(var4, var5, var6)) - 0.11111111F;
            float var9 = (float) (var5 + 1) - var8;
            return var2 < (double) var9;
        }
    }

    public boolean hazmatAbsorbs(DamageSource source) {
        return source == DamageSource.inFire || source == DamageSource.inWall || source == DamageSource.lava || source == DamageSource.onFire || source == IC2DamageSource.electricity || source == IC2DamageSource.radiation;
    }

    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return false;
    }
}
