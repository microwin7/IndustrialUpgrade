package com.denfop.integration.de;

import com.brandon3055.draconicevolution.common.items.weapons.IEnergyContainerWeaponItem;
import com.brandon3055.draconicevolution.common.utills.IUpgradableItem;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ToolHandler {
    public static void damageEntityBasedOnHealth(Entity entity, EntityPlayer player, float dmgMult) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack == null || !(stack.getItem() instanceof IEnergyContainerWeaponItem)) {
            LogHelper.error("[ToolHandler.java:147] WTF? I don't get it... Player " + player.getCommandSenderName()
                    + " whacked something with a DE weapon but that they are not holding? Ok someone is messing with my shit...");
            return;
        }
        IEnergyContainerWeaponItem item = (IEnergyContainerWeaponItem) stack.getItem();
        float baseAttack = getDamageAgainstEntity(stack, entity);
        if (entity instanceof EntityLivingBase) {
            float entHealth = ((EntityLivingBase) entity).getHealth();
            baseAttack += entHealth * dmgMult;
        }
        if (entity instanceof net.minecraft.entity.boss.EntityDragonPart) {
            List<EntityDragon> list = player.worldObj.getEntitiesWithinAABB(EntityDragon.class,
                    entity.boundingBox.expand(10.0D, 10.0D, 10.0D));
            if (!list.isEmpty() && list.get(0) != null) {
                EntityDragon dragon = list.get(0);
                float entHealth = dragon.getHealth();
                baseAttack += entHealth * dmgMult;
            }
        }
        int rf = (int) baseAttack * item.getEnergyPerAttack();
        if (rf > item.getEnergyStored(stack)) {
            baseAttack = ((float) item.getEnergyStored(stack) / (float) item.getEnergyPerAttack());
            rf = item.getEnergyStored(stack);
        }
        if (baseAttack <= 0.0F)
            baseAttack = 1.0F;
        entity.attackEntityFrom(DamageSource.causePlayerDamage(player), baseAttack);
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) > 0)
            entity.setFire(EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 15);
        if (!player.capabilities.isCreativeMode)
            item.extractEnergy(stack, rf, false);
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
            double d1 = player.posX - entityLivingBase.posX;
            double d0;
            for (d0 = player.posZ - entityLivingBase.posZ; d1 * d1
                    + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                d1 = (Math.random() - Math.random()) * 0.01D;
            entityLivingBase.attackedAtYaw = (float) (Math.atan2(d0, d1) * 180.0D / Math.PI)
                    - entityLivingBase.rotationYaw;
            if (entityLivingBase.worldObj.rand.nextDouble() >= entityLivingBase
                    .getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
                entityLivingBase.isAirBorne = true;
                float f1 = MathHelper.sqrt_double(d1 * d1 + d0 * d0);
                float f2 = 0.1F
                        + EnchantmentHelper.getKnockbackModifier(player, entityLivingBase) * 0.4F;
                entityLivingBase.motionX /= 2.0D;
                entityLivingBase.motionY /= 2.0D;
                entityLivingBase.motionZ /= 2.0D;
                entityLivingBase.motionX -= d1 / f1 * f2;
                entityLivingBase.motionY += f2;
                entityLivingBase.motionZ -= d0 / f1 * f2;
                if (entityLivingBase.motionY > 0.4000000059604645D)
                    entityLivingBase.motionY = 0.4000000059604645D;
            }
        }
    }

    public static void AOEAttack(EntityPlayer player, Entity entity, ItemStack stack, int range) {
        World world = player.worldObj;
        AxisAlignedBB box = AxisAlignedBB.getBoundingBox(entity.posX - range, entity.posY - range, entity.posZ - range,
                entity.posX + range, entity.posY + range, entity.posZ + range).expand(1.0D, 1.0D, 1.0D);
        List list = world.getEntitiesWithinAABBExcludingEntity(player, box);
        if (range == 0)
            return;
        IEnergyContainerWeaponItem item = (IEnergyContainerWeaponItem) stack.getItem();
        for (Object entityObject : list) {
            if (item.getEnergyStored(stack) < item.getEnergyPerAttack())
                break;
            if (entityObject instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) entityObject;
                if (entityLivingBase.getEntityId() == entity.getEntityId())
                    continue;
                float dmg = getDamageAgainstEntity(stack, entityLivingBase);
                int rf = (int) dmg * item.getEnergyPerAttack();
                if (rf > item.getEnergyStored(stack)) {
                    dmg = ((float) item.getEnergyStored(stack) / (float) item.getEnergyPerAttack());
                    rf = item.getEnergyStored(stack);
                }
                entityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(player), dmg);
                item.extractEnergy(stack, rf, false);
                if (EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) > 0)
                    entityLivingBase.setFire(
                            EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 15);
                double d1 = player.posX - entityLivingBase.posX;
                double d0;
                for (d0 = player.posZ - entityLivingBase.posZ; d1 * d1
                        + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
                    d1 = (Math.random() - Math.random()) * 0.01D;
                if (entityLivingBase.worldObj.rand.nextDouble() >= entityLivingBase
                        .getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()) {
                    entityLivingBase.isAirBorne = true;
                    float f1 = MathHelper.sqrt_double(d1 * d1 + d0 * d0);
                    float f2 = 0.1F
                            + EnchantmentHelper.getKnockbackModifier(player, entityLivingBase)
                            * 0.4F;
                    entityLivingBase.motionX /= 2.0D;
                    entityLivingBase.motionY /= 2.0D;
                    entityLivingBase.motionZ /= 2.0D;
                    entityLivingBase.motionX -= d1 / f1 * f2;
                    entityLivingBase.motionY += f2;
                    entityLivingBase.motionZ -= d0 / f1 * f2;
                    if (entityLivingBase.motionY > 0.4000000059604645D)
                        entityLivingBase.motionY = 0.4000000059604645D;
                }
                entityLivingBase.attackTime = 0;
                continue;
            }
            if (entityObject instanceof com.brandon3055.draconicevolution.common.entity.EntityDragonProjectile) {
                float dmg = getDamageAgainstEntity(stack, (Entity) entityObject);
                int rf = (int) dmg * item.getEnergyPerAttack();
                if (rf > item.getEnergyStored(stack)) {
                    dmg = ((float) item.getEnergyStored(stack) / (float) item.getEnergyPerAttack());
                    rf = item.getEnergyStored(stack);
                }
                ((Entity) entityObject).attackEntityFrom(DamageSource.causePlayerDamage(player), dmg);
                item.extractEnergy(stack, rf, false);
            }
        }
    }

    public static float getBaseAttackDamage(ItemStack stack) {
        float dmg = 0.0F;
        if (stack == null)
            return 1.0F;
        float sharpMod = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 4.0F;
        if (stack.getItem() == DraconicIntegration.ChaosDestructionStaff) {
            dmg = DraconicIntegration.CHAOS.getDamageVsEntity() + sharpMod;
        } else if (stack.getItem() instanceof ItemSword) {
            dmg = ((ItemSword) stack.getItem()).func_150931_i() + sharpMod;
        }
        dmg += (IUpgradableItem.EnumUpgrade.ATTACK_DAMAGE.getUpgradePoints(stack) * 5);
        return dmg;
    }

    public static float getDamageAgainstEntity(ItemStack stack, Entity entity) {
        float baseAttack = getBaseAttackDamage(stack);
        float smiteMod = EnchantmentHelper.getEnchantmentLevel(Enchantment.smite.effectId, stack) * 6.0F;
        float athropodsMod = EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, stack) * 6.0F;
        if (entity instanceof EntityLivingBase && ((EntityLivingBase) entity).isEntityUndead())
            baseAttack += smiteMod;
        if (entity instanceof net.minecraft.entity.monster.EntitySpider)
            baseAttack += athropodsMod;
        return baseAttack;
    }
}
