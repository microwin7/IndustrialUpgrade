package com.denfop.integration.crafttweaker;

import com.denfop.api.IDoubleMachineRecipeManager;
import com.denfop.api.Recipes;
import ic2.api.recipe.RecipeOutput;
import minetweaker.MineTweakerAPI;
import minetweaker.OneWayAction;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import minetweaker.mods.ic2.IC2RecipeInput;
import modtweaker2.helpers.InputHelper;
import modtweaker2.utils.BaseMapRemoval;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ZenClass("mods.industrialupgrade.Synthesis")
public class CTSynthesis {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container, IIngredient fill, int percent) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setDouble("percent", percent);
        MineTweakerAPI.apply(new AddSynthesisIngredientAction(container, fill, output, tag));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        LinkedHashMap<IDoubleMachineRecipeManager.Input, RecipeOutput> recipes = new LinkedHashMap();

        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> iRecipeInputRecipeOutputEntry : Recipes.synthesis.getRecipes().entrySet()) {

            for (ItemStack stack : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (stack.isItemEqual(InputHelper.toStack(output))) {
                    recipes.put(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue());
                }
            }
        }

        MineTweakerAPI.apply(new CTSynthesis.Remove(recipes));
    }

    private static class AddSynthesisIngredientAction extends OneWayAction {
        private final IIngredient container;

        private final IIngredient fill;

        private final IItemStack output;
        private final NBTTagCompound nbt;

        public AddSynthesisIngredientAction(IIngredient container, IIngredient fill, IItemStack output, NBTTagCompound nbt) {
            this.container = container;
            this.fill = fill;
            this.output = output;
            this.nbt = nbt;
        }

        public static ItemStack getItemStack(IItemStack item) {
            if (item == null) {
                return null;
            } else {
                Object internal = item.getInternal();
                if (!(internal instanceof ItemStack)) {
                    MineTweakerAPI.logError("Not a valid item stack: " + item);
                }

                return new ItemStack(((ItemStack) internal).getItem(), item.getAmount(), item.getDamage());
            }
        }

        public void apply() {
            Recipes.synthesis.addRecipe(
                    new IC2RecipeInput(this.container),
                    new IC2RecipeInput(this.fill), this.nbt,
                    getItemStack(this.output));

        }

        public String describe() {
            return "Adding synthesis recipe " + this.container + " + " + this.fill + " => " + this.output;
        }

        public Object getOverrideKey() {
            return null;
        }

        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + ((this.container != null) ? this.container.hashCode() : 0);
            hash = 67 * hash + ((this.fill != null) ? this.fill.hashCode() : 0);
            hash = 67 * hash + ((this.output != null) ? this.output.hashCode() : 0);
            hash = 67 * hash + ((this.nbt != null) ? this.nbt.hashCode() : 0);
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            AddSynthesisIngredientAction other = (AddSynthesisIngredientAction) obj;
            if (!Objects.equals(this.container, other.container))
                return false;
            if (!Objects.equals(this.fill, other.fill))
                return false;
            return Objects.equals(this.output, other.output);
        }
    }

    private static class Remove extends BaseMapRemoval<IDoubleMachineRecipeManager.Input, RecipeOutput> {
        protected Remove(Map<IDoubleMachineRecipeManager.Input, RecipeOutput> recipes) {
            super("synthesis", Recipes.synthesis.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }
}
