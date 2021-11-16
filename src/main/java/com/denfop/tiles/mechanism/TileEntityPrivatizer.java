package com.denfop.tiles.mechanism;

import com.denfop.container.ContainerPrivatizer;
import com.denfop.gui.GUIPrivatizer;
import com.denfop.invslot.InvSlotPrivatizer;
import com.denfop.tiles.base.TileEntityElectricMachine;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityPrivatizer extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, INetworkClientTileEntityEventListener {


    public final InvSlotPrivatizer inputslot;
    public final InvSlotPrivatizer inputslotA;
    public AudioSource audioSource;

    public TileEntityPrivatizer() {
        super(0, 10, 1);


        this.inputslot = new InvSlotPrivatizer(this, "input", 3, 0, 9);
        this.inputslotA = new InvSlotPrivatizer(this, "input2", 2, 1, 1);
    }


    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack ret = super.getWrenchDrop(entityPlayer);
        if (this.inputslotA.get() != null) {
            double var8 = 0.7D;
            double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                    this.inputslot.get());
            var16.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(var16);
        }
        for (int i = 0; i < inputslot.size(); i++)
            if (this.inputslot.get(i) != null) {
                double var8 = 0.7D;
                double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                        this.inputslot.get(i));
                var16.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(var16);
            }
        return ret;
    }


    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (this.energy >= this.maxEnergy)
            return amount;
        if (this.energy + amount >= this.maxEnergy) {

            double temp = (this.maxEnergy - this.energy);
            this.energy += temp;
        } else {
            this.energy += amount;
        }

        return 0.0D;
    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

    }


    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIPrivatizer(new ContainerPrivatizer(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityPrivatizer> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityPrivatizer>) new ContainerPrivatizer(entityPlayer, this);
    }


    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public String getStartSoundFile() {
        return null;
    }

    public String getInterruptSoundFile() {
        return null;
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    @Override
    public void onNetworkEvent(int event) {
        if (this.audioSource == null && getStartSoundFile() != null)
            this.audioSource = IC2.audioManager.createSource(this, getStartSoundFile());
        switch (event) {
            case 0:
                if (this.audioSource != null)
                    this.audioSource.play();
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (getInterruptSoundFile() != null)
                        IC2.audioManager.playOnce(this, getInterruptSoundFile());
                }
                break;
            case 2:
                if (this.audioSource != null)
                    this.audioSource.stop();
                break;
        }
    }

    @Override
    public void onGuiClosed(EntityPlayer arg0) {
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockModuleMachine.name");
    }


    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        if (!this.inputslotA.isEmpty()) {
            NBTTagCompound nbt = ModUtils.nbt(this.inputslotA.get());
            for (int i = 0; i < this.inputslot.size(); i++) {
                if (this.inputslot.get(i) != null) {
                    NBTTagCompound nbt1 = ModUtils.nbt(this.inputslot.get(i));
                    nbt.setString("player_" + i, nbt1.getString("name"));
                }
            }
        }


    }

}
