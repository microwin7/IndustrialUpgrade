package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseMolecular;
import com.denfop.tiles.base.TileEntityMolecularTransformer;
import com.denfop.utils.Helpers;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

@SideOnly(Side.CLIENT)
public class GUIMolecularTransformer extends GUIBaseMolecularTranformer {
    public final ContainerBaseMolecular<? extends TileEntityMolecularTransformer> container;

    public GUIMolecularTransformer(ContainerBaseMolecular<? extends TileEntityMolecularTransformer> container1) {
        super(container1);
        this.container = container1;
    }


    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        String input = I18n.format("gui.MolecularTransformer.input") + ": ";
        String output = I18n.format("gui.MolecularTransformer.output") + ": ";
        String energyPerOperation = I18n.format("gui.MolecularTransformer.energyPerOperation") + ": ";
        String progress = I18n.format("gui.MolecularTransformer.progress") + ": ";

        this.fontRendererObj.drawString(StatCollector.translateToLocal("button.changemode"), this.xoffset + 17, this.yoffset + 6, Helpers.convertRGBcolorToInt(23, 119, 167));
        this.fontRendererObj.drawString(StatCollector.translateToLocal("button.rg"), this.xoffset + 186, this.yoffset + 6, Helpers.convertRGBcolorToInt(23, 119, 167));

        double chargeLevel = (15.0D * this.container.base.getProgress());


        RecipeOutput output1 = Recipes.molecular.getOutputFor(this.container.base.inputSlot.get(0), false);
        if (chargeLevel > 0 && !this.container.base.inputSlot.isEmpty() && Recipes.molecular.getOutputFor(this.container.base.inputSlot.get(0), false) != null) {
            if (!this.container.base.queue) {
                this.mc.getTextureManager().bindTexture(getResourceLocation());
                drawTexturedModalRect(this.xoffset + 23, this.yoffset + 48, 221, 7, 10, (int) chargeLevel);
                this.mc.getTextureManager().bindTexture(getResourceLocation());
                this.fontRendererObj.drawString(input + this.container.base.inputSlot.get().getDisplayName(),
                        this.xoffset + 60, this.yoffset + 25, 4210752);

                this.fontRendererObj.drawString(output + output1.items.get(0).getDisplayName(), this.xoffset + 60,
                        this.yoffset + 25 + 11, 4210752);
                this.fontRendererObj.drawString(energyPerOperation + ModUtils.getString(output1.metadata.getDouble("energy")) + " EU",
                        this.xoffset + 60, this.yoffset + 25 + 22, 4210752);
                if (this.container.base.getProgress() * 100 <= 100)
                    this.fontRendererObj.drawString(
                            progress + MathHelper.floor_double(this.container.base.getProgress() * 100) + "%",
                            this.xoffset + 60, this.yoffset + 25 + 33, 4210752);
                if (this.container.base.getProgress() * 100 > 100)
                    this.fontRendererObj.drawString(
                            progress + MathHelper.floor_double(100) + "%",
                            this.xoffset + 60, this.yoffset + 25 + 33, 4210752);
                this.fontRendererObj.drawString(
                        "EU/t: " + ModUtils.getString(this.container.base.differenceenergy),
                        this.xoffset + 60, this.yoffset + 25 + 44, 4210752);
                double hours = this.container.base.time.get(0);
                double minutes = this.container.base.time.get(1);
                double seconds = this.container.base.time.get(2);
                String time1 = hours > 0 ? ModUtils.getString(hours) + StatCollector.translateToLocal("iu.hour") + "" : "";
                String time2 = minutes > 0 ? ModUtils.getString(minutes) + StatCollector.translateToLocal("iu.minutes") + "" : "";
                String time3 = seconds > 0 ? ModUtils.getString(seconds) + StatCollector.translateToLocal("iu.seconds") + "" : "";

                this.fontRendererObj.drawString(
                        StatCollector.translateToLocal("iu.timetoend") + time1 + time2 + time3,
                        this.xoffset + 60, this.yoffset + 25 + 55, 4210752);

            } else {
                ItemStack output2;
                int size;
                for (int i = 0; ; i++) {
                    ItemStack stack = new ItemStack(this.container.base.inputSlot.get().getItem(), i, this.container.base.inputSlot.get().getItemDamage());
                    if (Recipes.molecular.getOutputFor(stack, false) != null) {
                        size = i;
                        output2 = Recipes.molecular.getOutputFor(stack, false).items.get(0);
                        break;
                    }
                }
                int col = size;
                size = (int) Math.floor((float) this.container.base.inputSlot.get().stackSize / size);
                int size1 = this.container.base.outputSlot.get() != null ? (64 - this.container.base.outputSlot.get().stackSize) / output2.stackSize : 64 / output2.stackSize;

                size = Math.min(size1, size);
                size = Math.min(size, output2.getMaxStackSize());
                if (this.container.base.outputSlot.get() == null || this.container.base.outputSlot.get().stackSize < 64) {
                    this.mc.getTextureManager().bindTexture(getResourceLocation());
                    drawTexturedModalRect(this.xoffset + 23, this.yoffset + 48, 221, 7, 10, (int) chargeLevel);
                    this.mc.getTextureManager().bindTexture(getResourceLocation());
                    this.fontRendererObj.drawString(input + col * size + "x" + this.container.base.inputSlot.get().getDisplayName(),
                            this.xoffset + 60, this.yoffset + 25, 4210752);

                    this.fontRendererObj.drawString(output + output2.stackSize * size + "x" + output1.items.get(0).getDisplayName(), this.xoffset + 60,
                            this.yoffset + 25 + 11, 4210752);
                    this.fontRendererObj.drawString(energyPerOperation + ModUtils.getString(output1.metadata.getDouble("energy") * size) + " EU",
                            this.xoffset + 60, this.yoffset + 25 + 22, 4210752);
                    if (this.container.base.getProgress() * 100 <= 100)
                        this.fontRendererObj.drawString(
                                progress + MathHelper.floor_double(this.container.base.getProgress() * 100) + "%",
                                this.xoffset + 60, this.yoffset + 25 + 33, 4210752);
                    if (this.container.base.getProgress() * 100 > 100)
                        this.fontRendererObj.drawString(
                                progress + MathHelper.floor_double(100) + "%",
                                this.xoffset + 60, this.yoffset + 25 + 33, 4210752);

                    this.fontRendererObj.drawString(
                            "EU/t: " + ModUtils.getString(this.container.base.differenceenergy),
                            this.xoffset + 60, this.yoffset + 25 + 44, 4210752);
                    double hours = this.container.base.time.get(0);
                    double minutes = this.container.base.time.get(1);
                    double seconds = this.container.base.time.get(2);
                    String time1 = hours > 0 ? ModUtils.getString(hours) + StatCollector.translateToLocal("iu.hour") : "";
                    String time2 = minutes > 0 ? ModUtils.getString(minutes) + StatCollector.translateToLocal("iu.minutes") : "";
                    String time3 = seconds > 0 ? ModUtils.getString(seconds) + StatCollector.translateToLocal("iu.seconds") : "";

                    this.fontRendererObj.drawString(
                            StatCollector.translateToLocal("iu.timetoend") + time1 + time2 + time3,
                            this.xoffset + 60, this.yoffset + 25 + 55, 4210752);
                }
            }

        }
    }

    public String getName() {
        return StatCollector.translateToLocal("blockMolecularTransformer.name");
    }

    public ResourceLocation getResourceLocation() {
        switch (this.container.base.redstoneMode) {
            case 1:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_chemical_green.png");
            case 2:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_gold.png");
            case 3:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_red.png");
            case 4:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_silver.png");
            case 5:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_violet.png");
            case 6:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_blue.png");
            case 7:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew_green.png");
            default:
                return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIMolecularTransformerNew.png");
        }
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
}
