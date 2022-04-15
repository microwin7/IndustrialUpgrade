package com.denfop.api.recipe;

import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListRecipes implements IRecipes{

   public Map<String ,List<BaseMachineRecipe>> map_recipes = new HashMap<>();
   public Map<String ,IBaseRecipe> map_recipe_managers = new HashMap<>();

    public ListRecipes(){
      init();
   }

    public void init() {
        this.addRecipeManager("alloysmelter",2,true);
        this.addRecipeManager("enrichment",2,true);
        this.addRecipeManager("painter",2,true);
        this.addRecipeManager("sunnuriumpanel",2,true);
        this.addRecipeManager("synthesis",2,true);
        this.addRecipeManager("upgradeblock",2,true);
        this.addRecipeManager("advalloysmelter",3,true);
        this.addRecipeManager("genstone",2,false);
        this.addRecipeManager("microchip",5,true);
        this.addRecipeManager("sunnurium",4,true);
        this.addRecipeManager("wither",7,true);
        this.addRecipeManager("doublemolecular",2,true);
        this.addRecipeManager("molecular",1,true);
        this.addRecipeManager("plastic",2,true);
        this.addRecipeManager("plasticplate",1,true);
        this.addRecipeManager("converter",1,false);
    }
    public IBaseRecipe getRecipe(String name){
        return this.map_recipe_managers.get(name);
    }
    public void addRecipeManager(String name,int size, boolean consume){
       this.map_recipe_managers.put(name,new RecipeManager(name,size,consume));
        if(!this.map_recipes.containsKey(name)) {
            List<BaseMachineRecipe> lst = new ArrayList<>();
            this.map_recipes.put(name, lst);
        }
   }
   public void removeRecipe(String name, RecipeOutput output){
       List<BaseMachineRecipe> recipes = this.map_recipes.get(name);
       recipes.removeIf(recipe -> recipe.getOutput().items.get(0).isItemEqual(output.items.get(0)));

   }

    @Override
    public RecipeOutput getRecipeOutputFluid(
            final String name,
            final boolean adjustInput,
            final List<ItemStack> list,
            final FluidTank tank
    ) {
        List<BaseMachineRecipe> recipes = this.map_recipes.get(name);
        int size = this.map_recipe_managers.get(name).getSize();
        for(BaseMachineRecipe baseMachineRecipe : recipes) {
            int[] col = new int[size];
            int[] col1 = new int[size];
            List<Integer> lst = new ArrayList<>();
            for (int i = 0; i < size; i++)
                lst.add(i);
            List<IRecipeInput> recipeInputList = baseMachineRecipe.input.getInputs();
            List<Integer> lst1 = new ArrayList<>();
            if(tank.getFluid() == null)
                return null;
            if(!tank.getFluid().isFluidEqual(baseMachineRecipe.input.getFluid()))
                return null;
            if(tank.getFluidAmount() < 1000)
                return null;

            for (int j = 0; j < list.size(); j++) {
                for (int i = 0; i < recipeInputList.size(); i++) {
                    if (recipeInputList.get(i).matches(list.get(j)) && !lst1.contains(i)) {
                        lst1.add(i);

                        col1[j] = i;
                        break;
                    }
                }
            }
            if (lst.size() == lst1.size()) {
                for (int j = 0; j < list.size(); j++) {
                    ItemStack stack2 = recipeInputList.get(col1[j]).getInputs().get(0);
                    ItemStack stack = list.get(j);
                    if (stack.stackSize < stack2.stackSize) {
                        return null;
                    }
                    col[j] = stack2.stackSize;
                }
                if (adjustInput) {
                    for (int j = 0; j < list.size(); j++) {
                        list.get(j).stackSize = list.get(j).stackSize - col[j];
                    }
                    tank.drain(1000,true);
                    break;
                } else {
                    return baseMachineRecipe.getOutput();
                }
            }
        }
        return null;
    }

    public void addRecipeList(String name, List<BaseMachineRecipe> list){
       if(!this.map_recipes.containsKey(name))
       this.map_recipes.put(name,list);
       else
           this.map_recipes.replace(name,list);
   }
    public void addRecipe(String name, BaseMachineRecipe recipe){
        if(!this.map_recipes.containsKey(name)) {
            List<BaseMachineRecipe> lst = new ArrayList<>();
            lst.add(recipe);
            this.map_recipes.put(name, lst);
        }  else
           this.map_recipes.get(name).add(recipe);
    }
    public List<BaseMachineRecipe> getRecipeList(String name){
       return   this.map_recipes.get(name);
    }
    public RecipeOutput getRecipeOutput(String name,boolean adjustInput, ItemStack... stacks){
        List<BaseMachineRecipe> recipes = this.map_recipes.get(name);
        List<ItemStack> stack1 = Arrays.asList(stacks);
        int size = this.map_recipe_managers.get(name).getSize();
        for(BaseMachineRecipe baseMachineRecipe : recipes) {
            int[] col = new int[size];
            int[] col1 = new int[size];
            List<Integer> lst = new ArrayList<>();
            for (int i = 0; i < size; i++)
                lst.add(i);
            List<IRecipeInput> recipeInputList = baseMachineRecipe.input.getInputs();
            List<Integer> lst1 = new ArrayList<>();
            for (int j = 0; j < stack1.size(); j++) {
                for (int i = 0; i < recipeInputList.size(); i++) {
                    if (recipeInputList.get(i).matches(stack1.get(j)) && !lst1.contains(i)) {
                        lst1.add(i);

                        col1[j] = i;
                        break;
                    }
                }
            }
            if (lst.size() == lst1.size()) {
                for (int j = 0; j < stack1.size(); j++) {
                    ItemStack stack2 = recipeInputList.get(col1[j]).getInputs().get(0);
                    ItemStack stack = stack1.get(j);
                    if (stack.stackSize < stack2.stackSize) {
                        return null;
                    }
                    col[j] = stack2.stackSize;
                }
                if (adjustInput) {
                    for (int j = 0; j < stack1.size(); j++) {
                        stack1.get(j).stackSize = stack1.get(j).stackSize - col[j];
                    }
                    break;
                } else {
                    return baseMachineRecipe.getOutput();
                }
            }
        }
       return null;
    }

    @Override
    public RecipeOutput getRecipeOutput(final String name, final boolean adjustInput, final List<ItemStack> stacks) {
        List<BaseMachineRecipe> recipes = this.map_recipes.get(name);
        int size = this.map_recipe_managers.get(name).getSize();
        for(BaseMachineRecipe baseMachineRecipe : recipes) {
            int[] col = new int[size];
            int[] col1 = new int[size];
            List<Integer> lst = new ArrayList<>();
            for (int i = 0; i < size; i++)
                lst.add(i);
            List<IRecipeInput> recipeInputList = baseMachineRecipe.input.getInputs();
            List<Integer> lst1 = new ArrayList<>();
            for (int j = 0; j < stacks.size(); j++) {
                for (int i = 0; i < recipeInputList.size(); i++) {
                    if (recipeInputList.get(i).matches(stacks.get(j)) && !lst1.contains(i)) {
                        lst1.add(i);

                        col1[j] = i;
                        break;
                    }
                }
            }
            if (lst.size() == lst1.size()) {
                for (int j = 0; j < stacks.size(); j++) {
                    ItemStack stack2 = recipeInputList.get(col1[j]).getInputs().get(0);
                    ItemStack stack = stacks.get(j);
                    if (stack.stackSize < stack2.stackSize) {
                        return null;
                    }
                    col[j] = stack2.stackSize;
                }
                if (adjustInput) {
                    for (int j = 0; j < stacks.size(); j++) {
                        stacks.get(j).stackSize = stacks.get(j).stackSize - col[j];
                    }
                    break;
                } else {
                    return baseMachineRecipe.getOutput();
                }
            }
        }
        return null;
    }

}
