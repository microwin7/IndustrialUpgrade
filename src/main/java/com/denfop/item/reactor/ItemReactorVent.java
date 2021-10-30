package com.denfop.item.reactor;

import com.denfop.IUCore;
import ic2.api.reactor.IReactor;
import net.minecraft.item.ItemStack;

public class ItemReactorVent extends ItemReactorHeatStorage {
    public final int selfVent;
    public final int reactorVent;

    public ItemReactorVent(String internalName, int heatStorage1, int selfvent, int reactorvent) {
        super(internalName, heatStorage1);
        this.selfVent = selfvent;
        this.reactorVent = reactorvent;

        this.setCreativeTab(IUCore.tabssp3);
    }

    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
        if (heatrun) {
            int rheat;
            if (this.reactorVent > 0) {
                rheat = reactor.getHeat();
                int reactorDrain = Math.min(rheat, this.reactorVent);

                rheat -= reactorDrain;
                if (this.alterHeat(reactor, yourStack, x, y, reactorDrain) > 0) {
                    return;
                }

                reactor.setHeat(rheat);
            }

            rheat = this.alterHeat(reactor, yourStack, x, y, -this.selfVent);
            if (rheat <= 0) {
                reactor.addEmitHeat(rheat + this.selfVent);
            }
        }

    }
}
