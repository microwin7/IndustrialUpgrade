package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseDoubleMolecular;
import com.denfop.tiles.base.TileEntityDoubleMolecular;
import com.denfop.utils.Helpers;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUIDoubleMolecularTransformer extends GUIBaseMolecularTranformer {
    public final ContainerBaseDoubleMolecular<? extends TileEntityDoubleMolecular> container;

    public GUIDoubleMolecularTransformer(ContainerBaseDoubleMolecular<? extends TileEntityDoubleMolecular> container1) {
        super(container1);
        this.container = container1;
    }


    protected void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        int xMin = (this.width - this.xSize) / 2;
        int yMin = (this.height - this.ySize) / 2;
        int x = i - xMin;
        int y = j - yMin;
        if (x >= 180 && x <= 197 && y >= 3 && y <= 17) {
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 0);
        }

        if (x >= 7 && x <= 60 && y >= 3 && y <= 17) {
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 1);
        }

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        String input = I18n.format("gui.MolecularTransformer.input") + ": ";
        String output = I18n.format("gui.MolecularTransformer.output") + ": ";
        String energyPerOperation = I18n.format("gui.MolecularTransformer.energyPerOperation") + ": ";
        String progress = I18n.format("gui.MolecularTransformer.progress") + ": ";
        double chargeLevel = (15.0D * this.container.base.getProgress());
        this.fontRendererObj.drawString(StatCollector.translateToLocal("button.changemode"), this.xoffset + 17, this.yoffset + 6, Helpers.convertRGBcolorToInt(23, 119, 167));
        this.fontRendererObj.drawString(StatCollector.translateToLocal("button.rg"), this.xoffset + 186, this.yoffset + 6, Helpers.convertRGBcolorToInt(23, 119, 167));

        RecipeOutput output1 = Recipes.doublemolecular.getOutputFor(this.container.base.inputSlot.get(0), this.container.base.inputSlot.get(1), false, false);
        if (chargeLevel > 0 && !this.container.base.inputSlot.isEmpty() && Recipes.doublemolecular.getOutputFor(this.container.base.inputSlot.get(0), this.container.base.inputSlot.get(1), false, false) != null) {
            if (!this.container.base.queue) {
                this.mc.getTextureManager().bindTexture(getResourceLocation());
                drawTexturedModalRect(this.xoffset + 23, this.yoffset + 48, 221, 7, 10, (int) chargeLevel);
                this.mc.getTextureManager().bindTexture(getResourceLocation());

                this.fontRendererObj.drawString(input + this.container.base.inputSlot.get().getDisplayName(),
                        this.xoffset + 60, this.yoffset + 25, 4210752);
                this.fontRendererObj.drawString(input + this.container.base.inputSlot.get(1).getDisplayName(),
                        this.xoffset + 60, this.yoffset + 36, 4210752);

                this.fontRendererObj.drawString(output + output1.items.get(0).getDisplayName(), this.xoffset + 60,
                        this.yoffset + 47, 4210752);
                this.fontRendererObj.drawString(energyPerOperation + ModUtils.getString(output1.metadata.getDouble("energy")) + " EU",
                        this.xoffset + 60, this.yoffset + 58, 4210752);
                if (this.container.base.getProgress() * 100 <= 100)
                    this.fontRendererObj.drawString(
                            progress + MathHelper.floor_double(this.container.base.getProgress() * 100) + "%",
                            this.xoffset + 60, this.yoffset + 69, 4210752);
                if (this.container.base.getProgress() * 100 > 100)
                    this.fontRendererObj.drawString(
                            progress + MathHelper.floor_double(100) + "%",
                            this.xoffset + 60, this.yoffset + 69, 4210752);
                this.fontRendererObj.drawString(
                        "EU/t: " + ModUtils.getString(this.container.base.differenceenergy),
                        this.xoffset + 60, this.yoffset + 80, 4210752);

            } else {
                ItemStack output2 = null;
                int size = 0;
                int size2 = 0;
                int col = 0;
                int col1 = 0;
                boolean getrecipe = false;
                for (int i = 0; !getrecipe; i++)
                    for (int j = 0; j < 4; j++) {
                        ItemStack stack = new ItemStack(this.container.base.inputSlot.get(0).getItem(), i, this.container.base.inputSlot.get().getItemDamage());
                        ItemStack stack1 = new ItemStack(this.container.base.inputSlot.get(1).getItem(), j, this.container.base.inputSlot.get(1).getItemDamage());

                        if (Recipes.doublemolecular.getOutputFor(stack, stack1, false, false) != null) {
                            size = i;
                            size2 = j;
                            col = i;
                            col1 = j;
                            getrecipe = true;
                            output2 = Recipes.doublemolecular.getOutputFor(stack, stack1, false, false).items.get(0);
                            break;

                        }
                    }

                size = (int) Math.floor((float) this.container.base.inputSlot.get().stackSize / size);
                size2 = (int) Math.floor((float) this.container.base.inputSlot.get(1).stackSize / size2);
                size = Math.min(size, size2);

                int size1 = this.container.base.outputSlot.get() != null ? 64 - this.container.base.outputSlot.get().stackSize : 64;
                size = Math.min(size1, size);
                size = Math.min(size, output2.getMaxStackSize());
                if (this.container.base.outputSlot.get() == null || this.container.base.outputSlot.get().stackSize < 64) {
                    this.mc.getTextureManager().bindTexture(getResourceLocation());
                    drawTexturedModalRect(this.xoffset + 23, this.yoffset + 48, 221, 7, 10, (int) chargeLevel);
                    this.mc.getTextureManager().bindTexture(getResourceLocation());
                    this.fontRendererObj.drawString(input + col * size + "x" + this.container.base.inputSlot.get().getDisplayName(),
                            this.xoffset + 60, this.yoffset + 25, 4210752);

                    this.fontRendererObj.drawString(input + col1 * size + "x" + this.container.base.inputSlot.get(1).getDisplayName(),
                            this.xoffset + 60, this.yoffset + 36, 4210752);

                    this.fontRendererObj.drawString(output + output2.stackSize * size + "x" + output1.items.get(0).getDisplayName(), this.xoffset + 60,
                            this.yoffset + 47, 4210752);
                    this.fontRendererObj.drawString(energyPerOperation + ModUtils.getString(output1.metadata.getDouble("energy") * size) + " EU",
                            this.xoffset + 60, this.yoffset + 58, 4210752);
                    if (this.container.base.getProgress() * 100 <= 100)
                        this.fontRendererObj.drawString(
                                progress + MathHelper.floor_double(this.container.base.getProgress() * 100) + "%",
                                this.xoffset + 60, this.yoffset + 69, 4210752);
                    if (this.container.base.getProgress() * 100 > 100)
                        this.fontRendererObj.drawString(
                                progress + MathHelper.floor_double(100) + "%",
                                this.xoffset + 60, this.yoffset + 69, 4210752);

                    this.fontRendererObj.drawString(
                            "EU/t: " + ModUtils.getString(this.container.base.differenceenergy),
                            this.xoffset + 60, this.yoffset + 80, 4210752);

                }
            }

        }
    }

    protected void actionPerformed(GuiButton guibutton) {
        super.actionPerformed(guibutton);
        if (guibutton.id == 0) {
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 0);

        }
        if (guibutton.id == 1) {
            IC2.network.get().initiateClientTileEntityEvent(this.container.base, 1);

        }
    }

    public String getName() {
        return StatCollector.translateToLocal("blockDoubleMolecularTransformer.name");
    }

    public ResourceLocation getResourceLocation() {

        switch (this.container.base.redstoneMode) {
            case 1:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_chemical_green.png");
            case 2:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_gold.png");
            case 3:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_red.png");
            case 4:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_silver.png");
            case 5:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_violet.png");
            case 6:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_blue.png");
            case 7:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew_green.png");
            default:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIDoubleMolecularTransformerNew.png");
        }
    }
}
