package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityDoubleAssamplerScrap extends TileEntityMultiMachine {
    public TileEntityDoubleAssamplerScrap() {
        super(EnumMultiMachine.DOUBLE_AssamplerScrap.usagePerTick, EnumMultiMachine.DOUBLE_AssamplerScrap.lenghtOperation, Recipes.createscrap, 3);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.createscrap);
    }


    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.DOUBLE_AssamplerScrap;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAssamplerScrap1.name");
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
