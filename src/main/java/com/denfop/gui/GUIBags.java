//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerBags;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIBags extends GuiContainer {
    private static final ResourceLocation background;

    static {
        background = new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIBags.png");
    }

    public ContainerBags container;
    public String name;

    public GUIBags(ContainerBags container1, ItemStack stack) {
        super(container1);
        this.container = container1;
        this.name = StatCollector.translateToLocal(stack.getUnlocalizedName() + ".name");
        this.ySize = 232;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2 - 10, 11, 0);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(background);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
        int slots = this.container.inventorySize;
        slots = slots / 9;

        int col;
        for (col = 0; col < slots; ++col) {
            for (int col1 = 0; col1 < 9; ++col1) {
                this.drawTexturedModalRect(j + 7 + col1 * 18, k + 23 + col * 18, 176, 0, 18, 18);
            }
        }
    }
}
