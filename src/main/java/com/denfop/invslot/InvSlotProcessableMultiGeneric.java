package com.denfop.invslot;

import com.denfop.api.Recipes;
import com.denfop.api.inv.IInvSlotProcessableMulti;
import ic2.api.recipe.IMachineRecipeManager;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.item.ItemUpgradeModule;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotProcessableMultiGeneric extends InvSlot implements IInvSlotProcessableMulti {
    public IMachineRecipeManager recipeManager;


    public InvSlotProcessableMultiGeneric(TileEntityInventory base1, String name1, int count,
                                          IMachineRecipeManager recipeManager1) {
        super(base1, name1, 1, Access.I, count, InvSide.TOP);
        this.recipeManager = recipeManager1;
    }

    public boolean accepts(ItemStack itemStack) {
        if (this.recipeManager.equals(Recipes.createscrap))
            return itemStack.isItemEqual(Ic2Items.scrap) || itemStack.isItemEqual(Ic2Items.scrapBox);
        return itemStack == null || !(itemStack.getItem() instanceof ItemUpgradeModule);
    }

    public ItemStack get1(int i) {
        return this.get(i);
    }

    public RecipeOutput process(int slotId) {
        ItemStack input = this.get(slotId);
        if (input == null) {
            return null;
        } else {
            RecipeOutput output = this.getOutputFor(input, false);

            if (output == null) {
                return null;
            } else {
                List<ItemStack> itemsCopy = new ArrayList(output.items.size());

                for (ItemStack itemStack : output.items) {
                    itemsCopy.add(itemStack.copy());
                }
                return new RecipeOutput(output.metadata, itemsCopy);
            }
        }
    }

    public void consume(int slotId) {
        ItemStack input = this.get(slotId);
        if (input == null) {
            throw new IllegalStateException("consume from empty slot");
        } else {
            RecipeOutput output = this.getOutputFor(input, true);
            if (output == null) {
                throw new IllegalStateException("consume without a processing result");
            } else {
                if (input != null && input.stackSize <= 0) {
                    this.put(slotId, null);
                }

            }
        }
    }

    public boolean isEmpty(int slotId) {
        return this.get(slotId) == null;
    }

    public void setRecipeManager(IMachineRecipeManager recipeManager1) {
        this.recipeManager = recipeManager1;
    }

    protected RecipeOutput getOutputFor(ItemStack input, boolean adjustInput) {
        return this.recipeManager.getOutputFor(input, adjustInput);
    }

}