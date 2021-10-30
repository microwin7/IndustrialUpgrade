package com.denfop.integration.exnihilo.blocks;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.integration.exnihilo.ExNihiloIntegration;
import com.denfop.integration.exnihilo.items.itemblock.ItemBlockGravelBlocks;
import com.denfop.proxy.ClientProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class GravelBlocks extends Block {
    public static List<String> itemNames;
    private IIcon[][] IIconsList;

    public GravelBlocks() {
        super(Material.iron);
        itemNames = new ArrayList<>();
        this.setCreativeTab(IUCore.tabssp4);
        this.setHarvestLevel("pickaxe", 2);
        this.setHardness(2F);
        this.setStepSound(Block.soundTypeStone);
        this.addItemsNames();
        GameRegistry.registerBlock(this, ItemBlockGravelBlocks.class, "gravel_iu");

    }

    public static List<String> getlist() {
        return itemNames;
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs par2CreativeTabs, List itemList) {
        int var5 = getlist().size();

        for (int var6 = 0; var6 < var5; ++var6) {

            if (var6 != 6 && var6 != 7 && var6 != 11) {
                ItemStack stack = new ItemStack(item, 1, var6);
                itemList.add(stack);
            }
        }

    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(ExNihiloIntegration.gravel, 1, metadata));
            }
        }

        return ret;
    }

    public void addItemsNames() {
        for (int i = 0; i < IUItem.name_mineral1.size(); i++)
            itemNames.add(IUItem.name_mineral1.get(i) + "_gravelore");

    }

    @Override
    public IIcon getIcon(final int blockSide, final int blockMeta) {

        return this.IIconsList[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int blockSide) {
        int blockMeta = world.getBlockMetadata(x, y, z);
        return this.IIconsList[blockMeta][ClientProxy.sideAndFacingToSpriteOffset[blockSide][3]];

    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister par1IconRegister) {
        this.IIconsList = new IIcon[itemNames.size()][6];

        for (int i = 0; i < itemNames.size(); i++)
            for (int j = 0; j < 6; j++)
                this.IIconsList[i][j] = par1IconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }


}
