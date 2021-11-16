package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.ISunnariumRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.gui.GUISunnariumMaker;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEISunnarium extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUISunnariumMaker.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("blockSunnariumMaker.name");
    }

    public String getRecipeId() {
        return "iu.sunmaket";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUISunnariumMaker.png";
    }

    public String getOverlayIdentifier() {
        return "sunnariummaker";
    }

    public Map<ISunnariumRecipeManager.Input, RecipeOutput> getRecipeList() {
        return Recipes.sunnurium.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 3, 3, 140, 77);
    }

    public void drawExtras(int i) {
        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(52, 17, 176, 31, 18, 31, f, 0);

        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(9, 59, 176, 0, 14, 14, f, 3);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(52, 14, 17, 31),
                getRecipeId()));


    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<ISunnariumRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new SunnariumRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getKey().fill2, entry.getKey().fill3, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<ISunnariumRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new SunnariumRecipe(entry.getKey().container,
                                    entry.getKey().fill, entry.getKey().fill2, entry.getKey().fill3, entry.getValue()));
            }
        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<ISunnariumRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if (entry.getKey().container.matches(ingredient)
                    || entry.getKey().fill.matches(ingredient) || entry.getKey().fill2.matches(ingredient) || entry.getKey().fill3.matches(ingredient))
                this.arecipes.add(new SunnariumRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getKey().fill2, entry.getKey().fill3, entry.getValue()));
        }
    }

    public class SunnariumRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();


        public SunnariumRecipe(IRecipeInput container, IRecipeInput fill, IRecipeInput fill1, IRecipeInput fill2, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            List<ItemStack> fillItems = new ArrayList<>();
            List<ItemStack> fillItems1 = new ArrayList<>();
            List<ItemStack> fillItems2 = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));
            for (ItemStack item : fill.getInputs())
                fillItems.add(StackUtil.copyWithSize(item, fill.getAmount()));
            for (ItemStack item : fill1.getInputs())
                fillItems1.add(StackUtil.copyWithSize(item, fill1.getAmount()));
            for (ItemStack item : fill2.getInputs())
                fillItems2.add(StackUtil.copyWithSize(item, fill2.getAmount()));

            this.ingredients.add(new PositionedStack(containerItems, 35, 14));
            this.ingredients.add(new PositionedStack(fillItems, 71, 14));
            this.ingredients.add(new PositionedStack(fillItems1, 35, 36));
            this.ingredients.add(new PositionedStack(fillItems2, 71, 36));
            this.output = new PositionedStack(output1.items.get(0), 112, 31);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEISunnarium.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
