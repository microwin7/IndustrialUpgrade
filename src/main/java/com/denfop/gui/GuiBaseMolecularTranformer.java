package com.denfop.gui;

import ic2.core.ContainerBase;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class GUIBaseMolecularTranformer extends GuiContainer {

    protected int xoffset;

    protected int yoffset;

    public GUIBaseMolecularTranformer(ContainerBase<? extends IInventory> container) {
        this(container, 220, 193);
    }

    public GUIBaseMolecularTranformer(ContainerBase<? extends IInventory> container, int xSize, int ySize) {
        super(container);
        this.ySize = ySize;
        this.xSize = xSize;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(getName(), (this.xSize - this.fontRendererObj.getStringWidth(getName())) / 2 + 10, 6,
                4210752);
        String tooltip = StatCollector.translateToLocal("inforamtionmolecular");
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 180, 3, 197, 17);
        String tooltip1 = StatCollector.translateToLocal("inforamtionmolecular1");
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip1, 7, 3, 60, 17);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        this.xoffset = (this.width - this.xSize) / 2;
        this.yoffset = (this.height - this.ySize) / 2;
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);

    }

    public abstract String getName();

    public abstract ResourceLocation getResourceLocation();
}
