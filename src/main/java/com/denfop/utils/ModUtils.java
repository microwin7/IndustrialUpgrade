package com.denfop.utils;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import cpw.mods.fml.relauncher.FMLRelaunchLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.apache.logging.log4j.Level;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;

public class ModUtils {
    private static final EnumChatFormatting[] fabulousness;

    static {
        fabulousness = new EnumChatFormatting[]{EnumChatFormatting.RED, EnumChatFormatting.GOLD, EnumChatFormatting.YELLOW, EnumChatFormatting.GREEN, EnumChatFormatting.AQUA, EnumChatFormatting.BLUE, EnumChatFormatting.LIGHT_PURPLE};
    }

    public static void add_panel(double genday, double gennight, double storage, double producing, String unlocalization_name, int tier, ResourceLocation res, boolean rendertype) {
        List list = new ArrayList();
        list.add(genday);
        list.add(gennight);
        list.add(storage);
        list.add(producing);
        list.add(tier);
        list.add(res);
        list.add(rendertype);
        IUItem.panel_list.put(unlocalization_name, list);
    }

    public static void mode(ItemStack stack, List<String> list) {
        NBTTagCompound nbt = nbt(stack);
        String mode = nbt.getString("mode");
        if (mode.isEmpty())
            list.add(StatCollector.translateToLocal("defaultskin"));
        switch (mode) {
            case "Zelen":
                list.add(StatCollector.translateToLocal("camouflageskin"));
                break;
            case "Demon":
                list.add(StatCollector.translateToLocal("demonskin"));
                break;
            case "Dark":
                list.add(StatCollector.translateToLocal("Darkskin"));
                break;
            case "Cold":
                list.add(StatCollector.translateToLocal("Coldskin"));
                break;
            case "Ender":
                list.add(StatCollector.translateToLocal("Enderskin"));
                break;
        }
    }

    public static IIcon mode(ItemStack stack, IIcon[] icon) {
        NBTTagCompound nbt = nbt(stack);
        String mode = nbt.getString("mode");
        if (mode.isEmpty())
            return icon[0];
        else {
            switch (mode) {
                case "Zelen":
                    return icon[1];
                case "Demon":
                    return icon[2];
                case "Dark":
                    return icon[3];
                case "Cold":
                    return icon[4];
                case "Ender":
                    return icon[5];
                default:
                    return icon[0];
            }
        }
    }

    public static List<Double> Time(double time) {
        List<Double> list = new ArrayList<>();

        double temp = 0;

        if (time / 3600 >= 1) {
            temp = (time / (double) 3600);
        }
        temp = Math.floor(temp);
        list.add(Math.floor(temp));
        double temp1 = 0;


        if (((time - temp * 3600) / 60) >= 1) {
            temp1 = ((time - temp * 3600) / 60);
        }
        temp1 = Math.floor(temp1);
        list.add(Math.floor(temp1));
        double temp2;

        temp2 = (time - (temp * 3600 + temp1 * 60));

        list.add(Math.floor(temp2));
        return list;
    }

    public static String getString(float number) {
        float g = number;
        float gg;
        int i = 0;
        for (; g >= 10; i++) {
            g = g / 10;

        }
        String maxstorage_2 = "0";
        if (i >= 0 && i < 3 && number <= 1000) {

            gg = number;
            maxstorage_2 = String.format("%.0f", gg);
        } else if (i >= 3 && i < 6 && number >= 1000 && number < 1000000) {
            gg = number / (1000);
            maxstorage_2 = String.format("%.2fK", gg);
        } else if (i >= 6 && i < 9 && number >= 1000000 && number < 1000000000) {
            gg = number / (1000000);
            maxstorage_2 = String.format("%.2fM", gg);
        } else if (i >= 9 && i < 12 && number >= 1000000000 && number < 2100000000) {
            gg = number / (1000000000);
            maxstorage_2 = String.format("%.2fG", gg);
        }
        return maxstorage_2;

    }

    public static String getString1(double number) {
        String maxstorage_2 = "0";
        if (number <= 1000) {

            maxstorage_2 = String.format("%.2f", number);
        } else if (number >= 10E2D && number < 10E5D) {

            maxstorage_2 = String.format("%.0fK", number / 10E2D);
        }
        return maxstorage_2;
    }

    public static String getString(double number) {
        String maxstorage_2 = "0";
        if (number <= 1000) {

            maxstorage_2 = String.format("%.0f", number);
        } else if (number >= 10E2D && number < 10E5D) {

            maxstorage_2 = String.format("%.2fK", number / 10E2D);
        } else if (number >= 10E5D && number < 10E8D) {

            maxstorage_2 = String.format("%.2fM", number / 10E5D);
        } else if (number >= 10E8D && number < 10E11D) {

            maxstorage_2 = String.format("%.2fG", number / 10E8D);
        } else if (number >= 10E11D && number < 10E14D) {

            maxstorage_2 = String.format("%.2fT", number / 10E11D);
        } else if (number >= 10E14D && number < 10E17D) {

            maxstorage_2 = String.format("%.2fP", number / 10E14D);
        } else if (number >= 10E17D && number < 10E20D) {

            maxstorage_2 = String.format("%.2fE", number / 10E17D);
        } else if (number >= 10E20D && number < 10E23D) {

            maxstorage_2 = String.format("%.2fZ", number / 10E20D);
        } else if (number >= 10E23D && number < 10E26D) {

            maxstorage_2 = String.format("%.2fY", number / 10E23D);
        }
        return maxstorage_2;

    }

    public static String Boolean(boolean boolean1) {
        if (boolean1) {
            return StatCollector.translateToLocal("iu.yes");
        } else {
            return StatCollector.translateToLocal("iu.no");
        }

    }

    public static boolean Boolean(List<Boolean> boolean1) {

        for (Boolean aBoolean : boolean1) {
            if (aBoolean)
                return true;
        }

        return false;
    }

    public static int getsum1(List<Integer> sum) {
        int sum_sum = 0;
        for (Integer aDouble : sum) {
            sum_sum += aDouble;
        }
        return sum_sum;
    }

    public static NBTTagCompound nbt(ItemStack stack) {
        if (stack == null)
            return null;
        NBTTagCompound NBTTagCompound = stack.getTagCompound();
        if (NBTTagCompound == null)
            NBTTagCompound = new NBTTagCompound();
        stack.setTagCompound(NBTTagCompound);
        return NBTTagCompound;
    }

    public static NBTTagCompound nbt() {

        return new NBTTagCompound();
    }

    public static void SetDoubleWithoutItem(NBTTagCompound NBTTagCompound, String name, double amount) {
        if (NBTTagCompound == null)
            NBTTagCompound = new NBTTagCompound();
        NBTTagCompound.setDouble(name, amount);

    }

    public static void NBTSetString(ItemStack stack, String name, String string) {
        if (string == null)
            return;
        NBTTagCompound NBTTagCompound = stack.getTagCompound();
        if (NBTTagCompound == null)
            NBTTagCompound = new NBTTagCompound();
        NBTTagCompound.setString(name, string);
        stack.setTagCompound(NBTTagCompound);
    }

    public static void NBTSetInteger(ItemStack stack, String name, int string) {
        if (name == null)
            return;
        NBTTagCompound NBTTagCompound = stack.getTagCompound();
        if (NBTTagCompound == null)
            NBTTagCompound = new NBTTagCompound();
        NBTTagCompound.setInteger(name, string);
        stack.setTagCompound(NBTTagCompound);
    }

    public static String NBTGetString(ItemStack stack, String name) {
        if (name == null)
            return "";
        if (stack == null)
            return "";
        NBTTagCompound NBTTagCompound = nbt(stack);

        return NBTTagCompound.getString(name);

    }

    public static double NBTGetDouble(ItemStack stack, String name) {
        if (name == null)
            return 0;
        NBTTagCompound NBTTagCompound = stack.getTagCompound();
        if (NBTTagCompound == null)
            return 0;
        return NBTTagCompound.getDouble(name);

    }

    public static int NBTGetInteger(ItemStack stack, String name) {
        if (name == null)
            return 0;
        NBTTagCompound NBTTagCompound = stack.getTagCompound();
        if (NBTTagCompound == null)
            return 0;

        return NBTTagCompound.getInteger(name);
    }

    public static void info(String message) {
        FMLRelaunchLog.log(Constants.MOD_NAME, Level.INFO, message);
    }

    public static List<Block> blacklist_block() {
        List<Block> list = new ArrayList<>();
        list.add(Blocks.stone);
        list.add(Blocks.dirt);
        list.add(Blocks.netherrack);
        list.add(Blocks.end_stone);
        return list;
    }

    public static boolean getore(Block localBlock) {
        for (Block itemstack : blacklist_block()) {
            if (localBlock == itemstack)
                return false;
        }
        return true;
    }

    public static boolean getore(Item localBlock) {
        for (Block itemstack : blacklist_block()) {
            if (Block.getBlockFromItem(localBlock) == itemstack)
                return false;
        }
        return true;
    }

    public static boolean getore(Block stack, Block localBlock) {
        for (Block itemstack : blacklist_block()) {
            if (localBlock == itemstack)
                return false;
        }
        if (stack != localBlock)
            return false;
        for (ItemStack itemstack : IUCore.get_ore)
            if (stack == Block.getBlockFromItem(itemstack.getItem()))
                return true;
        return stack.getUnlocalizedName().equals("tile.oreRedstone");

    }

    public static int slot(List<Integer> list) {
        int meta = 0;
        for (Integer integer : list) {

            if (integer != 0)
                meta = integer;

        }
        return meta;
    }

    //TODO: code of avaritia
    @SideOnly(Side.CLIENT)
    public static String makeFabulous(String input) {
        return ludicrousFormatting(input, fabulousness, 80.0D, 1);
    }

    @SideOnly(Side.CLIENT)
    public static String ludicrousFormatting(String input, EnumChatFormatting[] colours, double delay, int posstep) {
        StringBuilder sb = new StringBuilder(input.length() * 3);
        if (delay <= 0.0D) {
            delay = 0.001D;
        }

        int offset = (int) Math.floor((double) (Sys.getTime() * 1000L / Sys.getTimerResolution()) / delay) % colours.length;

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            int col = (i * posstep + colours.length - offset) % colours.length;
            sb.append(colours[col].toString());
            sb.append(c);
        }

        return sb.toString();
    }
    // TODO: end code of Avaritia
}
