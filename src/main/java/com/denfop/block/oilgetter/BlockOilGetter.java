package com.denfop.block.oilgetter;

import com.denfop.IUCore;
import com.denfop.tiles.base.TileEntityOilGetter;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOilGetter extends Block implements ITileEntityProvider {


    public BlockOilGetter() {
        super(Material.iron);
        setHardness(3.0F);
        setCreativeTab(IUCore.tabssp);
        GameRegistry.registerBlock(this, ItemBlockOilGetter.class,
                "BlockOilGetter");
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);
        int heading = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;


        TileEntityOilGetter te = (TileEntityOilGetter) world.getTileEntity(x, y, z);
        TileEntityOilGetter.heading = heading;

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

    public void registerBlockIcons(IIconRegister par1IconRegister) {

    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityOilGetter();
    }

    public int getRenderType() {
        return -1;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null)

            dropItems((TileEntityOilGetter) tileentity, world);
        world.removeTileEntity(i, j, k);
        super.breakBlock(world, i, j, k, par5, par6);
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public int damageDropped(int i) {
        return i;
    }

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
        if (tileentity != null)
            player.openGui(IUCore.instance, 1, world, i, j, k);
        return true;
    }

    private void dropItems(TileEntityOilGetter tileentity, World world) {
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
    }

}
