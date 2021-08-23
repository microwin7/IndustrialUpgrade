package com.denfop.invslot;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.api.IPanel;
import com.denfop.integration.avaritia.ItemAvaritiaSolarPanel;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotSintezator extends InvSlot {


    public InvSlotSintezator(TileEntityInventory base1, int oldStartIndex1) {
        super(base1, "input2", oldStartIndex1, InvSlot.Access.IO, 9, InvSlot.InvSide.TOP);
       this.setStackSizeLimit(Config.limit);
    }

    public boolean accepts(ItemStack itemStack) {

        return itemStack.getItem() instanceof IPanel && (IUItem.map2.get(itemStack.getUnlocalizedName()+".name") != null || (itemStack.getItem() instanceof ItemAvaritiaSolarPanel));
    }



}
