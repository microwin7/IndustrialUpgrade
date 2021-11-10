package com.denfop.item.energy;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.proxy.CommonProxy;
import com.denfop.utils.Helpers;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IItemHudInfo;
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;
import java.util.List;

public class ItemBattery extends Item implements IElectricItem, IItemHudInfo {
    protected final double maxCharge;
    public final boolean wirellescharge;
    protected IIcon[] textures;
    protected final double transferLimit;
    private int rarity;
    protected final int tier;

    public ItemBattery(String name, double maxCharge, double transferLimit, int tier, boolean wirellescharge) {
        super();
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.tier = tier;
        setMaxDamage(27);
        setMaxStackSize(1);
        setNoRepair();
        setUnlocalizedName(name);
        this.setRarity(1);
        this.setCreativeTab(IUCore.tabssp2);
        this.wirellescharge = wirellescharge;
        GameRegistry.registerItem(this, name);
    }

    public void setRarity(int aRarity) {
        this.rarity = aRarity;
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.values()[this.rarity];
    }

    public String getTextureName(int index) {
        if (index < 5)
            return getUnlocalizedName().substring(3) + index;
        return null;
    }

    public String getUnlocalizedName() {
        return "iu" + super.getUnlocalizedName().substring(4);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        return StatCollector.translateToLocal(getUnlocalizedName(itemStack));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        int indexCount = 0;
        while (getTextureName(indexCount) != null) {
            indexCount++;
            if (indexCount > 32767)
                throw new RuntimeException("More Item Icons than actually possible @ " + getUnlocalizedName());
        }
        this.textures = new IIcon[indexCount];

        for (int index = 0; index < indexCount; index++)
            this.textures[index] = iconRegister.registerIcon(Constants.TEXTURES + ":" + getTextureName(index));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        if (meta <= 1)
            return this.textures[4];
        if (meta >= getMaxDamage() - 1)
            return this.textures[0];
        return this.textures[3 - 3 * (meta - 2) / (getMaxDamage() - 4 + 1)];
    }

    public boolean canProvideEnergy(ItemStack itemStack) {
        return true;
    }

    public double getTransferLimit(ItemStack itemStack) {
        return this.transferLimit;
    }

    @Override
    public void onUpdate(ItemStack itemStack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
        if (!(p_77663_3_ instanceof EntityPlayer))
            return;
        if (wirellescharge) {
            int mode = ModUtils.NBTGetInteger(itemStack, "mode");
            EntityPlayer entityplayer = (EntityPlayer) p_77663_3_;
            if (mode == 1) {
                if (IC2.platform.isSimulating()) {
                    boolean transferred = false;
                    for (int i = 0; i < 9; i++) {
                        ItemStack stack = entityplayer.inventory.mainInventory[i];
                        if (stack != null && !(stack.getItem() instanceof ic2.core.item.ItemBattery)) {
                            double transfer = ElectricItem.manager.discharge(itemStack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                            if (!(transfer <= 0.0D)) {
                                transfer = ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
                                if (!(transfer <= 0.0D)) {
                                    ElectricItem.manager.discharge(itemStack, transfer, 2147483647, true, true, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                    if (transferred && !IC2.platform.isRendering())
                        entityplayer.openContainer.detectAndSendChanges();
                }
            } else if (mode == 2) {
                if (IC2.platform.isSimulating()) {
                    boolean transferred = false;
                    for (int i = 0; i < entityplayer.inventory.mainInventory.length; i++) {
                        ItemStack stack = entityplayer.inventory.mainInventory[i];
                        if (stack != null && !(stack.getItem() instanceof ic2.core.item.ItemBattery)) {
                            double transfer = ElectricItem.manager.discharge(itemStack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                            if (!(transfer <= 0.0D)) {
                                transfer = ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
                                if (!(transfer <= 0.0D)) {
                                    ElectricItem.manager.discharge(itemStack, transfer, 2147483647, true, true, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                    if (transferred && !IC2.platform.isRendering())
                        entityplayer.openContainer.detectAndSendChanges();
                }

            } else if (mode == 3) {
                if (IC2.platform.isSimulating()) {
                    boolean transferred = false;
                    for (int i = 0; i < entityplayer.inventory.armorInventory.length; i++) {
                        ItemStack stack = entityplayer.inventory.armorInventory[i];
                        if (stack != null && !(stack.getItem() instanceof ic2.core.item.ItemBattery)) {
                            double transfer = ElectricItem.manager.discharge(itemStack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                            if (!(transfer <= 0.0D)) {
                                transfer = ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
                                if (!(transfer <= 0.0D)) {
                                    ElectricItem.manager.discharge(itemStack, transfer, 2147483647, true, true, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                    if (transferred && !IC2.platform.isRendering())
                        entityplayer.openContainer.detectAndSendChanges();
                }

            } else if (mode == 4) {
                if (IC2.platform.isSimulating()) {
                    boolean transferred = false;
                    for (int i = 0; i < entityplayer.inventory.armorInventory.length; i++) {
                        ItemStack stack = entityplayer.inventory.armorInventory[i];
                        if (stack != null && !(stack.getItem() instanceof ic2.core.item.ItemBattery)) {
                            double transfer = ElectricItem.manager.discharge(itemStack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                            if (!(transfer <= 0.0D)) {
                                transfer = ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
                                if (!(transfer <= 0.0D)) {
                                    ElectricItem.manager.discharge(itemStack, transfer, 2147483647, true, true, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                    if (transferred && !IC2.platform.isRendering())
                        entityplayer.openContainer.detectAndSendChanges();


                }
                if (IC2.platform.isSimulating()) {
                    boolean transferred = false;
                    for (int i = 0; i < entityplayer.inventory.mainInventory.length; i++) {
                        ItemStack stack = entityplayer.inventory.mainInventory[i];
                        if (stack != null && !(stack.getItem() instanceof ic2.core.item.ItemBattery)) {
                            double transfer = ElectricItem.manager.discharge(itemStack, 2.0D * this.transferLimit, 2147483647, true, true, true);
                            if (!(transfer <= 0.0D)) {
                                transfer = ElectricItem.manager.charge(stack, transfer, 2147483647, true, false);
                                if (!(transfer <= 0.0D)) {
                                    ElectricItem.manager.discharge(itemStack, transfer, 2147483647, true, true, false);
                                    transferred = true;
                                }
                            }
                        }
                    }
                    if (transferred && !IC2.platform.isRendering())
                        entityplayer.openContainer.detectAndSendChanges();
                }
            }

        }


    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass) {

        int mode = ModUtils.NBTGetInteger(par1ItemStack, "mode");
        return mode != 0;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (IC2.platform.isSimulating() && wirellescharge) {
            int mode = ModUtils.NBTGetInteger(itemStack, "mode");
            mode++;
            if (mode > 4 || mode < 0)
                mode = 0;

            ModUtils.NBTSetInteger(itemStack, "mode", mode);
            CommonProxy.sendPlayerMessage(entityplayer,
                    EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                            + Helpers.formatMessage("message.battery.mode." + mode));
        }

        return itemStack;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        if (wirellescharge) {
            if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                par3List.add(StatCollector.translateToLocal("press.lshift"));


            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                par3List.add(StatCollector.translateToLocal("iu.changemodebattery"));

            }
        }
    }

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    public List<String> getHudInfo(ItemStack itemStack) {
        List<String> info = new LinkedList<>();
        info.add(ElectricItem.manager.getToolTip(itemStack));
        return info;
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
