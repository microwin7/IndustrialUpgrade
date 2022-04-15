package com.denfop.register;

import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.block.adminpanel.BlockAdminPanel;
import com.denfop.block.base.*;
import com.denfop.block.blockoil.BlockOil;
import com.denfop.block.cable.BlockCable;
import com.denfop.block.chargepadstorage.BlockChargepad;
import com.denfop.block.doublemolecular.BlockDoubleMolecularTransformer;
import com.denfop.block.mechanism.*;
import com.denfop.block.molecular.BlockMolecularTransformer;
import com.denfop.block.oilgetter.BlockOilGetter;
import com.denfop.block.ore.*;
import com.denfop.block.radiationblock.BlockRadiation;
import com.denfop.block.radiationblock.BlockRadiationOre;
import com.denfop.block.sintezator.BlockSintezator;
import com.denfop.block.solargenerator.BlockAdvSolarGenerator;
import com.denfop.block.solargenerator.BlockImprSolarGenerator;
import com.denfop.block.solargenerator.BlockSolarGeneratorEnergy;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.item.*;
import com.denfop.item.armour.*;
import com.denfop.item.bags.ItemEnergyBags;
import com.denfop.item.base.IUItemBase;
import com.denfop.item.base.ItemCable;
import com.denfop.item.energy.*;
import com.denfop.item.matter.SolidMatter;
import com.denfop.item.modules.*;
import com.denfop.item.radionblock.RadoationResources;
import com.denfop.item.reactor.*;
import com.denfop.item.resources.*;
import com.denfop.item.resources.alloys.*;
import com.denfop.item.resources.preciousresources.ItemPreciousGem;
import com.denfop.item.rotor.ItemAdvancedWindRotor;
import com.denfop.item.upgrade.*;
import com.denfop.tiles.base.*;
import com.denfop.tiles.mechanism.*;
import com.denfop.tiles.neutroniumgenerator.TileEntityLiquidTankElectricMachine;
import com.denfop.tiles.neutroniumgenerator.TileneutronGenerator;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.tiles.reactors.*;
import com.denfop.tiles.se.TileAdvSolarGenerator;
import com.denfop.tiles.se.TileImpSolarGenerator;
import com.denfop.tiles.se.TileSolarGenerator;
import com.denfop.tiles.sintezator.TileEntitySintezator;
import com.denfop.tiles.solidmatter.*;
import com.denfop.tiles.tank.TileEntityAdvTank;
import com.denfop.tiles.tank.TileEntityImpTank;
import com.denfop.tiles.tank.TileEntityPerTank;
import com.denfop.tiles.tank.TileEntityTank;
import com.denfop.tiles.transformer.*;
import com.denfop.tiles.wiring.chargepad.*;
import com.denfop.tiles.wiring.storage.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class Register {
    static final ArmorMaterial RubyMaterial = EnumHelper.addArmorMaterial("RubyMaterial", 450,
            new int[]{2, 7, 3, 1}, 25);
    static final ArmorMaterial SapphireMaterial = EnumHelper.addArmorMaterial("SapphireMaterial", 550,
            new int[]{3, 5, 3, 2}, 25);
    static final ArmorMaterial TopazMaterial = EnumHelper.addArmorMaterial("TopazMaterial", 650,
            new int[]{3, 5, 4, 1}, 25);

    public static void register() {
        IUItem.iridium = new ItemStack(new ItemAdvancedWindRotor("iridium", Config.Radius, Config.durability,
                Config.efficiency, Config.minWindStrength, Config.maxWindStrength,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model1.png")));
        IUItem.compressiridium = new ItemStack(new ItemAdvancedWindRotor("compressiridium", Config.Radius1,
                Config.durability1, Config.efficiency1, Config.minWindStrength1, Config.maxWindStrength1,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model2.png")));
        IUItem.spectral = new ItemStack(new ItemAdvancedWindRotor("spectral", Config.Radius2,
                Config.durability2, Config.efficiency2, Config.minWindStrength2, Config.maxWindStrength2,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model3.png")));
        IUItem.myphical = new ItemStack(new ItemAdvancedWindRotor("myphical", Config.Radius5,
                Config.durability5, Config.efficiency5, Config.minWindStrength5, Config.maxWindStrength5,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model4.png")));

        IUItem.photon = new ItemStack(new ItemAdvancedWindRotor("photon", Config.Radius3, Config.durability3,
                Config.efficiency3, Config.minWindStrength3, Config.maxWindStrength3,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model6.png")));
        IUItem.neutron = new ItemStack(new ItemAdvancedWindRotor("neutron", Config.Radius4, Config.durability4,
                Config.efficiency4, Config.minWindStrength4, Config.maxWindStrength4,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model5.png")));

        IUItem.barionrotor = new ItemStack(new ItemAdvancedWindRotor("barionrotor", Config.Radius5,
                Config.durability5, Config.efficiency4 * 2, Config.minWindStrength5, Config.maxWindStrength5,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model7.png")));

        IUItem.adronrotor = new ItemStack(new ItemAdvancedWindRotor("adronrotor", Config.Radius5,
                Config.durability5, Config.efficiency4 * 4, Config.minWindStrength5, Config.maxWindStrength5,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model8.png")));
        IUItem.ultramarinerotor = new ItemStack(new ItemAdvancedWindRotor("ultramarinerotor", Config.Radius5,
                Config.durability5, Config.efficiency4 * 8, Config.minWindStrength5, Config.maxWindStrength5,
                new ResourceLocation(Constants.TEXTURES, "textures/items/carbon_rotor_model9.png")));


        IUItem.advBatChargeCrystal = new ItemBattery("itemadvBatChargeCrystal", Config.Storagequantumsuit, 32368D, 5, true);
        IUItem.itemBatChargeCrystal = new ItemBattery("itemBatChargeCrystal", Config.Storagequantumsuit * 4, 129472D, 6, true);

        IUItem.lapotronCrystal = new ItemBattery("itemBatLamaCrystal", Config.Storagequantumsuit, 8092.0D, 4, false);
        IUItem.spectralSaber = new ItemSpectralSaber("itemNanoSaber", Config.maxCharge, Config.transferLimit, Config.tier1,
                Config.spectralsaberactive, Config.spectralsabernotactive);
        IUItem.quantumSaber = new ItemQuantumSaber("itemNanoSaber1", Config.maxCharge1, Config.transferLimit1,
                Config.tier1, Config.spectralsaberactive1, Config.spectralsabernotactive1);
        IUItem.quantumHelmet = new ItemArmorImprovemedQuantum("itemArmorQuantumHelmet", 0, Config.armor_maxcharge,
                Config.armor_transferlimit, Config.tier);
        IUItem.quantumBodyarmor = new ItemArmorImprovemedQuantum("itemArmorQuantumChestplate", 1,
                Config.armor_maxcharge_body, Config.armor_transferlimit, Config.tier);
        IUItem.quantumLeggings = new ItemArmorImprovemedQuantum("itemArmorQuantumLegs", 2, Config.armor_maxcharge,
                Config.armor_transferlimit, Config.tier);
        IUItem.quantumBoots = new ItemArmorImprovemedQuantum("itemArmorQuantumBoots", 3, Config.armor_maxcharge,
                Config.armor_transferlimit, Config.tier);
        IUItem.NanoHelmet = new ItemArmorImprovemedNano("itemArmorNanoHelmet", 0, (float) Config.NanoHelmet,
                (float) Config.NanoTransfer, Config.Nanotier);
        IUItem.NanoBodyarmor = new ItemArmorImprovemedNano("itemArmorNanoChestplate", 1,
                (float) Config.NanoBodyarmor, Config.NanoTransfer, Config.Nanotier);
        IUItem.NanoLeggings = new ItemArmorImprovemedNano("itemArmorNanoLegs", 2, (float) Config.NanoLeggings,
                (float) Config.NanoTransfer, Config.Nanotier);
        IUItem.NanoBoots = new ItemArmorImprovemedNano("itemArmorNanoBoots", 3, (float) Config.NanoBoots,
                (float) Config.NanoTransfer, Config.Nanotier);

        IUItem.reactorDepletedprotonSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedprotonSimple"));
        IUItem.reactorDepletedprotonDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedprotonDual"));
        IUItem.reactorDepletedprotonQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedprotonQuad"));
        IUItem.reactorDepletedprotoneit = new ItemStack(
                new ItemDepletedRod("reactorDepletedprotoneit"));
        ItemStack[] stack = {IUItem.reactorDepletedprotonSimple, IUItem.reactorDepletedprotonDual, IUItem.reactorDepletedprotonQuad, IUItem.reactorDepletedprotoneit};

        IUItem.reactorprotonSimple = new ItemStack(new ItemBaseRod("reactorprotonSimple", 1,
                Config.ProtonRodCells, Config.ProtonRodHeat, Config.ProtonPower, stack));
        IUItem.reactorprotonDual = new ItemStack(new ItemBaseRod("reactorprotonDual", 2,
                Config.ProtonRodCells, Config.ProtonRodHeat, Config.ProtonPower, stack));
        IUItem.reactorprotonQuad = new ItemStack(new ItemBaseRod("reactorprotonQuad", 4,
                Config.ProtonRodCells, Config.ProtonRodHeat, Config.ProtonPower, stack));
        IUItem.reactorprotoneit = new ItemStack(new ItemBaseRod("reactorprotoneit", 8,
                Config.ProtonRodCells, Config.ProtonRodHeat, Config.ProtonPower, stack));
        IUItem.proton = new ItemRadioactive("proton", 150, 100);
        IUItem.toriy = new ItemRadioactive("toriy", 0, 0);
        //
        IUItem.reactorDepletedtoriySimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedtoriySimple"));
        IUItem.reactorDepletedtoriyDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedtoriyDual"));
        IUItem.reactorDepletedtoriyQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedtoriyQuad"));
        ItemStack[] stack1 = {IUItem.reactorDepletedtoriySimple, IUItem.reactorDepletedtoriyDual, IUItem.reactorDepletedtoriyQuad};
        IUItem.reactortoriySimple = new ItemStack(new ItemBaseRod("reactortoriySimple", 1,
                Config.toriyRodCells, Config.toriyRodHeat, Config.toriyPower, stack1));
        IUItem.reactortoriyDual = new ItemStack(new ItemBaseRod("reactortoriyDual", 2,
                Config.toriyRodCells, Config.toriyRodHeat, Config.toriyPower, stack1));
        IUItem.reactortoriyQuad = new ItemStack(new ItemBaseRod("reactortoriyQuad", 4,
                Config.toriyRodCells, Config.toriyRodHeat, Config.toriyPower, stack1));

        //
//
        IUItem.reactorDepletedamericiumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedamericiumSimple"));
        IUItem.reactorDepletedamericiumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedamericiumDual"));
        IUItem.reactorDepletedamericiumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedamericiumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedamericiumSimple, IUItem.reactorDepletedamericiumDual, IUItem.reactorDepletedamericiumQuad};
        IUItem.reactoramericiumSimple = new ItemStack(new ItemBaseRod("reactoramericiumSimple", 1,
                Config.americiumRodCells, Config.americiumRodHeat, (float) Config.americiumPower, stack1));
        IUItem.reactoramericiumDual = new ItemStack(new ItemBaseRod("reactoramericiumDual", 2,
                Config.americiumRodCells, Config.americiumRodHeat, (float) Config.americiumPower, stack1));
        IUItem.reactoramericiumQuad = new ItemStack(new ItemBaseRod("reactoramericiumQuad", 4,
                Config.americiumRodCells, Config.americiumRodHeat, (float) Config.americiumPower, stack1));

        //
        //
        IUItem.reactorDepletedneptuniumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedneptuniumSimple"));
        IUItem.reactorDepletedneptuniumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedneptuniumDual"));
        IUItem.reactorDepletedneptuniumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedneptuniumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedneptuniumSimple, IUItem.reactorDepletedneptuniumDual, IUItem.reactorDepletedneptuniumQuad};
        IUItem.reactorneptuniumSimple = new ItemStack(new ItemBaseRod("reactorneptuniumSimple", 1,
                Config.neptuniumRodCells, Config.neptuniumRodHeat, (float) Config.neptuniumPower, stack1));
        IUItem.reactorneptuniumDual = new ItemStack(new ItemBaseRod("reactorneptuniumDual", 2,
                Config.neptuniumRodCells, Config.neptuniumRodHeat, (float) Config.neptuniumPower, stack1));
        IUItem.reactorneptuniumQuad = new ItemStack(new ItemBaseRod("reactorneptuniumQuad", 4,
                Config.neptuniumRodCells, Config.neptuniumRodHeat, (float) Config.neptuniumPower, stack1));

        //
        IUItem.reactorDepletedcuriumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedcuriumSimple"));
        IUItem.reactorDepletedcuriumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedcuriumDual"));
        IUItem.reactorDepletedcuriumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedcuriumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedcuriumSimple, IUItem.reactorDepletedcuriumDual, IUItem.reactorDepletedcuriumQuad};
        IUItem.reactorcuriumSimple = new ItemStack(new ItemBaseRod("reactorcuriumSimple", 1,
                Config.curiumRodCells, Config.curiumRodHeat, (float) Config.curiumPower, stack1));
        IUItem.reactorcuriumDual = new ItemStack(new ItemBaseRod("reactorcuriumDual", 2,
                Config.curiumRodCells, Config.curiumRodHeat, (float) Config.curiumPower, stack1));
        IUItem.reactorcuriumQuad = new ItemStack(new ItemBaseRod("reactorcuriumQuad", 4,
                Config.curiumRodCells, Config.curiumRodHeat, (float) Config.curiumPower, stack1));

        //
        //
        IUItem.reactorDepletedcaliforniaSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedcaliforniaSimple"));
        IUItem.reactorDepletedcaliforniaDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedcaliforniaDual"));
        IUItem.reactorDepletedcaliforniaQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedcaliforniaQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedcaliforniaSimple, IUItem.reactorDepletedcaliforniaDual, IUItem.reactorDepletedcaliforniaQuad};
        IUItem.reactorcaliforniaSimple = new ItemStack(new ItemBaseRod("reactorcaliforniaSimple", 1,
                Config.californiaRodCells, Config.californiaRodHeat, (float) Config.californiaPower, stack1));
        IUItem.reactorcaliforniaDual = new ItemStack(new ItemBaseRod("reactorcaliforniaDual", 2,
                Config.californiaRodCells, Config.californiaRodHeat, (float) Config.californiaPower, stack1));
        IUItem.reactorcaliforniaQuad = new ItemStack(new ItemBaseRod("reactorcaliforniaQuad", 4,
                Config.californiaRodCells, Config.californiaRodHeat, (float) Config.californiaPower, stack1));

        //

        //
        IUItem.reactorDepletedmendeleviumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedmendeleviumSimple"));
        IUItem.reactorDepletedmendeleviumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedmendeleviumDual"));
        IUItem.reactorDepletedmendeleviumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedmendeleviumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedmendeleviumSimple, IUItem.reactorDepletedmendeleviumDual, IUItem.reactorDepletedmendeleviumQuad};
        IUItem.reactormendeleviumSimple = new ItemStack(new ItemBaseRod("reactormendeleviumSimple", 1,
                Config.mendeleviumRodCells, Config.mendeleviumRodHeat, (float) Config.mendeleviumPower, stack1));
        IUItem.reactormendeleviumDual = new ItemStack(new ItemBaseRod("reactormendeleviumDual", 2,
                Config.mendeleviumRodCells, Config.mendeleviumRodHeat, (float) Config.mendeleviumPower, stack1));
        IUItem.reactormendeleviumQuad = new ItemStack(new ItemBaseRod("reactormendeleviumQuad", 4,
                Config.mendeleviumRodCells, Config.mendeleviumRodHeat, (float) Config.mendeleviumPower, stack1));

        //
        //
        IUItem.reactorDepletedberkeliumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedberkeliumSimple"));
        IUItem.reactorDepletedberkeliumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedberkeliumDual"));
        IUItem.reactorDepletedberkeliumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedberkeliumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedberkeliumSimple, IUItem.reactorDepletedberkeliumDual, IUItem.reactorDepletedberkeliumQuad};
        IUItem.reactorberkeliumSimple = new ItemStack(new ItemBaseRod("reactorberkeliumSimple", 1,
                Config.berkeliumRodCells, Config.berkeliumRodHeat, (float) Config.berkeliumPower, stack1));
        IUItem.reactorberkeliumDual = new ItemStack(new ItemBaseRod("reactorberkeliumDual", 2,
                Config.berkeliumRodCells, Config.berkeliumRodHeat, (float) Config.berkeliumPower, stack1));
        IUItem.reactorberkeliumQuad = new ItemStack(new ItemBaseRod("reactorberkeliumQuad", 4,
                Config.berkeliumRodCells, Config.berkeliumRodHeat, (float) Config.berkeliumPower, stack1));

        //
        //
        IUItem.reactorDepletedeinsteiniumSimple = new ItemStack(
                new ItemDepletedRod("reactorDepletedeinsteiniumSimple"));
        IUItem.reactorDepletedeinsteiniumDual = new ItemStack(
                new ItemDepletedRod("reactorDepletedeinsteiniumDual"));
        IUItem.reactorDepletedeinsteiniumQuad = new ItemStack(
                new ItemDepletedRod("reactorDepletedeinsteiniumQuad"));
        stack1 = new ItemStack[]{IUItem.reactorDepletedeinsteiniumSimple, IUItem.reactorDepletedeinsteiniumDual, IUItem.reactorDepletedeinsteiniumQuad};
        IUItem.reactoreinsteiniumSimple = new ItemStack(new ItemBaseRod("reactoreinsteiniumSimple", 1,
                Config.einsteiniumRodCells, Config.einsteiniumRodHeat, (float) Config.einsteiniumPower, stack1));
        IUItem.reactoreinsteiniumDual = new ItemStack(new ItemBaseRod("reactoreinsteiniumDual", 2,
                Config.einsteiniumRodCells, Config.einsteiniumRodHeat, (float) Config.einsteiniumPower, stack1));
        IUItem.reactoreinsteiniumQuad = new ItemStack(new ItemBaseRod("reactoreinsteiniumQuad", 4,
                Config.einsteiniumRodCells, Config.einsteiniumRodHeat, (float) Config.einsteiniumPower, stack1));

        //
        //
        IUItem.reactorDepleteduran233Simple = new ItemStack(
                new ItemDepletedRod("reactorDepleteduran233Simple"));
        IUItem.reactorDepleteduran233Dual = new ItemStack(
                new ItemDepletedRod("reactorDepleteduran233Dual"));
        IUItem.reactorDepleteduran233Quad = new ItemStack(
                new ItemDepletedRod("reactorDepleteduran233Quad"));
        stack1 = new ItemStack[]{IUItem.reactorDepleteduran233Simple, IUItem.reactorDepleteduran233Dual, IUItem.reactorDepleteduran233Quad};
        IUItem.reactoruran233Simple = new ItemStack(new ItemBaseRod("reactoruran233Simple", 1,
                Config.uran233RodCells, Config.uran233RodHeat, (float) Config.uran233Power, stack1));
        IUItem.reactoruran233Dual = new ItemStack(new ItemBaseRod("reactoruran233Dual", 2,
                Config.uran233RodCells, Config.uran233RodHeat, (float) Config.uran233Power, stack1));
        IUItem.reactoruran233Quad = new ItemStack(new ItemBaseRod("reactoruran233Quad", 4,
                Config.uran233RodCells, Config.uran233RodHeat, (float) Config.uran233Power, stack1));

        //
        IUItem.protonshard = new ItemRadioactive("protonshard", 150, 100);
        IUItem.reactorCoolantmax = new ItemStack(new ItemReactorHeatStorage("reactorCoolantmax", 240000));
        IUItem.reactorCoolanttwelve = new ItemStack(new ItemReactorHeatStorage("reactorCoolanttwelve", 120000));

        IUItem.cable = new ItemCable();

        if (Config.BotaniaLoaded) {
            ItemStack[] stack4 = {BotaniaIntegration.reactorDepletedterastrellSimple, BotaniaIntegration.reactorDepletedterastrellDual, BotaniaIntegration.reactorDepletedterastrellQuad};
            BotaniaIntegration.reactorDepletedterastrellSimple = new ItemStack(
                    new ItemDepletedRod("reactorDepletedterastrellSimple"));
            BotaniaIntegration.reactorDepletedterastrellDual = new ItemStack(
                    new ItemDepletedRod("reactorDepletedterastrellDual"));
            BotaniaIntegration.reactorDepletedterastrellQuad = new ItemStack(
                    new ItemDepletedRod("reactorDepletedterastrellQuad"));
            BotaniaIntegration.reactorterastrellSimple = new ItemStack(new ItemBaseRod("reactorterastrellSimple", 1,
                    Config.TerrasteelRodCells, Config.TerrasteelRodHeat, Config.TerrasteelPower, stack4));

            BotaniaIntegration.reactorterastrellDual = new ItemStack(new ItemBaseRod("reactorterastrellDual", 2,
                    Config.TerrasteelRodCells, Config.TerrasteelRodHeat, Config.TerrasteelPower, stack4));

            BotaniaIntegration.reactorterastrellQuad = new ItemStack(new ItemBaseRod("reactorterastrellQuad", 4,
                    Config.TerrasteelRodCells, Config.TerrasteelRodHeat, Config.TerrasteelPower, stack4));


        }
        IUItem.toriyore = new BlockRadiation("toriyore");
        IUItem.plate = new ItemPlate();
        IUItem.spectralSolarHelmet = new ItemSolarPanelHelmet(ItemArmor.ArmorMaterial.DIAMOND, IUCore.proxy.addArmor("spectralSolarHelmet"), 0, 4, "spectral_solar_helmet");
        IUItem.singularSolarHelmet = new ItemSolarPanelHelmet(ItemArmor.ArmorMaterial.DIAMOND, IUCore.proxy.addArmor("singularSolarHelmet"), 0, 5, "singular_solar_helmet");
        IUItem.blockpanel = new BlockSolarPanel();
        IUItem.basemachine1 = new BlockBaseMachine3();
        IUItem.basemachine2 = new BlockBaseMachine4();
        IUItem.sunnarium = new itemSunnarium();
        IUItem.blockdoublemolecular = new BlockDoubleMolecularTransformer();
        IUItem.paints = new ItemPaints();
        IUItem.nano_bow = new EnergyBow("nano_bow", 0, Config.tier_nano_bow, Config.transfer_nano_bow, Config.maxenergy_nano_bow, 1f);
        IUItem.quantum_bow = new EnergyBow("quantum_bow", 0, Config.tier_quantum_bow, Config.transfer_quantum_bow, Config.maxenergy_quantum_bow, 2f);
        IUItem.spectral_bow = new EnergyBow("spectral_bow", 0, Config.tier_spectral_bow, Config.transfer_spectral_bow, Config.maxenergy_spectral_bow, 4f);
        IUItem.adv_lappack = new ItemLappack("adv_lappack", ArmorMaterial.DIAMOND, 1, Config.adv_lappack_maxenergy, Config.adv_lappack_tier, Config.adv_lappack_transfer);
        IUItem.imp_lappack = new ItemLappack("imp_lappack", ArmorMaterial.DIAMOND, 1, Config.imp_lappack_maxenergy, Config.imp_lappack_tier, Config.imp_lappack_transfer);
        IUItem.per_lappack = new ItemLappack("per_lappack", ArmorMaterial.DIAMOND, 1, Config.per_lappack_maxenergy, Config.per_lappack_tier, Config.per_lappack_transfer);
        IUItem.preciousgem = new ItemPreciousGem();
        IUItem.preciousore = new BlockPreciousOre();
        IUItem.preciousblock = new BlockPrecious();
        IUItem.core = new ItemCore();
        IUItem.basecircuit = new ItemBaseCircuit();
        IUItem.magnet = new ItemMagnet("magnet", 100000, 5000, 4, 7);
        IUItem.impmagnet = new ItemMagnet("impmagnet", 200000, 7500, 5, 11);
        IUItem.ruby_helmet = new BaseArmor("ruby_helmet", RubyMaterial, 3, 0, "ruby");
        IUItem.ruby_chestplate = new BaseArmor("ruby_chestplate", RubyMaterial, 3, 1, "ruby");
        IUItem.ruby_leggings = new BaseArmor("ruby_leggings", RubyMaterial, 3, 2, "ruby");
        IUItem.ruby_boots = new BaseArmor("ruby_boots", RubyMaterial, 3, 3, "ruby");
        IUItem.topaz_helmet = new BaseArmor("topaz_helmet", TopazMaterial, 3, 0, "topaz");
        IUItem.topaz_chestplate = new BaseArmor("topaz_chestplate", TopazMaterial, 3, 1, "topaz");
        IUItem.topaz_leggings = new BaseArmor("topaz_leggings", TopazMaterial, 3, 2, "topaz");
        IUItem.topaz_boots = new BaseArmor("topaz_boots", TopazMaterial, 3, 3, "topaz");
        IUItem.sapphire_helmet = new BaseArmor("sapphire_helmet", SapphireMaterial, 3, 0, "sapphire");
        IUItem.sapphire_chestplate = new BaseArmor("sapphire_chestplate", SapphireMaterial, 3, 1, "sapphire");
        IUItem.sapphire_leggings = new BaseArmor("sapphire_leggings", SapphireMaterial, 3, 2, "sapphire");
        IUItem.sapphire_boots = new BaseArmor("sapphire_boots", SapphireMaterial, 3, 3, "sapphire");
        IUItem.Purifier = new ItemPurifier("Purifier", 100000, 1000, 3);
        IUItem.GraviTool = new ItemGraviTool("GraviTool", Item.ToolMaterial.EMERALD);
        IUItem.nugget = new ItemNugget();
        IUItem.UpgradePanelKit = new ItemUpgradePanelKit();
        IUItem.casing = new ItemCasing();
        IUItem.iudust = new ItemDust();
        IUItem.iuingot = new ItemIngot();
        IUItem.crushed = new ItemCrushed();
        IUItem.purifiedcrushed = new ItemPurifiedCrushed();
        IUItem.smalldust = new ItemsmallDust();
        IUItem.stik = new ItemStiks();
        IUItem.verysmalldust = new ItemverysmallDust();
        IUItem.doubleplate = new ItemDoublePlate();
        IUItem.photonglass = new ItemPhotoniumGlass();
        IUItem.lens = new ItemLens();
        IUItem.netherore = new BlockNetherOre();
        IUItem.endore = new BlockEndOre();
        IUItem.netherore1 = new BlockNetherOre1();
        IUItem.endore1 = new BlockEndOre1();
        IUItem.heavyore = new BlockHeavyOre();
        IUItem.ore = new BlockBaseOre();
        IUItem.ore1 = new BlockBaseOre1();
        IUItem.tranformer = new BlockTransformer();
        IUItem.radiationore = new BlockRadiationOre();
        IUItem.radiationresources = new RadoationResources();
        IUItem.advchamberblock = new BlockReactorChamber();
        IUItem.impchamberblock = new BlockImpReactorChamber();
        IUItem.perchamberblock = new BlockPerReactorChamber();
        IUItem.block = new BlockIngot();
        IUItem.block1 = new BlockIngot1();
        IUItem.solidmatter = new BlockSolidMatter();
        IUItem.convertersolidmatter = new BlockConverterSolidMatter();
        IUItem.sunnariumpanel = new ItemSunnariumPanel();
        IUItem.alloyscasing = new ItemAlloysCasing();
        IUItem.alloysdoubleplate = new ItemAlloysDoublePlate();
        IUItem.alloysdust = new ItemAlloysDust();
        IUItem.alloysingot = new ItemAlloysIngot();
        IUItem.alloysnugget = new ItemAlloysNugget();
        IUItem.alloysplate = new ItemAlloysPlate();
        IUItem.alloysblock = new BlockAlloysIngot(Material.iron);
        IUItem.basemachine = new BlockBaseMachine2();
        IUItem.bucket = new ItemBucket();
        IUItem.UpgradeKit = new ItemUpgradeKit();
        IUItem.doublescrapBox = new IUItemBase("doublescrapBox");
        IUItem.quarrymodule = new IUItemBase("quarrymodule").setCreativeTab(IUCore.tabssp1);
        IUItem.analyzermodule = new IUItemBase("analyzermodule").setCreativeTab(IUCore.tabssp1);
        IUItem.machines = new BlockBaseMachine();
        IUItem.ImpblockSE = new BlockImprSolarGenerator();
        IUItem.AdvblockSE = new BlockAdvSolarGenerator();
        IUItem.blockSE = new BlockSolarGeneratorEnergy();
        IUItem.blockmolecular = new BlockMolecularTransformer();
        IUItem.sunnariummaker = new BlockSunnariumMaker();
        IUItem.sunnariumpanelmaker = new BlockSunnariumPanelMaker();
        IUItem.blockadmin = new BlockAdminPanel();
        IUItem.electricblock = new BlockElectric();
        IUItem.Chargepadelectricblock = new BlockChargepad();
        IUItem.photoniy = new IUItemBase("photoniy");
        IUItem.photoniy_ingot = new IUItemBase("photoniy_ingot");
        IUItem.compressIridiumplate = new IUItemBase("QuantumItems2");
        IUItem.advQuantumtool = new IUItemBase("QuantumItems3");
        IUItem.expmodule = new IUItemBase("expmodule").setCreativeTab(IUCore.tabssp1);
        IUItem.doublecompressIridiumplate = new IUItemBase("QuantumItems4");
        IUItem.circuitSpectral = new IUItemBase("QuantumItems5");
        IUItem.nanoBox = new IUItemBase("nanobox");
        IUItem.module_schedule = new IUItemBase("module_schedule");
        IUItem.quantumtool = new IUItemBase("QuantumItems6");
        IUItem.advnanobox = new IUItemBase("QuantumItems7");
        IUItem.neutronium = new IUItemBase("neutronium");
        IUItem.plast = new IUItemBase("plast");
        IUItem.module_stack = new IUItemBase("module_stack").setCreativeTab(IUCore.tabssp1);
        IUItem.module_quickly = new IUItemBase("module_quickly").setCreativeTab(IUCore.tabssp1);
        IUItem.plastic_plate = new IUItemBase("plastic_plate");
        IUItem.neutroniumingot = new IUItemBase("neutroniumingot");
        IUItem.blocksintezator = new BlockSintezator();
        IUItem.advancedSolarHelmet = new ItemSolarPanelHelmet(ItemArmor.ArmorMaterial.DIAMOND, IUCore.proxy.addArmor("advancedSolarHelmet"), 0, 1, "advanced_solar_helmet");
        IUItem.hybridSolarHelmet = new ItemSolarPanelHelmet(ItemArmor.ArmorMaterial.DIAMOND, IUCore.proxy.addArmor("hybridSolarHelmet"), 0, 2, "hybrid_solar_helmet");
        IUItem.ultimateSolarHelmet = new ItemSolarPanelHelmet(ItemArmor.ArmorMaterial.DIAMOND, IUCore.proxy.addArmor("ultimateSolarHelmet"), 0, 3, "ultimate_solar_helmet");
        IUItem.itemIU = new ItemIUCrafring();
        IUItem.cirsuitQuantum = new IUItemBase("QuantumItems8");
        IUItem.Helium = new IUItemBase("Helium");
        IUItem.QuantumItems9 = new IUItemBase("QuantumItems9");
        IUItem.coal_chunk1 = new IUItemBase("coal_chunk");
        IUItem.compresscarbon = new IUItemBase("compresscarbon");
        IUItem.compresscarbonultra = new IUItemBase("compresscarbonultra");
        IUItem.nanodrill = new EnergyDrill(Item.ToolMaterial.EMERALD, "nanodrill", 1, 1, Config.nano_transfer, Config.nano_maxEnergy, 2, 20, 15, Config.nano_energyPerOperation, Config.nano_energyPerbigHolePowerOperation);
        IUItem.quantumdrill = new EnergyDrill(Item.ToolMaterial.EMERALD, "quantumdrill", 3, 2, Config.quantum_transfer, Config.quantum_maxEnergy, 3, 25, 20, Config.quantum_energyPerOperation, Config.quantum_energyPerbigHolePowerOperation);
        IUItem.spectraldrill = new EnergyDrill(Item.ToolMaterial.EMERALD, "spectraldrill", 5, 3, Config.spectral_transfer, Config.spectral_maxEnergy, 4, 30, 25, Config.spectral_energyPerOperation, Config.spectral_energyPerbigHolePowerOperation);
        IUItem.upgrademodule = new UpgradeModule();
        IUItem.lathingprogram = new ItemLathingProgramm("itemLathingProgramm");
        // TODO
        IUItem.nanopickaxe = new EnergyPickaxe(Item.ToolMaterial.EMERALD, "nanopickaxe", 1,
                1, Config.nano_transfer, Config.nano_maxEnergy, 2, 20, 15, Config.nano_energyPerOperation, Config.nano_energyPerbigHolePowerOperation);
        IUItem.nanoshovel = new EnergyShovel(Item.ToolMaterial.EMERALD, "nanoshovel", 1, 1,
                Config.nano_transfer, Config.nano_maxEnergy, 2, 20, 10, Config.nano_energyPerOperation, Config.nano_energyPerbigHolePowerOperation);
        IUItem.nanoaxe = new EnergyAxe(Item.ToolMaterial.EMERALD, "nanoaxe", 1, 1, Config.nano_transfer, Config.nano_maxEnergy, 2, 20, 15, Config.nano_energyPerOperation, Config.nano_energyPerbigHolePowerOperation);
        IUItem.quantumpickaxe = new EnergyPickaxe(Item.ToolMaterial.EMERALD, "quantumpickaxe", 3, 2,
                Config.quantum_transfer, Config.quantum_maxEnergy, 3, 25, 20, Config.quantum_energyPerOperation, Config.quantum_energyPerbigHolePowerOperation);
        IUItem.quantumshovel = new EnergyShovel(Item.ToolMaterial.EMERALD, "quantumshovel",
                3, 2, Config.quantum_transfer, Config.quantum_maxEnergy, 3, 25, 10, Config.quantum_energyPerOperation, Config.quantum_energyPerbigHolePowerOperation);
        IUItem.quantumaxe = new EnergyAxe(Item.ToolMaterial.EMERALD, "quantumaxe", 3, 2,
                Config.quantum_transfer, Config.quantum_maxEnergy, 3, 25, 20, Config.quantum_energyPerOperation, Config.quantum_energyPerbigHolePowerOperation);
        IUItem.spectralpickaxe = new EnergyPickaxe(Item.ToolMaterial.EMERALD, "spectralpickaxe", 5, 3,
                Config.spectral_transfer, Config.spectral_maxEnergy, 4, 30, 25, Config.spectral_energyPerOperation, Config.spectral_energyPerbigHolePowerOperation);

        IUItem.spectralshovel = new EnergyShovel(Item.ToolMaterial.EMERALD, "spectralshovel", 5, 3,
                Config.spectral_transfer, Config.spectral_maxEnergy, 4, 30, 10, Config.spectral_energyPerOperation, Config.spectral_energyPerbigHolePowerOperation);
        IUItem.spectralaxe = new EnergyAxe(Item.ToolMaterial.EMERALD, "spectralaxe", 5, 3,
                Config.spectral_transfer, Config.spectral_maxEnergy, 4, 30, 25, Config.spectral_energyPerOperation, Config.spectral_energyPerbigHolePowerOperation);
        IUItem.module7 = new AdditionModule();
        IUItem.module9 = new QuarryModule();
        IUItem.machines_base = new BlockMoreMachine();
        IUItem.machines_base1 = new BlockMoreMachine2();
        IUItem.machines_base2 = new BlockMoreMachine3();
        IUItem.machines_base3 = new BlockMoreMachine4();

        IUItem.upgradeblock = new BlockUpgrade();
        IUItem.module8 = new ItemWirelessModule();
        IUItem.ultDDrill = new AdvancedMultiTool(Item.ToolMaterial.EMERALD, "ultDDrill");
        IUItem.module1 = new ModuleBase("module1");
        IUItem.genmodule = new ModuleBase("genmodule");
        IUItem.genmodule1 = new ModuleBase("genmodule1");
        IUItem.module2 = new ModuleBase("module2");
        IUItem.gennightmodule = new ModuleBase("gennightmodule");
        IUItem.gennightmodule1 = new ModuleBase("gennightmodule1");
        IUItem.module3 = new ModuleBase("module3");
        IUItem.storagemodule = new ModuleBase("storagemodule");
        IUItem.storagemodule1 = new ModuleBase("storagemodule1");
        IUItem.module4 = new ModuleBase("module4");
        IUItem.outputmodule = new ModuleBase("outputmodule");
        IUItem.outputmodule1 = new ModuleBase("outputmodule1");
        IUItem.module5 = new ModuleType();
        IUItem.module6 = new ModuleTypePanel();
        IUItem.cableblock = new BlockCable();
        IUItem.module = new ItemUpgradeModule();
        IUItem.machinekit = new ItemUpgradeMachinesKit();
        IUItem.cell_all = new ItemCell();
        IUItem.matter = new SolidMatter();
        IUItem.oilrefiner = new BlockOilRefiner();
        IUItem.oilblock = new BlockOil();
        IUItem.oilquarry = new BlockQuarryOil();
        IUItem.oilgetter = new BlockOilGetter();
        IUItem.combinersolidmatter = new BlockCombinerSolidMatter();
        IUItem.hazmathelmet = new ItemArmorAdvHazmat("hazmathelmet", 0);
        IUItem.hazmatchest = new ItemArmorAdvHazmat("hazmatchest", 1);
        IUItem.hazmatleggins = new ItemArmorAdvHazmat("hazmatleggins", 2);
        IUItem.hazmatboosts = new ItemArmorAdvHazmat("hazmatboosts", 3);
        IUItem.advjetpack = new ItemAdvJetpack("advjetpack", Config.adv_jetpack_maxenergy, Config.adv_jetpack_transfer, Config.adv_jetpack_tier);
        IUItem.impjetpack = new ItemAdvJetpack("impjetpack", Config.imp_jetpack_maxenergy, Config.imp_jetpack_transfer, Config.imp_jetpack_tier);
        IUItem.perjetpack = new ItemAdvJetpack("perjetpack", Config.per_jetpack_maxenergy, Config.per_jetpack_transfer, Config.per_jetpack_tier);
        IUItem.oiladvrefiner = new BlockOilAdvRefiner();
        IUItem.anode = new ItemChemistry("anode");
        IUItem.cathode = new ItemChemistry("cathode");
        IUItem.advventspread = new ItemReactorVentSpread("advventspread", Config.advventspread_sidevent);
        IUItem.impventspread = new ItemReactorVentSpread("impventspread", Config.impventspread_sidevent);
        IUItem.reactoradvVent = new ItemReactorVent("reactoradvVent", Config.reactoradvVent_heatStorage1, Config.reactoradvVent_selfvent, Config.reactoradvVent_reactorvent);
        IUItem.reactorimpVent = new ItemReactorVent("reactorimpVent", Config.reactorimpVent_heatStorage1, Config.reactorimpVent_selfvent, Config.reactorimpVent_reactorvent);
        IUItem.reactorCondensatorDiamond = new ItemReactorCondensator("reactorcondensatordiamond", 500000);
        IUItem.advheatswitch = new ItemReactorHeatSwitch("advheatswitch", Config.advheatswitch_heatStorage1, Config.advheatswitch_switchside, Config.advheatswitch_switchreactor);
        IUItem.impheatswitch = new ItemReactorHeatSwitch("impheatswitch", Config.impheatswitch_heatStorage1, Config.impheatswitch_switchside, Config.impheatswitch_switchreactor);
        IUItem.entitymodules = new ItemEntityModule();
        IUItem.bags = new ItemEnergyBags("iu_bags", 27, 50000, 500);
        IUItem.adv_bags = new ItemEnergyBags("adv_iu_bags", 45, 75000, 750);
        IUItem.imp_bags = new ItemEnergyBags("imp_iu_bags", 63, 100000, 1000);
        IUItem.spawnermodules = new SpawnerModules();
        IUItem.tank = new BlockTank();
        IUItem.vein = new BlockVein();
        IUItem.phase_module = new ModuleBase("phase_module");
        IUItem.phase_module1 = new ModuleBase("phase_module1");
        IUItem.phase_module2 = new ModuleBase("phase_module2");
        IUItem.moonlinse_module = new ModuleBase("moonlinse_module");
        IUItem.moonlinse_module1 = new ModuleBase("moonlinse_module1");
        IUItem.moonlinse_module2 = new ModuleBase("moonlinse_module2");
        IUItem.errorblock = new BlockError();
    }

    public static void registertiles() {

        GameRegistry.registerTileEntity(TileOilBlock.class, "TileOilBlock");
        GameRegistry.registerTileEntity(TileEntityQuarryVein.class, "TileEntityQuarryVein");
        GameRegistry.registerTileEntity(TileEntityImpPump.class, "TileEntityImpPump");

        GameRegistry.registerTileEntity(TileEntityChargepadBatBox.class, "Chargepad BatBox1");
        GameRegistry.registerTileEntity(TileEntityChargepadCESU.class, "Chargepad CESU1");
        GameRegistry.registerTileEntity(TileEntityChargepadMFE.class, "Chargepad MFE1");
        GameRegistry.registerTileEntity(TileEntityChargepadMFSU.class, "Chargepad MFSU1");
        GameRegistry.registerTileEntity(TileEntityDoubleMolecular.class, "TileEntityDoubleMolecular");
        GameRegistry.registerTileEntity(TileEntityDieselGenerator.class, "TileEntityDieselGenerator");
        GameRegistry.registerTileEntity(TileEntityPetrolGenerator.class, "TileEntityPetrolGenerator");
        GameRegistry.registerTileEntity(TileEntityElectrolyzer.class, "TileEntityElectrolyzer");
        GameRegistry.registerTileEntity(TileEntityElectricLather.class, "TileEntityElectricLather");
        GameRegistry.registerTileEntity(TileEntityTunerWireless.class, "TileEntityTunerWireless");
        GameRegistry.registerTileEntity(TileEntityAutoSpawner.class, "TileEntityAutoSpawner");


        GameRegistry.registerTileEntity(TileEntityOilRefiner.class, "TileEntityOilRefiner");
        GameRegistry.registerTileEntity(TileEntityOilGetter.class, "TileEntityOilGetter");
        GameRegistry.registerTileEntity(TileEntityHydrogenGenerator.class, "TileEntityHydrogenGenerator");
        GameRegistry.registerTileEntity(TileEntityObsidianGenerator.class, "TileEntityObsidianGenerator");


        GameRegistry.registerTileEntity(TileEntityElectricCESU.class, " CESU1");
        GameRegistry.registerTileEntity(TileEntityElectricMFE.class, " MFE1");
        GameRegistry.registerTileEntity(TileEntityElectricMFSU.class, " MFSU1");

        GameRegistry.registerTileEntity(TileEntityImpKineticGenerator.class, "TileEntityImpKineticGenerator");
        GameRegistry.registerTileEntity(TileEntityPerKineticGenerator.class, "TileEntityPerKineticGenerator");
        GameRegistry.registerTileEntity(TileEntityAdvKineticGenerator.class, "TileEntityAdvKineticGenerator");
        GameRegistry.registerTileEntity(TileEntityStorageExp.class, "TileEntityStorageExp");


        GameRegistry.registerTileEntity(TileEntityMolecularTransformer.class, "MolecularTransformer");
        GameRegistry.registerTileEntity(TileEntityQuantumQuarry.class, "QuantumQuarry");
        GameRegistry.registerTileEntity(TileEntityGenerationStone.class, "Generation Stone");
        GameRegistry.registerTileEntity(TileEntityAdvancedMatter.class, "Mass Fabricator Advanced");
        GameRegistry.registerTileEntity(TileEntityImprovedMatter.class, "Mass Fabricator Improved");
        GameRegistry.registerTileEntity(TileEntityUltimateMatter.class, "Mass Fabricator Ultimate");
        GameRegistry.registerTileEntity(TileEntityAlloySmelter.class, "AlloySmelter");
        GameRegistry.registerTileEntity(TileEntityGenerationMicrochip.class, "GenerationMicrochip");

        GameRegistry.registerTileEntity(TileEntityAdminSolarPanel.class, "TileEntityAdminSolarPanel");
        GameRegistry.registerTileEntity(TileEntitySintezator.class, "TileEntitySintezator");
        GameRegistry.registerTileEntity(TileEntityPrivatizer.class, "TileEntityPrivatizer");

        GameRegistry.registerTileEntity(TileEntityElectricAdvMFSU.class, "MFES");
        GameRegistry.registerTileEntity(TileEntityElectricUltMFSU.class, "MFSUS");
        GameRegistry.registerTileEntity(TileEntityElectricBatBox.class, "MFSUS1");
        GameRegistry.registerTileEntity(TileEntityPump.class, "TileEntityPump");

        GameRegistry.registerTileEntity(TileEntityElectricPerMFSU.class, "PerMFSU");
        GameRegistry.registerTileEntity(TileEntityElectricBarMFSU.class, "BarMFSU");
        EnumMultiMachine.registerTile();
        EnumSolarPanels.registerTile();
        GameRegistry.registerTileEntity(TileEntityCable.class, "Cable1");
        GameRegistry.registerTileEntity(TileSolarGenerator.class, "TileSE");
        GameRegistry.registerTileEntity(TileAdvSolarGenerator.class, "TileAdvSE");
        GameRegistry.registerTileEntity(TileImpSolarGenerator.class, "TileImpSE");
        GameRegistry.registerTileEntity(TileEntityAerSolidMatter.class, "TileAerSolidMatter");
        GameRegistry.registerTileEntity(TileEntityAquaSolidMatter.class, "TileAquaSolidMatter");
        GameRegistry.registerTileEntity(TileEntityEarthSolidMatter.class, "TileEarthSolidMatter");
        GameRegistry.registerTileEntity(TileEntityEndSolidMatter.class, "TileEndSolidMatter");
        GameRegistry.registerTileEntity(TileEntityNetherSolidMatter.class, "TileNetherSolidMatter");
        GameRegistry.registerTileEntity(TileEntityNightSolidMatter.class, "TileNightSolidMatter");
        GameRegistry.registerTileEntity(TileEntitySolidMatter.class, "TileSolidMatter");
        GameRegistry.registerTileEntity(TileEntitySunSolidMatter.class, "TileSunSolidMatter");
        GameRegistry.registerTileEntity(TileEntityImpReactorChamberElectric.class, "TileEntityImpReactorChamberElectric");
        GameRegistry.registerTileEntity(TileEntityModuleMachine.class, "TileEntityModuleMachine");
        GameRegistry.registerTileEntity(TileEntityPerReactorChamberElectric.class, "TileEntityPerReactorChamberElectric");
        GameRegistry.registerTileEntity(TileEntityMagnet.class, "TileEntityMagnet");
        GameRegistry.registerTileEntity(TileEntityMagnetGenerator.class, "TileEntityMagnetGenerator");
        GameRegistry.registerTileEntity(TileneutronGenerator.class, "TileneutronGenerator");
        GameRegistry.registerTileEntity(TileEntityLiquidTankElectricMachine.class, "TileEntityLiquidTankElectricMachine");
        GameRegistry.registerTileEntity(TileEntityElectricMachine.class, "TileEntityElectricMachine");
        GameRegistry.registerTileEntity(TileEntityCombinerSolidMatter.class, "TileEntityCombinerSolidMatter");
        GameRegistry.registerTileEntity(TileEntityAdvPump.class, "TileEntityAdvPump");
        GameRegistry.registerTileEntity(TileEntityHEEVTransformer.class, "TileEntityHEEVTransformer");

        GameRegistry.registerTileEntity(TileEntityChargepadAdvMFSU.class, "TileEntityChargepadAdvMFSU");

        GameRegistry.registerTileEntity(TileEntityUEVTransformer.class, "TileEntityUEVTransformer");
        GameRegistry.registerTileEntity(TileEntityUHEVTransformer.class, "TileEntityUHEVTransformer");
        GameRegistry.registerTileEntity(TileEntityUHVTransformer.class, "TileEntityUHVTransformer");
        GameRegistry.registerTileEntity(TileEntityUMEVTransformer.class, "TileEntityUMEVTransformer");
        GameRegistry.registerTileEntity(TileEntityUMHVTransformer.class, "TileEntityUMHVTransformer");
        GameRegistry.registerTileEntity(TileEntityUMVTransformer.class, "TileEntityUMVTransformer");
        GameRegistry.registerTileEntity(TileEntityConverterSolidMatter.class, "TileEntityConverterSolidMatter");

        GameRegistry.registerTileEntity(TileEntityWitherMaker.class, "TileEntityWitherMaker");
        GameRegistry.registerTileEntity(TileEntityCombinerMatter.class, "TileEntityCombinerMatter");
        GameRegistry.registerTileEntity(TileEntityFisher.class, "TileEntityFisher");
        GameRegistry.registerTileEntity(TileEntityAnalyzer.class, "TileEntityAnalyzer");
        GameRegistry.registerTileEntity(TileEntityPainting.class, "TileEntityPainting");
        GameRegistry.registerTileEntity(TileEntityUpgradeBlock.class, "TileEntityUpgradeBlock");
        GameRegistry.registerTileEntity(TileEntityLavaGenerator.class, "TileEntityLavaGenerator");


        GameRegistry.registerTileEntity(TileEntityElectricHadrMFSU.class, "TileEntityElectricHadrMFSU");
        GameRegistry.registerTileEntity(TileEntityElectricGraMFSU.class, "TileEntityElectricGraMFSU");
        GameRegistry.registerTileEntity(TileEntityElectricKvrMFSU.class, "TileEntityElectricKvrMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadPerMFSU.class, "TileEntityChargepadPerMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadUltMFSU.class, "TileEntityChargepadUltMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadBarMFSU.class, "TileEntityChargepadBarMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadHadrMFSU.class, "TileEntityChargepadHadrMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadGraMFSU.class, "TileEntityChargepadGraMFSU");
        GameRegistry.registerTileEntity(TileEntityChargepadKvrMFSU.class, "TileEntityChargepadKvrMFSU");

        GameRegistry.registerTileEntity(TileEntityGeneratorAdv.class, "TileEntityGeneratorAdv");
        GameRegistry.registerTileEntity(TileEntityGeneratorImp.class, "TileEntityGeneratorImp");
        GameRegistry.registerTileEntity(TileEntityGeneratorPer.class, "TileEntityGeneratorPer");

        GameRegistry.registerTileEntity(TileEntityDoubleCombRecycler.class, "TileEntityDoubleCombRecycler");
        GameRegistry.registerTileEntity(TileEntityTripleCombRecycler.class, "TileEntityTripleCombRecycler");
        GameRegistry.registerTileEntity(TileEntityQuadCombRecycler.class, "TileEntityQuadCombRecycler");
        GameRegistry.registerTileEntity(TileEntityAdvAlloySmelter.class, "TileEntityAdvAlloySmelter");

        GameRegistry.registerTileEntity(TileEntityAdvGeoGenerator.class, "TileEntityAdvGeoGenerator");
        GameRegistry.registerTileEntity(TileEntityImpGeoGenerator.class, "TileEntityImpGeoGenerator");
        GameRegistry.registerTileEntity(TileEntityPerGeoGenerator.class, "TileEntityPerGeoGenerator");
        GameRegistry.registerTileEntity(TileEntityHandlerHeavyOre.class, "TileEntityHandlerHeavyOre");
        GameRegistry.registerTileEntity(TileEntityAdvOilRefiner.class, "TileEntityAdvOilRefiner");
        GameRegistry.registerTileEntity(TileEntityPlasticCreator.class, "TileEntityPlasticCreator");


        GameRegistry.registerTileEntity(TileEntitySynthesis.class, "TileEntitySynthesis");

        GameRegistry.registerTileEntity(TileEntityAdvNuclearReactorElectric.class, "TileEntityAdvNuclearReactorElectric");
        GameRegistry.registerTileEntity(TileEntityImpNuclearReactor.class, "TileEntityImpNuclearReactor");
        GameRegistry.registerTileEntity(TileEntityPerNuclearReactor.class, "TileEntityPerNuclearReactor");
        GameRegistry.registerTileEntity(TileEntityAdvReactorChamberElectric.class, "TileEntityAdvReactorChamberElectric");
        GameRegistry.registerTileEntity(TileEntityEnrichment.class, "TileEntityEnrichment");
        GameRegistry.registerTileEntity(TileEntityRadiationPurifier.class, "TileEntityRadiationPurifier");

        GameRegistry.registerTileEntity(TileEntityAdvQuantumQuarry.class, "TileEntityAdvQuantumQuarry");
        GameRegistry.registerTileEntity(TileEntityImpQuantumQuarry.class, "TileEntityImpQuantumQuarry");
        GameRegistry.registerTileEntity(TileEntityPerQuantumQuarry.class, "TileEntityPerQuantumQuarry");
        GameRegistry.registerTileEntity(TileSunnariumMaker.class, "TileSunnariumMaker");
        GameRegistry.registerTileEntity(TileEntitySunnariumPanelMaker.class, "TileEntitySunnariumPanelMaker");
        GameRegistry.registerTileEntity(TileEntityPlasticPlateCreator.class, "TileEntityPlasticPlateCreator");
        GameRegistry.registerTileEntity(TileEntityHeliumGenerator.class, "TileEntityHeliumGenerator");

        GameRegistry.registerTileEntity(TileEntityTank.class, "TileEntityTank");
        GameRegistry.registerTileEntity(TileEntityAdvTank.class, "TileEntityAdvTank");
        GameRegistry.registerTileEntity(TileEntityImpTank.class, "TileEntityImpTank");
        GameRegistry.registerTileEntity(TileEntityPerTank.class, "TileEntityPerTank");
        GameRegistry.registerTileEntity(TileEntityVein.class, "TileEntityVein");
        GameRegistry.registerTileEntity(TileEntityElectricHeat.class, "TileEntityElectricHeat");

        GameRegistry.registerTileEntity(TileEntityFluidHeat.class, "TileEntityFluidHeat");

    }
}
