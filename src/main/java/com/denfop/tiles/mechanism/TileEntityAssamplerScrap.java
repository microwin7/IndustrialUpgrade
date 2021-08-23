package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.Ic2Items;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityAssamplerScrap extends TileEntityMultiMachine {
    public TileEntityAssamplerScrap() {
        super(EnumMultiMachine.AssamplerScrap.usagePerTick,EnumMultiMachine.AssamplerScrap.lenghtOperation, Recipes.createscrap,3);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.createscrap);
    }
    public static void init(){
        Recipes.createscrap = new BasicMachineRecipeManager();
        addrecipe(Ic2Items.scrap,Ic2Items.scrapBox);
        addrecipe(Ic2Items.scrapBox,new ItemStack(IUItem.doublescrapBox));
    }

    public static void addrecipe(ItemStack input, ItemStack output){
        Recipes.createscrap.addRecipe(new RecipeInputItemStack(input,9),null, output);
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.AssamplerScrap;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAssamplerScrap.name");
    }

    public String getStartSoundFile() {
        return "Machines/AssamplerScrap.ogg";
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
