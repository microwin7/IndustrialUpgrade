package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.gui.GUISunnariumPanelMaker;
import com.denfop.recipemanager.DoubleMachineRecipeManager;
import com.denfop.tiles.base.EnumDoubleElectricMachine;
import com.denfop.tiles.base.TileEntityDoubleElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntitySunnariumPanelMaker extends TileEntityDoubleElectricMachine {

    public TileEntitySunnariumPanelMaker() {
        super(1, 300, 1, StatCollector.translateToLocal("iu.SunnariumPanelMaker.name"), EnumDoubleElectricMachine.SUNNARIUM_PANEL);
    }

    public static void init() {
        Recipes.sunnuriumpanel = new DoubleMachineRecipeManager();

        addsunnuriumpanel(new ItemStack(IUItem.sunnarium, 1, 2), new ItemStack(IUItem.plate, 1, 9), new ItemStack(IUItem.sunnariumpanel, 1, 0));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 0), new ItemStack(IUItem.plate, 1, 0), new ItemStack(IUItem.sunnariumpanel, 1, 1));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 1), new ItemStack(IUItem.plate, 1, 11), new ItemStack(IUItem.sunnariumpanel, 1, 2));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 2), new ItemStack(IUItem.plate, 1, 13), new ItemStack(IUItem.sunnariumpanel, 1, 3));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 3), new ItemStack(IUItem.plate, 1, 7), new ItemStack(IUItem.sunnariumpanel, 1, 4));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 4), new ItemStack(IUItem.plate, 1, 15), new ItemStack(IUItem.sunnariumpanel, 1, 5));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 5), new ItemStack(IUItem.plate, 1, 16), new ItemStack(IUItem.sunnariumpanel, 1, 6));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 6), new ItemStack(IUItem.plate, 1, 6), new ItemStack(IUItem.sunnariumpanel, 1, 7));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 7), new ItemStack(IUItem.plate, 1, 8), new ItemStack(IUItem.sunnariumpanel, 1, 8));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 8), new ItemStack(IUItem.plate, 1, 14), new ItemStack(IUItem.sunnariumpanel, 1, 9));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 9), new ItemStack(IUItem.plate, 1, 2), new ItemStack(IUItem.sunnariumpanel, 1, 10));
        addsunnuriumpanel(new ItemStack(IUItem.sunnarium, 1, 0), new ItemStack(IUItem.plate, 1, 1), new ItemStack(IUItem.sunnarium, 1, 1));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 10), new ItemStack(IUItem.alloysplate, 1, 7), new ItemStack(IUItem.sunnariumpanel, 1, 11));
        addsunnuriumpanel(new ItemStack(IUItem.sunnariumpanel, 1, 11), new ItemStack(IUItem.plate, 1, 5), new ItemStack(IUItem.sunnariumpanel, 1, 12));

    }

    public static void addsunnuriumpanel(ItemStack container, ItemStack fill, ItemStack output) {
        int id = OreDictionary.getOreID(fill);
        String name = OreDictionary.getOreName(id);

        if (name == null && fill.getItem() != IUItem.neutroniumingot)
            Recipes.sunnuriumpanel.addRecipe(new RecipeInputItemStack(container), new RecipeInputItemStack(fill), null, output);
        else {
            Recipes.sunnuriumpanel.addRecipe(new RecipeInputItemStack(container), new RecipeInputOreDict(name), null, output);

        }
    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.SunnariumPanelMaker.name");
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUISunnariumPanelMaker(new ContainerDoubleElectricMachine(entityPlayer, this, type));

    }

    @Override
    public void operateOnce(RecipeOutput output, List<ItemStack> processResult) {
        this.inputSlotA.consume();
        this.outputSlot.add(processResult);
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
