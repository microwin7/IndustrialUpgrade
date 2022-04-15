package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.IWitherMaker;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIWitherMaker;
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

public class NEIWitherMaker extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIWitherMaker.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.blockWitherMaker.name");
    }

    public String getRecipeId() {
        return "iu.withermaker";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIWitherMaker.png";
    }

    public String getOverlayIdentifier() {
        return "withermaker";
    }

    public Map<IWitherMaker.Input, RecipeOutput> getRecipeList() {
        return Recipes.withermaker.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 3, 3, 147, 75);
    }

    public void drawExtras(int i) {
        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(78, 13, 177, 19, 40, 18, f, 0);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(76, 48, 176, 0, 14, 14, f, 3);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(72, 11, 40, 17),
                getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IWitherMaker.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new WitherMakerRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getKey().fill3, entry.getKey().fill1, entry.getKey().fill2, entry.getKey().container1, entry.getKey().fill4, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IWitherMaker.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new WitherMakerRecipe(entry.getKey().container,
                                    entry.getKey().fill, entry.getKey().fill3, entry.getKey().fill1, entry.getKey().fill2, entry.getKey().container1, entry.getKey().fill4, entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IWitherMaker.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if (entry.getKey().container.matches(ingredient)
                    || entry.getKey().fill.matches(ingredient) || entry.getKey().fill1.matches(ingredient))
                this.arecipes.add(new WitherMakerRecipe(entry.getKey().container,
                        entry.getKey().fill, entry.getKey().fill3, entry.getKey().fill1, entry.getKey().fill2, entry.getKey().container1, entry.getKey().fill4, entry.getValue()));
        }
    }

    public class WitherMakerRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();

        public WitherMakerRecipe(IRecipeInput container, IRecipeInput fill, IRecipeInput fill1, IRecipeInput fill2, IRecipeInput fill3, IRecipeInput fill4, IRecipeInput fill5, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            List<ItemStack> fillItems = new ArrayList<>();
            List<ItemStack> fillItems1 = new ArrayList<>();
            List<ItemStack> fillItems2 = new ArrayList<>();
            List<ItemStack> fillItems3 = new ArrayList<>();
            List<ItemStack> fillItems4 = new ArrayList<>();
            List<ItemStack> fillItems5 = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));
            for (ItemStack item : fill.getInputs())
                fillItems.add(StackUtil.copyWithSize(item, fill.getAmount()));
            for (ItemStack item : fill1.getInputs())
                fillItems1.add(StackUtil.copyWithSize(item, fill1.getAmount()));
            for (ItemStack item : fill2.getInputs())
                fillItems2.add(StackUtil.copyWithSize(item, fill2.getAmount()));
            for (ItemStack item : fill3.getInputs())
                fillItems3.add(StackUtil.copyWithSize(item, fill3.getAmount()));
            for (ItemStack item : fill4.getInputs())
                fillItems4.add(StackUtil.copyWithSize(item, fill4.getAmount()));
            for (ItemStack item : fill5.getInputs())
                fillItems5.add(StackUtil.copyWithSize(item, fill5.getAmount()));

            this.ingredients.add(new PositionedStack(containerItems, 8, 5));
            this.ingredients.add(new PositionedStack(fillItems, 26, 5));
            this.ingredients.add(new PositionedStack(fillItems1, 44, 5));
            this.ingredients.add(new PositionedStack(fillItems2, 8, 23));
            this.ingredients.add(new PositionedStack(fillItems3, 26, 23));
            this.ingredients.add(new PositionedStack(fillItems4, 44, 23));
            this.ingredients.add(new PositionedStack(fillItems5, 26, 41));
            this.output = new PositionedStack(output1.items.get(0), 128, 10);
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIWitherMaker.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
