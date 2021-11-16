package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerElectricLather;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ILatheItem;
import ic2.core.GuiIC2;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIElectricLather extends GuiIC2 {
    private final ContainerElectricLather container;

    public GUIElectricLather(ContainerElectricLather container) {
        super(container);
        this.container = container;
    }

    public static void renderILatheItemIntoGUI(ItemStack stack, int posX, int posY) {
        ILatheItem i = (ILatheItem) stack.getItem();
        int segLength = 24;
        int[] state = i.getCurrentState(stack);
        Minecraft.getMinecraft().renderEngine.bindTexture(i.getTexture(stack));

        for (int j = 0; j < 5; ++j) {
            int segWidth = (int) (32.0F / (float) i.getWidth(stack) * (float) state[j] + 0.5F);
            int offset = (int) ((float) (32 - segWidth) / 2.0F + 0.5F);
            drawTexturedModalRectOV(posX + segLength * j, posY + offset, segLength * j, offset, segLength, segWidth);
        }

    }

    private static void drawTexturedModalRectOV(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(p_73729_1_, (p_73729_2_ + p_73729_6_), 1.0D, ((float) (p_73729_3_) * f), ((float) (p_73729_4_ + p_73729_6_) * f1));
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, (p_73729_2_ + p_73729_6_), 1.0D, ((float) (p_73729_3_ + p_73729_5_) * f), (float) (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, (p_73729_2_), 1.0D, ((float) (p_73729_3_ + p_73729_5_) * f), (float) (p_73729_4_) * f1);
        tessellator.addVertexWithUV(p_73729_1_, p_73729_2_, 1.0D, ((float) (p_73729_3_) * f), ((float) (p_73729_4_) * f1));
        tessellator.draw();
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.container.base.latheSlot.get() != null && (this.container.base).latheSlot.get().getItem() instanceof ILatheItem) {
            ItemStack stack = (this.container.base).latheSlot.get();
            renderILatheItemIntoGUI(stack, 40, 22);
            ILatheItem i = (ILatheItem) stack.getItem();
            int segLength = 24;
            int[] state = i.getCurrentState(stack);
            int max = i.getWidth(stack);

            for (int j = 0; j < 5; ++j) {
                Minecraft.getMinecraft().fontRenderer.drawString(StatCollector.translateToLocalFormatted("ic2.Lathe.gui.info", state[j], max), 40 + segLength * j, 14, 4210752);
            }
        }


        GL11.glPopMatrix();
    }

    public String getName() {
        return StatCollector.translateToLocal("ic2.Lathe.gui.name");
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIAutoLathe.png");
    }


}
