package com.denfop.utils;

import com.brandon3055.draconicevolution.DraconicEvolution;
import net.minecraft.item.ItemStack;

public class Enchant {
    public static void addEnchant(ItemStack stack, int reaper) {
        stack.addEnchantment(DraconicEvolution.reaperEnchant, reaper);

    }
}
