
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import java.util.ArrayList;
import java.util.List;

public class EntityModule extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;

    public EntityModule() {
        this.itemNames = new ArrayList<>();
        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp1);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "module_entity");
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
        for(int i = 0; i < itemNames.size();i++)
            this.IIconsList[i] =  IIconRegister.registerIcon(Constants.TEXTURES_MAIN +itemNames.get(i));
    }
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity) {
        if (entity.worldObj.isRemote)
        {
            return false;
        }
        if(stack.getItemDamage() == 1) {
            if (entity instanceof EntityPlayer)
                return false;
            String entityId = EntityList.getEntityString(entity);
            NBTTagCompound root = new NBTTagCompound();
            root.setString("id", entityId);
            entity.writeToNBT(root);
            stack.setTagCompound(root);

            setDisplayNameFromEntityNameTag(stack, entity);
            entity.setDead();

            return true;
        } else if (stack.getItemDamage() == 0) {

            if (entity instanceof EntityPlayer){
                NBTTagCompound root = new NBTTagCompound();
                root.setString("name",((EntityPlayer)entity).getDisplayName() );
                entity.writeToNBT(root);
                stack.setTagCompound(root);
                return true;
            }else{
                return  false;
            }
        }
        return  false;
    }
    public static String  getMobTypeFromStack(ItemStack item) {

        if (item.stackTagCompound == null || !item.stackTagCompound.hasKey("id"))
            return null;
        return item.stackTagCompound.getString("id");
    }

    private void setDisplayNameFromEntityNameTag(ItemStack item, Entity ent) {
        if (ent instanceof EntityLiving) {
            EntityLiving entLiv = (EntityLiving)ent;
            if (entLiv.hasCustomNameTag()) {
                String name = entLiv.getCustomNameTag();
                if (name.length() > 0)
                    item.setStackDisplayName(name);
            }
        }
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
       if(itemStack.getItemDamage() == 1) {
           String mobName = getMobTypeFromStack(itemStack);
           if (mobName != null) {
               info.add(getDisplayNameForEntity(mobName));
           }
       }else{
           NBTTagCompound nbt = ModUtils.nbt(itemStack);
           if(!(nbt.getString("name").isEmpty()))
           info.add(nbt.getString("name"));
       }
    }
    public static String getDisplayNameForEntity(String mobName) {
        return StatCollector.translateToLocal("entity." + mobName + ".name");
    }
    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }



}
