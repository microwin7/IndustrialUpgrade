package com.denfop.integration.exnihilo.recipe;

import com.denfop.IUItem;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import exnihilo.ENBlocks;
import exnihilo.registries.SieveRegistry;
import net.minecraft.init.Blocks;

public class SieveRecipes {
    public  static void init(){
        for(int i = 0; i <  IUItem.name_mineral1.size(); i++) {
            SieveRegistry.register(Blocks.gravel,0,ExNihiloIntegration.gravel_crushed,i,40);
            SieveRegistry.register(Blocks.sand,0,ExNihiloIntegration.sand_crushed,i,40);
            SieveRegistry.register(ENBlocks.Dust,0,ExNihiloIntegration.dust_crushed,i,40);

        }
    }
}
