package com.denfop.api.space.colonies;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class ColonieHouse extends Building implements IBuildingHouse {

    private final EnumHouses houses;
    private int peoples;

    public ColonieHouse(final String name, EnumHouses houses) {
        super(name);
        this.houses = houses;
        Random rand = new Random();
        this.peoples = rand.nextInt(houses.getMax() + 1);
    }

    @Override
    public EnumHousesLevel getLevel() {
        return this.houses.getLevel();
    }

    @Override
    public int getPeoples() {
        return this.peoples;
    }

    @Override
    public void setPeoples(int peoples) {
        this.peoples = peoples;
    }

    @Override
    public int getEnergy() {
        return this.houses.getEnergy();
    }

    @Override
    public int getMaxPeoples() {
        return this.houses.getMax();
    }

    @Override
    public void addPeoples(int peoples) {
        assert this.peoples + peoples <= this.getMaxPeoples();
        this.peoples += peoples;
    }

    @Override
    public NBTTagCompound writeTag(final NBTTagCompound tag) {
        super.writeTag(tag);
        tag.setInteger("id", this.houses.ordinal());
        tag.setInteger("peoples", this.peoples);
        return tag;
    }

    @Override
    public void work() {

    }

}
