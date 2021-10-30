package cofh.api.energy;

import net.minecraft.item.ItemStack;

public interface IEnergyContainerItem {
    int receiveEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean);

    int extractEnergy(ItemStack paramItemStack, int paramInt, boolean paramBoolean);

    int getEnergyStored(ItemStack paramItemStack);

    int getMaxEnergyStored(ItemStack paramItemStack);
}
