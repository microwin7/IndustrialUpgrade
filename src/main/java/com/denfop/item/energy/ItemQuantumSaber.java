package com.denfop.item.energy;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IBoxable;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class ItemQuantumSaber extends ItemTool implements IElectricItem, IBoxable, IItemHudInfo {
    public static final int ticker = 0;
    public static int activedamage;
    private static int damage1;
    public final int maxCharge;
    public final int transferLimit;
    public final int tier;
    private final EnumSet<ToolClass> toolClasses;
    @SideOnly(Side.CLIENT)
    private IIcon[] textures;
    private int soundTicker;

    public ItemQuantumSaber(String internalName, int maxCharge, int transferLimit, int tier, int activedamage,
                            int damage) {
        this(internalName, HarvestLevel.Diamond, maxCharge, transferLimit, tier, activedamage, damage);
    }

    public ItemQuantumSaber(String name, HarvestLevel harvestLevel, int maxCharge,
                            int transferLimit, int tier, int activedamage, int damage) {
        super(0, harvestLevel.toolMaterial, null);
        this.soundTicker = 0;
        this.toolClasses = EnumSet.of(ToolClass.Sword);
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.tier = tier;
        ItemQuantumSaber.activedamage = activedamage;
        damage1 = damage;
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
        setUnlocalizedName(name);
        setCreativeTab(IUCore.tabssp2);
        for (ToolClass toolClass : toolClasses) {
            if (toolClass.name != null)
                setHarvestLevel(toolClass.name, harvestLevel.level);
        }

        GameRegistry.registerItem(this, name);
    }

    public static void drainSaber(ItemStack itemStack, double amount, EntityLivingBase entity) {
        NBTTagCompound nbt = ModUtils.nbt(itemStack);
        int saberenergy = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("saberenergy")) {
                saberenergy++;
            }

        }
        saberenergy = Math.min(saberenergy, EnumInfoUpgradeModules.SABERENERGY.max);

        if (!ElectricItem.manager.use(itemStack, amount - amount * 0.15 * saberenergy, entity)) {
            NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
            nbtData.setBoolean("active", false);
            updateAttributes(nbtData);
        }
    }

    private static void updateAttributes(NBTTagCompound nbtData) {
        boolean active = nbtData.getBoolean("active");

        int saberdamage = 0;
        for (int i = 0; i < 4; i++) {
            if (nbtData.getString("mode_module" + i).equals("saberdamage")) {
                saberdamage++;
            }

        }
        saberdamage = Math.min(saberdamage, EnumInfoUpgradeModules.SABER_DAMAGE.max);


        int damage = (int) (damage1 + damage1 * 0.15 * saberdamage);
        if (active)
            damage = (int) (activedamage + activedamage * 0.15 * saberdamage);
        NBTTagCompound entry = new NBTTagCompound();
        entry.setString("AttributeName", SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName());
        entry.setLong("UUIDMost", field_111210_e.getMostSignificantBits());
        entry.setLong("UUIDLeast", field_111210_e.getLeastSignificantBits());
        entry.setString("Name", "Tool modifier");
        entry.setDouble("Amount", damage);
        entry.setInteger("Operation", 0);
        NBTTagList list = new NBTTagList();
        list.appendTag(entry);
        nbtData.setTag("AttributeModifiers", list);
    }

    public void addInformation(final ItemStack itemStack, final EntityPlayer player, final List info, final boolean b) {
        info.add(StatCollector.translateToLocal("iu.spectralsaberactive") + activedamage);

        info.add(StatCollector.translateToLocal("ic2.item.tooltip.PowerTier") + " " + tier);
        info.add(StatCollector.translateToLocal("iu.maxCharge") + maxCharge + " EU");
        info.add(StatCollector.translateToLocal("iu.transferLimit") + transferLimit);
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            info.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            info.add(StatCollector.translateToLocal("iu.saberon"));

        }
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[2];
        this.textures[0] = iconRegister
                .registerIcon(Constants.TEXTURES + ":" + getUnlocalizedName().substring(3) + "." + "off");
        this.textures[1] = iconRegister
                .registerIcon(Constants.TEXTURES + ":" + getUnlocalizedName().substring(3) + "." + "active");
    }

    @Override
    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public int getItemEnchantability() {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List subs) {
        ItemStack stack = new ItemStack(this, 1);

        ElectricItem.manager.charge(stack, 2.147483647E9D, 2147483647, true, false);
        subs.add(stack);
        ItemStack itemstack = new ItemStack(this, 1, getMaxDamage());

        subs.add(itemstack);
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1) {
        return EnumRarity.uncommon;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
        if (nbtData.getBoolean("active"))
            return this.textures[1];
        return this.textures[0];
    }

    @Override
    public float getDigSpeed(ItemStack itemStack, Block block, int meta) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
        if (nbtData.getBoolean("active")) {
            this.soundTicker++;
            if (this.soundTicker % 4 == 0)
                IC2.platform.playSoundSp(getRandomSwingSound(), 1.0F, 1.0F);
            return 4.0F;
        }
        return 1.0F;
    }

    @Override
    public Multimap<Object, Object> getAttributeModifiers(ItemStack stack) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int saberdamage = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("saberdamage")) {
                saberdamage++;
            }

        }
        saberdamage = Math.min(saberdamage, EnumInfoUpgradeModules.SABER_DAMAGE.max);

        int dmg = (int) (damage1 + damage1 * 0.15 * saberdamage);
        if (ElectricItem.manager.canUse(stack, 400.0D)) {
            NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
            if (nbtData.getBoolean("active"))
                dmg = (int) (activedamage + activedamage * 0.15 * saberdamage);
        }
        HashMultimap<Object, Object> hashMultimap = HashMultimap.create();
        hashMultimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(),
                new AttributeModifier(field_111210_e, "Tool modifier", dmg, 0));
        return hashMultimap;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase source) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(stack);
        if (!nbtData.getBoolean("active"))
            return true;
        if (IC2.platform.isSimulating()) {
            drainSaber(stack, 400.0D, source);
            NBTTagCompound nbt = ModUtils.nbt(stack);
            int vampires = 0;
            boolean wither = false;
            boolean poison = false;
            for (int i = 0; i < 4; i++) {
                if (nbt.getString("mode_module" + i).equals("vampires")) {
                    vampires++;
                }
                if (nbt.getString("mode_module" + i).equals("wither")) {
                    wither = true;
                }
                if (nbt.getString("mode_module" + i).equals("poison")) {
                    poison = true;
                }
            }
            vampires = Math.min(vampires, EnumInfoUpgradeModules.VAMPIRES.max);
            if (vampires != 0)
                target.addPotionEffect(new PotionEffect(Potion.regeneration.id, 40, vampires));
            if (wither)
                target.addPotionEffect(new PotionEffect(Potion.wither.id, 60));
            if (poison)
                target.addPotionEffect(new PotionEffect(Potion.poison.id, 60));


            if (!(source instanceof EntityPlayer) || MinecraftServer.getServer().isPVPEnabled()
                    || !(target instanceof EntityPlayer))
                for (int i = 0; i < 4 && ElectricItem.manager.canUse(stack, 2000.0D); i++) {
                    ItemStack armor = target.getEquipmentInSlot(i + 1);
                    if (armor != null) {
                        double amount = 0.0D;
                        if (armor.getItem() instanceof ic2.core.item.armor.ItemArmorNanoSuit) {
                            amount = 48000.0D;
                        } else if (armor.getItem() instanceof ic2.core.item.armor.ItemArmorQuantumSuit) {
                            amount = 300000.0D;
                        }
                        if (amount > 0.0D) {
                            ElectricItem.manager.discharge(armor, amount, this.tier, true, false, false);
                            if (!ElectricItem.manager.canUse(armor, 1.0D))
                                target.setCurrentItemOrArmor(i + 1, null);
                            drainSaber(stack, 2000.0D, source);
                        }
                    }
                }
        }
        if (IC2.platform.isRendering())
            IC2.platform.playSoundSp(getRandomSwingSound(), 1.0F, 1.0F);
        return true;
    }

    public String getRandomSwingSound() {
        switch (IC2.random.nextInt(3)) {
            default:
                return "Tools/Nanosabre/NanosabreSwing1.ogg";
            case 1:
                return "Tools/Nanosabre/NanosabreSwing2.ogg";
            case 2:
                break;
        }
        return "Tools/Nanosabre/NanosabreSwing3.ogg";
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemStack, int i, int j, int k, EntityPlayer player) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
        if (nbtData.getBoolean("active"))
            drainSaber(itemStack, 80.0D, player);
        return false;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (!IC2.platform.isSimulating())
            return itemStack;
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
        if (nbtData.getBoolean("active")) {
            nbtData.setBoolean("active", false);
            updateAttributes(nbtData);
        } else if (ElectricItem.manager.canUse(itemStack, 16.0D)) {
            nbtData.setBoolean("active", true);
            updateAttributes(nbtData);
            IC2.platform.playSoundSp("Tools/Nanosabre/NanosabrePowerup.ogg", 1.0F, 1.0F);
        }
        return super.onItemRightClick(itemStack, world, entityplayer);
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);
        if (!nbtData.getBoolean("active"))
            return;
        int loot = 0;
        int fire = 0;
        for (int i = 0; i < 4; i++) {
            if (nbtData.getString("mode_module" + i).equals("loot")) {
                loot++;
            }
            if (nbtData.getString("mode_module" + i).equals("fire")) {
                fire++;
            }

        }
        loot = Math.min(loot, EnumInfoUpgradeModules.LOOT.max);
        fire = Math.min(fire, EnumInfoUpgradeModules.FIRE.max);
        Map<Integer, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(itemStack);
        if (loot != 0)
            enchantmentMap.put(Enchantment.looting.effectId, loot);
        if (fire != 0)
            enchantmentMap.put(Enchantment.fireAspect.effectId, fire);
        EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
        if (ticker % 16 == 0 && entity instanceof net.minecraft.entity.player.EntityPlayerMP)
            if (slot < 9) {
                drainSaber(itemStack, 64.0D, (EntityLivingBase) entity);
            } else if (ticker % 64 == 0) {
                drainSaber(itemStack, 16.0D, (EntityLivingBase) entity);
            }
    }

    @Override
    public String getUnlocalizedName() {
        return "iu" + super.getUnlocalizedName().substring(4);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }

    @Override
    public List<String> getHudInfo(ItemStack itemStack) {
        List<String> info = new LinkedList<>();
        info.add(ElectricItem.manager.getToolTip(itemStack));
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.PowerTier") + " " + this.tier);
        return info;
    }

    @Override
    public int getTier(ItemStack itemStack) {

        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {

        return this.transferLimit;
    }

    @Override
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        Material material = block.getMaterial();
        for (ToolClass toolClass : this.toolClasses) {
            if (toolClass.whitelist.contains(block) || toolClass.whitelist.contains(material))
                return true;
        }
        return super.canHarvestBlock(block, stack);
    }

    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }

    @Override
    public float func_150893_a(ItemStack stack, Block block) {
        if (canHarvestBlock(block, stack))
            return this.efficiencyOnProperMaterial;
        return super.func_150893_a(stack, block);
    }

    public boolean isRepairable() {
        return false;
    }

    protected enum HarvestLevel {
        Diamond();

        public final int level;

        public final Item.ToolMaterial toolMaterial;

        HarvestLevel() {
            this.level = 3;
            this.toolMaterial = ToolMaterial.EMERALD;
        }
    }

    protected enum ToolClass {
        Sword(Blocks.web,
                Material.plants, Material.vine, Material.coral, Material.leaves, Material.gourd);

        public final String name;

        public final Set<Object> whitelist;

        ToolClass(Object... whitelist) {
            this.name = "sword";
            this.whitelist = new HashSet<>(Arrays.asList(whitelist));
        }
    }

}
