package buildcraft.api.tools;

import net.minecraft.entity.player.EntityPlayer;

public interface IToolWrench {
    boolean canWrench(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);

    void wrenchUsed(EntityPlayer paramEntityPlayer, int paramInt1, int paramInt2, int paramInt3);
}
