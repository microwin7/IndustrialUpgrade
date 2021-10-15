
package com.denfop.item.base;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.tiles.base.TileEntityQuarryVein;
import com.denfop.tiles.base.TileEntityVein;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class IUItemBase extends Item {

    public IUItemBase(String name) {
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        setUnlocalizedName(name);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if(stack.getItem().equals(IUItem.analyzermodule)){
            if(world.getTileEntity(x,y,z) != null && world.getTileEntity(x,y,z) instanceof TileEntityQuarryVein){
                TileEntityQuarryVein tile = (TileEntityQuarryVein) world.getTileEntity(x,y,z);
                if(!tile.analysis) {
                    int xx = tile.x;
                    int yy = tile.y;
                    int zz = tile.z;
                    if(world.getTileEntity(xx,yy,zz) != null && world.getTileEntity(xx,yy,zz) instanceof TileEntityVein){
                        NBTTagCompound nbt = ModUtils.nbt(stack);
                        nbt.setBoolean("vein",true);
                        nbt.setInteger("x",xx);
                        nbt.setInteger("y",yy);
                        nbt.setInteger("z",zz);
                        return  true;
                    }
                }
            }

        }
        return false;
    }

}
