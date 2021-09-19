package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerCombinerSolidMatter;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;


public class GUICombinerSolidMatter extends GuiIC2 {
    public final ContainerCombinerSolidMatter container;

    public GUICombinerSolidMatter(ContainerCombinerSolidMatter container1) {
        super(container1);
        this.container = container1;
    }


    protected void drawGuiContainerForegroundLayer(int par1, int par2) {

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);


    }


    public String getName() {
        return container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUICombineSolidMatter.png");
    }
}
