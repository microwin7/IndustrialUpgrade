package com.denfop.events;

import com.denfop.IUCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import ic2.core.IC2;
import ic2.core.ITickCallback;
import ic2.core.WorldData;
import net.minecraft.world.World;

import java.util.Map;
import java.util.WeakHashMap;

public class TickHandlerIU {
    private static final boolean debugTickCallback;
    private static final Map<ITickCallback, Throwable> debugTraces;

    static {
        debugTickCallback = (System.getProperty("ic2.debugtickcallback") != null);
        debugTraces = (TickHandlerIU.debugTickCallback ? new WeakHashMap<>() : null);
    }

    private static void processTickCallbacks(final World world) {
        final WorldData worldData = WorldData.get(world);
        IC2.platform.profilerStartSection("SingleTickCallback");
        for (ITickCallback tickCallback = worldData.singleTickCallbacks.poll(); tickCallback != null; tickCallback = worldData.singleTickCallbacks.poll()) {
            if (TickHandlerIU.debugTickCallback) {
                TickHandlerIU.debugTraces.remove(tickCallback);
            }
            IC2.platform.profilerStartSection(tickCallback.getClass().getName());
            tickCallback.tickCallback(world);
            IC2.platform.profilerEndSection();
        }
        IC2.platform.profilerEndStartSection("ContTickCallback");
        worldData.continuousTickCallbacksInUse = true;
        for (final ITickCallback tickCallback2 : worldData.continuousTickCallbacks) {
            if (TickHandlerIU.debugTickCallback) {
                TickHandlerIU.debugTraces.remove(tickCallback2);
            }
            IC2.platform.profilerStartSection(tickCallback2.getClass().getName());
            tickCallback2.tickCallback(world);
            IC2.platform.profilerEndSection();
        }
        worldData.continuousTickCallbacksInUse = false;
        if (TickHandlerIU.debugTickCallback) {
        }
        worldData.continuousTickCallbacks.addAll(worldData.continuousTickCallbacksToAdd);
        worldData.continuousTickCallbacksToAdd.clear();
        worldData.continuousTickCallbacksToRemove.forEach(worldData.continuousTickCallbacks::remove);
        worldData.continuousTickCallbacksToRemove.clear();
        IC2.platform.profilerEndSection();
    }

    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            IUCore.proxy.profilerStartSection("Keyboard");
            IUCore.keyboard.sendKeyUpdate();
            IUCore.proxy.profilerEndStartSection("AudioManager");
            IUCore.audioManager.onTick();
            IUCore.proxy.profilerEndStartSection("TickCallbacks");
            if (IC2.platform.getPlayerInstance() != null && IC2.platform.getPlayerInstance().worldObj != null) {
                processTickCallbacks(IC2.platform.getPlayerInstance().worldObj);
            }
            IUCore.proxy.profilerEndSection();
        }
    }
}
