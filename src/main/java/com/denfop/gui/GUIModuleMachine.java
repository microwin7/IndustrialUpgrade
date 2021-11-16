package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerModuleMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;

@SideOnly(Side.CLIENT)
public class GUIModuleMachine extends GuiIC2 {
    public final ContainerModuleMachine container;

    public GUIModuleMachine(ContainerModuleMachine container1) {
        super(container1);
        this.container = container1;
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.moduleinformation"));
            List<String> compatibleUpgrades = getInformation();
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x, y, yoffset, xoffset, StatCollector.translateToLocal("iu.moduleinformation"), true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    private static List<String> getInformation() {
        List<String> ret = new ArrayList();
        ret.add(StatCollector.translateToLocal("iu.moduleinformation1"));
        ret.add(StatCollector.translateToLocal("iu.moduleinformation2"));
        ret.add(StatCollector.translateToLocal("iu.moduleinformation3"));


        return ret;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 103, (this.height - this.ySize) / 2 + 21,
                68, 17, I18n.format("button.write")));
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        this.mc.getTextureManager().bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
        this.drawTexturedModalRect(this.xoffset + 3, this.yoffset + 3, 0, 0, 10, 10);
        this.mc.getTextureManager().bindTexture(this.getResourceLocation());

    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 3, 3, 15, 15,
                25, 0);
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get().initiateClientTileEntityEvent((TileEntity) this.container.base, 0);

        }
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIModuleMachine.png");
    }
}
