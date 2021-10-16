package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerPlasticCreator;
import com.denfop.tiles.mechanism.TileEntityPlasticCreator;
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
public class GUIPlasticCreator extends GuiIC2 {
    public final ContainerPlasticCreator<? extends TileEntityPlasticCreator> container;

    public GUIPlasticCreator(ContainerPlasticCreator<? extends TileEntityPlasticCreator> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        FluidStack fluidstack = this.container.base.getFluidTank().getFluid();
        if (fluidstack != null && fluidstack.getFluid() != null) {
            String tooltip = StatCollector.translateToLocal(fluidstack.getUnlocalizedName()) + ": " + fluidstack.amount + "mB";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 10, 9, 20, 57);


        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (24.0F * this.container.base.getProgress());
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 56 + 1, this.yoffset + 36 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 79, this.yoffset + 34, 176, 14, progress + 1, 16);
        if (this.container.base.getFluidTank().getFluidAmount() > 0) {
            IIcon fluidIcon = new ItemStack(this.container.base.getFluidTank().getFluid().getFluid().getBlock()).getIconIndex();
            if (fluidIcon != null) {
                this.mc.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
                int liquidHeight = this.container.base.gaugeLiquidScaled(47);
                DrawUtil.drawRepeated(fluidIcon, this.xoffset + 10, this.yoffset + 9 + 47 - liquidHeight, 12.0D, liquidHeight, this.zLevel);
                this.mc.renderEngine.bindTexture(getResourceLocation());
                this.drawTexturedModalRect(this.xoffset + 10, this.yoffset + 9, 176, 103, 12, 47);
            }
        }
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIPlastic.png");
    }
}
