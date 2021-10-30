package com.denfop.render.tank;

import com.denfop.tiles.base.TileEntityLiquedTank;
import com.denfop.tiles.tank.TileEntityAdvTank;
import com.denfop.tiles.tank.TileEntityImpTank;
import com.denfop.tiles.tank.TileEntityPerTank;
import com.denfop.tiles.tank.TileEntityTank;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityTankItemRender implements IItemRenderer {

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
        GL11.glTranslatef(0.6F, 0.5F, 0.7F);
        GL11.glScalef(1F, 0.9F, 1F);
        GL11.glRotatef(0F, 0.0F, 0.0F, 1.0F);
        TileEntityLiquedTank tile;
        switch (is.getItemDamage()) {
            case 1:
                tile = new TileEntityAdvTank();
                break;
            case 2:
                tile = new TileEntityImpTank();
                break;
            case 3:
                tile = new TileEntityPerTank();
                break;
            default:
                tile = new TileEntityTank();
                break;
        }
        Minecraft.getMinecraft().renderEngine.bindTexture(tile.texture);
        TileEntityTankRender.model.renderAll();
        GL11.glPopMatrix();
    }

}