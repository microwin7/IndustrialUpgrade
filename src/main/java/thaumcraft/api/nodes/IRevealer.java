package thaumcraft.api.nodes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IRevealer {
    boolean showNodes(ItemStack paramItemStack, EntityLivingBase paramEntityLivingBase);
}
