package com.denfop.integration.wireless;

import net.minecraft.block.Block;
import ru.wirelesstools.MainWI;

public class WirelessIntegration {
    public static Block panel;
    public static Block panel1;
    public static Block panel2;
    public static Block panel3;
    public static Block panel4;
    public static Block panel5;
    public static Block panel6;
    public static Block panel7;
    public static Block panel8;
    public static Block panel9;
    public static Block panel10;
    public static Block panel11;

    public static void init() {
        panel = MainWI.wirelessasppersonal;
        panel1 = MainWI.wirelesshsppersonal;
        panel2 = MainWI.wirelessuhsppersonal;
        panel3 = MainWI.wirelessqsppersonal;
        panel4 = MainWI.wirelessspsppersonal;
        panel5 = MainWI.wirelessprotonsppersonal;
        panel6 = MainWI.wirelesssingsppersonal;
        panel7 = MainWI.wirelessabssppersonal;
        panel8 = MainWI.wirelessphotonicsppersonal;
        panel9 = MainWI.wirelessneutronsppersonal;
        panel10 = MainWI.wirelessadronsppersonal;
        panel11 = MainWI.wirelessbarionsppersonal;
    }
}
