package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerHandlerHeavyOre;
import com.denfop.tiles.mechanism.TileEntityHandlerHeavyOre;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUIHandlerHeavyOre extends GuiIC2 {
    public final ContainerHandlerHeavyOre<? extends TileEntityHandlerHeavyOre> container;

    public GUIHandlerHeavyOre(ContainerHandlerHeavyOre<? extends TileEntityHandlerHeavyOre> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.getName(), (this.xSize - this.fontRendererObj.getStringWidth(this.getName())) / 2, 3, 4210752);
        if (this.container.base instanceof IUpgradableBlock) {
            GuiTooltipHelper.drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 0, 0, 12, 12, this.container.base, 25, 0);
        }
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("iu.temperature") + ModUtils.getString(this.container.base.getTemperature()) + "/" + ModUtils.getString(this.container.base.getMaxTemperature()), 51, 52, 89, 63);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (44 * this.container.base.getProgress());
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 24, this.yoffset + 56 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 48, this.yoffset + 31, 177, 32, progress + 1, 14);
        int temperature = 38 * this.container.base.getTemperature() / this.container.base.getMaxTemperature();
        if (temperature > 0)
            drawTexturedModalRect(this.xoffset + 51, this.yoffset + 52, 176, 50, temperature + 1, 11);

    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIHandlerHO.png");
    }
}
