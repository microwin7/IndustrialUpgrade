package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerTripleElectricMachine;
import com.denfop.gui.GUIAdvAlloySmelter;
import com.denfop.recipemanager.TripleMachineRecipeManager;
import com.denfop.tiles.base.EnumTripleElectricMachine;
import com.denfop.tiles.base.TileEntityTripleElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityAdvAlloySmelter extends TileEntityTripleElectricMachine {

    public TileEntityAdvAlloySmelter() {
        super(1, 300, 1, StatCollector.translateToLocal("iu.AdvAlloymachine.name"), EnumTripleElectricMachine.ADV_ALLOY_SMELTER);
    }

    public static void init() {
        Recipes.Alloyadvsmelter = new TripleMachineRecipeManager();
        addAlloysmelter("ingotCopper", "ingotZinc", "ingotLead", new ItemStack(IUItem.alloysingot, 1, 3));
        addAlloysmelter("ingotAluminium", "ingotMagnesium", "ingotManganese", new ItemStack(IUItem.alloysingot, 1, 5));
        addAlloysmelter("ingotAluminium",
                "ingotCopper", "ingotTin",
                new ItemStack(IUItem.alloysingot, 1, 0));
        addAlloysmelter("ingotAluminium",
                "ingotVanady", "ingotCobalt",
                new ItemStack(IUItem.alloysingot, 1, 6));
        addAlloysmelter("ingotChromium",
                "ingotTungsten", "ingotNickel",
                new ItemStack(IUItem.alloysingot, 1, 7));

    }

    public static void addAlloysmelter(String container, String fill, String fill1, ItemStack output) {
        Recipes.Alloyadvsmelter.addRecipe(new RecipeInputOreDict(container), new RecipeInputOreDict(fill), new RecipeInputOreDict(fill1), output);
    }

    @Override
    public void operateOnce(List<ItemStack> processResult) {
        this.inputSlotA.consume();
        this.outputSlot.add(processResult);
    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.AdvAlloymachine.name");
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIAdvAlloySmelter(new ContainerTripleElectricMachine(entityPlayer, this, type));
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
