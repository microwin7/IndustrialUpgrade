package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerElectricBlock;
import com.denfop.utils.ListInformation;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;

@SideOnly(Side.CLIENT)
public class GUIElectricBlock extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation(Constants.TEXTURES,
            "textures/gui/GUIElectricBlockEuRf.png");
    private final ContainerElectricBlock container;
    private final String armorInv;
    private final String name;

    public GUIElectricBlock(ContainerElectricBlock container1) {
        super(container1);
        this.ySize = 196;
        this.container = container1;
        this.armorInv = StatCollector.translateToLocal("ic2.EUStorage.gui.info.armor");

        this.name = container1.base.getInventoryName();
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.electricstorageinformation"));
            List<String> compatibleUpgrades = ListInformation.storageinform;
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 60, y, yoffset, xoffset, StatCollector.translateToLocal("iu.electricstorageinformation"), true, width);
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
        if (x >= 110 && x <= 130 && y >= 34 && y <= 50)
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 0);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6,
                4210752);
        this.fontRendererObj.drawString(this.armorInv, 8, this.ySize - 126 + 3, 4210752);
        String tooltip = "EU: " + ModUtils.getString(this.container.base.energy) + "/" + ModUtils.getString(this.container.base.maxStorage);
        String tooltip1 = "RF: " + ModUtils.getString(this.container.base.energy2) + "/" + ModUtils.getString(this.container.base.maxStorage2);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 85 - 3, 38, 108, 46);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip1, 133, 38, 156 + 2, 46);
        String output = StatCollector.translateToLocalFormatted("ic2.EUStorage.gui.info.output",
                ModUtils.getString(this.container.base.getOutput()));
        this.fontRendererObj.drawString(output, 85, 70, 4210752);
        this.fontRendererObj.drawString(EnumChatFormatting.BOLD + "" + EnumChatFormatting.BLACK + StatCollector.translateToLocal("button.rg"), 118, 38, 4210752);

        String tooltip3 = StatCollector.translateToLocal("inforamtionelectricstorage");
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip3, 110, 34, 132, 51);
        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 0, 0, 12, 12,
                25, 0);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
        drawTexturedModalRect(j + 3, k + 3, 0, 0, 10, 10);
        this.mc.getTextureManager().bindTexture(background);
        if (this.container.base.energy > 0.0D) {
            int i1 = (int) (24.0F * this.container.base.getChargeLevel());
            drawTexturedModalRect(j + 79 + 6 - 2 - 1, k + 34, 176, 14, i1 + 1, 16);
        }
        if (this.container.base.energy2 > 0.0D) {

            int i1 = (int) (24.0F * this.container.base.getChargeLevel1());
            drawTexturedModalRect(j + 79 + 54 + 2, k + 34, 176, 31, i1 + 1, 16);
        }
        if (this.container.base.rf) {
            if (this.container.base.rfeu) {
                drawTexturedModalRect(j + 110, k + 22, 176, 62, 21, 9);

            } else {
                drawTexturedModalRect(j + 110, k + 22, 176, 51, 21, 9);

            }
        }

    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0)
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 0);
    }
}
