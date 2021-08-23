package com.denfop.item.reactor;

import com.denfop.IUCore;
import com.denfop.item.base.ReactorItemCore;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

public class ItemReactorCondensator extends ReactorItemCore implements IReactorComponent {
    public ItemReactorCondensator(String internalName, int maxdmg) {
        super(internalName, maxdmg + 1);

        this.setCreativeTab(IUCore.tabssp3);
    }

    public void processChamber(IReactor reactor, ItemStack stack, int x, int y, boolean heatrun) {
    }

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        return false;
    }

    public boolean canStoreHeat(IReactor reactor, ItemStack stack, int x, int y) {
        return this.getCustomDamage(stack) + 1 < this.getMaxCustomDamage(stack);
    }

    public int getMaxHeat(IReactor reactor, ItemStack stack, int x, int y) {
        return this.getMaxCustomDamage(stack);
    }

    public int getCurrentHeat(IReactor reactor, ItemStack stack, int x, int y) {
        return 0;
    }

    public int alterHeat(IReactor reactor, ItemStack stack, int x, int y, int heat) {
        if (heat >= 0) {
            int can = this.getMaxCustomDamage(stack) - (this.getCustomDamage(stack) + 1);
            if (can > heat) {
                can = heat;
            }

            heat -= can;
            this.setCustomDamage(stack, this.getCustomDamage(stack) + can);
        }
        return heat;
    }

    public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
        return 0.0F;
    }
}

