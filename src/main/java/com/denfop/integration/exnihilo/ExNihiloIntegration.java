package com.denfop.integration.exnihilo;

import com.denfop.IUItem;
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
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class ExNihiloIntegration {
    public static Block gravel;
    public static Block dust;
    public static Block sand;
    public static Item gravel_crushed;
    public static Item dust_crushed;
    public static Item sand_crushed;

    public static List<String> itemNames() {
        List<String> list = new ArrayList<>();
        list.add("Mikhail");//0
        list.add("Aluminium");//1
        list.add("Vanady");//2
        list.add("Tungsten");//3
        list.add("Cobalt");//6
        list.add("Magnesium");//7
        list.add("Nickel");//8
        list.add("Platinum");//9
        list.add("Titanium");//10
        list.add("Chromium");//11
        list.add("Spinel");//12
        list.add("Silver");//14
        list.add("Zinc");//15
        list.add("Manganese");//16
        return list;
    }

    public static void init() {
        gravel = new GravelBlocks();
        dust = new DustBlocks();
        sand = new SandBlocks();
        gravel_crushed = new ItemGravelCrushed();
        dust_crushed = new ItemDustCrushed();
        sand_crushed = new ItemSandCrushed();
        BaseRecipes.init();
        HammerRecipes.init();
        SieveRecipes.init();
        oredictionary();
    }

    private static void oredictionary() {
        List<String> list = itemNames();
        for (int i = 0; i < IUItem.name_mineral1.size(); i++) {
            OreDictionary.registerOre("ore" + list.get(i), new ItemStack(Item.getItemFromBlock((gravel)).setUnlocalizedName("gravel_iu"), 1, i));
            OreDictionary.registerOre("ore" + list.get(i), new ItemStack(Item.getItemFromBlock((dust)).setUnlocalizedName("dust_iu"), 1, i));
            OreDictionary.registerOre("ore" + list.get(i), new ItemStack(Item.getItemFromBlock((sand)).setUnlocalizedName("sand_iu"), 1, i));

        }
    }

}
