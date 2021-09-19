package com.denfop.item.energy;


import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.KeyboardClient;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class EnergyBow extends ItemBow implements IElectricItem {


    static final int[] CHARGE = new int[]{1500, 750, 2000, 5000, 1000};

    static final String[] MODE = new String[]{"normal", "rapidfire", "spread", "sniper", "flame"};

    public final IIcon[] icons;

    private final String name;

    private final double nanoBowBoost;

    private final int tier;

    private final int transferenergy;

    private final int maxenergy;

    private static float type;

    public EnergyBow(String name, double nanoBowBoost, int tier, int transferenergy, int maxenergy, float type) {
        setMaxDamage(27);
        setFull3D();
        setCreativeTab(IUCore.tabssp2);
        this.icons = new IIcon[4];
        this.name = name;
        this.nanoBowBoost = nanoBowBoost;
        this.tier = tier;
        this.transferenergy = transferenergy;
        this.maxenergy = maxenergy;
        EnergyBow.type = type;
        setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List tooltip, boolean advanced) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        IElectricItem item = (IElectricItem) stack.getItem();
        if (!nbt.getBoolean("loaded")) {
            if (nbt.getInteger("tier") == 0)
                nbt.setInteger("tier", item.getTier(stack));
            if (nbt.getDouble("transferLimit") == 0.0D)
                nbt.setDouble("transferLimit", item.getTransferLimit(stack));
            if (nbt.getDouble("maxCharge") == 0.0D)
                nbt.setDouble("maxCharge", item.getMaxCharge(stack));
            nbt.setBoolean("loaded", true);
        }
        if (nbt.getDouble("transferLimit") != item.getTransferLimit(stack))
            tooltip.add(String.format(I18n.format("info.transferspeed"), nbt.getDouble("transferLimit")));
        if (nbt.getInteger("tier") != item.getTier(stack))
            tooltip.add(String.format(I18n.format("info.chargingtier"), nbt.getInteger("tier")));

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            tooltip.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            tooltip.add(StatCollector.translateToLocal("iu.changemode_key") + Keyboard.getKeyName(KeyboardClient.changemode.getKeyCode()) + StatCollector.translateToLocal("iu.changemode_rcm"));

    }

    public void getSubItems(Item item, CreativeTabs tabs, List items) {
        ItemStack charged = new ItemStack(this, 1);
        ElectricItem.manager.charge(charged, 2.147483647E9D, 2147483647, true, false);
        items.add(charged);
        items.add(new ItemStack(this, 1, getMaxDamage()));
    }

    public boolean isRepairable() {
        return false;
    }

    public int getItemEnchantability() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        this.icons[0] = ir.registerIcon(Constants.TEXTURES + ":" + name);
        this.icons[1] = ir.registerIcon(Constants.TEXTURES + ":" + name + "_" + "1");
        this.icons[2] = ir.registerIcon(Constants.TEXTURES + ":" + name + "_" + "2");
        this.icons[3] = ir.registerIcon(Constants.TEXTURES + ":" + name + "_" + "3");
        this.itemIcon = this.icons[0];
    }

    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        if (usingItem != null) {
            NBTTagCompound nbt = ModUtils.nbt(stack);
            int mode = nbt.getInteger("bowMode");
            int i1 = 18;
            int i2 = 13;
            if (mode == 3) {
                i1 = 36;
                i2 = 26;
            } else if (mode == 1) {
                i1 = 5;
                i2 = 3;
            }
            int k = usingItem.getMaxItemUseDuration() - useRemaining;
            if (k >= i1)
                return this.icons[3];
            if (k > i2)
                return this.icons[2];
            if (k > 0)
                return this.icons[1];
        }
        return this.icons[0];
    }

    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int timeLeft) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int mode = nbt.getInteger("bowMode");
        int charge = getMaxItemUseDuration(stack) - timeLeft;
        ArrowLooseEvent event = new ArrowLooseEvent(player, stack, charge);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return;
        charge = event.charge;
        if (mode == 3)
            charge /= 2;
        if (mode == 1)
            charge *= 4;
        float f = getArrowVelocity(charge);
        if (f < 0.1D)
            return;
        if (!world.isRemote) {
            EntityArrow arrow = new EntityArrow(world, player, f);
            if (f == 1.5F)
                arrow.setIsCritical(true);
            int bowdamage = 0;
            for (int i = 0; i < 4; i++) {
                if (nbt.getString("mode_module" + i).equals("bowdamage")) {
                    bowdamage++;
                }

            }
            bowdamage = Math.min(bowdamage, EnumInfoUpgradeModules.BOWDAMAGE.max);

            arrow.setDamage(arrow.getDamage() + type * 2.5D + 0.5D + type * 2.5D * 0.25 * bowdamage);
            int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            if (j > 0)
                arrow.setDamage(arrow.getDamage() + j * 0.5D + 0.5D);
            if (mode == 0 && arrow.getIsCritical()) {
                j += 3;
            } else if (mode == 1 && arrow.getIsCritical()) {
                j++;
            } else if (mode == 3 && arrow.getIsCritical()) {
                j += 8;
            }
            if (j > 0)
                arrow.setDamage(arrow.getDamage() + j * 0.5D + 0.5D);
            if (nanoBowBoost > 0)
                arrow.setDamage(arrow.getDamage() + nanoBowBoost * 0.5D + 0.5D);
            int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if (mode == 0 && arrow.getIsCritical()) {
                k++;
            } else if (mode == 3 && arrow.getIsCritical()) {
                k += 5;
            }
            if (k > 0)
                arrow.setKnockbackStrength(k);
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0)
                arrow.setFire(100);
            if (mode == 4 && arrow.getIsCritical())
                arrow.setFire(2000);
            arrow.canBePickedUp = 2;

            int bowenergy = 0;
            for (int i = 0; i < 4; i++) {
                if (nbt.getString("mode_module" + i).equals("bowenergy")) {
                    bowenergy++;
                }

            }
            bowenergy = Math.min(bowenergy, EnumInfoUpgradeModules.BOWENERGY.max);

            if (mode == 2) {
                if (ElectricItem.manager.canUse(stack, CHARGE[mode] - CHARGE[mode] * 0.1 * bowenergy)) {
                    ElectricItem.manager.use(stack, CHARGE[mode] - CHARGE[mode] * 0.1 * bowenergy, player);
                    world.spawnEntityInWorld(arrow);
                    if (arrow.getIsCritical()) {
                        EntityArrow arrow2 = new EntityArrow(world, player, f * 2.0F);
                        arrow2.setPosition(arrow2.posX + 0.25D, arrow2.posY, arrow2.posZ);
                        arrow2.setIsCritical(true);
                        arrow2.canBePickedUp = 2;
                        EntityArrow arrow3 = new EntityArrow(world, player, f * 2.0F);
                        arrow3.setPosition(arrow3.posX, arrow3.posY + 0.25D, arrow3.posZ);
                        arrow3.setIsCritical(true);
                        arrow3.canBePickedUp = 2;
                        EntityArrow arrow4 = new EntityArrow(world, player, f * 2.0F);
                        arrow4.setPosition(arrow4.posX - 0.25D, arrow4.posY, arrow4.posZ);
                        arrow4.setIsCritical(true);
                        arrow4.canBePickedUp = 2;
                        EntityArrow arrow5 = new EntityArrow(world, player, f * 2.0F);
                        arrow5.setPosition(arrow5.posX, arrow5.posY - 0.25D, arrow5.posZ);
                        arrow5.setIsCritical(true);
                        arrow5.canBePickedUp = 2;
                        world.spawnEntityInWorld(arrow2);
                        world.spawnEntityInWorld(arrow3);
                        world.spawnEntityInWorld(arrow4);
                        world.spawnEntityInWorld(arrow5);
                    }
                }
            } else {
                if (ElectricItem.manager.canUse(stack, CHARGE[mode] - CHARGE[mode] * 0.1 * bowenergy)) {
                    ElectricItem.manager.use(stack, CHARGE[mode] - CHARGE[mode] * 0.1 * bowenergy, player);

                    world.spawnEntityInWorld(arrow);
                }
            }
        }
        if (IC2.platform.isRendering()) {
            IUCore.audioManager.playOnce(player, com.denfop.audio.PositionSpec.Hand, "Tools/bow.ogg", true, IC2.audioManager.getDefaultVolume());
        }
      }

    public static float getArrowVelocity(int charge) {
        float f = charge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        return Math.min(f, 1.5F);
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        switch (nbt.getInteger("bowMode")) {
            case 3:
            case 5:
                return 144000;
            case 1:
                return 18000;
        }
        return 72000;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound nbt = ModUtils.nbt(stack);

        int mode = nbt.getInteger("bowMode");
        int bowenergy = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("bowenergy")) {
                bowenergy++;
            }

        }
        bowenergy = Math.min(bowenergy, EnumInfoUpgradeModules.BOWENERGY.max);

        if (IUCore.keyboard.isChangeKeyDown(player) && nbt.getByte("toggleTimer") == 0) {
            if (!world.isRemote) {
                byte toggle = 10;
                nbt.setByte("toggleTimer", toggle);
                mode++;

                if (mode > 4)
                    mode = 0;
                nbt.setInteger("bowMode", mode);
                player.addChatMessage(new ChatComponentTranslation("info.nanobow." + MODE[mode]));
            }
        } else if (player.capabilities.isCreativeMode || ElectricItem.manager.canUse(stack, CHARGE[mode] - CHARGE[mode] * 0.1 * bowenergy)) {
            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }
        ArrowNockEvent event = new ArrowNockEvent(player, stack);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.isCanceled())
            return event.result;

        return stack;
    }

    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        byte toggle = nbt.getByte("toggleTimer");
        if (toggle > 0) {
            toggle = (byte) (toggle - 1);
            nbt.setByte("toggleTimer", toggle);
        }

    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int i) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int mode = nbt.getInteger("bowMode");

        if (mode == 1) {
            int bowenergy = 0;
            for (int k = 0; k < 4; k++) {
                if (nbt.getString("mode_module" + k).equals("bowenergy")) {
                    bowenergy++;
                }

            }
            bowenergy = Math.min(bowenergy, EnumInfoUpgradeModules.BOWENERGY.max);

            int j = getMaxItemUseDuration(stack) - i;
            if (j >= 10 && ElectricItem.manager.canUse(stack, CHARGE[1] - CHARGE[1] * 0.1 * bowenergy))
                player.stopUsingItem();
        }
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }

    public boolean canProvideEnergy(ItemStack is) {
        return false;
    }

    public double getMaxCharge(ItemStack stack) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        if (nbt.getDouble("maxCharge") == 0.0D)
            nbt.setDouble("maxCharge", getDefaultMaxCharge());
        return nbt.getDouble("maxCharge");
    }

    public int getTier(ItemStack stack) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        if (nbt.getInteger("tier") == 0)
            nbt.setInteger("tier", getDefaultTier());
        return nbt.getInteger("tier");
    }

    public double getTransferLimit(ItemStack stack) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        if (nbt.getDouble("transferLimit") == 0.0D)
            nbt.setDouble("transferLimit", getDefaultTransferLimit());
        return nbt.getDouble("transferLimit");
    }

    public Item getChargedItem(ItemStack stack) {
        return (stack == null) ? null : stack.getItem();
    }

    public Item getEmptyItem(ItemStack stack) {
        return (stack == null) ? null : stack.getItem();
    }

    public int getDefaultMaxCharge() {
        return maxenergy;
    }

    public int getDefaultTier() {
        return this.tier;
    }

    public int getDefaultTransferLimit() {
        return this.transferenergy;
    }


}
