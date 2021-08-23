package com.denfop.render.oilquarry;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class TileEntityQuarryOilItemRender implements IItemRenderer {

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

        GL11.glRotatef(0F, 0.0F, 0F, 0F);
        Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityQuarryOilRender.texture);
        TileEntityQuarryOilRender.model.renderAll();
        GL11.glPopMatrix();
    }

}