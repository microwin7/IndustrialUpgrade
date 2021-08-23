package com.denfop;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

	
	public static double SolidMatterStorage;
	public static boolean EnableNetherOres;
	public static int limit;
	public static int tier;
	public static int coefficientrf;
	public static double neutrongenday;
	public static double neutronGenNight;
	public static double neutronStorage;
	public static double neutronOutput;
	public static double InfinityGenNight;
	public static double InfinityGenDay;
	public static double InfinityOutput;
	public static double InfinityStorage;
	public static int manasteeltier;
	public static double manasteeloutput;
	public static double manasteelstorage;
	public static double manasteelgennight;
	public static double manasteelgenday;
	public static double elementiumstorage;
	public static double elementiumgenday;
	public static double elementiumgennight;
	public static double elementiumoutput;
	public static int elementiumtier;
	public static double terasteelgenday;
	public static double terasteelgennight;
	public static double terasteelstorage;
	public static double terasteeloutput;
	public static int terasteeltier;
	public static double chaosgenday;
	public static double chaosgennight;
	public static double chaosstorage;
	public static double chaosoutput;
	public static int chaostier;
	//
	public static double thaumgenday;
	public static double thaumgennight;
	public static double thaumstorage;
	public static double thaumoutput;
	public static int thaumtier;
	public static double voidgenday;
	public static double voidgennight;
	public static double voidstorage;
	public static double voidoutput;
	public static int voidtier;
	public static double ihorgenday;
	public static double ihorgennight;
	public static double ihorstorage;
	public static double ihoroutput;
	public static int ihortier;
	//
	public static int awakenedtier;
	public static double awakenedoutput;
	public static double awakenedstorage;
	public static double awakenedgennight;
	public static double awakenedgenday;
	public static int draconictier;
	public static double draconicoutput;
	public static double draconicstorage;
	public static double draconicgennight;
	public static double draconicgenday;
	public static int toriyRodCells;
	public static int toriyRodHeat;
	public static float toriyPower;
	public static File configFile;
	public static boolean registerDraconicPanels;
	public static boolean registerChaosArmour;
	public static boolean registerChaosFluxCapacitor;
	public static boolean registerChaosAxe;
	public static boolean registerChaosBow;
	public static boolean registerChaosSword;
	public static boolean registerChaosShovel;
	public static boolean registerChaosPickaxe;
	public static boolean registerChaosDestructionStaff;
	public static boolean nightvision;
	public static float effPower;
	public static float bigHolePower;
	public static int spectralsaberactive;
	public static int spectralsabernotactive;
	public static int Storagequantumsuit;
	public static double neutronpanelGenDay;
	public static double neutronpanelOutput;
	public static double neutronpanelGenNight;
	public static double neutronpanelStorage;
	public static int Radius3;
	public static int durability3;
	public static int efficiency3;
	public static int minWindStrength3;
	public static int maxWindStrength3;
	public static int Radius4;
	public static int durability4;
	public static int efficiency4;
	public static int minWindStrength4;
	public static int maxWindStrength4;
	public static int Radius5;
	public static int durability5;
	public static int efficiency5;
	public static int maxWindStrength5;
	public static int minWindStrength5;
	public static double protonOutput;
	public static double protonstorage;
	public static double protongenDay;
	public static double protongennitht;
	public static int adv_enegry;
	public static int adv_storage;
	public static int tier1;
	public static double energy;
	public static int ult_enegry;
	public static int ult_storage;
	public static float lowPower;
	public static float ultraLowPower;
	public static int ultdrillmaxCharge;
	public static int ultdrilltier;
	public static int energyPerOperation;
	public static int energyPerLowOperation;
	public static int energyPerultraLowPowerOperation;
	public static int energyPerbigHolePowerOperation;
	public static int ultdrilltransferLimit;
	public static boolean enableefficiency;
	public static int efficiencylevel;
	public static double advGenDay;
	public static boolean AvaritiaLoaded;
	public static boolean BotaniaLoaded;
	public static boolean Draconic;
	public static boolean Botania;
	public static boolean Avaritia;
	public static int efficiencylevel1;
	public static boolean enablesilkTouch;
	public static boolean enablefortune;
	public static int fortunelevel;
	public static boolean enableexlposion;
	public static boolean enableIC2EasyMode;
	public static boolean EnableEndOres;
	public static boolean EnableToriyOre;
	public static boolean damagecable;
	public static boolean newsystem;
	public static int armor_maxcharge;
	public static int armor_transferlimit;
	public static int armor_maxcharge_body;
	public static int percent_output;
	public static int percent_storage;
	public static int percent_day;
	public static int percent_night;
	public static double spectralpanelGenDay;
	public static double spectralpanelGenNight;
	public static double singularpanelGenDay;
	public static double singularpanelOutput;
	public static double spectralpanelOutput;
	public static double singularpanelGenNight;
	public static double adminpanelGenDay;
	public static double adminpanelGenNight;
	public static double AdminpanelStorage;
	public static double AdminpanelOutput;
	public static double photonicpanelGenDay;
	public static double photonicpanelGenNight;
	public static double photonicpanelOutput;
	public static double photonicpanelStorage;
	public static int tier_advmfsu;
	public static int tier_ultmfsu;
	public static double singularpanelstorage;
	public static int photonicpaneltier;
	public static boolean thaumcraft;
	public static double advGenNight;
	public static double advStorage;
	public static double advOutput;
	public static double hGenDay;
	public static double hGenNight;
	public static double hStorage;
	public static double hOutput;
	public static double uhGenDay;
	public static double uhGenNight;
	public static double uhStorage;
	public static double uhOutput;
	public static double qpGenDay;
	public static double qpGenNight;
	public static double qpStorage;
	public static double qpOutput;
	public static double spectralpanelstorage;
	public static int maxCharge;
	public static int transferLimit;
	public static boolean EnchantingPlus;
	public static boolean MineFactory;
	public static boolean EnableMineFactory;
	public static int Radius;
	public static int durability;
	public static int efficiency;
	public static int minWindStrength;
	public static int maxWindStrength;
	public static int Radius1;
	public static int durability1;
	public static int efficiency1;
	public static int minWindStrength1;
	public static int maxWindStrength1;
	public static int Radius2;
	public static int durability2;
	public static int efficiency2;
	public static int minWindStrength2;
	public static int maxWindStrength2;
	public static boolean Streak;
	public static int TerrasteelRodCells;
	public static int TerrasteelRodHeat;
	public static float TerrasteelPower;
	public static int ProtonRodHeat;
	public static int ProtonRodCells;
	public static float ProtonPower;
	public static boolean DraconicLoaded;
	public static int spectralsaberactive1;
	public static int spectralsabernotactive1;
	public static int maxCharge1;
	public static int transferLimit1;

	public static double molecular;
	public static double molecular1;
	public static double molecular2;
	public static double molecular3;
	public static double molecular4;
	public static double molecular5;
	public static double molecular6;
	public static double molecular7;
	public static double molecular8;
	public static double molecular9;
	public static double molecular10;
	public static double molecular11;
	public static double molecular12;
	public static double molecular13;
	public static double molecular14;
	public static double molecular15;
	public static double molecular16;
	public static double molecular17;
	public static double molecular18;
	public static double molecular19;
	public static double molecular20;
	public static double molecular21;
	public static double molecular22;
	public static double molecular23;
	public static double molecular24;
	public static double molecular25;
	public static double molecular26;
	public static double molecular27;
	public static double molecular28;
	public static double molecular29;
	public static double molecular30;
	public static double molecular31;
	public static double molecular32;
	public static double molecular33;
	public static double molecular34;
	public static double molecular35;
	public static int enerycost;
	public static int expstorage;

	public static double molecular36;
	public static int energyPerultraLowPowerOperation1;
	public static int ultraLowPower1;
	public static boolean promt;
	public static double tierPerMFSU;
	public static double PerMFSUOutput;
	public static double PerMFSUStorage;
	public static double tierBarMFSU;
	public static double BarMFSUOutput;
	public static double BarMFSUStorage;
	public static boolean disableUpdateCheck;
	public static double adrGenDay;
	public static double adrGenNight;
	public static double adrOutput;
	public static double adrStorage;
	public static double barGenDay;
	public static double barGenNight;
	public static double barOutput;
	public static double barStorage;
	public static double HadrMFSUStorage;
	public static double HadrMFSUOutput;
	public static double tierHadrMFSU;
	public static double GraMFSUStorage;
	public static double GraMFSUOutput;
	public static double tierGraMFSU;
	
	public static double KrvMFSUStorage;
	public static double KrvMFSUOutput;
	public static double tierKrvMFSU;

	public static int transfer_nano_bow;

	public static int transfer_quantum_bow;

	public static int transfer_spectral_bow;

	public static int tier_nano_bow;

	public static int tier_quantum_bow;

	public static int tier_spectral_bow;

	public static int maxenergy_nano_bow;

	public static int maxenergy_quantum_bow;

	public static int maxenergy_spectral_bow;
    public static int americiumRodCells;
	public static int americiumRodHeat;
	public static double americiumPower;
	public static int neptuniumRodCells;
	public static double neptuniumPower;
	public static int neptuniumRodHeat;
	public static int curiumRodCells;
	public static int curiumRodHeat;
	public static double curiumPower;
	public static int californiaRodCells;
	public static int californiaRodHeat;
	public static double californiaPower;
	public static double molecular37;
	public static double molecular38;
	public static double molecular39;
	public static double molecular40;
	public static double molecular41;
	public static double graGenDay;
	public static double graGenNight;
	public static double graOutput;
	public static double graStorage;
	public static double kvrGenDay;
	public static double kvrGenNight;
	public static double kvrOutput;
	public static double kvrStorage;
	public static boolean amplifierSlot;
	public static boolean spectralquantumprotection;
    public static boolean explode;
    public static int mendeleviumRodCells;
	public static int mendeleviumRodHeat;
	public static double mendeleviumPower;
	public static int berkeliumRodCells;
	public static int berkeliumRodHeat;
	public static double berkeliumPower;
	public static int einsteiniumRodCells;
	public static int einsteiniumRodHeat;
	public static double einsteiniumPower;
	public static int uran233RodCells;
	public static int uran233RodHeat;
	public static double uran233Power;
	public static boolean Thaumcraft;

	public static void config(final FMLPreInitializationEvent event) {
		configFile = event.getSuggestedConfigurationFile();
		 Configuration config = new Configuration(configFile);

			config.load();
			uran233RodCells=config.get("Uran233 Rod", "Cells", 5000).getInt(5000);
			uran233RodHeat=config.get("Uran233 Rod", "Heat", 1).getInt(1);
			uran233Power=config.get("Uran233 Rod", "Power", 3D).getDouble(3D);
			Config.Thaumcraft = Loader.isModLoaded("Thaumcraft");
		Config.DraconicLoaded = Loader.isModLoaded("DraconicEvolution");
		Config.AvaritiaLoaded = Loader.isModLoaded("Avaritia");
		Config.BotaniaLoaded = Loader.isModLoaded("Botania");
		Config.EnchantingPlus = Loader.isModLoaded("eplus");
		Config.MineFactory = Loader.isModLoaded("MineFactoryReloaded");



			einsteiniumRodCells=config.get("Einsteinium Rod", "Cells", 25000).getInt(25000);
			einsteiniumRodHeat=config.get("Einsteinium Rod", "Heat", 4).getInt(4);
			einsteiniumPower=config.get("Einsteinium Rod", "Power", 23D).getDouble(23D);


			berkeliumRodCells=config.get("Berkelium Rod", "Cells", 22500).getInt(22500);
			berkeliumRodHeat=config.get("Berkelium Rod", "Heat", 3).getInt(3);
			berkeliumPower=config.get("Berkelium Rod", "Power", 20D).getDouble(20D);


			mendeleviumRodCells=config.get("Mendelevium Rod", "Cells", 30000).getInt(30000);
			mendeleviumRodHeat=config.get("Mendelevium Rod", "Heat", 4).getInt(4);
			mendeleviumPower=config.get("Mendelevium Rod", "Power", 26D).getDouble(26D);


			californiaRodCells=config.get("California Rod", "Cells", 20000).getInt(20000);
			californiaRodHeat=config.get("California Rod", "Heat", 3).getInt(3);
			californiaPower=config.get("California Rod", "Power", 18D).getDouble(18D);


			curiumRodCells=config.get("Curium Rod", "Cells", 8000).getInt(8000);
			curiumRodHeat=config.get("Curium Rod", "Heat", 2).getInt(2);
			curiumPower=config.get("Curium Rod", "Power", 9.5D).getDouble(9.5D);


			neptuniumRodCells=config.get("Neptunium Rod", "Cells", 15000).getInt(15000);
			neptuniumRodHeat=config.get("Neptunium Rod", "Heat", 1).getInt(1);
			neptuniumPower=config.get("Neptunium Rod", "Power", 3.5D).getDouble(3.5D);


			americiumRodCells=config.get("Americium Rod", "Cells", 5000).getInt(5000);
			americiumRodHeat=config.get("Americium Rod", "Heat", 1).getInt(1);
			americiumPower=config.get("Americium Rod", "Power", 4.5D).getDouble(4.5D);

			amplifierSlot=config.get("MultiMatter", "amplifierSlot", true).getBoolean(true);
			spectralquantumprotection=config.get("Spectral armor", "protection", true).getBoolean(true);
			explode=config.get("Reactor", "explode", false).getBoolean(false);


			transfer_nano_bow=config.get("Nano Bow", "Transfer energy", 5000).getInt(5000);
			transfer_quantum_bow=config.get("Quantum Bow", "Transfer energy", 25000).getInt(25000);
			transfer_spectral_bow=config.get("Spectral Bow", "Transfer energy", 50000).getInt(50000);
			tier_spectral_bow=config.get("Spectral Bow", "Tier", 4).getInt(4);
			tier_quantum_bow=config.get("Quantum Bow", "Tier", 3).getInt(3);
			tier_nano_bow=config.get("Nano Bow", "Tier", 2).getInt(2);
			maxenergy_nano_bow=config.get("Nano Bow", "MaxEnergy", 50000).getInt(50000);
			maxenergy_quantum_bow=config.get("Quantum Bow", "MaxEnergy", 80000).getInt(80000);
			maxenergy_spectral_bow=config.get("Spectral Bow", "MaxEnergy", 150000).getInt(150000);
			
			KrvMFSUStorage=config.get("Glucun Quark MFSU", "storage", 409.6E9D).getDouble(409.6E9D);
			KrvMFSUOutput=config.get("Glucun Quark MFSU", "output", 247955456).getDouble(247955456);
			tierKrvMFSU=config.get("Glucun Quark MFSU", "tier", 11).getDouble(11);
		
			GraMFSUStorage=config.get("Gravitational MFSU", "storage", 102.4E9D).getDouble(102.4E9D);
			GraMFSUOutput=config.get("Gravitational MFSU", "output", 61988864).getDouble(61988864);
			tierGraMFSU=config.get("Gravitational MFSU", "tier", 10).getDouble(10);
		
			HadrMFSUStorage =config.get("Adron MFSU", "storage", 25.6E9D).getDouble( 25.6E9D);
			HadrMFSUOutput =config.get("Adron MFSU", "output", 15497216).getDouble(15497216);
			tierHadrMFSU =config.get("Adron MFSU", "tier", 9).getDouble(9);



			barGenDay = config.get("Barion Solar panel", "BarionGenDay", 5302880).getDouble(5302880);
			barGenNight = config.get("Barion Solar panel", "BarionGenNight", 5302880).getDouble(5302880);
			barOutput = config.get("Barion Solar panel", "BarionOutput", 10605760).getDouble(10605760);
			barStorage = config.get("Barion Solar panel", "BarionStorage", 10000000000D).getDouble(10000000000D);
		
			adrGenDay = config.get("Hadrion Solar panel", "HadrionGenDay", 21211520).getDouble(21211520);
			adrGenNight = config.get("Hadrion Solar panel", "HadrionGenNight", 21211520).getDouble(21211520);
			adrOutput = config.get("Hadrion Solar panel", "HadrionOutput", 42423040).getDouble(42423040);
			adrStorage = config.get("Hadrion Solar panel", "HadrionStorage", 2500000000D).getDouble(2500000000D);

			graGenDay = config.get("Graviton Solar panel", "GravitonGenDay", 84846080).getDouble(84846080);
			graGenNight = config.get("Graviton Solar panel", "GravitonGenNight", 84846080).getDouble(84846080);
			graOutput = config.get("Graviton Solar panel", "GravitonOutput", 169692160).getDouble(169692160);
			graStorage = config.get("Graviton Solar panel", "GravitonStorage", 25000000000D).getDouble(25000000000D);

			kvrGenDay = config.get("Kvark Solar panel", "KvarkGenDay", 339384320).getDouble(339384320);
			kvrGenNight = config.get("Kvark Solar panel", "KvarkGenNight", 339384320).getDouble(339384320);
			kvrOutput = config.get("Kvark Solar panel", "KvarkOutput", 678768640).getDouble(678768640);
			kvrStorage = config.get("Kvark Solar panel", "KvarkStorage", 2500000000000D).getDouble(2500000000000D);





			BarMFSUStorage=config.get("Barion MFSU", "storage", 6.4E9D).getDouble(6.4E9D);
			BarMFSUOutput=config.get("Barion MFSU", "output", 3874304).getDouble(3874304);
			tierBarMFSU=config.get("Barion MFSU", "tier", 8).getDouble(8);
			PerMFSUStorage=config.get("Perfect MFSU", "storage", 1.6E9D).getDouble(1.6E9D);
			PerMFSUOutput=config.get("Perfect MFSU", "output", 968576).getDouble(968576);
			tierPerMFSU= config.get("Perfect MFSU", "tier", 7).getDouble(7);
			expstorage = config.get("Basic Mechanisms", "exp storage", 500).getInt(500);
			enerycost = config.get("Quantum Querry", "energy consume", 25000).getInt(25000);
			coefficientrf=config.get("general", "coefficient rf", 4).getInt(4);
			if(coefficientrf < 1)
				coefficientrf =4;
			molecular = config.get("Crafts Molecular Transformer", "Wither Skeleton skull", 4000000D).getDouble(4000000D);
			molecular1 = config.get("Crafts Molecular Transformer", "Nether Star", 250000000D).getDouble(250000000D);
			molecular2 = config.get("Crafts Molecular Transformer", "Iridium Ore", 10000000D).getDouble(10000000D);
			molecular3 = config.get("Crafts Molecular Transformer", "Proton", 15500000D).getDouble(15500000D);
			molecular4 = config.get("Crafts Molecular Transformer", "Iridium ingot", 2500000D).getDouble(2500000D);
			molecular5 = config.get("Crafts Molecular Transformer", "Photon ingot", 12000000D).getDouble(12000000D);
			molecular6 = config.get("Crafts Molecular Transformer", "Gunpowder", 70000D).getDouble(70000D);
			molecular7 = config.get("Crafts Molecular Transformer", "Gravel", 45000D).getDouble(45000D);
			molecular8 = config.get("Crafts Molecular Transformer", "Diamond", 1000000D).getDouble(1000000D);
			molecular9 = config.get("Crafts Molecular Transformer", "Nickel ingot", 450000D).getDouble(450000D);
			molecular10 = config.get("Crafts Molecular Transformer", "Gold ingot", 450000D).getDouble(450000D);
			molecular11 = config.get("Crafts Molecular Transformer", "Silver ingot", 800000D).getDouble(800000D);
			molecular12 = config.get("Crafts Molecular Transformer", "Tungsten ingot", 700000D).getDouble(700000D);
			molecular13 = config.get("Crafts Molecular Transformer", "Spinel ingot", 800000D).getDouble(800000D);
			molecular14 = config.get("Crafts Molecular Transformer", "Mikhalov ingot", 900000D).getDouble(900000D);
			molecular15 = config.get("Crafts Molecular Transformer", "Chromium ingot", 600000D).getDouble(600000D);
			molecular16 = config.get("Crafts Molecular Transformer", "Platium ingot", 800000D).getDouble(800000D);
			molecular17 = config.get("Crafts Molecular Transformer", "Advanced ingot", 1500D).getDouble(1500D);
			molecular18 = config.get("Crafts Molecular Transformer", "Hybrid core", 11720D).getDouble(11720D);
			molecular19 = config.get("Crafts Molecular Transformer", "Ultimate core", 60000D).getDouble(60000D);
			molecular20 = config.get("Crafts Molecular Transformer", "Quantum core", 300000D).getDouble(300000D);
			molecular21 = config.get("Crafts Molecular Transformer", "Spectral core", 1500000D).getDouble(1500000D);
			molecular22 = config.get("Crafts Molecular Transformer", "Proton core", 7500000D).getDouble(7500000D);
			molecular23 = config.get("Crafts Molecular Transformer", "Singular core", 45000000D).getDouble(45000000D);
			molecular24 = config.get("Crafts Molecular Transformer", "Diffraction core", 180000000D).getDouble(180000000D);
			molecular25 = config.get("Crafts Molecular Transformer", "Photnic core", 900000000D).getDouble(900000000D);
			molecular26 = config.get("Crafts Molecular Transformer", "Neutron Core", 2700000000D).getDouble(2700000000D);

			molecular38 = config.get("Crafts Molecular Transformer", "Barion Core", 4500000000D).getDouble(4500000000D);
			molecular39 = config.get("Crafts Molecular Transformer", "Hadron Core", 9000000000D).getDouble(9000000000D);
			molecular40 = config.get("Crafts Molecular Transformer", "Graviton Core", 12000000000D).getDouble(12000000000D);
			molecular41 = config.get("Crafts Molecular Transformer", "Kvark Core", 21000000000D).getDouble(21000000000D);

			molecular27 = config.get("Crafts Molecular Transformer", "Sun lense", 25000000D).getDouble(25000000D);
			molecular28 = config.get("Crafts Molecular Transformer", "Rain lense", 25000000D).getDouble(25000000D);
			molecular29 = config.get("Crafts Molecular Transformer", "Nether lense", 25000000D).getDouble(25000000D);
			molecular30 = config.get("Crafts Molecular Transformer", "Night lense", 25000000D).getDouble(25000000D);
			molecular31 = config.get("Crafts Molecular Transformer", "Earth lense", 25000000D).getDouble(25000000D);
			molecular32 = config.get("Crafts Molecular Transformer", "End lense", 25000000D).getDouble(25000000D);
			molecular33 = config.get("Crafts Molecular Transformer", "Aer lense", 25000000D).getDouble(25000000D);
			molecular34 = config.get("Crafts Molecular Transformer", "Photon", 1450000D).getDouble(1450000D);
			molecular35 = config.get("Crafts Molecular Transformer", "Magnesium ingot", 700000D).getDouble(700000D);
			molecular36 = config.get("Crafts Molecular Transformer", "Caravkiy ingot", 900000D).getDouble(900000D);
			molecular37= config.get("Crafts Molecular Transformer", "Iridium ingot(from Iridium Ore Ic2)", 50000).getDouble(50000);

			disableUpdateCheck= config.get("general", "Disable Update Check ", false).getBoolean(false);
			promt = config.get("general", "Enable prompt about infomation a panel", true).getBoolean(true);

			SolidMatterStorage = config.get("Solid Matter Generator Storage", "Matter Generator Storage", 1E5D).getDouble(1E5D);
			damagecable = config.get("Events", "Damage", true).getBoolean(true);
			newsystem = config.get("Events", "New system transfer energy", true).getBoolean(true);
		EnableToriyOre = config.get("Generation", "Spawn Toriy Ore", true).getBoolean(true);
				effPower = config.get("UltimateDrill", "Mode 0 efficiency", 35).getInt(35);
			lowPower = config.get("UltimateDrill", "Mode 1 efficiency", 20).getInt(20);
			bigHolePower = config.get("UltimateDrill", "Mode 2 efficiency", 16).getInt(16);
			ultraLowPower = config.get("UltimateDrill", "Mode 3 efficiency", 12).getInt(12);
			ultraLowPower1 = config.get("UltimateDrill", "Mode 4 efficiency", 10).getInt(10);
			ultdrillmaxCharge = config.get("UltimateDrill", "maxCharge", 15000000).getInt(15000000);
			ultdrilltier = config.get("UltimateDrill", "tier", 2).getInt(2);
			ultdrilltransferLimit = config.get("UltimateDrill", "transfer Limit", 500).getInt(500);
			energyPerOperation = config.get("UltimateDrill", "energyPerOperation", 160).getInt(160);
			energyPerLowOperation = config.get("UltimateDrill", "energyPerLowOperation", 80).getInt(80);
			energyPerbigHolePowerOperation = config.get("UltimateDrill", "energyPerBigHolesOperation (3x3)", 300).getInt(300);
			energyPerultraLowPowerOperation = config.get("UltimateDrill", "energyPerUltraBigHolesOperation (5x5)", 500).getInt(500);
			energyPerultraLowPowerOperation1 = config.get("UltimateDrill", "energyPerUltraBigHolesOperation (7x7)", 700).getInt(700);
			enableefficiency = config.get("UltimateDrill", "Enable Efficiency tool mode 0", true).getBoolean(true);
			if (efficiencylevel > 1 && efficiencylevel < 15) {
				efficiencylevel = config.get("UltimateDrill", "Level efficiency(tool mode 0)", 10).getInt(10);
			} else {
				efficiencylevel = 10;
			}
			EnableNetherOres = config.get("Generation", "Spawn ores in Nether", true).getBoolean(true);
			EnableEndOres = config.get("Generation", "Spawn ores in End", true).getBoolean(true);
			enableIC2EasyMode = config.get("general", "Easy Mode", false).getBoolean(false);
			enableexlposion = config.get("Events", "Enable exlposion", true).getBoolean(true);
			enableefficiency = config.get("UltimateDrill", "Enable Efficiency tool mode 1", true).getBoolean(true);
			if (efficiencylevel1 > 1 && efficiencylevel1 < 15) {
				efficiencylevel1 = config.get("UltimateDrill", "Level efficiency(tool mode 1)", 10).getInt(10);
			} else {
				efficiencylevel1 = 10;
			}
			enablesilkTouch = config.get("UltimateDrill", "Enable silk Touch tool mode 2", true).getBoolean(true);
			enablefortune = config.get("UltimateDrill", "Enable fortune tool mode 3", true).getBoolean(true);
			if (fortunelevel > 1 && fortunelevel < 10) {
				fortunelevel = config.get("UltimateDrill", "Level fortune(tool mode 3)", 5).getInt(5);
			} else {
				fortunelevel = 5;
			}
			protongenDay = config.get("Proton Solar panel", "ProtonGenDay", 5120).getDouble(5120);
			protongennitht = config.get("Proton Solar panel", "ProtonGenNight", 2560).getDouble(2560);
			protonOutput = config.get("Proton Solar panel", "ProtonOutput", 10240).getDouble(10240);
			protonstorage = config.get("Proton Solar panel", "Protonstorage", 50000000D).getDouble(5000000D);
			spectralpanelGenDay = config.get("Spectral Solar panel", "SpectralSPGenDay", 1280).getDouble(1280);
			spectralpanelGenNight = config.get("Spectral Solar panel", "SpectralSPGenNight", 640).getDouble(640);
			spectralpanelOutput = config.get("Spectral Solar panel", "SpectralSPOutput", 2560).getDouble(2560);
			spectralpanelstorage = config.get("Spectral Solar panel", "Spectral Solar panel Storage", 5000000D).getDouble(500000D);
			singularpanelGenDay = config.get("Singular Solar panel", "SingularSPGenDay", 20480).getDouble(20480);
			singularpanelGenNight = config.get("Singular Solar panel", "SingularSPGenNight", 10240).getDouble(10240);
			singularpanelOutput = config.get("Singular Solar panel", "SingularSPOutput", 40960).getDouble(40960);
			singularpanelstorage = config.get("Singular Solar panel", "SingularSPStorage", 1000000000D).getDouble(100000000D);
			adminpanelGenDay = config.get("Light-Adbord Solar panel", "AdminPanelGenDay", 81920).getDouble(81920);
			adminpanelGenNight = config.get("Light-Adbord Solar panel", "AdminPanelGenNight", 40960).getDouble(40960);
			AdminpanelStorage = config.get("Light-Adbord Solar panel", "AdminPanelStorage", 1500000000D).getDouble(1500000000D);
			AdminpanelOutput = config.get("Light-Adbord Solar panel", "AdminPanelOutput", 327680).getDouble(327680);
			photonicpanelGenDay = config.get("Photonic Solar panel", "PhotonicPanelGenDay", 327680).getDouble(327680);
			photonicpanelGenNight = config.get("Photonic Solar panel", "PhotonicPanelGenNight", 327680).getDouble(327680);
			photonicpanelOutput = config.get("Photonic Solar panel", "PhotonicPanelOutput", 655360).getDouble(655360);
			photonicpanelStorage = config.get("Photonic Solar panel", "PhotonicPanelStorage",  5000000000D).getDouble(5000000000D);
			neutronpanelGenDay = config.get("Neutronium Solar panel", "NeutronPanelGenDay", 1325720).getDouble(1325720);
			neutronpanelGenNight = config.get("Neutronium Solar panel", "NeutronPanelGenNight", 1325720).getDouble(1325720);
			neutronpanelOutput = config.get("Neutronium Solar panel", "NeutronPanelOutput", 2651440).getDouble(2651440);
			neutronpanelStorage = config.get("Neutronium Solar panel", "NeutronPanelStorage", 6500000000D).getDouble(6500000000D);
			spectralsaberactive1 = config.get("Quantum Saber", "QuantumSaber Damage Active", 40).getInt(40);
			spectralsabernotactive1 = config.get("Quantum Saber", "QuantumSaber Damage Not Active", 8).getInt(8);
			maxCharge1 = config.get("Quantum Saber", "SpectralSaber max Charge", 200000).getInt(200000);
			transferLimit1 = config.get("Quantum Saber", "SpectralSaber transfer Limit", 15000).getInt(20000);
			tier1 = config.get("Quantum Saber", "SpectralSaber tier", 4).getInt(4);
			energy = config.get("Neutronium generator", "Consumes energy to make 1 mb neutronim", 16250000.0D).getDouble(16250000.0D);
			maxCharge = config.get("Spectral Saber", "SpectralSaber max Charge", 600000).getInt(300000);
			transferLimit = config.get("Spectral Saber", "SpectralSaber transfer Limit", 40000).getInt(20000);
			spectralsaberactive = config.get("Spectral Saber", "SpectralSaber Damage Active", 60).getInt(60);
			spectralsabernotactive = config.get("Spectral Saber", "SpectralSaber Damage Not Active", 12).getInt(12);
			adv_enegry = config.get("Imroved MFSU", "energy transfer Et/t", 32768).getInt(32768);
			adv_storage = config.get("Imroved MFSU", "energy storage", 100000000).getInt(100000000);
			tier_advmfsu = config.get("Imroved MFSU", "tier", 5).getInt(5);

			tier_ultmfsu = config.get("Advanced MFSU", "tier", 6).getInt(6);
			ult_enegry = config.get("Advanced MFSU", "energy transfer Et/t", 242144).getInt(242144);
			ult_storage = config.get("Advanced MFSU", "energy storage", 400000000).getInt(400000000);
				photonicpaneltier = config.get("Photonic Solar panel", "tier", 9).getInt(9);
			InfinityGenDay = config.get("Infinity Solar panel", "GenDay", 21211520).getDouble(21211520);
			InfinityGenNight = config.get("Infinity Solar panel", "GenNight", 21211520).getDouble(21211520);
			InfinityOutput = config.get("Infinity Solar panel", "Output", 42423040).getDouble(42423040);
			InfinityStorage = config.get("Infinity Solar panel", "Storage", 2.5E10).getDouble(2.5E10);
			tier = config.get("Neutron Solar panel(Avaritia)", "tier", 10).getInt(10);
			neutrongenday = config.get("Neutron Solar panel(Avaritia)", "GenDay", 1325720).getDouble(1325720);
			neutronGenNight = config.get("Neutron Solar panel(Avaritia)", "GenNight", 1325720).getDouble(1325720);
			neutronOutput = config.get("Neutron Solar panel(Avaritia)", "Output", 2651440).getDouble(2651440);
			neutronStorage = config.get("Neutron Solar panel(Avaritia)", "Storage", 6.5E9).getDouble(6.5E9);
			percent_output = config.get("Modules", "percent output", 5).getInt(5);
			percent_storage = config.get("Modules", "percent storage", 5).getInt(5);
			percent_day = config.get("Modules", "percent generation day", 5).getInt(5);
			percent_night = config.get("Modules", "percent generation night", 5).getInt(5);
			Storagequantumsuit = config.get("Battery", "MaxEnergy", 100000000).getInt(100000000);
			registerDraconicPanels = config.get("Draconic Integration", "Register Draconic Panels", true).getBoolean(true);
			registerChaosArmour = config.get("Draconic Integration", "Register Chaos Armour", true).getBoolean(true);
			registerChaosFluxCapacitor = config.get("Draconic Integration", "Register Chaos Flux Capacitor", true).getBoolean(true);
			registerChaosDestructionStaff = config.get("Draconic Integration", "Register Chaos Destruction Staff", true).getBoolean(true);
			registerChaosPickaxe = config.get("Draconic Integration", "Register Chaos Pickaxe", true).getBoolean(true);
			registerChaosShovel = config.get("Draconic Integration", "Register Chaos Shovel", true).getBoolean(true);
			registerChaosSword = config.get("Draconic Integration", "Register Chaos Sword", true).getBoolean(true);
			registerChaosBow = config.get("Draconic Integration", "Register Chaos Bow", true).getBoolean(true);
			registerChaosAxe = config.get("Draconic Integration", "Register Chaos Axe", true).getBoolean(true);
			nightvision = config.get("Events","If player have quantum or nano or improvemed helmet and it was dressed and if player has coord y < 60 and skylight < 8,nightvision allow",true).getBoolean(true);
			thaumcraft = config.get("Integrastion", "Integrastion Thaumcraft", true).getBoolean(true);
			Draconic = config.get("Integrastion", "Integrastion Draconic Evolution", true).getBoolean(true);
			Botania = config.get("Integrastion", "Integrastion Botania", true).getBoolean(true);
			Avaritia = config.get("Integrastion", "Integrastion Avaritia", true).getBoolean(true);
			EnableMineFactory= config.get("Integrastion", "Integrastion MineFactoryReloaded", true).getBoolean(true);
			advGenDay = config.get("general", "AdvancedSPGenDay", 5).getDouble(5);
			advGenNight = config.get("general", "AdvancedSPGenNight", 5D).getDouble(5D);
			advStorage = config.get("general", "AdvancedSPStorage", 3200D).getDouble(3200D);
			advOutput = config.get("general", "AdvancedSPOutput", 10D).getDouble(10D);
			hGenDay = config.get("general", "HybrydSPGenDay", 20).getDouble(20);
			hGenNight = config.get("general", "HybrydSPGenNight", 10).getDouble(10);
			hStorage = config.get("general", "HybrydSPStorage", 20000D).getDouble(20000D);
			hOutput = config.get("general", "HybrydSPOutput", 40).getDouble(40);

			uhGenDay = config.get("general", "UltimateHSPGenDay", 80).getDouble(80);
			uhGenNight = config.get("general", "UltimateHSPGenNight", 40D).getDouble(40D);
			uhStorage = config.get("general", "UltimateHSPStorage", 200000D).getDouble(200000D);
			uhOutput = config.get("general", "UltimateHSPOutput", 160D).getDouble(160D);
			qpGenDay = config.get("general", "QuantumSPGenDay", 320).getDouble(320);
			qpGenNight = config.get("general", "QuantumSPGenNight", 160).getDouble(160);
			qpStorage = config.get("general", "QuantumSPStorage", 1000000D).getDouble(1000000D);
			qpOutput = config.get("general", "QuantumSPOutput", 640).getDouble(640);
			TerrasteelRodHeat = config.get("TerrasteelRod", "Heat", 1).getInt(1);
			TerrasteelRodCells = config.get("TerrasteelRod", "Cells", 20000).getInt(20000);
			TerrasteelPower = config.get("TerrasteelRod", "Power", 2).getInt(2);
			toriyRodHeat = config.get("ToriyRod", "Heat", 1).getInt(1);
			toriyRodCells = config.get("ToriyRod", "Cells", 10000).getInt(10000);
			toriyPower = config.get("ToriyRod", "Power", 3).getInt(3);
			ProtonRodHeat = config.get("ProtonRod", "Heat", 1).getInt(1);
			ProtonRodCells = config.get("ProtonRod", "Cells", 30000).getInt(30000);
			ProtonPower = config.get("ProtonRod", "Power", 6).getInt(6);
			Radius = config.get("Iridium rotor", "Radius", 11).getInt(11);
			durability = config.get("Iridium rotor", "durability", 648000).getInt(648000);
			efficiency = config.get("Iridium rotor", "efficiency", 2.0F).getInt(2);
			minWindStrength = config.get("Iridium rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength = config.get("Iridium rotor", "maxWindStrength", 110).getInt(110);
			Radius1 = config.get("Compress Iridium rotor", "Radius", 11).getInt(11);
			durability1 = config.get("Compress Iridium rotor", "durability", 720000).getInt(720000);
			efficiency1 = config.get("Compress Iridium rotor", "efficiency", 4.0F).getInt(4);

			minWindStrength1 = config.get("Compress Iridium rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength1 = config.get("Compress Iridium rotor", "maxWindStrength", 110).getInt(110);
			Radius2 = config.get("Spectral rotor", "Radius", 11).getInt(11);
			durability2 = config.get("Spectral rotor", "durability", 172800).getInt(172800);
			efficiency2 = config.get("Spectral rotor", "efficiency", 8.0F).getInt((int) 8.0F);
			minWindStrength2 = config.get("Spectral rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength2 = config.get("Spectral rotor", "maxWindStrength", 110).getInt(110);
			Streak = config.get("Quantum Armor", "Allow Streak", true).getBoolean(true);
			Radius5 = config.get("Myphical rotor", "Radius", 11).getInt(11);
			durability5 = config.get("Myphical rotor", "durability", 345600).getInt(345600);
			efficiency5 = config.get("Myphical rotor", "efficiency", 16.0F).getInt((int) 16.0F);
			minWindStrength5 = config.get("Myphical rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength5 = config.get("Myphical rotor", "maxWindStrength", 110).getInt(110);
			Radius4 = config.get("Neutron rotor", "Radius", 11).getInt(11);
			durability4 = config.get("Neutron rotor", "durability", 2764800).getInt(2764800);
			efficiency4 = config.get("Neutron rotor", "efficiency", 64).getInt( 64);
			minWindStrength4 = config.get("Neutron rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength4 = config.get("Neutron rotor", "maxWindStrength", 110).getInt(110);

			Radius3 = config.get("Photon rotor", "Radius", 11).getInt(11);
			durability3 = config.get("Photon rotor", "durability", 691200).getInt(691200);
			efficiency3 = config.get("Photon rotor", "efficiency", 32).getInt( 32);
			minWindStrength3 = config.get("Photon rotor", "minWindStrength", 25).getInt(25);
			maxWindStrength3 = config.get("Photon rotor", "maxWindStrength", 110).getInt(110);
			limit = config.get("Unifier panels", "Limit", 2).getInt(2);
			manasteelgenday = config.get("Manasteel Solar Panel", "genday", 80).getDouble(80);
			manasteelgennight = config.get("Manasteel Solar Panel", "gennight", 40D).getDouble(40D);
			manasteelstorage = config.get("Manasteel Solar Panel", "storage", 200000D).getDouble(200000D);
			manasteeloutput = config.get("Manasteel Solar Panel", "output", 160).getDouble(160);
			manasteeltier = config.get("Manasteel Solar Panel", "tier", 3).getInt(3);
			elementiumgenday = config.get("Elementium Solar Panel", "genday", 320).getDouble(320);
			elementiumgennight = config.get("Elementium Solar Panel", "gennight", 160D).getDouble(160D);
			elementiumstorage = config.get("Elementium Solar Panel", "storage", 1000000).getDouble(1000000);
			elementiumoutput = config.get("Elementium Solar Panel", "output", 160).getDouble(160);
			elementiumtier = config.get("Elementium Solar Panel", "tier", 4).getInt(4);
			terasteelgenday = config.get("Terasteel Solar Panel", "genday", 1280).getDouble(1280);
			terasteelgennight = config.get("Terasteel Solar Panel", "gennight", 640).getDouble(640);

			terasteelstorage = config.get("Terasteel Solar Panel", "storage", 5000000).getDouble(5000000);
			terasteeloutput = config.get("Terasteel Solar Panel", "output", 2560).getDouble(2560);
			terasteeltier = config.get("Terasteel Solar Panel", "tier", 5).getInt(5);
			draconicgenday = config.get("Draconic Solar Panel", "genday", 80D).getDouble(80D);
			draconicgennight = config.get("Draconic Solar Panel", "gennight", 40D).getDouble(40D);
			draconicstorage = config.get("Draconic Solar Panel", "storage", 50000D).getDouble(50000D);
			draconicoutput = config.get("Draconic Solar Panel", "output", 160).getDouble(160);
			draconictier = config.get("Draconic Solar Panel", "tier", 3).getInt(3);
			awakenedgenday = config.get("Awakaned Solar Panel", "genday", 20480).getDouble(20480);
			awakenedgennight = config.get("Awakaned Solar Panel", "gennight", 10240).getDouble(10240);
			awakenedstorage = config.get("Awakaned Solar Panel", "storage", 1.0E9).getDouble(1.0E9);
			awakenedoutput = config.get("Awakaned Solar Panel", "output", 40960).getDouble(40960);
			awakenedtier = config.get("Awakaned Solar Panel", "tier", 7).getInt(7);
			chaosgenday = config.get("Chaos Solar Panel", "genday", 1325720).getDouble(1325720);
			chaosgennight = config.get("Chaos Solar Panel", "gennight", 1325720).getDouble(1325720);
			chaosstorage = config.get("Chaos Solar Panel", "storage", 6.5E9).getDouble(6.5E9);
			chaosoutput = config.get("Chaos Solar Panel", "output", 2651440).getDouble(2651440);
			chaostier = config.get("Chaos Solar Panel", "tier", 10).getInt(10);
			armor_maxcharge = config.get("Improvemed Quantum Armor", "maxcharge exept Improvemed Quantum Body", 100000000).getInt(100000000);
			armor_transferlimit = config.get("Improvemed Quantum Armor", "transferlimit", 10000).getInt(10000);
			armor_maxcharge_body = config.get("Improvemed Quantum Armor", "maxcharge Improvemed Quantum Body", 300000000).getInt(300000000);
			//
			thaumgenday = config.get("Thaum Solar Panel", "genday", 80).getDouble(80);
			thaumgennight = config.get("Thaum Solar Panel", "gennight", 40).getDouble(40);
			thaumstorage = config.get("Thaum Solar Panel", "storage", 50000D).getDouble(50000D);
			thaumoutput = config.get("Thaum Solar Panel", "output", 160).getDouble(160);
			thaumtier = config.get("Thaum Solar Panel", "tier", 3).getInt(3);
			voidgenday = config.get("Thaum Solar Panel", "genday", 320).getDouble(320);
			voidgennight = config.get("Void Solar Panel", "gennight", 80).getDouble(80);
			voidstorage = config.get("Void Solar Panel", "storage", 200000D).getDouble(200000D);
			voidoutput = config.get("Void Solar Panel", "output", 640).getDouble(640);
			voidtier = config.get("Void Solar Panel", "tier", 4).getInt(4);
			ihorgenday = config.get("Ihor Solar Panel", "genday", 5120).getDouble(5120);
			ihorgennight = config.get("Ihor Solar Panel", "gennight", 2560).getDouble(2560);
			ihorstorage = config.get("Ihor Solar Panel", "storage", 5.0E7).getDouble(5.0E7);
			ihoroutput = config.get("Ihor Solar Panel", "output", 10240).getDouble(10240);
			ihortier = config.get("Ihor Solar Panel", "tier", 6).getInt(6);


		//

			config.save();

	}

}
