package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerBaseWitherMaker;
import com.denfop.tiles.base.TileEntityBaseWitherMaker;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIWitherMaker extends GuiIC2 {
    public final ContainerBaseWitherMaker<? extends TileEntityBaseWitherMaker> container;

    public GUIWitherMaker(
            ContainerBaseWitherMaker<? extends TileEntityBaseWitherMaker> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (40 * this.container.base.getProgress());

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 79, this.yoffset + 51 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 81, this.yoffset + 16, 177, 19, progress + 1, 18);

    }

    public String getName() {
        return "";
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIWitherMaker.png");
    }
}
