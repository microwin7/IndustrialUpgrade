package com.denfop.item.mechanism;

import com.denfop.IUCore;
import com.denfop.block.mechanism.BlockImpReactorChamber;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemImpChamber extends ItemBlock {
    public ItemImpChamber(Block block) {
        super(block);
        setMaxDamage(0);
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
        return "iu.block" + BlockImpReactorChamber.names[meta];


    }


    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < BlockImpReactorChamber.names.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
