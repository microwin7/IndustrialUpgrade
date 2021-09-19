//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop.item.reactor;

import com.denfop.IUCore;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;

public class ItemReactorHeatSwitch extends ItemReactorHeatStorage {
    public final int switchSide;
    public final int switchReactor;

    public ItemReactorHeatSwitch(String internalName, int heatStorage1, int switchside, int switchreactor) {
        super(internalName, heatStorage1);
        this.switchSide = switchside;
        this.switchReactor = switchreactor;

        this.setCreativeTab(IUCore.tabssp3);
    }

    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
        if (heatrun) {
            int myHeat = 0;
            ArrayList<ItemReactorHeatSwitch.ItemStackCoord> heatAcceptors = new ArrayList();
            if (this.switchSide > 0) {
                this.checkHeatAcceptor(reactor, x - 1, y, heatAcceptors);
                this.checkHeatAcceptor(reactor, x + 1, y, heatAcceptors);
                this.checkHeatAcceptor(reactor, x, y - 1, heatAcceptors);
                this.checkHeatAcceptor(reactor, x, y + 1, heatAcceptors);
            }

            int add;
            if (this.switchSide > 0) {
                for (Iterator var8 = heatAcceptors.iterator(); var8.hasNext(); myHeat += add) {
                    ItemReactorHeatSwitch.ItemStackCoord stackcoord = (ItemReactorHeatSwitch.ItemStackCoord) var8.next();
                    IReactorComponent heatable = (IReactorComponent) stackcoord.stack.getItem();
                    double mymed = (double) this.getCurrentHeat(reactor, yourStack, x, y) * 100.0D / (double) this.getMaxHeat(reactor, yourStack, x, y);
                    double heatablemed = (double) heatable.getCurrentHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y) * 100.0D / (double) heatable.getMaxHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y);
                    add = (int) ((double) heatable.getMaxHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y) / 100.0D * (heatablemed + mymed / 2.0D));
                    if (add > this.switchSide) {
                        add = this.switchSide;
                    }

                    if (heatablemed + mymed / 2.0D < 1.0D) {
                        add = this.switchSide / 2;
                    }

                    if (heatablemed + mymed / 2.0D < 0.75D) {
                        add = this.switchSide / 4;
                    }

                    if (heatablemed + mymed / 2.0D < 0.5D) {
                        add = this.switchSide / 8;
                    }

                    if (heatablemed + mymed / 2.0D < 0.25D) {
                        add = 1;
                    }

                    if ((double) Math.round(heatablemed * 10.0D) / 10.0D > (double) Math.round(mymed * 10.0D) / 10.0D) {
                        add -= 2 * add;
                    } else if ((double) Math.round(heatablemed * 10.0D) / 10.0D == (double) Math.round(mymed * 10.0D) / 10.0D) {
                        add = 0;
                    }

                    myHeat -= add;
                    add = heatable.alterHeat(reactor, stackcoord.stack, stackcoord.x, stackcoord.y, add);
                }
            }

            if (this.switchReactor > 0) {
                double mymed = (double) this.getCurrentHeat(reactor, yourStack, x, y) * 100.0D / (double) this.getMaxHeat(reactor, yourStack, x, y);
                double Reactormed = (double) reactor.getHeat() * 100.0D / (double) reactor.getMaxHeat();
                add = (int) Math.round((double) reactor.getMaxHeat() / 100.0D * (Reactormed + mymed / 2.0D));
                if (add > this.switchReactor) {
                    add = this.switchReactor;
                }

                if (Reactormed + mymed / 2.0D < 1.0D) {
                    add = this.switchSide / 2;
                }

                if (Reactormed + mymed / 2.0D < 0.75D) {
                    add = this.switchSide / 4;
                }

                if (Reactormed + mymed / 2.0D < 0.5D) {
                    add = this.switchSide / 8;
                }

                if (Reactormed + mymed / 2.0D < 0.25D) {
                    add = 1;
                }

                if ((double) Math.round(Reactormed * 10.0D) / 10.0D > (double) Math.round(mymed * 10.0D) / 10.0D) {
                    add -= 2 * add;
                } else if ((double) Math.round(Reactormed * 10.0D) / 10.0D == (double) Math.round(mymed * 10.0D) / 10.0D) {
                    add = 0;
                }

                myHeat -= add;
                reactor.setHeat(reactor.getHeat() + add);
            }

            this.alterHeat(reactor, yourStack, x, y, myHeat);
        }

    }

    private void checkHeatAcceptor(IReactor reactor, int x, int y, ArrayList<ItemReactorHeatSwitch.ItemStackCoord> heatAcceptors) {
        ItemStack thing = reactor.getItemAt(x, y);
        if (thing != null && thing.getItem() instanceof IReactorComponent) {
            IReactorComponent comp = (IReactorComponent) thing.getItem();
            if (comp.canStoreHeat(reactor, thing, x, y)) {
                heatAcceptors.add(new ItemStackCoord(thing, x, y));
            }
        }

    }

    private static class ItemStackCoord {
        public final ItemStack stack;
        public final int x;
        public final int y;

        public ItemStackCoord(ItemStack stack1, int x1, int y1) {
            this.stack = stack1;
            this.x = x1;
            this.y = y1;
        }
    }
}
