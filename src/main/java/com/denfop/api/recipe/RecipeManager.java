package com.denfop.api.recipe;

public class RecipeManager implements IBaseRecipe{

    private final String name;
    private final int size;
    private final boolean consume;

    public RecipeManager(String name, int size, boolean consume){
        this.name = name;
        this.size = size;
        this.consume= consume;
    }
    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean consume() {
        return this.consume;
    }

}
