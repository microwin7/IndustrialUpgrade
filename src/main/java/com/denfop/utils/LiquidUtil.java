package com.denfop.utils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

public class LiquidUtil {


    public static boolean placeFluid(FluidStack fs, World world, int x, int y, int z) {
        if (fs == null || fs.amount < 1000)
            return false;
        Fluid fluid = fs.getFluid();
        Block block = world.getBlock(x, y, z);
        if ((block.isAir(world, x, y, z) || !block.getMaterial().isSolid()) && fluid.canBePlacedInWorld() && (block != fluid
                .getBlock() || !isFullFluidBlock(world, x, y, z, block))) {
            if (world.provider.isHellWorld && fluid == FluidRegistry.WATER) {
                world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "random.fizz", 0.5F, 2.6F + (world.rand
                        .nextFloat() - world.rand.nextFloat()) * 0.8F);
                for (int i = 0; i < 8; i++)
                    world.spawnParticle("largesmoke", x + Math.random(), y + Math.random(), z + Math.random(), 0.0D, 0.0D, 0.0D);
            } else {
                if (!world.isRemote && !block.getMaterial().isSolid() && !block.getMaterial().isLiquid())
                    world.func_147480_a(x, y, z, true);
                if (fluid == FluidRegistry.WATER) {
                } else if (fluid == FluidRegistry.LAVA) {
                } else {
                    block = fluid.getBlock();
                }
                int meta = (block instanceof BlockFluidBase) ? ((BlockFluidBase) block).getMaxRenderHeightMeta() : 0;
                if (!world.setBlock(x, y, z, block, meta, 3))
                    return false;
            }
            fs.amount -= 1000;
            return true;
        }
        return false;
    }

    private static boolean isFullFluidBlock(World world, int x, int y, int z, Block block) {
        if (block instanceof IFluidBlock) {
            IFluidBlock fBlock = (IFluidBlock) block;
            FluidStack drained = fBlock.drain(world, x, y, z, false);
            return (drained != null && drained.amount >= 1000);
        }
        if (block == Blocks.water || block == Blocks.flowing_water || block == Blocks.lava || block == Blocks.flowing_lava)
            return (world.getBlockMetadata(x, y, z) == 0);
        return false;
    }
}
