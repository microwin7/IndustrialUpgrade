package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerStorageExp;
import com.denfop.tiles.base.TileEntityStorageExp;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;


public class GUIStorageExp extends GuiIC2 {
    public final ContainerStorageExp container;

    public GUIStorageExp(ContainerStorageExp container1) {
        super(container1);
        this.container = container1;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 38, (this.height - this.ySize) / 2 + 61,
                74, 16, I18n.format("button.xpremove")));
        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + 38, (this.height - this.ySize) / 2 + 17,
                74, 16, I18n.format("button.xpadd")));

    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get().initiateClientTileEntityEvent(((TileEntityStorageExp) this.container.base), 0);

        }
        if (guibutton.id == 1) {
            IC2.network.get().initiateClientTileEntityEvent(((TileEntityStorageExp) this.container.base), 1);

        }
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        this.fontRendererObj.drawString("Lvl:" + ((TileEntityStorageExp) this.container.base).expirencelevel, 100, 51 - 5, 4210752);
        this.fontRendererObj.drawString("Lvl:" + ((TileEntityStorageExp) this.container.base).expirencelevel1, 31, 51 - 5, 4210752);


    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);
        int chargeLevel = (int) (47.0F * ((TileEntityStorageExp) this.container.base).storage
                / ((TileEntityStorageExp) this.container.base).maxStorage);
        int chargeLevel1 = (int) (47.0F * ((TileEntityStorageExp) this.container.base).storage1
                / ((TileEntityStorageExp) this.container.base).maxStorage);
        chargeLevel = Math.min(chargeLevel, 47);
        chargeLevel1 = Math.min(chargeLevel1, 47);
        if (chargeLevel > 0)

            drawTexturedModalRect(this.xoffset + 153, this.yoffset + 26 + 47 - chargeLevel, 180,
                    51 - chargeLevel, 12, chargeLevel);

        if (chargeLevel1 > 0)
            drawTexturedModalRect(this.xoffset + 11, this.yoffset + 26 + 47 - chargeLevel1, 180,
                    51 - chargeLevel1, 12, chargeLevel1);

    }


    public String getName() {
        return container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIExpStorage.png");
    }
}
