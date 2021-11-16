package com.denfop.item.energy;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.api.IPurifierItem;
import com.denfop.tiles.base.TileEntitySolarPanel;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemPurifier extends Item implements IElectricItem, IPurifierItem {
    protected final double maxCharge;
    protected final IIcon[] textures = new IIcon[1];
    protected final double transferLimit;
    protected final int tier;
    private final String name;
    private int rarity;

    public ItemPurifier(String name, double maxCharge, double transferLimit, int tier) {
        super();
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.tier = tier;
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
        this.name = name;
        setUnlocalizedName(name);
        this.setRarity(1);
        setCreativeTab(IUCore.tabssp2);
        GameRegistry.registerItem(this, name);

    }

    public void setRarity(int aRarity) {
        this.rarity = aRarity;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.values()[this.rarity];
    }

    public String getTextureName() {

        return name;
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int i, int j, int k, int side, float a, float b, float c) {

        TileEntity tile = world.getTileEntity(i, j, k);
        if (!(tile instanceof TileEntitySolarPanel))
            return false;
        TileEntitySolarPanel base = (TileEntitySolarPanel) tile;
        double energy = 10000;
        if (base.time > 0)
            energy = (double) 10000 / (double) (base.time / 20);
        if (base.time1 > 0 && base.time <= 0)
            energy += (double) 10000 / (double) (base.time1 / 20);
        if (base.time2 > 0 && base.time <= 0 && base.time1 <= 0)
            energy += ((double) 10000 / (double) (base.time2 / 20)) + 10000;
        if (ElectricItem.manager.canUse(itemstack, energy)) {
            base.time = 28800;
            base.time1 = 14400;
            base.time2 = 14400;
            base.work = true;
            base.work1 = true;
            base.work2 = true;
            ElectricItem.manager.use(itemstack, 1000, entityplayer);
        }


        return true;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            par3List.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            par3List.add(StatCollector.translateToLocal("iu.purifiermode"));

        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {


        this.textures[0] = iconRegister.registerIcon(Constants.TEXTURES + ":" + getTextureName());
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {

        return this.textures[0];
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }


    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {


        return itemStack;
    }

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }


    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        ItemStack itemStack = new ItemStack(this, 1);
        if (getChargedItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, Double.POSITIVE_INFINITY, 2147483647, true, false);
            itemList.add(charged);

        }

        if (getEmptyItem(itemStack) == this) {
            ItemStack charged = new ItemStack(this, 1);
            ElectricItem.manager.charge(charged, 0.0D, 2147483647, true, false);
            itemList.add(charged);
        }
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack itemStack) {

        return this.tier;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return itemStack.getItem();
    }


}
