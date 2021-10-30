package com.denfop.render.tank;

import com.denfop.Constants;
import com.denfop.tiles.base.TileEntityLiquedTank;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import net.minecraftforge.fluids.FluidRegistry;
import org.lwjgl.opengl.GL11;

public class TileEntityTankRender extends TileEntitySpecialRenderer {

    static final IModelCustom model = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/tank.obj"));

    static final IModelCustom model1 = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oil.obj"));

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        render((TileEntityLiquedTank) tile, x, y, z);
    }

    private void render(TileEntityLiquedTank tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.6F, 0.51F, 0.7F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        GL11.glScalef(1F, 0.8F, 1F);
        bindTexture(tile.texture);
        model.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        double m1 = (tile.gaugeLiquidScaled(0.51));
        GL11.glTranslatef(0.6F, (float) m1, 0.7F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        double m = (tile.gaugeLiquidScaled(0.8));
        m = Math.min(0.8, m);
        GL11.glScalef(1F, (float) m, 1F);
        if (tile.getFluidTank().getFluid() != null)
            if (new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex() != null) {
                ResourceLocation name1 = null;
                if (tile.getFluidTank().getFluid().getFluid() == FluidRegistry.LAVA || tile.getFluidTank().getFluid().getFluid() == FluidRegistry.WATER) {
                    name1 = new ResourceLocation(null,
                            "textures/blocks/" + new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName() + ".png");
                } else {
                    if (new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName().indexOf(":") > 0) {
                        String name = new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName().substring(0, new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName().indexOf(":"));
                        String name2 = new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName().substring(new ItemStack(tile.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex().getIconName().indexOf(":") + 1);

                        name1 = new ResourceLocation(name,
                                "textures/blocks/" + name2 + ".png");
                    }


                }
                bindTexture(name1);
            }
        model1.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

}