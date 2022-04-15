package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerSolidMatter;
import com.denfop.tiles.base.TileMatterGenerator;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUISolidMatter extends GuiIC2 {
    public final ContainerSolidMatter<? extends TileMatterGenerator> container;

    public GUISolidMatter(ContainerSolidMatter<? extends TileMatterGenerator> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int progress = (int) (27.0D * (this.container.base).getProgress());

        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 62, this.yoffset + 26, 176, 7 + 28 * this.container.base.getBlockMetadata(), 29, progress + 1);
        String progress1 = I18n.format("gui.MolecularTransformer.progress") + ": ";
        this.fontRendererObj.drawString(progress1 + ModUtils.getString(this.container.base.getProgress() * 100) + "%", this.xoffset + 5, this.yoffset + 36, 4210752);

    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUISolidMatter.png");
    }
}
