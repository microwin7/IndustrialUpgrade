package com.denfop.integration.de;

import com.brandon3055.draconicevolution.client.render.IRenderTweak;
import com.brandon3055.draconicevolution.common.ModItems;
import com.brandon3055.draconicevolution.common.handler.ConfigHandler;
import com.denfop.Config;
import com.denfop.IUItem;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.util.EnumHelper;

public class DraconicIntegration {

    public static final Item.ToolMaterial CHAOS = EnumHelper.addToolMaterial("CHAOS", 15, -1, 600.0F, 90.0F, 60);
    public static Item ChaosDestructionStaff;
    public static Item ChaosPickaxe;
    public static Item ChaosAxe;
    public static ItemArmor ChaosHelm;
    public static Item ChaosShovel;
    public static Item ChaosSword;
    public static Item ChaosBow;
    public static ItemArmor ChaosChest;
    public static ItemArmor ChaosLeggs;
    public static ItemArmor ChaosBoots;
    public static Item ChaosEnergyCore;
    public static ItemDC ChaosFluxCapacitor;
    public static Item chaosingot;
    public static Block blockDESolarPanel;
    private static final ArmorMaterial CHAOS_ARMOR = EnumHelper.addArmorMaterial("CHAOS_ARMOR", -1, new int[]{5, 10, 8, 6},
            30);

    public static void init() {
        if (Config.Draconic) {
            ChaosEnergyCore = new SSPDEItem("ChaosEnergyCore");

            if (Config.registerChaosFluxCapacitor)
                ChaosFluxCapacitor = new ChaosFluxCapacitor();
            if (Config.registerChaosDestructionStaff)
                ChaosDestructionStaff = new ChaosDistructionStaff();
            if (Config.registerChaosPickaxe)
                ChaosPickaxe = new ChaosPickaxe();
            if (Config.registerChaosAxe)
                ChaosAxe = new ChaosAxe();
            if (Config.registerChaosShovel)
                ChaosShovel = new ChaosShovel();
            if (Config.registerChaosSword)
                ChaosSword = new ChaosSword();
            if (Config.registerChaosBow)
                ChaosBow = new ChaosBow();
            if (Config.registerChaosArmour) {
                ChaosHelm = new ChaosArmor(CHAOS_ARMOR, 0, "ChaosHelm");
                ChaosChest = new ChaosArmor(CHAOS_ARMOR, 1, "ChaosChest");
                ChaosLeggs = new ChaosArmor(CHAOS_ARMOR, 2, "ChaosLeggs");
                ChaosBoots = new ChaosArmor(CHAOS_ARMOR, 3, "ChaosBoots");
            }
            chaosingot = new SSPDEItem("chaosingot");
            if (Config.registerDraconicPanels) {
                blockDESolarPanel = new blockDESolarPanel();
            }
        }
    }

    public static void register(ItemDC item) {
        String name = item.getUnwrappedUnlocalizedName(item.getUnlocalizedName());
        if (isEnabled(item))
            GameRegistry.registerItem(item, name.substring(name.indexOf(":") + 1));
    }

    public static boolean isEnabled(Item item) {
        return !ConfigHandler.disabledNamesList.contains(item.getUnlocalizedName());
    }

    public static void Recipes() {
        GameRegistry.addRecipe(new ItemStack(ChaosEnergyCore, 1), " B ", "BAB", " B ", 'B',
                new ItemStack(chaosingot, 1), 'A', ModItems.draconicEnergyCore);
        Recipes.compressor.addRecipe(
                new RecipeInputItemStack(new ItemStack(ModItems.chaosFragment, 1, 2), 1),
                null, new ItemStack(chaosingot, 1));
        GameRegistry.addRecipe(new ItemStack(ChaosHelm), "CDC", "CAC", "CBC", 'B', new ItemStack(ChaosEnergyCore, 1),
                'A', new ItemStack(ModItems.draconicHelm), 'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosChest, 1), "CDC", "CAC", "CBC", 'B',
                new ItemStack(ChaosEnergyCore, 1), 'A', new ItemStack(ModItems.draconicChest), 'D',
                ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosLeggs, 1), "CDC", "CAC", "CBC", 'B',
                new ItemStack(ChaosEnergyCore, 1), 'A', ModItems.draconicLeggs, 'D', ModItems.chaoticCore, 'C',
                chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosBoots, 1), "CDC", "CAC", "CBC", 'B',
                new ItemStack(ChaosEnergyCore, 1), 'A', ModItems.draconicBoots, 'D', ModItems.chaoticCore, 'C',
                chaosingot);
        //
        GameRegistry.addRecipe(new ItemStack(ChaosPickaxe, 1),
                " D ", "CAC", " B ", 'B', new ItemStack(ChaosEnergyCore, 1), 'A',
                ModItems.draconicPickaxe, 'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosAxe, 1),
                " D ", "CAC", " B ", 'B', new ItemStack(ChaosEnergyCore, 1), 'A', ModItems.draconicAxe,
                'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosShovel, 1),
                " D ", "CAC", " B ", 'B', new ItemStack(ChaosEnergyCore, 1), 'A',
                ModItems.draconicShovel, 'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosSword, 1),
                " D ", "CAC", " B ", 'B', new ItemStack(ChaosEnergyCore, 1), 'A', ModItems.draconicSword,
                'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(ChaosBow, 1),
                " D ", "CAC", " B ", 'B', new ItemStack(ChaosEnergyCore, 1), 'A', ModItems.draconicBow,
                'D', ModItems.chaoticCore, 'C', chaosingot);

        GameRegistry.addRecipe(new ItemStack(ChaosDestructionStaff, 1),
                "CDC", "ACE", "CBC", 'B', new ItemStack(ChaosSword, 1), 'A', ChaosPickaxe, 'D',
                ModItems.chaoticCore, 'C', chaosingot, 'E', ChaosShovel);
        GameRegistry.addRecipe(new ItemStack(ChaosFluxCapacitor, 1),
                "CDC", "CAC", "CBC", 'B', new ItemStack(ChaosEnergyCore, 1), 'A',
                ModItems.draconicFluxCapacitor, 'D', ModItems.chaoticCore, 'C', chaosingot);
        GameRegistry.addRecipe(new ItemStack(blockDESolarPanel, 1, 0), " B ", "BAB", " B ", 'B',
                new ItemStack(IUItem.blockpanel, 1), 'A', ModItems.wyvernCore);
        GameRegistry.addRecipe(new ItemStack(blockDESolarPanel, 1, 1), "AB ", "BAB", " BA", 'B',
                new ItemStack(blockDESolarPanel, 1, 0), 'A', ModItems.awakenedCore);
        GameRegistry.addRecipe(new ItemStack(blockDESolarPanel, 1, 2), "ABC", "BAB", "CBA", 'B',
                new ItemStack(blockDESolarPanel, 1, 1), 'A', ModItems.chaoticCore, 'C', ChaosEnergyCore);

        GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, 14), "   ", "BAB", " B ", 'B',
                new ItemStack(IUItem.blockpanel, 1), 'A', ModItems.wyvernCore);
        GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, 15), "A  ", "BAB", " BA", 'B',
                new ItemStack(blockDESolarPanel, 1, 0), 'A', ModItems.awakenedCore);
        GameRegistry.addRecipe(new ItemStack(IUItem.UpgradePanelKit, 1, 16), "A C", "BAB", "CBA", 'B',
                new ItemStack(blockDESolarPanel, 1, 1), 'A', ModItems.chaoticCore, 'C', ChaosEnergyCore);

    }

    public static void render() {
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosBow,
                new RenderBowModel(true));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosSword,
                new RenderTool("models/tools/DraconicSword.obj",
                        "textures/models/tools/DraconicSword.png", (IRenderTweak) DraconicIntegration.ChaosSword));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosPickaxe,
                new RenderTool("models/tools/DraconicPickaxe.obj",
                        "textures/models/tools/DraconicPickaxe.png", (IRenderTweak) DraconicIntegration.ChaosPickaxe));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosAxe,
                new RenderTool("models/tools/DraconicLumberAxe.obj",
                        "textures/models/tools/DraconicLumberAxe.png", (IRenderTweak) DraconicIntegration.ChaosAxe));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosShovel,
                new RenderTool("models/tools/DraconicShovel.obj",
                        "textures/models/tools/DraconicShovel.png", (IRenderTweak) DraconicIntegration.ChaosShovel));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosDestructionStaff,
                new RenderTool("models/tools/DraconicStaffOfPower.obj",
                        "textures/models/tools/DraconicStaffOfPower.png",
                        (IRenderTweak) DraconicIntegration.ChaosDestructionStaff));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosHelm,
                new RenderArmor(DraconicIntegration.ChaosHelm));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosChest,
                new RenderArmor(DraconicIntegration.ChaosChest));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosLeggs,
                new RenderArmor(DraconicIntegration.ChaosLeggs));
        MinecraftForgeClient.registerItemRenderer(DraconicIntegration.ChaosBoots,
                new RenderArmor(DraconicIntegration.ChaosBoots));

    }
}
