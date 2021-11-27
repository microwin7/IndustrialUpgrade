package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityMagnetGenerator extends TileEntityInventory
        implements INetworkTileEntityEventListener, IEnergySource {

    private final double maxEnergy;
    private final double production;
    public AudioSource audioSource;

    public boolean addedToEnergyNet = false;
    private double energy;

    public TileEntityMagnetGenerator() {
        super();
        this.maxEnergy = 500000;
        this.energy = 0;
        production = 0.5;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.energy = nbttagcompound.getDouble("energy");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("energy", this.energy);
    }

    public int getSourceTier() {
        return 1;
    }

    public void updateEntityServer() {

        super.updateEntityServer();

        if (this.energy + production < maxEnergy) {
            this.energy += production;
            setActive(true);
            initiate(0);
        } else {
            initiate(2);
            setActive(false);
        }
        if (worldObj.provider.getWorldTime() % 60 == 0)
            initiate(2);
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }


    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public void drawEnergy(double amount) {
        this.energy -= amount;
    }


    public double getOfferedEnergy() {

        return Math.min(this.production, this.energy);
    }


    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }


    public float getWrenchDropRate() {
        return 0.85F;
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/magnet_generator.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public void onNetworkEvent(int event) {
        if (this.audioSource == null && getStartSoundFile() != null)
            this.audioSource = IUCore.audioManager.createSource(this, getStartSoundFile());
        switch (event) {
            case 0:
                if (this.audioSource != null)
                    this.audioSource.play();
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (getInterruptSoundFile() != null)
                        IUCore.audioManager.playOnce(this, getInterruptSoundFile());
                }
                break;
            case 2:
                if (this.audioSource != null)
                    this.audioSource.stop();
                break;


        }
    }


    @Override
    public String getInventoryName() {
        // TODO Auto-generated method stub
        return null;
    }

}
