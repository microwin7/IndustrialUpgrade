package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityDoubleCompressor extends TileEntityMultiMachine {
	public TileEntityDoubleCompressor() {
		super(EnumMultiMachine.DOUBLE_COMPRESSER.usagePerTick,EnumMultiMachine.DOUBLE_COMPRESSER.lenghtOperation, Recipes.compressor,0 );
		this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.compressor);
	}


	public EnumMultiMachine getMachine() {
		return EnumMultiMachine.DOUBLE_COMPRESSER;
	}

	public String getInventoryName() {
		return StatCollector.translateToLocal("iu.blockCompressor.name");
	}

	public String getStartSoundFile() {
		return "Machines/CompressorOp.ogg";
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
