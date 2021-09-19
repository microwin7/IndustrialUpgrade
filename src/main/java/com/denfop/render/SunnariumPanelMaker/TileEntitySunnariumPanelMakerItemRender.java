package com.denfop.render.SunnariumPanelMaker;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntitySunnariumPanelMakerItemRender implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack is, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack is, Object... data) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0F, 0.5F);
        GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(TileEntitySunnariumPanelMakerRender.texture);
        TileEntitySunnariumPanelMakerRender.model.renderAll();
        GL11.glPopMatrix();
    }

}