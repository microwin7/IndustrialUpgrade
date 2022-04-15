package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerFisher;
import com.denfop.tiles.base.TileEntityFisher;
import com.denfop.utils.ListInformation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;

@SideOnly(Side.CLIENT)
public class GUIFisher extends GuiIC2 {
    public final ContainerFisher container;

    public GUIFisher(ContainerFisher container1) {
        super(container1);
        this.container = container1;
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.fisherinformation"));
            List<String> compatibleUpgrades = ListInformation.fisherinform;
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 60, y, yoffset, xoffset, StatCollector.translateToLocal("iu.fisherinformation"), true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x - 60, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.getName(), (this.xSize - this.fontRendererObj.getStringWidth(this.getName())) / 2, 6, 4210752);

        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 3, 3, 15, 15,
                25, 0);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);

        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
        drawTexturedModalRect(this.xoffset + 3, this.yoffset + 3, 0, 0, 10, 10);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        int chargeLevel = (int) (48.0F * ((TileEntityFisher) this.container.base).getEnergy()
                / ((TileEntityFisher) this.container.base).maxEnergy);
        int progress = (15 * ((TileEntityFisher) this.container.base).progress / 100);
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 140 + 1 + 5, this.yoffset + 28 + 48 - chargeLevel, 176,
                    48 - chargeLevel, 48, chargeLevel);

        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 42, this.yoffset + 46, 177, 48, progress + 1, 13);


    }

    public String getName() {
        return StatCollector.translateToLocal("iu.blockFisher.name");
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIFisher.png");
    }
}
