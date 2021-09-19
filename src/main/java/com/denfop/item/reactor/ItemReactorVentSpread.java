package com.denfop.item.reactor;

import com.denfop.Constants;
import com.denfop.IUCore;
import cpw.mods.fml.common.registry.GameRegistry;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemReactorVentSpread extends Item implements IReactorComponent {
    public final int sideVent;

    public ItemReactorVentSpread(String internalName, int sidevent) {

        this.setMaxStackSize(1);
        this.sideVent = sidevent;
        setUnlocalizedName(internalName);
        setNoRepair();

        this.setCreativeTab(IUCore.tabssp3);
        this.setTextureName(Constants.TEXTURES_MAIN + internalName);
        GameRegistry.registerItem(this, internalName);
    }

    public String getUnlocalizedName() {
        return "iu." + super.getUnlocalizedName().substring(5);
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }


    public void processChamber(IReactor reactor, ItemStack yourStack, int x, int y, boolean heatrun) {
        if (heatrun) {
            this.cool(reactor, x - 1, y);
            this.cool(reactor, x + 1, y);
            this.cool(reactor, x, y - 1);
            this.cool(reactor, x, y + 1);
        }

    }

    private void cool(IReactor reactor, int x, int y) {
        ItemStack stack = reactor.getItemAt(x, y);
        if (stack != null && stack.getItem() instanceof IReactorComponent) {
            IReactorComponent comp = (IReactorComponent) stack.getItem();
            if (comp.canStoreHeat(reactor, stack, x, y)) {
                int self = comp.alterHeat(reactor, stack, x, y, -this.sideVent);
                if (self <= 0) {
                    reactor.addEmitHeat(self + this.sideVent);
                }
            }
        }

    }

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        return false;
    }

    public boolean canStoreHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return false;
    }

    public int getMaxHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return 0;
    }

    public int getCurrentHeat(IReactor reactor, ItemStack yourStack, int x, int y) {
        return 0;
    }

    public int alterHeat(IReactor reactor, ItemStack yourStack, int x, int y, int heat) {
        return heat;
    }

    public float influenceExplosion(IReactor reactor, ItemStack yourStack) {
        return 0.0F;
    }
}
