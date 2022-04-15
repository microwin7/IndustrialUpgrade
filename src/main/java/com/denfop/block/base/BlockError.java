package com.denfop.block.base;

import com.denfop.IUCore;
import com.denfop.item.base.ItemBlockError;
import com.denfop.tiles.base.TileEntityError;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockError extends BlockContainer {
    public BlockError() {
        super(Material.rock);
        this.setCreativeTab(IUCore.tabssp);
        this.setBlockUnbreakable();
        this.setStepSound(Block.soundTypeStone);
        GameRegistry.registerBlock(this, ItemBlockError.class, "IUError");
        GameRegistry.registerTileEntity(TileEntityError.class, "TileEntityError");

    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, player, stack);


    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public void breakBlock(World world, int i, int j, int k, Block par5, int par6) {


        world.removeTileEntity(i, j, k);
        super.breakBlock(world, i, j, k, par5, par6);
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityError();
    }
}
