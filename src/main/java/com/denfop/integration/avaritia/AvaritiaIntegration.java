package com.denfop.integration.avaritia;

import com.denfop.IUItem;
import com.denfop.integration.de.SSPDEItem;
import cpw.mods.fml.common.registry.GameRegistry;
import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.Grinder;
import fox.spiteful.avaritia.items.LudicrousItems;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AvaritiaIntegration {

    public static Block blockAvSolarPanel;
    public static Item neutroncore;
    public static Item infinitycore;
    public static Item singularity;

    public static void init() {
        blockAvSolarPanel = new blockAvaritiaSolarPanel();
        neutroncore = new SSPDEItem("neutroncore");
        infinitycore = new SSPDEItem("infinitycore");
        singularity = new ItemSingularity();
    }

    public static void recipe() {
        GameRegistry.addRecipe(new ItemStack(blockAvSolarPanel, 1, 0), " B ", "BAB", " B ", 'B',
                new ItemStack(IUItem.blockpanel, 1, 8), 'A', neutroncore);
        GameRegistry.addRecipe(new ItemStack(blockAvSolarPanel, 1, 1),
                " B ", "BAB", " B ", 'B', new ItemStack(blockAvSolarPanel, 1, 0), 'A', infinitycore);

        GameRegistry.addRecipe(new ItemStack(neutroncore, 1), " A ", "ABA", " A ", 'B',
                new ItemStack(IUItem.core, 1, 5), 'A', new ItemStack(LudicrousItems.resource, 1, 4));
        GameRegistry.addRecipe(new ItemStack(infinitycore, 1), "BAB", "ABA", "BAB", 'B',
                new ItemStack(neutroncore, 1), 'A', new ItemStack(LudicrousItems.resource, 1, 6));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 0), 350, "blockMikhail");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 0));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 1), 325, "blockAluminium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 1));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 2), 375, "blockVanady");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 2));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 3), 355, "blockTungsten");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 3));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 4), 345, "blockCobalt");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 4));
        //
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 5), 335, "blockMagnesium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 5));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 6), 350, "blockPlatinum");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 6));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 7), 340, "blockTitanium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 7));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 8), 330, "blockChromium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 9));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 9), 360, "blockSpinel");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 9));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 10), 380, "blockZinc");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 10));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 11), 400, "blockManganese");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 11));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 12), 215, "blockInvar");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 12));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 13), 300, "blockCaravky");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 13));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 14), 310, "blockElectrum");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 14));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 15), 320, "blockIridium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 15));
        CompressorManager.addOreRecipe(new ItemStack(singularity, 1, 16), 330, "blockGermanium");
        Grinder.catalyst.getInput().add(new ItemStack(singularity, 1, 16));


        GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, 20), "   ", "BAB", " B ", 'B',
                new ItemStack(IUItem.blockpanel, 1, 8), 'A', neutroncore);
        GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, 21),
                "   ", "BAB", " B ", 'B', new ItemStack(blockAvSolarPanel, 1, 0), 'A', infinitycore);

    }

}
