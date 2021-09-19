package com.denfop.block.ore;

import com.denfop.IUCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockBaseOre extends ItemBlock {


    public ItemBlockBaseOre(Block block) {
        super(block);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(IUCore.tabssp);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();


        return BlockBaseOre.getlist().get(meta);


    }


    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        for (int i = 0; i < BlockBaseOre.getlist().size(); i++) {
            if (i != 4 && i != 5 && i != 13) {
                ItemStack itemStack = new ItemStack(item, 1, i);
                itemList.add(itemStack);
            }
        }
    }

}
