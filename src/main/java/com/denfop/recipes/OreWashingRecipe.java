package com.denfop.recipes;

import com.denfop.IUItem;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import ic2.core.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class OreWashingRecipe {
    public static void init() {
        addrecipe(0, null);
        addrecipe(1, new ItemStack(Blocks.sand));
        addrecipe(2, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 5));
        addrecipe(3, new ItemStack(IUItem.smalldust, 2, 16));
        addrecipe(6, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 1));
        addrecipe(7, new ItemStack(IUItem.smalldust, 2, 16));
        addrecipe(8, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 0));
        addrecipe(9, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 2));
        addrecipe(10, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 0));
        addrecipe(11, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 5));
        addrecipe(12, new ItemStack(IUItem.smalldust, 2, 10));
        addrecipe(15, new ItemStack(Ic2Items.smallLeadDust.getItem(), 2, 5));
        addrecipe(16, null);
        addrecipe(17, new ItemStack(IUItem.smalldust, 2, 9));
        addrecipe(18, new ItemStack(IUItem.smalldust, 2, 15));
    }

    public static void addrecipe(int meta, ItemStack output) {
        ItemStack[] stack;
        if (output != null)
            stack = new ItemStack[3];
        else {
            stack = new ItemStack[2];

        }
        stack[0] = new ItemStack(IUItem.purifiedcrushed, 1, meta);
        stack[1] = Ic2Items.stoneDust;
        if (output != null)
            stack[2] = output;
        NBTTagCompound nbt = ModUtils.nbt();
        nbt.setInteger("amount", 1000);
        Recipes.oreWashing.addRecipe(new RecipeInputItemStack(new ItemStack(IUItem.crushed, 1, meta)), nbt, stack);
    }
}
