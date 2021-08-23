package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityDoubleRecycler extends TileEntityMultiMachine {
	public TileEntityDoubleRecycler() {
		super(EnumMultiMachine.DOUBLE_RECYCLER.usagePerTick,EnumMultiMachine.DOUBLE_RECYCLER.lenghtOperation, Recipes.recycler,1,4,true,1);
	this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", 2, Recipes.recycler);
	}

	@Override
	public EnumMultiMachine getMachine() {
		return EnumMultiMachine.DOUBLE_RECYCLER;
	}

	public String getInventoryName() {
		return StatCollector.translateToLocal("iu.blockRecycler.name");
	}

	public String getStartSoundFile() {
		return "Machines/RecyclerOp.ogg";
	}

	public String getInterruptSoundFile() {
		return "Machines/InterruptOne.ogg";
	}

	public float getWrenchDropRate() {
		return 0.85F;
	}

	public Set<UpgradableProperty> getUpgradableProperties() {
		return EnumSet.of(UpgradableProperty.Processing,
				UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer,
				UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming,
				UpgradableProperty.ItemProducing);
	}

}
