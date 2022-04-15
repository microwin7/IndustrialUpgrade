package com.denfop.integration.crafttweaker;

import minetweaker.MineTweakerAPI;
import modtweaker2.utils.TweakerPlugin;

public class CTCore {
    public CTCore() {
        MineTweakerAPI.registerClass(CTMolecularTransformer.class);
        MineTweakerAPI.registerClass(CTSynthesis.class);
        MineTweakerAPI.registerClass(CTAlloySmelter.class);
        MineTweakerAPI.registerClass(CTEnrich.class);
        MineTweakerAPI.registerClass(CTAdvAlloySmelter.class);
        MineTweakerAPI.registerClass(CTFermer.class);
        MineTweakerAPI.registerClass(CTGenMicrochip.class);
        MineTweakerAPI.registerClass(CTDoubleMolecularTransformer.class);
        MineTweakerAPI.registerClass(CTMatterRecipe.class);

    }

    public static void register() {
        TweakerPlugin.register("industrialupgrade", CTCore.class);
    }
}
