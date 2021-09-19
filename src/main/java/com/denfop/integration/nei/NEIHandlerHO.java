
package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.gui.GuiHandlerHeavyOre;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.neiIntegration.core.recipehandler.MachineRecipeHandler;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

public class NEIHandlerHO extends MachineRecipeHandler {
    public NEIHandlerHO() {
    }

    public Class<? extends GuiContainer> getGuiClass() {
        return GuiHandlerHeavyOre.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.handler.name");
    }

    protected int getInputPosX() {
        return 22;
    }

    protected int getInputPosY() {
        return 26;
    }

    protected int getOutputPosX() {
        return 99;
    }

    protected int getOutputPosY() {
        return 8;
    }

    public String getRecipeId() {
        return "iu.handler.name";
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 3, 3, 140, 75);
    }

    public void drawExtras(int i) {


        float f = this.ticks >= 20 ? (float) ((this.ticks - 20) % 20) / 20.0F : 0.0F;
        this.drawProgressBar(45, 28, 177, 32, 44, 14, f, 0);
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.ticks;
    }

    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(48, 27, 25, 16), this.getRecipeId()));
    }

    public String getGuiTexture() {
        return Constants.TEXTURES + ":textures/gui/GUIHandlerHO.png";

    }

    public int recipiesPerPage() {
        return 1;
    }

    public String getOverlayIdentifier() {
        return "handler";
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return Recipes.handlerore.getRecipes();
    }
}
