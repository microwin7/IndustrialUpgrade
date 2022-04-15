package com.denfop.register;

import com.denfop.IUItem;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class RegisterOreDict {
    public static final List list_item = new ArrayList<>();
    public static final List<String> list_string = itemNames();
    public static final List<String> list_string1 = itemNames1();
    public static final List<String> list_heavyore = itemNames2();
    public static final List list_item1 = new ArrayList<>();
    public static final String[] string1 = {"casing", "doubleplate", "dust", "ingot", "nugget", "plate", "block"};

    public static final String[] string = {"casing", "crushed", "doubleplate", "ore", "dust", "ingot", "nugget", "plate", "purifiedcrushed", "smalldust", "stik", "verysmalldust", "block"};

    public static void writelist() {
        list_item.add(IUItem.casing);
        list_item.add(IUItem.crushed);
        list_item.add(IUItem.doubleplate);
        list_item.add(IUItem.ore);
        list_item.add(IUItem.iudust);
        list_item.add(IUItem.iuingot);
        list_item.add(IUItem.nugget);
        list_item.add(IUItem.plate);
        list_item.add(IUItem.purifiedcrushed);
        list_item.add(IUItem.smalldust);
        list_item.add(IUItem.stik);
        list_item.add(IUItem.verysmalldust);
        list_item.add(IUItem.block);
    }

    public static void writelist1() {

        list_item1.add(IUItem.alloyscasing);
        list_item1.add(IUItem.alloysdoubleplate);
        list_item1.add(IUItem.alloysdust);
        list_item1.add(IUItem.alloysingot);
        list_item1.add(IUItem.alloysnugget);
        list_item1.add(IUItem.alloysplate);
        list_item1.add(IUItem.alloysblock);

    }

    public static List<String> itemNames2() {
        List<String> list = new ArrayList<>();
        list.add("Magnetite");
        list.add("Calaverite");
        list.add("Galena");
        list.add("Nickelite");
        list.add("Pyrite");
        list.add("Quartzite");
        list.add("Uranite");
        list.add("Azurite");
        list.add("Rhodonite");
        list.add("Alfildit");
        list.add("Euxenite");
        list.add("Smithsonite");
        return list;
    }

    public static List<String> itemNames3() {
        List<String> list = new ArrayList<>();

        list.add("Americium");
        list.add("Neptunium");
        list.add("Curium");
        list.add("Ruby");
        list.add("Topaz");
        list.add("Sapphire");
        list.add("Thorium");

        return list;
    }

    public static List<String> itemNames1() {
        List<String> list = new ArrayList<>();
        list.add("Aluminumbronze");//0
        list.add("Alumel");//1
        list.add("Redbrass");//2
        list.add("Muntsa");//3
        list.add("Nichrome");//4
        list.add("Alcled");//5
        list.add("Vanadoalumite");//6
        list.add("Vitalium");//7
        list.add("Duralumin");//8
        list.add("Ferromanganese");//9

        return list;
    }

    public static List<String> itemNames() {
        List<String> list = new ArrayList<>();
        list.add("Mikhail");//0
        list.add("Aluminium");//1
        list.add("Vanady");//2
        list.add("Tungsten");//3
        list.add("Invar");//4
        list.add("Caravky");//5
        list.add("Cobalt");//6
        list.add("Magnesium");//7
        list.add("Nickel");//8
        list.add("Platinum");//9
        list.add("Titanium");//10
        list.add("Chromium");//11
        list.add("Spinel");//12
        list.add("Electrum");//13
        list.add("Silver");//14
        list.add("Zinc");//15
        list.add("Manganese");//16
        list.add("Iridium");//17
        list.add("Germanium");//18
        return list;
    }

    public static void oredict() {
        writelist();
        writelist1();

        OreDictionary.registerOre("gemCoal", Items.coal);
        OreDictionary.registerOre("oreQuartz", Blocks.quartz_ore);
        OreDictionary.registerOre("oreGlowstone", Blocks.glowstone);
        OreDictionary.registerOre("gemQuartz", Items.quartz);
        OreDictionary.registerOre("gemGlowstone", Items.glowstone_dust);


        OreDictionary.registerOre("oreThorium", IUItem.toriyore);
        OreDictionary.registerOre("gemThorium", IUItem.toriy);
        OreDictionary.registerOre("panelAdv", new ItemStack(IUItem.blockpanel, 1, 0));
        OreDictionary.registerOre("panelHyb", new ItemStack(IUItem.blockpanel, 1, 1));
        OreDictionary.registerOre("panelPer", new ItemStack(IUItem.blockpanel, 1, 2));
        OreDictionary.registerOre("panelQun", new ItemStack(IUItem.blockpanel, 1, 3));
        OreDictionary.registerOre("panelSpe", new ItemStack(IUItem.blockpanel, 1, 4));
        OreDictionary.registerOre("panelPro", new ItemStack(IUItem.blockpanel, 1, 5));
        OreDictionary.registerOre("panelSin", new ItemStack(IUItem.blockpanel, 1, 6));
        OreDictionary.registerOre("panelDif", new ItemStack(IUItem.blockpanel, 1, 7));
        OreDictionary.registerOre("panelPho", new ItemStack(IUItem.blockpanel, 1, 8));
        OreDictionary.registerOre("panelNeu", new ItemStack(IUItem.blockpanel, 1, 9));
        for (int j = 0; j < list_item1.size(); j++) {
            for (int i = 0; i < list_string1.size(); i++) {
                if (list_item1.get(j) instanceof Item)
                    OreDictionary.registerOre(string1[j] + list_string1.get(i), new ItemStack(((Item) list_item1.get(j)).setUnlocalizedName(("alloys" + string1[j])), 1, i));
                if (list_item1.get(j) instanceof Block)
                    OreDictionary.registerOre(string1[j] + list_string1.get(i), new ItemStack(Item.getItemFromBlock((Block) list_item1.get(j)).setUnlocalizedName(string1[j]), 1, i));

            }
        }
        for (int i = 0; i < list_heavyore.size(); i++) {
            OreDictionary.registerOre("ore" + list_heavyore.get(i), new ItemStack(Item.getItemFromBlock((IUItem.heavyore)).setUnlocalizedName("heavyore"), 1, i));

        }
        for (int j = 0; j < list_item.size(); j++) {
            for (int i = 0; i < list_string.size(); i++) {
                if (list_item.get(j) instanceof Item)
                    OreDictionary.registerOre(string[j] + list_string.get(i), new ItemStack(((Item) list_item.get(j)).setUnlocalizedName(string[j]), 1, i));
                if (list_item.get(j) instanceof Block)
                    if (!(string[j] + list_string.get(i)).equals("oreCaravky") && !(string[j] + list_string.get(i)).equals("oreInvar") && !(string[j] + list_string.get(i)).equals("oreElectrum"))
                        if (i < 16)
                            OreDictionary.registerOre(string[j] + list_string.get(i), new ItemStack(Item.getItemFromBlock((Block) list_item.get(j)).setUnlocalizedName(string[j]), 1, i));
                        else {
                            if (IUItem.ore == list_item.get(j)) {
                                OreDictionary.registerOre(string[j] + list_string.get(i), new ItemStack(Item.getItemFromBlock(IUItem.ore1).setUnlocalizedName(string[j]), 1, i - 16));

                            }
                            if (IUItem.block == list_item.get(j)) {
                                OreDictionary.registerOre(string[j] + list_string.get(i), new ItemStack(Item.getItemFromBlock(IUItem.block1).setUnlocalizedName(string[j]), 1, i - 16));

                            }
                        }
            }
        }

        OreDictionary.registerOre("oreMikhail", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 0));
        OreDictionary.registerOre("oreMagnesium", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 1));
        OreDictionary.registerOre("oreChromium", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 2));
        OreDictionary.registerOre("orePlatinum", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 3));
        OreDictionary.registerOre("oreIridium", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 4));
        OreDictionary.registerOre("oreTungsten", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 5));
        OreDictionary.registerOre("oreSpinel", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 6));
        OreDictionary.registerOre("oreCoal", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 7));
        OreDictionary.registerOre("oreCopper", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 8));
        OreDictionary.registerOre("oreDiamond", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 9));
        OreDictionary.registerOre("oreEmerald", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 10));
        OreDictionary.registerOre("oreGold", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 11));
        OreDictionary.registerOre("oreIron", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 12));
        OreDictionary.registerOre("oreLapis", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 13));
        OreDictionary.registerOre("oreLead", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 14));
        OreDictionary.registerOre("oreRedstone", new ItemStack(Item.getItemFromBlock(IUItem.netherore).setUnlocalizedName("netherore"), 1, 15));

        OreDictionary.registerOre("oreTin", new ItemStack(Item.getItemFromBlock(IUItem.netherore1).setUnlocalizedName("netherore1"), 1, 0));
        OreDictionary.registerOre("oreUranium", new ItemStack(Item.getItemFromBlock(IUItem.netherore1).setUnlocalizedName("netherore1"), 1, 1));
        OreDictionary.registerOre("oreAluminium", new ItemStack(Item.getItemFromBlock(IUItem.netherore1).setUnlocalizedName("netherore1"), 1, 2));
        OreDictionary.registerOre("oreTitanium", new ItemStack(Item.getItemFromBlock(IUItem.netherore1).setUnlocalizedName("netherore1"), 1, 3));
        OreDictionary.registerOre("oreCobalt", new ItemStack(Item.getItemFromBlock(IUItem.netherore1).setUnlocalizedName("netherore1"), 1, 4));

        OreDictionary.registerOre("oreMikhail", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 0));
        OreDictionary.registerOre("oreMagnesium", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 1));
        OreDictionary.registerOre("oreChromium", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 2));
        OreDictionary.registerOre("orePlatinum", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 3));
        OreDictionary.registerOre("oreIridium", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 4));
        OreDictionary.registerOre("oreTungsten", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 5));
        OreDictionary.registerOre("oreSpinel", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 6));
        OreDictionary.registerOre("oreCoal", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 7));
        OreDictionary.registerOre("oreCopper", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 8));
        OreDictionary.registerOre("oreDiamond", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 9));
        OreDictionary.registerOre("oreEmerald", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 10));
        OreDictionary.registerOre("oreGold", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 11));
        OreDictionary.registerOre("oreIron", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 12));
        OreDictionary.registerOre("oreLapis", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 13));
        OreDictionary.registerOre("oreLead", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 14));
        OreDictionary.registerOre("oreRedstone", new ItemStack(Item.getItemFromBlock(IUItem.endore).setUnlocalizedName("endore"), 1, 15));

        OreDictionary.registerOre("oreTin", new ItemStack(Item.getItemFromBlock(IUItem.endore1).setUnlocalizedName("endore1"), 1, 0));
        OreDictionary.registerOre("oreUranium", new ItemStack(Item.getItemFromBlock(IUItem.endore1).setUnlocalizedName("endore1"), 1, 1));
        OreDictionary.registerOre("oreAluminium", new ItemStack(Item.getItemFromBlock(IUItem.endore1).setUnlocalizedName("endore1"), 1, 2));
        OreDictionary.registerOre("oreTitanium", new ItemStack(Item.getItemFromBlock(IUItem.endore1).setUnlocalizedName("endore1"), 1, 3));
        OreDictionary.registerOre("oreCobalt", new ItemStack(Item.getItemFromBlock(IUItem.endore1).setUnlocalizedName("endore1"), 1, 4));


        OreDictionary.registerOre("oreAmericium", new ItemStack(Item.getItemFromBlock(IUItem.radiationore).setUnlocalizedName("radiationore"), 1, 0));
        OreDictionary.registerOre("oreNeptunium", new ItemStack(Item.getItemFromBlock(IUItem.radiationore).setUnlocalizedName("radiationore"), 1, 1));
        OreDictionary.registerOre("oreCurium", new ItemStack(Item.getItemFromBlock(IUItem.radiationore).setUnlocalizedName("radiationore"), 1, 2));
        OreDictionary.registerOre("gemAmericium", new ItemStack(IUItem.radiationresources.setUnlocalizedName("radiationgem"), 1, 0));
        OreDictionary.registerOre("gemNeptunium", new ItemStack(IUItem.radiationresources.setUnlocalizedName("radiationgem"), 1, 1));
        OreDictionary.registerOre("gemCurium", new ItemStack(IUItem.radiationresources.setUnlocalizedName("radiationgem"), 1, 2));

        OreDictionary.registerOre("oreRuby", new ItemStack(Item.getItemFromBlock(IUItem.preciousore).setUnlocalizedName("preciousore"), 1, 0));
        OreDictionary.registerOre("oreSapphire", new ItemStack(Item.getItemFromBlock(IUItem.preciousore).setUnlocalizedName("preciousore"), 1, 1));
        OreDictionary.registerOre("oreTopaz", new ItemStack(Item.getItemFromBlock(IUItem.preciousore).setUnlocalizedName("preciousore"), 1, 2));
        OreDictionary.registerOre("gemRuby", new ItemStack(IUItem.preciousgem.setUnlocalizedName("preciousgem"), 1, 0));
        OreDictionary.registerOre("gemSapphire", new ItemStack(IUItem.preciousgem.setUnlocalizedName("preciousgem"), 1, 1));
        OreDictionary.registerOre("gemTopaz", new ItemStack(IUItem.preciousgem.setUnlocalizedName("preciousgem"), 1, 2));

    }


}
