package com.denfop.block.mechanism;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.api.utils.textures.TextureAtlasSheet;
import com.denfop.item.mechanism.ItemMoreMachine3;
import com.denfop.item.modules.AdditionModule;
import com.denfop.item.modules.ModuleTypePanel;
import com.denfop.tiles.base.TileEntityMultiMachine;
import com.denfop.tiles.mechanism.*;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.utils.CheckWrench;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import ic2.api.tile.IWrenchable;
import ic2.core.block.TileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class BlockMoreMachine3 extends BlockContainer {
    public static final String[] names = new String[]{"Rolling", "Rolling1", "Rolling2", "Rolling3", "Extruding", "Extruding1", "Extruding2", "Extruding3", "Cutting", "Cutting1", "Cutting2", "Cutting3"};

    private static final int[][] sideAndFacingToSpriteOffset = new int[][]{{3, 2, 0, 0, 0, 0}, {2, 3, 1, 1, 1, 1},
            {1, 1, 3, 5, 2, 4}, {0, 0, 5, 3, 4, 2}, {4, 5, 4, 2, 3, 5}, {5, 4, 2, 4, 5, 3}};
    private IIcon[][] iconBuffer;

    public BlockMoreMachine3() {
        super(Material.iron);
        setHardness(2.0F);
        setStepSound(soundTypeMetal);
        this.setCreativeTab(IUCore.tabssp);
        GameRegistry.registerBlock(this, ItemMoreMachine3.class,
                "machines_base2");
    }

    @Override
    public TileEntity createTileEntity(World world, int meta) {
        switch (meta) {
            case 0:
                return new TileEntityRolling();
            case 1:
                return new TileEntityDoubleRolling();
            case 2:
                return new TileEntityTripleRolling();
            case 3:
                return new TileEntityQuadRolling();

            case 4:
                return new TileEntityExtruding();
            case 5:
                return new TileEntityDoubleExtruding();
            case 6:
                return new TileEntityTripleExtruding();
            case 7:
                return new TileEntityQuadExtruding();

            case 8:
                return new TileEntityCutting();
            case 9:
                return new TileEntityDoubleCutting();
            case 10:
                return new TileEntityTripleCutting();
            case 11:
                return new TileEntityQuadCutting();

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

        if (isActive(world, x, y, z))
            return iconBuffer[blockMeta][sideAndFacingToSpriteOffset[blockSide][facing] + 6];
        else
            return iconBuffer[blockMeta][sideAndFacingToSpriteOffset[blockSide][facing]];
    }

    @Override
    public IIcon getIcon(int blockSide, int blockMeta) {
        return iconBuffer[blockMeta][sideAndFacingToSpriteOffset[blockSide][3]];
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return null;
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
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        NBTTagCompound nbttagcompound = ModUtils.nbt(stack);
        boolean rf = nbttagcompound.getBoolean("rf");
        TileEntityMultiMachine te1 = (TileEntityMultiMachine) world.getTileEntity(x, y, z);
        te1.rf = rf;
        int heading = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        TileEntityBlock te = (TileEntityBlock) world.getTileEntity(x, y, z);
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
        if (world.isRemote)
            return true;
        if (!entityPlayer.isSneaking()) {
            if (CheckWrench.getwrench(entityPlayer))
                return false;
            TileEntityMultiMachine tile = (TileEntityMultiMachine) world.getTileEntity(x, y, z);
            if (entityPlayer.getHeldItem() != null) {
                if (entityPlayer.getHeldItem().getItem() instanceof AdditionModule && entityPlayer.getHeldItem().getItemDamage() == 4) {
                    if (!tile.rf && tile.module == 0) {
                        tile.rf = true;
                        tile.module = 1;
                        entityPlayer.getHeldItem().stackSize--;
                    }
                }
                if (entityPlayer.getHeldItem().getItem().equals(IUItem.module_quickly)) {
                    if (!tile.quickly && tile.module == 0) {
                        tile.quickly = true;
                        tile.module = 1;
                        entityPlayer.getHeldItem().stackSize--;
                    }
                }
                if (entityPlayer.getHeldItem().getItem() instanceof ModuleTypePanel) {
                    if (tile.solartype != null) {
                        EnumSolarPanels type = tile.solartype;
                        int meta = type.meta;
                        ItemStack stack = new ItemStack(IUItem.module6, 1, meta);
                        if (!entityPlayer.inventory.addItemStackToInventory(stack)) {
                            EntityItem item = new EntityItem(entityPlayer.getEntityWorld());
                            item.setEntityItemStack(stack);
                            item.setPosition(entityPlayer.posX, entityPlayer.posY - 1, entityPlayer.posZ);
                            item.delayBeforeCanPickup = 10;
                            world.func_147479_m((int) (entityPlayer.posX), (int) entityPlayer.posY - 1, (int) (entityPlayer.posZ));
                        }

                    }
                    tile.solartype = ModuleTypePanel.getSolarType(entityPlayer.getHeldItem().getItemDamage());
                    entityPlayer.getHeldItem().stackSize--;
                    return true;
                }
                if (entityPlayer.getHeldItem().getItem().equals(IUItem.module_stack)) {
                    if (!tile.modulesize && tile.module == 0) {
                        tile.modulesize = true;
                        tile.module = 1;
                        entityPlayer.getHeldItem().stackSize--;
                    }
                }
            }
            entityPlayer.openGui(IUCore.instance, 0, world, x, y, z);
            return true;
        } else {
            if (CheckWrench.getwrench(entityPlayer))
                return false;

        }

        return false;
    }

    private boolean isActive(IBlockAccess iba, int x, int y, int z) {
        return ((TileEntityBlock) iba.getTileEntity(x, y, z)).getActive();
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


    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return 0;
    }

    @Override
    public boolean hasComparatorInputOverride() {
        return true;
    }

}
