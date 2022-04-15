package com.denfop.item.armour;

import com.brandon3055.draconicevolution.common.utills.IConfigurableItem;
import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.api.upgrade.IUpgradeItem;
import com.denfop.api.upgrade.UpgradeSystem;
import com.denfop.api.upgrade.event.EventItemLoad;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.Optional.Method;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.IC2Potion;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ItemSolarPanelHelmet extends ItemArmor implements IElectricItem, IUpgradeItem, IMetalArmor, ISpecialArmor {
    protected static final Map<Integer, Integer> potionRemovalCost = new HashMap<>();
    private final int solarType;
    private double maxCharge;
    private double transferLimit;
    private int tier;
    private double genDay;
    private double genNight;
    private int energyPerDamage;
    private double damageAbsorptionRatio;
    private double baseAbsorptionRatio;
    private boolean sunIsUp;
    private boolean skyIsVisible;
    private boolean ret = false;
    private double storage;
    private double maxstorage;
    private boolean update = false;

    public ItemSolarPanelHelmet(final ItemArmor.ArmorMaterial par2EnumArmorMaterial, final int par3, final int par4,
                                final int htype, String name) {
        super(par2EnumArmorMaterial, par3, par4);
        this.solarType = htype;

        this.transferLimit = 3000.0;
        this.tier = 1;
        if (this.solarType == 1) {
            this.genDay = Config.advGenDay;
            this.genNight = genDay/2;
            this.maxCharge = 1000000.0;
            this.energyPerDamage = 800;
            this.damageAbsorptionRatio = 0.9;
            this.baseAbsorptionRatio = 0.15;
            this.storage = 0;
            this.maxstorage = Config.advStorage / 2;
        }
        if (this.solarType == 2) {
            this.genDay = Config.hGenDay;
            this.genNight = genDay/2;
            this.maxCharge = 1.0E7;
            this.transferLimit = 10000.0;
            this.tier = 2;
            this.energyPerDamage = 2000;
            this.damageAbsorptionRatio = 1.0;
            this.baseAbsorptionRatio = 0.15;
            this.storage = 0;
            this.maxstorage = Config.hStorage / 2;
        }
        if (this.solarType == 3) {
            this.genDay = Config.uhGenDay;
            this.genNight = genDay/2;
            this.maxCharge = 1.0E7;
            this.transferLimit = 10000.0;
            this.tier = 3;
            this.energyPerDamage = 2000;
            this.damageAbsorptionRatio = 1.0;
            this.baseAbsorptionRatio = 0.15;
            this.storage = 0;
            this.maxstorage = Config.uhStorage / 2;
        }
        if (this.solarType == 4) {
            this.genDay = Config.spectralpanelGenDay;
            this.genNight = genDay/2;
            this.transferLimit = 38000.0;
            this.maxCharge = Config.Storagequantumsuit;
            this.tier = 5;
            this.energyPerDamage = 5000;
            this.damageAbsorptionRatio = 0.9;
            this.baseAbsorptionRatio = 0.15;
            this.storage = 0;
            this.maxstorage = Config.spectralpanelstorage / 2;
        }
        if (this.solarType == 5) {
            this.genDay = Config.singularpanelGenDay;
            this.genNight = genDay/2;
            this.transferLimit = 100000.0;
            this.maxCharge = Config.Storagequantumsuit;
            this.tier = 7;
            this.energyPerDamage = 10000;
            this.damageAbsorptionRatio = 1.0;
            this.baseAbsorptionRatio = 0.15;
            this.storage = 0;
            this.maxstorage = Config.singularpanelstorage / 2;
        }
        this.setCreativeTab(IUCore.tabssp2);
        this.setMaxDamage(27);
        potionRemovalCost.put(Potion.poison.id, 100);
        potionRemovalCost.put(IC2Potion.radiation.id, 20);
        potionRemovalCost.put(Potion.wither.id, 100);
        potionRemovalCost.put(Potion.hunger.id, 200);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
    }
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5) {
        NBTTagCompound nbt = ModUtils.nbt(itemStack);

        if (!UpgradeSystem.system.hasInMap(itemStack)) {
            nbt.setBoolean("hasID", false);
            MinecraftForge.EVENT_BUS.post(new EventItemLoad(world, this, itemStack));
        }
    }
    @Method(modid = "Thaumcraft")
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return IConfigurableItem.ProfileHelper.getBoolean(itemstack, "GogglesOfRevealing", true);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Method(modid = "Thaumcraft")
    public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
        return IConfigurableItem.ProfileHelper.getBoolean(itemstack, "GogglesOfRevealing", true);
    }
    private double experimental_generating(World world) {
        double k = 0;
        float angle = world.getCelestialAngle(1.0F) - 0.784690560F < 0 ? 1.0F - 0.784690560F : -0.784690560F;
        float celestialAngle = (world.getCelestialAngle(1.0F) + angle) * 360.0F;

        celestialAngle %= 360;
        celestialAngle += 12;
        //TODO: end code GC
        if (celestialAngle <= 90)
            k = celestialAngle / 90;
        else if (celestialAngle > 90 && celestialAngle < 180) {
            celestialAngle -= 90;
            k = 1 - celestialAngle / 90;
        } else if (celestialAngle > 180 && celestialAngle < 270) {
            celestialAngle -= 180;
            k = celestialAngle / 90;
        } else if (celestialAngle > 270 && celestialAngle < 360) {
            celestialAngle -= 270;
            k = 1 - celestialAngle / 90;
        }


        return k;

    }
    public void onArmorTick(World worldObj, EntityPlayer player, ItemStack itemStack) {
        if (worldObj.isRemote)
            return;
        gainFuel(player);
        NBTTagCompound nbtData = ModUtils.nbt(itemStack);
        int resistance = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.RESISTANCE, itemStack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.RESISTANCE, itemStack).number : 0);
        int repaired = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.REPAIRED, itemStack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.REPAIRED, itemStack).number : 0);
        if (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.INVISIBILITY, itemStack)) {
            player.addPotionEffect(new PotionEffect(Potion.invisibility.id, 300));
        }

        if (repaired != 0) {
            if (worldObj.provider.getWorldTime() % 80 == 0) {
                ElectricItem.manager.charge(
                        itemStack,
                        this.getMaxCharge(itemStack) * 0.00001 * repaired,
                        Integer.MAX_VALUE,
                        true,
                        false
                );
            }
        }
        if (resistance != 0) {
            player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 300, resistance));
        }

        int genday = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.GENDAY, itemStack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.GENDAY, itemStack).number : 0);

        int gennight = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.GENNIGHT, itemStack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.GENNIGHT, itemStack).number : 0);
        int storage = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.STORAGE, itemStack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.STORAGE, itemStack).number : 0);

        double k = experimental_generating(worldObj);
        if (this.sunIsUp && this.skyIsVisible) {
            this.storage = nbtData.getDouble("storage");
            this.storage = this.storage + (this.genDay + this.genDay * 0.05 * genday)*k;
            nbtData.setDouble("storage", this.storage);
        }
        if (this.skyIsVisible) {
            this.storage = nbtData.getDouble("storage");
            this.storage = this.storage + (this.genNight + this.genNight * 0.05 * gennight)*k;
            nbtData.setDouble("storage", this.storage);

        }
        if (nbtData.getDouble("storage") >= (maxstorage + maxstorage * 0.05 * storage)) {
            nbtData.setDouble("storage", (maxstorage + maxstorage * 0.05 * storage));
        }
        if (nbtData.getDouble("storage") < 0) {
            nbtData.setDouble("storage", 0);
        }

        for (PotionEffect effect : new LinkedList<PotionEffect>(player.getActivePotionEffects())) {
            int id = effect.getPotionID();
            Integer cost = potionRemovalCost.get(id);
            if (cost != null) {
                cost = cost * (effect.getAmplifier() + 1);
                if (ElectricItem.manager.canUse(itemStack, cost)) {
                    ElectricItem.manager.use(itemStack, cost, null);
                    IC2.platform.removePotion(player, id);
                    ret = true;
                }
            }
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
                stack = can.onEaten(stack, worldObj, player);
                if (stack.stackSize <= 0)
                    player.inventory.mainInventory[slot] = null;
                ElectricItem.manager.use(itemStack, 1000.0D, null);
                ret = true;
            }
        } else if (player.getFoodStats().getFoodLevel() <= 0) {
            IC2.achievements.issueAchievement(player, "starveWithQHelmet");
        }
        if (this.solarType == 2 || this.solarType == 3) {
            int airLevel = player.getAir();
            if (ElectricItem.manager.canUse(itemStack, 1000.0D) && airLevel < 100) {
                player.setAir(airLevel + 200);
                ElectricItem.manager.use(itemStack, 1000.0D, null);
            }
        }

        double tempstorage = nbtData.getDouble("storage");
        if (tempstorage > 0) {
            double energyLeft = tempstorage;
            for (int i = 0; i < player.inventory.armorInventory.length; i++) {
                if (energyLeft > 0) {
                    if (player.inventory.armorInventory[i] != null
                            && player.inventory.armorInventory[i].getItem() instanceof IElectricItem) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.armorInventory[i], energyLeft,
                                2147483647, true, false);

                        if (sentPacket > 0.0D) {
                            energyLeft = (energyLeft - sentPacket);
                            nbtData.setDouble("storage", energyLeft);
                            ret = true;

                        }
                    }
                } else {
                    return;
                }
            }
            for (int j = 0; j < player.inventory.mainInventory.length; j++) {
                if (energyLeft > 0) {
                    if (player.inventory.mainInventory[j] != null
                            && player.inventory.mainInventory[j].getItem() instanceof ic2.api.item.IElectricItem) {
                        double sentPacket = ElectricItem.manager.charge(player.inventory.mainInventory[j], energyLeft,
                                2147483647, true, false);
                        if (sentPacket > 0.0D) {
                            energyLeft -= sentPacket;
                            nbtData.setDouble("storage", energyLeft);
                            ret = true;

                        }

                    }

                } else {
                    return;
                }
            }
        }
        if (ret)
            player.inventoryContainer.detectAndSendChanges();
        if (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.NIGTHVISION, itemStack)) {
            player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300));
        }
        if (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.WATER, itemStack)) {
            player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 300));
        }
    }

    public void gainFuel(EntityPlayer player) {
        if (player.worldObj.provider.getWorldTime() % 128 == 0)
            updateVisibility(player);

    }

    public void updateVisibility(EntityPlayer player) {
        boolean wetBiome = (player.worldObj.getWorldChunkManager().getBiomeGenAt((int) player.posX, (int) player.posZ)
                .getIntRainfall() > 0);
        boolean noSunWorld = player.worldObj.provider.hasNoSky;
        boolean rainWeather = (wetBiome && (player.worldObj.isRaining() || player.worldObj.isThundering()));
        this.sunIsUp = player.worldObj.isDaytime() && !rainWeather;
        this.skyIsVisible = player.worldObj.canBlockSeeTheSky((int) player.posX, (int) player.posY + 1, (int) player.posZ)
                && !noSunWorld;
    }

    public boolean isMetalArmor(final ItemStack itemstack, final EntityPlayer player) {
        return true;
    }

    public ISpecialArmor.ArmorProperties getProperties(final EntityLivingBase player, final ItemStack armor,
                                                       final DamageSource source, final double damage, final int slot) {
        if (source.isUnblockable()) {
            return new ISpecialArmor.ArmorProperties(0, 0.0, 0);
        }

        final double absorptionRatio = this.getBaseAbsorptionRatio() * this.getDamageAbsorptionRatio();
        int protect = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.PROTECTION, armor) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.PROTECTION, armor).number : 0);


        final int energyPerDamage = (int) (this.getEnergyPerDamage() - this.getEnergyPerDamage() * 0.2 * protect);
        final int damageLimit = (int) ((energyPerDamage > 0)
                ? (25.0 * ElectricItem.manager.getCharge(armor) / energyPerDamage)
                : 0.0);
        return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
    }

    public int getArmorDisplay(final EntityPlayer player, final ItemStack armor, final int slot) {
        if (ElectricItem.manager.getCharge(armor) >= this.getEnergyPerDamage()) {
            return (int) Math.round(20.0 * this.getBaseAbsorptionRatio() * this.getDamageAbsorptionRatio());
        }
        return 0;
    }

    public void damageArmor(final EntityLivingBase entity, final ItemStack stack, final DamageSource source,
                            final int damage, final int slot) {
        int protect = (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.PROTECTION, stack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.PROTECTION, stack).number : 0);


        ElectricItem.manager.discharge(stack, damage * (this.getEnergyPerDamage() - this.getEnergyPerDamage() * 0.2 * protect), Integer.MAX_VALUE, true,
                false, false);
    }

    public int getEnergyPerDamage() {
        return this.energyPerDamage;
    }

    public double getDamageAbsorptionRatio() {
        return this.damageAbsorptionRatio;
    }

    private double getBaseAbsorptionRatio() {
        return this.baseAbsorptionRatio;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(final Item item, final CreativeTabs var2, final List var3) {
        final ItemStack var4 = new ItemStack(this, 1);
        ElectricItem.manager.charge(var4, 2.147483647E9, Integer.MAX_VALUE, true, false);
        var3.add(var4);
        var3.add(new ItemStack(this, 1, this.getMaxDamage()));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        if (this.solarType == 1) {
            this.itemIcon = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "AdvSolarHelmet");
        }
        if (this.solarType == 2) {
            this.itemIcon = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "HybridSolarHelmet");
        }
        if (this.solarType == 3) {
            this.itemIcon = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "UltimateSolarHelmet");
        }
        if (this.solarType == 4) {
            this.itemIcon = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "spsolarhelmet");
        }
        if (this.solarType == 5) {
            this.itemIcon = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "sssolarhelmet");
        }
    }

    @SideOnly(Side.CLIENT)
    public String getArmorTexture(final ItemStack stack, final Entity entity, final int slot, final String type) {
        if (this.solarType == 1) {
            return Constants.TEXTURES + ":" + "textures/armor/advancedSolarHelmet.png";
        }
        if (this.solarType == 2) {
            return Constants.TEXTURES + ":" + "textures/armor/hybridSolarHelmet.png";
        }
        if (this.solarType == 3) {
            return Constants.TEXTURES + ":" + "textures/armor/ultimateSolarHelmet.png";
        }
        if (this.solarType == 4) {
            return Constants.TEXTURES + ":" + "textures/armor/spectralsolarhelmet.png";
        }
        if (this.solarType == 5) {
            return Constants.TEXTURES + ":" + "textures/armor/singularsolarhelmetoverlay.png";
        }
        return "";
    }

    public boolean canProvideEnergy(final ItemStack itemStack) {
        return false;
    }

    public Item getChargedItem(final ItemStack itemStack) {
        return this;
    }

    public Item getEmptyItem(final ItemStack itemStack) {
        return this;
    }

    public double getMaxCharge(final ItemStack itemStack) {
        return this.maxCharge;
    }

    public int getTier(final ItemStack itemStack) {
        return this.tier;
    }

    public double getTransferLimit(final ItemStack itemStack) {
        return this.transferLimit;
    }

    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List info, final boolean b) {

        NBTTagCompound nbtData1 = NBTData.getOrCreateNbtData(itemStack);

        info.add(StatCollector.translateToLocal("iu.storage.helmet") + " "
                + ModUtils.getString(nbtData1.getDouble("storage")) + " EU");

    }

    @Override
    public void setUpdate(boolean update) {

    }
}
