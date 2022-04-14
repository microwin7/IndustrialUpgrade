package com.denfop.tiles.mechanism;

import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.EnumMultiMachine;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.Recipes;
import ic2.core.init.Localization;

public class TileEntityCompressor extends TileEntityMultiMachine {

    public TileEntityCompressor() {
        super(
                EnumMultiMachine.COMPRESSER.usagePerTick,
                EnumMultiMachine.COMPRESSER.lenghtOperation,
                Recipes.compressor,
                4
        );
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.compressor);
    }


    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.COMPRESSER;
    }

    public String getInventoryName() {
        return Localization.translate("iu.blockCompressor.name");
    }

    public String getStartSoundFile() {
        return "Machines/CompressorOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }


}
