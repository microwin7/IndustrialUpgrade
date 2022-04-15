package com.denfop.block.mechanism;

import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.item.mechanism.ItemBlockQuarryOil;
import com.denfop.tiles.base.TileEntityQuarryVein;
import com.denfop.tiles.base.TileEntityVein;
import com.denfop.tiles.base.TileOilBlock;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Random;

public class BlockQuarryOil extends Block implements ITileEntityProvider {


    public BlockQuarryOil() {
        super(Material.iron);
        setHardness(3.0F);
        setCreativeTab(IUCore.tabssp);
        GameRegistry.registerBlock(this, ItemBlockQuarryOil.class,
                "BlockQuarryOil");
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {

    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityQuarryVein();
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

            dropItems((TileEntityQuarryVein) tileentity, world);
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
        TileEntity tile = world.getTileEntity(i, j, k);
        if (tile instanceof TileEntityQuarryVein) {
            Map map = tile.getWorldObj().getChunkFromBlockCoords(tile.xCoord, tile.zCoord).chunkTileEntityMap;

            TileEntityQuarryVein tile1 = (TileEntityQuarryVein) tile;
            for (Object o : map.values()) {
                TileEntity tile3 = (TileEntity) o;
                if (tile3 instanceof TileOilBlock) {
                    TileOilBlock tile2 = (TileOilBlock) tile3;
                    if (!tile2.empty)
                        player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("iu.fluidneft") + ": " + tile2.number + " mb"
                        ));
                    else
                        player.addChatMessage(new ChatComponentTranslation(StatCollector.translateToLocal("iu.empty")));

                    return true;
                } else if (tile3 instanceof TileEntityVein) {
                    TileEntityVein tile2 = (TileEntityVein) tile3;
                    player.addChatMessage(new ChatComponentTranslation(new ItemStack(IUItem.heavyore, 1, tile2.getWorldObj().getBlockMetadata(tile2.xCoord, tile2.yCoord, tile2.zCoord)).getDisplayName() + ": " + tile2.number + "/" + tile2.max
                    ));
                    return true;
                }

            }
            if (tile1.analysis) {
                player.addChatMessage(new ChatComponentTranslation(ModUtils.getString(((double) tile1.progress / 1200) * 100) + StatCollector.translateToLocal("scanning")
                ));
                return true;
            }


        }


        return true;
    }

    private void dropItems(TileEntityQuarryVein tileentity, World world) {
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
