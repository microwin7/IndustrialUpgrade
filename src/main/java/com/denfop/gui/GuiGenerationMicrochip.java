package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerBaseGenerationChipMachine;
import com.denfop.tiles.mechanism.TileEntityGenerationMicrochip;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GuiGenerationMicrochip extends GuiIC2 {
	public final ContainerBaseGenerationChipMachine<? extends TileEntityGenerationMicrochip> container;

	public GuiGenerationMicrochip(
			ContainerBaseGenerationChipMachine<? extends TileEntityGenerationMicrochip> container1) {
		super(container1);
		this.container = container1;
	}

	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		super.drawGuiContainerBackgroundLayer(f, x, y);
		int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
		int progress = (int) (15.0F * this.container.base.getProgress());
		int progress1 = (int) (10.0F * this.container.base.getProgress());
		int progress2 = (int) (19.0F * this.container.base.getProgress());

		if (chargeLevel > 0)
			drawTexturedModalRect(this.xoffset + 6, this.yoffset + 76-13 + 14 - chargeLevel, 176, 14 - chargeLevel,
					14, chargeLevel);
		if (progress > 0)
			drawTexturedModalRect(this.xoffset + 27, this.yoffset + 13, 176, 34, progress + 1, 28);
		if (progress1 > 0)
			drawTexturedModalRect(this.xoffset + 60, this.yoffset + 17, 176, 64, progress1 + 1, 19);
		if (progress2 > 2)
			drawTexturedModalRect(this.xoffset + 88, this.yoffset + 23, 176, 85, progress2 + 1, 7);

	}

	public String getName() {
		return "";
	}

	public ResourceLocation getResourceLocation() {
		return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUICirsuit.png");
	}
}
