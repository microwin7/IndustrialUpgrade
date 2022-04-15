package com.denfop.item.upgrade;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.block.base.BlockElectric;
import com.denfop.block.chargepadstorage.BlockChargepad;
import com.denfop.tiles.base.TileEntityElectricBlock;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemUpgradeKit extends Item {
    private final List<String> itemNames;
    private final int[] names = {5, 3, 4, 0, 1, 6, 7, 8, 9};
    private final int[] names1 = {3, 4, 0, 1, 6, 7, 8, 9, 10};

    private final int[] chargenames = {3, 4, 5, 0, 1, 6, 7, 8, 9};
    private final int[] chargenames1 = {4, 5, 0, 1, 6, 7, 8, 9, 10};
    private IIcon[] IIconsList;

    public ItemUpgradeKit() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "upgradekitIU");
    }


    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        if (!IC2.platform.isSimulating()) {
            return false;
        } else {
            int meta = stack.getItemDamage();
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            Block block = world.getBlock(x, y, z);

            double eustored;
            double eustored1;
            short facing;
            ItemStack[] items;
            int i;

            if (block instanceof BlockElectric) {
                TileEntityElectricBlock tile = (TileEntityElectricBlock) tileEntity;
                if (world.getBlockMetadata(x, y, z) == names[meta]) {
                    eustored = tile.energy;
                    eustored1 = tile.energy2;
                    facing = tile.getFacing();
                    items = new ItemStack[tile.getSizeInventory()];

                    for (i = 0; i < items.length; ++i) {
                        items[i] = tile.getStackInSlot(i);
                    }

                    world.setBlock(x, y, z, ((ItemBlock) new ItemStack(IUItem.electricblock, 1, names1[meta]).getItem()).field_150939_a, names1[meta], 2);
                    tileEntity = world.getTileEntity(x, y, z);
                    block = world.getBlock(x, y, z);
                    if (block instanceof BlockElectric && world.getBlockMetadata(x, y, z) == names1[meta]) {
                        tile = (TileEntityElectricBlock) tileEntity;
                        tile.setFacing(facing);
                        tile.energy = eustored;
                        tile.energy2 = eustored1;


                        for (i = 0; i < items.length; ++i) {
                            tile.setInventorySlotContents(i, items[i]);
                        }

                        tile.markDirty();
                        --stack.stackSize;


                        return true;
                    }
                }


            }
            if (block instanceof BlockChargepad) {
                TileEntityElectricBlock tile = (TileEntityElectricBlock) tileEntity;
                if (world.getBlockMetadata(x, y, z) == chargenames[meta]) {
                    eustored = tile.energy;
                    eustored1 = tile.energy2;
                    facing = tile.getFacing();
                    items = new ItemStack[tile.getSizeInventory()];

                    for (i = 0; i < items.length; ++i) {
                        items[i] = tile.getStackInSlot(i);
                    }

                    world.setBlock(x, y, z, ((ItemBlock) new ItemStack(IUItem.Chargepadelectricblock, 1, chargenames1[meta]).getItem()).field_150939_a, chargenames1[meta], 2);
                    tileEntity = world.getTileEntity(x, y, z);
                    block = world.getBlock(x, y, z);
                    if (block instanceof BlockElectric && world.getBlockMetadata(x, y, z) == chargenames1[meta]) {
                        tile = (TileEntityElectricBlock) tileEntity;
                        tile.setFacing(facing);
                        tile.energy = eustored;
                        tile.energy2 = eustored1;


                        for (i = 0; i < items.length; ++i) {
                            tile.setInventorySlotContents(i, items[i]);
                        }

                        tile.markDirty();
                        --stack.stackSize;


                        return true;
                    }
                }


            }


        }
        return false;
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("upgradekit");
        this.itemNames.add("upgradekit1");
        this.itemNames.add("upgradekit2");
        this.itemNames.add("upgradekit3");
        this.itemNames.add("upgradekit4");
        this.itemNames.add("upgradekit5");
        this.itemNames.add("upgradekit6");
        this.itemNames.add("upgradekit7");
        this.itemNames.add("upgradekit8");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

}
