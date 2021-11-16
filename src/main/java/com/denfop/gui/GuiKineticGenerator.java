package com.denfop.gui;

import com.denfop.container.ContainerKineticGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIKineticGenerator extends GuiContainer {
    private static final ResourceLocation background;

    static {
        background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUIKineticGenerator.png");
    }

    public ContainerKineticGenerator container;
    public String name;

    public GUIKineticGenerator(ContainerKineticGenerator container1) {
        super(container1);
        this.container = container1;
        this.name = StatCollector.translateToLocal("ic2.KineticGenerator.gui.name");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 4, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("ic2.KineticGenerator.gui.Output", (float) Math.round(this.container.base.getproduction() * 10.0D) / 10.0F), 42, 52, 2157374);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocalFormatted("ic2.generic.text.bufferEU", (int) this.container.base.EUstorage), 59, 33, 116, 46);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        int i1 = this.container.base.gaugeEUStorageScaled(58);
        this.drawTexturedModalRect(j + 62, k + 36, 179, 18, i1, 8);
    }
}
