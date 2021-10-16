package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.tiles.mechanism.TileEntitySynthesis;
import com.denfop.utils.Helpers;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeOutput;
import ic2.core.GuiIC2;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUISynthesis extends GuiIC2 {
    public final ContainerDoubleElectricMachine<? extends TileEntitySynthesis> container;

    public GUISynthesis(ContainerDoubleElectricMachine<? extends TileEntitySynthesis> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (10 * this.container.base.getProgress());
        int progress1 = (int) (24 * this.container.base.getProgress());

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 24, this.yoffset + 56 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        if (progress > 0)
            drawTexturedModalRect(this.xoffset + 39, this.yoffset + 37, 177, 35, progress + 1, 9);
        if (progress1 > 0)
            drawTexturedModalRect(this.xoffset + 82, this.yoffset + 30, 177, 52, progress1 + 1, 23);
        RecipeOutput output = Recipes.synthesis.getOutputFor(this.container.base.inputSlotA.get(0), this.container.base.inputSlotA.get(1), false, false);
        if (output != null) {
            this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("chance") + output.metadata.getInteger("percent") + "%", this.xoffset + 69,
                    this.yoffset + 67, Helpers.convertRGBcolorToInt(217, 217, 217));
        }
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUISynthesis.png");
    }
}
