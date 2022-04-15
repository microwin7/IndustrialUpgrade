package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.item.ItemPaints;
import com.denfop.tiles.base.TileEntityPainting;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.GuiIC2;
import ic2.core.IC2;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static ic2.core.util.GuiTooltipHelper.drawTooltip;

@SideOnly(Side.CLIENT)
public class GUIPainting extends GuiIC2 {
    public final ContainerDoubleElectricMachine<? extends TileEntityPainting> container;

    public GUIPainting(ContainerDoubleElectricMachine<? extends TileEntityPainting> container1) {
        super(container1);
        this.container = container1;
    }

    public static void drawUpgradeslotTooltip(int x, int y, int minX, int minY, int maxX, int maxY, int yoffset, int xoffset) {
        if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int width = fontRenderer.getStringWidth(StatCollector.translateToLocal("iu.paintinginformation"));
            List<String> compatibleUpgrades = getInformation();
            Iterator var12 = compatibleUpgrades.iterator();

            String itemstack;
            while (var12.hasNext()) {
                itemstack = (String) var12.next();
                if (fontRenderer.getStringWidth(itemstack) > width) {
                    width = fontRenderer.getStringWidth(itemstack);
                }
            }

            drawTooltip(x - 120, y, yoffset, xoffset, StatCollector.translateToLocal("iu.paintinginformation"), true, width);
            yoffset += 15;

            for (var12 = compatibleUpgrades.iterator(); var12.hasNext(); yoffset += 14) {
                itemstack = (String) var12.next();
                drawTooltip(x - 120, y, yoffset, xoffset, itemstack, false, width);
            }
        }

    }

    private static List<String> getInformation() {
        List<String> ret = new ArrayList();
        ret.add(StatCollector.translateToLocal("iu.paintinginformation1"));
        ret.add(StatCollector.translateToLocal("iu.paintinginformation2"));
        ret.add(StatCollector.translateToLocal("iu.paintinginformation3"));
        ret.add(StatCollector.translateToLocal("iu.paintinginformation4"));
        ret.add(StatCollector.translateToLocal("iu.paintinginformation5"));


        return ret;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(this.getName(), (this.xSize - this.fontRendererObj.getStringWidth(this.getName())) / 2, 6, 4210752);
        if (this.container.base instanceof IUpgradableBlock) {
            GuiTooltipHelper.drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 0, 0, 12, 12, this.container.base, 25, 0);
        }
        drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 165, 0, 175, 12,
                25, 0);
    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);
        this.mc.getTextureManager()
                .bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
        drawTexturedModalRect(this.xoffset + 165, this.yoffset, 0, 0, 10, 10);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        if (this.container.base instanceof IUpgradableBlock) {
            this.mc.getTextureManager().bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
            this.drawTexturedModalRect(this.xoffset + 3, this.yoffset + 3, 0, 0, 10, 10);
            this.mc.getTextureManager().bindTexture(this.getResourceLocation());
        }
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (14 * this.container.base.getProgress());

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 25, this.yoffset + 57 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);
        int down;
        ItemStack stack = null;
        if (this.container.base.inputSlotA.get(0) != null)
            stack = this.container.base.inputSlotA.get(0).getItem() instanceof ItemPaints ? this.container.base.inputSlotA.get(0) : this.container.base.inputSlotA.get(1);
        if (stack == null)
            down = 0;
        else {
            down = 14 * (stack.getItemDamage() - 1);
        }

        if (progress > 0 && down >= 0)
            drawTexturedModalRect(this.xoffset + 75, this.yoffset + 35, 178, 33 + down, progress + 1, 13);

    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIPainter.png");
    }
}
