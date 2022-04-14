package com.denfop.api.space.colonies;

import net.minecraft.nbt.NBTTagCompound;

public class Building implements IColonieBuilding {

    private final String name;

    public Building(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public NBTTagCompound writeTag(final NBTTagCompound tag) {
        tag.setString("name", this.name);
        return tag;
    }

    @Override
    public void work() {

    }


}
