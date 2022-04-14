package com.denfop.proxy;


import com.denfop.Constants;
import com.denfop.IUItem;
import com.denfop.api.IFluidModelProvider;
import com.denfop.api.IModelRegister;
import com.denfop.blocks.FluidName;
import com.denfop.gui.GuiColor;
import com.denfop.render.EntityRendererStreak;
import com.denfop.render.EntityStreak;
import com.denfop.render.EventDarkQuantumSuitEffect;
import com.denfop.render.IUModelLoader;
import com.denfop.render.ModelCable;
import com.denfop.render.ModelCoolPipes;
import com.denfop.render.ModelPipes;
import com.denfop.render.ModelQCable;
import com.denfop.render.ModelSCable;
import com.denfop.render.advoilrefiner.TileEntityAdvOilRefinerRender;
import com.denfop.render.doublemoleculartransformer.TileEntityDoubleMolecularRender;
import com.denfop.render.moleculartransformer.TileEntityMolecularRender;
import com.denfop.render.oilquarry.TileEntityQuarryOilRender;
import com.denfop.render.oilrefiner.TileEntityOilRefinerRender;
import com.denfop.render.sintezator.TileEntitySintezatorRender;
import com.denfop.render.tank.TileEntityTankRender;
import com.denfop.render.tile.TileEntityPanelRender;
import com.denfop.tiles.base.TileEntityAdminSolarPanel;
import com.denfop.tiles.base.TileEntityAdvOilRefiner;
import com.denfop.tiles.base.TileEntityDoubleMolecular;
import com.denfop.tiles.base.TileEntityLiquedTank;
import com.denfop.tiles.base.TileEntityMolecularTransformer;
import com.denfop.tiles.base.TileEntityQuarryVein;
import com.denfop.tiles.base.TileEntitySintezator;
import com.denfop.tiles.mechanism.TileEntityOilRefiner;
import ic2.core.IC2;
import ic2.core.profile.ProfileManager;
import ic2.core.util.LogCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ClientProxy extends CommonProxy {

    public static final ArrayList<IModelRegister> modelList = new ArrayList();

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        for (IModelRegister register : modelList) {
            register.registerModels();
        }
        OBJLoader.INSTANCE.addDomain(Constants.MOD_ID);
        FluidName[] var8 = FluidName.values;
        final int var2 = var8.length;

        int var3;
        for (var3 = 0; var3 < var2; ++var3) {
            FluidName name = var8[var3];
            if (!name.hasInstance()) {
                IC2.log.warn(LogCategory.Block, "The fluid " + name + " is not initialized.");
            } else {
                Fluid provider = name.getInstance();
                if (provider instanceof IFluidModelProvider) {
                    ((IFluidModelProvider) provider).registerModels(name);
                }
            }
        }
        final EntityRendererStreak streak = new EntityRendererStreak(Minecraft.getMinecraft().getRenderManager());
        RenderingRegistry.registerEntityRenderingHandler(
                EntityStreak.class,
                renderManager -> new EntityRendererStreak(renderManager) {


                }
        );
        IUModelLoader loader = new IUModelLoader();
        loader.register(new ResourceLocation(Constants.MOD_ID, "models/block/wiring/cable_iu"), new ModelCable());
        loader.register(new ResourceLocation(Constants.MOD_ID, "models/block/wiring/pipes_iu"), new ModelPipes());
        loader.register(new ResourceLocation(Constants.MOD_ID, "models/block/wiring/qcable"), new ModelQCable());
        loader.register(new ResourceLocation(Constants.MOD_ID, "models/block/wiring/scable"), new ModelSCable());
        loader.register(new ResourceLocation(Constants.MOD_ID, "models/block/wiring/cool_pipes_iu"), new ModelCoolPipes());

        ModelLoaderRegistry.registerLoader(loader);
        ProfileManager.doTextureChanges();


    }

    public void regrecipemanager() {
        super.regrecipemanager();
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

    public boolean isRendering() {
        return !this.isSimulating();
    }

    @Override
    public void profilerStartSection(final String section) {
        if (this.isRendering()) {
            Minecraft.getMinecraft().mcProfiler.startSection(section);
        } else {
            super.profilerStartSection(section);
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(
            final int ID,
            final EntityPlayer player,
            final World world,
            final int x,
            final int y,
            final int z
    ) {
        if (ID == 4) {
            if (player.inventory.armorInventory.get(2).getItem() == IUItem.quantumBodyarmor) {
                return new GuiColor(player);
            }
        }
        return null;
    }

    public void registerRecipe() {
        super.registerRecipe();

    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        MinecraftForge.EVENT_BUS.register(new EventDarkQuantumSuitEffect());
        FMLCommonHandler.instance().bus().register(new EventDarkQuantumSuitEffect());



        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySintezator.class, new TileEntitySintezatorRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityQuarryVein.class, new TileEntityQuarryOilRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMolecularTransformer.class, new TileEntityMolecularRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdminSolarPanel.class, new TileEntityPanelRender());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityOilRefiner.class, new TileEntityOilRefinerRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityDoubleMolecular.class, new TileEntityDoubleMolecularRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAdvOilRefiner.class, new TileEntityAdvOilRefinerRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLiquedTank.class, new TileEntityTankRender());


    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

    }

    public boolean addIModelRegister(IModelRegister modelRegister) {
        return modelList.add(modelRegister);
    }

}
