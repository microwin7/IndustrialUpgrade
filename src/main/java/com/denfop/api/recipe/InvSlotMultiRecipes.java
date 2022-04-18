package com.denfop.api.recipe;

import com.denfop.Ic2Items;
import com.denfop.api.Recipes;
import com.denfop.tiles.base.TileEntityConverterSolidMatter;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.IRecipeInputFactory;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.item.upgrade.ItemUpgradeModule;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.List;

public class InvSlotMultiRecipes extends InvSlot {

    private IBaseRecipe recipe;
    private final IMultiUpdateTick tile;
    private FluidTank tank;

    public InvSlotMultiRecipes(final TileEntityInventory base, IBaseRecipe baseRecipe, IMultiUpdateTick tile, int size) {
        super(base, "input", Access.I, size);
        this.recipe = baseRecipe;
        this.tile = tile;
        this.tank = null;
    }

    public InvSlotMultiRecipes(final TileEntityInventory base, String baseRecipe, IMultiUpdateTick tile, int size) {
        this(base, Recipes.recipes.getRecipe(baseRecipe), tile,size);

    }

    public InvSlotMultiRecipes(final TileEntityInventory base, String baseRecipe, IMultiUpdateTick tile, FluidTank tank, int size) {
        this(base, Recipes.recipes.getRecipe(baseRecipe), tile,size);
        this.tank = tank;
    }

    @Override
    public void put(final int index, final ItemStack content) {
        super.put(index, content);
        if(!recipe.getName().equals("recycler")) {
            this.tile.setRecipeOutput(this.process(index), index);
        }else{
            ((TileEntityMultiMachine)this.tile).getOutput(index);

        }
        this.tile.onUpdate();
    }

    @Override
    public boolean accepts(final ItemStack itemStack) {
        return !itemStack.isEmpty() && !(itemStack.getItem() instanceof ItemUpgradeModule);
    }

    public void consume(int number, int amount) {
        this.consume(number, amount, false, false);
    }

    public void consume(int number, int amount, boolean simulate, boolean consumeContainers) {


        ItemStack stack = this.get(number);
        if (!stack.isEmpty() && stack.getCount() >= 1 && this.accepts(stack) && (stack.getCount() >= 1 || consumeContainers || !stack
                .getItem()
                .hasContainerItem(stack))) {
            int currentAmount = Math.min(amount, stack.getCount());
            if (!simulate) {
                if (stack.getCount() == currentAmount) {
                    if (!consumeContainers && stack.getItem().hasContainerItem(stack)) {
                        this.put(number, stack.getItem().getContainerItem(stack));
                    } else {
                        this.put(number, null);
                    }
                } else {
                    stack.setCount(stack.getCount() - currentAmount);
                }
            }


        }


    }

    @Override
    public ItemStack get(final int index) {
        return super.get(index);
    }
    public boolean continue_proccess(InvSlotOutput slot,int slotid){
     return slot.canAdd(tile.getRecipeOutput(slotid).output.items) && this.get(slotid).getCount() >= tile.getRecipeOutput(slotid).input.getInputs().get(0).getInputs().get(0).getCount();
    }
    public BaseMachineRecipe process(int slotid) {

            if (this.get(slotid).isEmpty()) {
                return null;
            }

        return getOutputFor(slotid);
    }
    public BaseMachineRecipe fastprocess(int slotid) {

        if (this.get(slotid).isEmpty()) {
            return null;
        }

        BaseMachineRecipe output;
        output = getOutputFor(slotid);


        return output;
    }
    public BaseMachineRecipe consume(int slotid) {

            if (this.get(slotid).isEmpty()) {
                throw new NullPointerException();
            }
        if(!this.recipe.getName().equals("recycler")) {
            List<ItemStack> list = new ArrayList<>();
            list.add(this.get(slotid));
            if (this.tank == null) {
                return Recipes.recipes.getRecipeOutput(this.recipe.getName(), this.recipe.consume(), list);
            } else {
                return Recipes.recipes.getRecipeOutputFluid(this.recipe.getName(), this.recipe.consume(), list, this.tank);
            }
        }else{
            this.get(slotid).shrink(1);
            final IRecipeInputFactory input = ic2.api.recipe.Recipes.inputFactory;
            return new BaseMachineRecipe(new Input(input.forStack(this.get(slotid))),new RecipeOutput(null, Ic2Items.scrap)) ;
        }
    }

    private BaseMachineRecipe getOutputFor(int slotid) {
        List<ItemStack> list = new ArrayList<>();
        list.add(this.get(slotid));
        if (this.tank == null) {
            return Recipes.recipes.getRecipeMultiOutput(this.recipe.getName(), false, list);
        } else {
            return Recipes.recipes.getRecipeOutputFluid(this.recipe.getName(), false, list, this.tank);
        }

    }
    public void setNameRecipe(String nameRecipe){
        this.recipe = Recipes.recipes.getRecipe(nameRecipe);
    }


}
