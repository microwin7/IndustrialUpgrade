package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.gui.GUIConverterSolidMatter;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Map;

import static codechicken.lib.gui.GuiDraw.drawTexturedModalRect;


public class NEIConverterMatter extends MachineRecipeHandler {
    final EnumChatFormatting[] name = {EnumChatFormatting.DARK_PURPLE, EnumChatFormatting.YELLOW, EnumChatFormatting.BLUE, EnumChatFormatting.RED, EnumChatFormatting.GRAY, EnumChatFormatting.GREEN, EnumChatFormatting.DARK_AQUA, EnumChatFormatting.AQUA};

    public NEIConverterMatter() {
    }

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIConverterSolidMatter.class;
    }

    public String getRecipeName() {
        return StatCollector.translateToLocal("blockConverterSolidMatter.name");
    }

    protected int getInputPosX() {
        return 46;
    }

    protected int getInputPosY() {
        return 48;
    }

    protected int getOutputPosX() {
        return 112;
    }

    protected int getOutputPosY() {
        return 48;
    }

    public String getRecipeId() {
        return "blockConverterSolidMatter.name";
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        drawTexturedModalRect(0, 0, 5, 3, 168, 135);
        CachedIORecipe recipe = (CachedIORecipe) this.arecipes.get(i);

        for (int j = 0; j < 8; j++) {
            double p = ((recipe.meta.getDouble("quantitysolid_" + j) / 5000) * 11);
            int l = j - 6;
            if (l != 1)
                l = 0;
            drawTexturedModalRect((int) (21 + p), 22 + 15 * j - l, 182, 12, 1, 3);


        }

        for (int j = 0; j < 8; j++) {
            double p = ((recipe.meta.getDouble("quantitysolid_" + j) / 5000) * 11);
            int l = j - 6;
            if (l != 1)
                l = 0;
            GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
            Point mouse = GuiDraw.getMousePosition();
            Point offset = gui.getRecipePosition(i);
            String tooltip = name[j] + ModUtils.getString(recipe.meta.getDouble("quantitysolid_" + j));

            GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, (int) (21 + p), 22 + 15 * j - l, 38, 30 + 15 * j - l);


        }


    }

    public void loadCraftingRecipes(ItemStack result) {


        for (Map.Entry<IRecipeInput, RecipeOutput> iRecipeInputRecipeOutputEntry : this.getRecipeList().entrySet()) {
            if (iRecipeInputRecipeOutputEntry.getKey().matches(result)) {
                this.arecipes.add(new CachedIORecipe(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue()));
            }
        }
    }

    public void drawExtras(int i) {

        float f = (this.ticks >= 20) ? (((this.ticks - 20) % 20) / 20.0F) : 0.0F;
        drawProgressBar(112, 15, 177, 43, 16, 34, f, 1);
        drawProgressBar(75, 48, 177, 24, 34, 16, f, 0);
        drawProgressBar(135, 48, 177, 24, 34, 16, f, 2);
        drawProgressBar(112, 67, 177, 43, 16, 34, f, 3);
        f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(115, 112, 177, 81, 37, 11, f, 0);

    }

    public void onUpdate() {
        super.onUpdate();
        ++this.ticks;
    }

    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(71, 46, 35, 15),
                getRecipeId()));
    }

    public String getGuiTexture() {
        return Constants.TEXTURES + ":textures/gui/GUIConverterSolidMatter.png";

    }

    public int recipiesPerPage() {
        return 1;
    }

    public String getOverlayIdentifier() {
        return "ñonvertersolidmatter";
    }

    public Map<IRecipeInput, RecipeOutput> getRecipeList() {
        return Recipes.matterrecipe.getRecipes();
    }
}
