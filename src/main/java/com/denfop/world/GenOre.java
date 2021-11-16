package com.denfop.world;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.block.ore.BlockEndOre;
import com.denfop.block.ore.BlockEndOre1;
import com.denfop.block.ore.BlockNetherOre;
import com.denfop.block.ore.BlockNetherOre1;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Random;

public class GenOre implements IWorldGenerator {
    final FluidStack fs = FluidContainerRegistry.getFluidForFilledItem(IUItem.NeftCell);
    final Fluid fluid = fs.getFluid();
    final Block block = fluid.getBlock();

    public static void init() {
        GameRegistry.registerWorldGenerator(new GenOre(), 0);
    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
                         IChunkProvider chunkProvider) {

        switch (world.provider.dimensionId) {
            case -1:
                generateNether(world, random, chunkX * 16, chunkZ * 16);
                break;
            case 0:
                int var2;
                int var3;
                int var4;
                int var5;

                if (world.provider.dimensionId == 0 && random.nextInt(100) + 1 > 70 && world.getBiomeGenForCoords(chunkX * 16, chunkZ * 16) == BiomeGenBase.desert) {
                    for (var2 = 0; var2 < 1; ++var2) {
                        var3 = chunkX * 16 + random.nextInt(16) + 8;
                        var4 = random.nextInt(random.nextInt(random.nextInt(112) + 8) + 8) + 60;
                        var5 = chunkZ * 16 + random.nextInt(16) + 8;
                        if (block != null)
                            new WorldGenOil(block, block, 3)
                                    .generate(world, random, var3, var4, var5);

                    }
                }
                break;
            case 1:
                generateEnd(world, random, chunkX * 16, chunkZ * 16);
                break;
        }
        if (Config.DimensionList.contains(world.provider.dimensionId)) {
            generateSurface(world, random, chunkX * 16, chunkZ * 16);
        }
    }

    private void generateNether(World world, Random random, int x, int y) {
        if (Config.EnableNetherOres) {
            for (int i = 0; i < BlockNetherOre.getlist().size(); i++)
                addOreSpawn1(IUItem.netherore, i, world, random, x, y, 16, 16, 3 + random.nextInt(2), 10, 20, 120);
            for (int i = 0; i < BlockNetherOre1.getlist().size(); i++)
                addOreSpawn1(IUItem.netherore1, i, world, random, x, y, 16, 16, 3 + random.nextInt(2), 10, 20, 120);


        }
    }

    private void generateSurface(World world, Random random, int x, int y) {

        if (Config.EnableToriyOre)
            this.addOreSpawn(IUItem.toriyore, world, random, x, y, 16, 16, 3 + random.nextInt(2), 11, 10, 70);

        if (Config.MikhailOre)
            this.addOreSpawn(IUItem.ore, 0, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.AluminiumOre)
            this.addOreSpawn(IUItem.ore, 1, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.VanadyOre)
            this.addOreSpawn(IUItem.ore, 2, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.TungstenOre)
            this.addOreSpawn(IUItem.ore, 3, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.CobaltOre)
            this.addOreSpawn(IUItem.ore, 6, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.MagnesiumOre)
            this.addOreSpawn(IUItem.ore, 7, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.NickelOre)
            this.addOreSpawn(IUItem.ore, 8, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.PlatiumOre)
            this.addOreSpawn(IUItem.ore, 9, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.TitaniumOre)
            this.addOreSpawn(IUItem.ore, 10, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.ChromiumOre)
            this.addOreSpawn(IUItem.ore, 11, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.SpinelOre)
            this.addOreSpawn(IUItem.ore, 12, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.SilverOre)
            this.addOreSpawn(IUItem.ore, 14, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.ZincOre)
            this.addOreSpawn(IUItem.ore, 15, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.ManganeseOre)
            this.addOreSpawn(IUItem.ore1, 0, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.IridiumOre)
            this.addOreSpawn(IUItem.ore1, 1, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.GermaniumOre)
            this.addOreSpawn(IUItem.ore1, 2, world, random, x, y, 16, 16, 3 + random.nextInt(3), 9, 0, 70);
        if (Config.MagnetiteOre)
            this.addOreSpawn(IUItem.heavyore, 0, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.CalaveriteOre)
            this.addOreSpawn(IUItem.heavyore, 1, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.GalenaOre)
            this.addOreSpawn(IUItem.heavyore, 2, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.NickeliteOre)
            this.addOreSpawn(IUItem.heavyore, 3, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.PyriteOre)
            this.addOreSpawn(IUItem.heavyore, 4, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.QuartziteOre)
            this.addOreSpawn(IUItem.heavyore, 5, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.UraniteOre)
            this.addOreSpawn(IUItem.heavyore, 6, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.AzuriteOre)
            this.addOreSpawn(IUItem.heavyore, 7, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.RhodoniteOre)
            this.addOreSpawn(IUItem.heavyore, 8, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.AlfilditOre)
            this.addOreSpawn(IUItem.heavyore, 9, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.EuxeniteOre)
            this.addOreSpawn(IUItem.heavyore, 10, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);
        if (Config.SmithsoniteOre)
            this.addOreSpawn(IUItem.heavyore, 11, world, random, x, y, 16, 16, 3 + random.nextInt(2), 9, 10, 70);

        if (Config.AmericiumOre)
            this.addOreSpawn(IUItem.radiationore, 0, world, random, x, y, 16, 16, 2 + random.nextInt(2), 14, 10, 70);
        if (Config.NeptuniumOre)
            this.addOreSpawn(IUItem.radiationore, 1, world, random, x, y, 16, 16, 2 + random.nextInt(2), 16, 10, 70);
        if (Config.CuriumOre)
            this.addOreSpawn(IUItem.radiationore, 2, world, random, x, y, 16, 16, 2 + random.nextInt(2), 10, 10, 70);

        if (Config.RubyOre)
            this.addOreSpawn(IUItem.preciousore, 0, world, random, x, y, 16, 16, 3 + random.nextInt(2), 8, 10, 70);
        if (Config.SapphireOre)
            this.addOreSpawn(IUItem.preciousore, 1, world, random, x, y, 16, 16, 3 + random.nextInt(2), 8, 10, 70);
        if (Config.TopazOre)
            this.addOreSpawn(IUItem.preciousore, 2, world, random, x, y, 16, 16, 3 + random.nextInt(2), 8, 10, 70);


    }

    private void generateEnd(World world, Random random, int x, int y) {
        if (Config.EnableEndOres) {
            for (int i = 0; i < BlockEndOre.getlist().size(); i++)
                addOreSpawn2(IUItem.endore, i, world, random, x, y, 16, 16, 3 + random.nextInt(2), 10, 0, 128);

            for (int i = 0; i < BlockEndOre1.getlist().size(); i++)
                addOreSpawn2(IUItem.endore1, i, world, random, x, y, 16, 16, 3 + random.nextInt(2), 10, 0, 128);

        }
    }

    public void addOreSpawn(Block block, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ,
                            int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, 0, maxVeinSize, Blocks.stone)).generate(world, random, posX, posY, posZ);

        }
    }

    public void addOreSpawn(Block block, int meta, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ,
                            int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, meta, maxVeinSize, Blocks.stone)).generate(world, random, posX, posY, posZ);

        }
    }

    public void addOreSpawn1(Block block, int meta, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ,
                             int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, meta, maxVeinSize, Blocks.netherrack)).generate(world, random, posX, posY, posZ);
        }
    }

    public void addOreSpawn2(Block block, int meta, World world, Random random, int blockXPos, int blockZPos, int maxX, int maxZ,
                             int maxVeinSize, int chancesToSpawn, int minY, int maxY) {
        for (int i = 0; i < chancesToSpawn; i++) {
            int posX = blockXPos + random.nextInt(maxX);
            int posY = minY + random.nextInt(maxY - minY);
            int posZ = blockZPos + random.nextInt(maxZ);

            (new WorldGenMinable(block, meta, maxVeinSize, Blocks.end_stone)).generate(world, random, posX, posY, posZ);
        }
    }

}
