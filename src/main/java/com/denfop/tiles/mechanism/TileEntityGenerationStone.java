package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.container.ContainerGenStone;
import com.denfop.gui.GUIGenStone;
import com.denfop.invslot.InvSlotProcessableStone;
import com.denfop.recipemanager.GenStoneRecipeManager;
import com.denfop.tiles.base.TileEntityBaseGenStone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.Ic2Items;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityGenerationStone extends TileEntityBaseGenStone {
    public TileEntityGenerationStone() {
        super(1, 100, 12);
        this.inputSlotA = new InvSlotProcessableStone(this, "inputA", 0, 1);
        this.inputSlotB = new InvSlotProcessableStone(this, "inputB", 1, 1);
    }

    public static void init() {
        Recipes.GenStone = new GenStoneRecipeManager();
        addGen(new RecipeInputItemStack(new ItemStack(Items.lava_bucket), 1), new RecipeInputItemStack(new ItemStack(Items.water_bucket), 1), new ItemStack(Blocks.cobblestone, 8));
        addGen(new RecipeInputItemStack(Ic2Items.lavaCell, 1), new RecipeInputItemStack(Ic2Items.waterCell, 1), new ItemStack(Blocks.cobblestone, 8));

    }

    public static void addGen(IRecipeInput container, IRecipeInput fill, ItemStack output) {
        Recipes.GenStone.addRecipe(container, fill, output);
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.genstone");
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIGenStone(new ContainerGenStone(entityPlayer, this));
    }

    public String getStartSoundFile() {
        return "Machines/gen_cobblectone.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

}
