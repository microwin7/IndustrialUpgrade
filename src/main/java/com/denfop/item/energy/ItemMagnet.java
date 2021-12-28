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
import ic2.core.IC2;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemMagnet extends Item implements IElectricItem {
    protected final double maxCharge;
    protected final IIcon[] textures = new IIcon[1];
    protected final double transferLimit;
    protected final int tier;
    private final String name;
    private final int radius;
    private int rarity;

    public ItemMagnet(String name, double maxCharge, double transferLimit, int tier, int radius) {
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
        this.setCreativeTab(IUCore.tabssp2);
        GameRegistry.registerItem(this, name);
        this.radius = radius;
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

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_) {

        if (!(entity instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) entity;
        int mode = ModUtils.NBTGetInteger(itemStack, "mode");
        if (mode != 0) {
            AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(entity.posX - radius, entity.posY - radius, entity.posZ - radius, entity.posX + radius, entity.posY + radius, entity.posZ + radius);
            List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity, axisalignedbb);

            for (Entity entityinlist : list) {
                if (entityinlist instanceof EntityItem) {
                    EntityItem item = (EntityItem) entityinlist;
                    if (ElectricItem.manager.canUse(itemStack, 500)) {
                        ItemStack stack = item.getEntityItem();
                        if (!(stack.getItem() instanceof ItemMagnet))
                            if (mode == 1) {
                                if (player.inventory.addItemStackToInventory(stack)) {

                                    ElectricItem.manager.use(itemStack, 500, (EntityLivingBase) entity);
                                    player.inventoryContainer.detectAndSendChanges();
                                } else {
                                    boolean xcoord = item.posX + 2 >= entity.posX && item.posX - 2 <= entity.posX;
                                    boolean zcoord = item.posZ + 2 >= entity.posZ && item.posZ - 2 <= entity.posZ;

                                    if (!xcoord && !zcoord) {
                                        item.setPosition(entity.posX, entity.posY - 1, entity.posZ);
                                        item.delayBeforeCanPickup = 10;
                                        world.func_147479_m((int) (entity.posX), (int) entity.posY - 1, (int) (entity.posZ));
                                    }
                                }
                            } else if (mode == 2) {
                                boolean xcoord = item.posX + 2 >= entity.posX && item.posX - 2 <= entity.posX;
                                boolean zcoord = item.posZ + 2 >= entity.posZ && item.posZ - 2 <= entity.posZ;

                                if (!xcoord && !zcoord) {
                                    item.setPosition(entity.posX, entity.posY - 1, entity.posZ);
                                    item.delayBeforeCanPickup = 10;
                                    world.func_147479_m((int) (entity.posX), (int) entity.posY - 1, (int) (entity.posZ));
                                }
                            }
                        player.inventoryContainer.detectAndSendChanges();
                    }

                }
            }
        }


    }

    public boolean hasEffect(ItemStack par1ItemStack, int pass) {

        int mode = ModUtils.NBTGetInteger(par1ItemStack, "mode");
        return mode != 0;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityplayer) {
        if (IC2.platform.isSimulating()) {
            int mode = ModUtils.NBTGetInteger(itemStack, "mode");
            mode++;
            if (mode > 2 || mode < 0)
                mode = 0;

            ModUtils.NBTSetInteger(itemStack, "mode", mode);
            CommonProxy.sendPlayerMessage(entityplayer,
                    EnumChatFormatting.GREEN + Helpers.formatMessage("message.text.mode") + ": "
                            + Helpers.formatMessage("message.magnet.mode." + mode));
        }

        return itemStack;
    }

    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
            par3List.add(StatCollector.translateToLocal("press.lshift"));


        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            par3List.add(StatCollector.translateToLocal("iu.changemodebattery"));

        }
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
        // TODO Auto-generated method stub
        return itemStack.getItem();
    }

}
