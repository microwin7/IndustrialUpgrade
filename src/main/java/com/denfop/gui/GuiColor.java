package com.denfop.gui;

import com.denfop.Constants;
import ic2.core.init.Localization;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.io.IOException;

public class GuiColor extends GuiScreen implements GuiSlider.FormatHelper, GuiPageButtonList.GuiResponder {

    protected final EntityPlayer player;
    protected final int xSize = 176;

    protected final int ySize = 166;
    private final ResourceLocation background = new ResourceLocation(Constants.TEXTURES, "textures/gui/color.png");

    public GuiColor(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void initGui() {
        player.getEntityData();
        NBTTagCompound nbt = player.getEntityData();
        this.buttonList.add(new GuiSlider(this, 0, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 65,
                Localization.translate("iu.red"), 0F, 255F, (float) (nbt.getDouble("Red")), this
        ));
        this.buttonList.add(new GuiSlider(
                this,
                1,
                (this.width - this.xSize) / 2 + 10,
                (this.height - this.ySize) / 2 + 95,
                Localization.translate("iu.green"),
                0,
                255,
                (float) nbt.getDouble("Green"),
                this
        ));
        this.buttonList.add(new GuiSlider(this, 2, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 125,
                Localization.translate("iu.blue"), 0, 255, (float) nbt.getDouble("Blue"), this
        ));
        this.buttonList.add(new GuiCheckBox(3, (this.width - this.xSize) / 2 + 10, (this.height - this.ySize) / 2 + 155,
                Localization.translate(
                        "iu.rgb"), nbt.getBoolean("RGB")
        ));

    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        String[] name = {"Red", "Green", "Blue"};
        for (int i = 0; i < 3; i++) {
            if (this.buttonList.get(i) instanceof GuiSlider) {
                NBTTagCompound nbt = player.getEntityData();
                GuiSlider slider = (GuiSlider) this.buttonList.get(i);
                nbt.setDouble(name[i], slider.getSliderValue());
            }

        }
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {

        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
        this.drawGuiContainerBackgroundLayer();
    }

    public void drawTexturedModalRect1(int x, int y, int textureX, int textureY, int width, int height) {
        float f = 0.00390625F;
        float f1 = 0.00390625F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        double[] name = new double[3];
        for (int i = 0; i < 3; i++) {

            if (this.buttonList.get(i) instanceof GuiSlider) {
                GuiSlider slider = (GuiSlider) this.buttonList.get(i);
                name[i] = slider.getSliderValue();
            }
        }
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.color((float) name[0] / 255, (float) name[1] / 255, (float) name[2] / 255, 1);
        bufferbuilder
                .pos(x, y + height, this.zLevel)
                .tex((float) (textureX + 0) * 0.00390625F, (float) (textureY + height) * 0.00390625F)
                .endVertex();
        bufferbuilder
                .pos(x + width, y + height, this.zLevel)
                .tex((float) (textureX + width) * 0.00390625F, (float) (textureY + height) * 0.00390625F)
                .endVertex();
        bufferbuilder
                .pos(x + width, y + 0, this.zLevel)
                .tex((float) (textureX + width) * 0.00390625F, (float) (textureY + 0) * 0.00390625F)
                .endVertex();
        bufferbuilder
                .pos(x, y + 0, this.zLevel)
                .tex((float) (textureX + 0) * 0.00390625F, (float) (textureY + 0) * 0.00390625F)
                .endVertex();
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
    protected void actionPerformed(GuiButton guibutton) throws IOException {
        super.actionPerformed(guibutton);
        if (player != null) {
            NBTTagCompound nbt = player.getEntityData();
            if (guibutton instanceof GuiSlider) {
                GuiSlider slider = (GuiSlider) guibutton;
                switch (guibutton.id) {
                    case 0:
                        nbt.setDouble("Red", slider.getSliderValue());
                        break;
                    case 1:
                        nbt.setDouble("Green", slider.getSliderValue());
                        break;
                    case 2:
                        nbt.setDouble("Blue", slider.getSliderValue());
                        break;

                }
            }
            if (guibutton instanceof GuiCheckBox) {
                GuiCheckBox checkbox = (GuiCheckBox) guibutton;
                nbt.setBoolean("RGB", checkbox.isChecked());

            }
        }
    }

    @Override
    public void setEntryValue(final int id, final boolean value) {

    }

    @Override
    public void setEntryValue(final int id, final float value) {
        String[] name = {"Red", "Green", "Blue"};
        NBTTagCompound nbt = player.getEntityData();
        GuiSlider slider = (GuiSlider) this.buttonList.get(id);
        nbt.setDouble(name[id], slider.getSliderValue());

    }

    @Override
    public void setEntryValue(final int id, final String value) {

    }

    @Nonnull
    @Override
    public String getText(final int id, final String name, final float value) {
        switch (id) {
            case 0:
                return "Red: " + value;
            case 1:
                return "Blue: " + value;
            case 2:
                return "Green: " + value;
        }
        return "";
    }

}
