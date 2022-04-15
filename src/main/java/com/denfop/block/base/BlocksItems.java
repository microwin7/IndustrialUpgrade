package com.denfop.block.base;


import com.denfop.Constants;
import com.denfop.fluid.IUFluid;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.*;

public class BlocksItems {
    private static final Map<String, Fluid> fluids = new HashMap<>();
    private static final Map<String, Block> fluidBlocks = new HashMap<>();
    private static final Map<String, String> renames = new HashMap<>();
    private static final Set<String> dropped = new HashSet<>();

    public static void init() {
        initFluids();

    }

    private static void initFluids() {
        registerIC2fluid("fluidNeutron", 3867955, 3000, 300, false);
        registerIC2fluid("fluidHelium", 10983500, 1000, 300, true);
        registerIC2fluid("fluidbenz", 3867955, 3000, 500, false);
        registerIC2fluid("fluiddizel", 3867955, 3000, 500, false);
        registerIC2fluid("fluidneft", 3867955, 3000, 500, false);
//
        registerIC2fluid("fluidpolyeth", 3867955, 3000, 2000, true);
        registerIC2fluid("fluidpolyprop", 3867955, 3000, 2000, true);
        registerIC2fluid("fluidoxy", 3867955, 3000, 500, false);

        registerIC2fluid("fluidhyd", 3867955, 3000, 500, false);

    }

    public static Material fluid = (new MaterialLiquid(MapColor.tntColor));

    private static void registerIC2fluid(String internalName, int color, int density,
                                         int temperature, boolean isGaseous) {
        Block block = null;
        String fluidName = internalName.substring("fluid".length()).toLowerCase(Locale.ENGLISH);
        Fluid fluid = (new IUFluid(fluidName)).setDensity(density).setViscosity(3000).setLuminosity(0)
                .setTemperature(temperature).setGaseous(isGaseous);
        if (!FluidRegistry.registerFluid(fluid))
            fluid = FluidRegistry.getFluid(fluidName);
        if (!fluid.canBePlacedInWorld()) {
            BlockIUFluid blockIC2Fluid = new BlockIUFluid(internalName, fluid, temperature >= 3000 ? Material.lava : Material.water, color);
            fluid.setBlock(blockIC2Fluid);
            fluid.setUnlocalizedName(blockIC2Fluid.getUnlocalizedName());
        } else {
            block = fluid.getBlock();
        }
        fluids.put(internalName, fluid);
        fluidBlocks.put(internalName, block);
    }

    public static void onMissingMappings(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.get()) {
            if (mapping.name.startsWith(Constants.TEXTURES_MAIN)) {
                String subName = mapping.name.substring(Constants.MOD_ID.length() + 1);
                String newName = renames.get(subName);
                if (newName != null) {
                    String name = Constants.TEXTURES_MAIN + newName;
                    if (mapping.type == GameRegistry.Type.BLOCK) {
                        mapping.remap(GameData.getBlockRegistry().getRaw(name));
                        continue;
                    }
                    mapping.remap(GameData.getItemRegistry().getRaw(name));
                    continue;
                }
                if (dropped.contains(subName))
                    mapping.ignore();
            }
        }
    }

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Pre event) {
        if (event.map.getTextureType() == 0) {
            for (Map.Entry<String, Fluid> entry : fluids.entrySet()) {

                IIcon still = event.map.registerIcon(Constants.TEXTURES_MAIN + "blocks/" + entry.getKey().substring(entry.getKey().indexOf("d") + 1).toLowerCase() + "_still");
                IIcon flow = event.map.registerIcon(Constants.TEXTURES_MAIN + "blocks/" + entry.getKey().substring(entry.getKey().indexOf("d") + 1).toLowerCase() + "_flow");

                Fluid fluid = entry.getValue();
                fluid.setIcons(still, flow);

            }
        }

    }

    public static Fluid getFluid(String name) {
        return fluids.get(name);
    }

    public static Block getFluidBlock(String blockName) {
        return fluidBlocks.get(blockName);
    }

}
