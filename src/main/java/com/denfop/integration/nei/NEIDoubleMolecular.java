package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.IDoubleMolecularRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIDoubleMolecularTransformer;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

public class NEIDoubleMolecular extends DoubleMolecularRecipeHandler {

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIDoubleMolecularTransformer.class;
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
        return StatCollector.translateToLocal("blockDoubleMolecularTransformer.name");
    }

    public String getRecipeId() {
        return StatCollector.translateToLocal("blockDoubleMolecularTransformer.name");
    }

    public String getGuiTexture() {
        return Constants.TEXTURES + ":textures/gui/GUIDoubleMolecularTransformerNew.png";
    }

    public String getOverlayIdentifier() {
        return StatCollector.translateToLocal("blockDoubleMolecularTransformer.name");
    }

    public Map<IDoubleMolecularRecipeManager.Input, RecipeOutput> getRecipeList() {
        return Recipes.doublemolecular.getRecipes();
    }
}
