package com.denfop.events;


import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.block.base.BlockIUFluid;
import com.denfop.block.base.BlocksItems;
import com.denfop.item.ItemBucket;
import com.denfop.item.armour.ItemArmorImprovemedNano;
import com.denfop.item.armour.ItemArmorImprovemedQuantum;
import com.denfop.item.armour.ItemSolarPanelHelmet;
import com.denfop.item.energy.*;
import com.denfop.item.modules.EnumModule;
import com.denfop.item.modules.EnumType;
import com.denfop.item.modules.ItemEntityModule;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fluids.Fluid;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class IUEventHandler {
    final EnumChatFormatting[] name = {EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.BLUE, EnumChatFormatting.RED, EnumChatFormatting.GRAY, EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.AQUA};
    final String[] mattertype = {"matter.name", "sun_matter.name", "aqua_matter.name", "nether_matter.name", "night_matter.name", "earth_matter.name", "end_matter.name", "aer_matter.name"};

    public IUEventHandler() {
    }

    public static boolean getUpgradeItem(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof EnergyAxe
                || item instanceof EnergyDrill
                || item instanceof AdvancedMultiTool
                || item instanceof EnergyPickaxe
                || item instanceof EnergyShovel
                || item instanceof ItemSolarPanelHelmet
                || item instanceof ItemArmorImprovemedQuantum
                || item instanceof ItemArmorImprovemedNano
                || item instanceof EnergyBow
                || item instanceof ItemQuantumSaber
                || item instanceof ItemSpectralSaber
                ;

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onViewRenderFogDensity(EntityViewRenderEvent.FogDensity event) {
        if (!(event.block instanceof BlockIUFluid))
            return;
        event.setCanceled(true);
        Fluid fluid = ((BlockIUFluid) event.block).getFluid();
        GL11.glFogi(2917, 2048);
        event.density = (float) Util.map(Math.abs(fluid.getDensity()), 20000.0D, 2.0D);
    }

    @SubscribeEvent
    public void onCrafting(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.isItemEqual(Ic2Items.toolbox))
            event.crafting.setItemDamage(5);

    }

    @SubscribeEvent
    public void addInfoforItem(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        Item item = stack.getItem();

        if (item.equals(IUItem.module8)) {
            event.toolTip.add(StatCollector.translateToLocal("module.wireless"));
        }
        if (item.equals(IUItem.module_quickly) || item.equals(IUItem.module_stack) || (item.equals(IUItem.module7) && stack.getItemDamage() == 4)) {
            event.toolTip.add(StatCollector.translateToLocal("module.wireless"));
        }

    }

    @SubscribeEvent
    public void addInfo(ItemTooltipEvent event) {
        ItemStack stack = event.itemStack;
        RecipeOutput output = Recipes.matterrecipe.getOutputFor(stack, false);
        if (stack.getItem().equals(IUItem.plast)) {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                event.toolTip.add(StatCollector.translateToLocal("press.lshift"));


            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {

                event.toolTip.add(StatCollector.translateToLocal("iu.create_plastic"));
            }
        }
        if (stack.getItem().equals(IUItem.analyzermodule))
            event.toolTip.add(StatCollector.translateToLocal("iu.analyzermodule"));
        if (stack.getItem().equals(IUItem.quarrymodule))
            event.toolTip.add(StatCollector.translateToLocal("iu.quarrymodule"));
        if (stack.getItem() instanceof ItemEntityModule) {
            int meta = stack.getItemDamage();
            if (meta == 0)
                event.toolTip.add(StatCollector.translateToLocal("iu.entitymodule"));
            if (meta == 1)
                event.toolTip.add(StatCollector.translateToLocal("iu.entitymodule1"));

        }

        if (IUItem.modules.containsKey(stack.getItem())) {
            EnumModule module = IUItem.modules.get(stack.getItem());
            if (module.type.equals(EnumType.PHASE)) {
                event.toolTip.add(StatCollector.translateToLocal("iu.phasemodule"));

            }
            if (module.type.equals(EnumType.MOON_LINSE)) {
                event.toolTip.add(StatCollector.translateToLocal("iu.moonlinse"));

            }

        }
        if (stack.getItem().equals(IUItem.plastic_plate)) {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                event.toolTip.add(StatCollector.translateToLocal("press.lshift"));
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                event.toolTip.add(StatCollector.translateToLocal("iu.create_plastic_plate"));
            }
        }
        if (stack.isItemEqual(new ItemStack(IUItem.sunnarium, 1, 4))) {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                event.toolTip.add(StatCollector.translateToLocal("press.lshift"));
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                event.toolTip.add(StatCollector.translateToLocal("iu.create_sunnarium"));
            }
        }
        if (output != null) {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                event.toolTip.add(StatCollector.translateToLocal("press.lshift"));
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                event.toolTip.add(StatCollector.translateToLocal("clonning"));
                for (int i = 0; i < this.name.length; i++) {
                    if (output.metadata.getDouble(("quantitysolid_" + i)) != 0) {
                        event.toolTip.add(name[i] + StatCollector.translateToLocal(mattertype[i]) + ": " + output.metadata.getDouble(("quantitysolid_" + i)) + StatCollector.translateToLocal("matternumber"));
                    }
                }
            }
        }
        if (getUpgradeItem(stack)) {
            NBTTagCompound nbt = ModUtils.nbt(stack);
            int aoe = 0;
            int energy = 0;
            int speed = 0;
            int depth = 0;
            int gennight = 0;
            int genday = 0;
            int storage = 0;
            int protect = 0;
            int free_slot = 0;
            int speedfly = 0;
            boolean moveSpeed = false;
            boolean jump = false;
            boolean fireResistance = false;
            boolean waterBreathing = false;
            int bowdamage = 0;
            int bowenergy = 0;
            int saberdamage = 0;
            int saberenergy = 0;

            int vampires = 0;
            int resistance = 0;
            int poison = 0;
            int wither = 0;
            int loot = 0;
            int fire = 0;

            int repaired = 0;
            boolean silk = false;
            boolean invisibility = false;
            for (int i = 0; i < 4; i++) {
                if (nbt.getString("mode_module" + i).equals("repaired")) {
                    repaired++;
                }
                if (nbt.getString("mode_module" + i).equals("loot")) {
                    loot++;
                }
                if (nbt.getString("mode_module" + i).equals("fire")) {
                    fire++;
                }

                if (nbt.getString("mode_module" + i).equals("saberenergy")) {
                    saberenergy++;
                }
                if (nbt.getString("mode_module" + i).equals("saberenergy")) {
                    saberenergy++;
                }
                if (nbt.getString("mode_module" + i).equals("invisibility")) {
                    invisibility = true;
                }
                if (nbt.getString("mode_module" + i).equals("silk")) {
                    silk = true;
                }
                if (nbt.getString("mode_module" + i).equals("poison")) {
                    poison++;
                }
                if (nbt.getString("mode_module" + i).equals("wither")) {
                    wither++;
                }
                if (nbt.getString("mode_module" + i).equals("vampires")) {
                    vampires++;
                }
                if (nbt.getString("mode_module" + i).equals("resistance")) {
                    resistance++;
                }
                if (nbt.getString("mode_module" + i).equals("saberdamage")) {
                    saberdamage++;
                }
                if (nbt.getString("mode_module" + i).equals("bowdamage")) {
                    bowdamage++;
                }
                if (nbt.getString("mode_module" + i).equals("bowenergy")) {
                    bowenergy++;
                }
                if (nbt.getString("mode_module" + i).equals("moveSpeed")) {
                    moveSpeed = true;
                }
                if (nbt.getString("mode_module" + i).equals("flyspeed")) {
                    speedfly++;
                }

                if (nbt.getString("mode_module" + i).equals("jump")) {
                    jump = true;
                }
                if (nbt.getString("mode_module" + i).equals("fireResistance")) {
                    fireResistance = true;
                }
                if (nbt.getString("mode_module" + i).equals("waterBreathing")) {
                    waterBreathing = true;
                }
                if (nbt.getString("mode_module" + i).equals("energy")) {
                    energy++;
                }
                if (nbt.getString("mode_module" + i).isEmpty()) {
                    free_slot++;
                }
                if (nbt.getString("mode_module" + i).equals("dig_depth")) {
                    depth++;
                }
                if (nbt.getString("mode_module" + i).equals("AOE_dig")) {
                    aoe++;
                }
                if (nbt.getString("mode_module" + i).equals("speed")) {
                    speed++;
                }
                if (nbt.getString("mode_module" + i).equals("genday")) {
                    genday++;
                }
                if (nbt.getString("mode_module" + i).equals("gennight")) {
                    gennight++;
                }
                if (nbt.getString("mode_module" + i).equals("storage")) {
                    storage++;
                }
                if (nbt.getString("mode_module" + i).equals("protect")) {
                    protect++;
                }
            }

            if (free_slot != 0) {
                event.toolTip.add(StatCollector.translateToLocal("free_slot") + free_slot + StatCollector.translateToLocal("free_slot1"));
            } else {
                event.toolTip.add(StatCollector.translateToLocal("not_free_slot"));

            }
            if (saberenergy != 0)
                event.toolTip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("saberenergy") + EnumChatFormatting.GREEN + ModUtils.getString(0.15 * saberenergy * 100) + "%");


            if (vampires != 0)
                event.toolTip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("vampires") + EnumChatFormatting.GREEN + ModUtils.getString(vampires));
            if (resistance != 0)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("resistance") + EnumChatFormatting.GREEN + ModUtils.getString(resistance));
            if (wither != 0)
                event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("wither"));
            if (poison != 0)
                event.toolTip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("poison"));
            if (invisibility)
                event.toolTip.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("invisibility"));
            if (repaired != 0)
                event.toolTip.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("repaired") + EnumChatFormatting.GREEN + 0.001 * repaired + "%");
            if (loot != 0)
                event.toolTip.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("loot") + EnumChatFormatting.GREEN + ModUtils.getString(loot));
            if (fire != 0)
                event.toolTip.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("fire") + EnumChatFormatting.GREEN + ModUtils.getString(fire));
            if (silk)
                event.toolTip.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("silk"));


            if (saberdamage != 0)
                event.toolTip.add(EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("saberdamage") + EnumChatFormatting.GREEN + ModUtils.getString(0.15 * saberdamage * 100) + "%");


            if (bowenergy != 0)
                event.toolTip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("bowenergy") + EnumChatFormatting.GREEN + ModUtils.getString(0.1 * bowenergy * 100) + "%");


            if (bowdamage != 0)
                event.toolTip.add(EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("bowdamage") + EnumChatFormatting.GREEN + ModUtils.getString((0.25 * bowdamage) * 100) + "%");


            if (speedfly != 0)
                event.toolTip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("speedfly") + EnumChatFormatting.GREEN + ModUtils.getString((0.1 * speedfly / 0.2) * 100) + "%");


            if (waterBreathing)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("waterBreathing"));

            if (fireResistance)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("fireResistance"));

            if (jump)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("jump"));

            if (moveSpeed)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("moveSpeed"));

            if (energy != 0)
                event.toolTip.add(EnumChatFormatting.RED + StatCollector.translateToLocal("energy_less_use") + EnumChatFormatting.GREEN + ModUtils.getString(0.25 * energy * 100) + "%");

            if (depth != 0)
                event.toolTip.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("depth") + EnumChatFormatting.GREEN + depth);

            if (aoe != 0)
                event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("aoe") + EnumChatFormatting.GREEN + aoe);

            if (speed != 0)
                event.toolTip.add(EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("speed") + EnumChatFormatting.GREEN + ModUtils.getString(0.2 * speed * 100) + "%");


            if (genday != 0)
                event.toolTip.add(EnumChatFormatting.YELLOW + StatCollector.translateToLocal("genday") + EnumChatFormatting.GREEN + ModUtils.getString(0.05 * genday * 100) + "%");

            if (gennight != 0)
                event.toolTip.add(EnumChatFormatting.AQUA + StatCollector.translateToLocal("gennight") + EnumChatFormatting.GREEN + ModUtils.getString(0.05 * gennight * 100) + "%");

            if (storage != 0)
                event.toolTip.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("storage") + EnumChatFormatting.GREEN + ModUtils.getString(0.05 * storage * 100) + "%");

            if (protect != 0)
                event.toolTip.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("protect") + EnumChatFormatting.GREEN + ModUtils.getString(0.2 * protect * 100) + "%");

        }
    }

    @SubscribeEvent
    public void setprogram(PlayerEvent.ItemCraftedEvent event) {
        if (event.crafting.getItem().equals(IUItem.lathingprogram) && event.crafting != null) {
            ItemStack stack = event.crafting;
            for (int i = 0; i < event.craftMatrix.getSizeInventory(); i++) {
                if (event.craftMatrix.getStackInSlot(i) != null && event.craftMatrix.getStackInSlot(i).getItemDamage() == 344865) {
                    NBTTagCompound tag = ModUtils.nbt(stack);
                    int[] ret = {5, 4, 3, 2, 1};
                    for (int j = 0; j < 5; ++j)
                        tag.setInteger("l" + j, ret[j]);

                    stack.setTagCompound(tag);
                    break;
                }
                if (event.craftMatrix.getStackInSlot(i) != null && event.craftMatrix.getStackInSlot(i).getItemDamage() == 275508) {
                    NBTTagCompound tag = ModUtils.nbt(stack);
                    int[] ret = {4, 3, 4, 3, 4};
                    for (int j = 0; j < 5; ++j)
                        tag.setInteger("l" + j, ret[j]);

                    stack.setTagCompound(tag);
                    break;
                }
                if (event.craftMatrix.getStackInSlot(i) != null && event.craftMatrix.getStackInSlot(i).getItemDamage() == 274978) {
                    NBTTagCompound tag = ModUtils.nbt(stack);
                    int[] ret = {4, 3, 2, 2, 2};
                    for (int j = 0; j < 5; ++j)
                        tag.setInteger("l" + j, ret[j]);

                    stack.setTagCompound(tag);
                    break;
                }
            }


        }
    }

    @SubscribeEvent
    public void getBucket(PlayerInteractEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        if (player.getHeldItem() == null || player.getHeldItem().getItem() != Items.bucket) {
            World world = event.world;
            int x = event.x;
            int y = event.y;
            int z = event.z;
            if (world.getBlock(x, y, z) instanceof BlockIUFluid) {
                Block block = world.getBlock(x, y, z);
                String name = block.getUnlocalizedName();

                Fluid fluid = BlocksItems.getFluid(name);
                name = fluid.getName().substring(name.indexOf("fluid"));

                if (ItemBucket.itemNames.contains(("itemCell" + name))) {

                    int meta = 0;
                    for (int i = 0; i < ItemBucket.itemNames.size(); i++) {
                        if (ItemBucket.itemNames.get(i).equals(("itemCell" + name))) {

                            meta = i;
                            break;
                        }
                    }
                    ItemStack stack1 = new ItemStack(IUItem.cell.getItem(), 1, meta);
                    if (player.inventory.addItemStackToInventory(stack1)) {
                        player.getHeldItem().stackSize--;
                        world.setBlockToAir(x, y, z);
                        player.inventoryContainer.detectAndSendChanges();

                    }
                }
            }
        }
    }


    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onViewRenderFogColors(EntityViewRenderEvent.FogColors event) {
        if (!(event.block instanceof BlockIUFluid))
            return;
        int color = ((BlockIUFluid) event.block).getColor();
        event.red = (color >>> 16 & 0xFF) / 255.0F;
        event.green = (color >>> 8 & 0xFF) / 255.0F;
        event.blue = (color & 0xFF) / 255.0F;
    }

    @SubscribeEvent
    public void FlyUpdate(LivingEvent.LivingUpdateEvent event) {

        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;

        if (!player.capabilities.isCreativeMode) {

            NBTTagCompound nbtData = player.getEntityData();
            if (!player.capabilities.isCreativeMode) {
                if (player.inventory.armorInventory[2] != null) {
                    if (player.inventory.armorInventory[2].getItem() == IUItem.quantumBodyarmor || player.inventory.armorInventory[2].getItem() == IUItem.NanoBodyarmor || player.inventory.armorInventory[2].getItem() == IUItem.perjetpack) {
                        NBTTagCompound nbtData1 = NBTData.getOrCreateNbtData(player.inventory.armorInventory[2]);
                        boolean jetpack = nbtData1.getBoolean("jetpack");
                        if (!jetpack) {
                            if (nbtData.getBoolean("isFlyActive")) {
                                player.capabilities.isFlying = false;
                                player.capabilities.allowFlying = false;
                                nbtData1.setBoolean("isFlyActive", false);
                                nbtData.setBoolean("isFlyActive", false);
                                if (player.getEntityWorld().isRemote)
                                    player.capabilities.setFlySpeed((float) 0.05);
                            }
                        } else {
                            player.capabilities.isFlying = true;
                            player.capabilities.allowFlying = true;
                            nbtData.setBoolean("isFlyActive", true);
                            nbtData1.setBoolean("isFlyActive", true);
                            int flyspeed = 0;
                            for (int i = 0; i < 4; i++) {
                                if (nbtData1.getString("mode_module" + i).equals("flyspeed")) {
                                    flyspeed++;
                                }

                            }
                            flyspeed = Math.min(flyspeed, EnumInfoUpgradeModules.FLYSPEED.max);

                            if (player.getEntityWorld().isRemote)
                                player.capabilities.setFlySpeed((float) ((float) 0.2 + 0.1 * flyspeed));
                            if (Config.DimensionidList.contains(player.getEntityWorld().provider.dimensionId)) {
                                if (nbtData.getBoolean("isFlyActive")) {
                                    player.capabilities.isFlying = false;
                                    player.capabilities.allowFlying = false;
                                    nbtData1.setBoolean("isFlyActive", false);
                                    nbtData.setBoolean("isFlyActive", false);
                                    nbtData1.setBoolean("jetpack", false);
                                    if (player.getEntityWorld().isRemote)
                                        player.capabilities.setFlySpeed((float) 0.05);
                                }
                            }
                        }
                    } else if (player.inventory.armorInventory[2].getItem() != IUItem.quantumBodyarmor && player.inventory.armorInventory[2].getItem() != IUItem.NanoBodyarmor && player.inventory.armorInventory[2].getItem() != IUItem.perjetpack
                            && player.inventory.armorInventory[2] != null) {
                        if (nbtData.getBoolean("isFlyActive")) {
                            player.capabilities.isFlying = false;
                            player.capabilities.allowFlying = false;
                            nbtData.setBoolean("isFlyActive", false);
                            if (player.getEntityWorld().isRemote)
                                player.capabilities.setFlySpeed((float) 0.05);
                        }
                    }
                } else {
                    if (nbtData.getBoolean("isFlyActive")) {
                        player.capabilities.isFlying = false;
                        player.capabilities.allowFlying = false;
                        nbtData.setBoolean("isFlyActive", false);
                        if (player.getEntityWorld().isRemote)
                            player.capabilities.setFlySpeed((float) 0.05);
                    }
                }
            } else {
                if (nbtData.getBoolean("isFlyActive")) {
                    player.capabilities.isFlying = false;
                    player.capabilities.allowFlying = false;
                    nbtData.setBoolean("isFlyActive", false);
                    if (player.getEntityWorld().isRemote)
                        player.capabilities.setFlySpeed((float) 0.05);
                }
            }
        }

    }

    @SubscribeEvent
    public void fix_streak(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        NBTTagCompound nbtData = player.getEntityData();

        if (player.inventory.armorInventory[2] == null || !(player.inventory.armorInventory[2].getItem() instanceof ItemArmorImprovemedQuantum))
            return;
        ItemStack stack = player.inventory.armorInventory[2];
        NBTTagCompound nbt = ModUtils.nbt(stack);
        if (nbtData.getDouble("Red") != 0 && nbtData.getDouble("Green") != 0 && nbtData.getDouble("Blue") != 0) {
            if (nbt.getDouble("Red") == 0 && nbt.getDouble("Green") == 0 && nbt.getDouble("Blue") == 0) {

                nbt.setDouble("Red", nbtData.getDouble("Red"));
                nbt.setDouble("Green", nbtData.getDouble("Green"));
                nbt.setDouble("Blue", nbtData.getDouble("Blue"));
                nbt.setBoolean("RGB", nbtData.getBoolean("RGB"));
                stack.setTagCompound(nbt);
            }
        } else {
            if (nbt.getDouble("Red") != 0 && nbt.getDouble("Green") != 0 && nbt.getDouble("Blue") != 0) {
                nbtData.setDouble("Red", nbt.getDouble("Red"));
                nbtData.setDouble("Green", nbt.getDouble("Green"));
                nbtData.setDouble("Blue", nbt.getDouble("Blue"));
                nbtData.setBoolean("RGB", nbt.getBoolean("RGB"));

            }
        }

    }

    @SubscribeEvent
    public void UpdateHelmet(LivingEvent.LivingUpdateEvent event) {

        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData1(player);
        if (player.inventory.armorInventory[3] != null) {
            if (player.inventory.armorInventory[3].getItem() == IUItem.quantumHelmet) {
                nbtData.setBoolean("isNightVision", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.NanoHelmet) {
                nbtData.setBoolean("isNightVision", true);
            } else if (player.inventory.armorInventory[3].getItem() == Ic2Items.nanoHelmet.getItem()) {
                nbtData.setBoolean("isNightVision", true);
            } else if (player.inventory.armorInventory[3].getItem() == Ic2Items.quantumHelmet.getItem()) {
                nbtData.setBoolean("isNightVision", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.advancedSolarHelmet) {
                nbtData.setBoolean("isNightVision", true);
                nbtData.setBoolean("isNightVisionEnable", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.hybridSolarHelmet) {
                nbtData.setBoolean("isNightVision", true);
                nbtData.setBoolean("isNightVisionEnable", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.spectralSolarHelmet) {
                nbtData.setBoolean("isNightVision", true);
                nbtData.setBoolean("isNightVisionEnable", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.singularSolarHelmet) {
                nbtData.setBoolean("isNightVision", true);
                nbtData.setBoolean("isNightVisionEnable", true);
            } else if (player.inventory.armorInventory[3].getItem() == Ic2Items.nightvisionGoggles.getItem()) {
                nbtData.setBoolean("isNightVision", true);
            } else if (player.inventory.armorInventory[3].getItem() == IUItem.ultimateSolarHelmet) {
                nbtData.setBoolean("isNightVision", true);
                nbtData.setBoolean("isNightVisionEnable", true);
            } else if (nbtData.getBoolean("isNightVision")) {
                nbtData.setBoolean("isNightVision", false);
                nbtData.setBoolean("isNightVisionEnable", false);

            }
        } else if (nbtData.getBoolean("isNightVision")) {
            nbtData.setBoolean("isNightVision", false);
            nbtData.setBoolean("isNightVisionEnable", false);

        }
    }

    @SubscribeEvent
    public void UpdateNightVision(LivingEvent.LivingUpdateEvent event) {
        if (Config.nightvision) {
            if (!(event.entityLiving instanceof EntityPlayer))
                return;
            EntityPlayer player = (EntityPlayer) event.entity;
            int x = MathHelper.floor_double(player.posX);
            int z = MathHelper.floor_double(player.posZ);
            int y = MathHelper.floor_double(player.posY);
            int skylight = player.worldObj.getBlockLightValue(x, y, z);
            NBTTagCompound nbtData = NBTData.getOrCreateNbtData1(player);
            if (nbtData.getBoolean("isNightVision")) {
                if (player.posY < 60 || skylight < 8 || !player.worldObj.isDaytime()) {
                    player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 300, 0, true));
                }

            }

        }
    }

    //
    @SubscribeEvent
    public void checkinstruments(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;

        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            // TODO start Check inventory
            if (player.inventory.mainInventory[i] != null
                    && (player.inventory.mainInventory[i].getItem() instanceof EnergyAxe
                    || player.inventory.mainInventory[i].getItem() instanceof EnergyPickaxe
                    || player.inventory.mainInventory[i].getItem() instanceof EnergyShovel)) {
                ItemStack input = player.inventory.mainInventory[i];
                NBTTagCompound nbtData = NBTData.getOrCreateNbtData(input);
                if (nbtData.getBoolean("create")) {
                    Map<Integer, Integer> enchantmentMap4 = new HashMap<>();

                    if (input.getItem() instanceof EnergyAxe) {
                        EnergyAxe drill = (EnergyAxe) input.getItem();
                        if (Config.enableefficiency) {
                            enchantmentMap4.put(Enchantment.efficiency.effectId,
                                    drill.efficienty);
                            enchantmentMap4.put(Enchantment.fortune.effectId,
                                    drill.lucky);
                            nbtData.setBoolean("create", false);
                            EnchantmentHelper.setEnchantments(enchantmentMap4, input);
                        }
                    } else if (input.getItem() instanceof EnergyPickaxe) {
                        EnergyPickaxe drill = (EnergyPickaxe) input.getItem();
                        if (Config.enableefficiency) {
                            enchantmentMap4.put(Enchantment.efficiency.effectId,
                                    drill.efficienty);
                            enchantmentMap4.put(Enchantment.fortune.effectId,
                                    drill.lucky);
                            nbtData.setBoolean("create", false);
                            EnchantmentHelper.setEnchantments(enchantmentMap4, input);
                        }
                    } else if (input.getItem() instanceof EnergyShovel) {
                        EnergyShovel drill = (EnergyShovel) input.getItem();
                        if (Config.enableefficiency) {
                            enchantmentMap4.put(Enchantment.efficiency.effectId,
                                    drill.efficienty);
                            enchantmentMap4.put(Enchantment.fortune.effectId,
                                    drill.lucky);
                            nbtData.setBoolean("create", false);
                            EnchantmentHelper.setEnchantments(enchantmentMap4, input);
                        }
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public void checkdrill(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            // TODO start Check inventory
            if (player.inventory.mainInventory[i] != null
                    && (player.inventory.mainInventory[i].getItem() == IUItem.ultDDrill)) {
                ItemStack input = player.inventory.mainInventory[i];
                NBTTagCompound nbtData = NBTData.getOrCreateNbtData(input);
                if (nbtData.getBoolean("create")) {
                    Map<Integer, Integer> enchantmentMap4 = new HashMap<>();
                    if (Config.enableefficiency) {
                        enchantmentMap4.put(Enchantment.efficiency.effectId,
                                Config.efficiencylevel);
                        nbtData.setBoolean("create", false);
                        EnchantmentHelper.setEnchantments(enchantmentMap4, input);
                    }

                }
            }
        }
    }

    @SubscribeEvent
    public void check(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        for (int i = 0; i < 36; i++) {
            if (player.inventory.mainInventory[i] != null
                    && player.inventory.mainInventory[i].getItem() instanceof ic2.core.item.block.ItemElectricBlock) {
                int meta = player.inventory.mainInventory[i].getItemDamage();
                if (meta == 0) {
                    player.inventory.mainInventory[i] = new ItemStack(IUItem.electricblock, 1, 2);
                }
                if (meta == 7) {
                    player.inventory.mainInventory[i] = new ItemStack(IUItem.electricblock, 1, 5);
                }
                if (meta == 1) {
                    player.inventory.mainInventory[i] = new ItemStack(IUItem.electricblock, 1, 3);
                }
                if (meta == 2) {
                    player.inventory.mainInventory[i] = new ItemStack(IUItem.electricblock, 1, 4);
                }
            }
            if (player.inventory.mainInventory[i] != null
                    && player.inventory.mainInventory[i].isItemEqual(Ic2Items.toolbox))
                player.inventory.mainInventory[i].setItemDamage(5);
            if (player.inventory.mainInventory[i] != null
                    && player.inventory.mainInventory[i].getItem() instanceof ic2.core.item.block.ItemChargepadBlock) {
                int meta = player.inventory.mainInventory[i].getItemDamage();

                player.inventory.mainInventory[i] = new ItemStack(IUItem.Chargepadelectricblock, 1, meta + 2);

            }
        }
    }


    @SubscribeEvent
    public void Potion(LivingEvent.LivingUpdateEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData1(player);
        if (player.inventory.armorInventory[0] != null
                && player.inventory.armorInventory[0].getItem() == IUItem.quantumBoots) {
            nbtData.setBoolean("stepHeight", true);
            player.stepHeight = 1.0F;

            nbtData.setBoolean("falldamage", true);
            player.fallDistance = 0;


        } else {
            if (nbtData.getBoolean("stepHeight")) {
                player.stepHeight = 0.5F;
                nbtData.setBoolean("stepHeight", false);
            }
            if (nbtData.getBoolean("falldamage")) {
                player.fallDistance = 1;
                nbtData.setBoolean("falldamage", false);
            }

        }
    }

    @SubscribeEvent
    public void jump(LivingEvent.LivingJumpEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        if (player.inventory.armorInventory[0] != null
                && (player.inventory.armorInventory[0].getItem() == IUItem.quantumBoots || player.inventory.armorInventory[0].getItem() == IUItem.NanoLeggings)) {
            player.motionY = 0.8;
            ElectricItem.manager.use(player.inventory.armorInventory[0], 4000.0D, player);

        }
    }

    @SubscribeEvent
    public void falling(LivingFallEvent event) {
        if (!(event.entityLiving instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.entity;
        if (player.inventory.armorInventory[0] != null
                && (player.inventory.armorInventory[0].getItem() == IUItem.quantumBoots || player.inventory.armorInventory[0].getItem() == IUItem.NanoLeggings)) {
            if (ElectricItem.manager.canUse(player.inventory.armorInventory[0], 500.0D)) {
                ElectricItem.manager.use(player.inventory.armorInventory[0], 500.0D, player);
            } else {
                ElectricItem.manager.use(player.inventory.armorInventory[0], ElectricItem.manager.getCharge(player.inventory.armorInventory[0]), player);

            }

        }
    }
    //

}