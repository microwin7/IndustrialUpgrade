package com.denfop.invslot;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.item.modules.*;
import com.denfop.tiles.base.TileSintezator;
import com.denfop.tiles.sintezator.TileEntitySintezator;
import com.denfop.utils.ModUtils;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotSintezator extends InvSlot {


    public final int type;

    public InvSlotSintezator(TileEntityInventory base1, int oldStartIndex1, String name, int type, int count) {
        super(base1, name, oldStartIndex1, InvSlot.Access.IO, count, InvSlot.InvSide.TOP);
        this.type = type;
        if (type == 0)
            this.setStackSizeLimit(Config.limit);
        else
            this.setStackSizeLimit(1);
    }

    public boolean accepts(ItemStack itemStack) {

        if (this.type == 0)
            return IUItem.map2.containsKey(itemStack.getUnlocalizedName() + ".name") || IUItem.panel_list.containsKey(itemStack.getUnlocalizedName() + ".name");
        else
            return itemStack.getItem() instanceof ModuleBase
                    || itemStack.getItem() instanceof ItemWirelessModule
                    || (itemStack.getItem() instanceof AdditionModule && itemStack.getItemDamage() == 4)
                    || (itemStack.getItem() instanceof ModuleType)
                    ;
    }

    public void getrfmodule() {
        TileSintezator tile = (TileSintezator) base;
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) != null && this.get(i).getItemDamage() == 4 && this.get(i).getItem() instanceof AdditionModule) {
                tile.getmodulerf = true;
                return;
            }
        tile.getmodulerf = false;
    }

    public int solartype() {
        TileEntitySintezator tile = (TileEntitySintezator) base;

        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) != null && this.get(i).getItem() instanceof ModuleType)
                list1.add(get(i).getItemDamage() + 1);
            else
                list1.add(0);
        com.denfop.tiles.overtimepanel.EnumType type = IUItem.type.get(ModUtils.slot(list1));

        return tile.setSolarType(type);
    }

    public void checkmodule() {
        TileSintezator tile = (TileSintezator) base;
        double temp_day = tile.genDay;
        double temp_night = tile.genNight;
        double temp_storage = tile.maxStorage;
        double temp_producing = tile.production;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null && IUItem.modules.get(this.get(i).getItem()) != null) {
                EnumModule module = IUItem.modules.get(this.get(i).getItem());
                EnumType type = module.type;
                double percent = module.percent;
                switch (type) {
                    case DAY:
                        temp_day += tile.genDay * percent;
                        break;
                    case NIGHT:
                        temp_night += tile.genNight * percent;
                        break;
                    case STORAGE:
                        temp_storage += tile.maxStorage * percent;
                        break;
                    case OUTPUT:
                        temp_producing += tile.production * percent;
                        break;
                }
            }
        }
        tile.genDay = temp_day;
        tile.genNight = temp_night;
        tile.maxStorage = temp_storage;
        tile.production = temp_producing;
    }

}
