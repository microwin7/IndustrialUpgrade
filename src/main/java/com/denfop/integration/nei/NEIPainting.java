package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.IDoubleMachineRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIPainting;
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

public class NEIPainting extends TemplateRecipeHandler {
    final String[] name = {"defaultskin", "Coldskin", "", "camouflageskin", "demonskin", "", "Darkskin", "Enderskin"};
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIPainting.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.blockPainting.name");
    }

    public String getRecipeId() {
        return "iu.painting";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIPainter.png";
    }

    public String getOverlayIdentifier() {
        return "painting";
    }

    public Map<IDoubleMachineRecipeManager.Input, RecipeOutput> getRecipeList() {
        return Recipes.painting.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 3, 3, 140, 75);
    }

    public void drawExtras(int i) {
        NEIPainting.PaintingPanelRecipe recipe = (NEIPainting.PaintingPanelRecipe) this.arecipes.get(i);

        int down = 14 * (recipe.getIngredients().get(1).item.getItemDamage() - 1);

        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        if (down >= 0)
            drawProgressBar(72, 32, 178, 33 + down, 14, 13, f, 0);

        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(22, 45 + 9, 176, 0, 14, 14, f, 3);
        GuiDraw.drawString(StatCollector.translateToLocal(name[recipe.getIngredients().get(1).item.getItemDamage()]), 54, 53, 13487565);

    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(65, 25, 13, 13),
                getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new PaintingPanelRecipe((entry.getKey()).container,
                        (entry.getKey()).fill, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new PaintingPanelRecipe((entry.getKey()).container,
                                    (entry.getKey()).fill, entry.getValue()));
            }
        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if ((entry.getKey()).container.matches(ingredient)
                    || (entry.getKey()).fill.matches(ingredient))
                this.arecipes.add(new PaintingPanelRecipe((entry.getKey()).container,
                        (entry.getKey()).fill, entry.getValue()));
        }
    }

    public class PaintingPanelRecipe extends TemplateRecipeHandler.CachedRecipe {

        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();

        public PaintingPanelRecipe(IRecipeInput container, IRecipeInput fill, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            List<ItemStack> fillItems = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));
            for (ItemStack item : fill.getInputs())
                fillItems.add(StackUtil.copyWithSize(item, fill.getAmount()));
            this.ingredients.add(new PositionedStack(containerItems, 11, 31));
            this.ingredients.add(new PositionedStack(fillItems, 33, 31));
            ItemStack stack = output1.items.get(0);
            this.output = new PositionedStack(stack, 103, 31);

        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIPainting.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {

            return this.output;
        }
    }
}
