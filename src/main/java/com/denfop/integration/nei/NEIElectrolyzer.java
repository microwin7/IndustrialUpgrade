package com.denfop.integration.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.denfop.Constants;
import com.denfop.IUItem;
import com.denfop.api.IFluidRecipeManager;
import com.denfop.api.Recipes;
import com.denfop.block.base.BlocksItems;
import com.denfop.gui.GUIElectrolyzer;
import com.denfop.item.ItemBucket;
import com.denfop.item.ItemCell;
import ic2.core.util.DrawUtil;
import ic2.core.util.GuiTooltipHelper;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NEIElectrolyzer extends TemplateRecipeHandler {
    int ticks;

    public Class<? extends GuiContainer> getGuiClass() {
        return GUIElectrolyzer.class;
    }

    public String getRecipeName() {

        return StatCollector.translateToLocal("iu.blockElectrolyzer.name");
    }

    public String getRecipeId() {
        return "iu.blockElectrolyzer";
    }

    public String getGuiTexture() {

        return Constants.TEXTURES + ":textures/gui/GUIElectolyzer.png";
    }

    public String getOverlayIdentifier() {

        return "electolyzer";
    }

    public Map<IFluidRecipeManager.Input, FluidStack[]> getRecipeList() {
        return Recipes.electrolyzer.getRecipes();
    }

    private void drawLiquid(FluidStack stack, int x) {

        IIcon fluidIcon = new ItemStack(stack.getFluid().getBlock()).getIconIndex();
        GuiDraw.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        int liquidHeight = (int) ((float) stack.amount / 8000.0F * 47.0F);
        DrawUtil.drawRepeated(fluidIcon, x, 5 + 47 - liquidHeight, 12.0D, liquidHeight, GuiDraw.gui.getZLevel());
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(x, 5 + 2, 176, 57, 12, 46);
    }

    private void drawLiquidTooltip(FluidStack stack, int recipe, int x) {

        GuiRecipe gui = (GuiRecipe) Minecraft.getMinecraft().currentScreen;
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        String tooltip = stack.getLocalizedName() + " (" + stack.amount + "mb)";
        GuiTooltipHelper.drawAreaTooltip(mouse.x - (gui.width - 176) / 2 - offset.x, mouse.y - (gui.height - 176) / 2 - offset.y, tooltip, x, 5, x + 12, 52);
    }

    public void drawBackground(int i) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 5, 140, 75);
        NEIElectrolyzer.ElectrolyzerRecipe recipe = (NEIElectrolyzer.ElectrolyzerRecipe) this.arecipes.get(i);
        drawLiquid(recipe.fluidstack, 11);
        drawLiquid(recipe.output[0], 73);
        drawLiquid(recipe.output[1], 105);
    }

    public void drawExtras(int i) {
        float f = (this.ticks <= 20) ? (this.ticks / 20.0F) : 1.0F;
        drawProgressBar(34, 64, 177, 104, 29, 9, f, 0);
        NEIElectrolyzer.ElectrolyzerRecipe recipe = (NEIElectrolyzer.ElectrolyzerRecipe) this.arecipes.get(i);
        this.drawLiquidTooltip(recipe.fluidstack, i, 11);
        this.drawLiquidTooltip(recipe.output[0], i, 73);
        this.drawLiquidTooltip(recipe.output[1], i, 105);


    }

    public void onUpdate() {
        super.onUpdate();
        this.ticks++;
    }

    public void loadTransferRects() {

    }

    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipeId())) {
            for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry : getRecipeList().entrySet())
                this.arecipes.add(new ElectrolyzerRecipe(entry.getKey().fluidStack,
                        entry.getValue()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    public int recipiesPerPage() {
        return 1;
    }

    public void loadCraftingRecipes(ItemStack result) {
        FluidStack stack = null;
        if (result.getItem() instanceof IFluidContainerItem) {
            IFluidContainerItem container = (IFluidContainerItem) result.getItem();
            stack = container.getFluid(result);
        } else if (result.getItem() instanceof ItemBlock && Block.getBlockFromItem(result.getItem()) instanceof BlockFluidBase) {
            stack = new FluidStack(((BlockFluidBase) Block.getBlockFromItem(result.getItem())).getFluid(), 1000);
        } else if (result.getItem() instanceof ItemBucket) {
            if (result.getItemDamage() == 7)
                stack = new FluidStack(BlocksItems.getFluid("fluidoxy"), 1000);
            if (result.getItemDamage() == 8)
                stack = new FluidStack(BlocksItems.getFluid("fluidhyd"), 1000);

        } else if (result.getItem() instanceof ItemCell) {
            if (result.getItemDamage() == 8)
                stack = new FluidStack(BlocksItems.getFluid("fluidoxy"), 1000);
            if (result.getItemDamage() == 9)
                stack = new FluidStack(BlocksItems.getFluid("fluidhyd"), 1000);

        }

        if (stack != null && stack.getFluid() != null) {

            for (Map.Entry<IFluidRecipeManager.Input, FluidStack[]> inputEntry : this.getRecipeList().entrySet()) {
                for (FluidStack fluid : inputEntry.getValue())
                    if (stack.isFluidEqual(fluid)) {
                        this.arecipes.add(new ElectrolyzerRecipe(inputEntry.getKey().fluidStack,
                                inputEntry.getValue()));
                    }
            }
        }
    }

    public void loadUsageRecipes(ItemStack ingredient) {
        FluidStack stack = null;
        if (ingredient.getItem() instanceof IFluidContainerItem) {
            stack = ((IFluidContainerItem) ingredient.getItem()).getFluid(ingredient);
        } else if (ingredient.getItem() instanceof ItemBlock && Block.getBlockFromItem(ingredient.getItem()) instanceof BlockFluidBase) {
            stack = new FluidStack(((BlockFluidBase) Block.getBlockFromItem(ingredient.getItem())).getFluid(), 1000);
        }

        Iterator var3 = this.getRecipeList().entrySet().iterator();

        while (true) {
            Map.Entry<IFluidRecipeManager.Input, FluidStack[]> entry;
            do {
                if (!var3.hasNext()) {
                    return;
                }

                entry = (Map.Entry) var3.next();
            } while ((stack == null || stack.getFluid() == null || !stack.getFluid().equals(entry.getKey().fluidStack.getFluid())));

            this.arecipes.add(new ElectrolyzerRecipe(entry.getKey().fluidStack,
                    entry.getValue()));
        }
    }

    public class ElectrolyzerRecipe extends TemplateRecipeHandler.CachedRecipe {
        public final FluidStack[] output;

        public final List<PositionedStack> ingredients = new ArrayList<>();
        public final FluidStack fluidstack;

        public ElectrolyzerRecipe(FluidStack fluidstack, FluidStack[] output1) {
            super();

            this.ingredients.add(new PositionedStack(new ItemStack(IUItem.cathode), 49, 29));
            this.ingredients.add(new PositionedStack(new ItemStack(IUItem.anode), 125, 29));
            this.output = output1;
            this.fluidstack = fluidstack;
        }

        public List<PositionedStack> getIngredients() {
            return getCycledIngredients(NEIElectrolyzer.this.cycleticks / 20, this.ingredients);
        }

        public PositionedStack getResult() {
            return null;
        }
    }
}
