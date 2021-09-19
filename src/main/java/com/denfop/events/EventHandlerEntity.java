package com.denfop.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class EventHandlerEntity {
    @SubscribeEvent
    public void droppedItem(ItemTossEvent event) {
        NBTTagCompound itemData = event.entityItem.getEntityData();
        itemData.setString("thrower", event.player.getCommandSenderName());
    }

}
