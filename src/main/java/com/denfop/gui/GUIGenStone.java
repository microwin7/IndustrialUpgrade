package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerGenStone;
import com.denfop.tiles.mechanism.TileEntityGenerationStone;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class GUIGenStone extends GUIIC2 {
    public final ContainerGenStone<? extends TileEntityGenerationStone> container;

    public GUIGenStone(ContainerGenStone<? extends TileEntityGenerationStone> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (16 * this.container.base.getProgress());
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 56 + 1 - 48, this.yoffset + 36 + 14 - chargeLevel, 176,
                    14 - chargeLevel, 14, chargeLevel);
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

        itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(Blocks.stone), xoffset + 64, yoffset + 28);

        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
        if (progress > 0) {

            GL11.glPushMatrix();
            GL11.glColor4f(1, 1, 1, 1);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(Blocks.cobblestone), xoffset + 64, yoffset + 28);

            GL11.glDisable(GL11.GL_LIGHTING);

            GL11.glPopMatrix();
        }
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIGenStone.png");
    }
}
