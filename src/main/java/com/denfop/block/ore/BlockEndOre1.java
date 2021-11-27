package com.denfop.block.ore;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.proxy.ClientProxy;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockEndOre1 extends Block {
    public static List<String> itemNames;
    private IIcon[][] IIconsList;

    public BlockEndOre1() {
        super(Material.iron);
        itemNames = new ArrayList<>();
        this.setCreativeTab(IUCore.tabssp4);
        this.setHarvestLevel("pickaxe", 3);
        this.setHardness(2F);
        this.setStepSound(Block.soundTypeStone);
        this.addItemsNames();
        GameRegistry.registerBlock(this, ItemBlockEndOre1.class, "endore1");
    }

    public static List<String> getlist() {
        return itemNames;
    }

    public boolean canEntityDestroy(IBlockAccess world, int x, int y, int z, Entity entity) {

        return !(entity instanceof EntityDragon);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            if (item != null) {
                ret.add(new ItemStack(item, 1, damageDropped(metadata)));
            }
        }
        if (ret.size() < 1)
            ret.add(new ItemStack(IUItem.endore1, 1, metadata));
        return ret;
    }

    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    public int damageDropped(int meta) {
        return meta;
    }


    public int quantityDropped(int meta, int fortune, Random random) {

        return quantityDroppedWithBonus(fortune, random);
    }

    public int quantityDroppedWithBonus(int fortune, Random random) {
        return (fortune == 0) ? quantityDropped(random)
                : (quantityDropped(random) + fortune);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        Block block = this;
        block.setBlockName(getlist().get(p_149650_1_)).setBlockTextureName(getlist().get(p_149650_1_));

        return null;

    }

    public void addItemsNames() {

        itemNames.add("endtin_stone");
        itemNames.add("enduran_stone");
        itemNames.add("endalum_stone");
        itemNames.add("endtitan_stone");
        itemNames.add("endcobalt_stone");
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

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(final Item item, final CreativeTabs tab, final List subItems) {
        for (int ix = 0; ix < itemNames.size(); ++ix) {
            subItems.add(new ItemStack(this, 1, ix));
        }
    }

}
