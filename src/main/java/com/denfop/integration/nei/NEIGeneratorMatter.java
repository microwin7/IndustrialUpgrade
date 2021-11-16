package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.gui.GUISolidMatter;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.StackUtil;
import ic2.neiIntegration.core.PositionedStackIc2;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NEIGeneratorMatter extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUISolidMatter.class;
    }

    public String getRecipeName() {
        for (CachedRecipe arecipe : this.arecipes) {
            GeneratorRecipe recipe = ((GeneratorRecipe) arecipe);
            switch (recipe.output.items.get(0).getItemDamage()) {
                case 0:
                    return StatCollector.translateToLocal("GenMatter_matter.name");
                case 1:
                    return StatCollector.translateToLocal("GenSun_matter.name");
                case 2:
                    return StatCollector.translateToLocal("GenAqua_matter.name");
                case 3:
                    return StatCollector.translateToLocal("GenNether_matter.name");
                case 4:
                    return StatCollector.translateToLocal("GenNight_matter.name");
                case 5:
                    return StatCollector.translateToLocal("GenEarth_matter.name");
                case 6:
                    return StatCollector.translateToLocal("GenEnd_matter.name");
                case 7:
                    return StatCollector.translateToLocal("GenAer_matter.name");

            }
        }
        return "";


    }

    public String getRecipeId() {
        return "iu.genmatter";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUISolidMatter.png";
    }

    public String getOverlayIdentifier() {
        return "genmatter";
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return Recipes.mattergenerator.getRecipes();
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 5, 140, 75);
        GeneratorRecipe recipe = (GeneratorRecipe) this.arecipes.get(i);

        GuiDraw.drawString(StatCollector.translateToLocal("cost.name") + " " + ModUtils.getString((double) recipe.output.metadata.getInteger("amount")) + "EU", 2, 70, 4210752);

    }

    public void drawExtras(int i) {

    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {

    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(this.getRecipeId())) {

            for (Map.Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : this.getRecipeList().entrySet()) {
                this.arecipes.add(new NEIGeneratorMatter.GeneratorRecipe(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue()));
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }

    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadCraftingRecipes(ItemStack result) {
        for (Map.Entry<IRecipeInput, RecipeOutput> entry : getRecipeList().entrySet()) {
            for (ItemStack output : entry.getValue().items) {
                if (NEIServerUtils.areStacksSameTypeCrafting(output, result))
                    this.arecipes.add(new GeneratorRecipe(entry.getKey(), entry.getValue()));
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {

    }

    public class GeneratorRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final RecipeOutput output;

        public final List<PositionedStack> ingredients = new ArrayList<>();
        public final PositionedStack output1;

        public GeneratorRecipe(IRecipeInput tag, RecipeOutput output1) {
            super();
            this.output = output1;
            if (output1.items.isEmpty())
                throw new IllegalArgumentException(
                        "Output must not be empty (recipe " + tag + " -> " + output1 + ").");
            if (output1.items.contains(null))
                throw new IllegalArgumentException(
                        "Output must not contain null (recipe " + tag + " -> " + output1 + ").");
            List<ItemStack> items = new ArrayList<>();
            for (ItemStack item : tag.getInputs())
                items.add(StackUtil.copyWithSize(item, tag.getAmount()));
            this.output1 = new PositionedStackIc2(output1.items.get(0),
                    64, 27);

        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIGeneratorMatter.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return this.output1;
        }
    }
}
