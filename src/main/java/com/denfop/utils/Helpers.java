package com.denfop.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

import java.awt.*;

public class Helpers {

    public static Block getBlock(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ItemBlock)
            return ((ItemBlock) item).field_150939_a;
        return null;
    }

    public static boolean equals(Block block, ItemStack stack) {
        return (block == getBlock(stack));
    }

    public static String formatMessage(String inputString) {
        ChatComponentTranslation cht = new ChatComponentTranslation(inputString);
        return StatCollector.translateToLocal(cht.getUnformattedTextForChat());
    }

    public static int convertRGBcolorToInt(int r, int g, int b) {
        float divColor = 255.0F;
        Color tmpColor = new Color(r / divColor, g / divColor, b / divColor);
        return tmpColor.getRGB();
    }

}
