package com.denfop.recipes;

import com.denfop.IUItem;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class CentrifugeRecipe {

	public static void init() {
		addcentrifuge(IUItem.reactorDepletedamericiumDual, new ItemStack(IUItem.radiationresources, 2));
		addcentrifuge(IUItem.reactorDepletedamericiumQuad, new ItemStack(IUItem.radiationresources, 4));
		addcentrifuge(IUItem.reactorDepletedamericiumSimple, new ItemStack(IUItem.radiationresources, 1));

		addcentrifuge(IUItem.reactorDepletedneptuniumDual, new ItemStack(IUItem.radiationresources, 2,1));
		addcentrifuge(IUItem.reactorDepletedneptuniumQuad, new ItemStack(IUItem.radiationresources, 4,1));
		addcentrifuge(IUItem.reactorDepletedneptuniumSimple, new ItemStack(IUItem.radiationresources, 1,1));

		addcentrifuge(IUItem.reactorDepletedcuriumDual, new ItemStack(IUItem.radiationresources, 2,2));
		addcentrifuge(IUItem.reactorDepletedcuriumQuad, new ItemStack(IUItem.radiationresources, 4,2));
		addcentrifuge(IUItem.reactorDepletedcuriumSimple, new ItemStack(IUItem.radiationresources, 1,2));

		addcentrifuge(IUItem.reactorDepletedcaliforniaDual, new ItemStack(IUItem.radiationresources, 2,3));
		addcentrifuge(IUItem.reactorDepletedcaliforniaQuad, new ItemStack(IUItem.radiationresources, 4,3));
		addcentrifuge(IUItem.reactorDepletedcaliforniaSimple, new ItemStack(IUItem.radiationresources, 1,3));

		addcentrifuge(IUItem.reactorDepletedmendeleviumDual, new ItemStack(IUItem.radiationresources, 2,5));
		addcentrifuge(IUItem.reactorDepletedmendeleviumQuad, new ItemStack(IUItem.radiationresources, 4,5));
		addcentrifuge(IUItem.reactorDepletedmendeleviumSimple, new ItemStack(IUItem.radiationresources, 1,5));

		addcentrifuge(IUItem.reactorDepletedberkeliumDual, new ItemStack(IUItem.radiationresources, 2,6));
		addcentrifuge(IUItem.reactorDepletedberkeliumQuad, new ItemStack(IUItem.radiationresources, 4,6));
		addcentrifuge(IUItem.reactorDepletedberkeliumSimple, new ItemStack(IUItem.radiationresources, 1,6));

		addcentrifuge(IUItem.reactorDepletedeinsteiniumDual, new ItemStack(IUItem.radiationresources, 2,7 ));
		addcentrifuge(IUItem.reactorDepletedeinsteiniumQuad, new ItemStack(IUItem.radiationresources, 4,7));
		addcentrifuge(IUItem.reactorDepletedeinsteiniumSimple, new ItemStack(IUItem.radiationresources, 1,7));

		addcentrifuge(IUItem.reactorDepleteduran233Dual, new ItemStack(IUItem.radiationresources, 2,8 ));
		addcentrifuge(IUItem.reactorDepleteduran233Quad, new ItemStack(IUItem.radiationresources, 4,8));
		addcentrifuge(IUItem.reactorDepleteduran233Simple, new ItemStack(IUItem.radiationresources, 1,8));

		addcentrifuge(IUItem.reactorDepletedtoriyDual, new ItemStack(IUItem.toriy, 2));
		addcentrifuge(IUItem.reactorDepletedtoriyQuad, new ItemStack(IUItem.toriy, 4));
		addcentrifuge(IUItem.reactorDepletedtoriySimple, new ItemStack(IUItem.toriy, 1));

		addcentrifuge(IUItem.reactorDepletedprotonDual, new ItemStack(IUItem.proton, 2));
		addcentrifuge(IUItem.reactorDepletedprotonQuad, new ItemStack(IUItem.proton, 4));
		addcentrifuge(IUItem.reactorDepletedprotoneit, new ItemStack(IUItem.proton, 8));
		addcentrifuge(IUItem.reactorDepletedprotonSimple, new ItemStack(IUItem.proton, 1));

	}

	public static void addcentrifuge(ItemStack stack, ItemStack output) {
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setShort("minHeat", (short) 5000);
		Recipes.centrifuge.addRecipe(new RecipeInputItemStack(stack), nbt, output);

	}
}
