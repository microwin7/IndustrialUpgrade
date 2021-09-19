package com.denfop.integration.de;

import com.brandon3055.draconicevolution.client.utill.CustomResourceLocation;
import com.brandon3055.draconicevolution.common.utills.LogHelper;
import com.denfop.Constants;
import com.google.gson.stream.JsonWriter;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.FolderResourcePack;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class ResourceHandler {

    private static final Map<String, ResourceLocation> cachedResources = new HashMap<>();

    public static final Map<String, CustomResourceLocation> downloadedImages = new HashMap<>();

    private static File saveFolder;

    private static File imagesFolder;

    private static DownloadThread downloadThread;


    @SubscribeEvent
    public void tick(TickEvent.ClientTickEvent event) {
        if (downloadThread != null && downloadThread.isFinished) {
            FMLCommonHandler.instance().bus().unregister(this);
            addRSPack((event != null));
            downloadThread = null;
        }
    }

    public static class DownloadThread extends Thread {
        private final List<String> imageURLs;

        private boolean isFinished = false;

        public DownloadThread(List<String> imageURLs) {
            this.imageURLs = imageURLs;
        }

        public void run() {
            for (String s : this.imageURLs) {

                if (checkExistence(s))
                    try {
                        URL url = new URL(s);
                        String fileName = url.getFile();
                        BufferedImage bi = ImageIO
                                .read(new File(ResourceHandler.getImagesFolder(), FilenameUtils.getName(fileName)));
                        ResourceHandler.downloadedImages.put(FilenameUtils.getName(fileName),
                                new CustomResourceLocation(FilenameUtils.getName(fileName), bi.getWidth(),
                                        bi.getHeight()));
                    } catch (IOException e) {
                        LogHelper.error("Image Read Failed");
                        e.printStackTrace();
                    }
            }
            this.isFinished = true;
        }

        private static boolean checkExistence(String urlS) {
            try {
                URL url = new URL(urlS);
                String fileName = url.getFile();
                return Arrays.asList(Objects.requireNonNull(ResourceHandler.getImagesFolder().list()))
                        .contains(FilenameUtils.getName(fileName));
            } catch (MalformedURLException e) {
                LogHelper.error("Unable to check files existence. Invalid URL: " + urlS);
                e.printStackTrace();
                return false;
            }
        }

    }

    private static void addRSPack(boolean refreash) {
        File rspack = new File(getConfigFolder(), "/resources");
        if (!rspack.exists())
            return;
        if (!Arrays.asList(Objects.requireNonNull(rspack.list())).contains("pack.mcmeta"))
            try {
                JsonWriter writer = new JsonWriter(new FileWriter(new File(rspack, "pack.mcmeta")));
                writer.beginObject();
                writer.name("pack");
                writer.beginObject();
                writer.name("pack_format").value(1L);
                writer.name("description").value("Draconic Evolution GUI Images");
                writer.endObject();
                writer.endObject();
                writer.close();
            } catch (IOException e) {
                LogHelper.error("Error creating pack.mcmeta");
                e.printStackTrace();
            }
        Field f = ReflectionHelper.findField(Minecraft.class,
                "defaultResourcePacks", "field_110449_ao");
        f.setAccessible(true);
        try {
            List<FolderResourcePack> defaultResourcePacks = (List) f.get(Minecraft.getMinecraft());
            defaultResourcePacks.add(new FolderResourcePack(rspack));
            f.set(Minecraft.getMinecraft(), defaultResourcePacks);
            LogHelper.info("RS Added");
            if (refreash)
                Minecraft.getMinecraft().refreshResources();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static File getConfigFolder() {
        return saveFolder;
    }

    public static File getImagesFolder() {
        if (imagesFolder == null)
            imagesFolder = new File(getConfigFolder(), "/resources/assets/draconicevolution/textures/gui/manualimages");

        return imagesFolder;
    }

    public static void bindTexture(ResourceLocation texture) {
        (Minecraft.getMinecraft()).renderEngine.bindTexture(texture);
    }

    public static ResourceLocation getResource(String rs) {
        if (!cachedResources.containsKey(rs))
            cachedResources.put(rs, new ResourceLocation(Constants.TEXTURES + ":" + rs));
        return cachedResources.get(rs);
    }

    public static ResourceLocation getResource(String rs, String rs1) {
        if (!cachedResources.containsKey(rs)) {
            cachedResources.put(rs, new ResourceLocation(rs1 + rs));
        }

        return cachedResources.get(rs);
    }

    public static void bindResource(String rs) {
        bindTexture(getResource(rs));
    }
}
