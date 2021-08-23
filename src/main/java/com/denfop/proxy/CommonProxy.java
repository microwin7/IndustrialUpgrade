package com.denfop.proxy;


import com.denfop.Config;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.events.IUEventHandler;
import com.denfop.events.de_mf.IUDEMFEventHandler;
import com.denfop.events.de_mf_ep.IUMFDEEventHandler;
import com.denfop.events.draconic.IUDEEventHandler;
import com.denfop.events.ep.IUEPEventHandler;
import com.denfop.events.ep_de.IUDEEPEventHandler;
import com.denfop.events.mf.IUMFEventHandler;
import com.denfop.events.mf_ep.IUMPMFEventHandler;
import com.denfop.integration.avaritia.AvaritiaIntegration;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.integration.crafttweaker.CTCore;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import com.denfop.integration.thaumcraft.ThaumcraftIntegration;
import com.denfop.integration.thaumtinker.ThaumTinkerIntegration;
import com.denfop.item.modules.EnumModule;
import com.denfop.recipes.*;
import com.denfop.render.cable.RenderBlock;
import com.denfop.tiles.base.*;
import com.denfop.tiles.mechanism.*;
import com.denfop.tiles.neutroniumgenerator.TileneutronGenerator;
import com.denfop.tiles.reactors.TileEntityAdvNuclearReactorElectric;
import com.denfop.tiles.reactors.TileEntityImpNuclearReactor;
import com.denfop.tiles.reactors.TileEntityPerNuclearReactor;
import com.denfop.tiles.wiring.storage.TileEntityElectricAdvMFSU;
import com.denfop.tiles.wiring.storage.TileEntityElectricUltMFSU;
import com.denfop.utils.CheckHeldItem;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderException;
import cpw.mods.fml.common.network.IGuiHandler;
import ic2.core.block.machine.tileentity.TileEntityMatter;
import modtweaker2.utils.TweakerPlugin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy implements IGuiHandler {

	public boolean isSimulating() {
		return !FMLCommonHandler.instance().getEffectiveSide().isClient();
	}

	public int getRenderId(String name) {
		return -1;
	}

	public RenderBlock getRender(String name) {
		return null;
	}


	public void registerRenderers() {
	}

		public void registerEvents() {

		
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

	public void profilerStartSection(final String section) {
	}
	public void profilerEndStartSection(final String section) {
	}
	public void profilerEndSection() {
	}

	public void load() {
	}

	public Object getServerGuiElement(final int ID, final EntityPlayer player, final World world, final int X,
			final int Y, final int Z) {
		final TileEntity te = world.getTileEntity(X, Y, Z);

		if(ID == 4){
			if(player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == IUItem.quantumBodyarmor)
				return null;
		}
		if (te == null) {
			return null;
		}
		boolean wrench = CheckHeldItem.gettrue1(player);

		if(!wrench) {

		if (te instanceof TileEntitySolarPanel) {
			return ((TileEntitySolarPanel) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityBaseGenerator) {
				return ((TileEntityBaseGenerator) te).getGuiContainer(player);
			}
		if (te instanceof TileMatterGenerator) {
			return ((TileMatterGenerator) te).getGuiContainer(player);
		}
		if (te instanceof TileSintezator) {
			return ((TileSintezator) te).getGuiContainer(player);
		}

			if (te instanceof TileEntityDoubleMolecular) {
				return ((TileEntityDoubleMolecular) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityMolecularTransformer) {
			return ((TileEntityMolecularTransformer) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityGeoGenerator) {
				return ((TileEntityGeoGenerator) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityFisher) {
				return ((TileEntityFisher) te).getGuiContainer(player);
			}

			if (te instanceof TileEntityMagnet) {
				return ((TileEntityMagnet) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityEnrichment) {
				return ((TileEntityEnrichment) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityHandlerHeavyOre) {
				return ((TileEntityHandlerHeavyOre) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityPrivatizer) {
				return ((TileEntityPrivatizer) te).getGuiContainer(player);
			}

			if (te instanceof TileEntitySynthesis) {
				return ((TileEntitySynthesis) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityTransformer) {
			return ((TileEntityTransformer) te).getGuiContainer(player);
		}
		if (te instanceof TileEntityConverterSolidMatter) {
			return ((TileEntityConverterSolidMatter) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityAnalyzer) {
				return ((TileEntityAnalyzer) te).getGuiContainer(player);
			}

		//

		if (te instanceof TileEntityMultiMatter) {
			return ((TileEntityMultiMatter) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityCombinerMatter) {
				return ((TileEntityCombinerMatter) te).getGuiContainer(player);
			}

			if (te instanceof TileEntityWitherMaker) {
				return ((TileEntityWitherMaker) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityLavaGenerator) {
				return ((TileEntityLavaGenerator) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityAlloySmelter) {
			return ((TileEntityAlloySmelter) te).getGuiContainer(player);
		}
		if (te instanceof TileEntityElectricAdvMFSU) {
			return ((TileEntityElectricAdvMFSU) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityKineticGenerator) {

				return ((TileEntityKineticGenerator) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityElectricUltMFSU) {
			return ((TileEntityElectricUltMFSU) te).getGuiContainer(player);
		}
		if (te instanceof TileEntityElectricBlock ) {
			return ((TileEntityElectricBlock) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityDoubleElectricMachine ) {
				return ((TileEntityDoubleElectricMachine) te).getGuiContainer(player);
			}

		if (te instanceof TileneutronGenerator) {
			return ((TileneutronGenerator) te).getGuiContainer(player);
		}
			if (te instanceof TileEntityTripleElectricMachine) {
				return ((TileEntityTripleElectricMachine) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityElectricLather) {
				return ((TileEntityElectricLather) te).getGuiContainer(player);
			}

			if (te instanceof TileEntityOilGetter) {
				return ((TileEntityOilGetter) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityGenerationMicrochip) {
			return ((TileEntityGenerationMicrochip) te).getGuiContainer(player);
		}

			if (te instanceof TileEntityElectrolyzer) {
				return ((TileEntityElectrolyzer) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityMultiMachine) {
			return ((TileEntityMultiMachine) te).getGuiContainer(player);
		}

			if (te instanceof TileEntitySunnariumPanelMaker) {
				return ((TileEntitySunnariumPanelMaker) te).getGuiContainer(player);
			}
			if (te instanceof TileEntityUpgradeBlock) {
				return ((TileEntityUpgradeBlock) te).getGuiContainer(player);
			}

			if (te instanceof TileSunnariumMaker)
				return ((TileSunnariumMaker) te).getGuiContainer(player);

			if (te instanceof TileEntityAdvNuclearReactorElectric)
				return ((TileEntityAdvNuclearReactorElectric) te).getGuiContainer(player);
			if (te instanceof TileEntityImpNuclearReactor)
				return ((TileEntityImpNuclearReactor) te).getGuiContainer(player);
			if (te instanceof TileEntityPerNuclearReactor)
				return ((TileEntityPerNuclearReactor) te).getGuiContainer(player);
			if (te instanceof TileEntityModuleMachine)
			return ((TileEntityModuleMachine) te).getGuiContainer(player);

			if (te instanceof TileEntityAdvAlloySmelter) {

				return ((TileEntityAdvAlloySmelter) te).getGuiContainer(player);	}

			if (te instanceof TileSolarGeneratorEnergy) {

			return ((TileSolarGeneratorEnergy) te).getGuiContainer(player);	}
		if (te instanceof TileEntityPainting) {
			return ((TileEntityPainting) te).getGuiContainer(player);
		}

			if (te instanceof TileEntityCombinerSolidMatter) {
				return ((TileEntityCombinerSolidMatter) te).getGuiContainer(player);
			}
		if (te instanceof TileEntityGenerationStone)
			return ((TileEntityGenerationStone) te).getGuiContainer(player);
		if (te instanceof TileEntityBaseQuantumQuarry)
			return ((TileEntityBaseQuantumQuarry) te).getGuiContainer(player);
			if (te instanceof TileEntityDieselGenerator)
				return ((TileEntityDieselGenerator) te).getGuiContainer(player);
			if (te instanceof TileEntityPetrolGenerator)
				return ((TileEntityPetrolGenerator) te).getGuiContainer(player);

			if (te instanceof TileEntityHeliumGenerator)
				return ((TileEntityHeliumGenerator) te).getGuiContainer(player);

			if (te instanceof TileEntityOilRefiner)
				return ((TileEntityOilRefiner) te).getGuiContainer(player);
			if (te instanceof TileEntityPump)
				return ((TileEntityPump) te).getGuiContainer(player);
			if (te instanceof TileEntityStorageExp)
				return ((TileEntityStorageExp) te).getGuiContainer(player);

			if (te instanceof TileEntityAdvOilRefiner)
				return ((TileEntityAdvOilRefiner) te).getGuiContainer(player);

			if (te instanceof TileEntityHydrogenGenerator)
				return ((TileEntityHydrogenGenerator) te).getGuiContainer(player);

			if (te instanceof TileEntityObsidianGenerator)
				return ((TileEntityObsidianGenerator) te).getGuiContainer(player);
			if (te instanceof TileEntityPlasticCreator)
				return ((TileEntityPlasticCreator) te).getGuiContainer(player);
			if (te instanceof TileEntityTunerWireless)
				return ((TileEntityTunerWireless) te).getGuiContainer(player);

			if (te instanceof TileEntityAutoSpawner)
				return ((TileEntityAutoSpawner) te).getGuiContainer(player);

			if (te instanceof TileEntityPlasticPlateCreator)
				return ((TileEntityPlasticPlateCreator) te).getGuiContainer(player);

		}


		return null;
	}

	public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int X,
			final int Y, final int Z) {
		return null;
	}

	public int addArmor(final String armorName) {
		return 0;
	}

	public static void sendPlayerMessage(EntityPlayer player, String message) {
		if (IUCore.isSimulating())
			player.addChatMessage(new ChatComponentTranslation(message));
	}

	public void initCore() {
		TileEntityFermer.init();
		TileEntitySynthesis.init();
		TileEntityHandlerHeavyOre.init();
		TileEntityEnrichment.init();
		TileEntityAlloySmelter.init();
		TileEntityAdvAlloySmelter.init();
		TileEntityCombMacerator.init();
		TileEntityMolecularTransformer.init();
		TileEntityGenerationMicrochip.init();
		TileEntityGenerationStone.init();
		TileEntityConverterSolidMatter.init();
		TileEntityAssamplerScrap.init();
		TileEntityWitherMaker.init();
		TileSunnariumMaker.init();
		TileEntitySunnariumPanelMaker.init();
		TileEntityMatter.addAmplifier(new ItemStack(IUItem.doublescrapBox), 1, 405000);
		TileEntityPainting.init();
		TileEntityUpgradeBlock.init();
		TileEntityDoubleMolecular.init();
		TileEntityObsidianGenerator.init();
		TileEntityPlasticCreator.init();
		TileEntityPlasticPlateCreator.init();
	}

	public static void throwInitException(LoaderException e) {
		throw e;
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

	public void integration() {
		Config.DraconicLoaded = Loader.isModLoaded("DraconicEvolution");
		Config.AvaritiaLoaded = Loader.isModLoaded("Avaritia");
		Config.BotaniaLoaded = Loader.isModLoaded("Botania");
		Config.EnchantingPlus = Loader.isModLoaded("eplus");
		Config.MineFactory = Loader.isModLoaded("MineFactoryReloaded");
		if (Loader.isModLoaded("modtweaker2")) {
			TweakerPlugin.register("industrialupgrade", CTCore.class);

		}
		
		if (Config.DraconicLoaded && Config.Draconic) {
			DraconicIntegration.init();
		}
		if (Config.AvaritiaLoaded && Config.Avaritia) {
			AvaritiaIntegration.init();
		}
		if(Config.thaumcraft && Config.Thaumcraft) {
			ThaumcraftIntegration.init();
			if(Loader.isModLoaded("ThaumicTinkerer"))
				ThaumTinkerIntegration.init();
		}
		if (Config.BotaniaLoaded && Config.Botania) {
			BotaniaIntegration.init();
		}
        if(Loader.isModLoaded("exnihilo"))
			ExNihiloIntegration.init();
	}


}
