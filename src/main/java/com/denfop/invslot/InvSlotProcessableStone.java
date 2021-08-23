package com.denfop.invslot;

import com.denfop.api.Recipes;
import com.denfop.tiles.mechanism.TileEntityGenerationStone;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InvSlotProcessableStone extends InvSlotProcessable {

	public InvSlotProcessableStone(TileEntityInventory base1, String name1, int oldStartIndex1, int count) {
		super(base1, name1, oldStartIndex1, count);

	}

	public boolean accepts(ItemStack itemStack) {
        return itemStack == null || !(itemStack.getItem() instanceof ic2.core.item.ItemUpgradeModule);

    }

	protected RecipeOutput getOutput(ItemStack container, ItemStack fill) {

		return Recipes.GenStone.getOutputFor(container, fill, false, false);

	}

	protected RecipeOutput getOutputFor(ItemStack input, ItemStack input1) {
		return getOutput(input, input1);
	}

	public RecipeOutput process() {
		ItemStack input = ((TileEntityGenerationStone) this.base).inputSlotA.get();
		ItemStack input1 = ((TileEntityGenerationStone) this.base).inputSlotB.get();
		if (input == null)
			return null;
		if (input1 == null)
			return null;
		RecipeOutput output = getOutputFor(input, input1);
        if (output == null)
			return null;
		List<ItemStack> itemsCopy = new ArrayList<>(output.items.size());
		itemsCopy.addAll(output.items);
		return new RecipeOutput(output.metadata, itemsCopy);
	}

	public void consume() {

		ItemStack input = ((TileEntityGenerationStone) this.base).inputSlotA.get();
		ItemStack input1 = ((TileEntityGenerationStone) this.base).inputSlotB.get();
		if (input != null && input.stackSize <= 1)
			((TileEntityGenerationStone) this.base).inputSlotA.put(null);
		if (input1 != null && input1.stackSize <= 1)
			((TileEntityGenerationStone) this.base).inputSlotB.put(null);



	}


}
