package com.denfop.invslot;

import com.denfop.IUItem;
import com.denfop.item.modules.EnumModule;
import com.denfop.item.modules.EnumType;
import com.denfop.item.modules.ModuleBase;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotGenSunarrium extends InvSlot {

    private int stackSizeLimit;

    public InvSlotGenSunarrium(TileEntityInventory base1) {
        super(base1, "input",4, InvSlot.Access.I, 4, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack.getItem() instanceof ModuleBase && (IUItem.modules.get(itemStack.getItem()).type.equals(EnumType.DAY) ||IUItem.modules.get(itemStack.getItem()).type.equals(EnumType.NIGHT) ||IUItem.modules.get(itemStack.getItem()).type.equals(EnumType.MOON_LINSE) );
    }
    public List<Double> coefday(){
        double coef = 0;
        double coef1 = 0;
        double coef2 = 0;
        List<Double> lst = new ArrayList<>();
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null && IUItem.modules.get(this.get(i).getItem())!= null && this
                    .get(i)
                    .getItem() instanceof ModuleBase) {
                EnumModule module = IUItem.modules.get(this.get(i).getItem());
                EnumType type = module.type;
                double percent = module.percent;
                switch (type) {
                    case DAY:
                        coef +=percent;
                        break;
                    case NIGHT:
                        coef1 +=percent;
                        break;
                    case MOON_LINSE:
                        coef2 = percent;
                        break;
                }
            }
        }

        lst.add(coef);
        lst.add(coef1);
        lst.add(coef2);
        return lst;
    }
    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }


}
