package com.denfop.utils;

import com.denfop.Config;

public enum EnumInfoUpgradeModules {
    AOE_DIG(Config.upgrades[0], "AOE_dig"),
    DIG_DEPTH(Config.upgrades[1], "dig_depth"),
    ENERGY(Config.upgrades[2], "energy"),
    EFFICIENCY(Config.upgrades[3], "speed"),
    GENDAY(Config.upgrades[4], "genday"),
    GENNIGHT(Config.upgrades[5], "gennight"),
    STORAGE(Config.upgrades[6], "storage"),
    PROTECTION(Config.upgrades[7], "protect"),
    FLYSPEED(Config.upgrades[8], "flyspeed"),
    BOWENERGY(Config.upgrades[9], "bowenergy"),
    BOWDAMAGE(Config.upgrades[10], "bowdamage"),
    SABERENERGY(Config.upgrades[11], "saberenergy"),
    FIRE_PROTECTION(Config.upgrades[12], "fireResistance"),
    JUMP(Config.upgrades[13], "jump"),
    WATER(Config.upgrades[14], "waterBreathing"),
    SPEED(Config.upgrades[15], "moveSpeed"),
    SABER_DAMAGE(Config.upgrades[16], "saberdamage"),
    VAMPIRES(Config.upgrades[17], "vampires"),
    RESISTANCE(Config.upgrades[18], "resistance"),
    POISON(Config.upgrades[19], "poison"),
    WITHER(Config.upgrades[20], "wither"),
    SILK_TOUCH(Config.upgrades[21], "silk"),
    INVISIBILITY(Config.upgrades[22], "invisibility"),
    LOOT(Config.upgrades[23], "loot"),
    FIRE(Config.upgrades[24], "fire"),
    REPAIRED(Config.upgrades[25], "repaired"),
    ;
    public final int max;
    public final String name;

    EnumInfoUpgradeModules(int max, String name) {
        this.max = max;
        this.name = name;
    }
}
