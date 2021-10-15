package com.denfop.render.oilrefiner;

import com.denfop.Constants;
import com.denfop.tiles.base.TileEntityOilRefiner;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityOilRefinerRender extends TileEntitySpecialRenderer {

    static final IModelCustom model = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oilrefiner.obj"));
    static final IModelCustom model1 = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oil.obj"));
    static final IModelCustom model2 = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oil1.obj"));
    static final IModelCustom model3 = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/oil2.obj"));

    public static final ResourceLocation texture = new ResourceLocation(Constants.TEXTURES,
            "textures/models/oilrefiner.png");
    public static final ResourceLocation texture1 = new ResourceLocation(Constants.TEXTURES,
            "textures/blocks/blocks/neft_still.png");
    public static final ResourceLocation texture2 = new ResourceLocation(Constants.TEXTURES,
            "textures/blocks/blocks/dizel_still.png");
    public static final ResourceLocation texture3 = new ResourceLocation(Constants.TEXTURES,
            "textures/blocks/blocks/benz_still.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        render((TileEntityOilRefiner) tile, x, y, z);
    }

    private void render(TileEntityOilRefiner tile, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.6F, 0.51F, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        GL11.glScalef(1F, 0.8F, 1F);
        bindTexture(texture);
        model.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        double m1 = (tile.gaugeLiquidScaled(0.51));
        GL11.glTranslatef(0.6F, (float) m1, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        double m = (tile.gaugeLiquidScaled(0.8));
        m = Math.min(0.8, m);
        GL11.glScalef(1F, (float) m, 1F);
        bindTexture(texture1);
        model1.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

       GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        m1 = (tile.gaugeLiquidScaled1(0.51));
        GL11.glTranslatef(0.6F, (float) m1, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        m = (tile.gaugeLiquidScaled1(0.8));
        m = Math.min(0.8, m);
        GL11.glScalef(1F, (float) m, 1F);
        bindTexture(texture3);
        model3.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        m1 = (tile.gaugeLiquidScaled2(0.51));
        GL11.glTranslatef(0.6F, (float) m1, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        m = (tile.gaugeLiquidScaled2(0.8));
        m = Math.min(0.8, m);
        GL11.glScalef(1F, (float) m, 1F);
        bindTexture(texture2);
        model2.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }


}