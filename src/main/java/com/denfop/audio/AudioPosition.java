package com.denfop.audio;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.lang.ref.WeakReference;

public class AudioPosition {
    public final float x;
    public final float y;
    public final float z;
    private final WeakReference<World> worldRef;

    public AudioPosition(final World world, final float x1, final float y1, final float z1) {
        this.worldRef = new WeakReference<>(world);
        this.x = x1;
        this.y = y1;
        this.z = z1;
    }

    public static AudioPosition getFrom(final Object obj) {
        if (obj instanceof AudioPosition) {
            return (AudioPosition) obj;
        }
        if (obj instanceof Entity) {
            final Entity e = (Entity) obj;
            return new AudioPosition(e.worldObj, (float) e.posX, (float) e.posY, (float) e.posZ);
        }
        if (obj instanceof TileEntity) {
            final TileEntity te = (TileEntity) obj;
            return new AudioPosition(te.getWorldObj(), te.xCoord + 0.5f, te.yCoord + 0.5f, te.zCoord + 0.5f);
        }
        return null;
    }

    public World getWorld() {
        return this.worldRef.get();
    }
}
