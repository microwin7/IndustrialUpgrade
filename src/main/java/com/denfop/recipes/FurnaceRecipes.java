package com.denfop.recipes;

import com.denfop.IUItem;
import com.denfop.register.RegisterOreDict;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class FurnaceRecipes {

    public static void recipe() {
        for (int i = 0; i < RegisterOreDict.itemNames().size(); i++) {
            if (i != 4 && i != 5 && i != 13 && i < 16)
                GameRegistry.addSmelting(new ItemStack(IUItem.ore, 1, i), new ItemStack(IUItem.iuingot, 1, i), 5);
            if (i >= 16)
                GameRegistry.addSmelting(new ItemStack(IUItem.ore1, 1, i - 16), new ItemStack(IUItem.iuingot, 1, i), 5);

        }
        for (int i = 0; i < RegisterOreDict.itemNames().size(); i++)
            if (i != 4 && i != 5 && i != 13)
                GameRegistry.addSmelting(new ItemStack(IUItem.crushed, 1, i), new ItemStack(IUItem.iuingot, 1, i), 5);
        for (int i = 0; i < RegisterOreDict.itemNames().size(); i++)
            GameRegistry.addSmelting(new ItemStack(IUItem.iudust, 1, i), new ItemStack(IUItem.iuingot, 1, i), 5);

        for (int i = 0; i < RegisterOreDict.itemNames1().size(); i++)
            GameRegistry.addSmelting(new ItemStack(IUItem.alloysdust, 1, i), new ItemStack(IUItem.alloysingot, 1, i), 5);

        GameRegistry.addSmelting(new ItemStack(IUItem.toriyore, 1), new ItemStack(IUItem.toriy, 1), 5);
        for (int i = 0; i < 3; i++)
            GameRegistry.addSmelting(new ItemStack(IUItem.radiationore, 1, i), new ItemStack(IUItem.radiationresources, 1, i), 5);


    }
}
