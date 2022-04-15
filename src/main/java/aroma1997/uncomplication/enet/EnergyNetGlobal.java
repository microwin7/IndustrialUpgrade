package aroma1997.uncomplication.enet;

import cpw.mods.fml.common.eventhandler.EventBus;
import cpw.mods.fml.relauncher.ReflectionHelper;
import ic2.api.energy.IEnergyNet;
import ic2.api.energy.NodeStats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map;
import java.util.WeakHashMap;

public class   EnergyNetGlobal implements IEnergyNet {
    private static Map<World, EnergyNetLocal> worldToEnergyNetMap;

    public TileEntity getTileEntity(final World world, final int x, final int y, final int z) {
        final EnergyNetLocal local = getForWorld(world);
        if (local != null) {
            return local.getTileEntity(x, y, z);
        }
        return null;
    }

    public TileEntity getNeighbor(final TileEntity te, final ForgeDirection dir) {
        if (te == null) {
            return null;
        }
        return this.getTileEntity(te.getWorldObj(), te.xCoord + dir.offsetX, te.yCoord + dir.offsetY, te.zCoord + dir.offsetZ);
    }

    public double getTotalEnergyEmitted(final TileEntity tileEntity) {
        if (tileEntity == null) {
            return 0.0;
        }
        final EnergyNetLocal local = getForWorld(tileEntity.getWorldObj());
        if (local != null) {
            return local.getTotalEnergyEmitted(tileEntity);
        }
        return 0.0;
    }

    public double getTotalEnergySunken(final TileEntity tileEntity) {
        if (tileEntity == null) {
            return 0.0;
        }
        final EnergyNetLocal local = getForWorld(tileEntity.getWorldObj());
        if (local != null) {
            return local.getTotalEnergySunken(tileEntity);
        }
        return 0.0;
    }

    public double getPowerFromTier(final int tier) {
        return 8 << tier * 2;
    }

    public NodeStats getNodeStats(final TileEntity te) {
        final EnergyNetLocal local = getForWorld(te.getWorldObj());
        if (local == null) {
            return new NodeStats(0.0, 0.0, 0.0);
        }
        return local.getNodeStats(te);
    }

    public int getTierFromPower(final double power) {
        if (power <= 0.0) {
            return 0;
        }
        return (int) Math.ceil(Math.log(power / 8.0) / Math.log(4.0));
    }

    public static EnergyNetLocal getForWorld(final World world) {
        if (world == null) {
            return null;
        }
        if (!EnergyNetGlobal.worldToEnergyNetMap.containsKey(world)) {
            EnergyNetGlobal.worldToEnergyNetMap.put(world, new EnergyNetLocal(world));
        }
        return EnergyNetGlobal.worldToEnergyNetMap.get(world);
    }

    public static void onTickStart(final World world) {
        final EnergyNetLocal energyNet = getForWorld(world);
        if (energyNet != null) {
            energyNet.onTickStart();
        }
    }

    public static void onTickEnd(final World world) {
        final EnergyNetLocal energyNet = getForWorld(world);
        if (energyNet != null) {
            energyNet.onTickEnd();
        }
    }

    public static EnergyNetGlobal initialize() {
        new EventHandler();
        final Map map = (Map) ReflectionHelper.getPrivateValue((Class) EventBus.class, (Object) MinecraftForge.EVENT_BUS, new String[]{"listeners"});
        Object toUnregister = null;
        for (final Object listener : map.keySet()) {
            if (listener != null && listener.getClass().equals(ic2.core.energy.EventHandler.class)) {
                toUnregister = listener;
                break;
            }
        }
        if (toUnregister != null) {
            MinecraftForge.EVENT_BUS.unregister(toUnregister);
        }
        return new EnergyNetGlobal();
    }

    public static void onWorldUnload(final World world) {
        final EnergyNetLocal local = EnergyNetGlobal.worldToEnergyNetMap.remove(world);
        if (local != null) {
            local.onUnload();
        }
    }

    static {
        EnergyNetGlobal.worldToEnergyNetMap = new WeakHashMap<>();
    }
}
