package com.denfop.item.armour;


import cofh.api.energy.IEnergyContainerItem;
import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.item.energy.ItemBattery;
import com.denfop.item.energy.ItemMagnet;
import com.denfop.proxy.CommonProxy;
import com.denfop.utils.*;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.*;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("SameReturnValue")
public class ItemArmorImprovemedQuantum extends ItemArmor
        implements ISpecialArmor, IElectricItem, IItemHudInfo, ICustomDamageItem, IMetalArmor {

    protected static final Map<Integer, Integer> potionRemovalCost = new HashMap<>();
    protected final double maxCharge;
    protected final double transferLimit;
    protected final int tier;
    private final ThreadLocal<Boolean> allowDamaging;
    private final String armorName;
    private IIcon[] textures;
    private float jumpCharge;

    public ItemArmorImprovemedQuantum(String name, int armorType1, double maxCharge1, double transferLimit1,
                                      int tier1) {

        super(ArmorMaterial.DIAMOND, IUCore.proxy.addArmor(name), armorType1);
        if (armorType1 == 3)
            MinecraftForge.EVENT_BUS.register(this);
        potionRemovalCost.put(Potion.poison.id, 100);
        potionRemovalCost.put(IC2Potion.radiation.id, 20);
        potionRemovalCost.put(Potion.wither.id, 100);
        potionRemovalCost.put(Potion.hunger.id, 200);
        this.allowDamaging = new ThreadLocal<>();
        this.maxCharge = maxCharge1;
        this.tier = tier1;
        this.transferLimit = transferLimit1;
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
        this.armorName = name;
        setUnlocalizedName(name);
        setCreativeTab(IUCore.tabssp2);
        GameRegistry.registerItem(this, name);
    }

    public static boolean hasCompleteHazmat(EntityLivingBase living) {

        for (int i = 1; i < 5; i++) {
            ItemStack stack = living.getEquipmentInSlot(i);

            if (stack == null || !(stack.getItem() instanceof ItemArmorImprovemedQuantum))
                return false;
        }

        return true;
    }

    public List<String> getHudInfo(ItemStack itemStack) {
        List<String> info = new LinkedList<>();
        info.add(ElectricItem.manager.getToolTip(itemStack));
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.PowerTier") + " " + this.tier);
        return info;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {


        return ModUtils.mode(itemStack, this.textures);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {

        String name = getUnlocalizedName();
        this.textures = new IIcon[6];
        this.textures[0] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name);
        this.textures[1] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name + "_" + "zelen");
        this.textures[2] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name + "_" + "demon");
        this.textures[3] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name + "_" + "dark");
        this.textures[4] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name + "_" + "cold");
        this.textures[5] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name + "_" + "ender");

    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        int suffix = (this.armorType == 2) ? 2 : 1;
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData(stack);
        if (!nbtData.getString("mode").isEmpty())
            return Constants.TEXTURES + ":textures/armor/" + this.armorName + "_" + nbtData.getString("mode") + "_" + suffix + ".png";


        return Constants.TEXTURES + ":textures/armor/" + this.armorName + "_" + suffix + ".png";
    }

    public String getUnlocalizedName() {
        return "supersolarpanels." + super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }

    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs var2, final List var3) {
        final ItemStack var4 = new ItemStack(this, 1);
        ElectricItem.manager.charge(var4, 2.147483647E9, Integer.MAX_VALUE, true, false);
        var3.add(var4);
        var3.add(new ItemStack(this, 1, this.getMaxDamage()));
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public boolean hasColor(ItemStack aStack) {
        return (getColor(aStack) != 10511680);
    }

    @Method(modid = "Thaumcraft")
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return IConfigurableItem.ProfileHelper.getBoolean(itemstack, "GogglesOfRevealing", true);
    }

    @Method(modid = "Thaumcraft")
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return IConfigurableItem.ProfileHelper.getBoolean(itemstack, "GogglesOfRevealing", true);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int par1, int par2) {
        return getIconFromDamage(par1);
    }

    public void removeColor(ItemStack par1ItemStack) {
        NBTTagCompound tNBT = par1ItemStack.getTagCompound();
        if (tNBT != null) {
            tNBT = tNBT.getCompoundTag("display");
            if (tNBT.hasKey("color"))
                tNBT.removeTag("color");
        }
    }

    public int getColor(ItemStack aStack) {
        NBTTagCompound tNBT = aStack.getTagCompound();
        if (tNBT == null)
            return 10511680;
        tNBT = tNBT.getCompoundTag("display");
        return (tNBT == null) ? 10511680 : (tNBT.hasKey("color") ? tNBT.getInteger("color") : 10511680);
    }

    public ISpecialArmor.ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source,
                                                       double damage, int slot) {
        if (Config.spectralquantumprotection) {
            double absorptionRatio = getBaseAbsorptionRatio() * getDamageAbsorptionRatio();
            NBTTagCompound nbt = ModUtils.nbt(armor);
            int protect = 0;
            for (int i = 0; i < 4; i++) {
                if (nbt.getString("mode_module" + i).equals("protect")) {
                    protect++;
                }

            }
            protect = Math.min(protect, EnumInfoUpgradeModules.PROTECTION.max);
            int energyPerDamage = (int) (this.getEnergyPerDamage() - this.getEnergyPerDamage() * 0.2 * protect);
            int damageLimit = (int) ((energyPerDamage > 0)
                    ? (25.0D * ElectricItem.manager.getCharge(armor) / energyPerDamage)
                    : 0.0D);
            return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
        } else {
            if (source == DamageSource.fall && this.armorType == 3) {
                NBTTagCompound nbt = ModUtils.nbt(armor);
                int protect = 0;
                for (int i = 0; i < 4; i++) {
                    if (nbt.getString("mode_module" + i).equals("protect")) {
                        protect++;
                    }

                }
                protect = Math.min(protect, EnumInfoUpgradeModules.PROTECTION.max);


                int energyPerDamage = (int) (this.getEnergyPerDamage() - this.getEnergyPerDamage() * 0.2 * protect);
                int damageLimit = 2147483647;
                if (energyPerDamage > 0) {
                    damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / (double) energyPerDamage);
                }

                return new ArmorProperties(10, 1.0D, damageLimit);
            } else {
                return getProperties1(armor, source);
            }
        }
    }

    public ArmorProperties getProperties1(ItemStack armor, DamageSource source) {
        if (source.isUnblockable()) {
            return new ArmorProperties(0, 0.0D, 0);
        } else {
            double absorptionRatio = this.getBaseAbsorptionRatio() * this.getDamageAbsorptionRatio();
            int energyPerDamage = this.getEnergyPerDamage();
            int damageLimit = 2147483647;
            if (energyPerDamage > 0) {
                damageLimit = (int) Math.min(damageLimit, 25.0D * ElectricItem.manager.getCharge(armor) / (double) energyPerDamage);
            }

            return new ArmorProperties(0, absorptionRatio, damageLimit);
        }
    }

    public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int protect = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("protect")) {
                protect++;
            }

        }
        protect = Math.min(protect, EnumInfoUpgradeModules.PROTECTION.max);


        ElectricItem.manager.discharge(stack, (damage * (this.getEnergyPerDamage() - this.getEnergyPerDamage() * 0.2 * protect) * 2), 2147483647, true, false, false);


    }

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @SubscribeEvent
    public void onEntityLivingFallEvent(LivingFallEvent event) {
        if (IC2.platform.isSimulating() && event.entity instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase) event.entity;
            ItemStack armor = entity.getEquipmentInSlot(1);
            if (armor != null && armor.getItem() == this) {
                int fallDamage = Math.max((int) event.distance - 10, 0);
                double energyCost = (getEnergyPerDamage() * fallDamage);
                if (energyCost <= ElectricItem.manager.getCharge(armor)) {
                    ElectricItem.manager.discharge(armor, energyCost, 2147483647, true, false, false);
                    event.setCanceled(true);
                }
            }
        }
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (armorType == 1)
            if (IC2.platform.isSimulating()) {
                int mode = ModUtils.NBTGetInteger(itemStack, "mode1");
                mode++;
                if (mode > 2 || mode < 0)
                    mode = 0;

                ModUtils.NBTSetInteger(itemStack, "mode1", mode);
                CommonProxy.sendPlayerMessage(entityplayer,
                        EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                + Helpers.formatMessage("message.magnet.mode." + mode));
                return itemStack;
            }

        return itemStack;
    }

    public double getDamageAbsorptionRatio() {
        if (this.armorType == 1)
            return 1.1D;
        return 1.0D;
    }

    private double getBaseAbsorptionRatio() {
        return 0.4D;
    }

    public int getEnergyPerDamage() {
        return 20000;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.epic;
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        int air;
        boolean Nightvision;
        short hubmode;
        boolean jetpack, hoverMode, enableQuantumSpeedOnSprint;
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData(itemStack);
        byte toggleTimer = nbtData.getByte("toggleTimer");
        boolean ret = false;
        int resistance = 0;
        int repaired = 0;
        for (int i = 0; i < 4; i++) {
            if (nbtData.getString("mode_module" + i).equals("invisibility")) {
                player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 300));
            }
            if (nbtData.getString("mode_module" + i).equals("resistance")) {
                resistance++;
            }
            if (nbtData.getString("mode_module" + i).equals("repaired")) {
                repaired++;
            }
        }
        if (repaired != 0)
            if (world.provider.getWorldTime() % 80 == 0)
                ElectricItem.manager.charge(itemStack, this.getMaxCharge(itemStack) * 0.00001 * repaired, Integer.MAX_VALUE, true, false);
        if (resistance != 0)
            player.addPotionEffect(new PotionEffect(Potion.resistance.id, 300, resistance));


        switch (this.armorType) {
            case 0:
                IC2.platform.profilerStartSection("QuantumHelmet");
                air = player.getAir();
                if (ElectricItem.manager.canUse(itemStack, 1000.0D) && air < 100) {
                    player.setAir(air + 200);
                    ElectricItem.manager.use(itemStack, 1000.0D, null);
                    ret = true;
                } else if (air <= 0) {
                    IC2.achievements.issueAchievement(player, "starveWithQHelmet");
                }
                if (ElectricItem.manager.canUse(itemStack, 1000.0D) && player.getFoodStats().needFood()) {
                    int slot = -1;
                    for (int i = 0; i < player.inventory.mainInventory.length; i++) {
                        if (player.inventory.mainInventory[i] != null
                                && player.inventory.mainInventory[i].getItem() instanceof ItemFood) {
                            slot = i;
                            break;
                        }
                    }
                    if (slot > -1) {
                        ItemStack stack = player.inventory.mainInventory[slot];
                        ItemFood can = (ItemFood) stack.getItem();
                        stack = can.onEaten(stack, world, player);
                        if (stack.stackSize <= 0)
                            player.inventory.mainInventory[slot] = null;
                        ElectricItem.manager.use(itemStack, 1000.0D, null);
                        ret = true;
                    }
                } else if (player.getFoodStats().getFoodLevel() <= 0) {
                    IC2.achievements.issueAchievement(player, "starveWithQHelmet");
                }

                for (PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
                    int id = effect.getPotionID();
                    Integer cost = potionRemovalCost.get(id);
                    if (cost != null) {
                        cost = cost * (effect.getAmplifier() + 1);
                        if (ElectricItem.manager.canUse(itemStack, cost)) {
                            ElectricItem.manager.use(itemStack, cost, null);
                            IC2.platform.removePotion(player, id);
                        }
                    }
                }

                Nightvision = nbtData.getBoolean("Nightvision");
                hubmode = nbtData.getShort("HudMode");
                if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    Nightvision = !Nightvision;
                    if (IC2.platform.isSimulating()) {
                        nbtData.setBoolean("Nightvision", Nightvision);
                        if (Nightvision) {
                            IC2.platform.messagePlayer(player, "Nightvision enabled.");
                        } else {
                            IC2.platform.messagePlayer(player, "Nightvision disabled.");
                        }
                    }
                }
                if (IC2.keyboard.isAltKeyDown(player) && IC2.keyboard.isHudModeKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    if (hubmode == 2) {
                        hubmode = 0;
                    } else {
                        hubmode = (short) (hubmode + 1);
                    }
                    if (IC2.platform.isSimulating()) {
                        nbtData.setShort("HudMode", hubmode);
                        switch (hubmode) {
                            case 0:
                                IC2.platform.messagePlayer(player, "HUD disabled.");
                                break;
                            case 1:
                                IC2.platform.messagePlayer(player, "HUD (basic) enabled.");
                                break;
                            case 2:
                                IC2.platform.messagePlayer(player, "HUD (extended) enabled");
                                break;
                        }
                    }
                }
                if (IC2.platform.isSimulating() && toggleTimer > 0) {
                    toggleTimer = (byte) (toggleTimer - 1);
                    nbtData.setByte("toggleTimer", toggleTimer);
                }
                if (Nightvision && IC2.platform.isSimulating() &&
                        ElectricItem.manager.use(itemStack, 1.0D, player)) {
                    int x = MathHelper.floor_double(player.posX);
                    int z = MathHelper.floor_double(player.posZ);
                    int y = MathHelper.floor_double(player.posY);
                    int skylight = player.worldObj.getBlockLightValue(x, y, z);
                    if (skylight > 8) {
                        IC2.platform.removePotion(player, Potion.nightVision.id);

                    } else {

                        player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0, true));
                    }
                    ret = true;
                }
                NBTTagCompound nbt = ModUtils.nbt(itemStack);
                boolean waterBreathing = false;
                for (int i = 0; i < 4; i++) {
                    if (nbt.getString("mode_module" + i).equals("waterBreathing")) {
                        waterBreathing = true;
                        break;
                    }

                }
                if (waterBreathing) {
                    player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 300));
                }
                IC2.platform.profilerEndSection();

                break;
            case 1:
                if (nbtData.getBoolean("jetpack")) {

                    if (ElectricItem.manager.canUse(itemStack, 25)) {
                        ElectricItem.manager.use(itemStack, 25, null);
                    } else {
                        nbtData.setBoolean("jetpack", false);
                    }
                }
                jetpack = nbtData.getBoolean("jetpack");
                boolean vertical = nbtData.getBoolean("vertical");
                hoverMode = nbtData.getBoolean("hoverMode");
                if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isModeSwitchKeyDown(player) && toggleTimer == 0) {
                    ItemStack jetpack1 = player.inventory.armorInventory[2];
                    ElectricItem.manager.discharge(jetpack1, 3000, 2147483647, true, false, false);
                    toggleTimer = 10;
                    hoverMode = !hoverMode;
                    if (IC2.platform.isSimulating()) {
                        nbtData.setBoolean("hoverMode", hoverMode);
                        if (hoverMode) {
                            IC2.platform.messagePlayer(player, "Quantum Hover Mode enabled.");
                        } else {
                            IC2.platform.messagePlayer(player, "Quantum Hover Mode disabled.");
                        }
                    }
                }
                if (IUCore.keyboard.isVerticalMode(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    vertical = !vertical;
                    if (IC2.platform.isSimulating()) {

                        nbtData.setBoolean("vertical", vertical);
                        if (vertical) {
                            IC2.platform.messagePlayer(player, "Vertical Mode enabled.");

                        } else {
                            IC2.platform.messagePlayer(player, "Vertical Mode disabled.");

                        }
                    }
                }
                if (vertical && jetpack) {
                    double motion = 0;
                    if (IC2.keyboard.isJumpKeyDown(player))
                        motion = 0.3;
                    if (player.isSneaking())
                        motion = -0.3;

                    player.motionY += motion;
                }


                if (nbtData.getBoolean("magnet")) {
                    int mode = ModUtils.NBTGetInteger(itemStack, "mode1");
                    int radius = 11;
                    if (mode != 0) {
                        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, axisalignedbb);

                        for (Entity entityinlist : list) {
                            if (entityinlist instanceof EntityItem) {
                                EntityItem item = (EntityItem) entityinlist;
                                if (ElectricItem.manager.canUse(itemStack, 500)) {
                                    ItemStack stack = item.getEntityItem();
                                    if (!(stack.getItem() instanceof ItemMagnet))
                                        if (mode == 1) {
                                            if (player.inventory.addItemStackToInventory(stack)) {
                                                ElectricItem.manager.use(itemStack, 500, player);
                                            } else {
                                                boolean xcoord = item.posX + 2 >= player.posX && item.posX - 2 <= player.posX;
                                                boolean zcoord = item.posZ + 2 >= player.posZ && item.posZ - 2 <= player.posZ;
                                                if (!xcoord && !zcoord) {
                                                    item.setPosition(player.posX, player.posY - 1, player.posZ);
                                                    item.delayBeforeCanPickup = 10;
                                                    world.func_147479_m((int) (player.posX), (int) player.posY - 1, (int) (player.posZ));
                                                }
                                            }
                                        } else if (mode == 2) {
                                            boolean xcoord = item.posX + 2 >= player.posX && item.posX - 2 <= player.posX;
                                            boolean zcoord = item.posZ + 2 >= player.posZ && item.posZ - 2 <= player.posZ;


                                            if (!xcoord && !zcoord) {
                                                item.setPosition(player.posX, player.posY - 1, player.posZ);
                                                item.delayBeforeCanPickup = 10;
                                                world.func_147479_m((int) (player.posX), (int) player.posY - 1, (int) (player.posZ));
                                            }
                                        }
                                }

                            }
                        }
                    }
                }

                if (IUCore.keyboard.isStreakKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    player.openGui(IUCore.instance, 4, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);

                }
                boolean magnet = !nbtData.getBoolean("magnet");

                if (IUCore.keyboard.isChangeKeyDown(player) && IC2.keyboard.isSneakKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    if (IC2.platform.isSimulating()) {
                        if (magnet)
                            IC2.platform.messagePlayer(player, "Magnet enabled.");
                        if (!magnet)
                            IC2.platform.messagePlayer(player, "Magnet disabled.");
                        nbtData.setBoolean("magnet", magnet);
                    }
                }

                if (IUCore.keyboard.isFlyModeKeyDown(player) && toggleTimer == 0) {
                    toggleTimer = 10;
                    jetpack = !jetpack;
                    if (IC2.platform.isSimulating()) {

                        nbtData.setBoolean("jetpack", jetpack);
                        if (jetpack) {
                            IC2.platform.messagePlayer(player, "Quantum Jetpack enabled.");
                            player.capabilities.isFlying = true;

                            player.capabilities.allowFlying = true;
                            player.fallDistance = 0.0F;
                            player.distanceWalkedModified = 0.0F;
                        } else {
                            IC2.platform.messagePlayer(player, "Quantum Jetpack disabled.");

                        }
                    }
                }


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
                            && player.inventory.mainInventory[j].getItem() instanceof ic2.api.item.IElectricItem
                            && !(player.inventory.mainInventory[j].getItem() instanceof ItemBattery && ((ItemBattery) player.inventory.mainInventory[j].getItem()).wirellescharge)) {
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

                if (IC2.platform.isSimulating() && toggleTimer > 0) {
                    toggleTimer = (byte) (toggleTimer - 1);
                    nbtData.setByte("toggleTimer", toggleTimer);
                }

                nbt = ModUtils.nbt(itemStack);
                boolean fireResistance = false;
                for (int i = 0; i < 4; i++) {
                    if (nbt.getString("mode_module" + i).equals("fireResistance")) {
                        fireResistance = true;
                        break;
                    }

                }

                if (fireResistance) {
                    player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 300));
                }
                if (ret)
                    player.inventoryContainer.detectAndSendChanges();
                player.extinguish();
                break;
            case 2:
                IC2.platform.profilerStartSection("QuantumLeggings");
                if (IC2.platform.isRendering()) {
                    enableQuantumSpeedOnSprint = ConfigUtil.getBool(MainConfig.get(), "misc/quantumSpeedOnSprint");
                } else {
                    enableQuantumSpeedOnSprint = true;
                }

                if (ElectricItem.manager.canUse(itemStack, 1000.0D) && (player.onGround || player.isInWater())
                        && IC2.keyboard.isForwardKeyDown(player) && ((enableQuantumSpeedOnSprint && player.isSprinting())
                        || (!enableQuantumSpeedOnSprint && IC2.keyboard.isBoostKeyDown(player)))) {
                    byte speedTicker = nbtData.getByte("speedTicker");
                    speedTicker = (byte) (speedTicker + 1);
                    if (speedTicker >= 10) {
                        speedTicker = 0;
                        ElectricItem.manager.use(itemStack, 1000.0D, null);
                        ret = true;
                    }
                    nbtData.setByte("speedTicker", speedTicker);
                    float speed = 0.22F;
                    if (player.isInWater()) {
                        speed = 0.1F;
                        if (IC2.keyboard.isJumpKeyDown(player))
                            player.motionY += 0.10000000149011612D;
                    }
                    player.moveFlying(0.0F, 1.0F, speed);
                }
                nbt = ModUtils.nbt(itemStack);
                boolean moveSpeed = false;
                for (int i = 0; i < 4; i++) {
                    if (nbt.getString("mode_module" + i).equals("moveSpeed")) {
                        moveSpeed = true;
                        break;
                    }

                }
                if (moveSpeed) {
                    player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 300));

                }
                IC2.platform.profilerEndSection();
                break;
            case 3:
                IC2.platform.profilerStartSection("QuantumBoots");
                if (IC2.platform.isSimulating()) {
                    boolean wasOnGround = !nbtData.hasKey("wasOnGround") || nbtData.getBoolean("wasOnGround");
                    if (wasOnGround && !player.onGround && IC2.keyboard

                            .isJumpKeyDown(player) && IC2.keyboard
                            .isBoostKeyDown(player)) {
                        ElectricItem.manager.use(itemStack, 4000.0D, null);
                        ret = true;
                    }
                    if (player.onGround != wasOnGround)
                        nbtData.setBoolean("wasOnGround", player.onGround);
                } else {
                    if (ElectricItem.manager.canUse(itemStack, 4000.0D) && player.onGround) {

                        this.jumpCharge = 1.0F;
                    }
                    if (player.motionY >= 0.0D && this.jumpCharge > 0.0F && !player.isInWater())
                        if (IC2.keyboard.isJumpKeyDown(player) && IC2.keyboard.isBoostKeyDown(player)) {
                            if (this.jumpCharge == 1.0F) {
                                player.motionX *= 3.5D;
                                player.motionZ *= 3.5D;
                            }
                            player.motionY += (this.jumpCharge * 0.3F);
                            this.jumpCharge = (float) (this.jumpCharge * 0.75D);
                        } else if (this.jumpCharge < 1.0F) {
                            this.jumpCharge = 0.0F;
                        }
                }
                nbt = ModUtils.nbt(itemStack);
                boolean jump = false;
                for (int i = 0; i < 4; i++) {
                    if (nbt.getString("mode_module" + i).equals("jump")) {
                        jump = true;
                        break;
                    }

                }
                if (jump) {


                    player.addPotionEffect(new PotionEffect(Potion.jump.id, 300));
                }
                IC2.platform.profilerEndSection();
                break;

        }

        if (ret)
            player.inventoryContainer.detectAndSendChanges();
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

    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return false;
    }

    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List info, final boolean b) {
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData(itemStack);

        if (itemStack.getItem() == IUItem.quantumBodyarmor) {
            info.add(StatCollector.translateToLocal("iu.fly") + " " + ModUtils.Boolean(nbtData.getBoolean("jetpack")));
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                info.add(StatCollector.translateToLocal("press.lshift"));


            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                info.add(StatCollector.translateToLocal("iu.changemode_fly") + Keyboard.getKeyName(KeyboardClient.flymode.getKeyCode()));
                info.add(StatCollector.translateToLocal("iu.vertical") + Keyboard.getKeyName(KeyboardClient.verticalmode.getKeyCode()));
                info.add(StatCollector.translateToLocal("iu.streak") + Keyboard.getKeyName(KeyboardClient.streakmode.getKeyCode()));
                info.add(StatCollector.translateToLocal("iu.magnet_mode") + Keyboard.getKeyName(KeyboardClient.changemode.getKeyCode()) + " + " + Keyboard.getKeyName(Keyboard.KEY_LSHIFT));

            }
            for (PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
                int id = effect.getPotionID();
                if (id == Potion.fireResistance.id) {
                    info.add(StatCollector.translateToLocal("iu.effect") + " " + ModUtils.Boolean(true));
                }
            }

        }
        ModUtils.mode(itemStack, info);
    }

    public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
        if (ElectricItem.manager.getCharge(armor) >= getEnergyPerDamage())
            return (int) Math.round(20.0D * getBaseAbsorptionRatio() * getDamageAbsorptionRatio());
        return 0;
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
