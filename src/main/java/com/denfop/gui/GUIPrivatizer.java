package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerPrivatizer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIPrivatizer extends GuiIC2 {
    public final ContainerPrivatizer container;

    public GUIPrivatizer(ContainerPrivatizer container1) {
        super(container1);
        this.container = container1;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 103, (this.height - this.ySize) / 2 + 21,
                68, 17, I18n.format("button.write")));
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        this.mc.getTextureManager().bindTexture(getResourceLocation());

    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

    }


    public String getName() {
        return this.container.base.getInventoryName();
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get().initiateClientTileEntityEvent((TileEntity) this.container.base, 0);

        }
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIPrivatizer.png");
    }
}
