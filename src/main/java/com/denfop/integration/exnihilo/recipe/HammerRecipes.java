package com.denfop.integration.exnihilo.recipe;


import com.denfop.IUItem;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import exnihilo.registries.HammerRegistry;

public class HammerRecipes {
    public static void init() {
        for (int i = 0; i < IUItem.name_mineral1.size(); i++) {
            if (i != 6 && i != 7 && i != 11) {
                HammerRegistry.registerOre(ExNihiloIntegration.gravel, i, ExNihiloIntegration.sand_crushed, i);
                HammerRegistry.registerOre(ExNihiloIntegration.sand, i, ExNihiloIntegration.dust_crushed, i);
            }
        }

    }

}
