package com.denfop.api.space.colonies;

public enum EnumHouses {
    LOW(20, 10, EnumHousesLevel.LOW),
    MEDIUM(35, 18, EnumHousesLevel.MEDIUM),
    HIGH(45, 25, EnumHousesLevel.HIGH),
    ;
    private final int max;
    private final int energy;
    private final EnumHousesLevel level;

    EnumHouses(int max, int energy, EnumHousesLevel level) {
        this.max = max;
        this.energy = energy;
        this.level = level;
    }

    public int getMax() {
        return max;
    }

    public EnumHousesLevel getLevel() {
        return level;
    }

    public int getEnergy() {
        return energy;
    }
}
