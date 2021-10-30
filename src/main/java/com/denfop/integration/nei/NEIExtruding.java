package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import com.denfop.Constants;
import com.denfop.gui.GUIMultiMachine2;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.neiIntegration.core.recipehandler.MachineRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.Map;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;


public class NEIExtruding extends MachineRecipeHandler {
    public NEIExtruding() {
    }

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIMultiMachine2.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.blockExtruding.name");
    }

    protected int getInputPosX() {
        return 67;
    }

    protected int getInputPosY() {
        return 13;
    }

    protected int getOutputPosX() {
        return 67;
    }

    protected int getOutputPosY() {
        return 57;
    }

    public String getRecipeId() {
        return "iu.blockExtruding.name";
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        drawTexturedModalRect(0, 0, 3, 3, 140, 80);
        drawTexturedModalRect(getInputPosX() - 1, getInputPosY() - 1, 238, 0, 18, 18);
        drawTexturedModalRect(getInputPosX(), getInputPosY() + 19, 176, 14 + 96, 16, 24);
        drawTexturedModalRect(getOutputPosX() - 1, getOutputPosY() - 1, 238, 0, 18, 18);

    }

    public void drawExtras(int i) {

        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(getInputPosX(), getInputPosY() + 19, 192, 14 + 96, 16, 24, f, 1);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(2, 44, 176, 0, 14, 14, f, 3);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(2 + 9, 44, 176 + 14, 0, 14, 14, f, 3);

    }

    public void onUpdate() {
        super.onUpdate();
        ++this.ticks;
    }

    public void loadTransferRects() {
    }

    public String getGuiTexture() {
        return Constants.TEXTURES + ":textures/gui/GUIMachine.png";

    }

    public int recipiesPerPage() {
        return 1;
    }

    public String getOverlayIdentifier() {
        return "Extruding";
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return Recipes.metalformerExtruding.getRecipes();
    }
}
