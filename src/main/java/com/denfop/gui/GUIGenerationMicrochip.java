package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerBaseGenerationChipMachine;
import com.denfop.tiles.mechanism.TileEntityGenerationMicrochip;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUIGenerationMicrochip extends GuiIC2 {
    public final ContainerBaseGenerationChipMachine<? extends TileEntityGenerationMicrochip> container;

    public GUIGenerationMicrochip(
            ContainerBaseGenerationChipMachine<? extends TileEntityGenerationMicrochip> container1) {
        super(container1);
        this.container = container1;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("iu.temperature") + ModUtils.getString(this.container.base.getTemperature()) + "/" + ModUtils.getString(this.container.base.getMaxTemperature()), 70, 62, 108, 73);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (15.0F * this.container.base.getProgress());
        int progress1 = (int) (10.0F * this.container.base.getProgress());
        int progress2 = (int) (19.0F * this.container.base.getProgress());
        int temperature = 38 * this.container.base.getTemperature() / this.container.base.getMaxTemperature();
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 6, this.yoffset + 76 - 13 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 27, this.yoffset + 13, 176, 34, progress + 1, 28);
        if (progress1 > 0)
            drawTexturedModalRect(this.xoffset + 60, this.yoffset + 17, 176, 64, progress1 + 1, 19);
        if (progress2 > 0)
            drawTexturedModalRect(this.xoffset + 88, this.yoffset + 23, 176, 85, progress2 + 1, 7);
        if (temperature > 0)
            drawTexturedModalRect(this.xoffset + 70, this.yoffset + 62, 176, 20, temperature + 1, 11);

    }

    public String getName() {
        return "";
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUICirsuit.png");
    }
}
