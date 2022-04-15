package com.denfop.api.upgrade;

import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class UpgradeItemInform {

    public final EnumInfoUpgradeModules upgrade;
    public final int number;

    public UpgradeItemInform(EnumInfoUpgradeModules modules, int number) {
        this.upgrade = modules;
        this.number = number;

    }

    public boolean matched(EnumInfoUpgradeModules modules) {
        return this.upgrade.name.equals(modules.name);
    }

    public int getInformation(EnumInfoUpgradeModules modules) {
        if (this.upgrade.name.equals(modules.name)) {
            return this.number;
        }
        return 0;
    }

    public String getName() {
        switch (this.upgrade) {
            case GENDAY:
                return EnumChatFormatting.YELLOW + StatCollector.translateToLocal("genday") + EnumChatFormatting.GREEN + ModUtils.getString((0.05 * this.number) * 100) + "%";
            case GENNIGHT:
                return EnumChatFormatting.AQUA + StatCollector.translateToLocal("gennight") + EnumChatFormatting.GREEN + ModUtils.getString(0.05 * this.number * 100) + "%";
            case PROTECTION:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("protect") + EnumChatFormatting.GREEN + ModUtils.getString(0.2 * this.number * 100) + "%";
            case EFFICIENCY:
                return EnumChatFormatting.LIGHT_PURPLE + StatCollector.translateToLocal("speed") + EnumChatFormatting.GREEN + ModUtils.getString(
                        0.2 * this.number * 100) + "%";
            case BOWENERGY:
                return EnumChatFormatting.RED + StatCollector.translateToLocal("bowenergy") + EnumChatFormatting.GREEN + ModUtils.getString(0.1 *
                        this.number * 100) + "%";
            case SABERENERGY:
                return EnumChatFormatting.RED + StatCollector.translateToLocal("saberenergy") + EnumChatFormatting.GREEN + ModUtils.getString(0.15 * this.number * 100) + "%";
            case DIG_DEPTH:
                return EnumChatFormatting.AQUA + StatCollector.translateToLocal("depth") + EnumChatFormatting.GREEN + this.number;
            case FIRE_PROTECTION:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("fireResistance");
            case WATER:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("waterBreathing");
            case SPEED:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("moveSpeed");
            case JUMP:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("jump");
            case BOWDAMAGE:
                return EnumChatFormatting.DARK_GREEN + StatCollector.translateToLocal("bowdamage") + EnumChatFormatting.GREEN + ModUtils.getString(
                        (0.25 * this.number) * 100) + "%";
            case SABER_DAMAGE:
                return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("saberdamage") + EnumChatFormatting.GREEN + ModUtils.getString(
                        0.15 * this.number * 100) + "%";
            case AOE_DIG:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("aoe") + EnumChatFormatting.GREEN + this.number;
            case FLYSPEED:
                return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("speedfly") + EnumChatFormatting.GREEN + ModUtils.getString(
                        ((0.1 * this.number) / 0.2) * 100) + "%";
            case STORAGE:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("storage") + EnumChatFormatting.GREEN + ModUtils.getString(0.05 * this.number
                        * 100) + "%";
            case ENERGY:
                return EnumChatFormatting.RED + StatCollector.translateToLocal("energy_less_use") + EnumChatFormatting.GREEN + ModUtils.getString(
                        0.25 * this.number * 100) + "%";
            case VAMPIRES:
                return EnumChatFormatting.RED + StatCollector.translateToLocal("vampires") + EnumChatFormatting.GREEN + ModUtils.getString(this.number);
            case RESISTANCE:
                return EnumChatFormatting.GOLD + StatCollector.translateToLocal("resistance") + EnumChatFormatting.GREEN + ModUtils.getString(this.number);
            case POISON:
                return EnumChatFormatting.GREEN + StatCollector.translateToLocal("poison");
            case WITHER:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("wither");
            case SILK_TOUCH:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("silk");
            case INVISIBILITY:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("invisibility");
            case LOOT:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("loot") + EnumChatFormatting.GREEN + ModUtils.getString(this.number);
            case FIRE:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("fire") + EnumChatFormatting.GREEN + ModUtils.getString(this.number);
            case REPAIRED:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("repaired") + EnumChatFormatting.GREEN + 0.001 * this.number + "%";
            case LUCKY:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("lucky") + EnumChatFormatting.GREEN + this.number;
            case EFFICIENT:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("efficient") + EnumChatFormatting.GREEN + (1 + (this.number - 1) * 2);
            case SMELTER:
                return EnumChatFormatting.GRAY + StatCollector.translateToLocal("iu.smelter");
            case NIGTHVISION:
                return EnumChatFormatting.BLUE + StatCollector.translateToLocal("iu.nightvision");
            case THORNS:
                return EnumChatFormatting.GRAY + StatCollector.translateToLocal("iu.thorns") + EnumChatFormatting.GREEN + this.number;
            case EXPERIENCE:
                return EnumChatFormatting.GREEN + StatCollector.translateToLocal("iu.experience") + EnumChatFormatting.GOLD + this.number * 50 + "%";
            case BLINDNESS:
                return EnumChatFormatting.GRAY + StatCollector.translateToLocal("iu.blindness");
            case PROTECTION_ARROW:
                return EnumChatFormatting.RED + StatCollector.translateToLocal("iu.protection_arrow") + EnumChatFormatting.DARK_PURPLE + (1 + (this.number - 1) * 2);
            case FALLING_DAMAGE:
                return EnumChatFormatting.WHITE + StatCollector.translateToLocal("iu.falling_damage") + EnumChatFormatting.DARK_GREEN + this.number * 25 + "%";
            case MACERATOR:
                return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("iu.macerator");
            case COMB_MACERATOR:
                return EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("iu.comb_macerator");
            case RANDOM:
                return EnumChatFormatting.GREEN + StatCollector.translateToLocal("iu.random") + 0.001 * this.number + "%";
            case HUNGRY:
                return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("iu.hungry");
            case GENERATOR:
                return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal("iu.generator");


        }
        return "";
    }


}
