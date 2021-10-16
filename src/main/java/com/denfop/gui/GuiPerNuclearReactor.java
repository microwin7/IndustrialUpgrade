
package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerPerNuclearReactor;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIPerNuclearReactor extends GuiContainer {
    public final ContainerPerNuclearReactor container;
    public final String name;
    private final ResourceLocation background;
    private final ResourceLocation backgroundfluid;

    public GUIPerNuclearReactor(ContainerPerNuclearReactor container1) {
        super(container1);
        this.background = new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIPerNuclearReaktor.png");
        this.backgroundfluid = new ResourceLocation(Constants.TEXTURES, "textures/gui/PerUINuclearReaktorFluid.png");
        this.container = container1;
        this.name = "";
        this.ySize = 243;
        this.xSize = 212;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6, 4210752);
        if ((this.container.base).isFluidCooled()) {
            this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("ic2.NuclearReactor.gui.info.hU", (this.container.base).EmitHeat, 100 * (this.container.base).heat / (this.container.base).maxHeat) + "%", 8, 140, 5752026);
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("ic2.NuclearReactor.gui.mode.fluid"), 5, 160, 22, 177);
            FluidStack fluidstackinput = (this.container.base).getinputtank().getFluid();
            FluidStack fluidstackoutput = (this.container.base).getoutputtank().getFluid();
            String tooltip;
            if (fluidstackinput != null && fluidstackinput.getFluid() != null) {
                tooltip = fluidstackinput.getFluid().getName() + ": " + fluidstackinput.amount + StatCollector.translateToLocal("ic2.generic.text.mb");
                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 10, 54, 21, 100);
            }

            if (fluidstackoutput != null && fluidstackoutput.getFluid() != null) {
                tooltip = fluidstackoutput.getFluid().getName() + ": " + fluidstackoutput.amount + StatCollector.translateToLocal("ic2.generic.text.mb");
                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 190, 54, 202, 100);
            }
        } else {
            this.fontRendererObj.drawString(StatCollector.translateToLocalFormatted("ic2.NuclearReactor.gui.info.EU", ModUtils.getString((this.container.base).getReactorEUEnergyOutput()), 100 * (this.container.base).heat / (this.container.base).maxHeat) + "%", 8, 140, 5752026);
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("ic2.NuclearReactor.gui.mode.electric"), 5, 160, 22, 177);
        }

    }

    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if ((this.container.base).isFluidCooled()) {
            this.mc.getTextureManager().bindTexture(this.backgroundfluid);
        } else {
            this.mc.getTextureManager().bindTexture(this.background);
        }

        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        int size = (this.container.base).getReactorSize();
        int startX = xOffset + 26 - 18;
        int startY = yOffset + 25 - 18;

        int i2;
        for (i2 = 0; i2 < 7; ++i2) {
            for (int x = size; x < 11; ++x) {
                this.drawTexturedModalRect(startX + x * 18, startY + i2 * 18, 213, 1, 16, 16);
            }
        }

        if ((this.container.base).isFluidCooled()) {
            i2 = (this.container.base).gaugeHeatScaled(160);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 23, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 41, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 59, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 77, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 95, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 113, 0, 243, i2, 2);
            this.drawTexturedModalRect(xOffset + 26 + 160 - i2, yOffset + 131, 0, 243, i2, 2);
            int liquidHeight;
            IIcon fluidIconoutput;
            if ((this.container.base).getinputtank().getFluidAmount() > 0) {
                fluidIconoutput = FluidRegistry.getFluid((this.container.base).getinputtank().getFluid().getFluidID()).getIcon();
                if (fluidIconoutput != null) {
                    this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    liquidHeight = (this.container.base).gaugeLiquidScaled(47, 0);
                    DrawUtil.drawRepeated(fluidIconoutput, xOffset + 10, yOffset + 101 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                    this.mc.renderEngine.bindTexture(this.background);
                    this.drawTexturedModalRect(xOffset + 11, yOffset + 59, 218, 123, 9, 37);
                }
            }

            if ((this.container.base).getoutputtank().getFluidAmount() > 0) {
                fluidIconoutput = FluidRegistry.getFluid((this.container.base).getoutputtank().getFluid().getFluidID()).getIcon();
                if (fluidIconoutput != null) {
                    this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                    liquidHeight = (this.container.base).gaugeLiquidScaled(47, 1);
                    DrawUtil.drawRepeated(fluidIconoutput, xOffset + 190, yOffset + 101 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                    this.mc.renderEngine.bindTexture(this.background);
                    this.drawTexturedModalRect(xOffset + 192, yOffset + 59, 218, 81, 9, 37);
                }
            }
        }

    }
}
