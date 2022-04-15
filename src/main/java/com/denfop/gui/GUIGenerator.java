package com.denfop.gui;

import com.denfop.container.ContainerGenerator;
import com.denfop.tiles.mechanism.TileEntityBaseGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIGenerator extends GuiContainer {
    private static final ResourceLocation background;

    static {
        background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUIGenerator.png");
    }

    public ContainerGenerator<? extends TileEntityBaseGenerator> container;
    public String name;

    public GUIGenerator(ContainerGenerator<? extends TileEntityBaseGenerator> container1) {
        super(container1);
        this.container = container1;
        this.name = StatCollector.translateToLocal("ic2.Generator.gui.name");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6, 4210752);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocalFormatted("ic2.generic.text.bufferEU", this.container.base.storage), 90, 35, 121, 51);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        int i1;
        if (this.container.base.fuel > 0) {
            i1 = this.container.base.gaugeFuelScaled(12);
            this.drawTexturedModalRect(j + 66, k + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.container.base.gaugeStorageScaled(24);
        this.drawTexturedModalRect(j + 94, k + 35, 176, 14, i1, 17);
    }
}
