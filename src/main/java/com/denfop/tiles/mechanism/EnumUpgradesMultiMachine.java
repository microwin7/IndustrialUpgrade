package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;

@SuppressWarnings("SameParameterValue")
public enum EnumUpgradesMultiMachine {
    DOUBLE_MACERATOR(Ic2Items.macerator.getUnlocalizedName(), 0, IUItem.machines_base, 0),
    DOUBLE_EXTRACTOR(Ic2Items.extractor.getUnlocalizedName(), 0, IUItem.machines_base, 9),
    DOUBLE_FURNACE("ElecFurnace", 0, IUItem.machines_base, 6),
    DOUBLE_COMPRESSOR(Ic2Items.compressor.getUnlocalizedName(), 0, IUItem.machines_base, 3),
    DOUBLE_RECYLER(Ic2Items.recycler.getUnlocalizedName(), 0, IUItem.machines_base1, 0),
    DOUBLE_METALFORMER(Ic2Items.metalformer.getUnlocalizedName(), 0, IUItem.machines_base, 12);

    public final int meta_item;
    public final Block block_new;
    public final int meta_new;
    public final String name;

    EnumUpgradesMultiMachine(String name, int meta_item, Block block_new, int meta_new) {
        this.name = name;
        this.meta_item = meta_item;
        this.block_new = block_new;
        this.meta_new = meta_new;
    }

    public static void register() {
        for (EnumUpgradesMultiMachine value : values()) {
            IUItem.map3.put(value.name, value);
        }
    }
}
