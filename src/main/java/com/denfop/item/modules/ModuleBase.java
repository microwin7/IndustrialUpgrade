package com.denfop.item.modules;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ModuleBase extends Item {
    public ModuleBase(String name) {
        super();
        this.setCreativeTab(IUCore.tabssp1);
        setUnlocalizedName(name);
        setTextureName(Constants.TEXTURES_MAIN + name);
        GameRegistry.registerItem(this, name);
    }

    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean b) {
        if (IUItem.modules.get(itemStack.getItem()) != null) {
            if (IUItem.modules.get(itemStack.getItem()).type != EnumType.PHASE && IUItem.modules.get(itemStack.getItem()).type != EnumType.MOON_LINSE)
                info.add(StatCollector.translateToLocal(IUItem.modules.get(itemStack.getItem()).description) + " +" + ModUtils.getString(IUItem.modules.get(itemStack.getItem()).percent_description) + "% "
                        + StatCollector.translateToLocal("iu.module"));
        }
    }


}
  