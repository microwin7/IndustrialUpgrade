package cofh.api.energy;

import net.minecraftforge.common.util.ForgeDirection;

public interface IEnergyHandler extends IEnergyProvider, IEnergyReceiver {
    int receiveEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean);

    int extractEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean);

    int getEnergyStored(ForgeDirection paramForgeDirection);

    int getMaxEnergyStored(ForgeDirection paramForgeDirection);
}
