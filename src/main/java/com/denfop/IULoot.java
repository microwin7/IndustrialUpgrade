//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop;

import net.minecraft.item.ItemStack;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;

import java.util.ArrayList;
import java.util.List;

public class IULoot {

    private static final List<WeightedRandomChestContent> DUNGEON_CHEST = new ArrayList<>();
    private static final List<WeightedRandomChestContent> ingots = new ArrayList<>();

    public IULoot() {

        for (int i = 0; i < IUItem.name_mineral.size(); i++) {
            ingots.add(new WeightedRandomChestContent(new ItemStack(IUItem.iuingot, 1, i), 2, 6, 9));
            DUNGEON_CHEST.add(new WeightedRandomChestContent(new ItemStack(IUItem.iuingot, 1, i), 2, 5, 60));
        }
        addLoot("mineshaftCorridor", ingots);
        addLoot("pyramidDesertyChest", ingots);
        addLoot("pyramidJungleChest", ingots);
        addLoot("strongholdCorridor", ingots);
        addLoot("strongholdCrossing", ingots);
        addLoot("villageBlacksmith", ingots);
        addLoot("dungeonChest", DUNGEON_CHEST);
    }

    @SafeVarargs
    private static void addLoot(String category, List<WeightedRandomChestContent>... loot) {
        ChestGenHooks cgh = ChestGenHooks.getInfo(category);
        for (List<WeightedRandomChestContent> lootList : loot)
            for (WeightedRandomChestContent lootEntry : lootList)
                cgh.addItem(lootEntry);
    }


}
