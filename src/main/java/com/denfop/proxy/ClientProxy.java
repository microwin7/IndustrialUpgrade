package com.denfop.proxy;

import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUItem;
import com.denfop.container.*;
import com.denfop.events.EventDarkQuantumSuitEffect;
import com.denfop.events.IUEventHandler;
import com.denfop.events.de_mf.IUDEMFEventHandler;
import com.denfop.events.de_mf_ep.IUMFDEEventHandler;
import com.denfop.events.draconic.IUDEEventHandler;
import com.denfop.events.ep.IUEPEventHandler;
import com.denfop.events.ep_de.IUDEEPEventHandler;
import com.denfop.events.mf.IUMFEventHandler;
import com.denfop.events.mf_ep.IUMPMFEventHandler;
import com.denfop.gui.*;
import com.denfop.handler.EntityStreak;
import com.denfop.integration.avaritia.AvaritiaIntegration;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.integration.crafttweaker.CTCore;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import com.denfop.integration.thaumcraft.ThaumcraftIntegration;
import com.denfop.integration.thaumtinker.ThaumTinkerIntegration;
import com.denfop.item.modules.EnumModule;
import com.denfop.recipes.*;
import com.denfop.render.EntityRendererStreak;
import com.denfop.render.SunnariumMaker.TileEntitySunnariumMakerItemRender;
import com.denfop.render.SunnariumMaker.TileEntitySunnariumMakerRender;
import com.denfop.render.SunnariumPanelMaker.TileEntitySunnariumPanelMakerItemRender;
import com.denfop.render.SunnariumPanelMaker.TileEntitySunnariumPanelMakerRender;
import com.denfop.render.advoilrefiner.TileEntityAdvOilRefinerItemRender;
import com.denfop.render.advoilrefiner.TileEntityAdvOilRefinerRender;
import com.denfop.render.cable.RenderBlock;
import com.denfop.render.cable.RenderBlockCable;
import com.denfop.render.cable.RenderBlockWall;
import com.denfop.render.combinersolidmatter.TileEntityCombineSolidMatterItemRender;
import com.denfop.render.combinersolidmatter.TileEntityCombineSolidMatterRender;
import com.denfop.render.convertersolidmatter.TileEntityRenderConverterMatter;
import com.denfop.render.convertersolidmatter.TileEntityRenderItemConverterMatter;
import com.denfop.render.doublemoleculartransformer.TileEntityDoubleMolecularItemRender;
import com.denfop.render.doublemoleculartransformer.TileEntityDoubleMolecularRender;
import com.denfop.render.moleculartransformer.TileEntityMolecularItemRender;
import com.denfop.render.moleculartransformer.TileEntityMolecularRender;
import com.denfop.render.oilgetter.TileEntityOilGetterItemRender;
import com.denfop.render.oilgetter.TileEntityOilGetterRender;
import com.denfop.render.oilquarry.TileEntityQuarryOilItemRender;
import com.denfop.render.oilquarry.TileEntityQuarryOilRender;
import com.denfop.render.oilrefiner.TileEntityOilRefinerItemRender;
import com.denfop.render.oilrefiner.TileEntityOilRefinerRender;
import com.denfop.render.sintezator.TileEntitySintezatorItemRender;
import com.denfop.render.sintezator.TileEntitySintezatorRender;
import com.denfop.render.solargenerator.*;
import com.denfop.render.tile.TileEntityPanelItemRender;
import com.denfop.render.tile.TileEntityPanelRender;
import com.denfop.render.upgradeblock.TileEntityUpgradeBlockItemRender;
import com.denfop.render.upgradeblock.TileEntityUpgradeBlockRender;
import com.denfop.tiles.base.*;
import com.denfop.tiles.mechanism.*;
import com.denfop.tiles.neutroniumgenerator.TileneutronGenerator;
import com.denfop.tiles.reactors.TileEntityAdvNuclearReactorElectric;
import com.denfop.tiles.reactors.TileEntityImpNuclearReactor;
import com.denfop.tiles.reactors.TileEntityPerNuclearReactor;
import com.denfop.tiles.se.TileAdvSolarGenerator;
import com.denfop.tiles.se.TileImpSolarGenerator;
import com.denfop.tiles.se.TileSolarGenerator;
import com.denfop.tiles.sintezator.TileEntitySintezator;
import com.denfop.tiles.wiring.storage.TileEntityElectricAdvMFSU;
import com.denfop.utils.CheckHeldItem;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import modtweaker2.utils.TweakerPlugin;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy  {

	Map<String, RenderBlock> renders;

	public RenderBlock getRender(String name) {
		return this.renders.get(name);
	}


	public static int[][] sideAndFacingToSpriteOffset;


	@Override
	public void load() {


		try {
			sideAndFacingToSpriteOffset = (int[][]) Class.forName("ic2.core.block.BlockMultiID")
					.getField("sideAndFacingToSpriteOffset").get(null);
		} catch (Exception e) {
			sideAndFacingToSpriteOffset = new int[][] { { 3, 2, 0, 0, 0, 0 }, { 2, 3, 1, 1, 1, 1 },
					{ 1, 1, 3, 2, 5, 4 }, { 0, 0, 2, 3, 4, 5 }, { 4, 5, 4, 5, 3, 2 }, { 5, 4, 5, 4, 2, 3 } };
		}
	}

	private void addBlockRenderer(String name, RenderBlock renderer) {
		RenderingRegistry.registerBlockHandler(renderer);
		this.renders.put(name, renderer);
	}

	public void integration() {
		Config.DraconicLoaded = Loader.isModLoaded("DraconicEvolution");
		Config.AvaritiaLoaded = Loader.isModLoaded("Avaritia");
		Config.BotaniaLoaded = Loader.isModLoaded("Botania");
		Config.EnchantingPlus = Loader.isModLoaded("eplus");
		Config.MineFactory = Loader.isModLoaded("MineFactoryReloaded");

		if (Loader.isModLoaded("modtweaker2")) {
			TweakerPlugin.register(Constants.MOD_ID, CTCore.class);

		}
		if (Config.DraconicLoaded && Config.Draconic) {
			DraconicIntegration.init();
		}
		if(Config.thaumcraft && Config.Thaumcraft)
			ThaumcraftIntegration.init();
		if (Config.AvaritiaLoaded && Config.Avaritia) {
			AvaritiaIntegration.init();
		}

		if (Config.BotaniaLoaded && Config.Botania) {
			BotaniaIntegration.init();
		}
		if(Loader.isModLoaded("exnihilo"))
			ExNihiloIntegration.init();
		if(Loader.isModLoaded("ThaumicTinkerer"))
			ThaumTinkerIntegration.init();
	}

	public void initCore() {
		TileEntityAssamplerScrap.init();
		TileEntityHandlerHeavyOre.init();
		TileEntityFermer.init();
		TileEntityEnrichment.init();
		TileEntitySynthesis.init();
		TileEntityAlloySmelter.init();
		TileEntityAdvAlloySmelter.init();
		TileEntityCombMacerator.init();
		TileEntityMolecularTransformer.init();
		TileEntityGenerationMicrochip.init();
		TileEntityGenerationStone.init();
		TileEntityConverterSolidMatter.init();
		TileEntityWitherMaker.init();
		TileSunnariumMaker.init();
		TileEntityPainting.init();
		TileEntitySunnariumPanelMaker.init();
		TileEntityUpgradeBlock.init();
		TileEntityMatter.addAmplifier(new ItemStack(IUItem.doublescrapBox), 1, 405000);
		TileEntityDoubleMolecular.init();
		TileEntityObsidianGenerator.init();
		TileEntityPlasticCreator.init();
		TileEntityPlasticPlateCreator.init();
	}

	public void registerRenderers() {
		RenderingRegistry.registerEntityRenderingHandler(EntityStreak.class, new EntityRendererStreak());
		//

		//

		this.renders = new HashMap<>();
		addBlockRenderer("cable", new RenderBlockCable());
		addBlockRenderer("wall",  new RenderBlockWall());
		if (Config.DraconicLoaded)
			DraconicIntegration.render();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdminSolarPanel.class, new TileEntityPanelRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.blockadmin),
				new TileEntityPanelItemRender());
		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySintezator.class, new TileEntitySintezatorRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.blocksintezator),
				new TileEntitySintezatorItemRender());
		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileSunnariumMaker.class, new TileEntitySunnariumMakerRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.sunnariummaker),
				new TileEntitySunnariumMakerItemRender());
		//

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySunnariumPanelMaker.class, new TileEntitySunnariumPanelMakerRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.sunnariumpanelmaker),
				new TileEntitySunnariumPanelMakerItemRender());

		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUpgradeBlock.class, new TileEntityUpgradeBlockRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.upgradeblock),
				new TileEntityUpgradeBlockItemRender());

		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuarryOil.class, new TileEntityQuarryOilRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.oilquarry),
				new TileEntityQuarryOilItemRender());

		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilRefiner.class, new TileEntityOilRefinerRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.oilrefiner),
				new TileEntityOilRefinerItemRender());

		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolecularTransformer.class, new TileEntityMolecularRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.blockmolecular),
				new TileEntityMolecularItemRender());
		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoubleMolecular.class, new TileEntityDoubleMolecularRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.blockdoublemolecular),
				new TileEntityDoubleMolecularItemRender());

		//

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvOilRefiner.class, new TileEntityAdvOilRefinerRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.oiladvrefiner),
				new TileEntityAdvOilRefinerItemRender());

		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilGetter.class, new TileEntityOilGetterRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.oilgetter),
				new TileEntityOilGetterItemRender());


		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCombinerSolidMatter.class, new TileEntityCombineSolidMatterRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.combinersolidmatter),
				new TileEntityCombineSolidMatterItemRender());


		//
		ClientRegistry.bindTileEntitySpecialRenderer(TileSolarGenerator.class, new TileEntitySolarEnergyRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.blockSE),
				new TileEntitySolarEnergyItemRender());
		ClientRegistry.bindTileEntitySpecialRenderer(TileAdvSolarGenerator.class, new TileEntityAdvSolarEnergyRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.AdvblockSE),
				new TileEntityAdvSolarEnergyItemRender());
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileImpSolarGenerator.class, new TileEntityImpSolarEnergyRender());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.ImpblockSE),
				new TileEntityImpSolarEnergyItemRender());
			ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConverterSolidMatter.class, new TileEntityRenderConverterMatter());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.convertersolidmatter),
				new TileEntityRenderItemConverterMatter());


	}

	public void registerRecipe() {

		if (Config.BotaniaLoaded && Config.Botania)
			BotaniaIntegration.recipe();
		BasicRecipe.recipe();
		if (Config.DraconicLoaded && Config.Draconic)
			DraconicIntegration.Recipes();
		if (Config.AvaritiaLoaded && Config.Avaritia)
			AvaritiaIntegration.recipe();

		CompressorRecipe.recipe();
		CannerRecipe.recipe();
		FurnaceRecipes.recipe();
		CentrifugeRecipe.init();
		MaceratorRecipe.recipe();
		MetalFormerRecipe.init();
		EnumModule.register();
	}

	public void registerEvents() {
		MinecraftForge.EVENT_BUS.register(new EventDarkQuantumSuitEffect());
		if (Config.Streak) {
			FMLCommonHandler.instance().bus().register(new EventDarkQuantumSuitEffect());
		}
		

		if (Config.DraconicLoaded && Config.EnchantingPlus && Config.MineFactory) {
			MinecraftForge.EVENT_BUS.register(new IUMFDEEventHandler());

		} else if (Config.DraconicLoaded && Config.EnchantingPlus) {
			MinecraftForge.EVENT_BUS.register(new IUDEEPEventHandler());
		} else if (Config.DraconicLoaded && Config.MineFactory) {
			MinecraftForge.EVENT_BUS.register(new IUDEMFEventHandler());
		} else if (Config.EnchantingPlus && Config.MineFactory) {
			MinecraftForge.EVENT_BUS.register(new IUMPMFEventHandler());
		} else {
			if (Config.DraconicLoaded) {
				MinecraftForge.EVENT_BUS.register(new IUDEEventHandler());
			}

			if (Config.EnchantingPlus) {
				MinecraftForge.EVENT_BUS.register(new IUEPEventHandler());
			}
			if (Config.MineFactory) {
				MinecraftForge.EVENT_BUS.register(new IUMFEventHandler());
			}
		}
		IUEventHandler sspEventHandler = new IUEventHandler();
		MinecraftForge.EVENT_BUS.register(sspEventHandler);
		FMLCommonHandler.instance().bus().register(sspEventHandler);
	}
	@Override
	public void profilerEndStartSection(final String section) {
		if (this.isRendering()) {
			Minecraft.getMinecraft().mcProfiler.endStartSection(section);
		}
		else {
			super.profilerEndStartSection(section);
		}
	}

	@Override
	public void profilerEndSection() {
		if (this.isRendering()) {
			Minecraft.getMinecraft().mcProfiler.endSection();
		}
		else {
			super.profilerEndSection();
		}
	}

	@Override
	public void profilerStartSection(final String section) {
		if (this.isRendering()) {
			Minecraft.getMinecraft().mcProfiler.startSection(section);
		}
		else {
			super.profilerStartSection(section);
		}
	}
	public boolean isRendering() {
		return !this.isSimulating();
	}

	@Override
	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int X,
			final int Y, final int Z) {
		final TileEntity te = world.getTileEntity(X, Y, Z);
	    if(ID == 4){
	    	if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == IUItem.quantumBodyarmor)
	    		return  new GuiColorPicker(player);
		}
		if (te == null) {
			return null;
		}
		 boolean wrench = CheckHeldItem.gettrue1(player);


		if(!wrench) {
			if (te instanceof TileEntitySolarPanel) {

				return new GuiSolarPanels(new ContainerSolarPanels(player, (TileEntitySolarPanel) te));
			}
			if (te instanceof TileSintezator) {
				return new GUISintezator(new ContainerSinSolarPanel<>(player, (TileSintezator) te));
			}
			if (te instanceof TileEntityKineticGenerator) {
				return new GuiKineticGenerator(new ContainerKineticGenerator(player, (TileEntityKineticGenerator) te));
			}

			if (te instanceof TileEntityFisher) {
				return new GuiFisher(new ContainerFisher<>(player, (TileEntityFisher) te));
			}


			if (te instanceof TileEntityMolecularTransformer) {
				return new GuiMolecularTransformer(new ContainerBaseMolecular<>(player, (TileEntityMolecularTransformer) te));
			}
			if (te instanceof TileEntityDoubleMolecular) {
				return new GuiDoubleMolecularTransformer(new ContainerBaseDoubleMolecular<>(player, (TileEntityDoubleMolecular) te));
			}
			if (te instanceof TileEntityMultiMachine) {
				return ((TileEntityMultiMachine) te).getGui(player, false);
			}
			if (te instanceof TileEntityPlasticPlateCreator) {
				return new GuiPlasticPlateCreator(new ContainerPlasticPlateCreator<>(player, (TileEntityPlasticPlateCreator) te));
			}

			if (te instanceof TileEntityHeliumGenerator) {
				return ((TileEntityHeliumGenerator) te).getGui(player, false);
			}

			if (te instanceof TileEntityPump) {
				return ((TileEntityPump) te).getGui(player, false);
			}
			if (te instanceof TileEntityStorageExp) {
				return ((TileEntityStorageExp) te).getGui(player, false);
			}
			if (te instanceof TileEntityCombinerSolidMatter) {
				return ((TileEntityCombinerSolidMatter) te).getGui(player, false);
			}
			if (te instanceof TileEntityCombinerMatter) {
				return ((TileEntityCombinerMatter) te).getGui(player, false);
			}
				if (te instanceof TileEntityMultiMatter) {
					return ((TileEntityMultiMatter) te).getGui(player, false);
				}
			if (te instanceof TileEntityBaseGenerator) {
				return new GuiGenerator(new ContainerGenerator<>(player, (TileEntityBaseGenerator) te));
			}

			if (te instanceof TileEntityMagnet) {
				return new GuiMagnet(new ContainerMagnet<>(player, (TileEntityMagnet) te));
			}
				if (te instanceof TileSunnariumMaker) {
					return new GuiSunnariumMaker(new ContainerSunnariumMaker<>(player, (TileSunnariumMaker) te));
				}



			if (te instanceof TileEntityElectrolyzer)
				return new GuiElectrolyzer(new ContainerElectrolyzer(player, (TileEntityElectrolyzer) te));

			if (te instanceof TileEntityWitherMaker)
					return new GuiWitherMaker(new ContainerBaseWitherMaker<>(player, (TileEntityWitherMaker) te));

			if (te instanceof TileEntityPlasticCreator)
				return new GuiPlasticCreator(new ContainerPlasticCreator<>(player, (TileEntityPlasticCreator) te));

			if (te instanceof TileEntityDoubleElectricMachine)
				return ((TileEntityDoubleElectricMachine) te).getGui(player, false);
			if (te instanceof TileEntityTripleElectricMachine)
				return ((TileEntityTripleElectricMachine) te).getGui(player, false);
			if (te instanceof TileEntityElectricLather)
				return ((TileEntityElectricLather) te).getGui(player, false);
			if (te instanceof TileEntityPrivatizer)
				return ((TileEntityPrivatizer) te).getGui(player, false);
			if (te instanceof TileEntityTunerWireless)
				return ((TileEntityTunerWireless) te).getGui(player, false);

			if (te instanceof TileEntityAutoSpawner)
				return ((TileEntityAutoSpawner) te).getGui(player, false);


			if (te instanceof TileEntityLavaGenerator)
				return new GuiLavaGenerator(new ContainerLavaGenerator(player, (TileEntityLavaGenerator) te));

				if (te instanceof TileEntityHandlerHeavyOre)
					return new GuiHandlerHeavyOre(new ContainerHandlerHeavyOre<>(player, (TileEntityHandlerHeavyOre) te));

			if (te instanceof TileEntityHydrogenGenerator)
				return new GuiHydrogenGenerator(new ContainerHydrogenGenerator(player, (TileEntityHydrogenGenerator) te));
			if (te instanceof TileEntityObsidianGenerator)
				return new GuiObsidianGenerator(new ContainerObsidianGenerator(player, (TileEntityObsidianGenerator) te));

			if (te instanceof TileEntityAnalyzer)
				return new GUIAnalyzer(new ContainerAnalyzer<>(player, (TileEntityAnalyzer) te));

			if (te instanceof TileEntityAdvNuclearReactorElectric)
					return new GuiNuclearReactor(new ContainerNuclearReactor(player, (TileEntityAdvNuclearReactorElectric) te));
				if (te instanceof TileEntityImpNuclearReactor)
					return new GuiImpNuclearReactor(new ContainerImpNuclearReactor(player, (TileEntityImpNuclearReactor) te));

				if (te instanceof TileEntityPerNuclearReactor)
					return new GuiPerNuclearReactor(new ContainerPerNuclearReactor(player, (TileEntityPerNuclearReactor) te));


				if (te instanceof TileEntityGeoGenerator) {
				return new GuiGeoGenerator(new ContainerGeoGenerator(player, (TileEntityGeoGenerator) te));
			}
			if (te instanceof TileEntityOilRefiner) {
				return new GuiOilRefiner(new ContainerOilRefiner(player, (TileEntityOilRefiner) te));
			}
			if (te instanceof TileEntityAdvOilRefiner) {
				return new GuiAdvOilRefiner(new ContainerAdvOilRefiner(player, (TileEntityAdvOilRefiner) te));
			}

			if (te instanceof TileEntityDieselGenerator) {
				return new GuiDieselGenerator(new ContainerDieselGenerator(player, (TileEntityDieselGenerator) te));
			}
			if (te instanceof TileEntityPetrolGenerator) {
				return new GuiPetrolGenerator(new ContainerPetrolGenerator(player, (TileEntityPetrolGenerator) te));
			}

			if (te instanceof TileEntityElectricAdvMFSU) {

				return new GuiElectricBlock(new ContainerElectricBlock(player, (TileEntityElectricAdvMFSU) te));
			}

			if (te instanceof TileEntityElectricBlock  ) {

				return new GuiElectricBlock(new ContainerElectricBlock(player, (TileEntityElectricBlock) te));
			}
			if (te instanceof TileneutronGenerator) {

				return new GuiNeutronGenerator(new ContainerNeutroniumGenerator(player, (TileneutronGenerator) te));
			}
			if (te instanceof TileEntityGenerationMicrochip) {
				return new GuiGenerationMicrochip(new ContainerBaseGenerationChipMachine<>(player, (TileEntityGenerationMicrochip) te));
			}
			if (te instanceof TileEntityConverterSolidMatter) 
				return new GUIConverterSolidMatter(new ContainerConverterSolidMatter(player, (TileEntityConverterSolidMatter) te));
			
			if (te instanceof TileEntityTransformer) {

				return new GuiTransformer(new ContainerTransformer(player, (TileEntityTransformer) te));
			}
			if (te instanceof TileEntityOilGetter) {

				return new GUIOilGetter(new ContainerOilGetter(player, (TileEntityOilGetter) te));
			}
			if (te instanceof TileMatterGenerator) {

				return new GUISolidMatter(new ContainerSolidMatter<>(player, (TileMatterGenerator) te));
			}
			if (te instanceof TileSolarGeneratorEnergy) {

				return new GuiSolarGeneratorEnergy(new ContainerSolarGeneratorEnergy(player, (TileSolarGeneratorEnergy) te));
			}
			if (te instanceof TileEntityModuleMachine)
				return new GUIModuleMachine(new ContainerModuleMachine<>(player, (TileEntityModuleMachine) te));

			if (te instanceof TileEntityGenerationStone)
				return new GuiGenStone(new ContainerGenStone<>(player, (TileEntityGenerationStone) te));
			if (te instanceof TileEntityBaseQuantumQuarry)
				return new GuiQuantumQuarry(new ContainerQuantumQuarry<>(player, (TileEntityBaseQuantumQuarry) te));
		}
		
		return null;
	}

	@Override
	public int addArmor(final String armorName) {
		return RenderingRegistry.addNewArmourRendererPrefix(armorName);
	}

	public int getRenderId(String name) {
		return this.renders.get(name).getRenderId();
	}
}
