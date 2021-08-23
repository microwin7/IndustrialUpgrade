package com.denfop.utils;

public enum EnumInfoUpgradeModules {
        AOE_DIG(2,"AOE_dig"),
        DIG_DEPTH(2,"dig_depth"),
        ENERGY(2,"energy"),
        EFFICIENCY(2,"speed"),
        GENDAY(2,"genday"),
    GENNIGHT(2,"gennight"),
    STORAGE(2,"storage"),
    PROTECTION(4,"protect"),
    FLYSPEED(2,"flyspeed"),
    BOWENERGY(2,"bowenergy"),
    BOWDAMAGE(2,"bowdamage"),
    SABERENERGY(2,"saberenergy"),
    FIREPROTECTION(1,"fireResistance"),
    JUMP(1,"jump"),
    WATER(1,"waterBreathing"),
    SPEED(1,"moveSpeed"),
    SABERDAMAGE(2,"saberdamage")
    ;
    public final int max;
    public final String name;
    EnumInfoUpgradeModules(int max,String name){
        this.max = max;
        this.name = name;
    }
}
