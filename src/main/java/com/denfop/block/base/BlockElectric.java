package com.denfop.block.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.api.utils.textures.TextureAtlasSheet;
import com.denfop.item.base.ItemElectricBlock;
import com.denfop.item.modules.AdditionModule;
import com.denfop.proxy.ClientProxy;
import com.denfop.tiles.base.TileEntityElectricBlock;
import com.denfop.tiles.wiring.storage.*;
import com.denfop.utils.CheckWrench;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.block.TileEntityBlock;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class BlockElectric extends BlockContainer {
    private final String[] name = new String[]{"ADVMFSU", "ULTMFSU", "BatBox", "MFE", "MFSU", "CESU", "PERMFSU", "BARMFSU", "ADRMFSU", "GRAMFSU", "KRVMFSU"};
    private IIcon[][] iconBuffer;


    public BlockElectric() {
        super(Material.iron);
        setHardness(1.5F);
        setStepSound(soundTypeMetal);
        this.setCreativeTab(IUCore.tabssp);
        setBlockUnbreakable();
        GameRegistry.registerBlock(this, ItemElectricBlock.class,
                "electricblock");
    }

    public static TileEntity getBlockEntity(final int i) {
        switch (i) {
            case 0:
                return new TileEntityElectricAdvMFSU();
            case 1:
                return new TileEntityElectricUltMFSU();
            case 2:
                return new TileEntityElectricBatBox();
            case 3:
                return new TileEntityElectricMFE();
            case 4:
                return new TileEntityElectricMFSU();
            case 5:
                return new TileEntityElectricCESU();
            case 6:
                return new TileEntityElectricPerMFSU();
            case 7:
                return new TileEntityElectricBarMFSU();
            case 8:
                return new TileEntityElectricHadrMFSU();
            case 9:
                return new TileEntityElectricGraMFSU();
            case 10:
                return new TileEntityElectricKvrMFSU();
        }
        return null;
    }

    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.iconBuffer = new IIcon[name.length + 1][6];
        for (int i = 0; i < name.length; i++) {
            IIcon[] icons = TextureAtlasSheet.unstitchIcons(par1IconRegister, Constants.TEXTURES_MAIN + "block" + name[i], 6,
                    1);
            System.arraycopy(icons, 0, iconBuffer[i], 0, icons.length);
        }


    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = world.getBlockMetadata(x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        int facing = (te instanceof TileEntityBlock) ? ((int) (((TileEntityBlock) te).getFacing())) : 0;

        return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];
    }

    @Override
    public IIcon getIcon(int blockSide, int blockMeta) {
        return iconBuffer[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
    }


    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> dropList = super.getDrops(world, x, y, z, metadata, fortune);
        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof IInventory) {
            IInventory iinv = (IInventory) te;
            for (int index = 0; index < iinv.getSizeInventory(); ++index) {
                ItemStack itemstack = iinv.getStackInSlot(index);
                if (itemstack != null) {
                    dropList.add(itemstack);
                    iinv.setInventorySlotContents(index, null);
                }
            }
        }

        return dropList;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block blockID, int blockMeta) {
        super.breakBlock(world, x, y, z, blockID, blockMeta);
        boolean var5 = true;
        for (Iterator<ItemStack> iter = getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0).iterator(); iter
                .hasNext(); var5 = false) {
            ItemStack var7 = iter.next();
            if (!var5) {
                if (var7 == null) {
                    return;
                }

                double var8 = 0.7D;
                double var10 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var12 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var14 = (double) world.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                EntityItem var16 = new EntityItem(world, (double) x + var10, (double) y + var12, (double) z + var14,
                        var7);
                var16.delayBeforeCanPickup = 10;
                world.spawnEntityInWorld(var16);
                return;
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return IC2Items.getItem("advancedMachine").getItem();
    }

    @Override
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z); // advanced machine item meta
        // exactly equals the block meta
    }

    @Override
    public TileEntity createNewTileEntity(final World var1, final int var2) {
        return getBlockEntity(var2);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        int heading = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityElectricBlock te = (TileEntityElectricBlock) world.getTileEntity(x, y, z);

        NBTTagCompound nbttagcompound1 = NBTData.getOrCreateNbtData(stack);
        double energy1 = nbttagcompound1.getDouble("energy");
        double energy2 = nbttagcompound1.getDouble("energy2");
        te.energy = energy1;
        te.energy2 = energy2;
        if (player instanceof EntityPlayer) {
            EntityPlayer entityplayer = (EntityPlayer) player;
            te.UUID = entityplayer.getDisplayName();
        }
        switch (heading) {
            case 0:
                te.setFacing((short) 2);
                break;
            case 1:
                te.setFacing((short) 5);
                break;
            case 2:
                te.setFacing((short) 3);
                break;
            case 3:
                te.setFacing((short) 4);
                break;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7,
                                    float par8, float par9) {
        if (world.isRemote) {
            return true;
        }
        if (entityPlayer.isSneaking()) {

            TileEntityElectricBlock tile = (TileEntityElectricBlock) world.getTileEntity(x, y, z);
            if (tile != null) {
                if (CheckWrench.getwrench(entityPlayer))
                    return false;

                TileEntityElectricBlock.module_charge(entityPlayer, tile);

            }
        }
        if (!entityPlayer.isSneaking()) {
            final TileEntity tileentity = world.getTileEntity(x, y, z);

            if (tileentity != null) {
                if (CheckWrench.getwrench(entityPlayer))
                    return false;
                if (world.getTileEntity(x, y, z) instanceof TileEntityElectricBlock) {
                    TileEntityElectricBlock tile = (TileEntityElectricBlock) world.getTileEntity(x, y, z);
                    List<String> list = new ArrayList<>();
                    list.add(tile.UUID);
                    for (int h = 0; h < 2; h++) {
                        if (tile.inputslotC.get(h) != null && tile.inputslotC.get(h).getItem() instanceof AdditionModule
                                && tile.inputslotC.get(h).getItemDamage() == 0) {
                            for (int m = 0; m < 9; m++) {
                                NBTTagCompound nbt = ModUtils.nbt(tile.inputslotC.get(h));
                                String name = "player_" + m;
                                if (!nbt.getString(name).isEmpty())
                                    list.add(nbt.getString(name));
                            }
                            break;
                        }

                    }
                    if (list.contains(entityPlayer.getDisplayName()) || entityPlayer.capabilities.isCreativeMode) {
                        entityPlayer.openGui(IUCore.instance, 1, world, x, y, z);
                    } else {
                        if (!tile.personality) {

                            entityPlayer.openGui(IUCore.instance, 1, world, x, y, z);
                        } else {
                            if (!list.contains(entityPlayer.getDisplayName()))
                                if (tile.getWorldObj().provider.getWorldTime() % 5 == 0)
                                    entityPlayer.addChatMessage(new ChatComponentTranslation(
                                            StatCollector.translateToLocal("iu.error")));
                        }
                    }
                    return true;
                }

            }

        } else {

            return false;

        }

        return true;
    }


    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis) {
        if (axis == ForgeDirection.UNKNOWN) {
            return false;
        }
        TileEntity tileEntity = worldObj.getTileEntity(x, y, z);

        if ((tileEntity instanceof IWrenchable)) {
            IWrenchable te = (IWrenchable) tileEntity;

            int newFacing = ForgeDirection.getOrientation(te.getFacing()).getRotation(axis).ordinal();

            if (te.wrenchCanSetFacing(null, newFacing)) {
                te.setFacing((short) newFacing);
            }
        }

        return false;
    }

    @Override
    public boolean canProvidePower() {
        return true;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return (stack.getItemDamage() == 0 || stack.getItemDamage() == 1) ? EnumRarity.uncommon : EnumRarity.common;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (!(te instanceof TileEntityBlock))
            return 0;
        TileEntityElectricBlock teb = (TileEntityElectricBlock) te;
        return (int) Math.round(Util.map(teb.energy, teb.maxStorage, 15.0D));

    }

}
