package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public enum EnumMultiMachine {

    DOUBLE_MACERATOR(TileEntityDoubleMacerator.class, "Macerator Double", 2, 300, 2, IUItem.machines_base, 1, 1),
    TRIPLE_MACERATOR(TileEntityTripleMacerator.class, "Macerator Triple", 2, 300, 3, IUItem.machines_base, 2, 2),
    QUAD_MACERATOR(TileEntityQuadMacerator.class, "Macerator Quad", 2, 300, 4, null, 0, 3),

    DOUBLE_COMPRESSER(TileEntityDoubleCompressor.class, "Compressor Double", 2, 300, 2, IUItem.machines_base, 4, 1),
    TRIPLE_COMPRESSER(TileEntityTripleCompressor.class, "Compressor Triple", 2, 300, 3, IUItem.machines_base, 5, 2),
    QUAD_COMPRESSER(TileEntityQuadCompressor.class, "Compressor Quad", 2, 300, 4, null, 0, 3),

    DOUBLE_EXTRACTOR(TileEntityDoubleExtractor.class, "Extractor Double", 2, 300, 2, IUItem.machines_base, 10, 1),
    TRIPLE_EXTRACTOR(TileEntityTripleExtractor.class, "Extractor Triple", 2, 300, 3, IUItem.machines_base, 11, 2),
    QUAD_EXTRACTOR(TileEntityQuadExtractor.class, "Extractor Quad", 2, 300, 4, null, 0, 3),

    DOUBLE_ELECTRIC_FURNACE(TileEntityDoubleElectricFurnace.class, "Double Electric Furnace", 3, 100, 2, IUItem.machines_base, 7, 1),
    TRIPLE_ELECTRIC_FURNACE(TileEntityTripleElectricFurnace.class, "Triple Electric Furnace", 3, 100, 3, IUItem.machines_base, 8, 2),
    QUAD_ELECTRIC_FURNACE(TileEntityQuadElectricFurnace.class, "Quad Electric Furnace", 3, 100, 4, null, 0, 3),

    DOUBLE_METAL_FORMER(TileEntityDoubleMetalFormer.class, "Metal Former Double", 10, 200, 2, IUItem.machines_base, 13, 1),
    TRIPLE_METAL_FORMER(TileEntityTripleMetalFormer.class, "Metal Former Triple", 10, 200, 3, IUItem.machines_base, 14, 1),
    QUAD_METAL_FORMER(TileEntityQuadMetalFormer.class, "Metal Former Quad", 10, 200, 4, null, 0, 3),

    DOUBLE_RECYCLER(TileEntityDoubleRecycler.class, "Double Recycler", 1, 45, 2, IUItem.machines_base1, 1, 1),
    TRIPLE_RECYCLER(TileEntityTripleRecycler.class, "Triple Recycler", 1, 45, 3, IUItem.machines_base1, 2, 2),
    QUAD_RECYCLER(TileEntityQuadRecycler.class, "Quad Recycler", 1, 45, 4, null, 0, 3),

    DOUBLE_COMB_RECYCLER(TileEntityDoubleCombRecycler.class, "Double Combined  Recycler", 1, 45, 2, IUItem.machines_base1, 4, 1),
    TRIPLE_COMB_RRECYCLER(TileEntityTripleCombRecycler.class, "Triple Combined  Recycler", 1, 45, 3, IUItem.machines_base1, 5, 2),
    QUAD_COMB_RRECYCLER(TileEntityQuadCombRecycler.class, "Quad Combined  Recycler", 1, 45, 4, null, 0, 3),
    COMB_MACERATOR(TileEntityCombMacerator.class, "Combined Macerator", 2, 300, 1, IUItem.machines_base1, 7, 0),
    COMB_DOUBLE_MACERATOR(TileEntityCombDoubleMacerator.class, "Combined Macerator Double", 2, 300, 2, IUItem.machines_base1, 8, 1),
    COMB_TRIPLE_MACERATOR(TileEntityCombTripleMacerator.class, "Combined Macerator Triple", 2, 300, 3, IUItem.machines_base1, 9, 2),
    COMB_QUAD_MACERATOR(TileEntityCombQuadMacerator.class, "Combined Macerator Quad", 2, 300, 4, null, 0, 3),

    Rolling(TileEntityRolling.class, "Rolling", 10, 200, 1, IUItem.machines_base2, 1, 0),
    DOUBLE_Rolling(TileEntityDoubleRolling.class, " Double Rolling", 10, 200, 2, IUItem.machines_base2, 2, 1),
    TRIPLE_Rolling(TileEntityTripleRolling.class, " Triple Rolling", 10, 200, 3, IUItem.machines_base2, 3, 2),
    QUAD_Rolling(TileEntityQuadRolling.class, "Quad Rolling", 10, 200, 4, null, 0, 3),
    Extruding(TileEntityExtruding.class, "Extruding", 10, 200, 1, IUItem.machines_base2, 5, 0),
    DOUBLE_Extruding(TileEntityDoubleExtruding.class, "Double Extruding ", 10, 200, 2, IUItem.machines_base2, 6, 1),
    TRIPLE_Extruding(TileEntityTripleExtruding.class, "Triple Extruding", 10, 200, 3, IUItem.machines_base2, 7, 2),
    QUAD_Extruding(TileEntityQuadExtruding.class, "Quad Extruding", 10, 200, 4, null, 0, 3),
    Cutting(TileEntityCutting.class, "Cutting", 10, 200, 1, IUItem.machines_base2, 9, 0),
    DOUBLE_Cutting(TileEntityDoubleCutting.class, "Double Cutting", 10, 200, 2, IUItem.machines_base2, 10, 1),
    TRIPLE_Cutting(TileEntityTripleCutting.class, "Triple Cutting", 10, 200, 3, IUItem.machines_base2, 11, 2),
    QUAD_Cutting(TileEntityQuadCutting.class, "Quad Cutting", 10, 200, 4, null, 0, 3),

    Fermer(TileEntityFermer.class, "Fermer", 4, 500, 1, IUItem.machines_base3, 1, 0),
    DOUBLE_Fermer(TileEntityDoubleFermer.class, "Double Fermer", 4, 500, 2, IUItem.machines_base3, 2, 1),
    TRIPLE_Fermer(TileEntityTripleFermer.class, "Triple Fermer", 4, 500, 3, IUItem.machines_base3, 3, 2),
    QUAD_Fermer(TileEntityQuadFermer.class, "Quad Fermer", 4, 500, 4, null, 0, 3),

    AssamplerScrap(TileEntityAssamplerScrap.class, "AssamplerScrap", 1, 25, 1, IUItem.machines_base3, 5, 0),
    DOUBLE_AssamplerScrap(TileEntityDoubleAssamplerScrap.class, "Double AssamplerScrap", 1, 25, 2, IUItem.machines_base3, 6, 1),
    TRIPLE_AssamplerScrap(TileEntityTripleAssamplerScrap.class, "Triple AssamplerScrap", 1, 25, 3, IUItem.machines_base3, 7, 2),
    QUAD_AssamplerScrap(TileEntityQuadAssamplerScrap.class, "Quad AssamplerScrap", 1, 25, 4, null, 0, 3);

    public final int usagePerTick;
    public final int lenghtOperation;
    public final int sizeWorkingSlot;
    public final Block block_new;
    public final int meta_new;
    public final int upgrade;
    private final Class<? extends TileEntity> clazz;
    private final String name;

    EnumMultiMachine(Class<? extends TileEntity> clazz, String name, int usagePerTick, int lenghtOperation, int sizeWorkingSlot, Block block_new, int meta_new, int upgrade) {
        this.clazz = clazz;
        this.name = name;
        this.usagePerTick = usagePerTick;
        this.lenghtOperation = lenghtOperation;
        this.sizeWorkingSlot = sizeWorkingSlot;
        this.block_new = block_new;
        this.meta_new = meta_new;
        this.upgrade = upgrade;
    }

    public static void registerTile() {
        for (EnumMultiMachine machine : EnumMultiMachine.values()) {
            GameRegistry.registerTileEntity(machine.clazz, machine.name);
        }
    }

}
