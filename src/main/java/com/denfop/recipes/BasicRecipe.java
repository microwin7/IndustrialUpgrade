package com.denfop.recipes;


import com.denfop.IUItem;
import com.denfop.block.mechanism.BlockMoreMachine;
import com.denfop.register.RegisterOreDict;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.item.IC2Items;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class BasicRecipe {
    public static void recipe() {
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,13),
                "CCC","BAB","EDE",'E',new ItemStack(IUItem.nanoBox),'D',new ItemStack(Blocks.soul_sand),'C',new ItemStack(Items.skull,1,1),'B',Ic2Items.advancedCircuit,'A', Ic2Items.advancedMachine);

        // TODO Recipes Proton Rods
        Recipes.advRecipes.addRecipe(IUItem.reactorprotonDual, "SQS", 'S',
                IUItem.reactorprotonSimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactorprotonQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorprotonSimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactorprotonQuad,
                "SQS", 'S', IUItem.reactorprotonDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);
        Recipes.advRecipes.addRecipe(IUItem.reactorprotoneit,
                "SQS", 'S', IUItem.reactorprotonQuad, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);
        Recipes.advRecipes.addRecipe(IUItem.reactorprotoneit,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorprotonDual,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);
// TODO Recipes Toriy Rods
        Recipes.advRecipes.addRecipe(IUItem.reactortoriyDual, "SQS", 'S',
                IUItem.reactortoriySimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactortoriyQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactortoriySimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactortoriyQuad,
                "SQS", 'S', IUItem.reactortoriyDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);
//
        Recipes.advRecipes.addRecipe(IUItem.reactoramericiumDual, "SQS", 'S',
                IUItem.reactoramericiumSimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactoramericiumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactoramericiumSimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactoramericiumQuad,
                "SQS", 'S', IUItem.reactoramericiumDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        //
        //
        Recipes.advRecipes.addRecipe(IUItem.reactorneptuniumDual, "SQS", 'S',
                IUItem.reactorneptuniumSimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactorneptuniumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorneptuniumSimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactorneptuniumQuad,
                "SQS", 'S', IUItem.reactorneptuniumDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        //
        //
        Recipes.advRecipes.addRecipe(IUItem.reactorcuriumDual, "SQS", 'S',
                IUItem.reactorcuriumSimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactorcuriumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorcuriumSimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactorcuriumQuad,
                "SQS", 'S', IUItem.reactorcuriumDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        //
        //
        Recipes.advRecipes.addRecipe(IUItem.reactorcaliforniaDual, "SQS", 'S',
                IUItem.reactorcaliforniaSimple, 'Q', Ic2Items.plateiron);
        Recipes.advRecipes.addRecipe(IUItem.reactorcaliforniaQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorcaliforniaSimple,
                'Q', Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        Recipes.advRecipes.addRecipe(IUItem.reactorcaliforniaQuad,
                "SQS", 'S', IUItem.reactorcaliforniaDual, 'Q',
                Ic2Items.plateiron, 'C', Ic2Items.platecopper);

        //
        //
        Recipes.advRecipes.addRecipe(IUItem.reactormendeleviumDual, "SQS", 'S',
                IUItem.reactormendeleviumSimple, 'Q', new ItemStack(IUItem.plate,1,14));
        Recipes.advRecipes.addRecipe(IUItem.reactormendeleviumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactormendeleviumSimple,
                'Q', new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        Recipes.advRecipes.addRecipe(IUItem.reactormendeleviumQuad,
                "SQS", 'S', IUItem.reactormendeleviumDual, 'Q',
                new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        //
        Recipes.advRecipes.addRecipe(IUItem.reactorberkeliumDual, "SQS", 'S',
                IUItem.reactorberkeliumSimple, 'Q', new ItemStack(IUItem.plate,1,14));
        Recipes.advRecipes.addRecipe(IUItem.reactorberkeliumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactorberkeliumSimple,
                'Q', new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        Recipes.advRecipes.addRecipe(IUItem.reactorberkeliumQuad,
                "SQS", 'S', IUItem.reactorberkeliumDual, 'Q',
                new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        //
        Recipes.advRecipes.addRecipe(IUItem.reactoreinsteiniumDual, "SQS", 'S',
                IUItem.reactoreinsteiniumSimple, 'Q', new ItemStack(IUItem.plate,1,14));
        Recipes.advRecipes.addRecipe(IUItem.reactoreinsteiniumQuad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactoreinsteiniumSimple,
                'Q', new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        Recipes.advRecipes.addRecipe(IUItem.reactoreinsteiniumQuad,
                "SQS", 'S', IUItem.reactoreinsteiniumDual, 'Q',
                new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        //
        Recipes.advRecipes.addRecipe(IUItem.reactoruran233Dual, "SQS", 'S',
                IUItem.reactoruran233Simple, 'Q', new ItemStack(IUItem.plate,1,14));
        Recipes.advRecipes.addRecipe(IUItem.reactoruran233Quad,
                "SQS", "CQC", "SQS", 'S', IUItem.reactoruran233Simple,
                'Q', new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        Recipes.advRecipes.addRecipe(IUItem.reactoruran233Quad,
                "SQS", 'S', IUItem.reactoruran233Dual, 'Q',
                new ItemStack(IUItem.plate,1,14), 'C', new ItemStack(IUItem.alloysplate,1,8));

        //
        GameRegistry.addRecipe(new ItemStack(IUItem.ruby_boots),
                "   ", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.sapphire_boots),
                "   ", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.topaz_boots),
                "   ", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(IUItem.ruby_chestplate),
                "A A", "AAA", "AAA", 'A', new ItemStack(IUItem.preciousgem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.sapphire_chestplate),
                "A A", "AAA", "AAA", 'A', new ItemStack(IUItem.preciousgem, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.topaz_chestplate),
                "A A", "AAA", "AAA", 'A', new ItemStack(IUItem.preciousgem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(IUItem.ruby_leggings),
                "AAA", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.sapphire_leggings),
                "AAA", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.topaz_leggings),
                "AAA", "A A", "A A", 'A', new ItemStack(IUItem.preciousgem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(IUItem.ruby_helmet),
                "AAA", "A A", "   ", 'A', new ItemStack(IUItem.preciousgem, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.sapphire_helmet),
                "AAA", "A A", "   ", 'A', new ItemStack(IUItem.preciousgem, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.topaz_helmet),
                "AAA", "A A", "   ", 'A', new ItemStack(IUItem.preciousgem, 1, 2));

        GameRegistry.addRecipe(new ItemStack(IUItem.NanoBoots, 1, OreDictionary.WILDCARD_VALUE),
                " A ", "BCB", "DED", 'A', new ItemStack(IUItem.QuantumItems9, 1), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 2), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.compresscarbon), 'E', new ItemStack(Ic2Items.nanoBoots.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.NanoLeggings, 1, OreDictionary.WILDCARD_VALUE),
                "DED", "BCB", "FAF", 'A', new ItemStack(IUItem.QuantumItems9, 1), 'B', new ItemStack(IUItem.alloysplate, 1, 2), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.compresscarbon), 'E', new ItemStack(Ic2Items.nanoLeggings.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'F', new ItemStack(IUItem.alloysplate));

        GameRegistry.addRecipe(new ItemStack(IUItem.NanoBodyarmor, 1, OreDictionary.WILDCARD_VALUE),
                "DBD", "CEC", "FAF", 'A', new ItemStack(Ic2Items.electricJetpack.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.adv_lappack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.compresscarbon), 'E', new ItemStack(Ic2Items.nanoBodyarmor.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'F', new ItemStack(IUItem.compresscarbonultra));


        GameRegistry.addRecipe(new ItemStack(IUItem.NanoHelmet, 1, OreDictionary.WILDCARD_VALUE),
                "DCD", "BEB", "FGF", 'A', new ItemStack(Ic2Items.electricJetpack.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.compresscarbon, 1), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'E', new ItemStack(Ic2Items.nanoHelmet.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'F', new ItemStack(IUItem.alloysdoubleplate, 1, 9), 'G', new ItemStack(IUItem.QuantumItems9));

        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 2),
                "ABA", "CCC", "AAA", 'A', new ItemStack(Blocks.log), 'C', new ItemStack(Ic2Items.reBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.insulatedCopperCableItem);
        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 5),
                "ABA", "CCC", "AAA", 'A', Ic2Items.platebronze, 'C', new ItemStack(Ic2Items.advBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.insulatedCopperCableItem);

        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 3),
                "ABA", "BCB", "ABA", 'A', new ItemStack(IUItem.alloysdoubleplate), 'C', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.machine);

        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 4),
                "CDC", "CAC", "CBC", 'D', new ItemStack(IUItem.QuantumItems9), 'A', new ItemStack(IUItem.electricblock, 1, 3), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.advancedMachine);

        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 0),
                "CDC", "DAD", "CDC", 'D', new ItemStack(IUItem.photoniy_ingot), 'A', new ItemStack(IUItem.electricblock, 1, 4), 'C', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.advancedMachine);
        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 1),
                "CDC", "DAD", "CDC", 'D', new ItemStack(IUItem.photoniy_ingot), 'A', new ItemStack(IUItem.electricblock, 1, 0), 'C', new ItemStack(IUItem.cirsuitQuantum));
        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 6),
                "CDC", "DAD", "CDC", 'D', new ItemStack(IUItem.core, 1, 4), 'A', new ItemStack(IUItem.electricblock, 1, 1), 'C', new ItemStack(IUItem.circuitSpectral));
        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 7),
                "CDC", "DAD", "CDC", 'D', new ItemStack(IUItem.core, 1, 8), 'A', new ItemStack(IUItem.electricblock, 1, 6), 'C', new ItemStack(IUItem.circuitSpectral));
        GameRegistry.addRecipe(new ItemStack(IUItem.electricblock, 1, 8),
                "CBC", "DAD", "CBC", 'D', new ItemStack(IUItem.core, 1, 9), 'A', new ItemStack(IUItem.electricblock, 1, 7), 'C', new ItemStack(IUItem.cirsuitQuantum), 'B', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(IUItem.overclockerUpgrade,
                "C C", " A ", "C C", 'C', new ItemStack(IUItem.QuantumItems9, 1), 'A', Ic2Items.overclockerUpgrade);
        GameRegistry.addRecipe(IUItem.overclockerUpgrade1,
                "C C", " A ", "C C", 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'A', IUItem.overclockerUpgrade);

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumBoots),
                "C C", " A ", "CDC", 'D', new ItemStack(Ic2Items.quantumBoots.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumBoots),
                "C C", "BAB", "CDC", 'B', Ic2Items.iridiumPlate, 'D', new ItemStack(IUItem.NanoBoots, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumLeggings),
                "C C", " A ", "CDC", 'D', new ItemStack(Ic2Items.quantumLeggings.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumLeggings),
                "CDC", "BAB", "C C", 'B', Ic2Items.iridiumPlate, 'D', new ItemStack(IUItem.NanoLeggings, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumHelmet),
                "C C", " A ", "CDC", 'D', new ItemStack(Ic2Items.quantumHelmet.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumHelmet),
                "CDC", "BAB", "C C", 'B', Ic2Items.iridiumPlate, 'D', new ItemStack(IUItem.NanoHelmet, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumBodyarmor),
                "CDC", "BAB", "C C", 'B', Ic2Items.iridiumPlate, 'D', new ItemStack(IUItem.NanoBodyarmor, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumBodyarmor),
                "CDC", "BAB", "C C", 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'D', new ItemStack(Ic2Items.quantumBodyarmor.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.quantumtool, 1), 'A', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(IUItem.advnanobox),
                " C ", "CAC", " C ", 'C', new ItemStack(IUItem.photoniy, 1), 'A', new ItemStack(IUItem.nanoBox));

        GameRegistry.addRecipe(new ItemStack(IUItem.advQuantumtool),
                "CDC", "BAB", "CFC", 'F', new ItemStack(IUItem.compressIridiumplate), 'B', new ItemStack(IUItem.doubleplate, 1, 17), 'D', new ItemStack(IUItem.casing, 1, 17), 'C', new ItemStack(IUItem.photoniy_ingot, 1), 'A', new ItemStack(IUItem.advnanobox));

        GameRegistry.addRecipe(new ItemStack(IUItem.nanoBox),
                " C ", "BAB", " C ", 'B', Ic2Items.carbonPlate, 'C', new ItemStack(IUItem.compresscarbon, 1), 'A', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumtool),
                "FDF", "BAB", "CDC", 'F', new ItemStack(IUItem.doubleplate, 1, 17), 'B', new ItemStack(IUItem.compresscarbonultra, 1), 'D', new ItemStack(IUItem.compresscarbon, 1), 'C', Ic2Items.iridiumOre, 'A', new ItemStack(IUItem.nanoBox));

        GameRegistry.addRecipe(new ItemStack(IUItem.magnet),
                "A B", "CDC", " C ", 'B', new ItemStack(IUItem.alloysingot, 1, 9), 'D', new ItemStack(Ic2Items.advBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.doubleplate, 1), 'A', new ItemStack(IUItem.alloysingot, 1, 7));

        GameRegistry.addRecipe(new ItemStack(IUItem.impmagnet),
                "B B", "CDC", " C ", 'B', new ItemStack(IUItem.alloysingot, 1, 8), 'D', new ItemStack(IUItem.magnet, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.doubleplate, 1, 4));

        GameRegistry.addRecipe(new ItemStack(IUItem.lapotronCrystal),
                "BCB", "CDC", "BCB", 'B', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.photoniy_ingot), 'C', new ItemStack(IUItem.advQuantumtool, 1));

        GameRegistry.addRecipe(IUItem.tranformerUpgrade,
                "BCB", "CDC", "BCB", 'B', new ItemStack(IUItem.QuantumItems9), 'D', Ic2Items.transformerUpgrade, 'C', new ItemStack(IUItem.alloysplate, 1, 7));

        GameRegistry.addRecipe(IUItem.tranformerUpgrade1,
                "BCB", "CDC", "BCB", 'B', new ItemStack(IUItem.cirsuitQuantum), 'D', IUItem.tranformerUpgrade, 'C', new ItemStack(IUItem.alloysplate, 1, 5));

        GameRegistry.addRecipe(new ItemStack(IUItem.nanopickaxe),
                "BCB", "CDC", "BFB", 'F', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 9), 'D', new ItemStack(Items.diamond_pickaxe), 'C', new ItemStack(IUItem.QuantumItems9, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.nanoaxe),
                "BCB", "CDC", "BFB", 'F', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.alloysplate, 1, 9), 'D', new ItemStack(Items.diamond_axe), 'C', new ItemStack(IUItem.QuantumItems9, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.nanoshovel),
                "BCB", "CDC", "BFB", 'F', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.alloysplate, 1, 9), 'D', new ItemStack(Items.diamond_shovel), 'C', new ItemStack(IUItem.QuantumItems9, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.quantumpickaxe),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.nanopickaxe, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumaxe),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.nanoaxe, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumshovel),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.nanoshovel, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.spectralpickaxe),
                "TCT", "CDC", "BFB", 'T', Ic2Items.iridiumPlate, 'F', new ItemStack(IUItem.advBatChargeCrystal, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'D', new ItemStack(IUItem.quantumpickaxe, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.spectralaxe),
                "TCT", "CDC", "BFB", 'T', Ic2Items.iridiumPlate, 'F', new ItemStack(IUItem.advBatChargeCrystal, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'D', new ItemStack(IUItem.quantumaxe, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.spectralshovel),
                "TCT", "CDC", "BFB", 'T', Ic2Items.iridiumPlate, 'F', new ItemStack(IUItem.advBatChargeCrystal, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'D', new ItemStack(IUItem.quantumshovel, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.advBatChargeCrystal),
                "BCB", "BAB", "BCB", 'B', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(Ic2Items.chargingLapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum));

        GameRegistry.addRecipe(new ItemStack(IUItem.itemBatChargeCrystal),
                "DCD", "BAB", "ECE", 'E', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'D', Ic2Items.iridiumPlate, 'B', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(IUItem.advBatChargeCrystal, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral));

        GameRegistry.addRecipe(new ItemStack(IUItem.ultDDrill),
                "ACB", "FDF", "ECE", 'E', new ItemStack(IUItem.advQuantumtool), 'F', IUItem.overclockerUpgrade1, 'A', new ItemStack(IUItem.spectralaxe, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.spectralshovel, 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(IUItem.spectralpickaxe, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantumSaber),
                "AB ", "AC ", "DEB", 'C', new ItemStack(Ic2Items.nanoSaber.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'E', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(Blocks.glowstone), 'B', new ItemStack(IUItem.cirsuitQuantum), 'A', new ItemStack(IUItem.compresscarbon));

        GameRegistry.addRecipe(new ItemStack(IUItem.spectralSaber),
                "AB ", "AC ", "DEB", 'C', new ItemStack(IUItem.quantumSaber, 1, OreDictionary.WILDCARD_VALUE), 'E', new ItemStack(IUItem.lapotronCrystal, 1, OreDictionary.WILDCARD_VALUE), 'D', new ItemStack(Blocks.glowstone), 'B', new ItemStack(IUItem.circuitSpectral), 'A', new ItemStack(IUItem.compressIridiumplate));

        GameRegistry.addRecipe(new ItemStack(IUItem.GraviTool),
                "ABA", "CDE", "FGF", 'G', new ItemStack(IUItem.Purifier, 1, OreDictionary.WILDCARD_VALUE), 'F', new ItemStack(IUItem.QuantumItems9), 'D', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'E',  new ItemStack(Ic2Items.electricTreetap.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C',  new ItemStack(Ic2Items.electricWrench.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(Ic2Items.electricHoe.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 3));

        GameRegistry.addRecipe(new ItemStack(IUItem.Purifier),
                "   ", " B ", "A  ", 'A', new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 3), 'B', new ItemStack(Blocks.wool));

        GameRegistry.addRecipe(new ItemStack(IUItem.itemSSP, 1, 3),
                "MDM", "MXM", "MDM", 'D', new ItemStack(IUItem.QuantumItems9), 'M', new ItemStack(IUItem.itemSSP, 1, 1), 'X',
                new ItemStack(Ic2Items.reactorReflector.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.blockmolecular),
                "MXM", "ABA", "MXM", 'M', IC2Items.getItem("advancedMachine"),
                'X', IC2Items.getItem("evTransformer"), 'A',
                IC2Items.getItem("advancedCircuit"), 'B', new ItemStack(IUItem.itemSSP, 1, 3));

        GameRegistry.addRecipe(new ItemStack(IUItem.nano_bow),
                " C ", "BAB", " C ", 'C', new ItemStack(IUItem.QuantumItems9), 'B', Ic2Items.carbonPlate, 'A', new ItemStack(Items.bow));

        GameRegistry.addRecipe(new ItemStack(IUItem.quantum_bow),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.alloysdoubleplate, 1, 5), 'C', new ItemStack(IUItem.cirsuitQuantum), 'D', new ItemStack(IUItem.nano_bow, 1, OreDictionary.WILDCARD_VALUE), 'A', Ic2Items.iridiumPlate, 'B', new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(IUItem.spectral_bow),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.alloysdoubleplate, 1, 8), 'C', new ItemStack(IUItem.circuitSpectral), 'D', new ItemStack(IUItem.quantum_bow, 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(IUItem.compressIridiumplate), 'B', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE));

        int[] meta = {3, 4, 0, 1, 6, 7, 8, 9, 10};
        for (int i = 0; i < 9; i++)
            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.UpgradeKit, 1, i),
                    Ic2Items.wrench, new ItemStack(IUItem.electricblock, 1, meta[i]));

        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 0),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.doubleplate, 1, 6), 'B', new ItemStack(IUItem.casing, 1, 13), 'A', new ItemStack(IUItem.casing, 1, 17));

        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 1),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.doubleplate, 1, 9), 'B', new ItemStack(IUItem.casing, 1, 7), 'A', new ItemStack(IUItem.casing, 1, 6));

        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 2),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 5), 'B', new ItemStack(IUItem.casing, 1, 11), 'A', new ItemStack(IUItem.casing, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 3),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 8), 'B', new ItemStack(IUItem.casing, 1, 5), 'A', new ItemStack(IUItem.casing, 1, 2));
        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 4),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.doubleplate, 1, 16), 'B', new ItemStack(IUItem.casing, 1, 12), 'A', new ItemStack(IUItem.casing, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 5),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.doubleplate, 1, 10), 'B', new ItemStack(IUItem.casing, 1, 4), 'A', new ItemStack(IUItem.casing, 1, 3));
        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 6),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 2), 'B', new ItemStack(IUItem.casing, 1, 11), 'A', new ItemStack(IUItem.casing, 1, 16));
        GameRegistry.addRecipe(new ItemStack(IUItem.solidmatter, 1, 7),
                "ABA", "CDC", "EBE", 'E', new ItemStack(IUItem.core, 1, 5), 'D', IUItem.blockmolecular, 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 1), 'B', new ItemStack(IUItem.casing, 1, 6), 'A', new ItemStack(IUItem.casing, 1, 2));
        GameRegistry.addRecipe(new ItemStack(IUItem.module_schedule),
                "ABA", "EDE", "CBC", 'D', new ItemStack(IUItem.iuingot, 1, 5), 'E', new ItemStack(IUItem.plate, 1, 15), 'C', new ItemStack(IUItem.plastic_plate), 'B', new ItemStack(IUItem.plastic_plate), 'A', new ItemStack(IUItem.plate, 1, 2));
        GameRegistry.addRecipe(Ic2Items.replicator, "EDE", "BBB", "CAC", 'A',
                new ItemStack(IUItem.electricblock, 1, 3), 'B', Ic2Items.teleporter, 'C', Ic2Items.hvTransformer, 'D', Ic2Items.reinforcedGlass, 'E', Ic2Items.reinforcedStone);
        GameRegistry.addRecipe(Ic2Items.advminer, "EDE", "ACB", "EDE", 'A',
                new ItemStack(IUItem.electricblock, 1, 3), 'B', Ic2Items.teleporter, 'C', Ic2Items.advancedMachine, 'D', Ic2Items.miner, 'E', Ic2Items.advancedAlloy);

        GameRegistry.addRecipe(Ic2Items.electricJetpack, "ADA", "ACA", "B B", 'A',
                Ic2Items.casingiron, 'B', Items.glowstone_dust, 'C', new ItemStack(IUItem.electricblock, 1, 2), 'D', Ic2Items.advancedCircuit);

        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 1),
                "ABA", "BCB", "DDD", 'D', new ItemStack(IUItem.doublecompressIridiumplate), 'C', new ItemStack(IUItem.advQuantumtool), 'B', new ItemStack(IUItem.circuitSpectral), 'A', new ItemStack(IUItem.core, 1, 5));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 2),
                "ABA", "BCB", "DDD", 'D', new ItemStack(IUItem.doublecompressIridiumplate), 'C', new ItemStack(IUItem.advnanobox), 'B', new ItemStack(IUItem.circuitSpectral), 'A', new ItemStack(IUItem.core, 1, 5));

        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 3),
                "AAA", "BCB", "EFE", 'F', new ItemStack(IUItem.alloysdoubleplate, 1, 5), 'E', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.module_schedule), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'A', new ItemStack(IUItem.compresscarbon));

        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 4),
                "AAA", "BCB", "EFE", 'F', new ItemStack(IUItem.alloysdoubleplate, 1, 8), 'E', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.module_schedule), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 6), 'A', new ItemStack(IUItem.compresscarbonultra));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 5),
                "ABA", "  ", "  ", 'B', new ItemStack(IUItem.module8), 'A', new ItemStack(IUItem.module7, 1, 3));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 6),
                "AB ", "  ", "  ", 'B', new ItemStack(IUItem.module8), 'A', new ItemStack(IUItem.module7, 1, 3));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 7),
                "ABA", "  ", "  ", 'B', new ItemStack(IUItem.module8), 'A', new ItemStack(IUItem.module7, 1, 8));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 8),
                "ABA", "  ", "  ", 'B', new ItemStack(IUItem.module7, 1, 4), 'A', new ItemStack(IUItem.module7, 1, 6));


        GameRegistry.addRecipe(new ItemStack(IUItem.module9),
                "BCA", "DED", "BCA", 'E', new ItemStack(IUItem.cirsuitQuantum, 1), 'D', new ItemStack(IUItem.doubleplate, 1, 14), 'C', new ItemStack(IUItem.doubleplate, 1, 13), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 2), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 5));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 1),
                "ABA", "DED", "ABA", 'E', IUItem.overclockerUpgrade1, 'D', new ItemStack(IUItem.core, 1, 1), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 6), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 5));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 2),
                "ABA", "CEC", "ABA", 'E', new ItemStack(IUItem.QuantumItems9, 1), 'C', new ItemStack(IUItem.module9, 1, 1), 'B', new ItemStack(IUItem.nanoBox, 1), 'A', new ItemStack(IUItem.core, 1, 2));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 3),
                "ABA", "CEC", "ABA", 'E', new ItemStack(IUItem.quantumtool, 1), 'C', new ItemStack(IUItem.module9, 1, 2), 'B', new ItemStack(IUItem.photoniy_ingot, 1), 'A', new ItemStack(IUItem.core, 1, 6));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 4),
                "ABA", "CEC", "ABA", 'E', new ItemStack(IUItem.circuitSpectral, 1), 'C', new ItemStack(IUItem.module9, 1, 3), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'A', new ItemStack(IUItem.core, 1, 8));


        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 5),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.doublecompressIridiumplate), 'B', new ItemStack(IUItem.module9, 1,4), 'A', new ItemStack(IUItem.core,1, 11));

        GameRegistry.addRecipe(new ItemStack(IUItem.module8),
                "AAA", "BCB", "DED", 'E', new ItemStack(IUItem.compressIridiumplate), 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 9), 'C', new ItemStack(IUItem.block, 1, 2), 'B', new ItemStack(IUItem.QuantumItems9), 'A', new ItemStack(IUItem.doubleplate, 1, 15));
        GameRegistry.addRecipe(new ItemStack(IUItem.module7, 1, 9),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.Purifier, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.nanoBox), 'A', Ic2Items.advancedCircuit);

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 9),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral, 1), 'B', new ItemStack(IUItem.photoniy, 1), 'A', new ItemStack(IUItem.core, 1, 3));
        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 10),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral, 1), 'B', new ItemStack(IUItem.photoniy_ingot, 1), 'A', new ItemStack(IUItem.module9, 1, 9));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 11),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral, 1), 'B', new ItemStack(IUItem.photoniy_ingot, 1), 'A', new ItemStack(IUItem.module9, 1, 10));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 12),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral, 1), 'B', new ItemStack(Blocks.redstone_block, 1), 'A', new ItemStack(Items.paper, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 6),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.photoniy, 1), 'A', new ItemStack(IUItem.core, 1, 4));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 7),
                "ABA", "DCD", "ABA", 'D', new ItemStack(IUItem.module9, 1, 6), 'C', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.photoniy, 1), 'A', new ItemStack(IUItem.core, 1, 4));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 8),
                "ABA", "DCD", "ABA", 'D', new ItemStack(IUItem.module9, 1, 7), 'C', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.doublecompressIridiumplate, 1), 'A', new ItemStack(IUItem.core, 1, 5));

        GameRegistry.addRecipe(new ItemStack(IUItem.module9, 1, 13),
                "A A", " C ", "A A", 'C', new ItemStack(IUItem.circuitSpectral, 1), 'A', new ItemStack(Items.paper, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.module1),
                "AAA", "BCB", " D ", 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'B', new ItemStack(IUItem.doubleplate, 1, 6), 'A', new ItemStack(IUItem.doubleplate, 1, 13));
        GameRegistry.addRecipe(new ItemStack(IUItem.module2),
                "AAA", "BCB", " D ", 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 2), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 9));

        GameRegistry.addRecipe(new ItemStack(IUItem.module3),
                "AAA", "BCB", " D ", 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysplate, 1, 9));

        GameRegistry.addRecipe(new ItemStack(IUItem.module4),
                "AAA", "BCB", " D ", 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'A', new ItemStack(IUItem.doublecompressIridiumplate, 1));
        for (int i = 0; i < 7; i++)
            GameRegistry.addRecipe(new ItemStack(IUItem.module5, 1, i),
                    "BBB", "ACA", "ADA", 'A', new ItemStack(IUItem.lens, 1, i), 'B', new ItemStack(IUItem.doublecompressIridiumplate), 'C', new ItemStack(IUItem.circuitSpectral), 'D', new ItemStack(IUItem.advQuantumtool));

        for (int i = 0; i < 14; i++) {

            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.module6, 1, i),
                    new ItemStack(IUItem.blockpanel, 1, i));
            ItemStack stack1 = new ItemStack(IUItem.module6, 1, i);

            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.blockpanel, 1, i),
                    stack1);
        }
        GameRegistry.addShapelessRecipe(new ItemStack(Ic2Items.reactorCondensatorLap.getItem(), 1, 1),
                new ItemStack(Ic2Items.reactorCondensatorLap.getItem(), 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.lapis_block));
        GameRegistry.addShapelessRecipe(new ItemStack(Ic2Items.reactorCondensator.getItem(), 1, 1),
                new ItemStack(Ic2Items.reactorCondensator.getItem(), 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.redstone_block));

        for (int i = 0; i < RegisterOreDict.list_string.size(); i++) {
            if(i < 16)
            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.iuingot, 9, i),
                    new ItemStack(IUItem.block, 1, i));
            else{
                GameRegistry.addShapelessRecipe(new ItemStack(IUItem.iuingot, 9, i),
                        new ItemStack(IUItem.block1, 1, i-16));

            }
            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.nugget, 9, i),
                    new ItemStack(IUItem.iuingot, 1, i));
            for (int j = 0; j < OreDictionary.getOres("ingot" + RegisterOreDict.list_string.get(i)).size(); j++)
                if(i < 16)
                GameRegistry.addRecipe(new ItemStack(IUItem.block, 1, i),
                        "AAA", "AAA", "AAA", 'A', OreDictionary.getOres("ingot" + RegisterOreDict.list_string.get(i)).get(j));
           else{
                    GameRegistry.addRecipe(new ItemStack(IUItem.block1, 1, i-16),
                            "AAA", "AAA", "AAA", 'A', OreDictionary.getOres("ingot" + RegisterOreDict.list_string.get(i)).get(j));

                }
            GameRegistry.addRecipe(new ItemStack(IUItem.iuingot, 1, i),
                    "AAA", "AAA", "AAA", 'A', new ItemStack(IUItem.nugget, 1, i));
            GameRegistry.addRecipe(new ItemStack(IUItem.smalldust, 1, i),
                    "AAA", "AAA", "AAA", 'A', new ItemStack(IUItem.verysmalldust, 1, i));
            GameRegistry.addRecipe(new ItemStack(IUItem.iudust, 1, i),
                    "AAA", "AAA", "AAA", 'A', new ItemStack(IUItem.smalldust, 1, i));

        }
        for (int i = 0; i < RegisterOreDict.list_string1.size(); i++) {
            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.alloysingot, 9, i),
                    new ItemStack(IUItem.alloysblock, 1, i));
            GameRegistry.addShapelessRecipe(new ItemStack(IUItem.alloysnugget, 9, i),
                    new ItemStack(IUItem.alloysingot, 1, i));
            for (int j = 0; j < OreDictionary.getOres("ingot" + RegisterOreDict.list_string1.get(i)).size(); j++)
                GameRegistry.addRecipe(new ItemStack(IUItem.alloysblock, 1, i),
                        "AAA", "AAA", "AAA", 'A', OreDictionary.getOres("ingot" + RegisterOreDict.list_string1.get(i)).get(j));
            GameRegistry.addRecipe(new ItemStack(IUItem.alloysingot, 1, i),
                    "AAA", "AAA", "AAA", 'A', new ItemStack(IUItem.alloysnugget, 1, i));
        }
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 0),
                " A ", "BBB", " A ", 'A', Ic2Items.glassFiberCableItem, 'B', new ItemStack(IUItem.itemSSP, 1, 0));
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 1),
                " A ", "BCB", " A ", 'C', new ItemStack(IUItem.iuingot, 1, 6), 'A', new ItemStack(IUItem.cable, 1, 0), 'B', Ic2Items.denseplatetin);

        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 2),
                " A ", "BCB", " A ", 'C', Ic2Items.denseplatetin, 'A', new ItemStack(IUItem.cable, 1, 1), 'B', Ic2Items.advancedAlloy);
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 3),
                "DAD", "BCB", "DAD", 'D', Ic2Items.denseplategold, 'C', Ic2Items.advancedAlloy, 'A', new ItemStack(IUItem.cable, 1, 2), 'B', Ic2Items.denseplatelead);
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 4),
                "DAD", "BCB", "DAD", 'D', new ItemStack(IUItem.alloysingot, 1, 2), 'C', Ic2Items.carbonPlate, 'A', new ItemStack(IUItem.cable, 1, 3), 'B', new ItemStack(IUItem.iuingot, 1, 12));
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 5),
                " A ", "BCB", " A ", 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'A', new ItemStack(IUItem.cable, 1, 4), 'B', Ic2Items.denseplateadviron);
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 6),
                "DAD", "BCB", "DAD", 'D', Ic2Items.carbonPlate, 'C', new ItemStack(IUItem.alloysingot, 1, 5), 'A', new ItemStack(IUItem.cable, 1, 5), 'B', new ItemStack(IUItem.alloysingot, 1, 8));

        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 7),
                "A A", "BCB", "A A", 'C', new ItemStack(IUItem.photoniy_ingot), 'B', new ItemStack(IUItem.photoniy), 'A', new ItemStack(IUItem.cable, 1, 6));

        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 2, 8),
                "BBB", "AAA", "BBB", 'A', new ItemStack(IUItem.photoniy_ingot), 'B', new ItemStack(IUItem.cable, 2, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 1, 9),
                "BBB", "ACA", "BBB", 'C', new ItemStack(IUItem.cirsuitQuantum), 'A', new ItemStack(IUItem.photoniy_ingot), 'B', new ItemStack(IUItem.cable, 2, 8));
        GameRegistry.addRecipe(new ItemStack(IUItem.cable, 1, 10),
                "BBB", "ACA", "BBB", 'C', new ItemStack(IUItem.alloysdoubleplate, 1, 6), 'A', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.cable, 2, 9));
        GameRegistry.addRecipe(IUItem.reactorCoolanttwelve,
                "BBB", "ACA", "BBB", 'C', Ic2Items.plateiron, 'B', Ic2Items.platetin, 'A', new ItemStack(Ic2Items.reactorCoolantSix.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.reactorCoolantmax,
                "BBB", "ACA", "BBB", 'C', Ic2Items.plateiron, 'B', Ic2Items.platetin, 'A', new ItemStack(IUItem.reactorCoolanttwelve.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.adv_lappack),
                "ABA", "CEC", "ADA", 'E', new ItemStack(IUItem.QuantumItems9), 'D', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.alloysplate, 1, 6), 'B', new ItemStack(Ic2Items.lapPack.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(IUItem.alloysplate, 1, 5));
        GameRegistry.addRecipe(new ItemStack(IUItem.imp_lappack),
                "ABA", "CEC", "ABA", 'E', new ItemStack(IUItem.adv_lappack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.QuantumItems9, 1), 'B', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 9));
        GameRegistry.addRecipe(new ItemStack(IUItem.per_lappack),
                "ABA", "CEC", "ABA", 'E', new ItemStack(IUItem.imp_lappack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1), 'B', new ItemStack(IUItem.compressIridiumplate, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));

        GameRegistry.addRecipe(new ItemStack(IUItem.advancedSolarHelmet),
                " A ", "BCB", "DED", 'D', new ItemStack(IUItem.QuantumItems9), 'E', new ItemStack(IUItem.compresscarbonultra), 'C', new ItemStack(IUItem.NanoHelmet, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.compresscarbon), 'A', new ItemStack(IUItem.blockpanel));

        GameRegistry.addRecipe(new ItemStack(IUItem.hybridSolarHelmet),
                " A ", "BCB", "DED", 'D', new ItemStack(IUItem.cirsuitQuantum), 'E', new ItemStack(IUItem.compresscarbonultra), 'C', new ItemStack(Ic2Items.quantumHelmet.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.iridiumPlate, 'A', new ItemStack(IUItem.blockpanel, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.ultimateSolarHelmet),
                " A ", "DCD", "BEB", 'D', new ItemStack(IUItem.cirsuitQuantum), 'E', new ItemStack(IUItem.compresscarbonultra), 'C', new ItemStack(IUItem.hybridSolarHelmet, 1, OreDictionary.WILDCARD_VALUE), 'B', Ic2Items.iridiumPlate, 'A', new ItemStack(IUItem.blockpanel, 1, 2));

        GameRegistry.addRecipe(IUItem.iridium,
                " A ", "ABA", " A ", 'A', Ic2Items.iridiumPlate, 'B', new ItemStack(Ic2Items.carbonrotor.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.compressiridium,
                " A ", "ABA", " A ", 'A', new ItemStack(IUItem.compresscarbon), 'B', new ItemStack(IUItem.iridium.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.spectral,
                " C ", "ABA", " C ", 'C', new ItemStack(IUItem.compressIridiumplate), 'A', new ItemStack(IUItem.advnanobox), 'B', new ItemStack(IUItem.compressiridium.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.myphical,
                " C ", "ABA", " C ", 'C', new ItemStack(IUItem.cirsuitQuantum), 'A', new ItemStack(IUItem.quantumtool), 'B', new ItemStack(IUItem.spectral.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.photon,
                " C ", "CBC", " C ", 'C', new ItemStack(IUItem.quantumtool), 'B', new ItemStack(IUItem.myphical.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.neutron,
                "ACA", "CBC", "ACA", 'A', Ic2Items.iridiumPlate, 'C', new ItemStack(IUItem.cirsuitQuantum), 'B', new ItemStack(IUItem.photon.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.barionrotor,
                " C ", "CBC", " C ", 'C', new ItemStack(IUItem.advQuantumtool), 'B', new ItemStack(IUItem.neutron.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.adronrotor,
                "ACA", "CBC", "ACA", 'A', new ItemStack(IUItem.doublecompressIridiumplate), 'C', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.barionrotor.getItem(), 1, OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(IUItem.ultramarinerotor,
                "ACA", "CBC", "ACA", 'A', new ItemStack(IUItem.doublecompressIridiumplate), 'C', new ItemStack(IUItem.photoniy_ingot), 'B', new ItemStack(IUItem.adronrotor.getItem(), 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(IUItem.spectralSolarHelmet),
                " A ", "DCD", "BEB", 'D', new ItemStack(IUItem.cirsuitQuantum), 'E', new ItemStack(IUItem.compresscarbonultra), 'C', new ItemStack(IUItem.ultimateSolarHelmet, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doublecompressIridiumplate, 1), 'A', new ItemStack(IUItem.blockpanel, 1, 4));

        GameRegistry.addRecipe(new ItemStack(IUItem.singularSolarHelmet),
                " A ", "DCD", "BDB", 'D', new ItemStack(IUItem.circuitSpectral), 'E', new ItemStack(IUItem.compresscarbonultra), 'C', new ItemStack(IUItem.spectralSolarHelmet, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doublecompressIridiumplate, 1), 'A', new ItemStack(IUItem.blockpanel, 1, 6));

        GameRegistry.addRecipe(new ItemStack(IUItem.machines, 1, 5),
                "BCB", "DAD", "BCB", 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.doublecompressIridiumplate), 'B', new ItemStack(IUItem.photoniy_ingot), 'A', new ItemStack(IUItem.machines, 1, 3));

        GameRegistry.addRecipe(new ItemStack(IUItem.machines, 1, 1),
                "DED", "BCB", "AAA", 'E', new ItemStack(IUItem.core, 1, 5), 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 1), 'B', new ItemStack(IUItem.cirsuitQuantum), 'C', Ic2Items.massFabricator, 'A', new ItemStack(IUItem.quantumtool));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines, 1, 2),
                "DED", "BCB", "AAA", 'E', new ItemStack(IUItem.core, 1, 7), 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 7), 'B', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines, 1, 1), 'A', new ItemStack(IUItem.advQuantumtool));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines, 1, 3),
                "DED", "BCB", "AFA", 'F', new ItemStack(IUItem.doublecompressIridiumplate), 'E', new ItemStack(IUItem.core, 1, 8), 'D', new ItemStack(IUItem.alloysdoubleplate, 1, 8), 'B', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines, 1, 2), 'A', new ItemStack(IUItem.advQuantumtool));
        GameRegistry.addRecipe(new ItemStack(IUItem.blocksintezator),
                "ABA", "BCB", "ABA", 'C', new ItemStack(IUItem.circuitSpectral), 'A', Ic2Items.advancedMachine, 'B', new ItemStack(IUItem.core, 1, 8));

        ItemStack[] stackmachine = {Ic2Items.macerator, Ic2Items.compressor, Ic2Items.electroFurnace, Ic2Items.extractor, Ic2Items.metalformer};
        for (int k = 0; k < BlockMoreMachine.names.length; k++) {
            if (k % 3 == 1) {


                GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, k),
                        "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base, 1, k - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
            }
            if (k % 3 == 2) {

                GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, k),
                        "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base, 1, k - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

            }
            if (k % 3 == 0) {
                GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, k),
                        "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', stackmachine[k / 3], 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));

            }

        }
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 0),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', Ic2Items.recycler, 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base1, 1, 0), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 2),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base1, 1, 1), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

        GameRegistry.addRecipe(new ItemStack(IUItem.blockSE),
                "ABA", "CDC", "EFE", 'F', Ic2Items.advancedCircuit, 'E', Ic2Items.advancedAlloy, 'D', Ic2Items.solarPanel, 'C', Ic2Items.platebronze, 'B', new ItemStack(IUItem.toriy), 'A', Ic2Items.carbonPlate);
        GameRegistry.addRecipe(new ItemStack(IUItem.AdvblockSE),
                "AAA", "BCB", "EFE", 'C', new ItemStack(IUItem.iuingot,1,0), 'F', Ic2Items.advancedCircuit, 'E', Ic2Items.iridiumPlate, 'A', new ItemStack(IUItem.itemSSP,1,3), 'B', new ItemStack(IUItem.blockSE));
        GameRegistry.addRecipe(new ItemStack(IUItem.ImpblockSE),
                "AAA", "BCB", "EFE", 'C', new ItemStack(IUItem.iuingot,1,5), 'F', new ItemStack(IUItem.QuantumItems9), 'E', Ic2Items.iridiumPlate, 'A', new ItemStack(IUItem.itemSSP,1,3), 'B', new ItemStack(IUItem.AdvblockSE));
        ItemStack[] stacks1 = {Ic2Items.ForgeHammer, Ic2Items.ForgeHammer, Ic2Items.cutter};
        ItemStack[] stacks2 = {Ic2Items.ForgeHammer, Ic2Items.cutter, Ic2Items.cutter};
        for (int i = 0; i < 12; i++) {
            int k = i % 4;
            switch (k) {
                case 0:
                    int m = i / 4;
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base2, 1, i),
                            "ABA", "CDC", "EEE", 'E', Ic2Items.leadIngot, 'D', Ic2Items.machine, 'C', Ic2Items.electronicCircuit, 'A', stacks1[m], 'B', stacks2[m]);

                    break;
                case 1:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base2, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.machines_base2, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));

                    break;
                case 2:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base2, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base2, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));

                    break;
                case 3:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base2, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base2, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

                    break;
            }
        }

        int[] meta2 = {2,5,3,4,0,1,6,7,8,9,10};
        int[] meta3 = {2,3,4,5,0,1,6,7,8,9,10};
        ItemStack[] stacks3 = {Ic2Items.electronicCircuit,Ic2Items.electronicCircuit,Ic2Items.advancedCircuit,Ic2Items.advancedCircuit,new ItemStack(IUItem.QuantumItems9),new ItemStack(IUItem.QuantumItems9),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.circuitSpectral),new ItemStack(IUItem.circuitSpectral),new ItemStack(IUItem.circuitSpectral)};
        for(int i  = 0; i < 11;i++)  {
            GameRegistry.addRecipe(new ItemStack(IUItem.Chargepadelectricblock, 1,  meta3[i]),
                    "ABA", "CDC", 'B', Blocks.stone_pressure_plate, 'A', stacks3[i], 'D',
                    new ItemStack(IUItem.electricblock, 1, meta2[i]), 'C', Ic2Items.rubber);

        }
         stacks3 = new ItemStack[]{new ItemStack(IUItem.QuantumItems9), new ItemStack(IUItem.QuantumItems9),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.circuitSpectral),new ItemStack(IUItem.circuitSpectral)};

        for(int i = 0; i < 7;i++){
            if(i < 3) {
                if(i == 0){
                    GameRegistry.addRecipe(new ItemStack(IUItem.tranformer,1,i),
                            " A ","BCD"," A ",'D',new ItemStack(Ic2Items.lapotronCrystal.getItem(),1,OreDictionary.WILDCARD_VALUE),'C',Ic2Items.evTransformer,'B',stacks3[i],'A',new ItemStack(IUItem.cable,1,i) );

                }else{
                    GameRegistry.addRecipe(new ItemStack(IUItem.tranformer,1,i),
                            " A ","BCD"," A ",'D',new ItemStack(Ic2Items.lapotronCrystal.getItem(),1,OreDictionary.WILDCARD_VALUE),'C',new ItemStack(IUItem.tranformer,1,i-1),'B',stacks3[i],'A',new ItemStack(IUItem.cable,1,i) );

                }

            }else{
                GameRegistry.addRecipe(new ItemStack(IUItem.tranformer,1,i),
                        " A ","BCD"," A ",'D',new ItemStack(IUItem.lapotronCrystal,1,OreDictionary.WILDCARD_VALUE),'C',new ItemStack(IUItem.tranformer,1,i-1),'B',stacks3[i],'A',new ItemStack(IUItem.cable,1,i) );

            }
        }
        GameRegistry.addRecipe(new ItemStack(IUItem.advchamberblock),
                " A ","ABA"," A ",'A',new ItemStack(IUItem.nanoBox),'B',Ic2Items.reactorChamber );
        GameRegistry.addRecipe(new ItemStack(IUItem.impchamberblock),
                "CAC","ABA","CAC",'C',new ItemStack(IUItem.photoniy),'A',new ItemStack(IUItem.quantumtool),'B',new ItemStack(IUItem.advchamberblock) );
        GameRegistry.addRecipe(new ItemStack(IUItem.perchamberblock),
                "CAC","ABA","CAC",'C',new ItemStack(IUItem.photoniy_ingot),'A',new ItemStack(IUItem.advQuantumtool),'B',new ItemStack(IUItem.impchamberblock) );

        for (int i = 0; i < 8; i++) {
            int k = i % 4;
            switch (k) {
                case 0:
                    if(i == 0){
                        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base3,1,0),
                                "ABA","DED","CCC",'E',Ic2Items.machine,'D',new ItemStack(IUItem.QuantumItems9),'C',new ItemStack(IUItem.alloysingot,1,7),'A',Ic2Items.advancedAlloy,'B',new ItemStack(Items.diamond_axe) );

                    }else{
                        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base3,1,4),
                                "ABA","CDC","FEF",'F',new ItemStack(IUItem.compresscarbon),'E',Ic2Items.recycler,'D',Ic2Items.compressor,'C',new ItemStack(IUItem.QuantumItems9),'B',new ItemStack(IUItem.alloysplate,1,6),'A',new ItemStack(IUItem.alloysplate,1,0) );

                    }
                    break;
                case 1:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base3, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.machines_base3, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));

                    break;
                case 2:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base3, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base3, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));

                    break;
                case 3:
                    GameRegistry.addRecipe(new ItemStack(IUItem.machines_base3, 1, i),
                            "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base3, 1, i - 1), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

                    break;
            }
        }
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1,1,6),
                "ABA","CDC","ABA",'C',new ItemStack(IUItem.circuitSpectral),'D',Ic2Items.macerator,'B',new ItemStack(IUItem.core,1,4),'A',new ItemStack(IUItem.advQuantumtool) );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 7),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.machines_base1, 1, 6), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 8),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base1, 1,7), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 9),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base1, 1, 8), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));



        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 3),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.machines_base1, 1, 2), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 4),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base1, 1,3), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base1, 1, 5),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base1, 1, 4), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,9),
                "ACA","BEB","ADA",'E',Ic2Items.advancedMachine,'D',new ItemStack(IUItem.module9,1,13),'C',new ItemStack(IUItem.module9,1,12),'B',new ItemStack(IUItem.cirsuitQuantum),'A',new ItemStack(IUItem.core,1,4) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,12),
                "ABA","CEC","DDD",'E',Ic2Items.advancedMachine,'D',Ic2Items.advancedAlloy,'C',new ItemStack(IUItem.QuantumItems9),'A',new ItemStack(IUItem.plate,1,6),'B',new ItemStack(Items.diamond) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,11),
                "DCD","BAB","DCD",'C',new ItemStack(IUItem.block,1,10),'D',new ItemStack(IUItem.neutroniumingot),'B',new ItemStack(IUItem.circuitSpectral),'A',Ic2Items.advancedMachine );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,10),
                "ACB","DED","BCA",'E',Ic2Items.advancedMachine,'D',Ic2Items.advancedCircuit,'C',new ItemStack(Ic2Items.reactorUraniumSimple.getItem(),1,OreDictionary.WILDCARD_VALUE),'A',Ic2Items.advancedAlloy,'B',Ic2Items.carbonPlate );

        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,3),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(IUItem.machines, 1,4), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,4),
                "AAA","CDC","BBB",'D',Ic2Items.geothermalGenerator,'C',new ItemStack(IUItem.QuantumItems9),'A',Ic2Items.advancedAlloy,'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,5),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,4),'C',new ItemStack(IUItem.cirsuitQuantum),'A',new ItemStack(IUItem.compresscarbon),'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,6),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,5),'C',new ItemStack(IUItem.circuitSpectral),'A',new ItemStack(IUItem.compresscarbon),'B',new ItemStack(IUItem.compresscarbonultra) );

        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,10),
                "AAA","CDC","BBB",'D',Ic2Items.generator,'C',new ItemStack(IUItem.QuantumItems9),'A',Ic2Items.advancedAlloy,'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,11),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.machines,1,10),'C',new ItemStack(IUItem.cirsuitQuantum),'A',new ItemStack(IUItem.compresscarbon),'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,12),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.machines,1,11),'C',new ItemStack(IUItem.circuitSpectral),'A',new ItemStack(IUItem.compresscarbon),'B',new ItemStack(IUItem.compresscarbonultra) );

        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,4),
                "AAA","CDC","BBB",'D',Ic2Items.geothermalGenerator,'C',new ItemStack(IUItem.QuantumItems9),'A',Ic2Items.advancedAlloy,'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,5),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,4),'C',new ItemStack(IUItem.cirsuitQuantum),'A',new ItemStack(IUItem.compresscarbon),'B',Ic2Items.carbonPlate );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,6),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,5),'C',new ItemStack(IUItem.circuitSpectral),'A',new ItemStack(IUItem.compresscarbon),'B',new ItemStack(IUItem.compresscarbonultra) );

        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,7),
                "AAA","CDC","BBB",'D',Ic2Items.nuclearReactor,'C',new ItemStack(IUItem.QuantumItems9),'A',Ic2Items.iridiumPlate,'B',new ItemStack(IUItem.nanoBox) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,8),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,7),'C',new ItemStack(IUItem.cirsuitQuantum),'A',new ItemStack(IUItem.compressIridiumplate),'B',new ItemStack(IUItem.quantumtool));
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine,1,9),
                "AAA","CDC","BBB",'D',new ItemStack(IUItem.basemachine,1,8),'C',new ItemStack(IUItem.circuitSpectral),'A',new ItemStack(IUItem.doublecompressIridiumplate),'B',new ItemStack(IUItem.advQuantumtool) );


        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, 0),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', Ic2Items.kineticGenerator, 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.machines_base, 1,0), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.machines_base, 1, 2),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'C', new ItemStack(IUItem.machines_base, 1, 1), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,7),
                "ABA","DED","CCC",'E',Ic2Items.generator,'D',new ItemStack(IUItem.QuantumItems9),'C',new ItemStack(IUItem.nanoBox),'B',new ItemStack(Blocks.cobblestone),'A',new ItemStack(IUItem.compresscarbonultra) );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,4),
                "AAA","BDB","CCC",'B',Ic2Items.advancedCircuit,'D',Ic2Items.inductionFurnace,'A',Ic2Items.advancedAlloy,'C',new ItemStack(IUItem.nanoBox) );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,4),
                "AAA","BDB","CCC",'B',Ic2Items.advancedCircuit,'D',Ic2Items.inductionFurnace,'A',Ic2Items.advancedAlloy,'C',new ItemStack(IUItem.nanoBox) );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,6),
                "AAA","CDC","BBB",'D',Ic2Items.machine,'C',Ic2Items.electronicCircuit,'B',new ItemStack(IUItem.nanoBox,1) ,'A',new ItemStack(IUItem.plate,1,9) );
        GameRegistry.addRecipe(new ItemStack(IUItem.machines,1,8),
                "EDE","BAB","CCC",'E',new ItemStack(IUItem.cirsuitQuantum),'D',Ic2Items.advminer,'C',new ItemStack(IUItem.compressIridiumplate),'B',new ItemStack(IUItem.advQuantumtool),'A',new ItemStack(IUItem.core,1,7) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,0),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.itemSSP,1,1),'C',new ItemStack(IUItem.stik,1,0),'A',new ItemStack(IUItem.sunnarium,1,2) );
        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,1),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,0),'C',new ItemStack(IUItem.stik,1,6),'A',new ItemStack(IUItem.sunnariumpanel,1,0) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,2),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,1),'C',new ItemStack(IUItem.stik,1,9),'A',new ItemStack(IUItem.sunnariumpanel,1,1) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,3),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,2),'C',new ItemStack(IUItem.stik,1,11),'A',new ItemStack(IUItem.sunnariumpanel,1,2) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,4),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,3),'C',new ItemStack(IUItem.stik,1,13),'A',new ItemStack(IUItem.sunnariumpanel,1,3) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,5),
                "CEC","ABA","CEC",'E',new ItemStack(IUItem.proton),'B',new ItemStack(IUItem.photonglass,1,4),'C',new ItemStack(IUItem.stik,1,16),'A',new ItemStack(IUItem.sunnariumpanel,1,4) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,6),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,5),'C',new ItemStack(IUItem.stik,1,4),'A',new ItemStack(IUItem.sunnariumpanel,1,5) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,7),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,6),'C',new ItemStack(IUItem.stik,1,12),'A',new ItemStack(IUItem.sunnariumpanel,1,6) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,8),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,7),'C',new ItemStack(IUItem.stik,1,11),'A',new ItemStack(IUItem.sunnariumpanel,1,7) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,9),
                "CDC","ABA","CDC",'D',new ItemStack(IUItem.neutroniumingot,1),'B',new ItemStack(IUItem.photonglass,1,8),'C',new ItemStack(IUItem.stik,1,18),'A',new ItemStack(IUItem.sunnariumpanel,1,8) );

        GameRegistry.addRecipe(new ItemStack(IUItem.photonglass,1,10),
                "CCC","ABA","CCC",'B',new ItemStack(IUItem.photonglass,1,9),'C',new ItemStack(IUItem.stik,1,15),'A',new ItemStack(IUItem.sunnariumpanel,1,9) );
      //
        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,6),
                "CCC","ABA","DDD",'D',Ic2Items.platebronze,'C',Ic2Items.carbonPlate,'B',Ic2Items.electronicCircuit,'A',new ItemStack(IUItem.basecircuit,1,0) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,7),
                "CCC","ABA","DDD",'D',Ic2Items.plateadviron,'C',Ic2Items.carbonPlate,'B',Ic2Items.advancedCircuit,'A',new ItemStack(IUItem.basecircuit,1,1) );

        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,8),
                "CCC","ABA","DDD",'D',new ItemStack(IUItem.iuingot,1,12),'C',Ic2Items.carbonPlate,'B',new ItemStack(IUItem.QuantumItems9),'A',new ItemStack(IUItem.basecircuit,1,2) );

        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,3),
                "BBB","BAB","BBB",'B',new ItemStack(IUItem.stik,1,10),'A',new ItemStack(IUItem.basecircuit,1,0) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,4),
                "BBB","BAB","BBB",'B',new ItemStack(IUItem.stik,1,14),'A',new ItemStack(IUItem.basecircuit,1,1) );
        GameRegistry.addRecipe(new ItemStack(IUItem.basecircuit,1,5),
                "BBB","BAB","BBB",'B',new ItemStack(IUItem.stik,7,14),'A',new ItemStack(IUItem.basecircuit,1,2) );

        //
        ItemStack[] circuit = {Ic2Items.electronicCircuit,Ic2Items.electronicCircuit,Ic2Items.advancedCircuit,Ic2Items.advancedCircuit,new ItemStack(IUItem.QuantumItems9),new ItemStack(IUItem.QuantumItems9),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.cirsuitQuantum),new ItemStack(IUItem.circuitSpectral),new ItemStack(IUItem.circuitSpectral),new ItemStack(IUItem.core,1,10),new ItemStack(IUItem.core,1,11),new ItemStack(IUItem.core,1,12),new ItemStack(IUItem.core,1,13)};
      ItemStack[] iridium = {new ItemStack(IUItem.sunnarium,1,1),Ic2Items.iridiumOre,Ic2Items.iridiumPlate,Ic2Items.iridiumPlate,new ItemStack(IUItem.compressIridiumplate),new ItemStack(IUItem.compressIridiumplate),new ItemStack(IUItem.compressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate),new ItemStack(IUItem.doublecompressIridiumplate)};

        for(int i =0; i < 14;i++){
            if(i != 0) {
                GameRegistry.addRecipe(new ItemStack(IUItem.blockpanel, 1, i),
                        "ABA", "CDC", "DED", 'A', new ItemStack(IUItem.photonglass, 1, i),'B',new ItemStack(IUItem.core,1,i),'C',iridium[i],'D',new ItemStack(IUItem.blockpanel,1,i-1),'E',circuit[i]);
                GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, i),
                        "ABA", "C C", "DED", 'A', new ItemStack(IUItem.photonglass, 1, i),'B',new ItemStack(IUItem.core,1,i),'C',iridium[i],'D',new ItemStack(IUItem.blockpanel,1,i-1),'E',circuit[i]);
            }else{
                GameRegistry.addRecipe(new ItemStack(IUItem.blockpanel, 1, i),
                        "ABA", "CDC", "DED", 'A', new ItemStack(IUItem.photonglass, 1, i),'B',new ItemStack(IUItem.core,1,i),'C',iridium[i],'D', Ic2Items.solarPanel,'E',circuit[i]);
                GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, i),
                        "ABA", "C C", "DED", 'A', new ItemStack(IUItem.photonglass, 1, i),'B',new ItemStack(IUItem.core,1,i),'C',iridium[i],'D',Ic2Items.solarPanel,'E',circuit[i]);

            }
        }
        //
       for(int i =0; i < 3;i++) {
           GameRegistry.addRecipe(new ItemStack(IUItem.preciousblock, 1, i),
                   "AAA", "AAA", "AAA",'A', new ItemStack(IUItem.preciousgem, 1, i));
           GameRegistry.addShapelessRecipe(new ItemStack(IUItem.preciousgem, 9, i),new ItemStack(IUItem.preciousblock, 1, i));
       }
        GameRegistry.addRecipe(new ItemStack(IUItem.sunnariummaker),
                "CCC","BAB","DDD",'D',Ic2Items.advancedAlloy,'C',Ic2Items.solarPanel,'B',Ic2Items.advancedCircuit,'A',Ic2Items.machine );
        GameRegistry.addRecipe(new ItemStack(IUItem.sunnariumpanelmaker),
                "BAB","DCD",'D',Ic2Items.carbonPlate,'C',new ItemStack(IUItem.blockSE),'B',Ic2Items.advancedCircuit,'A', new ItemStack(IUItem.sunnariummaker) );
        GameRegistry.addRecipe(new ItemStack(IUItem.module7),
                " C ","BAB","DCD",'D',new ItemStack(IUItem.plate,1,5),'C',new ItemStack(IUItem.plate,1,13),'B',new ItemStack(IUItem.plate,1,4),'A', new ItemStack(IUItem.module_schedule) );
        GameRegistry.addRecipe(Ic2Items.metalformer,
                " D ","ABC","EFE",'F',new ItemStack(IUItem.casing,1,17),'E',new ItemStack(IUItem.casing,1,4),'D', new ItemStack(IUItem.QuantumItems9),'A', new ItemStack(IUItem.machines_base2,1,0) ,'B', new ItemStack(IUItem.machines_base2,1,4),'C', new ItemStack(IUItem.machines_base2,1,8));
        GameRegistry.addShapelessRecipe(new ItemStack(IUItem.lathingprogram),new ItemStack(Ic2Items.odScanner.getItem(),1,OreDictionary.WILDCARD_VALUE),new ItemStack(Ic2Items.turningBlankIron.getItem(),1,344865));
        GameRegistry.addShapelessRecipe(new ItemStack(IUItem.lathingprogram),new ItemStack(Ic2Items.odScanner.getItem(),1,OreDictionary.WILDCARD_VALUE),new ItemStack(Ic2Items.turningBlankIron.getItem(),1,275508));
        GameRegistry.addShapelessRecipe(new ItemStack(IUItem.lathingprogram),new ItemStack(Ic2Items.odScanner.getItem(),1,OreDictionary.WILDCARD_VALUE),new ItemStack(Ic2Items.turningBlankIron.getItem(),1,274978));

        GameRegistry.addRecipe(new ItemStack(IUItem.paints),
              "AAA","A A","AAA",'A',new ItemStack(Items.iron_ingot));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,1),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,12));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,2),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,11));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,3),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,10));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,4),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,1));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,5),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,14));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,6),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,4));
        GameRegistry.addRecipe(new ItemStack(IUItem.paints,1,7),"AB ",'A',new ItemStack(IUItem.paints),'B',new ItemStack(Items.dye,1,5));

        GameRegistry.addRecipe(new ItemStack(IUItem.quarrymodule),"BAB","DCD",'D',new ItemStack(IUItem.iuingot,1,18),'C',new ItemStack(IUItem.module_schedule),'B', new ItemStack(IUItem.QuantumItems9),'A', new ItemStack(IUItem.machines,1,8)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.analyzermodule),"BAB","DCD",'D',new ItemStack(IUItem.iuingot,1,18),'C',new ItemStack(IUItem.module_schedule),'B', new ItemStack(IUItem.cirsuitQuantum),'A', new ItemStack(IUItem.basemachine1,1,2)
        );

        GameRegistry.addRecipe(new ItemStack(IUItem.machinekit, 1, 0),
                "ABA", "D D", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.machinekit, 1, 1),
                "ABA", "D D", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));


        GameRegistry.addRecipe(new ItemStack(IUItem.machinekit, 1, 2),
                "ABA", "D D", "EEE", 'E', new ItemStack(IUItem.advQuantumtool), 'D', new ItemStack(IUItem.circuitSpectral), 'B', new ItemStack(IUItem.doubleplate, 1, 12), 'A', new ItemStack(IUItem.doubleplate, 1, 16));

        GameRegistry.addRecipe(new ItemStack(IUItem.hazmathelmet),
                "AAA","BCB","DDD",'A', new ItemStack(IUItem.plate,1,0),'B', new ItemStack(IUItem.plate,1,9),'D', new ItemStack(IUItem.plate,1,6),'C', new ItemStack(Ic2Items.hazmatHelmet.getItem(),1,OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.hazmatchest),
                "AAA","BCB","DDD",'A', new ItemStack(IUItem.plate,1,0),'B', new ItemStack(IUItem.plate,1,9),'D', new ItemStack(IUItem.plate,1,6),'C', new ItemStack(Ic2Items.hazmatChestplate.getItem(),1,OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.hazmatleggins),
                "AAA","BCB","DDD",'A', new ItemStack(IUItem.plate,1,0),'B', new ItemStack(IUItem.plate,1,9),'D', new ItemStack(IUItem.plate,1,6),'C', new ItemStack(Ic2Items.hazmatLeggings.getItem(),1,OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.hazmatboosts),
                "AAA","BCB","DDD",'A', new ItemStack(IUItem.plate,1,0),'B', new ItemStack(IUItem.plate,1,9),'D', new ItemStack(IUItem.plate,1,6),'C', new ItemStack(Ic2Items.hazmatBoots.getItem(),1,OreDictionary.WILDCARD_VALUE));
        GameRegistry.addRecipe(new ItemStack(IUItem.entitymodules),"ABA","DCD","EBE",'A', Ic2Items.advancedCircuit,'B', new ItemStack(IUItem.alloyscasing,1,2),'C',new ItemStack(IUItem.module_schedule),'D', new ItemStack(IUItem.iuingot,1,4),'E', new ItemStack(IUItem.alloyscasing,1,4)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.entitymodules,1,1),"ABA","DCD","EBE",'A',new ItemStack(IUItem.QuantumItems9),'B', new ItemStack(IUItem.alloyscasing,1,6),'C',new ItemStack(IUItem.module_schedule),'D', new ItemStack(IUItem.iuingot,1,18),'E', new ItemStack(IUItem.alloyscasing,1,9));
        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules,1,0),"ABA",'B',new ItemStack(IUItem.module9,1,6),'B',new ItemStack(IUItem.alloysdoubleplate,1,4)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules, 1, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1),'C', new ItemStack(IUItem.spawnermodules,1,0));


        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules, 1, 2),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7),'C', new ItemStack(IUItem.spawnermodules,1,1));
        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules,1,3),"ABA",'B',new ItemStack(IUItem.module9,1,5),'A',new ItemStack(IUItem.alloysdoubleplate,1,4)
        );

        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules,1,4)," C ","ABA"," C ",'B',new ItemStack(IUItem.module_schedule,1),'A',new ItemStack(IUItem.alloysdoubleplate,1,4),'C', new ItemStack(Items.experience_bottle)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.spawnermodules, 1, 5),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1),'C', new ItemStack(IUItem.spawnermodules,1,4));

        GameRegistry.addRecipe(new ItemStack(IUItem.oilgetter)," D ","ABA"," D ",'D',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1),'A',Ic2Items.carbonPlate,'B',Ic2Items.machine
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.oilquarry),"CDC","ABA","FDF",'C',Ic2Items.electronicCircuit,'D',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1),'A',Ic2Items.advancedAlloy,'B',Ic2Items.miner,'F',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 10)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.oilrefiner),"ADA","ABA","ACA",'A', new ItemStack(IUItem.cell_all),'B', Ic2Items.advancedMachine,'C',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1),'D', Ic2Items.electronicCircuit
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.oiladvrefiner),"ADA","ABA","ACA",'A', new ItemStack(IUItem.cell_all),'B', Ic2Items.advancedMachine,'C',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1),'D', Ic2Items.advancedCircuit
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,5),"BDB","BAB","BCB",'A',Ic2Items.generator,'B', new ItemStack(IUItem.cell_all,1,4),'C', new ItemStack(IUItem.alloysplate,1,5),'D', new ItemStack(IUItem.alloysplate,1,3)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,4),"BDB","BAB","BCB",'A',Ic2Items.generator,'B', new ItemStack(IUItem.cell_all,1,5),'C', new ItemStack(IUItem.alloysplate,1,4),'D', new ItemStack(IUItem.alloysplate,1,8)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,9),"BCB","BAB","BCB",'A',Ic2Items.generator,'B', new ItemStack(IUItem.cell_all,1,9),'C', new ItemStack(IUItem.nanoBox)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,11),"CDC","BAB","CDC",'D',Ic2Items.advancedAlloy,'A',Ic2Items.fluiddistributor,'B',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 5),'C',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,15),"EDF","CAB","FDE",'F',Ic2Items.electronicCircuit, 'A', Ic2Items.advancedMachine,'B', new ItemStack(IUItem.cathode,1,OreDictionary.WILDCARD_VALUE),'C', new ItemStack(IUItem.anode,1,OreDictionary.WILDCARD_VALUE),'D',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 9),'E',new ItemStack(Ic2Items.powerunitsmall.getItem(), 1, 1)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.cathode),"B B","BAB","B B",'A', new ItemStack(IUItem.cell_all,1,0),'B',new ItemStack(IUItem.alloysplate,1,1)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.anode),"B B","BAB","B B",'A', new ItemStack(IUItem.cell_all,1,0),'B',new ItemStack(IUItem.alloysplate,1,3)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,13),"BEB","DAD","BCB",'E',Ic2Items.advancedCircuit,'A', Ic2Items.advancedMachine,'B', new ItemStack(IUItem.plast),'C',new ItemStack(Ic2Items.powerunitsmall.getItem()),'D',Ic2Items.reactorPlatingExplosive
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.blockdoublemolecular),"BDB","CAC","BDB",'C',new ItemStack(IUItem.cirsuitQuantum),'B',Ic2Items.advancedMachine,'A', new ItemStack(IUItem.blockmolecular),'D', new ItemStack(IUItem.tranformer,1,1)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.combinersolidmatter),"ABC","DJE","FGH",'J', new ItemStack(IUItem.core,1,8),'H', new ItemStack(IUItem.solidmatter,1,7),'G', new ItemStack(IUItem.solidmatter,1,6),'F', new ItemStack(IUItem.solidmatter,1,5),'E', new ItemStack(IUItem.solidmatter,1,4),'D', new ItemStack(IUItem.solidmatter,1,3),'C', new ItemStack(IUItem.solidmatter,1,2),'B', new ItemStack(IUItem.solidmatter,1,1),'A', new ItemStack(IUItem.solidmatter,1,0)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1),"DB ","BAB"," BD",'D',new ItemStack(IUItem.module_stack),'A',new ItemStack(IUItem.machines,1,2),'B', new ItemStack(IUItem.core,1,9)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,1),"BBB","CAC","DED",'D',new ItemStack(IUItem.alloysplate,1,1),'E',Ic2Items.electronicCircuit,'A',Ic2Items.advancedMachine,'B',new ItemStack(Items.fish),'C',new ItemStack(Items.fishing_rod,1,OreDictionary.WILDCARD_VALUE)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,2),"BBB","CAC","DED",'D',new ItemStack(IUItem.alloysplate,1,1),'E',new ItemStack(IUItem.cirsuitQuantum),'A',Ic2Items.advancedMachine,'B',Ic2Items.iridiumPlate,'C',new ItemStack(IUItem.plastic_plate)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,3),"   ","BAB","DCD",'D',Ic2Items.carbonPlate,'C',Ic2Items.electronicCircuit,'A',Ic2Items.machine,'B', new ItemStack(IUItem.paints)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1, 1, 6),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', Ic2Items.pump, 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1, 1, 6),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.basemachine1, 1, 6), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1, 1, 8)," C ","BAB"," C ",'C', new ItemStack(Items.experience_bottle),'A',Ic2Items.machine,'B',Ic2Items.electronicCircuit);
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1, 1, 10),"EDE","BAB","EDE",'E',new ItemStack(IUItem.alloysplate,1,0),'D',Ic2Items.electronicCircuit,'B',new ItemStack(Blocks.obsidian),'A', Ic2Items.generator
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1, 1, 12),"EDE","BAB","EDE",'E',new ItemStack(IUItem.alloysplate,1,5),'D',Ic2Items.electronicCircuit,'B',new ItemStack(Items.lava_bucket),'A', Ic2Items.generator
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine1,1,14),"BDB","BAB","BCB",'A',new ItemStack(IUItem.basemachine1,1,9),'B', new ItemStack(IUItem.cell_all,1,2),'C', new ItemStack(IUItem.alloysplate,1,4),'D', new ItemStack(IUItem.alloysplate,1,8)
        );

        GameRegistry.addRecipe(new ItemStack(IUItem.expmodule),"   ","ABA","   ",'A', new ItemStack(IUItem.module_schedule),'B', new ItemStack(Items.experience_bottle)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.module_stack),"DCD","BAB"," C ",'D',new ItemStack(IUItem.alloysplate,1,1),'A', new ItemStack(IUItem.module_schedule),'B', Ic2Items.overclockerUpgrade,'C', IUItem.tranformerUpgrade
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.module_quickly),"   ","ABA","   ",'A', IUItem.overclockerUpgrade1,'B', new ItemStack(IUItem.module_schedule)
                );

        GameRegistry.addRecipe(new ItemStack(IUItem.nanodrill)," D ","ABC"," D ",'D',new ItemStack(IUItem.photoniy_ingot),'B',new ItemStack(IUItem.advnanobox), 'A',new ItemStack(IUItem.nanopickaxe,1,OreDictionary.WILDCARD_VALUE),'C', new ItemStack(IUItem.nanoshovel,1,OreDictionary.WILDCARD_VALUE)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumdrill)," D ","ABC"," D ",'D',new ItemStack(IUItem.cirsuitQuantum),'B',new ItemStack(IUItem.quantumtool), 'A',new ItemStack(IUItem.quantumpickaxe,1,OreDictionary.WILDCARD_VALUE),'C', new ItemStack(IUItem.quantumshovel,1,OreDictionary.WILDCARD_VALUE)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.spectraldrill)," D ","ABC"," D ",'D',new ItemStack(IUItem.circuitSpectral),'B',new ItemStack(IUItem.advQuantumtool), 'A',new ItemStack(IUItem.spectralpickaxe,1,OreDictionary.WILDCARD_VALUE),'C', new ItemStack(IUItem.spectralshovel,1,OreDictionary.WILDCARD_VALUE)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.quantumdrill),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.lapotronCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.nanodrill, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.spectraldrill),
                "TCT", "CDC", "BFB", 'T', Ic2Items.iridiumPlate, 'F', new ItemStack(IUItem.advBatChargeCrystal, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'D', new ItemStack(IUItem.quantumdrill, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.bags),"BCB","BAB","B B" ,'C',new ItemStack(Ic2Items.suBattery.getItem(),1,OreDictionary.WILDCARD_VALUE),'B',new ItemStack(Items.leather), 'A', Ic2Items.electronicCircuit);
        GameRegistry.addRecipe(new ItemStack(IUItem.bags,1,1),"BCB","BAB","B B" ,'C',new ItemStack(Ic2Items.reBattery.getItem(),1,OreDictionary.WILDCARD_VALUE),'B',Ic2Items.carbonPlate, 'A', new ItemStack(IUItem.bags));
        GameRegistry.addRecipe(new ItemStack(IUItem.bags,1,2),"BCB","BAB","B B" ,'C',new ItemStack(Ic2Items.advBattery.getItem(),1,OreDictionary.WILDCARD_VALUE),'B',new ItemStack(IUItem.compresscarbon), 'A', new ItemStack(IUItem.bags,1,1));

        GameRegistry.addRecipe(new ItemStack(IUItem.advjetpack),
                "BCB", "CDC", "BFB", 'F', new ItemStack(Ic2Items.suBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 9), 'D', new ItemStack(Ic2Items.electricJetpack.getItem(),1,OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.QuantumItems9, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.impjetpack),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.reBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.advjetpack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.perjetpack),
                "TCT", "CDC", "BFB", 'T', Ic2Items.iridiumPlate, 'F', new ItemStack(Ic2Items.advBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.advQuantumtool, 1), 'D', new ItemStack(IUItem.impjetpack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.circuitSpectral, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.reactorCondensatorDiamond),"   ","AB ","   ", 'A',new ItemStack(IUItem.reactorCondensatorDiamond,1,OreDictionary.WILDCARD_VALUE),'B',new ItemStack(Blocks.diamond_block) );

        GameRegistry.addRecipe(new ItemStack(IUItem.advventspread),
                "BCB", "CDC", "BFB", 'F', new ItemStack(Ic2Items.suBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.alloysdoubleplate, 1, 9), 'D', new ItemStack(Ic2Items.electricJetpack.getItem(),1,OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.QuantumItems9, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.impventspread),
                "TCT", "CDC", "BFB", 'T', new ItemStack(IUItem.alloysdoubleplate, 1, 3), 'F', new ItemStack(Ic2Items.reBattery.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.quantumtool, 1), 'D', new ItemStack(IUItem.advjetpack, 1, OreDictionary.WILDCARD_VALUE), 'C', new ItemStack(IUItem.cirsuitQuantum, 1));

        GameRegistry.addRecipe(new ItemStack(IUItem.advventspread, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(Ic2Items.reactorVentSpread.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.impventspread, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.advventspread, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));

        GameRegistry.addRecipe(new ItemStack(IUItem.reactoradvVent, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(Ic2Items.reactorVentGold.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));


        GameRegistry.addRecipe(new ItemStack(IUItem.reactorimpVent, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.reactoradvVent, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.advheatswitch, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.nanoBox), 'D', new ItemStack(IUItem.QuantumItems9), 'C', new ItemStack(Ic2Items.reactorHeatSwitch.getItem(), 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 1), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 1));
        GameRegistry.addRecipe(new ItemStack(IUItem.impheatswitch, 1),
                "ABA", "DCD", "EEE", 'E', new ItemStack(IUItem.quantumtool), 'D', new ItemStack(IUItem.cirsuitQuantum), 'C', new ItemStack(IUItem.advheatswitch, 1, OreDictionary.WILDCARD_VALUE), 'B', new ItemStack(IUItem.doubleplate, 1, 9), 'A', new ItemStack(IUItem.alloysdoubleplate, 1, 7));
        GameRegistry.addRecipe(new ItemStack(IUItem.upgradeblock),"DDD","BAB","CCC",'D',new ItemStack(IUItem.alloysdoubleplate,1,6),'B',new ItemStack(IUItem.cirsuitQuantum),'A',Ic2Items.advancedMachine,'C', IUItem.quantumtool
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine2,1,2),"   ","ABC","   ",'A',new ItemStack(IUItem.module7),'B',Ic2Items.machine,'C',Ic2Items.electronicCircuit
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine2,1,3),"   ","ABC","   ",'A',new ItemStack(IUItem.module8),'B',Ic2Items.machine,'C',Ic2Items.electronicCircuit
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine2,1),"   "," BC","   ",'B',Ic2Items.lathe,'C',Ic2Items.electronicCircuit
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine2,1,1)," A ","ABA"," A ",'A', Ic2Items.reactorChamber,'B', new ItemStack(IUItem.alloysblock,1,6)
                );
        GameRegistry.addRecipe(new ItemStack(IUItem.convertersolidmatter,1),"EDE","ABC","FDF",'F',new ItemStack(IUItem.quantumtool),'E',new ItemStack(IUItem.cirsuitQuantum),'A',Ic2Items.replicator,'C',Ic2Items.scanner,'B', Ic2Items.advancedMachine,'D', new ItemStack(IUItem.core,1,6)
        );
        GameRegistry.addRecipe(new ItemStack(IUItem.basemachine2,1,4),"EDE","BAB","CCC",'A',Ic2Items.advancedMachine,'B', new ItemStack(IUItem.entitymodules,1,1),'C', new ItemStack(IUItem.advQuantumtool),'D',new ItemStack(IUItem.circuitSpectral),'E',new ItemStack(IUItem.alloysdoubleplate,1,3));
    }
}
