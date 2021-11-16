package com.denfop.item.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.block.cable.BlockCable;
import com.denfop.tiles.base.TileEntityCable;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IBoxable;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemCable extends Item implements IBoxable {
    private final List<String> itemNames;
    private final IIcon[] IIconsList;

    public ItemCable() {
        super();
        setHasSubtypes(true);

        this.itemNames = new ArrayList<>();
        this.IIconsList = new IIcon[11];
        this.setCreativeTab(IUCore.tabssp);
        this.addItemsNames();
    }

    public static Block getBlock(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof ItemBlock)
            return ((ItemBlock) item).field_150939_a;
        return null;
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("iu.itemCable");
        this.itemNames.add("iu.itemCableO");
        this.itemNames.add("iu.itemGoldCable");
        this.itemNames.add("iu.itemGoldCableI");
        this.itemNames.add("iu.itemGoldCableII");
        this.itemNames.add("iu.itemIronCable");
        this.itemNames.add("iu.itemIronCableI");
        this.itemNames.add("iu.itemIronCableII");
        this.itemNames.add("iu.itemIronCableIIII");
        this.itemNames.add("iu.itemGlassCable");
        this.itemNames.add("iu.itemGlassCableI");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList[0] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemCable");
        this.IIconsList[1] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemCableO");
        this.IIconsList[2] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemGoldCable");
        this.IIconsList[3] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemGoldCableI");
        this.IIconsList[4] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemGoldCableII");
        this.IIconsList[5] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemIronCable");
        this.IIconsList[6] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemIronCableI");
        this.IIconsList[7] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemIronCableII");
        this.IIconsList[8] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemIronCableIIII");
        this.IIconsList[9] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemGlassCable");
        this.IIconsList[10] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "itemGlassCableI");

    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        double capacity = TileEntityCable.getMaxCapacity(itemStack.getItemDamage());
        info.add(ModUtils.getString(capacity) + " EU/t");
    }

    public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
                             float a, float b, float c) {
        Block oldBlock = world.getBlock(x, y, z);
        if (!oldBlock.isAir(world, x, y, z))
            if (oldBlock == Blocks.snow_layer) {
                side = 1;
            } else if (oldBlock != Blocks.vine && oldBlock != Blocks.tallgrass && oldBlock != Blocks.deadbush &&

                    !oldBlock.isReplaceable(world, x, y, z)) {
                switch (side) {
                    case 0:
                        y--;
                        break;
                    case 1:
                        y++;
                        break;
                    case 2:
                        z--;
                        break;
                    case 3:
                        z++;
                        break;
                    case 4:
                        x--;
                        break;
                    case 5:
                        x++;
                        break;
                }
            }
        BlockCable block = (BlockCable) getBlock(new ItemStack(IUItem.cableblock));
        if ((oldBlock.isAir(world, x, y, z)
                || world.canPlaceEntityOnSide(block, x, y, z, false, side, entityplayer, itemstack))
                && world.checkNoEntityCollision(
                block.getCollisionBoundingBoxFromPool(x, y, z, itemstack.getItemDamage()))
                && world.setBlock(x, y, z, block, itemstack.getItemDamage(), 3)) {
            block.onPostBlockPlaced(world, x, y, z, side);
            block.onBlockPlacedBy(world, x, y, z, entityplayer, itemstack);
            if (!entityplayer.capabilities.isCreativeMode)
                itemstack.stackSize--;
            return true;
        }
        return false;
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

    public boolean canBeStoredInToolbox(ItemStack itemstack) {
        return true;
    }
}
