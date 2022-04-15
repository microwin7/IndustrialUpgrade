package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerSolarGeneratorEnergy;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUISolarGeneratorEnergy extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation(Constants.TEXTURES,
            "textures/gui/SunnariumGenerator.png");
    private final ContainerSolarGeneratorEnergy container;
    private final String name;


    public GUISolarGeneratorEnergy(ContainerSolarGeneratorEnergy container1) {
        super(container1);
        this.ySize = 196;
        this.container = container1;
        this.name = container1.base.getInventoryName();
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6,
                4210752);
        String tooltip = "SE: " + ModUtils.getString(this.container.base.energy);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 123, 38, 146, 46);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        if (this.container.base.energy > 0.0D) {
            int i1 = (int) (24.0D * (this.container.base.energy / this.container.base.maxSunEnergy));
            drawTexturedModalRect(j + 123, k + 34, 176, 14, i1 + 1, 16);
        }


    }
}
