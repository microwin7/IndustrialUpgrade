package com.denfop.item.mechanism;

import com.denfop.IUCore;
import com.denfop.block.mechanism.BlockSolidMatter;
import com.denfop.tiles.solidmatter.EnumSolidMatter;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemSolidMatter extends ItemBlock {
    public ItemSolidMatter(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(IUCore.tabssp);
    }

    public static EnumSolidMatter getsolidmatter(int meta) {
        switch (meta) {
            case 0:
                return EnumSolidMatter.AER;
            case 1:
                return EnumSolidMatter.AQUA;
            case 2:
                return EnumSolidMatter.EARTH;
            case 3:
                return EnumSolidMatter.END;
            case 5:
                return EnumSolidMatter.NETHER;
            case 6:
                return EnumSolidMatter.NIGHT;
            case 7:
                return EnumSolidMatter.SUN;
            default:
                return EnumSolidMatter.MATTER;

        }

    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();

        return BlockSolidMatter.names[meta];
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < BlockSolidMatter.names.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
