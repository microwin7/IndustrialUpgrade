package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.IUItem;
import com.denfop.api.IFluidRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.block.base.BlocksItems;
import com.denfop.gui.GuiOilRefiner;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEIRefiner extends TemplateRecipeHandler {
    int ticks;

    public class RefinerRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final FluidStack[] output;

        public final List<PositionedStack> ingredients = new ArrayList<>();
        public final FluidStack fluidstack;

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIRefiner.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return null;
        }

        public RefinerRecipe(FluidStack fluidstack, FluidStack[] output1) {
            super();
            this.output = output1;
            this.fluidstack = fluidstack;
        }
    }

    public Class<? extends GuiContainer> getGuiClass() {
        return GuiOilRefiner.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("blockOilRefiner.name");
    }

    public String getRecipeId() {
        return "iu.blockOilRefiner";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIOilRefiner.png";
    }

    public String getOverlayIdentifier() {
        return "refiner";
    }

    public Map<IFluidRecipeManager.Input, FluidStack[]> getRecipeList() {
        return Recipes.oilrefiner.getRecipes();
    }
    private void drawLiquid(FluidStack stack, int x) {

        IIcon fluidIcon = new ItemStack(stack.getFluid().getBlock()).getIconIndex();
        GuiDraw.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        int liquidHeight = (int)((float)stack.amount / 8000.0F * 47.0F);
        DrawUtil.drawRepeated(fluidIcon, x, 5 + 47 - liquidHeight, 12.0D, liquidHeight, GuiDraw.gui.getZLevel());
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(x, 5 +2, 176, 57, 12, 46);
    }
    private void drawLiquidTooltip(FluidStack stack, int recipe, int x) {

        GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        String tooltip = stack.getLocalizedName() + " (" + stack.amount + "mb)";
        GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, x, 5, x + 12, 52);
    }
    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 5, 140, 75);
        RefinerRecipe recipe = (RefinerRecipe)this.arecipes.get(i);
        drawLiquid(recipe.fluidstack,11);
        drawLiquid(recipe.output[0],73);
        drawLiquid(recipe.output[1],105);
    }

    public void drawExtras(int i) {
        float f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(34, 64, 177, 104, 29, 9, f, 0);
        RefinerRecipe recipe = (RefinerRecipe)this.arecipes.get(i);
        this.drawLiquidTooltip(recipe.fluidstack, i,11);
        this.drawLiquidTooltip(recipe.output[0], i,73);
        this.drawLiquidTooltip(recipe.output[1], i,105);


    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {

    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry : getRecipeList().entrySet())
                this.arecipes.add(new RefinerRecipe(entry.getKey().fluidStack,
                        entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    public int recipiesPerPage() {
        return 1;
    }
    public void loadCraftingRecipes(ItemStack result) {

        ItemStack[] stack = new ItemStack[]{new ItemStack(IUItem.bucket,1,3),new ItemStack(IUItem.bucket,1,4),new ItemStack(IUItem.cell_all,1,3),new ItemStack(BlocksItems.getFluid("fluiddizel").getBlock()),new ItemStack(BlocksItems.getFluid("fluidbenz").getBlock()),new ItemStack(IUItem.cell_all,1,4)};
        for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry : getRecipeList().entrySet()) {
            for (ItemStack output : stack) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes.add(new RefinerRecipe(entry.getKey().fluidStack,
                            entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry : getRecipeList().entrySet()) {
            this.arecipes.add(new RefinerRecipe(entry.getKey().fluidStack,
                    entry.getValue()));
        }
    }
}
