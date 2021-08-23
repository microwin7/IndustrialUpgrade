package com.denfop.invslot;

import com.denfop.IUItem;
import com.denfop.item.modules.AdditionModule;
import com.denfop.item.modules.EnumType;
import com.denfop.item.modules.ItemWirelessModule;
import com.denfop.tiles.base.TileEntityElectricBlock;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class InvSlotElectricBlockB extends InvSlot {

	private int stackSizeLimit;

	public InvSlotElectricBlockB(TileEntityInventory base1, int oldStartIndex1) {
		super(base1, "input4", oldStartIndex1, InvSlot.Access.IO, 2, InvSlot.InvSide.TOP);
		
		this.stackSizeLimit = 1;
	}

	public boolean accepts(ItemStack itemStack) {
		return ((itemStack.getItemDamage() >= 4 || itemStack.getItemDamage() == 0)
				&& itemStack.getItem() instanceof AdditionModule) || (itemStack.getItem() instanceof ItemWirelessModule)
				|| (IUItem.modules.get(itemStack.getItem()) != null&& IUItem.modules.get(itemStack.getItem()).type.equals(EnumType.OUTPUT) );
	}

	public int getStackSizeLimit() {
		return this.stackSizeLimit;
	}

	public void setStackSizeLimit(int stackSizeLimit) {
		this.stackSizeLimit = stackSizeLimit;
	}

	public List<Boolean> getstats() {
		List<Boolean> list = new ArrayList<>();
		List<Boolean> list1 = new ArrayList<>();

		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == null) {
				list.add(false);
				list.add(false);
				list.add(false);
				list.add(false);
				list.add(false);
				continue;
			}

			ItemStack stack = this.get(i);
			if (stack.getItemDamage() == 5) {
				list.add(true);
			} else {
				list.add(false);
			}
			if (stack.getItemDamage() == 6) {
				list.add(true);
			} else {
				list.add(false);
			}
			if (stack.getItemDamage() == 7) {
				list.add(true);
			} else {
				list.add(false);
			}
			if (stack.getItemDamage() == 8) {
				list.add(true);
			} else {
				list.add(false);
			}
			if (stack.getItemDamage() == 4) {
				list.add(true);
			} else {
				list.add(false);
			}

		}
		list1.add(gettrue(list.get(0), list.get(5)));
		list1.add(gettrue(list.get(1), list.get(6)));
		list1.add(gettrue(list.get(2), list.get(7)));
		list1.add(gettrue(list.get(3), list.get(8)));
		list1.add(gettrue(list.get(4), list.get(9)));

		return list1;
	}

	public boolean gettrue(boolean o, boolean j) {
		return (o || j);
	}


	public boolean wirelessA() {
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == null)
				continue;
			ItemStack stack = this.get(i);
			if (stack.getItem() instanceof ItemWirelessModule) {
				NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(stack);
				return nbttagcompound.getBoolean("change");

			}
		}
		return  false;
	}
	public void wirelessmodule() {
		TileEntityElectricBlock tile = (TileEntityElectricBlock) base;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) != null && this.get(i).getItem() instanceof ItemWirelessModule) {

				tile.wirelees = 1;
				int x ;
				int y ;
				int z ;
				String name;
				int tier1 ;

				NBTTagCompound nbttagcompound = ModUtils.nbt(this.get(i) );

				x = nbttagcompound.getInteger("Xcoord");
				y = nbttagcompound.getInteger("Ycoord");
				z = nbttagcompound.getInteger("Zcoord");
				tier1 = nbttagcompound.getInteger("tier");
				name = nbttagcompound.getString("Name");
				int world = nbttagcompound.getInteger("World1");

				if (x != 0 && y != 0 && z != 0) {
					tile.panelx = x;
					tile.panely = y;
					tile.panelz = z;
					tile.nameblock = name;
					tile.world1 = world;
					tile.blocktier = tier1;
				}
				break;
			} else {
				tile.wirelees = 0;
			}
		}
	}
	public void wireless(int xCoord, int yCoord, int zCoord, int tier, int dimensionId,
						 String string, String string2) {

		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == null)
				continue;
			ItemStack stack = this.get(i);
			if (stack.getItem() instanceof ItemWirelessModule) {

				NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(stack);
				if (!nbttagcompound.getBoolean("change")) {
					nbttagcompound.setInteger("Xcoord", xCoord);
					nbttagcompound.setInteger("Ycoord", yCoord);
					nbttagcompound.setInteger("Zcoord", zCoord);
					nbttagcompound.setInteger("tier", tier);
					nbttagcompound.setInteger("World1", dimensionId);
					nbttagcompound.setString("World", string);
					nbttagcompound.setString("Name", string2);
				}
			}
		}

	}

	public boolean personality() {

		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == null)
				continue;
			ItemStack stack = this.get(i);
			if (stack.getItem() instanceof AdditionModule)
				if (stack.getItemDamage() == 0)
					return true;
		}
		return false;
	}

	public double output_plus(double l) {
		int output = 0;
		for (int i = 0; i < this.size(); i++) {
			if (this.get(i) == null)
				continue;
			ItemStack stack = this.get(i);
			if ( IUItem.modules.get(stack.getItem()) != null ) {
				output +=l*IUItem.modules.get(stack.getItem()).percent;
			}
		}
		return output;
	}

}
