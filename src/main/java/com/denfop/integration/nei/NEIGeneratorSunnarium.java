package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEIGeneratorSunnarium extends TemplateRecipeHandler {
    int ticks;

    public String getRecipeName() {
        return StatCollector.translateToLocal("blockSolarGeneratorEnergy.name");
    }

    public String getRecipeId() {
        return "iu.blockSolarGeneratorEnergy";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/SunnariumGenerator.png";
    }

    public String getOverlayIdentifier() {
        return "sunnariumgenerator";
    }

    public Map<NBTTagCompound, ItemStack> getRecipeList() {
        return Recipes.sunnarium.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 3, 3, 160, 75);
    }

    public void drawExtras(int i) {


        float f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(120, 35, 176, 18, 24, 9, f, 0);
        drawLiquidTooltip(i);

    }

    private void drawLiquidTooltip(int recipe) {

        GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        String tooltip = "SE: " + 7500 + " EU";
        GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, 120, 35, 146, 47);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {

    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<NBTTagCompound, ItemStack> entry : getRecipeList().entrySet())
                this.arecipes.add(new GenerationSunnariumRecipe(entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<NBTTagCompound, ItemStack> entry : getRecipeList().entrySet()) {

            if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue(), result))
                this.arecipes
                        .add(new GenerationSunnariumRecipe(entry.getValue()));

        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadUsageRecipes(ItemStack ingredient) {

    }

    public class GenerationSunnariumRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();

        public GenerationSunnariumRecipe(ItemStack output1) {
            super();
            this.output = new PositionedStack(output1, 66, 32);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIGeneratorSunnarium.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
