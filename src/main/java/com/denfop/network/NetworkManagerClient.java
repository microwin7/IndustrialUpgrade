package com.denfop.network;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkItemEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.BlockTileEntity;
import ic2.core.block.TileEntityBlock;
import ic2.core.block.comp.TileEntityComponent;
import ic2.core.item.IHandHeldInventory;
import ic2.core.network.DataEncoder;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import io.netty.buffer.ByteBufInputStream;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.zip.InflaterInputStream;

@SuppressWarnings("ALL")
@SideOnly(Side.CLIENT)
public class NetworkManagerClient extends NetworkManager {
    private ByteArrayOutputStream largePacketBuffer;

    private static void processInitPacket(final byte[] data) throws IOException {
        final ByteArrayInputStream buffer = new ByteArrayInputStream(data);
        final DataInputStream is = new DataInputStream(buffer);
        final int dimensionId = is.readInt();
        final World world = Minecraft.getMinecraft().theWorld;
        if (world.provider.dimensionId != dimensionId) {
            return;
        }
        Label_0115_Outer:
        while (true) {
            int x;
            try {
                x = is.readInt();
            } catch (EOFException e) {
                break;
            }
            final int y = is.readInt();
            final int z = is.readInt();
            final byte[] fieldData = new byte[is.readInt()];
            is.readFully(fieldData);
            final ByteArrayInputStream fieldDataBuffer = new ByteArrayInputStream(fieldData);
            final DataInputStream fieldDataStream = new DataInputStream(fieldDataBuffer);
            final Map<String, Object> fieldValues = new HashMap<>();
            while (true) {
                {
                    try {
                        fieldDataStream.readUTF();
                    } catch (EOFException e2) {
                        final Block block = world.getBlock(x, y, z);
                        if (block == Blocks.air) {
                            continue Label_0115_Outer;
                        }
                        TileEntity te;
                        if (block instanceof BlockTileEntity) {
                            final int tileEntityId = (int) fieldValues.get("tileEntityId");
                            te = ((BlockTileEntity) block).getTileEntity(tileEntityId);
                            if (te != null) {
                                world.setTileEntity(x, y, z, te);
                            }
                        } else {
                            te = world.getTileEntity(x, y, z);
                        }
                        if (te == null) {
                            continue Label_0115_Outer;
                        }
                        for (final Map.Entry<String, Object> entry : fieldValues.entrySet()) {
                            ReflectionUtil.setValueRecursive(te, entry.getKey(), entry.getValue());
                            if (te instanceof INetworkUpdateListener) {
                                ((INetworkUpdateListener) te).onNetworkUpdate(entry.getKey());
                            }
                        }
                        continue Label_0115_Outer;

                    }
                }
                break;
            }
            break;
        }
        is.close();
    }

    private static void processChatPacket(final byte[] data) {
        String messages;
        messages = new String(data, StandardCharsets.UTF_8);
        for (final String line : messages.split("[\\r\\n]+")) {
            IC2.platform.messagePlayer(null, line);
        }
    }

    private static void processConsolePacket(final byte[] data) {
        String messages;
        messages = new String(data, StandardCharsets.UTF_8);
        final PrintStream console = new PrintStream(new FileOutputStream(FileDescriptor.out));

        console.flush();
    }

    @Override
    protected boolean isClient() {
        return true;
    }

    @Override
    public void initiateKeyUpdate(final int keyState) {
        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final DataOutputStream os = new DataOutputStream(buffer);
            os.writeByte(2);
            os.writeInt(keyState);
            os.close();
            this.sendPacket(buffer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initiateClientTileEntityEvent(final TileEntity te, final int event) {
        try {
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final DataOutputStream os = new DataOutputStream(buffer);
            os.writeByte(3);
            DataEncoder.encode(os, te, false);
            os.writeInt(event);
            os.close();
            this.sendPacket(buffer.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public void onPacket(final FMLNetworkEvent.ClientCustomPacketEvent event) {
        assert !this.getClass().getName().equals(NetworkManager.class.getName());
        this.onPacketData(new ByteBufInputStream(event.packet.payload()), Minecraft.getMinecraft().thePlayer);
    }

    @Override
    protected void onPacketData(final InputStream isRaw, final EntityPlayer player) {
        isRaw.mark(Integer.MAX_VALUE);
        final DataInputStream is = new DataInputStream(isRaw);
        try {
            if (isRaw.available() == 0) {
                return;
            }
            switch (is.read()) {
                case 0: {
                    final int state = is.read();
                    if ((state & 0x1) != 0x0) {
                        this.largePacketBuffer = new ByteArrayOutputStream(16384);
                    }
                    final byte[] buffer = new byte[4096];
                    int len;
                    while ((len = is.read(buffer)) != -1) {
                        this.largePacketBuffer.write(buffer, 0, len);
                    }
                    if ((state & 0x2) != 0x0) {
                        final ByteArrayInputStream inflateInput = new ByteArrayInputStream(this.largePacketBuffer.toByteArray());
                        final InflaterInputStream inflate = new InflaterInputStream(inflateInput);
                        final ByteArrayOutputStream inflateBuffer = new ByteArrayOutputStream(16384);
                        while ((len = inflate.read(buffer)) != -1) {
                            inflateBuffer.write(buffer, 0, len);
                        }
                        inflate.close();
                        final byte[] subData = inflateBuffer.toByteArray();
                        switch (state >> 2) {
                            case 0: {
                                processInitPacket(subData);
                                break;
                            }
                            case 1: {
                                processChatPacket(subData);
                                break;
                            }
                            case 2: {
                                processConsolePacket(subData);
                                break;
                            }
                        }
                        this.largePacketBuffer = null;
                        break;
                    }
                    break;
                }
                case 1: {
                    final TileEntity te = DataEncoder.decode(is, TileEntity.class);
                    final int event = is.readInt();
                    if (te instanceof INetworkTileEntityEventListener) {
                        ((INetworkTileEntityEventListener) te).onNetworkEvent(event);
                        break;
                    }
                    break;
                }
                case 2: {
                    final UUID uuid = new UUID(is.readLong(), is.readLong());
                    final ItemStack stack = DataEncoder.decode(is, ItemStack.class);
                    final int event2 = is.readInt();
                    final World world = Minecraft.getMinecraft().theWorld;
                    for (final Object obj : world.playerEntities) {
                        final EntityPlayer entityPlayer = (EntityPlayer) obj;
                        if (uuid.equals(entityPlayer.getGameProfile().getId())) {
                            if (stack.getItem() instanceof INetworkItemEventListener) {
                                ((INetworkItemEventListener) stack.getItem()).onNetworkEvent(stack, entityPlayer, event2);
                                break;
                            }
                            break;
                        }
                    }
                    break;
                }
                case 4: {
                    final EntityPlayer entityPlayer2 = IC2.platform.getPlayerInstance();
                    final boolean isAdmin = is.readByte() != 0;
                    switch (is.readByte()) {
                        case 0: {
                            final TileEntity te2 = DataEncoder.decode(is, TileEntity.class);
                            final int windowId = is.readInt();
                            if (te2 instanceof IHasGui) {
                                IC2.platform.launchGuiClient(entityPlayer2, (IHasGui) te2, isAdmin);
                            }
                            entityPlayer2.openContainer.windowId = windowId;
                            break;
                        }
                        case 1: {
                            final int currentItemPosition = is.readInt();
                            final int windowId = is.readInt();
                            if (currentItemPosition != entityPlayer2.inventory.currentItem) {
                                return;
                            }
                            final ItemStack currentItem = entityPlayer2.inventory.getCurrentItem();
                            if (currentItem != null && currentItem.getItem() instanceof IHandHeldInventory) {
                                IC2.platform.launchGuiClient(entityPlayer2, ((IHandHeldInventory) currentItem.getItem()).getInventory(entityPlayer2, currentItem), isAdmin);
                            }
                            entityPlayer2.openContainer.windowId = windowId;
                            break;
                        }
                    }
                    break;
                }
                case 5: {
                    final int dimensionId = is.readInt();
                    final double x = is.readDouble();
                    final double y = is.readDouble();
                    final double z = is.readDouble();
                    final World world2 = Minecraft.getMinecraft().theWorld;
                    if (world2.provider.dimensionId != dimensionId) {
                        return;
                    }
                    world2.playSoundEffect(x, y, z, "random.explode", 4.0f, (1.0f + (world2.rand.nextFloat() - world2.rand.nextFloat()) * 0.2f) * 0.7f);
                    world2.spawnParticle("hugeexplosion", x, y, z, 0.0, 0.0, 0.0);
                    break;
                }
                case 6: {
                    throw new RuntimeException("Received unexpected RPC packet");
                }
                case 7: {
                    final int dimensionId = is.readInt();
                    final int x2 = is.readInt();
                    final int y2 = is.readInt();
                    final int z2 = is.readInt();
                    final String componentName = is.readUTF();
                    final int dataLen = is.readInt();
                    if (dataLen > 65536) {
                        throw new IOException("data length limit exceeded");
                    }
                    final byte[] data = new byte[dataLen];
                    is.readFully(data);
                    final World world2 = Minecraft.getMinecraft().theWorld;
                    if (world2.provider.dimensionId != dimensionId) {
                        return;
                    }
                    final TileEntity teRaw = world2.getTileEntity(x2, y2, z2);
                    if (!(teRaw instanceof TileEntityBlock)) {
                        return;
                    }
                    final TileEntityComponent component = ((TileEntityBlock) teRaw).getComponent(componentName);
                    if (component == null) {
                        return;
                    }
                    final DataInputStream dataIs = new DataInputStream(new ByteArrayInputStream(data));
                    component.onNetworkUpdate(dataIs);
                    break;
                }
                default: {
                    isRaw.reset();
                    super.onPacketData(isRaw, player);
                    break;
                }
            }
        } catch (IOException e) {
            IC2.log.warn(LogCategory.Network, e, "Network read failed.");
        }
    }
}
