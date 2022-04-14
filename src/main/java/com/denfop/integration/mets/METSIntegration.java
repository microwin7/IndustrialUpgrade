package com.denfop.integration.mets;

import com.denfop.IUItem;
import com.denfop.utils.CraftManager;
import ic2.api.recipe.Recipes;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TeBlockRegistry;
import net.lrsoft.mets.block.MetsBlockWithTileEntity;
import net.lrsoft.mets.manager.ItemManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class METSIntegration {

    public static void init() {
        BlockTileEntity teBlock = TeBlockRegistry.get(MetsBlockWithTileEntity.loc);
        CraftManager.removeCrafting(CraftManager.getRecipe(teBlock.getItemStack(MetsBlockWithTileEntity.advanced_kinetic_generator)));
        CraftManager.removeCrafting(CraftManager.getRecipe(teBlock.getItemStack(MetsBlockWithTileEntity.super_kinetic_generator)));
        CraftManager.removeCrafting(CraftManager.getRecipe(teBlock.getItemStack(MetsBlockWithTileEntity.eesu)));
        ItemStack eesuStorage = teBlock.getItemStack(MetsBlockWithTileEntity.eesu);
        Recipes.advRecipes.addRecipe(eesuStorage, "BCB", "BAB", "BCB", 'A', new ItemStack(IUItem.electricblock, 1, 4),
                'B',
                getAllTypeStack(ItemManager.superLapotronCrystal), 'C', Recipes.inputFactory.forOreDict(
                        "circuitElite")
        );


    }

    private static ItemStack getAllTypeStack(ItemStack itemstack) {
        return new ItemStack(itemstack.getItem(), 1, 32767);
    }

    private static ItemStack getAllTypeStack(Item item) {
        return new ItemStack(item, 1, 32767);
    }

}
