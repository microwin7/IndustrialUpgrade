package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerMagnet;
import com.denfop.tiles.mechanism.TileEntityMagnet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIMagnet extends GuiIC2 {
    public final ContainerMagnet container;

    public GUIMagnet(ContainerMagnet container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);

        int chargeLevel = (int) (48.0F * ((TileEntityMagnet) this.container.base).getEnergy()
                / ((TileEntityMagnet) this.container.base).maxEnergy);

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 140 + 1 + 5, this.yoffset + 28 + 48 - chargeLevel, 176,
                    48 - chargeLevel, 48, chargeLevel);

    }


    public String getName() {
        return null;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMagnet.png");
    }
}
