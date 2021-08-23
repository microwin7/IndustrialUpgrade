//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop.item.bags;

import com.denfop.container.ContainerBags;
import com.denfop.gui.GuiBags;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.core.ContainerBase;
import ic2.core.item.tool.HandHeldInventory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class HandHeldBags extends HandHeldInventory {
    public final int inventorySize;
    public final ItemStack itemStack1;

    public HandHeldBags(EntityPlayer entityPlayer, ItemStack itemStack1, int inventorySize) {
        super(entityPlayer, itemStack1, inventorySize);
        this.inventorySize=inventorySize;
        this.itemStack1=itemStack1;
    }

    public ContainerBase<HandHeldBags> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerBags(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GuiBags(new ContainerBags(entityPlayer, this),itemStack1);
    }

    public String getInventoryName() {
        return "toolbox";
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {

        if(ElectricItem.manager.canUse(itemStack1,50)) {
            return itemstack != null;
        }else{
            return false;
        }
    }
}
