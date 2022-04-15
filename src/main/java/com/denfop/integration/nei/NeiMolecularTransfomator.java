package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIMolecularTransformer;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

public class NeiMolecularTransfomator extends MolecularRecipeHandler {

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIMolecularTransformer.class;
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        RenderHelper.enableGUIStandardItemLighting();
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(4, 0, 4, 22, 155, 70);

    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(21, 34, 10, 19),
                getRecipeId()));
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("blockMolecularTransformer.name");
    }

    public String getRecipeId() {
        return StatCollector.translateToLocal("blockMolecularTransformer.name");
    }

    public String getGuiTexture() {
        return Constants.TEXTURES + ":textures/gui/GUIMolecularTransformerNew.png";
    }

    public String getOverlayIdentifier() {
        return StatCollector.translateToLocal("blockMolecularTransformer.name");
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return Recipes.molecular.getRecipes();
    }
}
