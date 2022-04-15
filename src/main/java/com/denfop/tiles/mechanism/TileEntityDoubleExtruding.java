package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityDoubleExtruding extends TileEntityMultiMachine {
    public TileEntityDoubleExtruding() {
        super(EnumMultiMachine.DOUBLE_Extruding.usagePerTick, EnumMultiMachine.DOUBLE_Extruding.lenghtOperation, Recipes.metalformerExtruding, 2);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.metalformerExtruding);
    }


    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.DOUBLE_Extruding;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockExtruding1.name");
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
