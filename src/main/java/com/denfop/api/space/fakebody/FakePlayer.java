package com.denfop.api.space.fakebody;

import net.minecraft.nbt.NBTTagCompound;

public class FakePlayer implements IFakePlayer {

    private final String name;
    private final NBTTagCompound tag;

    public FakePlayer(String name, NBTTagCompound tag) {
        this.name = name;
        this.tag = tag;
    }

    @Override
    public NBTTagCompound getTag() {
        return this.tag;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean matched(final String name) {
        return this.name.equals(name);
    }

}
