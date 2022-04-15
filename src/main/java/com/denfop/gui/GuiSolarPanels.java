package com.denfop.gui;

import com.denfop.container.ContainerSolarPanels;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.utils.ListInformation;
import com.denfop.utils.ModUtils;
import ic2.core.IC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;

public class GUISolarPanels extends GuiContainer {
    public final TileEntitySolarPanel tileentity;
    private ResourceLocation res;


    public GUISolarPanels(ContainerSolarPanels container) {
        super(container);
        this.tileentity = container.tileentity;
        this.allowUserInput = false;
        this.xSize = 194;
        this.ySize = 238;
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.panelinformation"));
            List<String> compatibleUpgrades = ListInformation.panelinform;
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 60, y, yoffset, xoffset, StatCollector.translateToLocal("iu.panelinformation"), true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x - 60, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        int xMin = (this.width - this.xSize) / 2;
        int yMin = (this.height - this.ySize) / 2;
        int x = i - xMin;
        int y = j - yMin;
        if (x >= 70 && x <= 123 && y >= 40 && y <= 56) {
            IC2.network.get().initiateClientTileEntityEvent(this.tileentity, 0);
        }


    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {

        String formatPanelName = I18n.format(this.tileentity.panelName);
        int nmPos = (this.xSize - this.fontRendererObj.getStringWidth(formatPanelName)) / 2;
        this.fontRendererObj.drawString(formatPanelName, nmPos, 4, 7718655);
        if (tileentity.getmodulerf)
            if (tileentity.rf) {
                this.fontRendererObj.drawString("RF -> EU", 81, 44, 13487565);
            } else {
                this.fontRendererObj.drawString("EU -> RF", 81, 44, 13487565);
            }
        else
            this.fontRendererObj.drawString("EU -> RF", 81, 44, 13487565);

        String storageString = I18n.format("gui.SuperSolarPanel.storage") + ": ";
        String maxOutputString = I18n.format("gui.SuperSolarPanel.maxOutput") + ": ";
        String generatingString = I18n.format("gui.SuperSolarPanel.generating") + ": ";
        String energyPerTickString = I18n.format("gui.SuperSolarPanel.energyPerTick");
        String energyPerTickString1 = I18n.format("gui.SuperSolarPanel.energyPerTick1");
        String tierString = I18n.format("gui.iu.tier") + ": ";
        String ModulesString = I18n.format("iu.genday");
        String ModulesString1 = I18n.format("iu.gennight");
        String ModulesString2 = I18n.format("iu.storage");
        String ModulesString3 = I18n.format("iu.output");
        String ModulesString4 = I18n.format("iu.tier1");
        String ModulesString5 = I18n.format("iu.tier2");
        String ModulesString7 = I18n.format("iu.rfmodule");
        String ModulesString71 = I18n.format("iu.rfmodule1");
        String rfstorageString = I18n.format("iu.rfstorage");
        String ModulesString8 = I18n.format("iu.modulewirelles");
        String ModulesString10 = I18n.format("iu.modulewirelles2");
        String Time = ModUtils.getString(ModUtils.Time(this.tileentity.time).get(0)) + I18n.format("iu.hour") + ModUtils.getString(ModUtils.Time(this.tileentity.time).get(1)) + I18n.format("iu.minutes") + ModUtils.getString(ModUtils.Time(this.tileentity.time).get(2)) + I18n.format("iu.seconds");
        String Time2 = ModUtils.getString(ModUtils.Time(this.tileentity.time1).get(0)) + I18n.format("iu.hour") + ModUtils.getString(ModUtils.Time(this.tileentity.time1).get(1)) + I18n.format("iu.minutes") + ModUtils.getString(ModUtils.Time(this.tileentity.time1).get(2)) + I18n.format("iu.seconds");
        String Time3 = ModUtils.getString(ModUtils.Time(this.tileentity.time2).get(0)) + I18n.format("iu.hour") + ModUtils.getString(ModUtils.Time(this.tileentity.time2).get(1)) + I18n.format("iu.minutes") + ModUtils.getString(ModUtils.Time(this.tileentity.time2).get(2)) + I18n.format("iu.seconds");

        String Time1 = I18n.format("iu.time");
        String Time4 = I18n.format("iu.time1");
        String Time5 = I18n.format("iu.time2");
        String Time6 = I18n.format("iu.time3");

        String maxstorage_1 = ModUtils.getString(this.tileentity.maxStorage);
        String maxstorage_2 = ModUtils.getString(this.tileentity.storage);
        // TODO

        String rf = ModUtils.getString(this.tileentity.storage2);
        String rf1 = ModUtils.getString(this.tileentity.maxStorage2);
        String tooltip1 = rfstorageString + rf + "/" + rf1;

        if ((this.tileentity.maxStorage / this.tileentity.p) != 1)
            this.fontRendererObj.drawString(ModulesString2 + ModUtils.getString(((this.tileentity.maxStorage / this.tileentity.p) - 1) * 100) + "%", 15, 182 - 2, 13487565);

        if ((this.tileentity.production / this.tileentity.u) != 1)
            this.fontRendererObj.drawString(ModulesString3 + ModUtils.getString(((this.tileentity.production / this.tileentity.u) - 1) * 100) + "%", 15, 175 - 2, 13487565);

        String generation = ModUtils.getString(this.tileentity.generating);
        String generation1 = ModUtils.getString(this.tileentity.generating * 4);

        String tooltip2 = generatingString + generation + " " + energyPerTickString;
        String tooltip3 = generatingString + generation1 + " " + energyPerTickString1;


        String tooltip = storageString + maxstorage_2 + "/" + maxstorage_1;

        if ((this.tileentity.solarType > 0) && (this.tileentity.solarType <= 7)) {
            this.fontRendererObj.drawString(I18n.format("iu.moduletype" + this.tileentity.solarType), 15, 196 - 2, 13487565);
        }
        this.fontRendererObj.drawString(maxOutputString + ModUtils.getString(this.tileentity.production) + " " + energyPerTickString, 50,
                26 - 4 - 12 + 8 - 6, 13487565);

        this.fontRendererObj.drawString(StatCollector.translateToLocal("pollutioninformation"), 50,
                30, 13487565);
        String temptime = StatCollector.translateToLocal("pollutionpnale");

        if (this.tileentity.getmodulerf) {
            if (this.tileentity.rf)
                this.fontRendererObj.drawString(ModulesString7, 15, 203 - 2, 13487565);
            if (!this.tileentity.rf)
                this.fontRendererObj.drawString(ModulesString71, 15, 203 - 2, 13487565);

        }


        if (this.tileentity.wireless == 1) {
            this.fontRendererObj.drawString(ModulesString8, 15, 209 - 2, 13487565);

        } else {
            this.fontRendererObj.drawString(ModulesString10, 15, 209 - 2, 13487565);

        }


        if ((this.tileentity.genDay / this.tileentity.k) != 1 && this.tileentity.sunIsUp)
            this.fontRendererObj.drawString(ModulesString + ModUtils.getString(((this.tileentity.genDay / this.tileentity.k) - 1) * 100) + "%", 15, 189 - 2, 13487565);
        if ((this.tileentity.genNight / this.tileentity.m) != 1 && !this.tileentity.sunIsUp)
            this.fontRendererObj.drawString(ModulesString1 + ModUtils.getString(((this.tileentity.genNight / this.tileentity.m) - 1) * 100) + "%", 15, 189 - 2, 13487565);

        this.fontRendererObj.drawString(tierString + ModUtils.getString(this.tileentity.machineTire), 50, 46 - 4 - 12 - 8 + 5 - 6,
                13487565);
        double temp = this.tileentity.machineTire - this.tileentity.o;
        if (temp > 0) {
            this.fontRendererObj.drawString(ModulesString4 + ModUtils.getString(temp), 15, 209 - 2 + 6 + 6, 13487565);
        } else if (temp < 0) {

            this.fontRendererObj.drawString(ModulesString5 + ModUtils.getString(temp), 15, 209 - 2 + 6 + 6, 13487565);
        }

        if (this.tileentity.time > 0)
            temptime = Time1 + Time + Time4;
        if (this.tileentity.time1 > 0 && this.tileentity.time <= 0)
            temptime = Time1 + Time2 + Time5;
        if (this.tileentity.time2 > 0 && this.tileentity.time1 <= 0 && this.tileentity.time <= 0)
            temptime = Time1 + Time3 + Time6;
        else if (this.tileentity.time2 <= 0 && this.tileentity.time1 <= 0 && this.tileentity.time <= 0)
            temptime = StatCollector.translateToLocal("end_stage");
        if (this.tileentity.getmodulerf) {

            if (!this.tileentity.rf) {
                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip2, 18, 40, 43, 58);
            } else {
                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip3, 160, 40, 185, 58);

            }
        } else {
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip2, 18, 40, 43, 58);

        }
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, temptime, 50, 30, 144, 42);
        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 14, 5, 26, 17,
                25, 0);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 18, 24, 43, 38);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip1, 155, 24, 180, 38);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);


        setResourceLocation(this.tileentity.getType().texture);
        int h = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        drawTexturedModalRect(h, k, 0, 0, this.xSize, this.ySize);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
        drawTexturedModalRect(h + 14, k + 5, 0, 0, 10, 10);
        this.mc.getTextureManager().bindTexture(getResourceLocation());

        if (this.tileentity.storage > 0) {
            double l = this.tileentity.gaugeEnergyScaled(24.0F);
            drawTexturedModalRect(h + 18, k + 24, 194, 0, (int) (l + 1), 14);
        }
        if (this.tileentity.storage2 > 0 || this.tileentity.storage2 <= this.tileentity.maxStorage2) {
            float l = this.tileentity.gaugeEnergyScaled2(24.0F);

            drawTexturedModalRect(h + 19 + 72 + 40 + 23 + 1, k + 24, 219, 0, (int) (l), 14);
        }
        if (this.tileentity.getmodulerf) {

            if (!this.tileentity.rf) {
                drawTexturedModalRect(h + 40, k + 41, 195, 30, 15, 15);
            } else {
                drawTexturedModalRect(h + 142, k + 42, 210, 30, 15, 15);
            }

        }

        if (this.tileentity.skyIsVisible || (this.tileentity.solarType == 3 || this.tileentity.solarType == 4))
            DrawModel(h, k);


    }

    private void DrawModel(int h, int k) {
        int additionalRect_1 = 24;
        int additionalRect_3 = 15;
        int additionalRect_4 = 15;
        if (!(this.tileentity.sunIsUp || (this.tileentity.solarType == 3 || this.tileentity.solarType == 4)))
            additionalRect_3 *= 2;
        if (this.tileentity.getmodulerf && this.tileentity.rf) {
            additionalRect_1 = 160;
            additionalRect_4 = 46;
        }
        if (this.tileentity.rain) {
            additionalRect_3 *= 2;
        }
        drawTexturedModalRect(h + additionalRect_1, k + 42, 180 + additionalRect_3, additionalRect_4, 14, 14);
    }

    private ResourceLocation getResourceLocation() {
        return res;
    }

    private void setResourceLocation(ResourceLocation res) {
        this.res = res;
        this.mc.renderEngine.bindTexture(res);
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0)
            IC2.network.get().initiateClientTileEntityEvent(this.tileentity, 0);
    }

}
