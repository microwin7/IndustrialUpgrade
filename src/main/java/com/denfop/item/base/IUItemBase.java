package com.denfop.item.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class IUItemBase extends Item {

    public IUItemBase(String name) {
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        setUnlocalizedName(name);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }


}
