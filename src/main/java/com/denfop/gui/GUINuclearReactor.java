package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerBaseNuclearReactor;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUINuclearReactor extends GuiContainer {
    public final ContainerBaseNuclearReactor container;
    public final String name;
    private final ResourceLocation background;

    public GUINuclearReactor(ContainerBaseNuclearReactor container1) {
        super(container1);

        this.background = new ResourceLocation(Constants.TEXTURES, container1.base.background);

        this.container = container1;
        this.name = StatCollector.translateToLocal("iu.blockAdvRea.name");
        this.ySize = 243;
        this.xSize = 212;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("ic2.NuclearReactor.gui.info.EU", ModUtils.getString(Math.round(this.container.base.output *this.container.base.coef  *5.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear"))), 100 * this.container.base.heat / this.container.base.maxHeat) + "%", 8, 140, 5752026);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("ic2.NuclearReactor.gui.mode.electric"), 5, 160, 22, 177);


    }

    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.background);
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        int size = this.container.base.getReactorSize();
        int startX = xOffset + 26 - 18;
        int startY = yOffset + 25;
        if (this.container.base.sizeY == 7)
            startY -= 18;
        int i2;
        for (i2 = 0; i2 < this.container.base.sizeY; ++i2) {
            for (int x = size; x < this.container.base.sizeX; ++x) {
                this.drawTexturedModalRect(startX + x * 18, startY + i2 * 18, 213, 1, 16, 16);
            }
        }

    }
}
