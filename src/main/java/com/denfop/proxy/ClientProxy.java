package com.denfop.proxy;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.block.base.BlocksItems;
import com.denfop.container.*;
import com.denfop.entity.EntityStreak;
import com.denfop.events.EventDarkQuantumSuitEffect;
import com.denfop.gui.*;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.render.EntityRendererStreak;
import com.denfop.render.SunnariumMaker.TileEntitySunnariumMakerItemRender;
import com.denfop.render.SunnariumMaker.TileEntitySunnariumMakerRender;
import com.denfop.render.SunnariumPanelMaker.TileEntitySunnariumPanelMakerItemRender;
import com.denfop.render.SunnariumPanelMaker.TileEntitySunnariumPanelMakerRender;
import com.denfop.render.advoilrefiner.TileEntityAdvOilRefinerItemRender;
import com.denfop.render.advoilrefiner.TileEntityAdvOilRefinerRender;
import com.denfop.render.cable.RenderBlock;
import com.denfop.render.cable.RenderBlockCable;
import com.denfop.render.combinersolidmatter.TileEntityCombineSolidMatterItemRender;
import com.denfop.render.combinersolidmatter.TileEntityCombineSolidMatterRender;
import com.denfop.render.convertersolidmatter.TileEntityRenderConverterMatter;
import com.denfop.render.convertersolidmatter.TileEntityRenderItemConverterMatter;
import com.denfop.render.doublemoleculartransformer.TileEntityDoubleMolecularItemRender;
import com.denfop.render.doublemoleculartransformer.TileEntityDoubleMolecularRender;
import com.denfop.render.error.TileEntityErrorItemRender;
import com.denfop.render.error.TileEntityErrorRender;
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
import com.denfop.render.tank.TileEntityTankItemRender;
import com.denfop.render.tank.TileEntityTankRender;
import com.denfop.render.tile.TileEntityPanelItemRender;
import com.denfop.render.tile.TileEntityPanelRender;
import com.denfop.render.upgradeblock.TileEntityUpgradeBlockItemRender;
import com.denfop.render.upgradeblock.TileEntityUpgradeBlockRender;
import com.denfop.tiles.base.*;
import com.denfop.tiles.mechanism.*;
import com.denfop.tiles.neutroniumgenerator.TileneutronGenerator;
import com.denfop.tiles.reactors.TileEntityBaseNuclearReactorElectric;
import com.denfop.tiles.se.TileAdvSolarGenerator;
import com.denfop.tiles.se.TileImpSolarGenerator;
import com.denfop.tiles.se.TileSolarGenerator;
import com.denfop.tiles.sintezator.TileEntitySintezator;
import com.denfop.tiles.wiring.storage.TileEntityElectricAdvMFSU;
import com.denfop.utils.CheckWrench;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class ClientProxy extends CommonProxy {

    public static int[][] sideAndFacingToSpriteOffset;
    Map<String, RenderBlock> renders;

    public RenderBlock getRender(String name) {
        return this.renders.get(name);
    }

    @Override
    public void load() {
        MinecraftForge.EVENT_BUS.register(new BlocksItems());

        try {
            sideAndFacingToSpriteOffset = (int[][]) Class.forName("ic2.core.block.BlockMultiID")
                    .getField("sideAndFacingToSpriteOffset").get(null);
        } catch (Exception e) {
            sideAndFacingToSpriteOffset = new int[][]{{3, 2, 0, 0, 0, 0}, {2, 3, 1, 1, 1, 1},
                    {1, 1, 3, 2, 5, 4}, {0, 0, 2, 3, 4, 5}, {4, 5, 4, 5, 3, 2}, {5, 4, 5, 4, 2, 3}};
        }
    }

    private void addBlockRenderer(String name, RenderBlock renderer) {
        RenderingRegistry.registerBlockHandler(renderer);
        this.renders.put(name, renderer);
    }

    public void integration() {
        super.integration();
    }

    public void initCore() {
        super.initCore();
    }

    public void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(EntityStreak.class, new EntityRendererStreak());
        this.renders = new HashMap<>();
        addBlockRenderer("cable", new RenderBlockCable());
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuarryVein.class, new TileEntityQuarryOilRender());
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLiquedTank.class, new TileEntityTankRender());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.tank),
                new TileEntityTankItemRender());

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

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityError.class, new TileEntityErrorRender());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(IUItem.errorblock),
                new TileEntityErrorItemRender());

    }

    public void registerRecipe() {
        super.registerRecipe();

    }

    public void registerEvents() {
        super.registerEvents();
        MinecraftForge.EVENT_BUS.register(new EventDarkQuantumSuitEffect());
        if (Config.Streak) {
            FMLCommonHandler.instance().bus().register(new EventDarkQuantumSuitEffect());
        }


    }

    @Override
    public void profilerEndStartSection(final String section) {
        if (this.isRendering()) {
            Minecraft.getMinecraft().mcProfiler.endStartSection(section);
        } else {
            super.profilerEndStartSection(section);
        }
    }

    @Override
    public void profilerEndSection() {
        if (this.isRendering()) {
            Minecraft.getMinecraft().mcProfiler.endSection();
        } else {
            super.profilerEndSection();
        }
    }

    @Override
    public void profilerStartSection(final String section) {
        if (this.isRendering()) {
            Minecraft.getMinecraft().mcProfiler.startSection(section);
        } else {
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

        if (ID == 4) {
            if (player.inventory.armorInventory[2] != null && player.inventory.armorInventory[2].getItem() == IUItem.quantumBodyarmor)
                return new GUIColorPicker(player);
        }
        if (te == null) {
            return null;
        }
        boolean wrench = CheckWrench.getwrench(player);


        if (!wrench) {
            if (te instanceof TileEntitySolarPanel) {

                return new GUISolarPanels(new ContainerSolarPanels(player, (TileEntitySolarPanel) te));
            }
            if (te instanceof TileSintezator) {
                return new GUISintezator(new ContainerSinSolarPanel<>(player, (TileSintezator) te));
            }
            if (te instanceof TileEntityKineticGenerator) {
                return new GUIKineticGenerator(new ContainerKineticGenerator(player, (TileEntityKineticGenerator) te));
            }

            if (te instanceof TileEntityFisher) {
                return new GUIFisher(new ContainerFisher<>(player, (TileEntityFisher) te));
            }


            if (te instanceof TileEntityMolecularTransformer) {
                return new GUIMolecularTransformer(new ContainerBaseMolecular<>(player, (TileEntityMolecularTransformer) te));
            }
            if (te instanceof TileEntityDoubleMolecular) {
                return new GUIDoubleMolecularTransformer(new ContainerBaseDoubleMolecular<>(player, (TileEntityDoubleMolecular) te));
            }
            if (te instanceof TileEntityMultiMachine) {
                return ((TileEntityMultiMachine) te).getGui(player, false);
            }
            if (te instanceof TileEntityPlasticPlateCreator) {
                return new GUIPlasticPlateCreator(new ContainerPlasticPlateCreator<>(player, (TileEntityPlasticPlateCreator) te));
            }

            if (te instanceof TileEntityHeliumGenerator) {
                return ((TileEntityHeliumGenerator) te).getGui(player, false);
            }
            if (te instanceof TileEntityLiquedTank) {
                return ((TileEntityLiquedTank) te).getGui(player, false);
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
                return new GUIGenerator(new ContainerGenerator<>(player, (TileEntityBaseGenerator) te));
            }

            if (te instanceof TileEntityMagnet) {
                return new GUIMagnet(new ContainerMagnet<>(player, (TileEntityMagnet) te));
            }
            if (te instanceof TileSunnariumMaker) {
                return new GUISunnariumMaker(new ContainerSunnariumMaker<>(player, (TileSunnariumMaker) te));
            }


            if (te instanceof TileEntityElectrolyzer)
                return new GUIElectrolyzer(new ContainerElectrolyzer(player, (TileEntityElectrolyzer) te));

            if (te instanceof TileEntityWitherMaker)
                return new GUIWitherMaker(new ContainerBaseWitherMaker<>(player, (TileEntityWitherMaker) te));

            if (te instanceof TileEntityPlasticCreator)
                return new GUIPlasticCreator(new ContainerPlasticCreator<>(player, (TileEntityPlasticCreator) te));

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
                return new GUILavaGenerator(new ContainerLavaGenerator(player, (TileEntityLavaGenerator) te));

            if (te instanceof TileEntityHandlerHeavyOre)
                return new GUIHandlerHeavyOre(new ContainerHandlerHeavyOre<>(player, (TileEntityHandlerHeavyOre) te));

            if (te instanceof TileEntityHydrogenGenerator)
                return new GUIHydrogenGenerator(new ContainerHydrogenGenerator(player, (TileEntityHydrogenGenerator) te));
            if (te instanceof TileEntityObsidianGenerator)
                return new GUIObsidianGenerator(new ContainerObsidianGenerator(player, (TileEntityObsidianGenerator) te));

            if (te instanceof TileEntityAnalyzer)
                return new GUIAnalyzer(new ContainerAnalyzer<>(player, (TileEntityAnalyzer) te));

            if (te instanceof TileEntityBaseNuclearReactorElectric)
                return new GUINuclearReactor(new ContainerBaseNuclearReactor(player, (TileEntityBaseNuclearReactorElectric) te));

            if (te instanceof TileEntityGeoGenerator) {
                return new GUIGeoGenerator(new ContainerGeoGenerator(player, (TileEntityGeoGenerator) te));
            }
            if (te instanceof TileEntityOilRefiner) {
                return new GUIOilRefiner(new ContainerOilRefiner(player, (TileEntityOilRefiner) te));
            }
            if (te instanceof TileEntityAdvOilRefiner) {
                return new GUIAdvOilRefiner(new ContainerAdvOilRefiner(player, (TileEntityAdvOilRefiner) te));
            }

            if (te instanceof TileEntityDieselGenerator) {
                return new GUIDieselGenerator(new ContainerDieselGenerator(player, (TileEntityDieselGenerator) te));
            }
            if (te instanceof TileEntityPetrolGenerator) {
                return new GUIPetrolGenerator(new ContainerPetrolGenerator(player, (TileEntityPetrolGenerator) te));
            }

            if (te instanceof TileEntityElectricAdvMFSU) {

                return new GUIElectricBlock(new ContainerElectricBlock(player, (TileEntityElectricAdvMFSU) te));
            }

            if (te instanceof TileEntityElectricBlock) {

                return new GUIElectricBlock(new ContainerElectricBlock(player, (TileEntityElectricBlock) te));
            }
            if (te instanceof TileneutronGenerator) {

                return new GUINeutronGenerator(new ContainerNeutroniumGenerator(player, (TileneutronGenerator) te));
            }
            if (te instanceof TileEntityGenerationMicrochip) {
                return new GUIGenerationMicrochip(new ContainerBaseGenerationChipMachine<>(player, (TileEntityGenerationMicrochip) te));
            }
            if (te instanceof TileEntityConverterSolidMatter)
                return new GUIConverterSolidMatter(new ContainerConverterSolidMatter(player, (TileEntityConverterSolidMatter) te));

            if (te instanceof TileEntityTransformer) {

                return new GUITransformer(new ContainerTransformer(player, (TileEntityTransformer) te));
            }
            if (te instanceof TileEntityOilGetter) {

                return new GUIOilGetter(new ContainerOilGetter(player, (TileEntityOilGetter) te));
            }
            if (te instanceof TileMatterGenerator) {

                return new GUISolidMatter(new ContainerSolidMatter<>(player, (TileMatterGenerator) te));
            }
            if (te instanceof TileSolarGeneratorEnergy) {

                return new GUISolarGeneratorEnergy(new ContainerSolarGeneratorEnergy(player, (TileSolarGeneratorEnergy) te));
            }
            if (te instanceof TileEntityModuleMachine)
                return new GUIModuleMachine(new ContainerModuleMachine<>(player, (TileEntityModuleMachine) te));

            if (te instanceof TileEntityGenerationStone)
                return new GUIGenStone(new ContainerGenStone<>(player, (TileEntityGenerationStone) te));
            if (te instanceof TileEntityBaseQuantumQuarry)
                return new GUIQuantumQuarry(new ContainerQuantumQuarry<>(player, (TileEntityBaseQuantumQuarry) te));
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
