package com.denfop.api.space;

import com.denfop.Constants;
import net.minecraft.util.ResourceLocation;

public class SpaceInit {

    public static System solarSystem;
    public static Star sun;
    public static Planet mercury;
    public static Planet venus;
    public static Planet earth;
    public static Planet mars;
    public static Planet pluto;
    public static Planet jupiter;
    public static Planet saturn;
    public static Planet uranus;
    public static Planet neptune;

    public static void init() {
        solarSystem = new System("solarsystem");
        sun = new Star("sun", solarSystem, getTexture("sun"));
        mercury = new Planet(
                "mercury",
                solarSystem,
                getTexture("mercury"),
                EnumLevels.SECOND,
                sun,
                167,
                false,
                0.3,
                EnumType.NEUTRAL,
                false,
                true
        );
        venus = new Planet(
                "venus",
                solarSystem,
                getTexture("venus"),
                EnumLevels.FIRST,
                sun,
                464,
                true,
                0.65,
                EnumType.DANGEROUS,
                false,
                false
        );
        earth = new Planet(
                "earth",
                solarSystem,
                getTexture("earth"),
                EnumLevels.FIRST,
                sun,
                20,
                false,
                1,
                EnumType.SAFE,
                true,
                false
        );
        mars = new Planet(
                "mars",
                solarSystem,
                getTexture("mars"),
                EnumLevels.FIRST,
                sun,
                -65,
                false,
                1.5,
                EnumType.NEUTRAL,
                false,
                true
        );
        jupiter = new Planet(
                "jupiter",
                solarSystem,
                getTexture("jupiter"),
                null,
                sun,
                -110,
                false,
                2.5,
                EnumType.DANGEROUS,
                false,
                false
        );
        saturn = new Planet(
                "saturn",
                solarSystem,
                getTexture("saturn"),
                null,
                sun,
                -140,
                false,
                3.5,
                EnumType.DANGEROUS,
                false,
                false
        );

        uranus = new Planet(
                "uranus",
                solarSystem,
                getTexture("uranus"),
                null,
                sun,
                -195,
                false,
                4.25,
                EnumType.DANGEROUS,
                false,
                false
        );
        neptune = new Planet(
                "neptune",
                solarSystem,
                getTexture("neptune"),
                null,
                sun,
                -220,
                false,
                4.75,
                EnumType.DANGEROUS,
                false,
                false
        );
        pluto = new Planet(
                "pluto",
                solarSystem,
                getTexture("pluto"),
                EnumLevels.SIX,
                sun,
                -247,
                false,
                5.25,
                EnumType.NEUTRAL,
                false,
                false
        );
    }

    public static ResourceLocation getTexture(String name) {


        return new ResourceLocation(
                Constants.TEXTURES,
                "textures/body/" + name + ".png"
        );
    }

}
