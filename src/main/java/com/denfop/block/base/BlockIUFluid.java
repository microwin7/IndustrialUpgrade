package com.denfop.block.base;

import com.denfop.Constants;
import com.denfop.item.base.ItemBlockIC2;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

public class BlockIUFluid extends BlockFluidClassic {
    private final int color;
    protected IIcon[] fluidIcon;

    public BlockIUFluid(String internalName, Fluid fluid, Material material, int color) {
        super(fluid, BlocksItems.fluid);


        setBlockName(internalName);
        GameRegistry.registerBlock(this, ItemBlockIC2.class, internalName);

        this.color = color;

    }

    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (!(world.getBlock(x, y, z) instanceof BlockLiquid)) {
            return super.canDisplace(world, x, y, z);
        } else {
            int meta = world.getBlockMetadata(x, y, z);
            return meta > 1 || meta == -1;
        }
    }

    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z) instanceof BlockLiquid) {
            int meta = world.getBlockMetadata(x, y, z);
            return (meta > 1 || meta == -1) && super.displaceIfPossible(world, x, y, z);
        } else {
            return super.displaceIfPossible(world, x, y, z);
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {

        String name = this.fluidName;
        this.fluidIcon = new IIcon[]{
                iconRegister.registerIcon(Constants.TEXTURES + ":blocks/" + name + "_still"),
                iconRegister.registerIcon(Constants.TEXTURES + ":blocks/" + name + "_flow")};

    }

    public void updateTick(World world, int x, int y, int z, Random random) {
        super.updateTick(world, x, y, z, random);
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
    }

    public void onBlockAdded(World world, int x, int y, int z) {
        super.onBlockAdded(world, x, y, z);
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack itemStack) {


    }


    public IIcon getIcon(int side, int meta) {
        return (side != 0 && side != 1) ? this.fluidIcon[1] : this.fluidIcon[0];
    }

    public String getUnlocalizedName() {
        return super.getUnlocalizedName().substring(5);
    }

    public int getColor() {
        return this.color;
    }

}
