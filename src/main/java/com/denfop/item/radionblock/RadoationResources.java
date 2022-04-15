package com.denfop.item.radionblock;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.item.armour.ItemArmorAdvHazmat;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RadoationResources extends Item {
    public static List<String> itemNames;
    private IIcon[] IIconsList;

    public RadoationResources() {
        itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "radiationresources");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    @Override
    public void onUpdate(ItemStack p_77663_1_, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!(p_77663_3_ instanceof EntityPlayer))
            return;
        if (p_77663_1_.getItemDamage() == 3 || p_77663_1_.getItemDamage() >= 5) {
            if (!ItemArmorAdvHazmat.hasCompleteHazmat((EntityLivingBase) p_77663_3_))
                ((EntityPlayer) p_77663_3_).addPotionEffect(new PotionEffect(9, 300));
            ((EntityPlayer) p_77663_3_).addPotionEffect(new PotionEffect(18, 300));

        }
    }

    public void addItemsNames() {
        itemNames.add("americium_gem");
        itemNames.add("neptunium_gem");
        itemNames.add("curium_gem");
        itemNames.add("california_gem");
        itemNames.add("rad_toriy");
        itemNames.add("mendelevium_gem");
        itemNames.add("berkelium_gem");
        itemNames.add("einsteinium_gem");
        itemNames.add("uran233_gem");
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

}
