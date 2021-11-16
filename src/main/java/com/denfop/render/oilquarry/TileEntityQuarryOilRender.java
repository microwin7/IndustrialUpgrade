package com.denfop.render.oilquarry;

import com.denfop.Constants;
import com.denfop.tiles.base.TileEntityQuarryVein;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class TileEntityQuarryOilRender extends TileEntitySpecialRenderer {

    public static final ResourceLocation texture = new ResourceLocation(Constants.TEXTURES,
            "textures/models/quarryoil.png");
    static final IModelCustom model = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/quarryoil.obj"));
    static final IModelCustom ore = AdvancedModelLoader
            .loadModel(new ResourceLocation(Constants.TEXTURES, "models/ore.obj"));
    float rotation;
    float prevRotation;

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float f) {
        render((TileEntityQuarryVein) tile, x, y, z, f);
    }

    private void render(TileEntityQuarryVein tile, double x, double y, double z, float f) {
        GL11.glPushMatrix();

        GL11.glTranslated(x, y, z);
        GL11.glTranslatef(0.5F, 0F, 0.5F);
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        bindTexture(texture);

        model.renderAll();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        if (!tile.analysis)
            if (!tile.empty) {
                GL11.glPushMatrix();
                GL11.glTranslated(x, y, z);
                GL11.glTranslatef(0.45F, 0, 0.5F);
                GL11.glEnable(GL11.GL_BLEND);

                GL11.glRotatef(rotation, 0F, 1F, 0F);

                Block block = tile.getWorldObj().getBlock(tile.x, tile.y, tile.z);
                int meta = tile.getWorldObj().getBlockMetadata(tile.x, tile.y, tile.z);
                if (block != null)
                    if (block.getIcon(0, meta) != null) {
                        ResourceLocation name1 = null;

                        if (block.getBlockTextureFromSide(0).getIconName().indexOf(":") > 0) {
                            String name = block.getIcon(0, meta).getIconName().substring(0, block.getIcon(0, meta).getIconName().indexOf(":"));
                            String name2 = block.getIcon(0, meta).getIconName().substring(block.getIcon(0, meta).getIconName().indexOf(":") + 1);

                            name1 = new ResourceLocation(name,
                                    "textures/blocks/" + name2 + ".png");
                        }


                        bindTexture(name1);
                    }
                ore.renderAll();
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glPopMatrix();
            }
        rotation = prevRotation + (rotation - prevRotation) * f;
        prevRotation = rotation;
        rotation += 0.25;

    }

}