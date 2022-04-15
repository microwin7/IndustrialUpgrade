package com.denfop.block.blockoil;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.tiles.base.TileOilBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOil extends Block implements ITileEntityProvider {


    private IIcon icon;

    public BlockOil() {
        super(Material.iron);
        setHardness(3.0F);
        setCreativeTab(IUCore.tabssp4);
        setBlockName("OilBlock");

        this.setBlockUnbreakable();
        GameRegistry.registerBlock(this, "blockOil_IU");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileOilBlock();
    }

    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.icon = par1IconRegister.registerIcon(Constants.TEXTURES_MAIN + "blocks/neft_flow");
    }

    @Override
    public IIcon getIcon(final int blockSide, final int blockMeta) {

        return this.icon;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        return this.icon;

    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public int damageDropped(int i) {
        return i;
    }


}
