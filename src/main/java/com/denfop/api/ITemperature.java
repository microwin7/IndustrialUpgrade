package com.denfop.api;

import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import net.minecraftforge.fluids.FluidStack;

public interface ITemperature {

    short getTemperature();

    void setTemperature(short temperature);

    short getMaxTemperature();

    boolean isFluidTemperature();

    FluidStack getFluid();

    TileEntityElectricMachine getTile();
}
