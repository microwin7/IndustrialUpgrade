package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.tiles.mechanism.TileEntitySunnariumPanelMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUISunnariumPanelMaker extends GuiIC2 {
    public final ContainerDoubleElectricMachine<? extends TileEntitySunnariumPanelMaker> container;

    public GUISunnariumPanelMaker(ContainerDoubleElectricMachine<? extends TileEntitySunnariumPanelMaker> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (14 * this.container.base.getProgress());

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 25, this.yoffset + 57 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 74, this.yoffset + 34, 177, 32, progress + 1, 15);

    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUISunnariumPanelMaker.png");
    }
}
