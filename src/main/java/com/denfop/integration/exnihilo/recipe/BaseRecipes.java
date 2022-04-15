package com.denfop.integration.exnihilo.recipe;

import com.denfop.IUItem;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class BaseRecipes {
    public static void init() {
        for (int i = 0; i < IUItem.name_mineral1.size(); i++) {
            if (i != 6 && i != 7 && i != 11) {
                GameRegistry.addRecipe(new ItemStack(ExNihiloIntegration.gravel, 1, i),
                        "AA ", "AA ", "   ", 'A', new ItemStack(ExNihiloIntegration.gravel_crushed, 1, i));
                GameRegistry.addRecipe(new ItemStack(ExNihiloIntegration.dust, 1, i),
                        "AA ", "AA ", "   ", 'A', new ItemStack(ExNihiloIntegration.dust_crushed, 1, i));
                GameRegistry.addRecipe(new ItemStack(ExNihiloIntegration.sand, 1, i),
                        "AA ", "AA ", "   ", 'A', new ItemStack(ExNihiloIntegration.sand_crushed, 1, i));
            }
        }

    }
}
