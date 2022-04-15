package com.denfop.audio;

import com.denfop.IUCore;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.ReflectionUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.common.MinecraftForge;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

@SuppressWarnings("ALL")
@SideOnly(Side.CLIENT)
public final class AudioManagerClient extends AudioManager {
    public final float fadingDistance;
    private final Map<WeakObject, List<AudioSource>> objectToAudioSourceMap;
    private boolean enabled;
    private int maxSourceCount;
    private SoundManager soundManager;
    private Field soundManagerLoaded;
    private volatile Thread initThread;
    private SoundSystem soundSystem;
    private float masterVolume;
    private int nextId;

    public AudioManagerClient() {
        this.fadingDistance = 16.0f;
        this.enabled = true;
        this.maxSourceCount = 32;
        this.soundSystem = null;
        this.masterVolume = 0.5f;
        this.nextId = 0;
        this.objectToAudioSourceMap = new HashMap<>();
    }

    private static SoundManager getSoundManager() {
        final SoundHandler handler = Minecraft.getMinecraft().getSoundHandler();
        return (SoundManager) ReflectionUtil.getValue(handler, SoundManager.class);
    }

    private static SoundSystem getSoundSystem(final SoundManager soundManager) {
        return (SoundSystem) ReflectionUtil.getValue(soundManager, SoundSystem.class);
    }

    private static String getSourceName(final int id) {
        return "asm_snd" + id;
    }

    @Override
    public void initialize() {
        this.enabled = ConfigUtil.getBool(MainConfig.get(), "misc/enableIc2Audio");
        this.maxSourceCount = ConfigUtil.getInt(MainConfig.get(), "misc/maxAudioSourceCount");
        if (this.maxSourceCount <= 6) {
            IC2.log.info(LogCategory.Audio, "The audio source limit is too low to enable IC2 sounds.");
            this.enabled = false;
        }
        if (!this.enabled) {
            IC2.log.debug(LogCategory.Audio, "Sounds disabled.");
            return;
        }
        if (this.maxSourceCount < 6) {
            this.enabled = false;
            return;
        }
        IC2.log.debug(LogCategory.Audio, "Using %d audio sources.", this.maxSourceCount);
        SoundSystemConfig.setNumberStreamingChannels(4);
        SoundSystemConfig.setNumberNormalChannels(this.maxSourceCount - 4);
        this.soundManagerLoaded = ReflectionUtil.getField(SoundManager.class, Boolean.TYPE);
        if (this.soundManagerLoaded == null) {
            IC2.log.warn(LogCategory.Audio, "Can't find SoundManager.loaded, IC2 audio disabled.");
            this.enabled = false;
            return;
        }
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onSoundSetup(final SoundLoadEvent event) {
        if (!this.enabled) {
            return;
        }
        this.objectToAudioSourceMap.clear();
        final Thread thread = this.initThread;
        if (thread != null) {
            thread.interrupt();
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        IC2.log.debug(LogCategory.Audio, "IC2 audio starting.");
        this.soundSystem = null;
        this.soundManager = getSoundManager();
        (this.initThread = new Thread(() -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    boolean loaded;
                    try {
                        loaded = AudioManagerClient.this.soundManagerLoaded.getBoolean(AudioManagerClient.this.soundManager);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    if (loaded) {
                        AudioManagerClient.this.soundSystem = getSoundSystem(AudioManagerClient.this.soundManager);
                        if (AudioManagerClient.this.soundSystem == null) {
                            IC2.log.warn(LogCategory.Audio, "IC2 audio unavailable.");
                            AudioManagerClient.this.enabled = false;
                            break;
                        }
                        IC2.log.debug(LogCategory.Audio, "IC2 audio ready.");
                        break;
                    } else {
                        Thread.sleep(100L);
                    }
                }
            } catch (InterruptedException ignored) {
            }
            AudioManagerClient.this.initThread = null;
        }, "IC2 audio init thread")).setDaemon(true);
        this.initThread.start();
    }

    @Override
    public void onTick() {
        if (!this.enabled || this.valid()) {
            return;
        }
        IUCore.proxy.profilerStartSection("UpdateMasterVolume");
        final float configSoundVolume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.MASTER);
        if (configSoundVolume != this.masterVolume) {
            this.masterVolume = configSoundVolume;
        }
        IUCore.proxy.profilerEndStartSection("UpdateSourceVolume");
        final EntityPlayer player = IC2.platform.getPlayerInstance();
        final List<WeakObject> audioSourceObjectsToRemove = new Vector<>();
        if (player == null) {
            audioSourceObjectsToRemove.addAll(this.objectToAudioSourceMap.keySet());
        } else {
            final Queue<AudioSource> validAudioSources = new PriorityQueue<>();
            for (final Map.Entry<WeakObject, List<AudioSource>> entry : this.objectToAudioSourceMap.entrySet()) {
                if (entry.getKey().isEnqueued()) {
                    audioSourceObjectsToRemove.add(entry.getKey());
                } else {
                    for (final AudioSource audioSource : entry.getValue()) {
                        audioSource.updateVolume(player);
                        if (audioSource.getRealVolume() > 0.0f) {
                            validAudioSources.add(audioSource);
                        }
                    }
                }
            }
            IUCore.proxy.profilerEndStartSection("Culling");
            int i = 0;
            while (!validAudioSources.isEmpty()) {
                if (i < this.maxSourceCount) {
                    validAudioSources.poll().activate();
                } else {
                    validAudioSources.poll().cull();
                }
                ++i;
            }
        }
        for (final WeakObject obj : audioSourceObjectsToRemove) {
            this.removeSources(obj);
        }
        IUCore.proxy.profilerEndSection();
    }

    @Override
    public AudioSource createSource(final Object obj, final String initialSoundFile) {
        return this.createSource(obj, PositionSpec.Center, initialSoundFile, false, false, IC2.audioManager.getDefaultVolume());
    }

    @Override
    public AudioSource createSource(final Object obj, final PositionSpec positionSpec, final String initialSoundFile, final boolean loop, final boolean priorized, final float volume) {
        if (!this.enabled) {
            return null;
        }
        if (this.valid()) {
            return null;
        }
        final String sourceName = getSourceName(this.nextId);
        ++this.nextId;
        final AudioSource audioSource = new AudioSourceClient(this.soundSystem, sourceName, obj, positionSpec, initialSoundFile, loop, priorized, volume);
        final WeakObject key = new WeakObject(obj);
        if (!this.objectToAudioSourceMap.containsKey(key)) {
            this.objectToAudioSourceMap.put(key, new LinkedList<>());
        }
        this.objectToAudioSourceMap.get(key).add(audioSource);
        return audioSource;
    }

    @Override
    public void removeSources(final Object obj) {
        if (this.valid()) {
            return;
        }
        WeakObject key;
        if (obj instanceof WeakObject) {
            key = (WeakObject) obj;
        } else {
            key = new WeakObject(obj);
        }
        if (!this.objectToAudioSourceMap.containsKey(key)) {
            return;
        }
        for (final AudioSource audioSource : this.objectToAudioSourceMap.get(key)) {
            audioSource.remove();
        }
        this.objectToAudioSourceMap.remove(key);
    }

    @Override
    public void playOnce(final Object obj, final String soundFile) {
        this.playOnce(obj, PositionSpec.Center, soundFile, false, IC2.audioManager.getDefaultVolume());
    }

    @Override
    public void playOnce(final Object obj, final PositionSpec positionSpec, final String soundFile, final boolean priorized, final float volume) {
        if (!this.enabled) {
            return;
        }
        if (this.valid()) {
            return;
        }
        final AudioPosition position = AudioPosition.getFrom(obj);
        if (position == null) {
            return;
        }
        URL url = AudioSource.class.getClassLoader().getResource("assets/industrialupgrade/sounds/" + soundFile);
        if (url == null)
            url = AudioSource.class.getClassLoader().getResource("ic2/sounds/" + soundFile);

        if (url == null) {
            IC2.log.warn(LogCategory.Audio, "Invalid sound file: %s.", soundFile);
            return;
        }
        final String sourceName = this.soundSystem.quickPlay(priorized, url, soundFile, false, position.x, position.y, position.z, 2, this.fadingDistance * Math.max(volume, 1.0f));
        this.soundSystem.setVolume(sourceName, this.masterVolume * Math.min(volume, 1.0f));
    }

    @Override
    public float getDefaultVolume() {
        return 1.2f;
    }

    @Override
    public float getMasterVolume() {
        return this.masterVolume;
    }

    @Override
    protected boolean valid() {
        try {
            return this.soundSystem == null || this.soundManager == null || !this.soundManagerLoaded.getBoolean(this.soundManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static class WeakObject extends WeakReference<Object> {
        public WeakObject(final Object referent) {
            super(referent);
        }

        @Override
        public boolean equals(final Object object) {
            if (object instanceof WeakObject) {
                return ((WeakObject) object).get() == this.get();
            }
            return this.get() == object;
        }

        @Override
        public int hashCode() {
            final Object object = this.get();
            if (object == null) {
                return 0;
            }
            return object.hashCode();
        }
    }
}
