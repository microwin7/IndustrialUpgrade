package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerSinSolarPanel;
import com.denfop.utils.ModUtils;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GUISintezator extends GuiContainer {

    private static ResourceLocation tex;

    static {
        GUISintezator.tex = new ResourceLocation(Constants.TEXTURES, "textures/gui/GUI_Sintezator_Slots.png");
    }

    private final ContainerSinSolarPanel container;

    public GUISintezator(ContainerSinSolarPanel container) {
        super(container);
        this.container = container;
        this.allowUserInput = false;
        this.xSize = 194;
        this.ySize = 168;
    }

    protected void drawGuiContainerForegroundLayer(final int par1, final int par2) {
        final String formatPanelName = I18n.format(this.container.base.getInventoryName());
        final int nmPos = (this.xSize - this.fontRendererObj.getStringWidth(formatPanelName)) / 2;
        this.fontRendererObj.drawString(formatPanelName, nmPos, 7, 7718655);
        final String storageString = I18n.format("gui.SuperSolarPanel.storage") + ": ";
        final String maxOutputString = I18n.format("gui.SuperSolarPanel.maxOutput") + ": ";
        final String generatingString = I18n.format("gui.SuperSolarPanel.generating") + ": ";
        final String energyPerTickString = I18n.format("gui.SuperSolarPanel.energyPerTick");
        String tierString = I18n.format("gui.iu.tier") + ": ";
        String maxstorage_1 = ModUtils.getString(this.container.tileentity.maxStorage);
        String maxstorage_2 = ModUtils.getString(this.container.tileentity.storage);
        String tooltip = storageString + maxstorage_2 + "/" + maxstorage_1;
        String output = ModUtils.getString(this.container.tileentity.production);
        this.fontRendererObj.drawString(maxOutputString + output + (" " + energyPerTickString), 50, 32 - 10, 13487565);
        this.fontRendererObj.drawString(tierString + this.container.tileentity.machineTire, 50, 32, 13487565);
        if (!this.container.tileentity.getmodulerf) {
            String generation = ModUtils.getString(this.container.tileentity.generating);
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 18, 24, 43, 38);
            String tooltip2 = generatingString + generation + " " + energyPerTickString;
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip2, 18, 40, 43, 58);
        } else {
            maxstorage_1 = ModUtils.getString(this.container.tileentity.maxStorage2);
            maxstorage_2 = ModUtils.getString(this.container.tileentity.storage2);
            tooltip = "RF: " + maxstorage_2 + "/" + maxstorage_1;

            String generation = ModUtils.getString(this.container.tileentity.generating * 4);
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 18, 24, 43, 38);
            String tooltip2 = generatingString + generation + " " + "RF/t";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip2, 18, 40, 43, 58);

        }
    }

    protected void drawGuiContainerBackgroundLayer(final float f, final int i, final int j) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(GUISintezator.tex);
        final int h = (this.width - this.xSize) / 2;
        final int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(h, k, 0, 0, this.xSize, this.ySize);
        if (!this.container.tileentity.getmodulerf) {
            if (this.container.tileentity.storage > 0
                    || this.container.tileentity.storage <= this.container.tileentity.maxStorage) {
                final double l = this.container.tileentity.gaugeEnergyScaled(24);

                this.drawTexturedModalRect(h + 19, k + 24, 195, 0, (int) (l), 14);
            }
        } else {
            if (this.container.tileentity.storage2 > 0
                    || this.container.tileentity.storage2 <= this.container.tileentity.maxStorage2) {
                final double l = this.container.tileentity.gaugeEnergyScaled1(25);

                this.drawTexturedModalRect(h + 19, k + 24, 219, 0, (int) (l), 14);
            }
        }

        if (!this.container.tileentity.getmodulerf) {
            if (!this.container.tileentity.rain) {
                if (this.container.tileentity.sunIsUp) {
                    drawTexturedModalRect(h + 24, k + 42, 195, 15, 14, 14);
                } else {
                    drawTexturedModalRect(h + 24, k + 42, 210, 15, 14, 14);
                }
            } else {
                if (this.container.tileentity.sunIsUp) {
                    drawTexturedModalRect(h + 24, k + 42, 225, 15, 14, 14);
                } else {
                    drawTexturedModalRect(h + 24, k + 42, 240, 15, 14, 14);
                }
            }
        } else {
            if (!this.container.tileentity.rain) {
                if (this.container.tileentity.sunIsUp) {
                    drawTexturedModalRect(h + 24, k + 42, 195, 30, 14, 14);
                } else {
                    drawTexturedModalRect(h + 24, k + 42, 210, 30, 14, 14);
                }
            } else {
                if (this.container.tileentity.sunIsUp) {
                    drawTexturedModalRect(h + 24, k + 42, 225, 30, 14, 14);
                } else {
                    drawTexturedModalRect(h + 24, k + 42, 240, 30, 14, 14);
                }
            }
        }
    }
}
