package com.denfop.invslot;

import com.denfop.IUItem;
import com.denfop.item.modules.EnumQuarryType;
import com.denfop.item.modules.ItemWirelessModule;
import com.denfop.item.modules.QuarryModule;
import com.denfop.tiles.mechanism.TileEntityBaseQuantumQuarry;
import com.denfop.utils.NBTData;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

public class InvSlotQuantumQuarry extends InvSlot {

    public final int type;
    public int stackSizeLimit;

    public InvSlotQuantumQuarry(TileEntityInventory base1, int oldStartIndex1,String name,int type) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, 1, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
        this.type=type;
    }

    public boolean accepts(ItemStack itemStack) {
        if(type == 0) {
            if (itemStack.getItem() instanceof ItemWirelessModule) {
                TileEntityBaseQuantumQuarry base = (TileEntityBaseQuantumQuarry) this.base;
                NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(itemStack);
                nbttagcompound.setInteger("Xcoord", base.xCoord);
                nbttagcompound.setInteger("Ycoord", base.yCoord);
                nbttagcompound.setInteger("Zcoord", base.zCoord);
                nbttagcompound.setInteger("tier", 14);
                nbttagcompound.setInteger("World1", base.getWorldObj().provider.dimensionId);
                nbttagcompound.setString("World", base.getWorldObj().provider.getDimensionName());
                nbttagcompound.setString("Name", StatCollector.translateToLocal("iu.blockQuantumQuarry.name"));
            }
            return itemStack.getItem() instanceof QuarryModule && (IUItem.quarry_modules.get(itemStack.getItemDamage()).type != EnumQuarryType.WHITELIST && IUItem.quarry_modules.get(itemStack.getItemDamage()).type != EnumQuarryType.BLACKLIST);
        }else if(type == 1){
            return itemStack.getItem() instanceof QuarryModule && itemStack.getItemDamage() > 11;

        }else{
            return itemStack.getItem().equals(IUItem.analyzermodule);

        }
        }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
