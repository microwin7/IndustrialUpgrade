package com.denfop.integration.emc;

import emt.init.EMTBlocks;
import net.minecraft.block.Block;

public class EMTIntegration {
    public static Block panel;
    public static Block panel1;

    public static void init() {
        panel = EMTBlocks.emtSolars;
        panel1 = EMTBlocks.emtSolars2;
    }
}
