package com.denfop.block.base;

import com.denfop.IUCore;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockTransformer extends ItemBlock {


    public ItemBlockTransformer(Block block) {
        super(block);
        setHasSubtypes(true);
        setCreativeTab(IUCore.tabssp);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        return "Transformer" + BlockTransformer.name[meta];
    }


    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        for (int i = 0; i < BlockTransformer.name.length; i++) {
            ItemStack itemStack = new ItemStack(item, 1, i);
            itemList.add(itemStack);
        }
    }

}
