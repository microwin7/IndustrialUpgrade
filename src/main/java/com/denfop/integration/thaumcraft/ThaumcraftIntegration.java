package com.denfop.integration.thaumcraft;


import com.denfop.Constants;
import com.denfop.utils.Helpers;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.Aspect;

public class ThaumcraftIntegration {

    public static Aspect NIGHT;
    public static Aspect MATTERY;
    public static Aspect DAY;
    public static Aspect ENERGY;
    public static Block blockThaumSolarPanel;
    public static BlockThaumMachine blockThaummachines;

    public static void init() {
        blockThaumSolarPanel = new blockThaumcraftSolarPanel();
        blockThaummachines = new BlockThaumMachine();
        ENERGY = new Aspect("energy", Helpers.convertRGBcolorToInt(208, 52, 10), new Aspect[]{Aspect.ENERGY, Aspect.FIRE}, new ResourceLocation(Constants.TEXTURES, "textures/aspect/energy.png"), 771);
        DAY = new Aspect("day", Helpers.convertRGBcolorToInt(232, 212, 6), new Aspect[]{Aspect.LIFE, Aspect.LIGHT}, new ResourceLocation(Constants.TEXTURES, "textures/aspect/day.png"), 771);
        NIGHT = new Aspect("night", Helpers.convertRGBcolorToInt(26, 19, 19), new Aspect[]{Aspect.ENTROPY, Aspect.VOID}, new ResourceLocation(Constants.TEXTURES, "textures/aspect/night.png"), 771);
        MATTERY = new Aspect("mattery", Helpers.convertRGBcolorToInt(178, 5, 199), new Aspect[]{Aspect.LIFE, Aspect.SLIME}, new ResourceLocation(Constants.TEXTURES, "textures/aspect/materia.png"), 771);

    }
}
