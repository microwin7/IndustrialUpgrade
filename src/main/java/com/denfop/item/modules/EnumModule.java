package com.denfop.item.modules;

import com.denfop.Config;
import com.denfop.IUItem;
import net.minecraft.item.Item;

public enum EnumModule {
    DAY(IUItem.module1, EnumType.DAY, Config.percent_day, "iu.module1"),
    DAY_I(IUItem.genmodule, EnumType.DAY, Config.percent_day * 2, "iu.module1"),
    DAY_II(IUItem.genmodule1, EnumType.DAY, Config.percent_day * 3, "iu.module1"),
    NIGHT(IUItem.module2, EnumType.NIGHT, Config.percent_night, "iu.module2"),
    NIGHT_I(IUItem.gennightmodule, EnumType.NIGHT, Config.percent_night * 2, "iu.module2"),
    NIGHT_II(IUItem.gennightmodule1, EnumType.NIGHT, Config.percent_night * 3, "iu.module2"),
    STORAGE(IUItem.module3, EnumType.STORAGE, Config.percent_storage, "iu.module3"),
    STORAGE_I(IUItem.storagemodule, EnumType.STORAGE, Config.percent_storage * 2, "iu.module3"),
    STORAGE_II(IUItem.storagemodule1, EnumType.STORAGE, Config.percent_storage * 3, "iu.module3"),
    OUTPUT(IUItem.module4, EnumType.OUTPUT, Config.percent_output, "iu.module4"),
    OUTPUT_I(IUItem.outputmodule, EnumType.OUTPUT, Config.percent_output * 2, "iu.module4"),
    OUTPUT_II(IUItem.outputmodule1, EnumType.OUTPUT, Config.percent_output * 3, "iu.module4"),
    PHASE(IUItem.phase_module, EnumType.PHASE, 30, "iu.module5"),
    PHASE_I(IUItem.phase_module1, EnumType.PHASE, 65, "iu.module5"),
    PHASE_II(IUItem.phase_module2, EnumType.PHASE, 100, "iu.module5"),
    MOON_LINSE(IUItem.moonlinse_module, EnumType.MOON_LINSE, 130, "iu.module6"),
    MOON_LINSE_I(IUItem.moonlinse_module1, EnumType.MOON_LINSE, 165, "iu.module6"),
    MOON_LINSE_II(IUItem.moonlinse_module2, EnumType.MOON_LINSE, 200, "iu.module6"),
    ;
    public final Item item;
    public final EnumType type;
    public final double percent_description;
    public final String description;
    public final double percent;

    EnumModule(Item item, EnumType type, double percent, String description) {
        this.item = item;
        this.type = type;
        this.percent_description = percent;
        this.percent = percent / 100;
        this.description = description;
    }

    public static void register() {
        for (EnumModule value : values()) {
            IUItem.modules.put(value.item, value);
        }
    }
}
