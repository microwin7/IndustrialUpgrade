package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.block.base.BlocksItems;
import com.denfop.gui.GUINeutronGenerator;
import com.denfop.item.ItemCell;
import com.denfop.utils.ModUtils;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEINeutronGenerator extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUINeutronGenerator.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.blockMatter.name");
    }

    public String getRecipeId() {
        return "iu.genneutron";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/NeutronGeneratorGUI.png";
    }

    public String getOverlayIdentifier() {
        return "genlava";
    }

    public Map<NBTTagCompound, FluidStack> getRecipeList() {
        return Recipes.neutroniumgenrator.getRecipes();
    }

    private void drawLiquid(FluidStack stack) {

        IIcon fluidIcon = new ItemStack(stack.getFluid().getBlock()).getIconIndex();
        GuiDraw.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        int liquidHeight = (int) ((float) stack.amount / 8000.0F * 47.0F);
        DrawUtil.drawRepeated(fluidIcon, 95, 21 + 47 - liquidHeight, 12.0D, liquidHeight, GuiDraw.gui.getZLevel());
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(95, 21 + 2, 176, 57, 12, 46);
    }

    private void drawLiquidTooltip(FluidStack stack, int recipe) {

        GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        String tooltip = stack.getLocalizedName() + " (" + stack.amount + "mb)";
        GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, 95, 21, 95 + 12, 68);
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 5, 140, 75);
        GeneratorRecipe recipe = (GeneratorRecipe) this.arecipes.get(i);
        drawLiquid(recipe.output);
        GuiDraw.drawString(StatCollector.translateToLocal("cost.name") + " " + ModUtils.getString(recipe.tag.getDouble("amount")) + "EU", 10, 30, 4210752);

    }

    public void drawExtras(int i) {
        float f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(34, 64, 177, 104, 29, 9, f, 0);
        GeneratorRecipe recipe = (GeneratorRecipe) this.arecipes.get(i);
        this.drawLiquidTooltip(recipe.output, i);


    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {

    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<NBTTagCompound, FluidStack> entry : getRecipeList().entrySet())
                this.arecipes.add(new GeneratorRecipe(entry.getKey(),
                        entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadCraftingRecipes(ItemStack result) {
        FluidStack stack = null;
        if (result.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem container = (IFluidContainerItem) result.getItem();
            stack = container.getFluid(result);
        } else if (result.getItem() instanceof ItemBlock && Block.getBlockFromItem(result.getItem()) instanceof BlockFluidBase) {
            stack = new FluidStack(((BlockFluidBase) Block.getBlockFromItem(result.getItem())).getFluid(), 1000);
        } else if (result.getItem() instanceof ItemCell) {
            if (result.getItemDamage() == 1) {
                stack = new FluidStack(BlocksItems.getFluid("fluidNeutron"), 1000);
            }
        }

        if ((stack != null && stack.getFluid() != null)) {

            stack = new FluidStack(BlocksItems.getFluid("fluidNeutron"), 1000);
            for (Map.Entry<NBTTagCompound, FluidStack> inputEntry : this.getRecipeList().entrySet()) {
                if (stack.isFluidEqual(inputEntry.getValue())) {
                    this.arecipes.add(new GeneratorRecipe(inputEntry.getKey(),
                            inputEntry.getValue()));
                }
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {

    }

    public class GeneratorRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final FluidStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();
        public final NBTTagCompound tag;

        public GeneratorRecipe(NBTTagCompound tag, FluidStack output1) {
            super();
            this.output = output1;
            this.tag = tag;
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEINeutronGenerator.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return null;
        }
    }
}
