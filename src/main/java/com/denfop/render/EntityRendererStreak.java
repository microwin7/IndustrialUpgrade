package com.denfop.render;

import com.denfop.Constants;
import com.denfop.items.armour.ItemArmorImprovemedQuantum;
import com.denfop.utils.StreakLocation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

public class EntityRendererStreak extends Render<EntityStreak> {

    private static final ResourceLocation texture = new ResourceLocation(Constants.TEXTURES_ITEMS + "effect.png");
    public final int[] red = {255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 240, 222, 186, 150, 124, 96, 67, 40, 27, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 34, 56, 78, 102, 121, 145, 176, 201, 218, 230, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255};
    public final int[] green = {0, 24, 36, 54, 72, 96, 120, 145, 172, 192, 216, 234, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 234, 214, 195, 176, 153, 137, 112, 94, 86, 55, 31, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public final int[] blue = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 32, 45, 68, 78, 103, 118, 138, 151, 178, 205, 221, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 255, 240, 228, 208, 186, 165, 149, 132, 115, 102, 97, 76, 53, 32, 15, 0};

    public EntityRendererStreak(final RenderManager renderManager) {
        super(renderManager);
    }

    public static float clamp_float(float p_76131_0_, float p_76131_1_, float p_76131_2_) {
        return p_76131_0_ < p_76131_1_ ? p_76131_1_ : (Math.min(p_76131_0_, p_76131_2_));
    }

    public void doRender(EntityStreak entity, double par2, double par3, double par4, float par5, float par6) {
        renderStreak(entity, par6);
    }

    protected ResourceLocation getEntityTexture(EntityStreak p_110775_1_) {

        return texture;
    }

    private void renderStreak(EntityStreak entity, float par6) {
        if (entity.parent instanceof AbstractClientPlayer && !entity.isInvisible()) {
            AbstractClientPlayer player = (AbstractClientPlayer) entity.parent;
            Minecraft mc = Minecraft.getMinecraft();
            if (!entity.isInvisible() && (player != mc.player || mc.gameSettings.thirdPersonView != 0)) {
                if (player.inventory.armorInventory.get(2).isEmpty()) {
                    return;
                }
                if (!(player.inventory.armorInventory.get(2).getItem() instanceof ItemArmorImprovemedQuantum)) {
                    return;
                }
                ArrayList<StreakLocation> loc = EventDarkQuantumSuitEffect
                        .getPlayerStreakLocationInfo(player);
                GL11.glPushMatrix();
                GL11.glDisable(2884);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glShadeModel(7425);
                float startGrad = 5.0F - par6;
                float endGrad = 20.0F - par6;
                for (int i = loc.size() - 2; i >= 0; i--) {
                    int start = i;
                    StreakLocation infoStart = loc.get(i);
                    float startAlpha = (i < endGrad) ? clamp_float(0.8F * i / endGrad, 0.0F, 0.8F)
                            : ((i > (loc.size() - 2) - startGrad)
                                    ? clamp_float(0.8F * (loc.size() - 2 - i) / startGrad, 0.0F, 0.8F)
                                    : 0.8F);
                    if (player.getEntityWorld().getWorldTime() - infoStart.lastTick > 40L) {
                        break;
                    }
                    StreakLocation infoEnd = null;
                    double grad = 500.0D;
                    i--;
                    while (i >= 0) {
                        StreakLocation infoPoint = loc.get(i);
                        if (infoStart.isSprinting && loc.size() - 2 - i < 6) {
                            infoEnd = infoPoint;
                            start--;
                            i--;
                            break;
                        }
                        if (infoPoint.hasSameCoords(infoStart)) {
                            start--;
                            i--;
                            continue;
                        }
                        double grad1 = infoPoint.posZ - infoStart.posZ / (infoPoint.posX - infoStart.posX);
                        if (grad == grad1 && infoPoint.posY == infoStart.posY) {
                            infoEnd = infoPoint;
                            start--;
                            i--;
                            continue;
                        }
                        if (grad != 500.0D) {
                            break;
                        }
                        grad = grad1;
                        infoEnd = infoPoint;
                        i--;
                    }
                    if (infoEnd != null) {
                        i += 2;
                        float endAlpha = (i < endGrad) ? clamp_float(0.8F * (i - 1) / endGrad, 0.0F, 0.8F)
                                : ((i > (loc.size() - 1) - startGrad)
                                        ? clamp_float(0.8F * (loc.size() - 1 - i) / startGrad, 0.0F, 0.8F)
                                        : 0.8F);
                        double grad1 = infoStart.posX - mc.getRenderManager().renderPosX;
                        double posY = infoStart.posY - mc.getRenderManager().renderPosY;
                        double posZ = infoStart.posZ - mc.getRenderManager().renderPosZ;
                        double nextPosX = infoEnd.posX - mc.getRenderManager().renderPosX;
                        double nextPosY = infoEnd.posY - mc.getRenderManager().renderPosY;
                        double nextPosZ = infoEnd.posZ - mc.getRenderManager().renderPosZ;


                        Tessellator tessellator = Tessellator.getInstance();
                        GL11.glPushMatrix();
                        GL11.glTranslated(grad1, posY, posZ);
                        int ii = entity.getBrightnessForRender();
                        int j = ii % 65536;
                        int k = ii / 65536;
                        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
                        RenderHelper.disableStandardItemLighting();
                        GL11.glDisable(2896);
                        mc.renderEngine.bindTexture(texture);
                        net.minecraft.nbt.NBTTagCompound nbt = player.getEntityData();
                        double red = nbt.getDouble("Red");
                        double green = nbt.getDouble("Green");
                        double blue = nbt.getDouble("Blue");
                        boolean rgb = nbt.getBoolean("RGB");
                        //   tessellator.startDrawingQuads();
                        BufferBuilder bufferbuilder = tessellator.getBuffer();
                        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
                        if (rgb) {
                            red = this.red[(int) (player.getEntityWorld().provider.getWorldTime() % this.red.length)];
                            green = this.green[(int) (player.getEntityWorld().provider.getWorldTime() % this.red.length)];
                            blue = this.blue[(int) (player.getEntityWorld().provider.getWorldTime() % this.red.length)];

                        }


                        bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(infoStart.startU, 1.0D).endVertex();
                        bufferbuilder.pos(0.0D, (0.0F + infoStart.height), 0.0D).tex(infoStart.startU, 0.0D).endVertex();

                        double endTex = infoEnd.startU - start + i;
                        if (endTex > infoStart.startU) {
                            endTex--;
                        }
                        double distX = infoStart.posX - infoEnd.posX;
                        double distZ = infoStart.posZ - infoEnd.posZ;

                        double scales = Math.sqrt(distX * distX + distZ * distZ) / infoStart.height;
                        while (scales > 1.0D) {
                            endTex++;
                            scales--;
                        }
                        double pos = nextPosX - grad1;
                        double pos1 = nextPosZ - posZ;

                        if (pos >= 6) {
                            pos = 6;
                        }
                        if (pos1 >= 6) {
                            pos1 = 6;
                        }
                        if (pos <= -6) {
                            pos = -6;
                        }
                        if (pos1 <= -6) {
                            pos1 = -6;
                        }
                        if (endTex >= 24) {
                            endTex = 24;
                        }
                        if (endTex <= -24) {
                            endTex = -24;
                        }

                        bufferbuilder.pos(pos, nextPosY - posY + infoEnd.height, pos1).tex(
                                endTex, 0.0D).endVertex();
                        bufferbuilder.pos(pos, nextPosY - posY, pos).tex(endTex, 1.0D).endVertex();
                        tessellator.draw();
                        GL11.glEnable(2896);
                        RenderHelper.enableStandardItemLighting();
                        GL11.glPopMatrix();
                    }
                }
                GL11.glShadeModel(7424);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(2884);
                GL11.glPopMatrix();
            }
        }
    }

}
