package com.denfop.block.mechanism;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.api.utils.textures.TextureAtlasSheet;
import com.denfop.item.mechanism.ItemAdvChamber;
import com.denfop.proxy.ClientProxy;
import com.denfop.tiles.reactors.TileEntityAdvReactorChamberElectric;
import com.denfop.tiles.reactors.TileEntityBaseNuclearReactorElectric;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.Direction;
import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.block.TileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BlockReactorChamber extends BlockContainer {
    public static final String[] names = {"AdvReaCha"};
    TileEntityBaseNuclearReactorElectric reactor;
    private IIcon[][] iconBuffer;
    public BlockReactorChamber() {
        super(Material.iron);
        setHardness(2.0F);
        setStepSound(soundTypeMetal);
        this.setCreativeTab(IUCore.tabssp);
        GameRegistry.registerBlock(this, ItemAdvChamber.class, "AdvChamber");

    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        if (meta == 0) {
            return new TileEntityAdvReactorChamberElectric();
        }
        return null;
    }

    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.iconBuffer = new IIcon[names.length][12];
        for (int i = 0; i < names.length; i++) {
            IIcon[] icons = TextureAtlasSheet.unstitchIcons(par1IconRegister, Constants.TEXTURES_MAIN + "block" + names[i], 12,
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

    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighbor) {
        if (world.checkChunksExist(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1) && !this.canPlaceBlockAt(world, x, y, z)) {
            world.setBlockToAir(x, y, z);
            this.dropBlockAsItem(world, x, y, z, new ItemStack(IUItem.advchamberblock));
        }

    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        int count = 0;
        Direction[] var6 = Direction.directions;


        for (Direction dir : var6) {
            if (dir.applyTo(world, x, y, z) instanceof TileEntityBaseNuclearReactorElectric) {
                ++count;
            }
        }

        return count == 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return null;
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random) {
        this.reactor = this.getReactorEntity(world, i, j, k);
        if (this.reactor == null) {
            this.onNeighborBlockChange(world, i, j, k, this);
        } else {
            int puffs = this.reactor.heat / 1000;
            if (puffs > 0) {
                puffs = world.rand.nextInt(puffs);

                int n;
                for (n = 0; n < puffs; ++n) {
                    world.spawnParticle("smoke", (float) i + random.nextFloat(), (float) j + 0.95F, (float) k + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }

                puffs -= world.rand.nextInt(4) + 3;

                for (n = 0; n < puffs; ++n) {
                    world.spawnParticle("flame", (float) i + random.nextFloat(), (float) j + 1.0F, (float) k + random.nextFloat(), 0.0D, 0.0D, 0.0D);
                }

            }
        }
    }

    public TileEntityBaseNuclearReactorElectric getReactorEntity(World world, int x, int y, int z) {
        Direction[] var5 = Direction.directions;


        for (Direction dir : var5) {
            TileEntity te = dir.applyTo(world, x, y, z);
            if (te instanceof TileEntityBaseNuclearReactorElectric) {
                return (TileEntityBaseNuclearReactorElectric) te;
            }
        }

        this.onNeighborBlockChange(world, x, y, z, world.getBlock(x, y, z));
        return null;
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float a, float b, float c) {
        if (world.isRemote)
            return true;
        if (entityplayer.isSneaking()) {
            return false;
        } else {
            TileEntityBaseNuclearReactorElectric reactor = this.getReactorEntity(world, i, j, k);
            if (reactor == null) {
                this.onNeighborBlockChange(world, i, j, k, this);
                return false;
            } else {
                return !IC2.platform.isSimulating() || IC2.platform.launchGui(entityplayer, reactor);
            }
        }
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
    public boolean hasComparatorInputOverride() {
        return true;
    }

}
