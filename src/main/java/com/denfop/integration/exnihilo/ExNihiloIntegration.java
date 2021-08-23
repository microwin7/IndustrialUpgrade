package com.denfop.integration.exnihilo;

import com.denfop.integration.exnihilo.blocks.DustBlocks;
import com.denfop.integration.exnihilo.blocks.GravelBlocks;
import com.denfop.integration.exnihilo.blocks.SandBlocks;
import com.denfop.integration.exnihilo.items.ItemDustCrushed;
import com.denfop.integration.exnihilo.items.ItemGravelCrushed;
import com.denfop.integration.exnihilo.items.ItemSandCrushed;
import com.denfop.integration.exnihilo.recipe.BaseRecipes;
import com.denfop.integration.exnihilo.recipe.HammerRecipes;
import com.denfop.integration.exnihilo.recipe.SieveRecipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ExNihiloIntegration {
    public static Block gravel;
    public static Block dust;
    public static Block sand;
    public static Item gravel_crushed;
    public static Item dust_crushed;
    public static Item sand_crushed;

    public static void init(){
        gravel = new GravelBlocks();
        dust = new DustBlocks();
        sand = new SandBlocks();
        gravel_crushed = new ItemGravelCrushed();
        dust_crushed = new ItemDustCrushed();
        sand_crushed = new ItemSandCrushed();
        BaseRecipes.init();
        HammerRecipes.init();
        SieveRecipes.init();
    }

}
