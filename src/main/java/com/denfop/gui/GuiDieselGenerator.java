package com.denfop.gui;

import com.denfop.container.ContainerDieselGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIDieselGenerator extends GuiContainer {
    private static final ResourceLocation background;

    static {
        background = new ResourceLocation(IC2.textureDomain, "textures/gui/GUIFluidGenerator.png");
    }

    public ContainerDieselGenerator container;
    public String name;

    public GUIDieselGenerator(ContainerDieselGenerator container1) {
        super(container1);
        this.container = container1;
        this.name = StatCollector.translateToLocal("iu.blockDiesel.name");
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 6, 4210752);
        FluidStack fluidstack = this.container.base.getFluidStackfromTank();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal("iu.fluiddizel") + ": " + fluidstack.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 73, 23, 83, 71);
        }

        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocalFormatted("ic2.generic.text.bufferEU", this.container.base.storage), 108, 25, 139, 41);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        if (this.container.base.storage > 0.0D) {
            int i2 = this.container.base.gaugeStorageScaled(25);
            this.drawTexturedModalRect(xOffset + 111, yOffset + 25, 176, 0, i2, 17);
        }

        if (this.container.base.getTankAmount() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.drawTexturedModalRect(xOffset + 70, yOffset + 20, 176, 17, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled(47);
                DrawUtil.drawRepeated(fluidIcon, xOffset + 74, yOffset + 24 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(background);
                this.drawTexturedModalRect(xOffset + 74, yOffset + 24, 176, 72, 12, 47);
            }
        }

    }
}
