package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerKineticGenerator;
import com.denfop.gui.GUIKineticGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IKineticSource;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityKineticGenerator extends TileEntityInventory implements IEnergySource, IHasGui, INetworkTileEntityEventListener {
    private final int tier;
    private final String name;
    private final int maxEUStorage = 200000;
    private final double productionpeerkineticunit = 0.25D * (double) ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/Kinetic");
    public int updateTicker;
    public double EUstorage = 0.0D;
    public boolean addedToEnergyNet = false;
    public AudioSource audioSource;
    private double guiproduction = 0.0D;
    private double production = 0.0D;

    public TileEntityKineticGenerator(int tier, String name) {
        this.updateTicker = IC2.random.nextInt(this.getTickRate());
        this.tier = tier;
        this.name = name;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal(name);
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean newActive = this.gainEnergy();
        if (this.updateTicker++ >= this.getTickRate()) {
            this.guiproduction = this.production;
            this.updateTicker = 0;
        }

        if (this.EUstorage > (double) this.maxEUStorage) {
            this.EUstorage = this.maxEUStorage;
        }

        if (this.getActive() != newActive) {
            this.setActive(newActive);
        }

    }

    protected boolean gainEnergy() {
        ForgeDirection dir = ForgeDirection.getOrientation(this.getFacing());
        TileEntity te = this.worldObj.getTileEntity(this.xCoord + dir.offsetX, this.yCoord + dir.offsetY, this.zCoord + dir.offsetZ);
        int receivedkinetic;
        if (te instanceof IKineticSource) {
            int kineticbandwith = ((IKineticSource) te).maxrequestkineticenergyTick(dir.getOpposite());
            double freeEUstorage = (double) this.maxEUStorage - this.EUstorage;
            if (freeEUstorage > 0.0D && freeEUstorage < this.productionpeerkineticunit * (double) kineticbandwith) {
                freeEUstorage = this.productionpeerkineticunit * (double) kineticbandwith;
            }

            if (freeEUstorage >= this.productionpeerkineticunit * (double) kineticbandwith) {
                receivedkinetic = ((IKineticSource) te).requestkineticenergy(dir.getOpposite(), kineticbandwith);
                if (receivedkinetic != 0) {
                    this.production = (double) receivedkinetic * this.productionpeerkineticunit;
                    initiate(0);
                    this.EUstorage += this.production;
                    return true;
                }
            } else
                initiate(2);
        }

        this.production = 0.0D;
        return false;
    }

    public ContainerBase<TileEntityKineticGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerKineticGenerator(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIKineticGenerator(new ContainerKineticGenerator(entityPlayer, this));
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.EUstorage = nbttagcompound.getDouble("EUstorage");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("EUstorage", this.EUstorage);
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return Math.min(this.EUstorage, EnergyNet.instance.getPowerFromTier(this.getSourceTier()));
    }

    public int getSourceTier() {
        return tier;
    }

    public void drawEnergy(double amount) {
        this.EUstorage -= amount;
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public int gaugeEUStorageScaled(int i) {
        return (int) (this.EUstorage * (double) i / (double) this.maxEUStorage);
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return this.getFacing() != side;
    }

    public void setFacing(short side) {
        super.setFacing(side);
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

    public float getWrenchDropRate() {
        return 0.9F;
    }


    public double getproduction() {
        return this.guiproduction;
    }

    public int getTickRate() {
        return 20;
    }

    public String getStartSoundFile() {
        return "Machines/kineticgenerator.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }


    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    @Override
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
}
