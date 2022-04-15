package com.denfop.item.reactor;

import com.denfop.IUCore;
import com.denfop.utils.ModUtils;
import ic2.api.reactor.IReactor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.List;

public class ItemBaseRod extends ItemReactorBase {

    private final ItemStack[] depletedreactorrod;
    private final int heat;

    private final float power;

    public ItemBaseRod(String internalName, int cells, int time, int heat, float power, ItemStack[] depletedrod) {
        super(internalName, cells, time);

        this.heat = heat;
        this.power = power;
        this.depletedreactorrod = depletedrod;
        setCreativeTab(IUCore.tabssp3);
    }

    protected int getFinalHeat(IReactor reactor, int heat) {
        if (reactor.isFluidCooled()) {
            float breedereffectiveness = (float) reactor.getHeat() / (float) reactor.getMaxHeat();
            if (breedereffectiveness > 0.5D)
                heat *= this.heat;
        }
        return heat;
    }

    protected ItemStack getDepletedStack() {
        ItemStack ret;
        double temp = Math.log10(this.numberOfCells);
        double temp1 = Math.log10(2);
        double p = temp / temp1;
        if (depletedreactorrod[(int) p] != null) {
            ret = depletedreactorrod[(int) p];
            return new ItemStack(ret.getItem(), 1);
        }

        throw new RuntimeException("invalid cell count: " + this.numberOfCells);
    }

    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean b) {
        super.addInformation(stack, player, info, b);
        double[] p = new double[]{5.0D, 20D, 60D, 200D};

        double temp = Math.log10(this.numberOfCells);
        double temp1 = Math.log10(2);
        double m = temp / temp1;
        info.add(StatCollector.translateToLocal("reactor.info") + ModUtils.getString(p[(int) m] * this.power) + " EU");
        info.add(StatCollector.translateToLocal("reactor.info1") + ModUtils.getString((p[(int) m] * this.power) + p[(int) m] * (power / 2) * 0.99) + " EU");

    }

    public boolean acceptUraniumPulse(IReactor reactor, ItemStack yourStack, ItemStack pulsingStack, int youX, int youY, int pulseX, int pulseY, boolean heatrun) {
        if (!heatrun) {
            float breedereffectiveness = (float) reactor.getHeat() / (float) reactor.getMaxHeat();
            float ReaktorOutput = (power / 2) * breedereffectiveness + this.power;
            reactor.addOutput(ReaktorOutput);
        }
        return true;
    }
}
