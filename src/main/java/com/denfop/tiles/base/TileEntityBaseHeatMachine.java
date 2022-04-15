package com.denfop.tiles.base;

import com.denfop.api.ITemperature;
import com.denfop.api.ITemperatureSourse;
import com.denfop.api.Recipes;
import ic2.api.Direction;
import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityBaseHeatMachine extends TileEntityElectricMachine implements IFluidHandler, ITemperatureSourse {


    public final boolean hasFluid;
    public final FluidTank fluidTank;
    public final String name;
    public final short maxtemperature;
    public short temperature;

    public TileEntityBaseHeatMachine(String name, boolean hasFluid) {
        super(hasFluid ? 0 : 10000, 14, -1);
        this.hasFluid = hasFluid;
        this.fluidTank = new FluidTank(12000);
        this.name = name;
        this.maxtemperature = 5000;
        this.temperature = 0;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.fluidTank.readFromNBT(nbttagcompound.getCompoundTag("fluidTank"));

        this.temperature = nbttagcompound.getShort("temperature");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        nbttagcompound.setTag("fluidTank", fluidTankTag);
        nbttagcompound.setShort("temperature", this.temperature);

    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        IC2.network.get().updateTileEntityField(this, "temperature");
        IC2.network.get().updateTileEntityField(this, "fluidTank");
        setActive(Recipes.mechanism.process(this));
        for (Direction direction : Direction.directions) {
            TileEntity target = direction.applyToTileEntity(this);
            if (target instanceof ITemperature && !(target instanceof TileEntityBaseHeatMachine))
                Recipes.mechanism.transfer((ITemperature) target, this);
        }

    }


    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (amount == 0D)
            return 0;
        if (this.energy >= this.maxEnergy)
            return amount;
        if (this.energy + amount >= this.maxEnergy) {
            double p = this.maxEnergy - this.energy;
            this.energy += (p);
            return amount - (p);
        } else {
            this.energy += amount;
        }
        return 0.0D;
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal(name);
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }


    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (!canFill(from, resource.getFluid()))
            return 0;
        return getFluidTank().fill(resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(getFluidTank().getFluid()))
            return null;
        if (!canDrain(from, resource.getFluid()))
            return null;
        return getFluidTank().drain(resource.amount, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (!canDrain(from, null))
            return null;
        return getFluidTank().drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return hasFluid && fluid.equals(FluidRegistry.LAVA);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return hasFluid;
    }


    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{getFluidTank().getInfo()};
    }

    @Override
    public short getTemperature() {
        return this.temperature;
    }

    @Override
    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    @Override
    public short getMaxTemperature() {
        return this.maxtemperature;
    }

    @Override
    public boolean isFluidTemperature() {
        return this.hasFluid;
    }

    @Override
    public FluidStack getFluid() {
        return fluidTank.getFluid();
    }

    @Override
    public TileEntityElectricMachine getTile() {
        return this;
    }


}
