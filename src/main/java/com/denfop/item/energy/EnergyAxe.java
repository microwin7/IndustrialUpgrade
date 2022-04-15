package com.denfop.item.energy;

import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.api.Recipes;
import com.denfop.api.upgrade.IUpgradeWithBlackList;
import com.denfop.api.upgrade.UpgradeSystem;
import com.denfop.api.upgrade.event.EventItemBlackListLoad;
import com.denfop.audio.PositionSpec;
import com.denfop.proxy.CommonProxy;
import com.denfop.utils.*;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
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
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

import java.util.*;

public class EnergyAxe extends ItemTool implements IElectricItem, IUpgradeWithBlackList {
    public static final Set<Block> mineableBlocks = Sets.newHashSet(Blocks.planks, Blocks.bookshelf,
            Blocks.log, Blocks.log2, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin, Blocks.leaves, Blocks.leaves2);

    private static final Set<Material> materials = Sets.newHashSet(Material.wood, Material.leaves,
            Material.coral, Material.cactus, Material.plants, Material.vine);

    private static final Set<String> toolType = ImmutableSet.of("axe");
    public final String name;
    public final int efficienty;
    public final int lucky;
    private final float bigHolePower;
    private final float normalPower;
    private final int maxCharge;
    private final int tier;
    private final int energyPerOperation;
    private final int energyPerbigHolePowerOperation;
    private final int transferLimit;
    private IIcon[] textures;
    private boolean hasBlackList = false;
    private final List<String> blacklist = new ArrayList<>();
    private boolean update = false;

    public EnergyAxe(Item.ToolMaterial toolMaterial, String name, int efficienty, int lucky, int transferlimit,
                     int maxCharge, int tier, int normalPower, int bigHolesPower, int energyPerOperation,
                     int energyPerbigHolePowerOperation) {
        super(0.0F, toolMaterial, new HashSet());
        setMaxDamage(27);

        setCreativeTab(IUCore.tabssp2);
        this.efficienty = efficienty;
        this.lucky = lucky;
        this.name = name;
        this.transferLimit = transferlimit;
        this.maxCharge = maxCharge;
        this.tier = tier;
        this.efficiencyOnProperMaterial = this.normalPower = normalPower;
        this.bigHolePower = bigHolesPower;
        this.energyPerOperation = energyPerOperation;
        this.energyPerbigHolePowerOperation = energyPerbigHolePowerOperation;
        this.setUnlocalizedName(name);
        GameRegistry.registerItem(this, name);
    }

    public static void updateGhostBlocks(EntityPlayer player, World world) {
        if (world.isRemote)
            return;
        int xPos = (int) player.posX;
        int yPos = (int) player.posY;
        int zPos = (int) player.posZ;
        for (int x = xPos - 6; x < xPos + 6; x++) {
            for (int y = yPos - 6; y < yPos + 6; y++) {
                for (int z = zPos - 6; z < zPos + 6; z++)
                    ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S23PacketBlockChange(x, y, z, world));
            }
        }
    }

    public static int readToolMode(ItemStack itemstack) {
        NBTTagCompound nbt = NBTData.getOrCreateNbtData(itemstack);
        int toolMode = nbt.getInteger("toolMode");

        if (toolMode < 0 || toolMode > 2)
            toolMode = 0;
        return toolMode;
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

    public boolean hitEntity(ItemStack stack, EntityLivingBase damagee, EntityLivingBase damager) {
        return true;
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

    public boolean canHarvestBlock(Block block, ItemStack stack) {
        return (Items.diamond_axe.canHarvestBlock(block, stack) || Items.diamond_axe.func_150893_a(stack, block) > 1.0F
                || mineableBlocks.contains(block));
    }
    private int getExperience(
            int meta,
            World world,
            int fortune,
            ItemStack stack,
            final Block localBlock
    ) {
        int col =   localBlock.getExpDrop(world, meta , fortune);
        col *= (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.EXPERIENCE, stack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.EXPERIENCE, stack).number*0.5+1 : 1);
        return col;
    }
    public float getDigSpeed(ItemStack tool, Block block, int meta) {
        int energy = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.ENERGY, tool) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.ENERGY, tool).number : 0;
        int speed = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.EFFICIENCY, tool) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.EFFICIENCY, tool).number : 0;
        return !ElectricItem.manager.canUse(tool, (this.energyPerOperation - (int) (this.energyPerOperation * 0.25 * energy))) ? 1.0F
                : (canHarvestBlock(block, tool) ? (this.efficiencyOnProperMaterial + (int) (this.efficiencyOnProperMaterial * 0.2 * speed)) : 1.0F);
    }

    public int getHarvestLevel(ItemStack stack, String toolType) {
        return !toolType.equals("axe") ? super.getHarvestLevel(stack, toolType) : this.toolMaterial.getHarvestLevel();
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int slot, boolean par5) {
        NBTTagCompound nbt = ModUtils.nbt(itemStack);

        if (!UpgradeSystem.system.hasInMap(itemStack)) {
            nbt.setBoolean("hasID", false);
            MinecraftForge.EVENT_BUS.post(new EventItemBlackListLoad(world, this, itemStack, itemStack.getTagCompound()));
        }
    }

    @SideOnly(value = Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        this.textures = new IIcon[2];
        this.textures[0] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name);
        this.textures[1] = iconRegister.registerIcon(Constants.TEXTURES + ":" + name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack itemStack, int pass) {
        NBTTagCompound nbtData = NBTData.getOrCreateNbtData(itemStack);
        if (nbtData.getInteger("toolMode") >= 1)
            return this.textures[1];
        return this.textures[0];
    }

    public int getItemEnchantability() {
        return 0;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
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
        byte dig_depth = (byte) (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.DIG_DEPTH, stack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.DIG_DEPTH, stack).number : 0);
        zRange = zRange > 0 ? zRange : (byte) (zRange + dig_depth);
        xRange = xRange > 0 ? xRange : (byte) (xRange + dig_depth);
        yRange = yRange > 0 ? yRange : (byte) (yRange + dig_depth);

        float costenergy = energy(stack);
        boolean save = nbt.getBoolean("save");
        if (!player.capabilities.isCreativeMode) {
            for (int xPos = x - xRange; xPos <= x + xRange; xPos++) {
                for (int yPos = y - yRange + Yy; yPos <= y + yRange + Yy; yPos++) {
                    for (int zPos = z - zRange; zPos <= z + zRange; zPos++) {
                        if (ElectricItem.manager.canUse(stack, costenergy)) {
                            Block localBlock = world.getBlock(xPos, yPos, zPos);
                            if (localBlock != null && canHarvestBlock(localBlock, stack)
                                    && localBlock.getBlockHardness(world, xPos, yPos, zPos) >= 0.0F
                                    && (materials.contains(localBlock.getMaterial())
                                    || block == Blocks.monster_egg)) {
                                if (save)
                                    if (world.getTileEntity(xPos, yPos, zPos) != null)
                                        continue;
                                int localMeta = world.getBlockMetadata(xPos, yPos, zPos);
                                if (localBlock.getBlockHardness(world, xPos, yPos, zPos) > 0.0F)
                                    onBlockDestroyed(stack, world, localBlock, xPos, yPos, zPos,
                                            player);

                                if (!silktouch)
                                    ExperienceUtils.addPlayerXP(player, getExperience(localMeta, world, fortune,stack
                                            ,localBlock));

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
            if (ElectricItem.manager.canUse(stack, costenergy)) {
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
                        ExperienceUtils.addPlayerXP(player, getExperience(localMeta, world, fortune,stack
                                ,localBlock));


                }
            }
        }
        if (lowPower) {
            if (ElectricItem.manager.canUse(stack, costenergy)) {
                Block localBlock = world.getBlock(x, y, z);
                if (localBlock.getBlockHardness(world, x, y, z) > 0.0F)
                    return onBlockDestroyed(stack, world, localBlock, x, y, z,
                            player);
            }
        } else if (!IUCore.isSimulating()) {
            world.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(block) + (meta << 12));
        }
        return true;
    }

    public boolean onBlockStartBreak(ItemStack stack, int x, int y, int z, EntityPlayer player) {
        byte aoe = (byte) (UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.AOE_DIG, stack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.AOE_DIG, stack).number : 0);

        if (readToolMode(stack) == 0) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);


            return break_block(world, block, meta, mop, aoe, player, x, y, z, stack);
        }
        if (readToolMode(stack) == 1) {
            World world = player.worldObj;
            Block block = world.getBlock(x, y, z);
            int meta = world.getBlockMetadata(x, y, z);
            if (block == null)
                return super.onBlockStartBreak(stack, x, y, z, player);
            MovingObjectPosition mop = raytraceFromEntity(world, player, true, 4.5D);
            if (mop == null) {
                updateGhostBlocks(player, player.worldObj);
                return true;
            }

            if (materials.contains(block.getMaterial()) || block == Blocks.monster_egg) {
                return break_block(world, block, meta, mop, (byte) (1 + aoe), player, x, y, z, stack);
            }
        }
        if (readToolMode(stack) == 2) {
            if (isTree(player.worldObj, x, y, z)) {
                trimLeavs(x, y, z, player.worldObj);
                for (int i = 0; i < 9; i++)
                    player.worldObj.playAuxSFX(2001, x, y, z, Block.getIdFromBlock(player.worldObj.getBlock(x, y, z))
                            + (player.worldObj.getBlockMetadata(x, y, z) << 12));
                chopTree(x, y, z, player, player.worldObj, stack);
            }
        }
        return super.onBlockStartBreak(stack, x, y, z, player);
    }

    void chopTree(int X, int Y, int Z, EntityPlayer player, World world, ItemStack stack) {
        for (int xPos = X - 1; xPos <= X + 1; xPos++) {
            for (int yPos = Y; yPos <= Y + 1; yPos++) {
                for (int zPos = Z - 1; zPos <= Z + 1; zPos++) {
                    Block block = world.getBlock(xPos, yPos, zPos);

                    if (block.isWood(world, xPos, yPos, zPos)) {

                        if (!player.capabilities.isCreativeMode) {
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
    public boolean check_list(Block block, int metaFromState, ItemStack stack) {

        if (!UpgradeSystem.system.hasBlackList(stack)) {
            return true;
        }

        ItemStack stack1 = new ItemStack(block, 1, metaFromState);

        if (OreDictionary.getOreIDs(stack1).length < 1) {
            return true;
        }

        String name = OreDictionary.getOreName(OreDictionary.getOreIDs(stack1)[0]);


        return !UpgradeSystem.system.getBlackList(stack).contains(name);
    }
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int xPos, int yPos, int zPos, EntityLivingBase entity) {

        if (block == null) {
            return false;
        } else {

            if (world.isAirBlock(xPos, yPos, zPos)) return false;
            if (block.getMaterial() instanceof MaterialLiquid || (block.getBlockHardness(world, xPos, yPos, xPos) == -1 && !((EntityPlayer) entity).capabilities.isCreativeMode))
                return false;

            int meta = world.getBlockMetadata(xPos, yPos, zPos);
            if (!world.isRemote) {
                if (ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), (EntityPlayerMP) entity, xPos, yPos, zPos).isCanceled()) {
                    return false;
                }
                block.onBlockHarvested(world, xPos, yPos, zPos, meta, (EntityPlayerMP) entity);

                if (block.removedByPlayer(world, (EntityPlayerMP) entity, xPos, yPos, zPos, true)) {
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                    block.harvestBlock(world, (EntityPlayerMP) entity, xPos, yPos, zPos, meta);
                    boolean smelter = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.SMELTER, stack);
                    boolean comb = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.COMB_MACERATOR, stack);
                    boolean mac = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.MACERATOR, stack);
                    boolean generator = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.GENERATOR, stack);

                    List<EntityItem> items = entity.worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(xPos , yPos , zPos , xPos + 1, yPos  + 1, zPos  + 1));

                    if ((ModUtils.getore(block) && check_list(block, world.getBlockMetadata(xPos,yPos,zPos)
                            , stack)) || !Config.blacklist) {
                        for (EntityItem item : items) {
                            if (!entity.worldObj.isRemote) {

                                ItemStack stack1 = item.getEntityItem();

                                if (comb) {
                                    RecipeOutput rec = Recipes.macerator.getOutputFor(stack1, false);
                                    stack1 = rec.items.get(0);
                                } else if (mac) {
                                    final RecipeOutput output = ic2.api.recipe.Recipes.macerator.getOutputFor(stack1, false);
                                    if (output != null) {
                                        stack1 = output.items.get(0);
                                    }
                                }
                                ItemStack smelt = null;
                                if (smelter) {
                                    smelt = FurnaceRecipes.smelting().getSmeltingResult(stack1);
                                    if (smelt != null) {
                                        smelt.stackSize = stack1.stackSize;
                                    }
                                }
                                if (generator) {
                                    final boolean rec = Info.itemFuel.getFuelValue(stack1, false) > 0;
                                    if (rec) {
                                        int amount = stack1.stackSize;
                                        int value = Info.itemFuel.getFuelValue(stack1, false) / 4;
                                        amount *= value;
                                        amount *= Math.round(10.0F * ConfigUtil.getFloat(
                                                MainConfig.get(),
                                                "balance/energy/generator/generator"
                                        ));
                                        double sentPacket = ElectricItem.manager.charge(
                                                stack,
                                                amount,
                                                2147483647,
                                                true,
                                                false
                                        );
                                        amount -= sentPacket;
                                        amount /= (value * Math.round(10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance" +
                                                "/energy/generator/generator")));
                                        stack1.stackSize = amount;
                                    }
                                }
                                if (smelt != null) {
                                    item.setEntityItemStack(smelt);
                                } else {
                                    item.setEntityItemStack(stack1);
                                }




                                item.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, 0.0F, 0.0F);
                                ((EntityPlayerMP) entity).playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(item));
                                item.delayBeforeCanPickup = 0;
                            }

                        }
                        int random = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.RANDOM, stack) ?
                                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.RANDOM, stack).number : 0;
                        if(random !=0){
                            final int rand = world.rand.nextInt(100001);
                            if(rand >= 100000-random){
                                EntityItem item = new EntityItem(world);
                                item.setEntityItemStack(IUCore.get_ingot.get(world.rand.nextInt(IUCore.get_ingot.size())));
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
                ForgeHooks.onBlockBreakEvent(world, world.getWorldInfo().getGameType(), (EntityPlayerMP) entity, xPos, yPos, zPos);
                EntityPlayerMP mpPlayer = (EntityPlayerMP) entity;
                mpPlayer.playerNetServerHandler.sendPacket(new S23PacketBlockChange(xPos, yPos, zPos, world));
            } else {
                if (block.removedByPlayer(world, (EntityPlayer) entity, xPos, yPos, zPos, true)) {
                    block.onBlockDestroyedByPlayer(world, xPos, yPos, zPos, meta);
                }

                Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C07PacketPlayerDigging(2, xPos, yPos, zPos, Minecraft.getMinecraft().objectMouseOver.sideHit));
            }
            if (entity != null) {

                float energy = energy(stack);
                if (energy != 0.0F && block.getBlockHardness(world, xPos, yPos, zPos) != 0.0F) {
                    ElectricItem.manager.use(stack, energy, entity);
                }
            }

            return true;
        }
    }

    public float energy(ItemStack stack) {
        int energy1 = UpgradeSystem.system.hasModules(EnumInfoUpgradeModules.ENERGY, stack) ?
                UpgradeSystem.system.getModules(EnumInfoUpgradeModules.ENERGY, stack).number : 0;

        int toolMode = readToolMode(stack);
        float energy;
        switch (toolMode) {
            case 0:
                energy = (float) (this.energyPerOperation - this.energyPerOperation * 0.25 * energy1);
                break;
            case 1:
            case 2:
                energy = (float) (this.energyPerbigHolePowerOperation - this.energyPerbigHolePowerOperation * 0.25 * energy1);
                break;

            default:
                energy = 0.0F;
                break;
        }
        return energy;


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
        if (IUCore.keyboard.isSaveModeKeyDown(player)) {
            NBTTagCompound nbt = ModUtils.nbt(itemStack);
            boolean save = !nbt.getBoolean("save");
            CommonProxy.sendPlayerMessage(player,
                    EnumChatFormatting.GREEN + Helpers.formatMessage("message.savemode") +
                            (save ? Helpers.formatMessage("message.allow") : Helpers.formatMessage("message.disallow")));
            nbt.setBoolean("save", save);
        }
        if (IUCore.keyboard.isChangeKeyDown(player)) {
            int toolMode = readToolMode(itemStack) + 1;

            if (IC2.platform.isRendering()) {
                IUCore.audioManager.playOnce(player, PositionSpec.Hand, "Tools/toolChange.ogg", true, IC2.audioManager.getDefaultVolume());
            }
            if (toolMode > 2)
                toolMode = 0;
            saveToolMode(itemStack, toolMode);
            switch (toolMode) {
                case 0:
                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.normal"));
                    this.efficiencyOnProperMaterial = this.normalPower;


                    break;

                case 1:


                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.DARK_PURPLE + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.bigHoles"));
                    this.efficiencyOnProperMaterial = this.bigHolePower;
                    break;
                case 2:

                    CommonProxy.sendPlayerMessage(player,
                            EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                                    + Helpers.formatMessage("message.ultDDrill.mode.treemode"));
                    this.efficiencyOnProperMaterial = this.bigHolePower;
                    break;
            }
        }
        return itemStack;
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
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.bigHoles"));
                par3List.add(Helpers.formatMessage("message.description.bigHoles"));
                break;
            case 2:

                par3List.add(EnumChatFormatting.GOLD + Helpers.formatMessage("message.text.mode") + ": "
                        + EnumChatFormatting.WHITE + Helpers.formatMessage("message.ultDDrill.mode.treemode"));
                par3List.add(Helpers.formatMessage("message.description.treemode"));
                break;
        }
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            par3List.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            par3List.add(StatCollector.translateToLocal("iu.changemode_key") + Keyboard.getKeyName(KeyboardClient.changemode.getKeyCode()) + StatCollector.translateToLocal("iu.changemode_rcm"));
            par3List.add(StatCollector.translateToLocal("iu.savemode_key") + Keyboard.getKeyName(KeyboardClient.savemode.getKeyCode()) + StatCollector.translateToLocal("iu.changemode_rcm"));

        }
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

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public void setUpdate(final boolean update) {
        this.update = update;
    }


    @Override
    public List<String> getBlackList() {
        return this.blacklist;
    }

    @Override
    public void setBlackList(final boolean set) {
        this.hasBlackList = set;
    }

    @Override
    public boolean haveBlackList() {
        return this.hasBlackList;
    }

}
