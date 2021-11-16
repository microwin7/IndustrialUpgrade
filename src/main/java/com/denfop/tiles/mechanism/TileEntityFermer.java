package com.denfop.tiles.mechanism;

import com.denfop.Config;
import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.invslot.InvSlotProcessableMultiSmelting;
import com.denfop.recipemanager.BasicMachineRecipeManager;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;

public class TileEntityFermer extends TileEntityMultiMachine {
    public final int[] operationLength_temp;
    public final int[] operationsPerTick_temp;
    public final int defaultOperationsPerTick_temp;

    public TileEntityFermer() {
        super(EnumMultiMachine.Fermer.usagePerTick, EnumMultiMachine.Fermer.lenghtOperation, Recipes.fermer, 3);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.fermer);
        this.operationLength_temp = new int[sizeWorkingSlot];
        for (int i = 0; i < sizeWorkingSlot; i++)
            this.operationLength_temp[i] = EnumMultiMachine.Fermer.usagePerTick * EnumMultiMachine.Fermer.lenghtOperation;
        this.operationsPerTick_temp = new int[sizeWorkingSlot];
        this.defaultOperationsPerTick_temp = EnumMultiMachine.Fermer.usagePerTick * EnumMultiMachine.Fermer.lenghtOperation;
    }

    public static void init() {
        Recipes.fermer = new BasicMachineRecipeManager();
        addrecipe(Items.wheat_seeds, Items.wheat, 2);
        addrecipe(Items.wheat, Items.wheat_seeds, 1);
        addrecipe(Items.carrot, Items.carrot, 2);
        addrecipe(Items.potato, Items.potato, 2);
        addrecipe(Item.getItemFromBlock(Blocks.pumpkin), Items.pumpkin_seeds, 1);

        addrecipe(Items.pumpkin_seeds, Item.getItemFromBlock(Blocks.pumpkin), 2);
        addrecipe(Items.melon_seeds, Items.melon, 2);
        addrecipe(Items.melon, Items.melon_seeds, 1);
        addrecipe(Item.getItemFromBlock(Blocks.cocoa), Item.getItemFromBlock(Blocks.cocoa), 2);
        for (int i = 0; i < 4; i++)
            addrecipe(new ItemStack(Blocks.sapling, 1, i), new ItemStack(Blocks.log, 2, i));
        for (int i = 0; i < 2; i++)
            addrecipe(new ItemStack(Blocks.sapling, 1, i + 4), new ItemStack(Blocks.log2, 2, i));
        for (int i = 0; i < 4; i++)
            addrecipe(new ItemStack(Blocks.log, 1, i), new ItemStack(Blocks.sapling, 1, i));
        for (int i = 0; i < 2; i++)
            addrecipe(new ItemStack(Blocks.log2, 1, i), new ItemStack(Blocks.sapling, 1, i + 4));
        addrecipe(Ic2Items.rubber, Ic2Items.rubberSapling.getItem(), 1);
        addrecipe(Ic2Items.rubberSapling, Ic2Items.rubber.getItem(), 2);
    }

    public static void addrecipe(ItemStack input, Item output, int n) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(input), null, new ItemStack(output, n));
    }

    public static void addrecipe(ItemStack input, ItemStack output) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(input), null, output);
    }

    public static void addrecipe(Item input, Item output, int n) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(input)), null, new ItemStack(output, n));
    }

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        double[] stackOpLen = new double[sizeWorkingSlot];
        for (int i = 0; i < sizeWorkingSlot; i++) {
            if (this.inputSlots.get1(i) == null)
                this.operationLength_temp[i] = this.defaultOperationsPerTick_temp;
            stackOpLen[i] = (this.operationLength_temp[i] + this.upgradeSlot.extraProcessTime) * 64.0D * this.upgradeSlot.processTimeMultiplier;

            this.operationsPerTick_temp[i] = (int) Math.min(Math.ceil(64.0D / stackOpLen[i]), 2.147483647E9D);

            this.operationLength_temp[i] = (int) Math.round(stackOpLen[i] * this.operationsPerTick_temp[i] / 64.0D);
            if (this.operationLength_temp[i] < 1)
                this.operationLength_temp[i] = 1;
        }
        this.energyConsume = applyModifier(this.defaultEnergyConsume, this.upgradeSlot.extraEnergyDemand, this.upgradeSlot.energyDemandMultiplier);
        setTier(applyModifier(this.defaultTier, this.upgradeSlot.extraTier, 1.0D));
        this.maxEnergy = applyModifier(this.defaultEnergyStorage, this.upgradeSlot.extraEnergyStorage + this.operationLength * this.energyConsume, this.upgradeSlot.energyStorageMultiplier);

        if (this.operationLength < 1)
            this.operationLength = 1;
    }

    public void updateEntityServer() {
        if ((double) this.maxEnergy - this.energy >= 1.0D) {
            double amount = this.dischargeSlot.discharge((double) this.maxEnergy - this.energy, false);
            if (amount > 0.0D) {
                this.energy += amount;
                this.markDirty();
            }
        }

        boolean needsInvUpdate = false;
        boolean isActive = false;
        int quickly = 1;
        IC2.network.get().updateTileEntityField(this, "tier");

        for (int i = 0; i < sizeWorkingSlot; i++) {
            RecipeOutput output = getOutput(i);
            if (this.quickly)
                quickly = 100;
            int size = 1;
            if (this.inputSlots.get1(i) != null)
                if (this.modulesize) {
                    for (int j = 0; ; j++) {
                        ItemStack stack = new ItemStack(this.inputSlots.get1(i).getItem(), j, this.inputSlots.get1(i).getItemDamage());
                        if (recipe != null) {
                            if (recipe.getOutputFor(stack, false) != null) {
                                size = j;
                                break;
                            }
                        } else if (this.inputSlots instanceof InvSlotProcessableMultiSmelting) {
                            size = 1;
                            break;

                        }
                    }
                    size = (int) Math.floor((float) this.inputSlots.get1(i).stackSize / size);
                    int size1 = 0;

                    for (int ii = 0; ii < sizeWorkingSlot; ii++)
                        if (this.outputSlots.get(ii) != null) {
                            size1 += (64 - this.outputSlots.get(ii).stackSize);
                        } else {
                            size1 += 64;
                        }
                    if (output != null)
                        size1 = size1 / output.items.get(0).stackSize;
                    size = Math.min(size1, size);
                }
            if (output != null && (this.energy >= this.energyConsume * quickly * size || this.energy2 >= Math.abs(this.energyConsume * Config.coefficientrf * quickly * size))) {
                isActive = true;
                if (this.progress[i] == 0)
                    initiate(0);
                this.progress[i]++;
                if (output.metadata != null)
                    if (output.metadata.getInteger("operationLength") != 0)
                        this.operationLength_temp[i] = output.metadata.getInteger("operationLength");
                    else
                        this.operationLength_temp[i] = this.defaultOperationsPerTick * this.operationLength;
                this.guiProgress[i] = (double) this.progress[i] / this.operationLength_temp[i];
                if (this.energy >= this.energyConsume * quickly * size) {
                    this.energy -= this.energyConsume * quickly * size;
                } else if (this.energy2 >= Math.abs(this.energyConsume * Config.coefficientrf * quickly * size)) {
                    this.energy2 -= Math.abs(this.energyConsume * Config.coefficientrf * quickly * size);
                }

                if (this.progress[i] >= this.operationLength_temp[i] || this.quickly) {
                    this.guiProgress[i] = 0;
                    this.progress[i] = 0;
                    if (this.expstorage < this.expmaxstorage) {
                        Random rand = worldObj.rand;

                        int exp = rand.nextInt(3) + 1;
                        this.expstorage = this.expstorage + exp;
                        if (this.expstorage >= this.expmaxstorage) {
                            expstorage = this.expmaxstorage;
                        }
                    }
                    operate(i, output, size);
                    needsInvUpdate = true;
                    initiate(2);
                }

            } else {
                if (this.progress[i] != 0 && getActive())
                    initiate(1);
                if (output == null)
                    this.progress[i] = 0;
            }
        }

        if (getActive() != isActive) {
            setActive(isActive);
        }

        for (int i = 0; i < this.upgradeSlot.size(); i++) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null && stack.getItem() instanceof IUpgradeItem)
                if (((IUpgradeItem) stack.getItem()).onTick(stack, this))
                    needsInvUpdate = true;
        }

        if (needsInvUpdate)
            super.markDirty();

    }

    public void operate(int slotId, RecipeOutput output, int size) {
        for (int i = 0; i < this.operationsPerTick_temp[slotId]; i++) {

            operateOnce(slotId, output.items, size, output);
            output = getOutput(slotId);
            if (output == null)
                break;
        }
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.Fermer;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockFermer.name");
    }

    public String getStartSoundFile() {
        return "Machines/Fermer.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
                UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

}
