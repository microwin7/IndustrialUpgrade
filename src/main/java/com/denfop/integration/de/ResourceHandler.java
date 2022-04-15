package com.denfop.integration.de;

import com.denfop.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ResourceHandler {

    private static final Map<String, ResourceLocation> cachedResources = new HashMap<>();


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
