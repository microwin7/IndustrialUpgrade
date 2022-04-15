package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerConverterSolidMatter;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIConverterSolidMatter extends GuiContainer {
    private static final ResourceLocation background = new ResourceLocation(Constants.TEXTURES,
            "textures/gui/GUIConverterSolidMatter.png");
    final EnumChatFormatting[] name = {EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.BLUE, EnumChatFormatting.RED, EnumChatFormatting.GRAY, EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.AQUA};
    private final ContainerConverterSolidMatter container;

    public GUIConverterSolidMatter(ContainerConverterSolidMatter container1) {
        super(container1);
        this.ySize = 240;
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.getName(), (this.xSize - this.fontRendererObj.getStringWidth(this.getName())) / 2, 6, 4210752);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, "EU: " + ModUtils.getString((this.container.base).energy) + "/" + ModUtils.getString((this.container.base).maxEnergy), 119, 114, 157, 126);


        for (int i = 0; i < container.base.quantitysolid.length; i++) {
            String tooltip1 = container.base.quantitysolid[i] + "/" + 5000;
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, name[i] + tooltip1, 23, 20 + 15 * i, 40, 28 + 15 * i);

        }
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ModUtils.getString(this.container.base.getProgress() * 100) + "%", 78, 50, 111, 67);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ModUtils.getString(this.container.base.getProgress() * 100) + "%", 138, 50, 171, 67);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ModUtils.getString(this.container.base.getProgress() * 100) + "%", 116, 16, 133, 49);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, ModUtils.getString(this.container.base.getProgress() * 100) + "%", 116, 68, 133, 101);


    }

    private String getName() {
        return StatCollector.translateToLocal("blockConverterSolidMatter.name");
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        for (int i = 0; i < container.base.quantitysolid.length; i++) {
            double p = ((container.base.quantitysolid[i] / 5000) * 11);
            int l = i - 6;
            if (l != 1)
                l = 0;

            drawTexturedModalRect((int) (j + 26 + p), k + 25 + 15 * i - l, 182, 12, 1, 3);


        }
        double temp = container.base.getProgress();
        if (temp > 0) {
            temp *= 31;
            drawTexturedModalRect(j + 79, k + 51, 176, 24, (int) temp, 15);
            drawTexturedModalRect((int) ((j + 171) - temp), k + 51, (int) (208 - temp), 24, (int) temp, 15);

            drawTexturedModalRect(j + 116, k + 16 + 1, 176, 42, 17, (int) temp);

            drawTexturedModalRect(j + 116, (int) (k + 101 - temp), 176, (int) (74 - temp), 17, (int) temp);

        }
        double energy = ((this.container.base).energy / (this.container.base).maxEnergy) * 38;
        if (energy > 0)
            drawTexturedModalRect(j + 119, k + 115, 176,
                    81, (int) energy, 11);
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
    }
}
