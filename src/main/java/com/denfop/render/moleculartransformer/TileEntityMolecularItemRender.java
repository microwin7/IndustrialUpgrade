package com.denfop.render.moleculartransformer;

import com.denfop.utils.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class TileEntityMolecularItemRender implements IItemRenderer {

    @Override
    public boolean handleRenderType(ItemStack is, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack is, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack is, Object... data) {
        GL11.glPushMatrix();
        GL11.glTranslatef(0.5F, 0.8F, 0.5F);
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        NBTTagCompound nbt = ModUtils.nbt(is);
        byte redstoneMode = nbt.getByte("redstoneMode");
        switch (redstoneMode) {
            case 0:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture);
                break;
            case 1:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture1);
                break;
            case 2:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture2);
                break;
            case 3:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture3);
                break;
            case 4:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture4);
                break;
            case 5:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture5);
                break;
            case 6:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture6);
                break;
            case 7:
                Minecraft.getMinecraft().renderEngine.bindTexture(TileEntityMolecularRender.texture7);
                break;
        }
        TileEntityMolecularRender.model.renderAll();
        GL11.glPopMatrix();
    }

}