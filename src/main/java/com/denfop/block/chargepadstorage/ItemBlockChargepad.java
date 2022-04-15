package com.denfop.block.chargepadstorage;

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
import java.util.Objects;

public class ItemBlockChargepad extends ItemBlock {
    private final Block Block;

    public ItemBlockChargepad(Block block) {
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
                return "iu.blockChargepadMFE";
            case 1:
                return "iu.blockChargepadMFES";
            case 2:
                return "iu.blockChargepadBatBox";
            case 3:
                return "iu.blockChargepadCESU";
            case 4:
                return "iu.blockChargepadMFE1";
            case 5:
                return "iu.blockChargepadMFSU";
            case 6:
                return "iu.blockChargepadPerMFSU";
            case 7:
                return "iu.blockChargepadBarMFSU";
            case 8:
                return "iu.blockChargepadAdrMFSU";
            case 9:
                return "iu.blockChargepadGraMFSU";
            case 10:
                return "iu.blockChargepadKvrMFSU";
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
        TileEntityElectricBlock tile = (TileEntityElectricBlock) BlockChargepad.getBlockEntity(meta);
        info.add(StatCollector.translateToLocal("ic2.item.tooltip.Output") + " " + ModUtils.getString(Objects.requireNonNull(tile).getOutput()) + " EU/t ");
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
            TileEntityElectricBlock tile = (TileEntityElectricBlock) BlockChargepad.getBlockEntity(i);
            ItemStack itemStack = new ItemStack(item, 1, i);
            NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(itemStack);
            itemStack.setItemDamage(i);
            nbttagcompound.setDouble("energy", Objects.requireNonNull(tile).maxStorage);
            nbttagcompound.setDouble("energy2", tile.maxStorage2);
            itemList.add(itemStack);
        }
    }

}
