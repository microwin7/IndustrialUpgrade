package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseWitherMaker;
import com.denfop.gui.GUIWitherMaker;
import com.denfop.invslot.InvSlotProcessableWitherMaker;
import com.denfop.recipemanager.WitherMakerRecipeManager;
import com.denfop.tiles.base.TileEntityBaseWitherMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityWitherMaker extends TileEntityBaseWitherMaker {

    public TileEntityWitherMaker() {
        super(1, 1500, 1);
        this.inputSlotA = new InvSlotProcessableWitherMaker(this, "inputA", 0, 7
        );

    }

    public static void init() {
        Recipes.withermaker = new WitherMakerRecipeManager();
        GenerationMicrochip(new RecipeInputItemStack(new ItemStack(Items.skull, 1, 1), 1),
                new RecipeInputItemStack(new ItemStack(Blocks.soul_sand), 1),
                new ItemStack(Items.nether_star, 1));

    }

    public static void GenerationMicrochip(IRecipeInput container,
                                           IRecipeInput fill2, ItemStack output) {
        Recipes.withermaker.addRecipe(container, container, fill2, fill2, fill2, container, fill2, output);

    }

    public String getInventoryName() {

        return "wither maker";
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIWitherMaker(new ContainerBaseWitherMaker(entityPlayer, this));
    }

    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
                UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

}
