package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class ItemEntityModule extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public ItemEntityModule() {
        this.itemNames = new ArrayList<>();
        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module_entity");
    }

    public static String getMobTypeFromStack(ItemStack item) {

        if (item.stackTagCompound == null || !item.stackTagCompound.hasKey("id"))
            return null;
        return item.stackTagCompound.getString("id");
    }

    public static String getDisplayNameForEntity(String mobName) {
        return StatCollector.translateToLocal("entity." + mobName + ".name");
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("module_player");
        this.itemNames.add("module_mob");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        if (entity.worldObj.isRemote) {
            return false;
        }
        if (stack.getItemDamage() == 1) {
            if (entity instanceof EntityPlayer)
                return false;
            ItemStack stack1 = stack.copy();
            String entityId = EntityList.getEntityString(entity);
            NBTTagCompound root = new NBTTagCompound();
            root.setString("id", entityId);
            if (entity instanceof EntitySheep)
                root.setInteger("type", ((EntitySheep) entity).getFleeceColor());
            if (entity instanceof EntitySkeleton)
                root.setInteger("type", ((EntitySkeleton) entity).getSkeletonType());

            entity.writeToNBT(root);
            stack1.setTagCompound(root);

            setDisplayNameFromEntityNameTag(stack1, entity);
            entity.setDead();
            stack.stackSize--;
            stack1.stackSize = 1;
            if (!player.inventory.addItemStackToInventory(stack1)) {
                double var8 = 0.7D;
                double var10 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var12 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var14 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                EntityItem var16 = new EntityItem(player.worldObj, player.posX + var10, player.posY + var12, player.posZ + var14,
                        stack1);
                var16.delayBeforeCanPickup = 10;
                player.worldObj.spawnEntityInWorld(var16);
            }
            player.inventoryContainer.detectAndSendChanges();
            return true;
        } else if (stack.getItemDamage() == 0) {

            if (entity instanceof EntityPlayer) {
                ItemStack stack1 = stack.copy();
                NBTTagCompound root = new NBTTagCompound();
                root.setString("name", ((EntityPlayer) entity).getDisplayName());
                entity.writeToNBT(root);
                stack1.setTagCompound(root);
                stack1.stackSize = 1;
                stack.stackSize--;
                if (player.inventory.addItemStackToInventory(stack1)) {
                } else {
                    double var8 = 0.7D;
                    double var10 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                    double var12 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                    double var14 = (double) player.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                    EntityItem var16 = new EntityItem(player.worldObj, player.posX + var10, player.posY + var12, player.posZ + var14,
                            stack1);
                    var16.delayBeforeCanPickup = 10;
                    player.worldObj.spawnEntityInWorld(var16);
                }
                player.inventoryContainer.detectAndSendChanges();
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private void setDisplayNameFromEntityNameTag(ItemStack item, Entity ent) {
        if (ent instanceof EntityLiving) {
            EntityLiving entLiv = (EntityLiving) ent;
            if (entLiv.hasCustomNameTag()) {
                String name = entLiv.getCustomNameTag();
                if (name.length() > 0)
                    item.setStackDisplayName(name);
            }
        }
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        if (itemStack.getItemDamage() == 1) {
            String mobName = getMobTypeFromStack(itemStack);
            if (mobName != null) {
                info.add(getDisplayNameForEntity(mobName));
            }
        } else {
            NBTTagCompound nbt = ModUtils.nbt(itemStack);
            if (!(nbt.getString("name").isEmpty()))
                info.add(nbt.getString("name"));
        }
    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }


}
