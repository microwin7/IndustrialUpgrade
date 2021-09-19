package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityDoubleMacerator extends TileEntityMultiMachine {
    public TileEntityDoubleMacerator() {
        super(EnumMultiMachine.DOUBLE_MACERATOR.usagePerTick, EnumMultiMachine.DOUBLE_MACERATOR.lenghtOperation, Recipes.macerator, 0);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", 2, Recipes.macerator);
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.DOUBLE_MACERATOR;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockMacerator.name");
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
