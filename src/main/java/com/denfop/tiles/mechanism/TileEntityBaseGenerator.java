package com.denfop.tiles.mechanism;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotConsumableFuel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityBaseGenerator extends TileEntityInventory implements IEnergySource, IHasGui {
    public final int tier;
    public final double power;
    public final short maxStorage;
    public final int production;
    public final InvSlotCharge chargeSlot;
    public final InvSlotConsumableFuel fuelSlot = new InvSlotConsumableFuel(this, "fuel", 1, 1, false);
    public int fuel = 0;
    public double storage = 0.0D;
    public int ticksSinceLastActiveUpdate;
    public int activityMeter = 0;
    public boolean addedToEnergyNet = false;
    public AudioSource audioSource;

    public TileEntityBaseGenerator(int production1, int tier, int maxStorage1) {
        this.production = production1;
        this.tier = tier;
        this.power = EnergyNet.instance.getPowerFromTier(tier);
        this.maxStorage = (short) maxStorage1;
        this.ticksSinceLastActiveUpdate = IC2.random.nextInt(256);
        this.chargeSlot = new InvSlotCharge(this, 0, 1);

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        try {
            this.fuel = nbttagcompound.getInteger("fuel");
        } catch (Exception var4) {
            this.fuel = nbttagcompound.getShort("fuel");
        }

        try {
            this.storage = nbttagcompound.getDouble("storage");
        } catch (Exception var3) {
            this.storage = nbttagcompound.getShort("storage");
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("fuel", this.fuel);
        nbttagcompound.setDouble("storage", this.storage);
    }

    public int gaugeStorageScaled(int i) {
        return (int) (this.storage * (double) i / (double) this.maxStorage);
    }

    public abstract int gaugeFuelScaled(int var1);

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
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }

        super.onUnloaded();
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.needsFuel()) {
            needsInvUpdate = this.gainFuel();
        }

        boolean newActive = this.gainEnergy();
        if (this.storage > (double) this.maxStorage) {
            this.storage = this.maxStorage;
        }

        if (this.storage >= 1.0D && this.chargeSlot.get() != null) {
            double used = ElectricItem.manager.charge(this.chargeSlot.get(), this.storage, 1, false, false);
            this.storage -= used;
            if (used > 0.0D) {
                needsInvUpdate = true;
            }
        }

        if (needsInvUpdate) {
            this.markDirty();
        }

        if (!this.delayActiveUpdate()) {
            this.setActive(newActive);
        } else {
            if (this.ticksSinceLastActiveUpdate % 256 == 0) {
                this.setActive(this.activityMeter > 0);
                this.activityMeter = 0;
            }

            if (newActive) {
                ++this.activityMeter;
            } else {
                --this.activityMeter;
            }

            ++this.ticksSinceLastActiveUpdate;
        }

    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            this.storage += this.production;
            --this.fuel;
            return true;
        } else {
            return false;
        }
    }

    public boolean isConverting() {
        return this.fuel > 0 && this.storage + (double) this.production <= (double) this.maxStorage;
    }

    public boolean needsFuel() {
        return this.fuel <= 0 && this.storage + (double) this.production <= (double) this.maxStorage;
    }

    public abstract boolean gainFuel();

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return Math.min(this.storage, this.power);
    }

    public int getSourceTier() {
        return this.tier;
    }

    public void drawEnergy(double amount) {
        this.storage -= amount;
    }

    public abstract String getInventoryName();

    public String getOperationSoundFile() {
        return null;
    }

    public boolean delayActiveUpdate() {
        return false;
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public void onNetworkUpdate(String field) {
        if (field.equals("active") && this.prevActive != this.getActive()) {
            if (this.audioSource == null && this.getOperationSoundFile() != null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, this.getOperationSoundFile(), true, false, IC2.audioManager.getDefaultVolume());
            }

            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }

        super.onNetworkUpdate(field);
    }

    public float getWrenchDropRate() {
        return 0.9F;
    }
}
