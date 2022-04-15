package com.denfop.tiles.overtimepanel;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.integration.avaritia.AvaritiaIntegration;
import com.denfop.integration.avaritia.TileEntityInfinitySolarPanel;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.integration.botania.TileEntityElementumSolarPanel;
import com.denfop.integration.botania.TileEntityManasteelSolarPanel;
import com.denfop.integration.botania.TileEntityTerrasteelSolarPanel;
import com.denfop.integration.compactsolar.CompactSolarIntegration;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.integration.de.TileEntityAwakenedSolarPanel;
import com.denfop.integration.de.TileEntityChaoticSolarPanel;
import com.denfop.integration.de.TileEntityDraconianSolarPanel;
import com.denfop.integration.emc.EMTIntegration;
import com.denfop.integration.thaumcraft.ThaumcraftIntegration;
import com.denfop.integration.thaumcraft.TileEntityThaumSolarPanel;
import com.denfop.integration.thaumcraft.TileEntityVoidSolarPanel;
import com.denfop.integration.thaumtinker.ThaumTinkerIntegration;
import com.denfop.integration.thaumtinker.TileEntityIhorSolarPanel;
import com.denfop.integration.wireless.WirelessIntegration;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public enum EnumSolarPanels {

    ADVANCED_SOLAR_PANEL(null, IUItem.blockpanel, 0, TileEntityAdvancedSolarPanel.class, !Config.advloaded ?"Advanced Solar Panel" :"Advanced Solar Panel IU" , "blockAdvancedSolarPanel.name", 1, Config.advGenDay, Config.advStorage, Config.advOutput, true, "default", "asp_top", true),
    HYBRID_SOLAR_PANEL(ADVANCED_SOLAR_PANEL, IUItem.blockpanel, 1, TileEntityHybridSolarPanel.class, !Config.advloaded ?"Hybrid Solar Panel" : "Hybrid Solar Panel IU", "blockHybridSolarPanel.name", 2, Config.hGenDay, Config.hStorage, Config.hOutput, true, "default", "hsp_top", true),
    PERFECT_SOLAR_PANEL(HYBRID_SOLAR_PANEL, IUItem.blockpanel, 2, TileEntityPerfectSolarPanel.class, !Config.advloaded ?"Perfect Solar Panel" : "Perfect Solar Panel IU ", "blockUltimateSolarPanel.name", 3, Config.uhGenDay, Config.uhStorage, Config.uhOutput, true, "default", "usp_top", true),
    QUANTUM_SOLAR_PANEL(PERFECT_SOLAR_PANEL, IUItem.blockpanel, 3, TileEntityQuantumSolarPanel.class, !Config.advloaded ?"Quantum Solar Panel" : "Quantum Solar Panel IU", "blockQuantumSolarPanel.name", 4, Config.qpGenDay, Config.qpStorage, Config.qpOutput, true, "default", "qsp_top", true),
    SPECTRAL_SOLAR_PANEL(QUANTUM_SOLAR_PANEL, IUItem.blockpanel, 4, TileEntitySpectralSolarPanel.class, !Config.iu_old_loaded ? "Spectral Solar Panel" : "Spectral Solar Panel IU", "blockSpectralSolarPanel.name", 5, Config.spectralpanelGenDay, Config.spectralpanelstorage, Config.spectralpanelOutput, true, "default", "spsp_top", true),
    PROTON_SOLAR_PANEL(SPECTRAL_SOLAR_PANEL, IUItem.blockpanel, 5, TileEntityProtonSolarPanel.class, !Config.iu_old_loaded ? "Proton Solar Panel" : "Proton Solar Panel IU" ,  "blockProtonSolarPanel.name", 6, Config.protongenDay, Config.protonstorage, Config.protonOutput, true, "default", "psp_top", true),
    SINGULAR_SOLAR_PANEL(PROTON_SOLAR_PANEL, IUItem.blockpanel, 6, TileEntitySingularSolarPanel.class, !Config.iu_old_loaded ? "Singular Solar Panel" : "Singular Solar Panel IU" , "blockSingularSolarPanel.name", 7, Config.singularpanelGenDay, Config.singularpanelstorage, Config.singularpanelOutput, true, "default", "ssp_top", true),
    DIFFRACTION_SOLAR_PANEL(SINGULAR_SOLAR_PANEL, IUItem.blockpanel, 7, TileEntityDiffractionSolarPanel.class, !Config.iu_old_loaded ? "Diffraction Solar Panel" :"Diffraction Solar Panel IU", "blockAdminSolarPanel.name", 8, Config.adminpanelGenDay, Config.AdminpanelStorage, Config.AdminpanelOutput, true, "default", "admsp_model", true),
    PHOTONIC_SOLAR_PANEL(DIFFRACTION_SOLAR_PANEL, IUItem.blockpanel, 8, TileEntityPhotonicSolarPanel.class, !Config.iu_old_loaded ? "Photon Solar Panel" : "Photon Solar Panel IU", "blockPhotonicSolarPanel.name", 9, Config.photonicpanelGenDay, Config.photonicpanelStorage, Config.photonicpanelOutput, true, "default", "phsp_top", true),
    NEUTRONIUN_SOLAR_PANEL(PHOTONIC_SOLAR_PANEL, IUItem.blockpanel, 9, TileEntityNeutronSolarPanel.class, !Config.iu_old_loaded ? "Neutron Solar Panel" : "Neutron Solar Panel IU", "blockNeutronSolarPanel.name", 10, Config.neutronpanelGenDay, Config.neutronpanelStorage, Config.neutronpanelOutput, true, "default", "nsp_top", true),
    BARION_SOLAR_PANEL(NEUTRONIUN_SOLAR_PANEL, IUItem.blockpanel, 10, TileEntityBarionSolarPanel.class, "Barion Solar Panel", "blockBarionSolarPanel.name", 11, Config.barGenDay, Config.barStorage, Config.barOutput, true, "default", "bsp_top", true),
    HADRON_SOLAR_PANEL(BARION_SOLAR_PANEL, IUItem.blockpanel, 11, TileEntityHadronSolarPanel.class, "Hadron Solar Panel", "blockAdronSolarPanel.name", 12, Config.adrGenDay, Config.adrStorage, Config.adrOutput, true, "default", "adsp_top", true),
    GRAVITON_SOLAR_PANEL(HADRON_SOLAR_PANEL, IUItem.blockpanel, 12, TileEntityGravitonSolarPanel.class, "Graviton Solar Panel", "blocGravitonSolarPanel.name", 13, Config.graGenDay, Config.graStorage, Config.graOutput, true, "default", "grasp_top", true),
    KVARK_SOLAR_PANEL(GRAVITON_SOLAR_PANEL, IUItem.blockpanel, 13, TileEntityQuarkSolarPanel.class, "Kvark Solar Panel", "blockKvarkSolarPanel.name", 14, Config.kvrGenDay, Config.kvrStorage, Config.kvrOutput, true, "default", "kvsp_top", true),
    NEUTRONIUM_SOLAR_PANEL_AVARITIA(PHOTONIC_SOLAR_PANEL, Config.AvaritiaLoaded && Config.Avaritia ? AvaritiaIntegration.blockAvSolarPanel : null, 0, com.denfop.integration.avaritia.TileEntityNeutronSolarPanel.class, "Neutron Solar Panel Avaritia", "blockNeutronSolarPanelAvaritia.name", Config.tier, Config.neutrongenday, Config.neutronStorage, Config.neutronOutput, Config.AvaritiaLoaded && Config.Avaritia, "avaritia", "neutronium_top", false),
    INFINITY_SOLAR_PANEL(NEUTRONIUM_SOLAR_PANEL_AVARITIA, Config.AvaritiaLoaded && Config.Avaritia ? AvaritiaIntegration.blockAvSolarPanel : null, 1, TileEntityInfinitySolarPanel.class, "Infinity Solar Panels", "blockinfinitySolarPanel.name", 12, Config.InfinityGenDay, Config.InfinityStorage, Config.InfinityOutput, Config.AvaritiaLoaded && Config.Avaritia, "avaritia", "infinity_top", false),
    MANASTEEL_SOLAR_PANEL(ADVANCED_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel, 0, TileEntityManasteelSolarPanel.class, "Manasteel Solar Panel", "blockManasteelSolarPanel.name", Config.manasteeltier, Config.manasteelgenday, Config.manasteelstorage, Config.manasteeloutput, Config.BotaniaLoaded && Config.Botania, "botania", "manasteel_top", true),
    ELEMENTUM_SOLAR_PANEL(MANASTEEL_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel, 1, TileEntityElementumSolarPanel.class, "Elementum Solar Panel", "blockElementumSolarPanel.name", Config.elementiumtier, Config.elementiumgenday, Config.elementiumstorage, Config.elementiumoutput, Config.BotaniaLoaded && Config.Botania, "botania", "elementium_top", true),
    TERRASTEEL_SOLAR_PANEL(ELEMENTUM_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel, 2, TileEntityTerrasteelSolarPanel.class, "Terrasteel Solar Panel", "blockTerrasteelSolarPanel.name", Config.terasteeltier, Config.terasteelgenday, Config.terasteelstorage, Config.terasteeloutput, Config.BotaniaLoaded && Config.Botania, "botania", "terasteel_top", true),
    DRACONIC_SOLAR_PANEL(HYBRID_SOLAR_PANEL, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null, 0, TileEntityDraconianSolarPanel.class, "Draconian Solar Panel", "blockDraconSolarPanel.name", Config.draconictier, Config.draconicgenday, Config.draconicstorage, Config.draconicoutput, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic, "draconic", "draconium_top", true),
    AWAKENED_SOLAR_PANEL(DRACONIC_SOLAR_PANEL, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null, 1, TileEntityAwakenedSolarPanel.class, "Awakened Solar Panel", "blockAwakenedSolarPanel.name", Config.awakenedtier, Config.awakenedgenday, Config.awakenedstorage, Config.awakenedoutput, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic, "draconic", "awakened_top", true),
    CHAOTIC_SOLAR_PANEL(AWAKENED_SOLAR_PANEL, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null, 2, TileEntityChaoticSolarPanel.class, "Chaotic Solar Panel", "blockChaosSolarPanel.name", Config.chaostier, Config.chaosgenday, Config.chaosstorage, Config.chaosoutput, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic, "draconic", "chaotic_top", true),
    THAUM_SOLAR_PANEL(HYBRID_SOLAR_PANEL, ThaumcraftIntegration.blockThaumSolarPanel, 0, TileEntityThaumSolarPanel.class, "Thaum Solar Panel", "blockThaumSolarPanel.name", Config.thaumtier, Config.thaumgenday, Config.thaumstorage, Config.thaumoutput, Config.thaumcraft && Config.Thaumcraft, "thaumcraft", "thaum_model", true),
    VOID_SOLAR_PANEL(THAUM_SOLAR_PANEL, ThaumcraftIntegration.blockThaumSolarPanel, 1, TileEntityVoidSolarPanel.class, "Void Solar Panel", "blockVoidSolarPanel.name", Config.voidtier, Config.voidgenday, Config.voidstorage, Config.voidoutput, Config.thaumcraft && Config.Thaumcraft, "thaumcraft", "void_model", true),
    IHOR_SOLAR_PANEL(VOID_SOLAR_PANEL, ThaumTinkerIntegration.blockThaumTinkerSolarPanel, 0, TileEntityIhorSolarPanel.class, "Ihor Solar Panel", "blockIhorSolarPanel.name", Config.ihortier, Config.ihorgenday, Config.ihorstorage, Config.ihoroutput, Config.thaumcraft && Config.Thaumcraft && Loader.isModLoaded("ThaumicTinkerer"), "thaumcraft", "ichor_model", true),

    LV_SOLAR_PANEL(null, Loader.isModLoaded("CompactSolars") ? CompactSolarIntegration.solar : null, 0, null, null, "tile.compactsolars:LV_block.name", 1, 8, 64, 8, false, "compact", "lv_model", false),
    HV_SOLAR_PANEL(LV_SOLAR_PANEL, Loader.isModLoaded("CompactSolars") ? CompactSolarIntegration.solar : null, 1, null, null, "tile.compactsolars:MV_block.name", 2, 64, 256, 64, false, "compact", "mv_model", false),
    MV_SOLAR_PANEL(HV_SOLAR_PANEL, Loader.isModLoaded("CompactSolars") ? CompactSolarIntegration.solar : null, 2, null, null, "tile.compactsolars:HV_block.name", 3, 512, 1024, 512, false, "compact", "hv_model", false),

    SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 0, null, null, "tile.EMT.solar.compressed.name", 4, 10, 10000, 10, false, "emt", "panel_model", false),
    DOUBLE_SOLAR_PANEL(SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 1, null, null, "tile.EMT.solar.doublecompressed.name", 4, 100, 10000, 100, false, "emt", "doublepanel_model", false),
    TRIPLE_SOLAR_PANEL(DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 2, null, null, "tile.EMT.solar.triplecompressed.name", 4, 1000, 10000, 1000, false, "emt", "triplepanel_model", false),
    AQUA_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 3, null, null, "tile.EMT.solar.water.name", 4, 10, 10000, 10, false, "emt", "waterpanel_model", false),
    AQUA_DOUBLE_SOLAR_PANEL(AQUA_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 4, null, null, "tile.EMT.solar.doublewater.name", 4, 100, 10000, 100, false, "emt", "waterdoublepanel_model", false),
    AQUA_TRIPLE_SOLAR_PANEL(AQUA_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 5, null, null, "tile.EMT.solar.triplewater.name", 4, 1000, 10000, 1000, false, "emt", "watertriplepanel_model", false),
    DARK_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 6, null, null, "tile.EMT.solar.dark.name", 4, 0, 10000, 10, false, "emt", "darkpanel_model", false),
    DARK_DOUBLE_SOLAR_PANEL(DARK_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 7, null, null, "tile.EMT.solar.doubledark.name", 4, 0, 10000, 100, false, "emt", "darkdoublepanel_model", false),
    DARK_TRIPLE_SOLAR_PANEL(DARK_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 8, null, null, "tile.EMT.solar.tripledark.name", 4, 0, 10000, 1000, false, "emt", "darktriplepanel_model", false),
    ORDO_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 9, null, null, "tile.EMT.solar.order.name", 4, 30, 10000, 10, false, "emt", "orderpanel_model", false),
    ORDO_DOUBLE_SOLAR_PANEL(ORDO_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 10, null, null, "tile.EMT.solar.doubleorder.name", 4, 300, 10000, 100, false, "emt", "orderdoublepanel_model", false),
    ORDO_TRIPLE_SOLAR_PANEL(ORDO_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 11, null, null, "tile.EMT.solar.tripleorder.name", 4, 3000, 10000, 1000, false, "emt", "ordertriplepanel_model", false),
    FIRE_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 12, null, null, "tile.EMT.solar.fire.name", 4, 10, 10000, 10, false, "emt", "firepanel_model", false),
    FIRE_DOUBLE_SOLAR_PANEL(FIRE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 13, null, null, "tile.EMT.solar.doublefire.name", 4, 100, 10000, 100, false, "emt", "firedoublepanel_model", false),
    FIRE_TRIPLE_SOLAR_PANEL(FIRE_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 14, null, null, "tile.EMT.solar.triplefire.name", 4, 1000, 10000, 1000, false, "emt", "firetriplepanel_model", false),
    AER_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel : null, 15, null, null, "tile.EMT.solar.air.name", 4, 10, 10000, 10, false, "emt", "airpanel_model", false),
    AER_DOUBLE_SOLAR_PANEL(AER_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel1 : null, 0, null, null, "tile.EMT.solar2.doubleair.name", 4, 100, 10000, 100, false, "emt", "airdoublepanel_model", false),
    AER_TRIPLE_SOLAR_PANEL(AER_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel1 : null, 1, null, null, "tile.EMT.solar2.tripleair.name", 4, 1000, 10000, 1000, false, "emt", "airtriplepanel_model", false),
    EARTH_SOLAR_PANEL(null, Loader.isModLoaded("EMT") ? EMTIntegration.panel1 : null, 2, null, null, "tile.EMT.solar2.earth.name", 4, 10, 10000, 10, false, "emt", "earthpanel_model", false),
    EARTH_DOUBLE_SOLAR_PANEL(EARTH_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel1 : null, 3, null, null, "tile.EMT.solar2.doubleearth.name", 4, 100, 10000, 100, false, "emt", "earthdoublepanel_model", false),
    EARTH_TRIPLE_SOLAR_PANEL(EARTH_DOUBLE_SOLAR_PANEL, Loader.isModLoaded("EMT") ? EMTIntegration.panel1 : null, 4, null, null, "tile.EMT.solar2.tripleearth.name", 4, 1000, 10000, 1000, false, "emt", "earthtriplepanel_model", false),

    ADVANCED_SOLAR_PANEL_W(null, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel : null, 0, null, null, "tile.wirelessAdvancedPanelPersonal.name", 1, Config.advGenDay, Config.advStorage, Config.advOutput, false, "wireless", "asp_top_w", false),
    HYBRID_SOLAR_PANEL_W(ADVANCED_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel1 : null, 0, null, null, "tile.wirelessHybridPanelPersonal.name", 2, Config.hGenDay, Config.hStorage, Config.hOutput, false, "wireless", "hsp_top_w", false),
    PERFECT_SOLAR_PANEL_W(HYBRID_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel2 : null, 0, null, null, "tile.wirelessUltimatePanelPersonal.name", 3, Config.uhGenDay, Config.uhStorage, Config.uhOutput, false, "wireless", "usp_top_w", false),
    QUANTUM_SOLAR_PANEL_W(PERFECT_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel3 : null, 0, null, null, "tile.wirelessQuantumPanelPersonal.name", 4, Config.qpGenDay, Config.qpStorage, Config.qpOutput, false, "wireless", "qsp_top_w", false),
    SPECTRAL_SOLAR_PANEL_W(QUANTUM_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel4 : null, 0, null, null, "tile.wirelessSpectralPanelPersonal.name", 5, Config.spectralpanelGenDay, Config.spectralpanelstorage, Config.spectralpanelOutput, false, "wireless", "spsp_top_w", false),
    PROTON_SOLAR_PANEL_W(SPECTRAL_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel5 : null, 0, null, null, "tile.wirelessProtonPanelPersonal.name", 6, Config.protongenDay, Config.protonstorage, Config.protonOutput, false, "wireless", "psp_top_w", false),
    SINGULAR_SOLAR_PANEL_W(PROTON_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel6 : null, 0, null, null, "tile.wirelessSingularPanelPersonal.name", 7, Config.singularpanelGenDay, Config.singularpanelstorage, Config.singularpanelOutput, false, "wireless", "ssp_top_w", false),
    DIFFRACTION_SOLAR_PANEL_W(SINGULAR_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel7 : null, 0, null, null, "tile.wirelessAbsorbtionPanelPersonal.name", 8, Config.adminpanelGenDay, Config.AdminpanelStorage, Config.AdminpanelOutput, false, "wireless", "admsp_model_w", false),
    PHOTONIC_SOLAR_PANEL_W(DIFFRACTION_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel8 : null, 0, null, null, "tile.wirelessPhotonicPanelPersonal.name", 9, Config.photonicpanelGenDay, Config.photonicpanelStorage, Config.photonicpanelOutput, false, "wireless_w", "phsp_top_w", false),
    NEUTRONIUN_SOLAR_PANEL_W(PHOTONIC_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel9 : null, 0, null, null, "tile.wirelessNeutronSP.name", 10, Config.neutronpanelGenDay, Config.neutronpanelStorage, Config.neutronpanelOutput, true, "wireless", "nsp_top_w", false),
    BARION_SOLAR_PANEL_W(NEUTRONIUN_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel10 : null, 0, null, null, "tile.wirelessBarionPanelPersonal.name", 11, Config.barGenDay, Config.barStorage, Config.barOutput, false, "wireless", "bsp_top_w", false),
    HADRON_SOLAR_PANEL_W(BARION_SOLAR_PANEL_W, Loader.isModLoaded("wirelessindustry") ? WirelessIntegration.panel11 : null, 0, null, null, "tile.wirelessAdronPanelPersonal.name", 12, Config.adrGenDay, Config.adrStorage, Config.adrOutput, false, "wireless", "adsp_top_w", false),

    ;

    public final Class<? extends TileEntity> clazz;
    public final String name;
    public final int tier;
    public final double genday;
    public final double gennight;
    public final double maxstorage;
    public final double producing;
    public final String name1;
    public final boolean register;
    public final Block block;
    public final int meta;
    public final String type;
    public final String texturesmodels;
    public final EnumSolarPanels solarold;
    public final boolean rendertype;

    EnumSolarPanels(EnumSolarPanels solarold, Block block, int meta, Class<? extends TileEntity> clazz, String name, String name1, int tier, double genday, double maxstorage, double producing, boolean register, String type, String texturesmodels, boolean rendertype) {
        this.clazz = clazz;
        this.name = name;
        this.name1 = name1;
        this.tier = tier;
        this.genday = genday;
        this.gennight = genday / 2;
        this.maxstorage = maxstorage;
        this.producing = producing;
        this.register = register;
        this.solarold = solarold;
        this.block = block;
        this.meta = meta;
        this.type = type;
        this.texturesmodels = texturesmodels;
        this.rendertype = rendertype;
    }

    public static void registerTile() {
        for (EnumSolarPanels machine : EnumSolarPanels.values()) {
            if (machine.register)
                GameRegistry.registerTileEntity(machine.clazz, machine.name);
            if (machine.type.equals("default"))
                IUItem.map.put(machine.meta, machine);
            IUItem.map2.put(machine.name1, machine);
        }
    }

}
