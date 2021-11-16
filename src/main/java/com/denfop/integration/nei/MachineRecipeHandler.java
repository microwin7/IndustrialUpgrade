package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import ic2.neiIntegration.core.PositionedStackIc2;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class MachineRecipeHandler extends TemplateRecipeHandler {
    protected int ticks;

    public MachineRecipeHandler() {
    }

    public abstract String getRecipeName();

    public abstract String getRecipeId();

    public abstract String getGuiTexture();

    public abstract String getOverlayIdentifier();

    public abstract Map<IRecipeInput, RecipeOutput> getRecipeList();

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 140, 65);
    }

    public void drawExtras(int i) {
        float f = this.ticks >= 20 ? (float) ((this.ticks - 20) % 20) / 20.0F : 0.0F;
        this.drawProgressBar(74, 23, 176, 14, 25, 16, f, 0);
        f = this.ticks <= 20 ? (float) this.ticks / 20.0F : 1.0F;
        this.drawProgressBar(51, 25, 176, 0, 14, 14, f, 3);
    }

    public void onUpdate() {
        super.onUpdate();
        ++this.ticks;
    }

    public void loadTransferRects() {
        this.transferRects.add(new RecipeTransferRect(new Rectangle(74, 23, 25, 16), this.getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeId())) {

            for (Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : this.getRecipeList().entrySet()) {
                this.arecipes.add(new CachedIORecipe(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue()));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public void loadCraftingRecipes(ItemStack result) {


        for (Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : this.getRecipeList().entrySet()) {

            for (ItemStack output : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result)) {
                    this.arecipes.add(new CachedIORecipe(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue()));
                    break;
                }
            }


            return;
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {

        for (Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : this.getRecipeList().entrySet()) {
            if (iRecipeInputRecipeOutputEntry.getKey().matches(ingredient)) {
                this.arecipes.add(new CachedIORecipe(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue()));
            }
        }

    }

    protected int getInputPosX() {
        return 51;
    }

    protected int getInputPosY() {
        return 6;
    }

    protected int getOutputPosX() {
        return 111;
    }

    protected int getOutputPosY() {
        return 24;
    }


    public class CachedIORecipe extends CachedRecipe {
        protected final List<PositionedStack> ingredients = new ArrayList();
        protected final PositionedStack output;
        protected final List<PositionedStack> otherStacks = new ArrayList();
        protected final NBTTagCompound meta;

        public CachedIORecipe(IRecipeInput input, RecipeOutput output1) {
            super();
            if (input == null) {
                throw new NullPointerException("Input must not be null (recipe " + input + " -> " + output1 + ").");
            } else if (output1 == null) {
                throw new NullPointerException("Output must not be null (recipe " + input + " -> " + output1 + ").");
            } else if (output1.items.isEmpty()) {
                throw new IllegalArgumentException("Output must not be empty (recipe " + input + " -> " + output1 + ").");
            } else if (output1.items.contains(null)) {
                throw new IllegalArgumentException("Output must not contain null (recipe " + input + " -> " + output1 + ").");
            } else {
                List<ItemStack> items = new ArrayList();

                for (ItemStack item : input.getInputs()) {
                    items.add(StackUtil.copyWithSize(item, input.getAmount()));
                }

                this.ingredients.add(new PositionedStackIc2(items, MachineRecipeHandler.this.getInputPosX(), MachineRecipeHandler.this.getInputPosY()));
                this.output = new PositionedStackIc2(output1.items.get(0), MachineRecipeHandler.this.getOutputPosX(), MachineRecipeHandler.this.getOutputPosY());

                for (int i = 1; i < output1.items.size(); ++i) {
                    this.otherStacks.add(new PositionedStack(output1.items.get(i), MachineRecipeHandler.this.getOutputPosX(), MachineRecipeHandler.this.getOutputPosY() + i * 18));
                }

                this.meta = output1.metadata;
            }
        }

        public List<PositionedStack> getIngredients() {
            return this.getCycledIngredients(MachineRecipeHandler.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }

        public List<PositionedStack> getOtherStacks() {
            return this.otherStacks;
        }
    }
}
