package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerAutoSpawner;
import com.denfop.tiles.base.TileEntityAutoSpawner;
import com.denfop.utils.ExperienceUtils;
import com.denfop.utils.Helpers;
import com.denfop.utils.ModUtils;
import ic2.core.GuiIC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GUIAutoSpawner extends GuiIC2 {
    public final ContainerAutoSpawner container;

    public GUIAutoSpawner(ContainerAutoSpawner container1) {
        super(container1, 214, 176);
        this.container = container1;
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        int[] progress = new int[4];
        for (int i = 0; i < 4; i++) {
            progress[i] = 29 * ((TileEntityAutoSpawner) this.container.base).progress[i] / ((TileEntityAutoSpawner) this.container.base).tempprogress;
            progress[i] = Math.min(progress[i], 29);
            if (progress[i] > 0)
                drawTexturedModalRect(this.xoffset + 177, this.yoffset + 63 + i * 35 - progress[i], 215, 46 - progress[i] + 28, 4, progress[i]);
        }
        int exp = 34 * ((TileEntityAutoSpawner) this.container.base).expstorage / ((TileEntityAutoSpawner) this.container.base).expmaxstorage;
        exp = Math.min(exp, 34);
        if (exp > 0)
            drawTexturedModalRect(this.xoffset + 94, this.yoffset + 80, 216, 35, exp, 2);
        int energy = (int) (34 * ((TileEntityAutoSpawner) this.container.base).energy / ((TileEntityAutoSpawner) this.container.base).maxEnergy);
        energy = Math.min(energy, 34);
        if (energy > 0)
            drawTexturedModalRect(this.xoffset + 134, this.yoffset + 75, 216, 43, energy, 2);
        int energy2 = (int) (34 * ((TileEntityAutoSpawner) this.container.base).energy2 / ((TileEntityAutoSpawner) this.container.base).maxEnergy2);
        energy2 = Math.min(energy2, 34);
        if (energy2 > 0)
            drawTexturedModalRect(this.xoffset + 134, this.yoffset + 88, 216, 39, energy2, 2);


    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        this.fontRendererObj.drawString(ModUtils.getString(ExperienceUtils.getLevelForExperience(((TileEntityAutoSpawner) this.container.base).expstorage)),
                106, 70, Helpers.convertRGBcolorToInt(13, 229, 34));
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, "EU: " + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).energy) + "/" + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).maxEnergy), 133, 74, 168, 77);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, "RF: " + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).energy2) + "/" + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).maxEnergy2), 133, 87, 168, 90);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, "XP: " + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).expstorage) + "/" + ModUtils.getString(((TileEntityAutoSpawner) this.container.base).expmaxstorage), 93, 79, 128, 82);
        for (int i = 0; i < 4; i++) {
            int progress1 = Math.min((100 * ((TileEntityAutoSpawner) this.container.base).progress[i] / ((TileEntityAutoSpawner) this.container.base).tempprogress), 100);

            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("gui.MolecularTransformer.progress") + ": " + ModUtils.getString(progress1) + "%", 177, 33 + i * 35, 180, 63 + i * 35);
        }
    }

    @Override
    public String getName() {
        return StatCollector.translateToLocal("iu.blockSpawner.name");
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIAutoSpawner.png");
    }
}
