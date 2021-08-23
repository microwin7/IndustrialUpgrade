package com.denfop.invslot;

import com.denfop.item.modules.QuarryModule;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotQuantumQuarry1 extends InvSlot {

	private int stackSizeLimit;
	public InvSlotQuantumQuarry1(TileEntityInventory base1, int oldStartIndex1) {
		super(base1, "input3", oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
		this.stackSizeLimit = 1;
	}

	public boolean accepts(ItemStack itemStack) {
		return itemStack.getItem() instanceof QuarryModule&& itemStack.getItemDamage() > 11;
	}

	 public int getStackSizeLimit() {
			return this.stackSizeLimit;
		}

		public void setStackSizeLimit(int stackSizeLimit) {
			this.stackSizeLimit = stackSizeLimit;
		}

}
