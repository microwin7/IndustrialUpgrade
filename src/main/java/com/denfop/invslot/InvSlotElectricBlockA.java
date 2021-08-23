package com.denfop.invslot;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotElectricBlockA extends InvSlot {

	public InvSlotElectricBlockA(TileEntityInventory base1, int oldStartIndex1) {
		super(base1, "input3", oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);

	}

	public boolean accepts(ItemStack itemStack) {
		return itemStack.getItem() instanceof IElectricItem;
	}

	public double discharge(double amount, ItemStack stack,boolean simulate) {
		if (amount < 0.0) {
			throw new IllegalArgumentException("Amount must be > 0.");
		}
		double charged = 0.0;
		if (amount == 0.0)
			return 0;

		final double energyIn = ElectricItem.manager.discharge(stack, amount, 2147483647, false, true, simulate);
		amount -= energyIn;
		charged += energyIn;
		if (amount <= 0.0)
			return 0;

		return charged;
	}

}
