package com.denfop.item.base;

import com.denfop.IUCore;
import com.denfop.block.base.BlockElectric;
import com.denfop.tiles.base.TileEntityElectricBlock;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemElectricBlock extends ItemBlock {
    private final Block Block;

    public ItemElectricBlock(Block block) {
        super(block);
        this.Block = block;
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(1);
        setCreativeTab(IUCore.tabssp);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        switch (meta) {
            case 0:
                return "iu.blockMFE";
            case 1:
                return "iu.blockMFSU";
            case 2:
                return "iu.blockBatBox";
            case 3:
                return "iu.blockMFE1";
            case 4:
                return "iu.blockMFSU1";
            case 5:
                return "iu.blockCESU";
            case 6:
                return "iu.blockPerMFSU";
            case 7:
                return "iu.blockBarMFSU";
            case 8:
                return "iu.blockAdrMFSU";
            case 9:
                return "iu.blockGraMFSU";
            case 10:
                return "iu.blockKvrMFSU";
        }
        return null;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        if (this.Block instanceof BlockElectric)
            return ((BlockElectric) this.Block).getRarity(stack);
        return super.getRarity(stack);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {

        int meta = itemStack.getItemDamage();
        TileEntityElectricBlock tile = (TileEntityElectricBlock) BlockElectric.getBlockEntity(meta);
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Output") + " " + ModUtils.getString(tile.getOutput()) + " EU/t ");
        info.add(StatCollector.translateToLocal("iu.maxStoragestored") + " " + ModUtils.getString(tile.maxStorage) + " EU ");
        info.add(StatCollector.translateToLocal("iu.maxStoragestored") + " " + ModUtils.getString(tile.maxStorage2) + " RF ");
        NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(itemStack);
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " " + ModUtils.getString(nbttagcompound.getDouble("energy"))
                + " EU ");
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Capacity") + " " + ModUtils.getString(nbttagcompound.getDouble("energy2"))
                + " RF ");
        info.add(StatCollector.translateToLocal("iu.tier") + ModUtils.getString(tile.tier));

    }

    @Override
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        for (int i = 0; i < 11; i++) {
            ItemStack itemStack1 = new ItemStack(item, 1, i);
            NBTTagCompound nbttagcompound1 = NBTData.getOrCreateNbtData(itemStack1);
            nbttagcompound1.setDouble("energy", 0);
            nbttagcompound1.setDouble("energy2", 0);
            itemList.add(itemStack1);
            TileEntityElectricBlock tile = (TileEntityElectricBlock) BlockElectric.getBlockEntity(i);
            ItemStack itemStack = new ItemStack(item, 1, i);
            NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(itemStack);
            itemStack.setItemDamage(i);
            nbttagcompound.setDouble("energy", tile.maxStorage);
            nbttagcompound.setDouble("energy2", tile.maxStorage2);
            itemList.add(itemStack);
        }
    }

}
