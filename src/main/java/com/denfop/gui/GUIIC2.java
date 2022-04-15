package com.denfop.gui;

import com.denfop.api.inv.IInvSlotProcessableMulti;
import com.denfop.tiles.base.TileEntityCombinerMatter;
import com.denfop.tiles.base.TileEntityMultiMachine;
import com.denfop.tiles.mechanism.TileEntityAlloySmelter;
import com.denfop.tiles.mechanism.TileEntityGenerationStone;
import com.denfop.utils.ModUtils;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.slot.SlotInvSlot;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.List;

public abstract class GUIIC2 extends GuiContainer {
    public final ContainerBase<? extends IInventory> container;

    protected int xoffset;

    protected int yoffset;

    public GUIIC2(ContainerBase<? extends IInventory> container) {
        super(container);
        this.container = container;
    }


    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        TileEntity tile = (TileEntity) this.container.base;
        if (!(tile instanceof TileEntityCombinerMatter))
            if (!(tile instanceof TileEntityGenerationStone))
                this.fontRendererObj.drawString(getName(),
                        (this.xSize - this.fontRendererObj.getStringWidth(getName())) / 2, 6, 4210752);
            else
                this.fontRendererObj.drawString(getName(),
                        (this.xSize - this.fontRendererObj.getStringWidth(getName())) / 2 - 37, 1, 4210752);
        if (this.container.base instanceof TileEntityMultiMachine) {
            TileEntityMultiMachine tile1 = (TileEntityMultiMachine) this.container.base;
            String tooltip1 = ModUtils.getString(tile1.energy2) + "/" + ModUtils.getString(tile1.maxEnergy2) + " RF";
            String tooltip2 = ModUtils.getString(Math.min(tile1.energy, ((TileEntityMultiMachine) tile).maxEnergy)) + "/" + ModUtils.getString(tile1.maxEnergy) + " EU";

            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip2, 5, 47, 19, 61);
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip1, 14, 47, 28, 61);

            if (tile1.type != 1 && tile1.type != 3) {
                String tooltip = tile1.expstorage + "/" + tile1.expmaxstorage;

                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 9, 30, 32, 38);
                int i = 0;
                for (Slot slot : (List<Slot>) this.container.inventorySlots) {
                    if (slot instanceof SlotInvSlot) {
                        int xX = slot.xDisplayPosition;
                        int yY = slot.yDisplayPosition;
                        SlotInvSlot slotInv = (SlotInvSlot) slot;
                        if (slotInv.invSlot instanceof IInvSlotProcessableMulti) {

                            double progress = (24.0F * tile1.getProgress(i));
                            if (progress > 0)
                                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop,
                                        ModUtils.getString(tile1.getProgress(i) * 100) + "%", xX, yY + 19, xX + 16,
                                        yY + 19 + 25);
                            i++;
                        }

                    }
                }
            } else {
                int i = 0;
                for (Slot slot : (List<Slot>) this.container.inventorySlots) {
                    if (slot instanceof SlotInvSlot) {
                        int xX = slot.xDisplayPosition;
                        int yY = slot.yDisplayPosition;
                        SlotInvSlot slotInv = (SlotInvSlot) slot;
                        if (slotInv.invSlot instanceof IInvSlotProcessableMulti) {

                            double progress = (24.0F * tile1.getProgress(i));
                            if (progress > 0)
                                GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop,
                                        ModUtils.getString(tile1.getProgress(i) * 100) + "%", xX, yY + 19, xX + 16,
                                        yY + 19 + 25);
                            i++;
                        }

                    }
                }
            }
        }

        if (this.container.base instanceof GUIAlloySmelter) {
            TileEntityAlloySmelter tile1 = (TileEntityAlloySmelter) this.container.base;
            String tooltip = ModUtils.getString(tile1.getProgress() * 100) + "%";
            GuiTooltipHelper.drawAreaTooltip(par1 - this.guiLeft, par2 - this.guiTop, tooltip, 79, 34, 79 + 25,
                    34 + 14);

        }

        if (this.container.base instanceof IUpgradableBlock)
            GuiTooltipHelper.drawUpgradeslotTooltip(par1 - this.guiLeft, par2 - this.guiTop, 0, 0, 12, 12,
                    (IUpgradableBlock) this.container.base, 25, 0);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(getResourceLocation());
        this.xoffset = (this.width - this.xSize) / 2;
        this.yoffset = (this.height - this.ySize) / 2;
        drawTexturedModalRect(this.xoffset, this.yoffset, 0, 0, this.xSize, this.ySize);
        if (this.container.base instanceof IUpgradableBlock) {
            this.mc.getTextureManager()
                    .bindTexture(new ResourceLocation(IC2.textureDomain, "textures/gui/infobutton.png"));
            drawTexturedModalRect(this.xoffset + 3, this.yoffset + 3, 0, 0, 10, 10);
            this.mc.getTextureManager().bindTexture(getResourceLocation());
        }
    }

    public abstract String getName();

    public abstract ResourceLocation getResourceLocation();
}
