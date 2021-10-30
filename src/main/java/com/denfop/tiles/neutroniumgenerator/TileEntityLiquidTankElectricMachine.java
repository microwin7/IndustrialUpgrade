package com.denfop.tiles.neutroniumgenerator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public abstract class TileEntityLiquidTankElectricMachine extends TileEntityElectricMachine implements IFluidHandler {
    protected final FluidTank fluidTank;

    public TileEntityLiquidTankElectricMachine(double maxenergy, int tier, int oldDischargeIndex, int tanksize) {
        super(maxenergy, tier, oldDischargeIndex);
        this.fluidTank = new FluidTank(1000 * tanksize);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.fluidTank.readFromNBT(nbttagcompound.getCompoundTag("fluidTank"));
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        nbttagcompound.setTag("fluidTank", fluidTankTag);
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidStack getFluidStackfromTank() {
        return getFluidTank().getFluid();
    }

    public int getTankAmount() {
        return getFluidTank().getFluidAmount();
    }

    public int gaugeLiquidScaled(int i) {
        if (getFluidTank().getFluidAmount() <= 0)
            return 0;
        if (getFluidTank().getCapacity() == 0)
            return 0;
        return getFluidTank().getFluidAmount() * i / getFluidTank().getCapacity();
    }

    public double gaugeLiquidScaled(double i) {
        if (getFluidTank().getFluidAmount() <= 0)
            return 0;
        if (getFluidTank().getCapacity() == 0)
            return 0;
        return getFluidTank().getFluidAmount() * i / getFluidTank().getCapacity();
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

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{getFluidTank().getInfo()};
    }

    public abstract boolean canFill(ForgeDirection paramForgeDirection, Fluid paramFluid);

    public abstract boolean canDrain(ForgeDirection paramForgeDirection, Fluid paramFluid);
}
