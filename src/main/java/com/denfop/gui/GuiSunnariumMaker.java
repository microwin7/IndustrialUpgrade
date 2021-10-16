package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerSunnariumMaker;
import com.denfop.tiles.base.TileSunnariumMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUISunnariumMaker extends GuiIC2 {
    public final ContainerSunnariumMaker<? extends TileSunnariumMaker> container;

    public GUISunnariumMaker(ContainerSunnariumMaker<? extends TileSunnariumMaker> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (17 * this.container.base.getProgress());
        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 12, this.yoffset + 61 + 1 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 55, this.yoffset + 20, 176, 31, progress + 1, 31);
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUISunnariumMaker.png");
    }
}
