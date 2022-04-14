package com.simplequarries;

import com.denfop.items.modules.EnumQuarryModules;
import com.denfop.items.modules.EnumQuarryType;
import com.denfop.items.modules.QuarryModule;
import com.denfop.utils.ModUtils;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;

public class InvSlotBaseQuarry extends InvSlot {


    public final TileEntityBaseQuarry tile;
    public int stackSizeLimit;

    public InvSlotBaseQuarry(TileEntityBaseQuarry base1, int oldStartIndex1) {
        super(base1, "input", Access.I, oldStartIndex1, InvSlot.InvSide.TOP);
        this.tile = base1;
        this.stackSizeLimit = 1;
    }

    @Override
    public void put(final int index, final ItemStack content) {
        super.put(index, content);
        if (!this.isEmpty()) {
            for (int i = 0; i < this.size(); i++) {
                ItemStack type1 = this.get(i);
                if (type1.isEmpty()) {
                    continue;
                }
                EnumQuarryModules module = EnumQuarryModules.getFromID(type1.getItemDamage());
                EnumQuarryType type = module.type;

                switch (type) {
                    case SPEED:
                        tile.energyconsume += tile.consume * (module.meta * -0.03);
                        break;
                    case DEPTH:
                        if (tile.col == 1) {
                            tile.col = module.efficiency * module.efficiency;
                            tile.energyconsume += tile.consume * (module.cost);
                        }
                        break;
                    case LUCKY:
                        if (tile.chance == 0) {
                            tile.chance = module.efficiency;
                            tile.energyconsume += tile.consume * (module.cost);
                        }
                        break;
                    case FURNACE:
                        if (!tile.furnace) {
                            tile.furnace = true;
                            tile.energyconsume += tile.consume * (module.cost);
                        }
                        break;
                    case WHITELIST:
                    case BLACKLIST:
                        tile.list_modules = module;
                        break;

                }
            }
        }
    }

    public boolean accepts(ItemStack itemStack) {

        if (itemStack.getItem() instanceof QuarryModule && itemStack.getItemDamage() > 11) {
            for (int i = 0; i < this.size(); i++) {
                if (!(this.get(i).isEmpty() || (this
                        .get(i)
                        .getItem() instanceof QuarryModule && !(itemStack.getItemDamage() > 11)))) {
                    return false;
                }
            }
            ((TileEntityBaseQuarry) this.base).list = ModUtils.getListFromModule(itemStack);
            return true;
        }
        if (itemStack.getItem() instanceof QuarryModule && (EnumQuarryModules.getFromID(itemStack.getItemDamage()).type != EnumQuarryType.WHITELIST && EnumQuarryModules.getFromID(
                itemStack.getItemDamage()).type != EnumQuarryType.BLACKLIST)) {
            if (EnumQuarryModules.getFromID(
                    itemStack.getItemDamage()).type == EnumQuarryType.DEPTH) {
                tile.blockpos = null;
            }
            return itemStack.getItem() instanceof QuarryModule && (EnumQuarryModules.getFromID(itemStack.getItemDamage()).type != EnumQuarryType.WHITELIST && EnumQuarryModules.getFromID(
                    itemStack.getItemDamage()).type != EnumQuarryType.BLACKLIST);
        }
        return false;
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

}
