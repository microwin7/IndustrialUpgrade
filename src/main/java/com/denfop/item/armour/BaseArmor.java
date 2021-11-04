package com.denfop.item.armour;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;


public class BaseArmor extends ItemArmor {

    private final String name;
    private final String armor_type;
    private final int render;

    public BaseArmor(String name, ArmorMaterial material, int renderIndex, int armorType, String name_type) {
        super(material, renderIndex, armorType);
        setUnlocalizedName(name);
        setCreativeTab(IUCore.tabssp2);
        GameRegistry.registerItem(this, name);
        this.name = name;
        this.render = armorType;
        this.armor_type = name_type;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Constants.TEXTURES + ":" + name);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        if (this.render != 2) {
            return Constants.TEXTURES + ":" + "textures/armor/" + armor_type + "_layer_1.png";
        } else {
            return Constants.TEXTURES + ":" + "textures/armor/" + armor_type + "_layer_2.png";
        }
    }

}
