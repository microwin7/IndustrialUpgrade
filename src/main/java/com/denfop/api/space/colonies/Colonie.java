package com.denfop.api.space.colonies;

import com.denfop.api.space.IBody;
import com.denfop.api.space.SpaceNet;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class Colonie implements IColonie {

    private final IBody body;
    private final int level;
    private List<IColonieBuilding> list;

    public Colonie(IBody body) {
        this.level = 1;
        this.body = body;
        this.list = new ArrayList<>();
    }

    public Colonie(NBTTagCompound tag) {
        this.level = tag.getInteger("level");
        this.body = SpaceNet.instance.getBodyFromName(tag.getString("name"));

    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public boolean matched(final IBody body) {
        return this.body == body;
    }

    @Override
    public IBody getBody() {
        return this.body;
    }


    @Override
    public NBTTagCompound writeNBT(final NBTTagCompound tag) {
        tag.setString("name", this.body.getName());
        tag.setInteger("level", this.level);
        tag.setInteger("col", this.list.size());
        for (int i = 0; i < this.list.size(); i++) {
            tag.setTag("" + i, this.list.get(i).writeTag(new NBTTagCompound()));
        }
        return tag;
    }

    @Override
    public List<IColonieBuilding> getBuildingList() {
        return this.list;
    }

    @Override
    public void addBuilding(final IColonieBuilding building) {
        this.list.add(building);
    }

    @Override
    public List<IColonieBuilding> getListFromNBT(final NBTTagCompound tag) {
        return null;
    }


}
