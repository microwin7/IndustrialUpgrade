package com.denfop.integration.crafttweaker;

import com.denfop.api.ITripleMachineRecipeManager;
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
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ZenClass("mods.industrialupgrade.AdvAlloySmelter")
public class CTAdvAlloySmelter {
    @ZenMethod
    public static void addRecipe(IItemStack output, IIngredient container, IIngredient fill, IIngredient fill1) {
        MineTweakerAPI.apply(new AddAlloSmelterIngredientAction(container, fill, fill1, output));
    }

    private static class AddAlloSmelterIngredientAction extends OneWayAction {
        private final IIngredient container;

        private final IIngredient fill;
        private final IIngredient fill1;
        private final IItemStack output;

        public AddAlloSmelterIngredientAction(IIngredient container, IIngredient fill, IIngredient fill1, IItemStack output) {
            this.container = container;
            this.fill = fill;
            this.fill1 = fill1;
            this.output = output;
        }

        public void apply() {
            Recipes.Alloyadvsmelter.addRecipe(
                    new IC2RecipeInput(this.container),
                    new IC2RecipeInput(this.fill),
                    new IC2RecipeInput(this.fill1),


                    getItemStack(this.output));


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

        public String describe() {
            return "Adding canner bottle recipe " + this.container + " + " + this.fill + " + " + this.fill1 + " => " + this.output;
        }

        public Object getOverrideKey() {
            return null;
        }

        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + ((this.container != null) ? this.container.hashCode() : 0);
            hash = 67 * hash + ((this.fill != null) ? this.fill.hashCode() : 0);
            hash = 67 * hash + ((this.fill1 != null) ? this.fill1.hashCode() : 0);
            hash = 67 * hash + ((this.output != null) ? this.output.hashCode() : 0);
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            AddAlloSmelterIngredientAction other = (AddAlloSmelterIngredientAction) obj;
            if (!Objects.equals(this.container, other.container))
                return false;
            if (!Objects.equals(this.fill, other.fill))
                return false;
            if (!Objects.equals(this.fill1, other.fill1))
                return false;
            return Objects.equals(this.output, other.output);
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        LinkedHashMap<ITripleMachineRecipeManager.Input, RecipeOutput> recipes = new LinkedHashMap();

        for (Map.Entry<ITripleMachineRecipeManager.Input, RecipeOutput> iRecipeInputRecipeOutputEntry : Recipes.Alloyadvsmelter.getRecipes().entrySet()) {

            for (ItemStack stack : iRecipeInputRecipeOutputEntry.getValue().items) {
                if (stack.isItemEqual(InputHelper.toStack(output))) {
                    recipes.put(iRecipeInputRecipeOutputEntry.getKey(), iRecipeInputRecipeOutputEntry.getValue());
                }
            }
        }

        MineTweakerAPI.apply(new CTAdvAlloySmelter.Remove(recipes));
    }

    private static class Remove extends BaseMapRemoval<ITripleMachineRecipeManager.Input, RecipeOutput> {
        protected Remove(Map<ITripleMachineRecipeManager.Input, RecipeOutput> recipes) {
            super("advalloysmelter", Recipes.Alloyadvsmelter.getRecipes(), recipes);
        }

        protected String getRecipeInfo(Map.Entry<ITripleMachineRecipeManager.Input, RecipeOutput> recipe) {
            return recipe.toString();
        }
    }
}
