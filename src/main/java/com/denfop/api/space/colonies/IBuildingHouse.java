package com.denfop.api.space.colonies;

public interface IBuildingHouse extends IColonieBuilding {

    EnumHousesLevel getLevel();

    int getPeoples();

    void setPeoples(int peoples);

    int getEnergy();

    int getMaxPeoples();

    void addPeoples(int peoples);

}
