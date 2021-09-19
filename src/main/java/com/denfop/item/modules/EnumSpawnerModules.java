package com.denfop.item.modules;

import com.denfop.IUItem;

public enum EnumSpawnerModules {
    LUCKY_I(EnumSpawnerType.LUCKY, 0, 1),
    LUCKY_II(EnumSpawnerType.LUCKY, 1, 2),
    LUCKY_III(EnumSpawnerType.LUCKY, 2, 3),
    SPEED(EnumSpawnerType.SPEED, 3, 20),
    EXPERIENCE_I(EnumSpawnerType.EXPERIENCE, 4, 25),
    EXPERIENCE_II(EnumSpawnerType.EXPERIENCE, 5, 50),
    SPAWN(EnumSpawnerType.SPAWN, 6, 2),
    SPAWN_I(EnumSpawnerType.SPAWN, 7, 3);


    public final EnumSpawnerType type;
    public final int meta;
    public final int percent;

    EnumSpawnerModules(EnumSpawnerType type, int meta, int percent) {
        this.type = type;
        this.meta = meta;
        this.percent = percent;
    }

    public static void register() {
        for (EnumSpawnerModules value : values()) {
            IUItem.map4.put(value.meta, value);
        }
    }
}
