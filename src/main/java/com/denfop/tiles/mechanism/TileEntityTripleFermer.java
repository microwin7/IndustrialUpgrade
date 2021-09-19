package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityTripleFermer extends TileEntityMultiMachine {
    public TileEntityTripleFermer() {
        super(EnumMultiMachine.TRIPLE_Fermer.usagePerTick, EnumMultiMachine.TRIPLE_Fermer.lenghtOperation, Recipes.fermer, 3);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.fermer);
    }


    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.TRIPLE_Fermer;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockFermer2.name");
    }

    public String getStartSoundFile() {
        return "Machines/Fermer.ogg";
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
