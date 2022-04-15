package com.denfop.tab;

import com.denfop.IUItem;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class IUTab extends CreativeTabs {
    private final int type;

    public IUTab(int type, String name) {
        super(name);
        this.type = type;

    }

    public Item getTabIconItem() {
        switch (type) {
            case 0:
                return Item.getItemFromBlock(IUItem.blockpanel);
            case 1:
                return IUItem.module1;
            case 2:
                return IUItem.quantumHelmet;
            case 3:
                return IUItem.circuitSpectral;
            case 4:
                return Item.getItemFromBlock(IUItem.toriyore);
        }
        return Item.getItemFromBlock(IUItem.blockpanel);
    }
}
