package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.IUItem;
import com.denfop.container.ContainerAnalyzer;
import com.denfop.tiles.base.TileEntityAnalyzer;
import com.denfop.utils.Helpers;
import com.denfop.utils.ListInformation;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.client.config.GuiSlider;
import ic2.core.IC2;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;


public class GUIAnalyzer extends GuiContainer {
    public final ContainerAnalyzer container;
    public final String name;
    private final ResourceLocation background;
    private int xOffset;
    private int yOffset;


    public GUIAnalyzer(ContainerAnalyzer container1) {
        super(container1);
        this.background = new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIAnalyzer.png");
        this.container = container1;
        this.name = StatCollector.translateToLocal("iu.blockAnalyzer.name");
        this.ySize = 256;
        this.xSize = 212;
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.analyzerinformation"));
            List<String> compatibleUpgrades = ListInformation.analyzeinform;
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 60, y - 70, yoffset, xoffset, StatCollector.translateToLocal("iu.analyzerinformation"), true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x - 60, y - 70, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    //
    public static void drawUpgradeslotTooltip1(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset, String tooltip, String tooltip1, String tooltip2, String tooltip3, String tooltip4) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(tooltip);
            List<String> compatibleUpgrades = getInformation1(tooltip1, tooltip2, tooltip3, tooltip4);
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 30, y, yoffset, xoffset, tooltip, true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x - 30, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    public static void drawUpgradeslotTooltip2(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset, String tooltip, String tooltip1, String tooltip2, String tooltip3, String tooltip4) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(tooltip);
            List<String> compatibleUpgrades = getInformation1(tooltip1, tooltip2, tooltip3, tooltip4);
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x, y, yoffset, xoffset, tooltip, true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    private static List<String> getInformation1(String name, String name1, String name2, String name3) {
        List<String> ret = new ArrayList();
        ret.add(name);
        ret.add(name1);
        ret.add(name2);
        ret.add(name3);

        return ret;
    }

    public void initGui() {
        super.initGui();
        this.buttonList.add(new GuiButton(0, (this.width - this.xSize) / 2 + 22, (this.height - this.ySize) / 2 + 132,
                74, 16, I18n.format("button.analyzer")));
        this.buttonList.add(new GuiButton(1, (this.width - this.xSize) / 2 + 22, (this.height - this.ySize) / 2 + 152,
                74, 16, I18n.format("button.quarry")));

    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.name, (this.xSize - this.fontRendererObj.getStringWidth(this.name)) / 2, 3, 4210752);
        xOffset = (this.width - this.xSize) / 2;
        yOffset = (this.height - this.ySize) / 2;


        int i2;
        int chunk = ((TileEntityAnalyzer) this.container.base).xChunk;
        int chunk1 = ((TileEntityAnalyzer) this.container.base).zChunk;
        int endchunk = ((TileEntityAnalyzer) this.container.base).xendChunk;
        int endchunk1 = ((TileEntityAnalyzer) this.container.base).zendChunk;

        this.fontRendererObj.drawString(StatCollector.translateToLocal("startchunk") +
                        "X:" + chunk + " Z:" + chunk1,
                10, +18, Helpers.convertRGBcolorToInt(13, 229, 34));
        this.fontRendererObj.drawString(StatCollector.translateToLocal("endchunk") +
                        "X:" + endchunk + " Z:" + endchunk1,
                10, 39, Helpers.convertRGBcolorToInt(13, 229, 34));

        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("analyze") +
                        EnumChatFormatting.WHITE + ModUtils.getString(((TileEntityAnalyzer) this.container.base).breakblock),
                10, 80 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));
        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("ore") +
                        EnumChatFormatting.WHITE + ModUtils.getString(((TileEntityAnalyzer) this.container.base).sum),
                10, 80 + 8 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));

        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("procent_ore") +
                        EnumChatFormatting.WHITE + ModUtils.getString1((((TileEntityAnalyzer) this.container.base).sum / ((TileEntityAnalyzer) this.container.base).breakblock) * 100) + "%",
                10, 80 + 8 + 8 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));
        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("middleheight") +
                        EnumChatFormatting.WHITE + ModUtils.getString1(((double) ((TileEntityAnalyzer) this.container.base).sum1 / ((TileEntityAnalyzer) this.container.base).sum)),
                10, 80 + 8 + 8 + 8 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));
        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("cost.name") +
                        EnumChatFormatting.WHITE + ModUtils.getString(((TileEntityAnalyzer) this.container.base).sum * ((TileEntityAnalyzer) this.container.base).inputslot.getenergycost()) + " EU",
                10, 80 + 8 + 8 + 8 + 8 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));
        this.fontRendererObj.drawString(EnumChatFormatting.GREEN + StatCollector.translateToLocal("cost.name1") +
                        EnumChatFormatting.WHITE + ModUtils.getString1(((TileEntityAnalyzer) this.container.base).inputslot.getenergycost()) + "EU",
                10, 80 + 8 + 8 + 8 + 8 + 8 - 2, Helpers.convertRGBcolorToInt(217, 217, 217));

        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, StatCollector.translateToLocal("gui.MolecularTransformer.progress") + ": " + ModUtils.getString(((TileEntityAnalyzer) this.container.base).getProgress() * 100) + "%", 101, 159, 139, 170);
        GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, "EU: " + ModUtils.getString(((TileEntityAnalyzer) this.container.base).energy) + "/" + ModUtils.getString(((TileEntityAnalyzer) this.container.base).maxEnergy), 148, 159, 186, 170);
        if (!(((TileEntityAnalyzer) this.container.base).inputslotA.isEmpty())) {
            if (!(((TileEntityAnalyzer) this.container.base).listore.isEmpty())) {
                int id = OreDictionary.getOreID(((TileEntityAnalyzer) this.container.base).inputslotA.get(0));
                String name = OreDictionary.getOreName(id);
                if (((TileEntityAnalyzer) this.container.base).listore.contains(name)) {
                    int index = ((TileEntityAnalyzer) this.container.base).listore.indexOf(name);
                    ItemStack stack = OreDictionary.getOres(OreDictionary.getOreID(((TileEntityAnalyzer) this.container.base).listore.get(index))).get(0);

                    String tooltip1 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("chance.ore") + EnumChatFormatting.WHITE + (((TileEntityAnalyzer) this.container.base).listnumberore.get(index) - 1) + ".";
                    double number = (((TileEntityAnalyzer) this.container.base).listnumberore.get(index) - 1);
                    double sum = ((TileEntityAnalyzer) this.container.base).sum;
                    double m = (number / sum) * 100;
                    String tooltip2 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("chance.ore1") + EnumChatFormatting.WHITE + ModUtils.getString1(m) + "%" + ".";

                    String tooltip = EnumChatFormatting.GREEN + StatCollector.translateToLocal("name.ore") + EnumChatFormatting.WHITE + stack.getDisplayName();
                    String tooltip3 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("middleheight") + EnumChatFormatting.WHITE + ModUtils.getString1(((TileEntityAnalyzer) this.container.base).middleheightores.get(index)) + ".";
                    String tooltip4 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("cost.name") + EnumChatFormatting.WHITE + ModUtils.getString((((TileEntityAnalyzer) this.container.base).listnumberore.get(index) - 1) * ((TileEntityAnalyzer) this.container.base).inputslot.getenergycost()) + "EU";


                    drawUpgradeslotTooltip2(par1 - this.guiLeft, par2 - this.guiTop, 77, 55, 94, 72,
                            25, 0, tooltip, tooltip1, tooltip2, tooltip3, tooltip4);

                }

            }
        }
        for (i2 = 0; i2 < Math.min(((TileEntityAnalyzer) this.container.base).numberores, 48); i2++) {
            int k = i2 / 6;
            ItemStack stack = OreDictionary.getOres(OreDictionary.getOreID(((TileEntityAnalyzer) this.container.base).listore.get(i2))).get(0);
            String tooltip1 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("chance.ore") + EnumChatFormatting.WHITE + (((TileEntityAnalyzer) this.container.base).listnumberore.get(i2) - 1) + ".";
            double number = (((TileEntityAnalyzer) this.container.base).listnumberore.get(i2) - 1);
            double sum = ((TileEntityAnalyzer) this.container.base).sum;
            double m = (number / sum) * 100;
            String tooltip2 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("chance.ore1") + EnumChatFormatting.WHITE + ModUtils.getString1(m) + "%" + ".";

            String tooltip = EnumChatFormatting.GREEN + StatCollector.translateToLocal("name.ore") + EnumChatFormatting.WHITE + stack.getDisplayName();
            String tooltip3 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("middleheight") + EnumChatFormatting.WHITE + ModUtils.getString1(((TileEntityAnalyzer) this.container.base).middleheightores.get(i2)) + ".";
            String tooltip4 = EnumChatFormatting.GREEN + StatCollector.translateToLocal("cost.name") + EnumChatFormatting.WHITE + ModUtils.getString((((TileEntityAnalyzer) this.container.base).listnumberore.get(i2) - 1) * ((TileEntityAnalyzer) this.container.base).inputslot.getenergycost()) + "EU";


            drawUpgradeslotTooltip1(par1 - this.guiLeft, par2 - this.guiTop, 99 + (i2 - (6 * k)) * 18, 13 + k * 18, 99 + (i2 - (6 * k)) * 18 + 16, 13 + k * 18 + 16,
                    25, 0, tooltip, tooltip1, tooltip2, tooltip3, tooltip4);
        }
        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 5, 173, 22, 190,
                25, 0);
    }

    //
    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get().initiateClientTileEntityEvent(((TileEntityAnalyzer) this.container.base), 0);

        }
        if (guibutton.id == 1) {
            IC2.network.get().initiateClientTileEntityEvent(((TileEntityAnalyzer) this.container.base), 1);

        }

        if (guibutton.id == 2) {
            GuiSlider b = (GuiSlider) guibutton;

            ((TileEntityAnalyzer) this.container.base).breakblock = (int) b.getValue();
            IC2.network.get().initiateClientTileEntityEvent(((TileEntityAnalyzer) this.container.base), 1);

        }
    }

    protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(this.background);


        xOffset = (this.width - this.xSize) / 2;
        yOffset = (this.height - this.ySize) / 2;

        this.drawTexturedModalRect(xOffset, yOffset, 0, 0, this.xSize, this.ySize);
        double progress = ((TileEntityAnalyzer) this.container.base).getProgress() * 38;

        if (progress > 0)
            drawTexturedModalRect(this.xOffset + 101, this.yOffset + 159, 212,
                    24, (int) progress, 11);
        double energy = (((TileEntityAnalyzer) this.container.base).energy / ((TileEntityAnalyzer) this.container.base).maxEnergy) * 38;
        if (energy > 0)
            drawTexturedModalRect(this.xOffset + 148, this.yOffset + 159, 212,
                    36, (int) energy, 11);

        int i2;

        for (i2 = ((TileEntityAnalyzer) this.container.base).numberores; i2 < 48; i2++) {
            int k = i2 / 6;

            this.drawTexturedModalRect(xOffset + 99 + (i2 - (6 * k)) * 18, yOffset + 13 + k * 18, 213, 1, 16, 16);

        }
        for (i2 = 0; i2 < Math.min(((TileEntityAnalyzer) this.container.base).numberores, 48); i2++) {
            int k = i2 / 6;
            GL11.glPushMatrix();
            GL11.glColor4f(0.1F, 1, 0.1F, 1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            itemRender.zLevel = 100;
            mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

            itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), OreDictionary.getOres(OreDictionary.getOreID(((TileEntityAnalyzer) this.container.base).listore.get(i2))).get(0), xOffset + 99 + (i2 - (6 * k)) * 18, yOffset + 13 + k * 18);

            GL11.glColor4f(0.1F, 1, 0.1F, 1);
            GL11.glPopMatrix();

        }


        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        itemRender.zLevel = 100;
        itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(IUItem.basemachine1, 1, 2), xOffset + 5, yOffset + 133);

        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glColor4f(1, 1, 1, 1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        itemRender.zLevel = 100;
        itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), new ItemStack(IUItem.machines, 1, 8), xOffset + 5, yOffset + 133 + 20);

        GL11.glEnable(GL11.GL_LIGHTING);

        GL11.glPopMatrix();

    }


}
