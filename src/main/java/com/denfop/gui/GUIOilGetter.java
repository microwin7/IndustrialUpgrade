package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerOilGetter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class GUIOilGetter extends GuiIC2 {
    public final ContainerOilGetter container;


    public GUIOilGetter(ContainerOilGetter container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(getName(), (this.xSize - this.fontRendererObj.getStringWidth(getName())) / 2, 6, 4210752);


        super.drawGuiContainerForegroundLayer(par1, par2);
        FluidStack fluidstack = this.container.base.getFluidStackfromTank();
        if (fluidstack != null) {
            String tooltip = StatCollector.translateToLocal("iu.fluidneft") + ": " + fluidstack.amount
                    + StatCollector.translateToLocal("ic2.generic.text.mb");
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 99, 25, 112, 73);
        }
        String tooltip;
        if (!this.container.base.notoil) {


            tooltip = StatCollector.translateToLocal("iu.fluidneft") + ": " + this.container.base.number + "/" + this.container.base.max
                    + StatCollector.translateToLocal("ic2.generic.text.mb");
        } else {
            tooltip = StatCollector.translateToLocal("iu.notfindoil");

        }
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 43, 39, 52, 53);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);

        if (this.container.base.getTankAmount() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                drawTexturedModalRect(this.xoffset + 96, this.yoffset + 22, 176, 0, 20, 55);
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled(47);
                DrawUtil.drawRepeated(fluidIcon, (this.xoffset + 100), (this.yoffset + 26 + 47 - liquidHeight), 12.0D,
                        liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(getResourceLocation());
                drawTexturedModalRect(this.xoffset + 100, this.yoffset + 26, 176, 55, 12, 47);
            }
        }
        int temp = 0;
        if (this.container.base.max > 0)
            temp = 14 * this.container.base.number / this.container.base.max;
        temp = Math.min(14, temp);
        if (temp > 0) {
            drawTexturedModalRect(this.xoffset + 43, this.yoffset + 39 + 14 - temp, 177, 130 - temp, 10, temp);

        }
    }

    public String getName() {
        return StatCollector.translateToLocal("BlockOilGetter.name");
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIOilGetter.png");
    }
}
