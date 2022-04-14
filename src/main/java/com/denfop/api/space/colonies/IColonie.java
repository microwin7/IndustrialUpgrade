package com.denfop.api.space.colonies;

import com.denfop.api.space.IBody;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public interface IColonie {

    int getLevel();

    boolean matched(IBody body);

    IBody getBody();


    NBTTagCompound writeNBT(NBTTagCompound tag);

    List<IColonieBuilding> getBuildingList();

    void addBuilding(IColonieBuilding building);

    List<IColonieBuilding> getListFromNBT(NBTTagCompound tag);

}
