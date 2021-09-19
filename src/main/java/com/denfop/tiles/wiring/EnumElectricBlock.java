package com.denfop.tiles.wiring;


import com.denfop.Config;
import net.minecraft.util.StatCollector;

public enum EnumElectricBlock {
    BATBOX("iu.blockBatBox.name", 1, 32, 40000, false),
    CESU("iu.blockCESU.name", 2, 128, 300000, false),
    MFE("iu.blockMFE1.name", 3, 512, 4000000, false),
    MFSU("iu.blockMFSU1.name", 4, 2048, 40000000, false),
    ADV_MFSU("iu.blockMFE.name", Config.tier_advmfsu, Config.adv_enegry, Config.adv_storage, false),
    ULT_MFSU("iu.blockMFSU.name", Config.tier_ultmfsu, Config.ult_enegry, Config.ult_storage, false),
    PER_MFSU("iu.blockPerMFSU.name", (int) Config.tierPerMFSU, Config.PerMFSUOutput, Config.PerMFSUStorage, false),
    BAR_MFSU("iu.blockBarMFSU.name", (int) Config.tierBarMFSU, Config.BarMFSUOutput, Config.BarMFSUStorage, false),
    HAD_MFSU("iu.blockAdrMFSU.name", (int) Config.tierHadrMFSU, Config.HadrMFSUOutput, Config.HadrMFSUStorage, false),
    GRA_MFSU("iu.blockGraMFSU.name", (int) Config.tierGraMFSU, Config.GraMFSUOutput, Config.GraMFSUStorage, false),
    KVR_MFSU("iu.blockKvrMFSU.name", (int) Config.tierKrvMFSU, Config.KrvMFSUOutput, Config.KrvMFSUStorage, false),

    BATBOX_CHARGEPAD("iu.blockChargepadBatBox.name", 1, 32, 40000, true),
    CESU_CHARGEPAD("iu.blockChargepadCESU.name", 2, 128, 300000, true),
    MFE_CHARGEPAD("iu.blockChargepadMFE1.name", 3, 512, 4000000, true),
    MFSU_CHARGEPAD("iu.blockChargepadMFSU.name", 4, 2048, 40000000, true),
    ADV_MFSU_CHARGEPAD("iu.blockChargepadMFE.name", Config.tier_advmfsu, Config.adv_enegry, Config.adv_storage, true),
    ULT_MFSU_CHARGEPAD("iu.blockChargepadMFES.name", Config.tier_ultmfsu, Config.ult_enegry, Config.ult_storage, true),
    PER_MFSU_CHARGEPAD("iu.blockChargepadPerMFSU.name", (int) Config.tierPerMFSU, Config.PerMFSUOutput, Config.PerMFSUStorage, true),
    BAR_MFSU_CHARGEPAD("iu.blockChargepadBarMFSU.name", (int) Config.tierBarMFSU, Config.BarMFSUOutput, Config.BarMFSUStorage, true),
    HAD_MFSU_CHARGEPAD("iu.blockChargepadAdrMFSU.name", (int) Config.tierHadrMFSU, Config.HadrMFSUOutput, Config.HadrMFSUStorage, true),
    GRA_MFSU_CHARGEPAD("iu.blockChargepadGraMFSU.name", (int) Config.tierGraMFSU, Config.GraMFSUOutput, Config.GraMFSUStorage, true),
    KVR_MFSU_CHARGEPAD("iu.blockChargepadKvrMFSU.name", (int) Config.tierKrvMFSU, Config.KrvMFSUOutput, Config.KrvMFSUStorage, true),

    ;

    public final int tier;
    public final double maxstorage;
    public final double producing;
    public final String name1;
    public final boolean chargepad;

    EnumElectricBlock(String name1, int tier, double producing, double maxstorage, boolean chargepad) {
        this.name1 = StatCollector.translateToLocal(name1);
        this.tier = tier;
        this.maxstorage = maxstorage;
        this.producing = producing;
        this.chargepad = chargepad;
    }

}
