package com.denfop.gui;

import com.denfop.container.ContainerPump;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class GUIPump extends GuiIC2 {
    public final ContainerPump container;

    public GUIPump(ContainerPump container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        FluidStack fluidstack = this.container.base.getFluidStackfromTank();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = fluidstack.getFluid().getName() + ": " + fluidstack.amount + StatCollector.translateToLocal("ic2.generic.text.mb");
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 73, 19, 86, 67);
        }

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (24.0F * this.container.base.guiProgress);
        if (chargeLevel > 0) {
            this.drawTexturedModalRect(this.xoffset + 7, this.yoffset + 42 - chargeLevel, 176, 14 - chargeLevel, 14, chargeLevel);
        }

        if (progress > 0) {
            this.drawTexturedModalRect(this.xoffset + 36, this.yoffset + 34, 176, 14, progress + 1, 16);
        }

        if (this.container.base.getTankAmount() > 0) {
            IIcon fluidIcon = this.container.base.getFluidTank().getFluid().getFluid().getIcon();
            if (fluidIcon == null) {
                fluidIcon = new ItemStack(this.container.base.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex();
            }
            if (fluidIcon != null) {
                this.drawTexturedModalRect(this.xoffset + 70, this.yoffset + 16, 176, 30, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled(47);
                DrawUtil.drawRepeated(fluidIcon, this.xoffset + 74, this.yoffset + 20 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(this.getResourceLocation());
                this.drawTexturedModalRect(this.xoffset + 74, this.yoffset + 20, 176, 85, 12, 47);
            }
        }

    }

    public String getName() {
        return StatCollector.translateToLocal("ic2.Pump.gui.name");
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(IC2.textureDomain, "textures/gui/GUIPump.png");
    }
}
