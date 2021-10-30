package com.denfop.block.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.item.base.ItemBlockVein;
import com.denfop.proxy.ClientProxy;
import com.denfop.tiles.base.TileEntityVein;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockVein extends Block implements ITileEntityProvider {
    public static List<String> itemNames;
    private IIcon[][] IIconsList;

    public BlockVein() {
        super(Material.iron);
        itemNames = new ArrayList<>();
        this.setCreativeTab(IUCore.tabssp4);
        this.setStepSound(Block.soundTypeStone);
        this.addItemsNames();
        this.setBlockUnbreakable();
        this.setResistance(6000F);
        GameRegistry.registerBlock(this, ItemBlockVein.class, "VeinOre");

    }


    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta;
    }


    public void addItemsNames() {
        itemNames.add("magnetit");
        itemNames.add("calaverite");
        itemNames.add("galena");
        itemNames.add("nickelite");
        itemNames.add("pyrite");
        itemNames.add("quartzite");
        itemNames.add("uranite");
        itemNames.add("azurite");
        itemNames.add("rhodonite");
        itemNames.add("alfildit");
        itemNames.add("euxenite");
        itemNames.add("smithsonite");
    }

    @Override
    public IIcon getIcon(final int blockSide, final int blockMeta) {

        return this.IIconsList[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = world.getBlockMetadata(x, y, z);
        return this.IIconsList[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.IIconsList = new IIcon[itemNames.size()][6];

        for (int i = 0; i < itemNames.size(); i++)
            for (int j = 0; j < 6; j++)
                this.IIconsList[i][j] = par1IconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List subItems) {
        for (int ix = 0; ix < itemNames.size(); ++ix) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityVein();
    }


}
