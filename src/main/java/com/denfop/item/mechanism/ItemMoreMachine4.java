package com.denfop.item.mechanism;

import com.denfop.Config;
import com.denfop.IUCore;
import com.denfop.block.mechanism.BlockMoreMachine4;
import com.denfop.tiles.mechanism.EnumMultiMachine;
import com.denfop.utils.ModUtils;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemMoreMachine4 extends ItemBlock {
    public ItemMoreMachine4(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return "iu.block" + BlockMoreMachine4.names[itemstack.getItemDamage()];
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.power") + " "
                + EnumMultiMachine.values()[itemStack.getItemDamage() + 37].usagePerTick + " EU/t, 32 EU/t "
                + StatCollector.translateToLocal("ic2.item.tooltip.max"));
        NBTTagCompound nbt = ModUtils.nbt(itemStack);
        if (nbt.getBoolean("rf")) {
            info.add(StatCollector.translateToLocal("ic2.item.tooltip.power") + " "
                    + EnumMultiMachine.values()[itemStack.getItemDamage() + 37].usagePerTick * Config.coefficientrf + " RF/t, " + 32 * Config.coefficientrf + "RF/t "
                    + StatCollector.translateToLocal("ic2.item.tooltip.max"));

        }
    }

    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 0; i < BlockMoreMachine4.names.length; i++)
            par3List.add(new ItemStack(par1, 1, i));
    }
}
