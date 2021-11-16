package com.denfop.render.error;

import com.denfop.Constants;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityErrorRender extends TileEntitySpecialRenderer {


    static final IModelCustom ore = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/ore.obj"));

    static final ResourceLocation name1 = new ResourceLocation(Constants.TEXTURES,
            "textures/items/transformerUpgrade2.png");
    float rotation;
    float prevRotation;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        render(x, y, z, f);
    }

    private void render(double x, double y, double z, float f) {

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.45F, -1, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glRotatef(rotation, 0F, 1F, 0F);


        bindTexture(name1);

        ore.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();

        rotation = prevRotation + (rotation - prevRotation) * f;
        prevRotation = rotation;
        rotation += 0.25;

    }

}