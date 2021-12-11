package com.denfop.item.upgrade;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.IItemHudInfo;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import ic2.core.upgrade.UpgradeRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ItemUpgradeModule extends Item implements IUpgradeItem, IItemHudInfo {

    private static final DecimalFormat decimalformat = new DecimalFormat("0.##");
    private final List<String> itemNames;
    private final IIcon[] IIconsList;

    public ItemUpgradeModule() {
        super();
        setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        IUItem.overclockerUpgrade = UpgradeRegistry.register(new ItemStack(this, 1, Type.Overclocker1.ordinal()));
        IUItem.overclockerUpgrade1 = UpgradeRegistry.register(new ItemStack(this, 1, Type.Overclocker2.ordinal()));
        IUItem.tranformerUpgrade = UpgradeRegistry.register(new ItemStack(this, 1, Type.transformer.ordinal()));
        IUItem.tranformerUpgrade1 = UpgradeRegistry.register(new ItemStack(this, 1, Type.transformer1.ordinal()));

       this.itemNames = new ArrayList<>();
        this.IIconsList = new IIcon[4];
        this.addItemsNames();
        GameRegistry.registerItem(this, "upgrademodule");
    }

    public static Type getType(int meta) {
        if (meta < 0 || meta >= Type.Values.length)
            return null;
        return Type.Values[meta];
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("iu.overclockerUpgrade1");
        this.itemNames.add("iu.overclockerUpgrade2");
        this.itemNames.add("iu.transformerUpgrade1");
        this.itemNames.add("iu.transformerUpgrade2");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList[0] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "overclockerUpgrade1");
        this.IIconsList[1] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "overclockerUpgrade2");
        this.IIconsList[2] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "transformerUpgrade1");
        this.IIconsList[3] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + "transformerUpgrade2");

    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }

    public IIcon getIcon(ItemStack stack, int pass) {

        Type type = getType(stack.getItemDamage());
        if (type == null)
            return null;

        return super.getIcon(stack, pass);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    public List<String> getHudInfo(ItemStack stack) {
        List<String> info = new LinkedList<>();
        info.add("Machine Upgrade");
        return info;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

        Type type = getType(stack.getItemDamage());
        if (type == null)
            return;
        super.addInformation(stack, player, list, par4);
        switch (type) {
            case Overclocker1:
            case Overclocker2:
                list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.time",
                        decimalformat.format(100.0D
                                * Math.pow(getProcessTimeMultiplier(stack, null), stack.stackSize))));
                list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.overclocker.power",
                        decimalformat.format(100.0D
                                * Math.pow(getEnergyDemandMultiplier(stack, null), stack.stackSize))));
                break;
            case transformer:
            case transformer1:
                list.add(StatCollector.translateToLocalFormatted("ic2.tooltip.upgrade.transformer", this.getExtraTier(stack, null) * stack.stackSize));
                break;

        }
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer entityplayer, World world, int x, int y, int z, int side,
                             float xOffset, float yOffset, float zOffset) {


        return false;
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }

    public boolean isSuitableFor(ItemStack stack, Set<UpgradableProperty> types) {
        Type type = getType(stack.getItemDamage());
        if (type == null)
            return false;
        switch (type) {

            case Overclocker1:
            case Overclocker2:
                return (types.contains(UpgradableProperty.Processing) || types.contains(UpgradableProperty.Augmentable));
            case transformer:
            case transformer1:
                return types.contains(UpgradableProperty.Transformer);

        }
        return false;
    }

    public int getAugmentation(ItemStack stack, IUpgradableBlock parent) {
        Type type = getType(stack.getItemDamage());
        if (type == null)
            return 0;
        switch (type) {
            case Overclocker1:
            case Overclocker2:
                return 1;
        }
        return 0;
    }

    public int getExtraProcessTime(ItemStack stack, IUpgradableBlock parent) {
        return 0;
    }

    public double getProcessTimeMultiplier(ItemStack stack, IUpgradableBlock parent) {
        Type type = getType(stack.getItemDamage());
        if (type == null)
            return 1.0D;
        switch (type) {
            case Overclocker1:
                return 0.5D;
            case Overclocker2:
                return 0.4D;
        }
        return 1.0D;
    }

    public int getExtraEnergyDemand(ItemStack stack, IUpgradableBlock parent) {
        return 0;
    }

    public double getEnergyDemandMultiplier(ItemStack stack, IUpgradableBlock parent) {
        Type type = getType(stack.getItemDamage());
        if (type == null)
            return 1.0D;
        switch (type) {
            case Overclocker1:
                return 1.3D;
            case Overclocker2:
                return 1.2D;
        }
        return 1.0D;
    }

    @Override
    public int getExtraEnergyStorage(ItemStack itemStack, IUpgradableBlock iUpgradableBlock) {
        return 0;
    }

    public int getExtraTier(ItemStack stack, IUpgradableBlock parent) {
        ItemUpgradeModule.Type type = getType(stack.getItemDamage());
        if (type == null) {
            return 0;
        } else {
            switch (type) {
                case transformer:
                    return 2;
                case transformer1:
                    return 4;
                default:
                    return 0;
            }
        }
    }

    public double getEnergyStorageMultiplier(ItemStack stack, IUpgradableBlock parent) {
        return 1.0D;
    }

    public boolean modifiesRedstoneInput(ItemStack stack, IUpgradableBlock parent) {


        return false;
    }

    public int getRedstoneInput(ItemStack stack, IUpgradableBlock parent, int externalInput) {


        return externalInput;
    }

    public boolean onTick(ItemStack stack, IUpgradableBlock parent) {

        return false;
    }

    public void onProcessEnd(ItemStack stack, IUpgradableBlock parent, List<ItemStack> output) {
    }

    private enum Type {
        Overclocker1, Overclocker2, transformer, transformer1;

        public static final Type[] Values = values();

    }


}
