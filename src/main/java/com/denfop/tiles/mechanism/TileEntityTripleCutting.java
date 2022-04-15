package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityTripleCutting extends TileEntityMultiMachine {
    public TileEntityTripleCutting() {
        super(EnumMultiMachine.TRIPLE_Cutting.usagePerTick, EnumMultiMachine.TRIPLE_Cutting.lenghtOperation, Recipes.metalformerCutting, 2);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.metalformerCutting);
    }


    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.TRIPLE_Cutting;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockCutting2.name");
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
