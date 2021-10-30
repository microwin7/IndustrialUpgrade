package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerAdvOilRefiner;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
public class GUIAdvOilRefiner extends GuiContainer {
    public ContainerAdvOilRefiner container;

    private static final ResourceLocation background;

    public GUIAdvOilRefiner(ContainerAdvOilRefiner container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        FluidStack fluidstack = this.container.base.getFluidStackfromTank();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal("iu.fluidneft") + ": " + fluidstack.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 15, 9, 25, 57);
        }
        FluidStack fluidstack1 = this.container.base.getFluidStackfromTank1();
        if (fluidstack1 != null && fluidstack1.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal("iu.fluidpolyeth") + ": " + fluidstack1.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 77, 9, 87, 57);
        }
        FluidStack fluidstack2 = this.container.base.getFluidStackfromTank2();
        if (fluidstack2 != null && fluidstack2.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal("iu.fluidpolyprop") + ": " + fluidstack2.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 109, 9, 119, 57);
        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);


        if (this.container.base.getTankAmount() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.drawTexturedModalRect(xOffset + 12, yOffset + 6, 176, 0, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled(47);
                DrawUtil.drawRepeated(fluidIcon, xOffset + 16, yOffset + 10 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(background);
                this.drawTexturedModalRect(xOffset + 16, yOffset + 10, 176, 55, 12, 47);
            }
        }
        if (this.container.base.getTankAmount1() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank1().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.drawTexturedModalRect(xOffset + 74, yOffset + 6, 176, 0, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled1(47);
                DrawUtil.drawRepeated(fluidIcon, xOffset + 78, yOffset + 10 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(background);
                this.drawTexturedModalRect(xOffset + 78, yOffset + 10, 176, 55, 12, 47);
            }
        }
        if (this.container.base.getTankAmount2() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank2().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.drawTexturedModalRect(xOffset + 106, yOffset + 6, 176, 0, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled2(47);
                DrawUtil.drawRepeated(fluidIcon, xOffset + 110, yOffset + 10 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(background);
                this.drawTexturedModalRect(xOffset + 110, yOffset + 10, 176, 55, 12, 47);
            }
        }
        int energy = (int) ((this.container.base.energy / this.container.base.maxEnergy) * 29);
        energy = Math.min(energy, 29);
        this.drawTexturedModalRect(xOffset + 39, yOffset + 69, 177, 104, energy, 9);

    }

    static {
        background = new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIOilRefiner1.png");
    }
}
