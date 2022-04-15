package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerObsidianGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class GUIObsidianGenerator extends GuiIC2 {
    public final ContainerObsidianGenerator container;

    public GUIObsidianGenerator(ContainerObsidianGenerator container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.getName(), (this.xSize - this.fontRendererObj.getStringWidth(this.getName())) / 2, 1, 4210752);
        if (this.container.base instanceof IUpgradableBlock) {
            GuiTooltipHelper.drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 0, 0, 12, 12, this.container.base, 25, 0);
        }
        FluidStack fluidstack = this.container.base.getFluidTank1().getFluid();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal(fluidstack.getUnlocalizedName()) + ": " + fluidstack.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 44, 12, 54, 60);


        }
        fluidstack = this.container.base.getFluidTank2().getFluid();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal(fluidstack.getUnlocalizedName()) + ": " + fluidstack.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 68, 12, 78, 60);


        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (16 * this.container.base.getProgress());
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 25, this.yoffset + 57 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 101, this.yoffset + 34, 176, 32, progress, 16);

        if (this.container.base.getTankAmount1() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank1().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled1(47);
                DrawUtil.drawRepeated(fluidIcon, this.xoffset + 44, this.yoffset + 12 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(getResourceLocation());
                this.drawTexturedModalRect(this.xoffset + 44, this.yoffset + 12, 176, 103, 12, 47);
            }
        }
        if (this.container.base.getTankAmount2() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank2().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled2(47);
                DrawUtil.drawRepeated(fluidIcon, xoffset + 68, yoffset + 12 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(getResourceLocation());
                this.drawTexturedModalRect(xoffset + 68, yoffset + 12, 176, 103, 12, 47);
            }
        }
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIObsidianGenerator.png");
    }
}
