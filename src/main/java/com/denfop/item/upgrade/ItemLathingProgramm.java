package com.denfop.item.upgrade;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class ItemLathingProgramm extends Item {
    public static final int[] drillmode = {5, 4, 3, 2, 1};
    public static final int[] handlemode = {4, 3, 2, 2, 2};
    public static final int[] axe = {4, 3, 4, 3, 4};

    public ItemLathingProgramm(String name) {
        super();
        this.setCreativeTab(IUCore.tabssp1);
        setUnlocalizedName(name);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        int[] state = getCurrentState(itemStack);
        if (Keyboard.isKeyDown(42)) {
            int max = 5;
            StringBuilder sb = new StringBuilder();
            if (getModeDrill(state))
                info.add(StatCollector.translateToLocal("ic2.itemTurningBlanks.tooltip.54321"));
            if (getModeHandlemode(state))
                info.add(StatCollector.translateToLocal("ic2.itemTurningBlanks.tooltip.43222"));
            if (getModeAxe(state))
                info.add(StatCollector.translateToLocal("ic2.itemTurningBlanks.tooltip.43434"));

            for (int j = 0; j < 5; ++j) {
                sb.append(StatCollector.translateToLocalFormatted("ic2.Lathe.gui.info", state[j], max));
                sb.append("   ");
            }

            info.add(sb.toString());
        }
    }

    private boolean getModeDrill(int[] state) {
        for (int i = 0; i < 5; i++) {
            if (!(state[i] == drillmode[i]))
                return false;

        }
        return true;
    }

    private boolean getModeHandlemode(int[] state) {
        for (int i = 0; i < 5; i++) {
            if (!(state[i] == handlemode[i]))
                return false;

        }
        return true;
    }

    private boolean getModeAxe(int[] state) {
        for (int i = 0; i < 5; i++) {
            if (!(state[i] == axe[i]))
                return false;

        }
        return true;
    }

    public int[] getCurrentState(ItemStack stack) {
        if (stack == null) {
            return new int[0];
        } else {
            int[] ret = new int[5];
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound();

                for (int i = 0; i < ret.length; ++i) {
                    if (tag.hasKey("l" + i)) {
                        ret[i] = tag.getInteger("l" + i);
                    } else {
                        ret[i] = 5;
                    }
                }
            } else {
                Arrays.fill(ret, 5);
            }

            return ret;
        }
    }

}