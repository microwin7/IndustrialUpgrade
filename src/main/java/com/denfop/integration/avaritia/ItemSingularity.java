package com.denfop.integration.avaritia;

import com.denfop.utils.Helpers;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.Avaritia;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ItemSingularity extends Item implements IHaloRenderItem {
    public static final String[] types = new String[]{"mikhail", "aluminium", "vanady", "wolfram", "cobalt", "magnesium", "platium", "titanium", "chromium", "spinel", "zinc", "manganese", "invar", "caravky", "electrium", "iridium", "germanium"};
    public static final int[] colors = new int[]{Helpers.convertRGBcolorToInt(119, 210, 202), Helpers.convertRGBcolorToInt(156, 132, 156), Helpers.convertRGBcolorToInt(0, 211, 226), Helpers.convertRGBcolorToInt(199, 199, 199), Helpers.convertRGBcolorToInt(0, 166, 226), Helpers.convertRGBcolorToInt(217, 185, 211), Helpers.convertRGBcolorToInt(165, 194, 244), Helpers.convertRGBcolorToInt(71, 71, 71), Helpers.convertRGBcolorToInt(64, 145, 66), Helpers.convertRGBcolorToInt(254, 145, 196), Helpers.convertRGBcolorToInt(199, 199, 199), Helpers.convertRGBcolorToInt(226, 167, 187), Helpers.convertRGBcolorToInt(155, 155, 155), Helpers.convertRGBcolorToInt(114, 69, 26), Helpers.convertRGBcolorToInt(226, 199, 0), Helpers.convertRGBcolorToInt(240, 240, 240), Helpers.convertRGBcolorToInt(188, 104, 21)};
    public static final int[] colors2 = new int[]{Helpers.convertRGBcolorToInt(0, 151, 130), Helpers.convertRGBcolorToInt(111, 89, 111), Helpers.convertRGBcolorToInt(131, 140, 0), Helpers.convertRGBcolorToInt(109, 109, 109), Helpers.convertRGBcolorToInt(0, 103, 140), Helpers.convertRGBcolorToInt(151, 128, 141), Helpers.convertRGBcolorToInt(28, 98, 221), Helpers.convertRGBcolorToInt(50, 50, 51), Helpers.convertRGBcolorToInt(36, 81, 37), Helpers.convertRGBcolorToInt(248, 108, 173), Helpers.convertRGBcolorToInt(186, 186, 186), Helpers.convertRGBcolorToInt(219, 147, 172), Helpers.convertRGBcolorToInt(85, 85, 85), Helpers.convertRGBcolorToInt(62, 38, 15), Helpers.convertRGBcolorToInt(138, 121, 1), Helpers.convertRGBcolorToInt(231, 231, 231), Helpers.convertRGBcolorToInt(35, 35, 35)};
    public static IIcon background;
    public static IIcon foreground;

    public ItemSingularity() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setUnlocalizedName("avaritia_iu_singularity");
        this.setTextureName("avaritia:singularity");
        this.setCreativeTab(Avaritia.tab);
        GameRegistry.registerItem(this, "avaritia_iu_singularity");
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack itemstack, int renderpass) {
        return renderpass == 0 ? colors2[itemstack.getItemDamage() % colors.length] : colors[itemstack.getItemDamage() % colors2.length];
    }

    public String getUnlocalizedName(ItemStack stack) {
        int i = MathHelper.clamp_int(stack.getItemDamage(), 0, types.length);
        return "item.singularity_" + types[i];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int j = 0; j < types.length; ++j) {
            list.add(new ItemStack(item, 1, j));
        }

    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister ir) {
        foreground = ir.registerIcon("avaritia:singularity");
        background = ir.registerIcon("avaritia:singularity2");
    }

    public IIcon getIcon(ItemStack stack, int pass) {
        return pass == 0 ? background : foreground;
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.uncommon;
    }


    @SideOnly(Side.CLIENT)
    public boolean drawHalo(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getHaloTexture(ItemStack stack) {
        return ((ItemResource) LudicrousItems.resource).halo[0];
    }

    @SideOnly(Side.CLIENT)
    public int getHaloSize(ItemStack stack) {
        return 4;
    }

    @SideOnly(Side.CLIENT)
    public boolean drawPulseEffect(ItemStack stack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getHaloColour(ItemStack stack) {
        return -16777216;
    }
}
