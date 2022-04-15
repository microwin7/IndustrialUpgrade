package com.denfop.integration.de;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SSPDEItem extends Item {

    public SSPDEItem(String name) {
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        setUnlocalizedName(name);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }

    public int getItemStackLimit() {
        return this.maxStackSize;
    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass) {
        return true;
    }
}
