package com.denfop.gui;

import com.denfop.Constants;
import cpw.mods.fml.client.config.GuiCheckBox;
import cpw.mods.fml.client.config.GuiSlider;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GUIColorPicker extends GuiScreen {
    protected final EntityPlayer player;
    protected final int xSize = 176;

    protected final int ySize = 166;
    private final ResourceLocation background = new ResourceLocation(Constants.TEXTURES, "textures/gui/Color.png");

    public GUIColorPicker(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        if (player.getEntityData() != null) {
            NBTTagCompound nbt = player.getEntityData();
            this.buttonList.add(new GuiSlider(0, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 65, 160, 20, StatCollector.translateToLocal("iu.red"), "", 0, 255, nbt.getDouble("Red"), false, true));
            this.buttonList.add(new GuiSlider(1, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 95, 160, 20, StatCollector.translateToLocal("iu.green"), "", 0, 255, nbt.getDouble("Green"), false, true));
            this.buttonList.add(new GuiSlider(2, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 125, 160, 20, StatCollector.translateToLocal("iu.blue"), "", 0, 255, nbt.getDouble("Blue"), false, true));
            this.buttonList.add(new GuiCheckBox(3, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 155, StatCollector.translateToLocal("iu.rgb"), nbt.getBoolean("RGB")));

        }
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        String[] name = {"Red", "Green", "Blue"};
        for (int i = 0; i < 3; i++) {
            if (this.buttonList.get(i) instanceof GuiSlider) {
                NBTTagCompound nbt = player.getEntityData();
                GuiSlider slider = (GuiSlider) this.buttonList.get(i);
                nbt.setDouble(name[i], slider.getValue());
            }

        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawGuiContainerBackgroundLayer();
    }

    public void drawTexturedModalRect1(int p_73729_1_, int p_73729_2_, int p_73729_3_, int p_73729_4_, int p_73729_5_, int p_73729_6_) {
        double[] name = new double[3];
        for (int i = 0; i < 3; i++) {

            if (this.buttonList.get(i) instanceof GuiSlider) {
                GuiSlider slider = (GuiSlider) this.buttonList.get(i);
                name[i] = slider.getValue();
            }
        }
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F((float) name[0] / 255, (float) name[1] / 255, (float) name[2] / 255, 1);
        tessellator.addVertexWithUV(p_73729_1_, p_73729_2_ + p_73729_6_, this.zLevel, (float) (p_73729_3_) * f, (float) (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_ + p_73729_6_, this.zLevel, (float) (p_73729_3_ + p_73729_5_) * f, (float) (p_73729_4_ + p_73729_6_) * f1);
        tessellator.addVertexWithUV(p_73729_1_ + p_73729_5_, p_73729_2_, this.zLevel, (float) (p_73729_3_ + p_73729_5_) * f, (float) (p_73729_4_) * f1);
        tessellator.addVertexWithUV(p_73729_1_, p_73729_2_, this.zLevel, (float) (p_73729_3_) * f, (float) (p_73729_4_) * f1);
        tessellator.draw();
    }

    protected void drawGuiContainerBackgroundLayer() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        int xOffset = (this.width - this.xSize) / 2;
        int yOffset = (this.height - this.ySize) / 2;
        this.mc.getTextureManager().bindTexture(this.background);

        this.drawTexturedModalRect1(xOffset, yOffset, 15, 1, 180, 60);

    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (player != null) {
            NBTTagCompound nbt = player.getEntityData();
            if (guibutton instanceof GuiSlider) {
                GuiSlider slider = (GuiSlider) guibutton;
                switch (guibutton.id) {
                    case 0:
                        nbt.setDouble("Red", slider.getValue());
                        break;
                    case 1:
                        nbt.setDouble("Green", slider.getValue());
                        break;
                    case 2:
                        nbt.setDouble("Blue", slider.getValue());
                        break;

                }
            }
            if (guibutton instanceof GuiCheckBox) {
                GuiCheckBox checkbox = (GuiCheckBox) guibutton;
                nbt.setBoolean("RGB", checkbox.isChecked());

            }
        }
    }
}
