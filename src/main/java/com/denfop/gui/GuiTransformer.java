package com.denfop.gui;

import com.denfop.container.ContainerTransformer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUITransformer extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUITransfomer.png");
    public final ContainerTransformer container;
    public final String name;
    public final String[] mode = new String[]{"", "", "", ""};

    public GUITransformer(ContainerTransformer container1) {
        super(container1);
        this.container = container1;
        this.name = StatCollector.translateToLocal("ic2.blockTransformer");
        this.mode[1] = StatCollector.translateToLocal("ic2.Transformer.gui.switch.mode1");
        this.mode[2] = StatCollector.translateToLocal("ic2.Transformer.gui.switch.mode2");
        this.mode[3] = StatCollector.translateToLocal("ic2.Transformer.gui.switch.mode3");
        this.ySize = 219;
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        IC2.network.get().initiateClientTileEntityEvent(this.container.base, guibutton.id);
    }

    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        int xMin = (this.width - this.xSize) / 2;
        int yMin = (this.height - this.ySize) / 2;
        int x = i - xMin;
        int y = j - yMin;
        if (x >= 150 && y >= 32 && x <= 167 && y <= 49)
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 3);
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString((this.container.base).getTyp() + " - " + this.name, (this.xSize - this.fontRendererObj.getStringWidth((this.container.base).getTyp() + " - " + this.name)) / 2, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("ic2.Transformer.gui.Output"), 6, 30, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("ic2.Transformer.gui.Input"), 6, 43, 4210752);
        this.fontRendererObj.drawString((this.container.base).getoutputflow() + " " + StatCollector.translateToLocal("ic2.generic.text.EUt"), 52, 30, 2157374);
        this.fontRendererObj.drawString((this.container.base).getinputflow() + " " + StatCollector.translateToLocal("ic2.generic.text.EUt"), 52, 45, 2157374);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("ic2.Transformer.gui.refresh"), 150, 32, 167, 49, 0, -50);
        RenderItem renderItem = new RenderItem();
        RenderHelper.enableGUIStandardItemLighting();
        switch ((this.container.base).getMode()) {
            case 0:
                renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, Ic2Items.wrench, 152, 67);
                break;
            case 1:
                renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, Ic2Items.wrench, 152, 87);
                break;
            case 2:
                renderItem.renderItemIntoGUI(this.mc.fontRenderer, this.mc.renderEngine, Ic2Items.wrench, 152, 107);
                break;
        }
        RenderHelper.disableStandardItemLighting();
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 65, 144, 20, this.mode[1]));
        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 85, 144, 20, this.mode[2]));
        this.buttonList.add(new GuiButton(2, (this.width - this.xSize) / 2 + 7, (this.height - this.ySize) / 2 + 105, 144, 20, this.mode[3]));
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
