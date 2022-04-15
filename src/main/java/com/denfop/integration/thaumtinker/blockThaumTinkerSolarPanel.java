package com.denfop.integration.thaumtinker;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.item.modules.AdditionModule;
import com.denfop.proxy.ClientProxy;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.tiles.overtimepanel.TileEntityAdvancedSolarPanel;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.block.TileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class blockThaumTinkerSolarPanel extends BlockContainer {


    private final String[] name = new String[]{"ihor"};
    private final String[] side = new String[]{"_bottom", "_top", "_side", "_side", "_side", "_side"};
    private final String[] type = new String[]{"", "aer", "earth", "nether", "end", "night", "sun", "rain"};
    private final IIcon[][][] main_iconBuffer1 = new IIcon[name.length][type.length][side.length];

    public blockThaumTinkerSolarPanel() {
        super(Material.iron);
        setHardness(3.0F);
        setCreativeTab(IUCore.tabssp);
        this.setResistance(6000F);
        GameRegistry.registerBlock(this,
                ItemThaumTinkerSolarPanel.class, "blockThaumTinkerSolarPanel");
    }

    public static TileEntity getBlockEntity(int i) {
        if (i == 0) {
            return new TileEntityIhorSolarPanel();
        }
        return new TileEntityAdvancedSolarPanel();
    }

    public void registerBlockIcons(IIconRegister par1IconRegister) {
        for (int i = 0; i < name.length; i++)
            for (int m = 0; m < type.length; m++)
                for (int k = 0; k < side.length; k++) {
                    if (k != 1) {
                        this.main_iconBuffer1[i][m][k] = par1IconRegister.registerIcon(Constants.TEXTURES_MAIN + name[i] + side[k]);
                    } else {
                        this.main_iconBuffer1[i][m][k] = par1IconRegister.registerIcon(Constants.TEXTURES_MAIN + name[i] + side[k] + type[m]);

                    }
                }
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = world.getBlockMetadata(x, y, z);
        TileEntity te = world.getTileEntity(x, y, z);
        int facing = (te instanceof TileEntityBlock) ? ((TileEntityBlock) te).getFacing() : 0;
        TileEntitySolarPanel tile = (TileEntitySolarPanel) te;
        return this.main_iconBuffer1[blockMeta][tile.solarType][ClientProxy.sideAndFacingToSpriteOffset[blockSide][facing]];

    }

    @Override
    public IIcon getIcon(final int blockSide, final int blockMeta) {

        return this.main_iconBuffer1[blockMeta][0][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
    }

    public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {
        TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null)

            dropItems((TileEntitySolarPanel) tileentity, world);
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
    public boolean onBlockActivated(final World world, final int i, final int j, final int k, final EntityPlayer player,
                                    final int s, final float f1, final float f2, final float f3) {
        if (player.isSneaking()) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }
        final TileEntity tileentity = world.getTileEntity(i, j, k);
        if (tileentity != null) {

            if (world.getTileEntity(i, j, k) instanceof TileEntitySolarPanel) {
                TileEntitySolarPanel tile = (TileEntitySolarPanel) world.getTileEntity(i, j, k);
                for (int m = 0; m < 9; m++) {
                    if (tile.inputslot.get(m) != null && tile.inputslot.get(m).getItem() instanceof AdditionModule
                            && tile.inputslot.get(m).getItemDamage() == 0 && (tile.player.equals(player.getDisplayName()) || player.capabilities.isCreativeMode)) {
                        player.openGui(IUCore.instance, 1, world, i, j, k);

                        break;

                    } else {
                        if (!tile.personality) {

                            player.openGui(IUCore.instance, 1, world, i, j, k);
                        } else {
                            if (!tile.player.equals(player.getDisplayName()))
                                if (tile.getWorldObj().provider.getWorldTime() % 5 == 0)
                                    player.addChatMessage(new ChatComponentTranslation(
                                            StatCollector.translateToLocal("iu.error")));
                        }
                    }

                }
            }

        }
        return true;
    }

    private void dropItems(TileEntitySolarPanel tileentity, World world) {
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

    public TileEntity createNewTileEntity(World var1, int var2) {
        return getBlockEntity(var2);
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List subItems) {
        for (int ix = 0; ix < this.name.length; ++ix) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }
}
