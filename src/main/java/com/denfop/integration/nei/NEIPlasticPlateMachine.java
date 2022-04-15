package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.IPlasticPlateRecipemanager;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIPlasticPlateCreator;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import ic2.core.util.StackUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEIPlasticPlateMachine extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIPlasticPlateCreator.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("iu.blockPlasticPlateCreator.name");
    }

    public String getRecipeId() {
        return "iu.blockPlasticPlateCreator";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIPlasticPlate.png";
    }

    public String getOverlayIdentifier() {
        return "placticplatemachine";
    }

    public Map<IPlasticPlateRecipemanager.Input, RecipeOutput> getRecipeList() {
        return Recipes.plasticplate.getRecipes();
    }

    private void drawLiquid(FluidStack stack) {

        IIcon fluidIcon = new ItemStack(stack.getFluid().getBlock()).getIconIndex();
        GuiDraw.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        int liquidHeight = (int) ((float) stack.amount / 12000.0F * 47.0F);
        DrawUtil.drawRepeated(fluidIcon, 5, 4 + 47 - liquidHeight, 12.0D, liquidHeight, GuiDraw.gui.getZLevel());
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(5, 4, 176, 103, 12, 47);
    }

    private void drawLiquidTooltip(FluidStack stack, int recipe) {

        GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        String tooltip = stack.getLocalizedName() + " (" + stack.amount + "mb)";
        GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, 5, 15, 5 + 12, 62);
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 5, 140, 75);
        NEIPlasticPlateMachine.ChemicalFactroryRecipe recipe = (NEIPlasticPlateMachine.ChemicalFactroryRecipe) this.arecipes.get(i);
        drawLiquid(recipe.fluidstack);
    }

    public void drawExtras(int i) {
        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(74, 30, 176, 14, 25, 16, f, 0);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(52, 31, 176, 0, 14, 14, f, 3);
        NEIPlasticPlateMachine.ChemicalFactroryRecipe recipe = (NEIPlasticPlateMachine.ChemicalFactroryRecipe) this.arecipes.get(i);

        this.drawLiquidTooltip(recipe.fluidstack, i);
    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 19, 25, 14),
                getRecipeId()));
    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IPlasticPlateRecipemanager.Input, RecipeOutput> entry : getRecipeList().entrySet())
                this.arecipes.add(new ChemicalFactroryRecipe(entry.getKey().container,
                        entry.getKey().fluidStack, entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IPlasticPlateRecipemanager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes
                            .add(new ChemicalFactroryRecipe(entry.getKey().container,
                                    entry.getKey().fluidStack, entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        for (Map.Entry<IPlasticPlateRecipemanager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if (entry.getKey().container.matches(ingredient))
                this.arecipes.add(new ChemicalFactroryRecipe(entry.getKey().container
                        , entry.getKey().fluidStack, entry.getValue()));
        }
    }

    public class ChemicalFactroryRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final PositionedStack output;

        public final List<PositionedStack> ingredients = new ArrayList<>();
        public final FluidStack fluidstack;

        public ChemicalFactroryRecipe(IRecipeInput container, FluidStack fluidstack, RecipeOutput output1) {
            super();
            List<ItemStack> containerItems = new ArrayList<>();
            for (ItemStack item : container.getInputs())
                containerItems.add(StackUtil.copyWithSize(item, container.getAmount()));

            this.ingredients.add(new PositionedStack(containerItems, 51, 12));
            this.output = new PositionedStack(output1.items.get(0), 111, 29);
            this.fluidstack = fluidstack;
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIPlasticPlateMachine.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output;
        }
    }
}
