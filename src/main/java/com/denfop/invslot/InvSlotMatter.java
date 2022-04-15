package com.denfop.invslot;

import com.denfop.item.mechanism.ItemBaseMachine;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import ic2.core.item.block.ItemMachine;
import net.minecraft.item.ItemStack;

public class InvSlotMatter extends InvSlot {

    private int stackSizeLimit;

    public InvSlotMatter(TileEntityInventory base1, int oldStartIndex1) {
        super(base1, "input2", oldStartIndex1, InvSlot.Access.IO, 9, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 4;
    }

    public boolean accepts(ItemStack itemStack) {
        return (itemStack.getItem() instanceof ItemBaseMachine && itemStack.getItemDamage() <= 3) || (itemStack.getItem() instanceof ItemMachine && itemStack.getItemDamage() == 14);
    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }

    public double getMattercostenergy(ItemStack stack) {
        int count = stack.getItemDamage();
        if (stack.getItem() instanceof ItemBaseMachine) {
            switch (count) {
                case 1:
                    return 900000;
                case 2:
                    return 800000;
                case 3:
                    return 700000;
            }
        }
        return 1000000;
    }

    public double getMatterenergy(ItemStack stack) {
        int count = stack.getItemDamage();
        if (stack.getItem() instanceof ItemBaseMachine) {
            switch (count) {
                case 1:
                    return 8000000;
                case 2:
                    return 64000000;
                case 3:
                    return 256000000;
            }
        }
        return 1000000;
    }

    public double getMaxEnergy() {
        double maxEnergy = 0;
        for (int i = 0; i < 9; i++) {
            if (this.get(i) != null) {
                maxEnergy += (getMatterenergy(this.get(i)) * this.get(i).stackSize);
            }

        }
        return maxEnergy;
    }

    public double getcostEnergy(InvSlotMatter inputSlot) {
        double cost = 0;
        int k = 0;
        for (int i = 0; i < 9; i++) {
            if (inputSlot.get(i) != null) {
                cost += (getMattercostenergy(inputSlot.get(i)) * inputSlot.get(i).stackSize);
                k += (inputSlot.get(i).stackSize);

            }

        }
        return cost / k;
    }

    public int getFluidTank(InvSlotMatter inputSlot) {
        int count = 0;
        for (int i = 0; i < 9; i++) {
            if (inputSlot.get(i) != null) {
                count += (getMattertank(inputSlot.get(i)) * inputSlot.get(i).stackSize);
            }
        }
        return 1000 * count;
    }

    private int getMattertank(ItemStack itemStack) {
        int count = itemStack.getItemDamage();
        if (itemStack.getItem() instanceof ItemBaseMachine) {
            switch (count) {
                case 1:
                    return 12;
                case 2:
                    return 14;
                case 3:
                    return 16;
            }
        }
        return 10;
    }
}
