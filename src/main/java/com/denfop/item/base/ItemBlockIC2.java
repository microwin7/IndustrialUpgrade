package com.denfop.item.base;

import com.denfop.IUCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.Ic2Items;
import ic2.core.util.StackUtil;
import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockIC2 extends ItemBlock {

    public ItemBlockIC2(Block block) {
        super(block);
        setCreativeTab(IUCore.tabssp);
    }

    public String getUnlocalizedName() {
        return "iu." + super.getUnlocalizedName();
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack stack) {
        return StatCollector.translateToLocal(getUnlocalizedName(stack));
    }

    public float getDigSpeed(ItemStack stack, Block par2Block, int meta) {
        return canHarvestBlock(par2Block, stack) ? 1.01F : 1.0F;
    }

    public boolean canHarvestBlock(Block aBlock, ItemStack stack) {
        return (StackUtil.equals(aBlock, Ic2Items.scaffold) || StackUtil.equals(aBlock, Ic2Items.ironScaffold));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {

        return super.getRarity(stack);
    }
}
