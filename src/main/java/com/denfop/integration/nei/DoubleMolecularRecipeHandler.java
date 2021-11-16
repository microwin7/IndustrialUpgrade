package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.api.IDoubleMolecularRecipeManager;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class DoubleMolecularRecipeHandler extends TemplateRecipeHandler {
    protected int ticks;

    public DoubleMolecularRecipeHandler() {
    }

    public abstract String getRecipeName();

    public abstract String getRecipeId();

    public abstract String getGuiTexture();

    public abstract String getOverlayIdentifier();

    public abstract Map<IDoubleMolecularRecipeManager.Input, RecipeOutput> getRecipeList();


    public void drawBackground(int i) {

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 140, 65);

    }

    public void drawExtras(int i) {
        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(23, 26, 221, 7, 25, 20, f, 1);
        DoubleMolecularRecipeHandler.CachedIORecipe recipe = (DoubleMolecularRecipeHandler.CachedIORecipe) this.arecipes.get(i);

        String energyPerTick = I18n.format("gui.MolecularTransformer.energyPerOperation") + ": ";
        String input2 = I18n.format("gui.MolecularTransformer.input") + ": ";
        String output2 = I18n.format("gui.MolecularTransformer.output") + ": ";
        GuiDraw.drawString(energyPerTick + ModUtils.getString(recipe.meta.getDouble("energy")) + " EU", 55,
                25 + 11, 13487565);

        GuiDraw.drawString(input2 + recipe.getIngredients().get(0).item.getDisplayName(), 55, 14 - 11, 13487565);
        GuiDraw.drawString(input2 + recipe.getIngredients().get(1).item.getDisplayName(), 55, 14, 13487565);
        GuiDraw.drawString(output2 + recipe.getResult().item.getDisplayName(), 55, 25, 13487565);

    }

    public void onUpdate() {
        super.onUpdate();
        ++this.ticks;
    }

    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 25, 16), this.getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IDoubleMolecularRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new DoubleMolecularRecipeHandler.CachedIORecipe((entry.getKey()).container,
                        (entry.getKey()).fill, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IDoubleMolecularRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new DoubleMolecularRecipeHandler.CachedIORecipe((entry.getKey()).container,
                                    (entry.getKey()).fill, entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IDoubleMolecularRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if ((entry.getKey()).container.matches(ingredient)
                    || (entry.getKey()).fill.matches(ingredient))
                this.arecipes.add(new DoubleMolecularRecipeHandler.CachedIORecipe((entry.getKey()).container,
                        (entry.getKey()).fill, entry.getValue()));
        }
    }


    public class CachedIORecipe extends CachedRecipe {
        protected final List<PositionedStack> ingredients = new ArrayList();
        protected final PositionedStack output;
        protected final List<PositionedStack> otherStacks = new ArrayList();
        protected final NBTTagCompound meta;

        public CachedIORecipe(IRecipeInput container, IRecipeInput fill, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            List<ItemStack> fillItems = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));
            for (ItemStack item : fill.getInputs())
                fillItems.add(StackUtil.copyWithSize(item, fill.getAmount()));
            this.ingredients.add(new PositionedStack(containerItems, 11, 5));
            this.ingredients.add(new PositionedStack(fillItems, 29, 5));
            this.output = new PositionedStack(output1.items.get(0), 20, 46);
            this.meta = output1.metadata;
        }

        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(DoubleMolecularRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.otherStacks;
        }
    }

}
