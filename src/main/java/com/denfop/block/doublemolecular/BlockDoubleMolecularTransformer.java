package com.denfop.block.doublemolecular;

import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.item.modules.AdditionModule;
import com.denfop.tiles.base.TileEntityBaseDoubleMolecular;
import com.denfop.tiles.base.TileEntityDoubleMolecular;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDoubleMolecularTransformer extends Block implements ITileEntityProvider {


    public BlockDoubleMolecularTransformer() {
        super(Material.iron);
        setHardness(3.0F);
        setCreativeTab(IUCore.tabssp);
        GameRegistry.registerBlock(this,
                ItemBlockDoubleMolecularTransformer.class, "doublemolecular");
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {

    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityDoubleMolecular();
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null)
            dropItems((TileEntityDoubleMolecular) tileentity, world);
        world.removeTileEntity(i, j, k);
        super.breakBlock(world, i, j, k, par5, par6);
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public int damageDropped(int i) {
        return i;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int s, float f1, float f2,
                                    float f3) {
        if (player.isSneaking())
            return false;
        if (world.isRemote)
            return true;
        TileEntity tileentity = world.getTileEntity(i, j, k);
        TileEntityBaseDoubleMolecular tile = (TileEntityBaseDoubleMolecular) world.getTileEntity(i, j, k);

        if (player.getHeldItem() != null) {
            if (player.getHeldItem().getItem() instanceof AdditionModule && player.getHeldItem().getItemDamage() == 4) {
                if (!tile.rf) {
                    tile.rf = true;
                    player.getHeldItem().stackSize--;
                    return true;
                }
            }
        }
        if (tileentity != null)
            player.openGui(IUCore.instance, 1, world, i, j, k);
        return true;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }


    private void dropItems(TileEntityDoubleMolecular tileentity, World world) {
        Random rand = new Random();
        if (tileentity == null)
            return;
        for (int i = 0; i < tileentity.getSizeInventory(); i++) {
            ItemStack item = tileentity.getStackInSlot(i);
            if (item != null && item.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, (tileentity.xCoord + rx), (tileentity.yCoord + ry),
                        (tileentity.zCoord + rz), new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound())
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.20000000298023224D;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                item.stackSize = 0;
            }
        }
        float rx = rand.nextFloat() * 0.8F + 0.1F;
        float ry = rand.nextFloat() * 0.8F + 0.1F;
        float rz = rand.nextFloat() * 0.8F + 0.1F;
        ItemStack stack = new ItemStack(IUItem.blockdoublemolecular);
        NBTTagCompound nbt = ModUtils.nbt(stack);
        nbt.setByte("redstoneMode", tileentity.redstoneMode);

        EntityItem entityItem = new EntityItem(world, (tileentity.xCoord + rx), (tileentity.yCoord + ry),
                (tileentity.zCoord + rz), stack);
        world.spawnEntityInWorld(entityItem);

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        TileEntityDoubleMolecular te = (TileEntityDoubleMolecular) world.getTileEntity(x, y, z);

        NBTTagCompound nbttagcompound1 = NBTData.getOrCreateNbtData(stack);
        te.redstoneMode = nbttagcompound1.getByte("redstoneMode");

    }
}
