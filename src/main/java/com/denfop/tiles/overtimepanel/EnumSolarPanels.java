package com.denfop.tiles.overtimepanel;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.integration.avaritia.AvaritiaIntegration;
import com.denfop.integration.avaritia.TileEntityInfinitySolarPanel;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.integration.botania.TileEntityElementumSolarPanel;
import com.denfop.integration.botania.TileEntityManasteelSolarPanel;
import com.denfop.integration.botania.TileEntityTerrasteelSolarPanel;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.integration.de.TileEntityAwakenedSolarPanel;
import com.denfop.integration.de.TileEntityChaoticSolarPanel;
import com.denfop.integration.de.TileEntityDraconianSolarPanel;
import com.denfop.integration.thaumcraft.ThaumcraftIntegration;
import com.denfop.integration.thaumcraft.TileEntityThaumSolarPanel;
import com.denfop.integration.thaumcraft.TileEntityVoidSolarPanel;
import com.denfop.integration.thaumtinker.ThaumTinkerIntegration;
import com.denfop.integration.thaumtinker.TileEntityIhorSolarPanel;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public enum EnumSolarPanels {

      ADVANCED_SOLAR_PANEL(null,IUItem.blockpanel,0, TileEntityAdvancedSolarPanel.class,"Advanced Solar Panel","blockAdvancedSolarPanel.name",1, Config.advGenDay,Config.advGenNight,Config.advStorage,Config.advOutput,true,"default","asp_top"),
      HYBRID_SOLAR_PANEL(ADVANCED_SOLAR_PANEL,IUItem.blockpanel,1, TileEntityHybridSolarPanel.class,"Hybrid Solar Panel", "blockHybridSolarPanel.name",2,Config.hGenDay,Config.hGenNight,Config.hStorage,Config.hOutput,true,"default","hsp_top"),
      PERFECT_SOLAR_PANEL(HYBRID_SOLAR_PANEL,IUItem.blockpanel,2,TileEntityUltimateSolarPanel.class,"Perfect Solar Panel", "blockUltimateSolarPanel.name",3,Config.uhGenDay,Config.uhGenNight,Config.uhStorage,Config.uhOutput,true,"default","usp_top"),
      QUANTUM_SOLAR_PANEL(PERFECT_SOLAR_PANEL,IUItem.blockpanel,3,TileEntityQuantumSolarPanel.class,"Quantum Solar Panel", "blockQuantumSolarPanel.name",4,Config.qpGenDay,Config.qpGenNight,Config.qpStorage,Config.qpOutput,true,"default","qsp_top"),
      SPECTRAL_SOLAR_PANEL(QUANTUM_SOLAR_PANEL,IUItem.blockpanel,4,TileEntitySpectralSolarPanel.class,"Spectral Solar Panel","blockSpectralSolarPanel.name",5, Config.spectralpanelGenDay, Config.spectralpanelGenNight, Config.spectralpanelstorage, Config.spectralpanelOutput,true,"default","spsp_top"),
      PROTON_SOLAR_PANEL(SPECTRAL_SOLAR_PANEL,IUItem.blockpanel,5,TileEntityProtonSolarPanel.class,"Proton Solar Panel","blockProtonSolarPanel.name",6, Config.protongenDay, Config.protongennitht, Config.protonstorage, Config.protonOutput,true,"default","psp_top"),
      SINGULAR_SOLAR_PANEL(PROTON_SOLAR_PANEL,IUItem.blockpanel,6,TileEntitySingularSolarPanel.class,"Singular Solar Panel","blockSingularSolarPanel.name", 7, Config.singularpanelGenDay, Config.singularpanelGenNight, Config.singularpanelstorage, Config.singularpanelOutput,true,"default","ssp_top"),
      DIFFRACTION_SOLAR_PANEL(SINGULAR_SOLAR_PANEL,IUItem.blockpanel,7,TileEntityDiffractionSolarPanel.class,"Diffraction Solar Panel","blockAdminSolarPanel.name", 8, Config.adminpanelGenDay, Config.adminpanelGenNight, Config.AdminpanelStorage, Config.AdminpanelOutput,true,"default","admsp_model"),
      PHOTONIC_SOLAR_PANEL(DIFFRACTION_SOLAR_PANEL,IUItem.blockpanel,8,TileEntityPhotonicSolarPanel.class,"Photon Solar Panel","blockPhotonicSolarPanel.name",Config.photonicpaneltier, Config.photonicpanelGenDay, Config.photonicpanelGenNight, Config.photonicpanelStorage, Config.photonicpanelOutput,true,"default","phsp_top"),
      NEUTRONIUN_SOLAR_PANEL(PHOTONIC_SOLAR_PANEL,IUItem.blockpanel,9,TileEntityNeutronSolarPanel.class,"Neutron Solar Panel","blockNeutronSolarPanel.name", 10, Config.neutronpanelGenDay, Config.neutronpanelGenNight, Config.neutronpanelStorage, Config.neutronpanelOutput,true,"default","nsp_top"),
      BARION_SOLAR_PANEL(NEUTRONIUN_SOLAR_PANEL,IUItem.blockpanel,10,TileEntityBarionSolarPanel.class,"Barion Solar Panel","blockBarionSolarPanel.name", 11, Config.barGenDay, Config.barGenNight,Config.barStorage, Config.barOutput,true,"default","bsp_top"),
      HADRON_SOLAR_PANEL(BARION_SOLAR_PANEL,IUItem.blockpanel,11,TileEntityHadronSolarPanel.class,"Hadron Solar Panel","blockAdronSolarPanel.name", 12, Config.adrGenDay, Config.adrGenNight,Config.adrStorage, Config.adrOutput,true,"default","adsp_top"),
      GRAVITON_SOLAR_PANEL(HADRON_SOLAR_PANEL,IUItem.blockpanel,12,TileEntityGravitonSolarPanel.class,"Graviton Solar Panel","blocGravitonSolarPanel.name", 13, Config.graGenDay, Config.graGenNight,Config.graStorage, Config.graOutput,true,"default","grasp_top"),
      KVARK_SOLAR_PANEL(GRAVITON_SOLAR_PANEL,IUItem.blockpanel,13,TileEntityKvarkSolarPanel.class,"Kvark Solar Panel","blockKvarkSolarPanel.name", 14, Config.kvrGenDay, Config.kvrGenNight,Config.kvrStorage, Config.kvrOutput,true,"default","kvsp_top"),
      NEUTRONIUM_SOLAR_PANEL_AVARITIA(PHOTONIC_SOLAR_PANEL,Config.AvaritiaLoaded && Config.Avaritia ? AvaritiaIntegration.blockAvSolarPanel : null,0,com.denfop.integration.avaritia.TileEntityNeutronSolarPanel.class,"Neutron Solar Panel Avaritia","blockNeutronSolarPanelAvaritia.name", Config.tier, Config.neutrongenday, Config.neutronGenNight, Config.neutronStorage, Config.neutronOutput, Config.AvaritiaLoaded && Config.Avaritia,"avaritia","neutronium_top" ),
      INFINITY_SOLAR_PANEL(NEUTRONIUM_SOLAR_PANEL_AVARITIA, Config.AvaritiaLoaded && Config.Avaritia ? AvaritiaIntegration.blockAvSolarPanel : null,1,TileEntityInfinitySolarPanel.class,"Infinity Solar Panels","blockInfinitySolarPanel.name", 12, Config.InfinityGenDay, Config.InfinityGenNight,Config.InfinityStorage, Config.InfinityOutput, Config.AvaritiaLoaded && Config.Avaritia,"avaritia" ,"infinity_top" ),
      MANASTEEL_SOLAR_PANEL(ADVANCED_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel,0,TileEntityManasteelSolarPanel.class,"Manasteel Solar Panel","blockManasteelSolarPanel.name", Config.manasteeltier, Config.manasteelgenday,  Config.manasteelgennight, Config.manasteelstorage, Config.manasteeloutput,Config.BotaniaLoaded && Config.Botania,"botania","manasteel_top" ),
      ELEMENTUM_SOLAR_PANEL(MANASTEEL_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel,1,TileEntityElementumSolarPanel.class,"Elementum Solar Panel","blockElementumSolarPanel.name", Config.elementiumtier, Config.elementiumgenday, Config.elementiumgennight, Config.elementiumstorage, Config.elementiumoutput,Config.BotaniaLoaded && Config.Botania,"botania","elementium_top"),
      TERRASTEEL_SOLAR_PANEL(ELEMENTUM_SOLAR_PANEL, BotaniaIntegration.blockBotSolarPanel,2,TileEntityTerrasteelSolarPanel.class,"Terrasteel Solar Panel","blockTerrasteelSolarPanel.name", Config.terasteeltier, Config.terasteelgenday, Config.terasteelgennight, Config.terasteelstorage, Config.terasteeloutput,Config.BotaniaLoaded && Config.Botania,"botania","terasteel_top"),
      DRACONIC_SOLAR_PANEL(HYBRID_SOLAR_PANEL,Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null,0,TileEntityDraconianSolarPanel.class,"Draconian Solar Panel","blockDraconSolarPanel.name", Config.draconictier, Config.draconicgenday, Config.draconicgennight,Config.draconicstorage,Config.draconicoutput,  Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic,"draconic","draconium_top"),
      AWAKENED_SOLAR_PANEL(DRACONIC_SOLAR_PANEL, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null,1,TileEntityAwakenedSolarPanel.class,"Awakened Solar Panel","blockAwakenedSolarPanel.name", Config.awakenedtier, Config.awakenedgenday, Config.awakenedgennight, Config.awakenedstorage, Config.awakenedoutput,  Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic,"draconic","awakened_top"),
      CHAOTIC_SOLAR_PANEL(AWAKENED_SOLAR_PANEL, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic ? DraconicIntegration.blockDESolarPanel : null,2,TileEntityChaoticSolarPanel.class, "Chaotic Solar Panel","blockChaosSolarPanel.name", Config.chaostier, Config.chaosgenday, Config.chaosgennight, Config.chaosstorage, Config.chaosoutput,Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic,"draconic","chaotic_top"),
      THAUM_SOLAR_PANEL(HYBRID_SOLAR_PANEL, ThaumcraftIntegration.blockThaumSolarPanel,0, TileEntityThaumSolarPanel.class,"Thaum Solar Panel","blockThaumSolarPanel.name",Config.thaumtier,Config.thaumgenday,Config.thaumgennight,Config.thaumstorage,Config.thaumoutput,Config.thaumcraft &&Config.Thaumcraft ,"thaumcraft","thaum_model"),
       VOID_SOLAR_PANEL(THAUM_SOLAR_PANEL, ThaumcraftIntegration.blockThaumSolarPanel,1, TileEntityVoidSolarPanel.class,"Void Solar Panel","blockVoidSolarPanel.name",Config.voidtier,Config.voidgenday,Config.voidgennight,Config.voidstorage,Config.voidoutput,Config.thaumcraft && Config.Thaumcraft,"thaumcraft","void_model"),
    IHOR_SOLAR_PANEL(VOID_SOLAR_PANEL, ThaumTinkerIntegration.blockThaumTinkerSolarPanel,0, TileEntityIhorSolarPanel.class,"Ihor Solar Panel","blockIhorSolarPanel.name",Config.ihortier,Config.ihorgenday,Config.ihorgennight,Config.ihorstorage,Config.ihoroutput,Config.thaumcraft && Config.Thaumcraft && Loader.isModLoaded("ThaumicTinkerer"),"thaumcraft","ichor_model")
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
    EnumSolarPanels(EnumSolarPanels solarold,Block block, int meta, Class<? extends TileEntity> clazz, String name, String name1, int tier, double genday, double gennight, double maxstorage, double producing, boolean register,String type,String texturesmodels) {
        this.clazz = clazz;
        this.name = name;
        this.name1 = name1;
        this.tier = tier;
        this.genday = genday;
        this.gennight = gennight;
        this.maxstorage = maxstorage;
        this.producing= producing;
        this.register = register;
        this.solarold =solarold;
        this.block = block;
        this.meta= meta;
        this.type=type;
        this.texturesmodels=texturesmodels;
    }

    public static void registerTile() {
        for (EnumSolarPanels machine : EnumSolarPanels.values()) {
            if(machine.register)
            GameRegistry.registerTileEntity(machine.clazz, machine.name);
            if(machine.type.equals("default"))
            IUItem.map.put(machine.meta,machine);
            IUItem.map2.put(machine.name1,machine);
        }
    }

}
