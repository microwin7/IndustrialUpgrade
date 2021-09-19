package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiSmelting;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityQuadElectricFurnace extends TileEntityMultiMachine {
    public TileEntityQuadElectricFurnace() {
        super(EnumMultiMachine.QUAD_ELECTRIC_FURNACE.usagePerTick, EnumMultiMachine.QUAD_ELECTRIC_FURNACE.lenghtOperation, null, 0);
        this.inputSlots = new InvSlotProcessableMultiSmelting(this, "input", sizeWorkingSlot);
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.QUAD_ELECTRIC_FURNACE;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockElecFurnace3.name");
    }

    public String getStartSoundFile() {
        return "Machines/Electro Furnace/ElectroFurnaceLoop.ogg";
    }

    public String getInterruptSoundFile() {
        return null;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
                UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }
}
