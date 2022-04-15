package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.gui.GUIAlloySmelter;
import com.denfop.recipemanager.DoubleMachineRecipeManager;
import com.denfop.tiles.base.EnumDoubleElectricMachine;
import com.denfop.tiles.base.TileEntityDoubleElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityAlloySmelter extends TileEntityDoubleElectricMachine {

    public TileEntityAlloySmelter() {
        super(1, 300, 1, StatCollector.translateToLocal("iu.Alloymachine.name"), EnumDoubleElectricMachine.ALLOY_SMELTER);
    }

    public static void init() {
        Recipes.Alloysmelter = new DoubleMachineRecipeManager();
        addAlloysmelter(new RecipeInputItemStack(new ItemStack(Items.iron_ingot), 1),
                new RecipeInputItemStack(new ItemStack(Items.coal), 2),
                new ItemStack(Ic2Items.advIronIngot.getItem(), 1, 3));
        addAlloysmelter(new RecipeInputItemStack(new ItemStack(Items.gold_ingot), 1),
                new RecipeInputOreDict("ingotSilver", 1),
                new ItemStack(OreDictionary.getOres("ingotElectrum").get(0).getItem(), 2, OreDictionary.getOres("ingotElectrum").get(0).getItemDamage()));
        addAlloysmelter(new RecipeInputOreDict("ingotNickel", 1),
                new RecipeInputItemStack(new ItemStack(Items.iron_ingot), 2),
                new ItemStack(OreDictionary.getOres("ingotInvar").get(0).getItem(), 3, OreDictionary.getOres("ingotInvar").get(0).getItemDamage()));

        addAlloysmelter(new RecipeInputOreDict("ingotCopper", 1),
                new RecipeInputOreDict("ingotZinc", 1),
                new ItemStack(IUItem.alloysingot, 1, 2));
        addAlloysmelter(new RecipeInputOreDict("ingotNickel", 1),
                new RecipeInputOreDict("ingotChromium", 1),
                new ItemStack(IUItem.alloysingot, 1, 4));
        addAlloysmelter(new RecipeInputOreDict("ingotAluminium", 1),
                new RecipeInputOreDict("ingotMagnesium", 1),
                new ItemStack(IUItem.alloysingot, 1, 8));
        addAlloysmelter(new RecipeInputOreDict("ingotAluminium", 1),
                new RecipeInputOreDict("ingotTitanium", 1),
                new ItemStack(IUItem.alloysingot, 1, 1));
        addAlloysmelter(new RecipeInputItemStack(new ItemStack(Items.iron_ingot), 1),
                new RecipeInputOreDict("ingotManganese", 1),
                new ItemStack(IUItem.alloysingot, 1, 9));


    }


    public static void addAlloysmelter(IRecipeInput container, IRecipeInput fill, ItemStack output) {
        Recipes.Alloysmelter.addRecipe(container, fill, null, output);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIAlloySmelter(new ContainerDoubleElectricMachine<>(entityPlayer, this, this.type));
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
