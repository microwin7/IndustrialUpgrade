package aroma1997.uncomplication.enet;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;

public class EventHandler {
    public EventHandler() {
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEnergyTileLoad(final EnergyTileLoadEvent event) {
        final EnergyNetLocal local = EnergyNetGlobal.getForWorld(event.world);
        if (local != null) {
            local.addTile((TileEntity) event.energyTile);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEnergyTileUnload(final EnergyTileUnloadEvent event) {
        final EnergyNetLocal local = EnergyNetGlobal.getForWorld(event.world);
        if (local != null) {
            local.removeTile((TileEntity) event.energyTile);
        }
    }

    @SubscribeEvent
    public void tick(final TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            EnergyNetGlobal.onTickStart(event.world);
        } else if (event.phase == TickEvent.Phase.END) {
            EnergyNetGlobal.onTickEnd(event.world);
        }
    }

    @SubscribeEvent
    public void onWorldUnload(final WorldEvent.Unload event) {
        EnergyNetGlobal.onWorldUnload(event.world);
    }
}
