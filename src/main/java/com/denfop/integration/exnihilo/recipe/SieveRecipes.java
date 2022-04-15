package com.denfop.integration.exnihilo.recipe;

import com.denfop.IUItem;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import exnihilo.ENBlocks;
import exnihilo.registries.SieveRegistry;
import net.minecraft.init.Blocks;

public class SieveRecipes {
    public static void init() {
        for (int i = 0; i < IUItem.name_mineral1.size(); i++) {
            if (i != 6 && i != 7 && i != 11) {
                SieveRegistry.register(Blocks.gravel, 0, ExNihiloIntegration.gravel_crushed, i, 35);
                SieveRegistry.register(Blocks.sand, 0, ExNihiloIntegration.sand_crushed, i, 35);
                SieveRegistry.register(ENBlocks.Dust, 0, ExNihiloIntegration.dust_crushed, i, 35);
            }
        }
        SieveRegistry.register(Blocks.sand, 0, IUItem.toriy, 0, 50);

        SieveRegistry.register(Blocks.sand, 0, IUItem.radiationresources, 0, 70);
        SieveRegistry.register(Blocks.sand, 0, IUItem.radiationresources, 1, 75);
        SieveRegistry.register(Blocks.sand, 0, IUItem.radiationresources, 2, 80);
        SieveRegistry.register(Blocks.sand, 0, IUItem.preciousgem, 0, 50);
        SieveRegistry.register(Blocks.sand, 0, IUItem.preciousgem, 1, 50);
        SieveRegistry.register(Blocks.sand, 0, IUItem.preciousgem, 2, 50);

    }
}
