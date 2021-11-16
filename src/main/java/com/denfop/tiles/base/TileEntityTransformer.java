package com.denfop.tiles.base;

import com.denfop.container.ContainerTransformer;
import com.denfop.gui.GUITransformer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityTransformer extends TileEntityInventory implements IEnergySink, IEnergySource, IHasGui, INetworkClientTileEntityEventListener {
    public final int tier;
    public final double maxStorage;
    public int mode;
    public double power;
    public double energy;

    public boolean redstone;
    public boolean addedToEnergyNet;
    private boolean needrefresh;
    private double inputflow;
    private double outputflow;

    public TileEntityTransformer(int tier) {
        this.energy = 0.0D;
        this.redstone = false;
        this.needrefresh = false;
        this.inputflow = 0.0D;
        this.outputflow = 0.0D;
        this.addedToEnergyNet = false;
        this.tier = tier;
        this.power = EnergyNet.instance.getPowerFromTier(tier);
        this.maxStorage = this.power * 8.0D;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("Transformer" + this.getTyp() + ".name");
    }

    public String getTyp() {
        switch (this.tier) {
            case 5:
                return "UMV";
            case 6:
                return "UHV";
            case 7:
                return "UEV";
            case 8:
                return "UMHV";
            case 9:
                return "UMEV";
            case 10:
                return "UHEV";
            case 11:
                return "HEEV";
        }
        return "";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.mode = nbttagcompound.getInteger("mode");
        this.energy = nbttagcompound.getDouble("energy");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("energy", this.energy);
        nbttagcompound.setInteger("mode", this.mode);
    }

    public int getMode() {
        return this.mode;
    }

    public void onNetworkEvent(EntityPlayer player, int event) {
        switch (event) {
            case 0:
            case 1:
            case 2:
                this.mode = event;
                break;
            case 3:
                this.outputflow = EnergyNet.instance.getPowerFromTier(getSourceTier());
                this.inputflow = EnergyNet.instance.getPowerFromTier(getSinkTier());
                this.needrefresh = true;
                break;
        }
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
        super.onUnloaded();
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        updateRedstone();
    }

    public void updateRedstone() {
        boolean red;
        switch (this.mode) {
            case 0:
                red = this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord);
                break;
            case 1:
                red = false;
                break;
            case 2:
                red = true;
                break;
            default:
                throw new RuntimeException("invalid mode: " + this.mode);
        }
        if (red != this.redstone) {
            if (this.addedToEnergyNet)
                MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
            this.energy = 0.0D;
            this.redstone = red;
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
            setActive(this.redstone);
            this.power = EnergyNet.instance.getPowerFromTier(getSourceTier());
        }
    }

    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        if (this.redstone)
            return !facingMatchesDirection(direction);
        return facingMatchesDirection(direction);
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        if (this.redstone)
            return facingMatchesDirection(direction);
        return !facingMatchesDirection(direction);
    }

    public boolean facingMatchesDirection(ForgeDirection direction) {
        return (direction.ordinal() == getFacing());
    }

    public double getOfferedEnergy() {
        return (this.energy >= this.power) ? this.power : 0.0D;
    }

    public void drawEnergy(double amount) {
        this.outputflow = amount;
        this.energy -= amount;
    }

    public double getDemandedEnergy() {
        if (this.needrefresh) {
            this.needrefresh = false;
            return 1.0D;
        }
        return this.maxStorage - this.energy;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        this.inputflow = amount;
        this.energy += amount;
        return 0.0D;
    }

    public int getSourceTier() {
        if (this.redstone)
            return this.tier + 1;
        return this.tier;
    }

    public int getSinkTier() {
        if (this.redstone)
            return this.tier;
        if (this.tier < 4)
            return this.tier + 1;
        return Integer.MAX_VALUE;
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return (getFacing() != side);
    }

    public void setFacing(short side) {
        if (this.addedToEnergyNet)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        this.energy = 0.0D;
        super.setFacing(side);
        if (this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
    }

    public ContainerBase<TileEntityTransformer> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerTransformer(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUITransformer(new ContainerTransformer(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public double getinputflow() {
        if (!this.redstone)
            return this.inputflow;
        return this.outputflow;
    }

    public double getoutputflow() {
        if (this.redstone)
            return this.inputflow;
        return this.outputflow;
    }
}
