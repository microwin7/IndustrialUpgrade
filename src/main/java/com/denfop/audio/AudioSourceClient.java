package com.denfop.audio;

import com.denfop.IUCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import org.jetbrains.annotations.NotNull;
import paulscode.sound.SoundSystem;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URL;

@SuppressWarnings("ALL")
@SideOnly(Side.CLIENT)
public final class AudioSourceClient extends AudioSource implements Comparable<AudioSourceClient> {
    private final SoundSystem soundSystem;
    private final Reference<Object> obj;
    private final PositionSpec positionSpec;
    private String sourceName;
    private boolean valid;
    private boolean culled;
    private AudioPosition position;
    private float configuredVolume;
    private float realVolume;
    private boolean isPlaying;

    public AudioSourceClient(final SoundSystem soundSystem1, final String sourceName1, final Object obj1, final PositionSpec positionSpec1, final String initialSoundFile, final boolean loop, final boolean priorized, final float volume) {
        this.valid = false;
        this.culled = false;
        this.isPlaying = false;
        this.soundSystem = soundSystem1;
        this.sourceName = sourceName1;
        this.obj = new WeakReference<>(obj1);
        this.positionSpec = positionSpec1;
        URL url = AudioSource.class.getClassLoader().getResource("assets/industrialupgrade/sounds/" + initialSoundFile);
        if (url == null)
            url = AudioSource.class.getClassLoader().getResource("ic2/sounds/" + initialSoundFile);
        if (url == null) {
            IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", initialSoundFile);
            return;
        }

        this.position = AudioPosition.getFrom(obj1);
        soundSystem1.newSource(priorized, sourceName1, url, initialSoundFile, loop, this.position.x, this.position.y, this.position.z, 0, ((AudioManagerClient) IUCore.audioManager).fadingDistance * Math.max(volume, 1.0f));
        this.valid = true;
        this.setVolume(volume);
    }

    @Override
    public int compareTo(final @NotNull AudioSourceClient x) {
        if (this.culled) {
            return (int) ((this.realVolume * 0.9f - x.realVolume) * 128.0f);
        }
        return (int) ((this.realVolume - x.realVolume) * 128.0f);
    }

    @Override
    public void remove() {
        if (this.check()) {
            return;
        }
        if (this.sourceName == null) {
            return;
        }
        this.stop();
        this.soundSystem.removeSource(this.sourceName);
        this.sourceName = null;
        this.valid = false;
    }

    @Override
    public void play() {
        if (this.check()) {
            return;
        }
        if (this.isPlaying) {
            return;
        }
        this.isPlaying = true;
        if (this.culled) {
            return;
        }
        this.soundSystem.play(this.sourceName);
    }

    @Override
    public void stop() {
        if (this.check() || !this.isPlaying) {
            return;
        }
        this.isPlaying = false;
        if (this.culled) {
            return;
        }
        this.soundSystem.stop(this.sourceName);
    }

    @Override
    public void cull() {
        if (this.check() || this.culled) {
            return;
        }
        this.soundSystem.cull(this.sourceName);
        this.culled = true;
    }

    @Override
    public void activate() {
        if (this.check() || !this.culled) {
            return;
        }
        this.soundSystem.activate(this.sourceName);
        this.culled = false;
        if (this.isPlaying) {
            this.isPlaying = false;
            this.play();
        }
    }

    @Override
    public float getRealVolume() {
        return this.realVolume;
    }

    private void setVolume(final float volume) {
        if (this.check()) {
            return;
        }
        this.configuredVolume = volume;
        this.soundSystem.setVolume(this.sourceName, 0.001f);
    }

    @Override
    public void updateVolume(final EntityPlayer player) {
        if (this.check() || !this.isPlaying) {
            this.realVolume = 0.0f;
            return;
        }
        final float maxDistance = ((AudioManagerClient) IUCore.audioManager).fadingDistance * Math.max(this.configuredVolume, 1.0f);
        final float rolloffFactor = 1.0f;
        final float referenceDistance = 1.0f;
        float x = (float) player.posX;
        float y = (float) player.posY;
        float z = (float) player.posZ;
        float distance;
        if (this.position != null && this.position.getWorld() == player.worldObj) {
            final float deltaX = this.position.x - x;
            final float deltaY = this.position.y - y;
            final float deltaZ = this.position.z - z;
            distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
        } else {
            distance = Float.POSITIVE_INFINITY;
        }
        if (distance > maxDistance) {
            this.realVolume = 0.0f;
            this.cull();
            return;
        }
        if (distance < referenceDistance) {
            distance = referenceDistance;
        }
        final float gain = 1.0f - rolloffFactor * (distance - referenceDistance) / (maxDistance - referenceDistance);
        float newRealVolume = gain * this.configuredVolume * IC2.audioManager.getMasterVolume();
        final float dx = (this.position.x - x) / distance;
        final float dy = (this.position.y - y) / distance;
        final float dz = (this.position.z - z) / distance;
        if (newRealVolume > 0.1) {
            for (int i = 0; i < distance; ++i) {
                final int xi = Util.roundToNegInf(x);
                final int yi = Util.roundToNegInf(y);
                final int zi = Util.roundToNegInf(z);
                final Block block = player.worldObj.getBlock(xi, yi, zi);
                if (!block.isAir(player.worldObj, xi, yi, zi)) {
                    if (block.isNormalCube(player.worldObj, xi, yi, zi)) {
                        newRealVolume *= 0.6f;
                    } else {
                        newRealVolume *= 0.8f;
                    }
                }
                x += dx;
                y += dy;
                z += dz;
            }
        }
        if (Math.abs(this.realVolume / newRealVolume - 1.0f) > 0.06) {
            this.soundSystem.setVolume(this.sourceName, IUCore.audioManager.getMasterVolume() * Math.min(newRealVolume, 1.0f));
        }
        this.realVolume = newRealVolume;
    }

    private boolean check() {
        return (!this.valid || IUCore.audioManager.valid()) && (!(this.valid = false));
    }
}
