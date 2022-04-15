package com.denfop.tiles.base;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerSunnariumMaker;
import com.denfop.gui.GUISunnariumMaker;
import com.denfop.invslot.InvSlotProcessableSunnarium;
import com.denfop.recipemanager.SunnariumRecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileSunnariumMaker extends TileEntityBaseSunnariumMaker {

    public TileSunnariumMaker() {
        super(1, 300, 1);
        this.inputSlotA = new InvSlotProcessableSunnarium(this, "inputA", 0, 4);
    }

    public static void init() {
        Recipes.sunnurium = new SunnariumRecipeManager();
        addSunnariumMaker(new ItemStack(IUItem.sunnarium, 1, 4), new ItemStack(Items.glowstone_dust), new ItemStack(Items.quartz), new ItemStack(IUItem.iuingot, 1, 3), new ItemStack(IUItem.sunnarium, 1, 3));
    }

    public static void addSunnariumMaker(ItemStack container, ItemStack container1, ItemStack container2, ItemStack container3, ItemStack output) {
        Recipes.sunnurium.addRecipe(new RecipeInputItemStack(container, 4), new RecipeInputItemStack(container1), new RecipeInputItemStack(container2), new RecipeInputItemStack(container3), output);

    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("blockSunnariumMaker.name");
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUISunnariumMaker(new ContainerSunnariumMaker(entityPlayer, this));
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
