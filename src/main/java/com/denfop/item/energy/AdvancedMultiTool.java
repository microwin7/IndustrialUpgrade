package com.denfop.item.energy;


import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.proxy.CommonProxy;
import com.denfop.utils.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class AdvancedMultiTool extends ItemTool implements IElectricItem {
    public static final Set<Block> mineableBlocks = Sets.newHashSet(Blocks.cobblestone,
            Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone,
            Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore,
            Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block,
            Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail,
            Blocks.activator_rail, Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.planks,
            Blocks.bookshelf, Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.leaves,
            Blocks.leaves2, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand,
            Blocks.mycelium);

    private static final Set<Material> materials = Sets.newHashSet(Material.iron, Material.anvil,
            Material.rock, Material.glass, Material.ice, Material.packedIce, Material.wood, Material.leaves,
            Material.coral, Material.cactus, Material.plants, Material.vine, Material.grass, Material.ground,
            Material.sand, Material.snow, Material.craftedSnow, Material.clay);

    private static final Set<String> toolType = ImmutableSet.of("pickaxe", "shovel", "axe");

    private final float bigHolePower = Config.bigHolePower;

    private final float normalPower = Config.effPower;

    private final float lowPower = Config.lowPower;

    private final float ultraLowPower = Config.ultraLowPower;

    private final int maxCharge = Config.ultdrillmaxCharge;

    private final int tier = Config.ultdrilltier;

    private final int energyPerOperation = Config.energyPerOperation;

    private final int energyPerLowOperation = Config.energyPerLowOperation;

    private final int energyPerbigHolePowerOperation = Config.energyPerbigHolePowerOperation;
    private final int energyPerultraLowPowerOperation = Config.energyPerultraLowPowerOperation;

    private final int transferLimit = Config.ultdrilltransferLimit;


    public final float energyPerultraLowPowerOperation1 = Config.energyPerultraLowPowerOperation1;

    private final float ultraLowPower1 = Config.ultraLowPower1;

    public AdvancedMultiTool(Item.ToolMaterial toolMaterial, String name) {
        super(0.0F, toolMaterial, new HashSet());
        setMaxDamage(27);
        this.efficiencyOnProperMaterial = this.normalPower;
        setCreativeTab(IUCore.tabssp2);
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase damagee, EntityLivingBase damager) {
        return true;
    }


    public int getItemEnchantability() {
        return 0;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
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

    public Set<String> getToolClasses(ItemStack stack) {
        return toolType;
    }
    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5) {
        NBTTagCompound nbtData = StackUtil.getOrCreateNbtData(itemStack);


        for (int i = 0; i < 4; i++)
            if (nbtData.getString("mode_module" + i).equals("silk")) {
                Map<Integer, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(itemStack);
                enchantmentMap.put(Enchantment.silkTouch.effectId, 1);
                EnchantmentHelper.setEnchantments(enchantmentMap,itemStack);
           break;
            }




    }
    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return (Items.diamond_axe.canHarvestBlock(block, stack) || Items.diamond_axe.func_150893_a(stack, block) > 1.0F
                || Items.diamond_pickaxe.canHarvestBlock(block, stack)
                || Items.diamond_pickaxe.func_150893_a(stack, block) > 1.0F
                || Items.diamond_shovel.canHarvestBlock(block, stack)
                || Items.diamond_shovel.func_150893_a(stack, block) > 1.0F || mineableBlocks.contains(block));
    }

    public float getDigSpeed(ItemStack tool, Block block, int meta) {
        NBTTagCompound nbt = ModUtils.nbt(tool);
        int energy = 0;
        int speed = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("speed")) {
                speed++;
            }
            if (nbt.getString("mode_module" + i).equals("energy")) {
                energy++;
            }
        }
        energy = Math.min(energy, EnumInfoUpgradeModules.ENERGY.max);
        speed = Math.min(speed, EnumInfoUpgradeModules.EFFICIENCY.max);
        return !ElectricItem.manager.canUse(tool, (this.energyPerOperation - (int) (this.energyPerOperation * 0.25 * energy))) ? 1.0F
                : (canHarvestBlock(block, tool) ? (this.efficiencyOnProperMaterial + (int) (this.efficiencyOnProperMaterial * 0.2 * speed)) : 1.0F);
    }

    public int getHarvestLevel(ItemStack stack, String toolType) {
        return (!toolType.equals("pickaxe") && !toolType.equals("shovel") && !toolType.equals("axe") && !toolType.equals("shears"))
                ? super.getHarvestLevel(stack, toolType)
                : this.toolMaterial.getHarvestLevel();
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(Constants.TEXTURES_MAIN + "ultDDrill");
    }

    private void ore_break(World world, int x, int y, int z, EntityPlayer player, boolean silktouch, int fortune, boolean lowPower, ItemStack stack, Block block1) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int energy = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("energy")) {
                energy++;
            }
        }
        energy = Math.min(energy, EnumInfoUpgradeModules.ENERGY.max);
        for (int Xx = x - 1; Xx <= x + 1; Xx++) {
            for (int Yy = y - 1; Yy <= y + 1; Yy++) {
                for (int Zz = z - 1; Zz <= z + 1; Zz++) {
                    if (ElectricItem.manager.canUse(stack, (this.energyPerOperation - this.energyPerOperation * 0.25 * energy))) {
                        Block localBlock = world.getBlock(Xx, Yy, Zz);
                        if (ModUtils.getore(localBlock, block1)) {
                            NBTTagCompound NBTTagCompound = stack.getTagCompound();
                            int ore = NBTTagCompound.getInteger("ore");
                            if (ore < 16)
                                if (!player.capabilities.isCreativeMode) {
                                    int localMeta = world.getBlockMetadata(Xx, Yy, Zz);
                                    if (localBlock.getBlockHardness(world, Xx, Yy, Zz) > 0.0F) {
                                        onBlockDestroyed(stack, world, localBlock, Xx, Yy, Zz,
                                                player);

                                    }
                                    if (!silktouch)
                                        localBlock.dropXpOnBlockBreak(world, Xx, Yy, Zz,
                                                localBlock.getExpDrop(world, localMeta, fortune));
                                    localBlock.onBlockHarvested(world, Xx, Yy, Zz, localMeta, player);
                                    if (localBlock.removedByPlayer(world, player, Xx, Yy, Zz, true)) {
                                        localBlock.onBlockDestroyedByPlayer(world, Xx, Yy, Zz, localMeta);
                                        //		localBlock.harvestBlock(world, player, Xx, Yy, Zz, localMeta);
                                        List<ItemStack> stacklist = localBlock.getDrops(world, Xx, Yy, Zz, localMeta, fortune);
                                        for (ItemStack item : stacklist) {
                                            if (!player.inventory.addItemStackToInventory(item)) {

                                                float f = 0.7F;
                                                double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                                double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                                double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                                EntityItem entityitem = new EntityItem(world, (double) Xx + d0, (double) Yy + d1, (double) Zz + d2, item);
                                                entityitem.delayBeforeCanPickup = 10;
                                                world.spawnEntityInWorld(entityitem);
                                            }
                                        }
                                    }

                                    ore = ore + 1;
                                    NBTTagCompound.setInteger("ore", ore);

                                    ore_break(world, Xx, Yy, Zz, player, silktouch, fortune, lowPower, stack, block1);
                                } else
                                    break;


                            world.func_147479_m(Xx, Yy, Zz);
                        }

                    } else {
                        lowPower = true;
                        break;
                    }
                }
            }
        }

    }

    void chopTree(int X, int Y, int Z, EntityPlayer player, World world, ItemStack stack) {
        int fortune = EnchantmentHelper.getFortuneModifier(player);
        for (int xPos = X - 1; xPos <= X + 1; xPos++) {
            for (int yPos = Y; yPos <= Y + 1; yPos++) {
                for (int zPos = Z - 1; zPos <= Z + 1; zPos++) {
                    Block block = world.getBlock(xPos, yPos, zPos);
                    int meta = world.getBlockMetadata(xPos, yPos, zPos);
                    if (block.isWood(world, xPos, yPos, zPos)) {
                        world.setBlockToAir(xPos, yPos, zPos);
                        if (!player.capabilities.isCreativeMode) {
                            if (block.removedByPlayer(world, player, xPos, yPos, zPos, false))
                                block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                            //	block.harvestBlock(world, player, xPos, yPos, zPos, meta);
                            List<ItemStack> stacklist = block.getDrops(world, xPos, yPos, zPos, meta, fortune);
                            for (ItemStack item : stacklist) {
                                if (!player.inventory.addItemStackToInventory(item)) {

                                    float f = 0.7F;
                                    double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                    double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                    double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                                    EntityItem entityitem = new EntityItem(world, (double) xPos + d0, (double) yPos + d1, (double) zPos + d2, item);
                                    entityitem.delayBeforeCanPickup = 10;
                                    world.spawnEntityInWorld(entityitem);
                                }
                            }
                            block.onBlockHarvested(world, xPos, yPos, zPos, meta, player);
                            onBlockDestroyed(stack, world, block, xPos, yPos, zPos, player);
                        }
                        chopTree(xPos, yPos, zPos, player, world, stack);
                    }
                }
            }
        }
    }

    private boolean isTree(World world, int X, int Y, int Z) {
        Block wood = world.getBlock(X, Y, Z);
        if (wood == null || !wood.isWood(world, X, Y, Z))
            return false;
        int top = Y;
        for (int y = Y; y <= Y + 50; y++) {
            if (!world.getBlock(X, y, Z).isWood(world, X, y, Z)
                    && !world.getBlock(X, y, Z).isLeaves(world, X, y, Z)) {
                top += y;
                break;
            }
        }
        int leaves = 0;
        for (int xPos = X - 1; xPos <= X + 1; xPos++) {
            for (int yPos = Y; yPos <= top; yPos++) {
                for (int zPos = Z - 1; zPos <= Z + 1; zPos++) {
                    if (world.getBlock(xPos, yPos, zPos).isLeaves(world, xPos, yPos, zPos))
                        leaves++;
                }
            }
        }
        return leaves >= 3;
    }

    void trimLeavs(int X, int Y, int Z, World world) {
        scedualUpdates(X, Y, Z, world);
    }

    void scedualUpdates(int X, int Y, int Z, World world) {
        for (int xPos = X - 15; xPos <= X + 15; xPos++) {
            for (int yPos = Y; yPos <= Y + 50; yPos++) {
                for (int zPos = Z - 15; zPos <= Z + 15; zPos++) {
                    Block block = world.getBlock(xPos, yPos, zPos);
                    if (block.isLeaves(world, xPos, yPos, zPos))
                        world.scheduleBlockUpdate(xPos, yPos, zPos, block, 2 + world.rand.nextInt(10));
                }
            }
        }
    }

    boolean break_block(World world, Block block, int meta, MovingObjectPosition mop, byte mode_item, EntityPlayer player, int x, int y, int z, ItemStack stack) {
        byte xRange = mode_item;
        byte yRange = mode_item;
        byte zRange = mode_item;

        switch (mop.sideHit) {
            case 0:
            case 1:
                yRange = 0;
                break;
            case 2:
            case 3:
                zRange = 0;
                break;
            case 4:
            case 5:
                xRange = 0;
                break;
        }

        boolean lowPower = false;
        boolean silktouch = EnchantmentHelper.getSilkTouchModifier(player);
        int fortune = EnchantmentHelper.getFortuneModifier(player);

        int Yy;
        Yy = yRange > 0 ? yRange - 1 : 0;
        NBTTagCompound nbt = ModUtils.nbt(stack);
        int energy = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("energy")) {
                energy++;
            }
        }
        energy = Math.min(energy, EnumInfoUpgradeModules.ENERGY.max);
        byte dig_depth = 0;

        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("dig_depth")) {
                dig_depth++;
            }
        }
        dig_depth = (byte) Math.min(dig_depth, EnumInfoUpgradeModules.DIG_DEPTH.max);
        zRange = zRange > 0 ? zRange : (byte) (zRange + dig_depth);
        xRange = xRange > 0 ? xRange : (byte) (xRange + dig_depth);
        nbt.setInteger("zRange", zRange);
        nbt.setInteger("xRange", xRange);
        nbt.setInteger("yRange", yRange);
        if (!player.capabilities.isCreativeMode) {
            for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
                for (int yPos = y - yRange + Yy; yPos <= y + yRange + Yy; yPos++) {
                    for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
                        if (ElectricItem.manager.canUse(stack, (this.energyPerOperation - this.energyPerOperation * 0.25 * energy)) && materials.contains(block.getMaterial())) {
                            Block localBlock = world.getBlock(xPos, yPos, zPos);
                            if (localBlock != null && canHarvestBlock(localBlock, stack)
                                    && localBlock.getBlockHardness(world, xPos, yPos, zPos) >= 0.0F
                                    && (materials.contains(localBlock.getMaterial())
                                    || block == Blocks.monster_egg)) {
                                int localMeta = world.getBlockMetadata(xPos, yPos, zPos);
                                if (localBlock.getBlockHardness(world, xPos, yPos, zPos) > 0.0F)
                                    onBlockDestroyed(stack, world, localBlock, xPos, yPos, zPos,
                                            player);
                                if (!silktouch)
                                    localBlock.dropXpOnBlockBreak(world, xPos, yPos, zPos,
                                            localBlock.getExpDrop(world, localMeta, fortune));
                                localBlock.onBlockHarvested(world, xPos, yPos, zPos, localMeta, player);
                                if (localBlock.getBlockHardness(world, xPos, yPos, zPos) > 0.0F)
                                    onBlockDestroyed(stack, world, localBlock, xPos, yPos, zPos,
                                            player);


                            } else {
                                if (localBlock.getBlockHardness(world, xPos, yPos, zPos) > 0.0F && materials.contains(localBlock.getMaterial()))
                                    return onBlockDestroyed(stack, world, localBlock, xPos, yPos, zPos,
                                            player);

                            }


                        } else {
                            lowPower = true;
                            break;
                        }
                    }
                }
            }
        } else {
            if (ElectricItem.manager.canUse(stack, (this.energyPerOperation - this.energyPerOperation * 0.25 * energy))) {
                Block localBlock = world.getBlock(x, y, z);
                if (localBlock != null && canHarvestBlock(localBlock, stack)
                        && localBlock.getBlockHardness(world, x, y, z) >= 0.0F
                        && (materials.contains(localBlock.getMaterial())
                        || block == Blocks.monster_egg)) {
                    int localMeta = world.getBlockMetadata(x, y, z);
                    if (localBlock.getBlockHardness(world, x, y, z) > 0.0F)
                        onBlockDestroyed(stack, world, localBlock, x, y, z,
                                player);
                    if (!silktouch)
                        localBlock.dropXpOnBlockBreak(world, x, y, z,
                                localBlock.getExpDrop(world, localMeta, fortune));
                    localBlock.onBlockHarvested(world, x, y, z, localMeta, player);
                } else {
                    if (localBlock.getBlockHardness(world, x, y, z) > 0.0F)
                        return onBlockDestroyed(stack, world, localBlock, x, y, z,
                                player);
                }
            }
        }
        if (lowPower) {
            CommonProxy.sendPlayerMessage(player, "Not enough energy to complete this operation !");
        } else if (!IUCore.isSimulating()) {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
        }
        return true;
    }

    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        NBTTagCompound nbt = ModUtils.nbt(stack);
        byte aoe = 0;

        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).equals("AOE_dig")) {
                aoe++;

            }
        }
        aoe = (byte) Math.min(aoe, EnumInfoUpgradeModules.AOE_DIG.max);

        if (readToolMode(stack) == 1 || readToolMode(stack) == 0) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);
            return break_block(world, block, meta, mop, aoe, player, x, y, z, stack);
        }
        if (readToolMode(stack) == 5) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);

            if (mop != null && (materials.contains(block.getMaterial()) || block == Blocks.monster_egg)) {
                boolean silktouch = EnchantmentHelper.getSilkTouchModifier(player);
                int fortune = EnchantmentHelper.getFortuneModifier(player);

                NBTTagCompound NBTTagCompound = stack.getTagCompound();
                NBTTagCompound.setInteger("ore", 1);
                Block block1 = world.getBlock(x, y, z);
                ore_break(world, x, y, z, player, silktouch, fortune, false, stack, block1);


            }
        }
        if (readToolMode(stack) == 6) {
            if (isTree(player.worldObj, x, y, z)) {
                trimLeavs(x, y, z, player.worldObj);
                for (int i = 0; i < 9; i++)
                    player.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(player.worldObj.getBlock(x, y, z))
                            + (player.worldObj.getBlockMetadata(x, y, z) << 12));
                chopTree(x, y, z, player, player.worldObj, stack);
            }
        }
        if (readToolMode(stack) == 2) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);

            if (mop != null && (materials.contains(block.getMaterial()) || block == Blocks.monster_egg)) {
                return break_block(world, block, meta, mop, (byte) (1 + aoe), player, x, y, z, stack);

            }
        }
        //
        if (readToolMode(stack) == 3) {

            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);
            if (mop != null && (materials.contains(block.getMaterial()) || block == Blocks.monster_egg)) {
                return break_block(world, block, meta, mop, (byte) (2 + aoe), player, x, y, z, stack);
            }
        }
        if (readToolMode(stack) == 4) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);
            if (mop != null && (materials.contains(block.getMaterial()) || block == Blocks.monster_egg)) {
                return break_block(world, block, meta, mop, (byte) (3 + aoe), player, x, y, z, stack);
            }
        }
        //
        return super.onBlockStartBreak(stack, x, y, z, player);
    }

    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int xPos, int yPos, int zPos, EntityLivingBase entity) {

        if (block == null) {
            return false;
        } else {

            if (world.isAirBlock(xPos, yPos, zPos)) return false;
            if (block.getMaterial() instanceof MaterialLiquid || (block.getBlockHardness(world, xPos, yPos, xPos) == -1 && !((EntityPlayer) entity).capabilities.isCreativeMode))
                return false;
            if (!world.isRemote) {
                BlockEvent.BreakEvent event = ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), (EntityPlayerMP) entity, xPos, yPos, zPos);
                if (event.isCanceled()) {
                    ((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S23PacketBlockChange(xPos, yPos, zPos, world));
                    return false;
                }
            }
            int meta = world.getBlockMetadata(xPos, yPos, zPos);
            if (!world.isRemote) {
                block.onBlockHarvested(world, xPos, yPos, zPos, meta, (EntityPlayerMP) entity);

                if (block.removedByPlayer(world, (EntityPlayerMP) entity, xPos, yPos, zPos, true)) {
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                    block.harvestBlock(world, (EntityPlayerMP) entity, xPos, yPos, zPos, meta);
                    NBTTagCompound nbt = ModUtils.nbt(stack);

                    int xMin = nbt.getInteger("xRange"), xMax = nbt.getInteger("xRange");
                    int yMin = nbt.getInteger("yRange"), yMax = nbt.getInteger("yRange");
                    int zMin = nbt.getInteger("zRange"), zMax = nbt.getInteger("zRange");
                    List<EntityItem> items = entity.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xPos - xMin, yPos - yMin, zPos - zMin, xPos + xMax + 1, yPos + yMax + 1, zPos + zMax + 1));

                    if (ModUtils.getore(block) || !Config.blacklist) {
                        for (EntityItem item : items) {
                            if (!entity.worldObj.isRemote) {
                                item.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
                                ((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(item));
                                item.delayBeforeCanPickup = 0;

                            }
                        }
                    } else {
                        for (EntityItem item : items) {
                            if (!entity.worldObj.isRemote) {
                                if ((ModUtils.getore(item.getEntityItem().getItem())))
                                    item.setDead();
                            }
                        }
                    }
                    ((EntityPlayerMP) entity).addExhaustion(-0.025F);
                }

                EntityPlayerMP mpPlayer = (EntityPlayerMP) entity;
                mpPlayer.playerNetServerHandler.sendPacket(new S23PacketBlockChange(xPos, yPos, zPos, world));
            } else {
                if (block.removedByPlayer(world, (EntityPlayer) entity, xPos, yPos, zPos, true)) {
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                }

                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, xPos, yPos, zPos, Minecraft.getMinecraft().objectMouseOver.sideHit));
            }
            if (entity != null) {
                NBTTagCompound nbt = ModUtils.nbt(stack);
                int energy1 = 0;

                for (int i = 0; i < 4; ++i) {
                    if (nbt.getString("mode_module" + i).equals("energy")) {
                        ++energy1;
                    }
                }

                energy1 = Math.min(energy1, EnumInfoUpgradeModules.ENERGY.max);
                int toolMode = readToolMode(stack);
                float energy;
                switch (toolMode) {
                    case 0:
                    case 6:
                    case 5:
                        energy = (float) (this.energyPerOperation - this.energyPerOperation * 0.25 * energy1);
                        break;
                    case 1:
                        energy = (float) (this.energyPerLowOperation - this.energyPerLowOperation * 0.25 * energy1);
                        break;
                    case 2:
                        energy = (float) (this.energyPerbigHolePowerOperation - this.energyPerbigHolePowerOperation * 0.25 * energy1);
                        break;
                    case 3:
                        energy = (float) (this.energyPerultraLowPowerOperation - this.energyPerultraLowPowerOperation * 0.25 * energy1);
                        break;
                    case 4:
                        energy = (float) (this.energyPerultraLowPowerOperation1 - this.energyPerultraLowPowerOperation1 * 0.25 * energy1);

                        break;
                    default:
                        energy = 0.0F;
                        break;
                }

                if (energy != 0.0F && block.getBlockHardness(world, xPos, yPos, zPos) != 0.0F) {
                    ElectricItem.manager.use(stack, energy, entity);
                }
            }

            return true;
        }
    }

    public static int readToolMode(ItemStack itemstack) {
        NBTTagCompound nbt = NBTData.getOrCreateNbtData(itemstack);
        int toolMode = nbt.getInteger("toolMode");

        if (toolMode < 0 || toolMode > 6)
            toolMode = 0;
        return toolMode;
    }

    public void saveToolMode(ItemStack itemstack, int toolMode) {
        NBTTagCompound nbt = NBTData.getOrCreateNbtData(itemstack);
        nbt.setInteger("toolMode", toolMode);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side,
                             float xOffset, float yOffset, float zOffset) {
        for (int i = 0; i < player.inventory.mainInventory.length; i++) {
            ItemStack torchStack = player.inventory.mainInventory[i];
            if (torchStack != null && torchStack.getUnlocalizedName().toLowerCase().contains("torch")) {
                Item item = torchStack.getItem();
                if (item instanceof net.minecraft.item.ItemBlock) {
                    int oldMeta = torchStack.getItemDamage();
                    int oldSize = torchStack.stackSize;
                    boolean result = torchStack.tryPlaceItemIntoWorld(player, world, x, y, z, side, xOffset, yOffset,
                            zOffset);
                    if (player.capabilities.isCreativeMode) {
                        torchStack.setItemDamage(oldMeta);
                        torchStack.stackSize = oldSize;
                    } else if (torchStack.stackSize <= 0) {
                        ForgeEventFactory.onPlayerDestroyItem(player, torchStack);
                        player.inventory.mainInventory[i] = null;
                    }
                    if (result)
                        return true;
                }
            }
        }
        return super.onItemUse(stack, player, world, x, y, z, side, xOffset, yOffset, zOffset);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (IUCore.keyboard.isChangeKeyDown(player)) {
            int toolMode = readToolMode(itemStack) + 1;

            if (toolMode > 6)
                toolMode = 0;
            saveToolMode(itemStack, toolMode);
            switch (toolMode) {
                case 0:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.normal"));
                    this.efficiencyOnProperMaterial = this.normalPower;
                    Map<Integer, Integer> enchantmentMap = EnchantmentHelper.getEnchantments(itemStack);
                    if (Config.enableefficiency) {
                        enchantmentMap.put(Enchantment.efficiency.effectId,
                                Config.efficiencylevel);
                    }
                    if (Config.enablefortune) {
                        enchantmentMap.remove(Enchantment.fortune.effectId,
                                Config.fortunelevel);
                    }
                    if (Config.enablefortune || Config.enableefficiency)
                        EnchantmentHelper.setEnchantments(enchantmentMap, itemStack);
                    break;

                case 1:
                    Map<Integer, Integer> enchantmentMap4 = EnchantmentHelper.getEnchantments(itemStack);
                    if (Config.enableefficiency) {
                        enchantmentMap4.remove(Enchantment.efficiency.effectId,
                                Config.efficiencylevel);
                    }
                    enchantmentMap4.put(Enchantment.efficiency.effectId,
                            Config.efficiencylevel1);

                    EnchantmentHelper.setEnchantments(enchantmentMap4, itemStack);
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.lowPower"));
                    this.efficiencyOnProperMaterial = this.lowPower;
                    break;
                case 2:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.AQUA + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.bigHoles"));
                    this.efficiencyOnProperMaterial = this.bigHolePower;
                    Map<Integer, Integer> enchantmentMap2 = EnchantmentHelper.getEnchantments(itemStack);
                    enchantmentMap2.remove(Enchantment.efficiency.effectId,
                            Config.efficiencylevel1);

                    if (Config.enablesilkTouch) {
                        enchantmentMap2.put(Enchantment.silkTouch.effectId, 1);
                    }
                    EnchantmentHelper.setEnchantments(enchantmentMap2, itemStack);
                    break;
                case 3:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.LIGHT_PURPLE + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.bigHoles1"));
                    this.efficiencyOnProperMaterial = this.ultraLowPower;
                    Map<Integer, Integer> enchantmentMap1 = EnchantmentHelper.getEnchantments(itemStack);
                    if (Config.enablesilkTouch) {
                        enchantmentMap1.remove(Enchantment.silkTouch.effectId, 1);
                    }
                    if (Config.enablefortune) {
                        enchantmentMap1.put(Enchantment.fortune.effectId,
                                Config.fortunelevel);
                    }
                    if (Config.enablefortune || Config.enablesilkTouch)
                        EnchantmentHelper.setEnchantments(enchantmentMap1, itemStack);
                    break;
                case 4:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.DARK_PURPLE + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.bigHoles2"));
                    this.efficiencyOnProperMaterial = this.ultraLowPower1;

                    break;
                case 5:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.pickaxe"));
                    this.efficiencyOnProperMaterial = this.normalPower;
                    Map<Integer, Integer> enchantmentMap3 = EnchantmentHelper.getEnchantments(itemStack);
                    if (Config.enableefficiency) {
                        enchantmentMap3.put(Enchantment.efficiency.effectId,
                                Config.efficiencylevel);
                    }
                    if (Config.enablefortune) {
                        enchantmentMap3.remove(Enchantment.fortune.effectId,
                                Config.fortunelevel);
                    }
                    if (Config.enablefortune || Config.enableefficiency)
                        EnchantmentHelper.setEnchantments(enchantmentMap3, itemStack);
                    break;
                case 6:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.treemode"));
                    this.efficiencyOnProperMaterial = this.normalPower;

                    break;
            }
        }
        return itemStack;
    }

    public static MovingObjectPosition raytraceFromEntity(World world, Entity player, boolean par3, double range) {
        float pitch = player.rotationPitch;
        float yaw = player.rotationYaw;
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        if (!world.isRemote && player instanceof EntityPlayer)
            y++;
        Vec3 vec3 = Vec3.createVectorHelper(x, y, z);
        float f3 = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float f4 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float f5 = -MathHelper.cos(-pitch * 0.017453292F);
        float f6 = MathHelper.sin(-pitch * 0.017453292F);
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        if (player instanceof EntityPlayerMP)
            range = ((EntityPlayerMP) player).theItemInWorldManager.getBlockReachDistance();
        Vec3 vec31 = vec3.addVector(range * f7, range * f6, range * f8);
        return world.func_147447_a(vec3, vec31, par3, !par3, par3);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        int toolMode = readToolMode(par1ItemStack);
        switch (toolMode) {
            case 0:


                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.normal"));
                par3List.add(Helpers.formatMessage("message.description.normal"));
                break;
            case 1:
                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.lowPower"));
                par3List.add(Helpers.formatMessage("message.description.lowPower"));
                break;
            case 2:
                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.bigHoles"));
                par3List.add(Helpers.formatMessage("message.description.bigHoles"));

                break;
            case 3:

                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.bigHoles1"));
                par3List.add(Helpers.formatMessage("message.description.bigHoles1"));
                break;
            case 4:
                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.bigHoles2"));
                par3List.add(Helpers.formatMessage("message.description.bigHoles2"));
                break;
            case 5:

                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.pickaxe"));
                par3List.add(Helpers.formatMessage("message.description.pickaxe"));
                break;
            case 6:
                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.treemode"));
                par3List.add(Helpers.formatMessage("message.description.treemode"));
                break;
        }
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            par3List.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            par3List.add(StatCollector.translateToLocal("iu.changemode_key") + Keyboard.getKeyName(KeyboardClient.changemode.getKeyCode()) + StatCollector.translateToLocal("iu.changemode_rcm"));


    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List subs) {
        ItemStack stack = new ItemStack(this, 1);
        Map<Integer, Integer> enchantmentMap4 = new HashMap<>();

        enchantmentMap4.put(Enchantment.efficiency.effectId, 10);
        EnchantmentHelper.setEnchantments(enchantmentMap4, stack);
        ElectricItem.manager.charge(stack, 2.147483647E9D, 2147483647, true, false);
        subs.add(stack);
        ItemStack itemstack = new ItemStack(this, 1, getMaxDamage());
        EnchantmentHelper.setEnchantments(enchantmentMap4, itemstack);

        subs.add(itemstack);
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack var1) {
        return EnumRarity.uncommon;
    }

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }
}
