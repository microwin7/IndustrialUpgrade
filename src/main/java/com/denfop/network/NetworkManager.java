package com.denfop.network;

import com.denfop.IUCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkItemEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.WorldData;
import ic2.core.network.ClientModifiable;
import ic2.core.network.DataEncoder;
import ic2.core.network.IPlayerItemDataListener;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.*;
import java.lang.reflect.Field;

public class NetworkManager {
    private static FMLEventChannel channel;

    public NetworkManager() {
        if (NetworkManager.channel == null) {
            NetworkManager.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel("IU");
        }
        NetworkManager.channel.register(this);
    }

    private static void retrieveFieldData(final Object object, final String fieldName, final OutputStream out) throws IOException {
        final DataOutputStream os = new DataOutputStream(out);
        os.writeUTF(fieldName);
        try {
            DataEncoder.encode(os, ReflectionUtil.getValueRecursive(object, fieldName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        os.flush();
    }

    private static FMLProxyPacket makePacket(final byte[] data) {
        return new FMLProxyPacket(Unpooled.wrappedBuffer(data), "IU");
    }

    protected boolean isClient() {
        return false;
    }

    public void onTickEnd(final World world) {
        final WorldData value;
        final WorldData worldData = value = WorldData.get(world);
        final int ticksLeftToNetworkUpdate = value.ticksLeftToNetworkUpdate - 1;
        value.ticksLeftToNetworkUpdate = ticksLeftToNetworkUpdate;
        if (ticksLeftToNetworkUpdate == 0) {
            this.sendUpdatePacket(world);
            worldData.ticksLeftToNetworkUpdate = 1;
        }
    }

    public void updateTileEntityField(final TileEntity te, final String field) {
        if (!this.isClient()) {
        } else if (this.getClientModifiableField(te.getClass(), field) == null) {
            IC2.log.warn(LogCategory.Network, "Field update for %s failed.", te);
        } else {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final DataOutputStream os = new DataOutputStream(buffer);
            try {
                os.writeByte(13);
                DataEncoder.encode(os, te, false);
                retrieveFieldData(te, field, os);
                os.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            final byte[] packetData = buffer.toByteArray();
            this.sendPacket(packetData);
        }
    }

    private Field getClientModifiableField(final Class<?> cls, final String fieldName) {
        final Field field = ReflectionUtil.getFieldRecursive(cls, fieldName);
        if (field == null) {
            IC2.log.warn(LogCategory.Network, "Can't find field %s in %s.", fieldName, cls.getName());
            return null;
        }
        if (field.getAnnotation(ClientModifiable.class) == null) {
            IC2.log.warn(LogCategory.Network, "The field %s in %s is not modifiable.", fieldName, cls.getName());
            return null;
        }
        return field;
    }

    public void initiateTileEntityEvent(final TileEntity te, final int event, final boolean limitRange) {
        if (te.getWorldObj().playerEntities.isEmpty()) {
            return;
        }
        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        final DataOutputStream os = new DataOutputStream(buffer);
        try {
            os.writeByte(1);
            DataEncoder.encode(os, te, false);
            os.writeInt(event);
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final byte[] packetData = buffer.toByteArray();
        final int maxDistance = limitRange ? 400 : (MinecraftServer.getServer().getConfigurationManager().getEntityViewDistance() + 16);
        for (final Object obj : te.getWorldObj().playerEntities) {
            if (!(obj instanceof EntityPlayerMP)) {
                continue;
            }
            final EntityPlayerMP entityPlayer = (EntityPlayerMP) obj;
            final int distanceX = te.xCoord - (int) entityPlayer.posX;
            final int distanceZ = te.zCoord - (int) entityPlayer.posZ;
            int distance;
            if (limitRange) {
                distance = distanceX * distanceX + distanceZ * distanceZ;
            } else {
                distance = Math.max(Math.abs(distanceX), Math.abs(distanceZ));
            }
            if (distance > maxDistance) {
                continue;
            }
            this.sendPacket(packetData, entityPlayer);
        }
    }

    public void initiateClientTileEntityEvent(final TileEntity te, final int event) {
    }

    private void sendUpdatePacket(final World world) {
        final WorldData worldData = WorldData.get(world);
        if (worldData.networkedFieldsToUpdate.isEmpty()) {
            return;
        }


        worldData.networkedFieldsToUpdate.clear();
    }

    @SubscribeEvent
    public void onPacket(final FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (this.getClass() == NetworkManager.class) {
            this.onPacketData(new ByteBufInputStream(event.packet.payload()), ((NetHandlerPlayServer) event.handler).playerEntity);
        }
    }

    protected void onPacketData(final InputStream isRaw, final EntityPlayer player) {
        try {
            if (isRaw.available() == 0) {
                return;
            }
            final int id = isRaw.read();

            final DataInputStream is = new DataInputStream(isRaw);
            switch (id) {
                case 1: {
                    final ItemStack stack = DataEncoder.decode(is, ItemStack.class);
                    final int event = is.readInt();
                    if (stack.getItem() instanceof INetworkItemEventListener) {
                        ((INetworkItemEventListener) stack.getItem()).onNetworkEvent(stack, player, event);
                        break;
                    }
                    break;
                }
                case 2: {
                    final int keyState = is.readInt();
                    IUCore.keyboard.processKeyUpdate(player, keyState);
                    break;
                }
                case 3: {
                    final TileEntity te = DataEncoder.decode(is, TileEntity.class);
                    final int event = is.readInt();
                    if (te instanceof INetworkClientTileEntityEventListener) {
                        ((INetworkClientTileEntityEventListener) te).onNetworkEvent(player, event);
                        break;
                    }
                    break;
                }

                case 10: {
                    final int slot = is.readByte();
                    final Item item = DataEncoder.decode(is, Item.class);
                    final int dataCount = is.readShort();
                    final Object[] subData = new Object[dataCount];
                    for (int i = 0; i < dataCount; ++i) {
                        subData[i] = DataEncoder.decode(is);
                    }
                    if (slot >= 0 && slot <= 9) {
                        final ItemStack itemStack = player.inventory.mainInventory[slot];
                        if (itemStack != null && itemStack.getItem() == item && item instanceof IPlayerItemDataListener) {
                            ((IPlayerItemDataListener) item).onPlayerItemNetworkData(player, slot, subData);
                        }
                        break;
                    }
                    break;
                }
                case 11: {
                    final int windowId = DataEncoder.readVarInt(is);
                    final String fieldName = is.readUTF();
                    final Object value = DataEncoder.decode(is);
                    if (player.openContainer instanceof ContainerBase && player.openContainer.windowId == windowId && (this.isClient() || this.getClientModifiableField(player.openContainer.getClass(), fieldName) != null)) {
                        ReflectionUtil.setValueRecursive(player.openContainer, fieldName, value);
                        break;
                    }
                    break;
                }
                case 12: {
                    final int windowId = DataEncoder.readVarInt(is);
                    final String event2 = is.readUTF();
                    if (player.openContainer instanceof ContainerBase && player.openContainer.windowId == windowId) {
                        ((ContainerBase) player.openContainer).onContainerEvent(event2);
                        break;
                    }
                    break;
                }
                case 13: {
                    final TileEntity te = DataEncoder.decode(is, TileEntity.class);
                    final String fieldName = is.readUTF();
                    final Object value = DataEncoder.decode(is);
                    if (te != null && (this.isClient() || this.getClientModifiableField(te.getClass(), fieldName) != null)) {
                        ReflectionUtil.setValueRecursive(te, fieldName, value);
                        break;
                    }
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initiateKeyUpdate(final int keyState) {
    }

    protected void sendPacket(final byte[] data) {
        if (IC2.platform.isSimulating()) {
            NetworkManager.channel.sendToAll(makePacket(data));
        } else {
            NetworkManager.channel.sendToServer(makePacket(data));
        }
    }

    protected void sendPacket(final byte[] data, final EntityPlayerMP player) {
        NetworkManager.channel.sendTo(makePacket(data), player);
    }


}
