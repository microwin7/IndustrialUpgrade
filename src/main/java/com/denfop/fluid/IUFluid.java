package com.denfop.fluid;

import net.minecraftforge.fluids.Fluid;

public class IUFluid extends Fluid {
    public IUFluid(String fluidName) {
        super(fluidName);
    }

    public String getUnlocalizedName() {
        return "iu." + super.getUnlocalizedName().substring(6);
    }
}
