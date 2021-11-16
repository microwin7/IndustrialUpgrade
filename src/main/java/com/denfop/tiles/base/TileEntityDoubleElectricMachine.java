package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.invslot.InvSlotDoubleMachineRecipe;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.recipe.RecipeOutput;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public abstract class TileEntityDoubleElectricMachine extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, IUpgradableBlock {
    public final int defaultEnergyConsume;
    public final int defaultOperationLength;
    public final int defaultTier;
    public final int defaultEnergyStorage;
    public final InvSlotProcessable inputSlotA;
    public final InvSlotOutput outputSlot;
    public final InvSlotUpgrade upgradeSlot;
    protected final String name;
    protected final EnumDoubleElectricMachine type;
    public int energyConsume;
    public int operationLength;
    public int operationsPerTick;
    public AudioSource audioSource;
    protected short progress;
    protected double guiProgress;

    public TileEntityDoubleElectricMachine(int energyPerTick, int length, int outputSlots, String name, EnumDoubleElectricMachine type) {
        this(energyPerTick, length, outputSlots, 1, name, type);
    }

    public TileEntityDoubleElectricMachine(int energyPerTick, int length, int outputSlots, int aDefaultTier, String name, EnumDoubleElectricMachine type) {
        super(energyPerTick * length, 1, 1);
        this.progress = 0;
        this.defaultEnergyConsume = this.energyConsume = energyPerTick;
        this.defaultOperationLength = this.operationLength = length;
        this.defaultTier = aDefaultTier;
        this.defaultEnergyStorage = energyPerTick * length;
        this.outputSlot = new InvSlotOutput(this, "output", 2, outputSlots);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.name = name;
        this.inputSlotA = new InvSlotDoubleMachineRecipe(this, "inputA", 0, 2, type.recipe);
        this.type = type;
    }

    public static int applyModifier(int base, int extra, double multiplier) {
        double ret = Math.round((base + extra) * multiplier);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (amount == 0D)
            return 0;
        if (this.energy >= this.maxEnergy)
            return amount;
        if (this.energy + amount >= this.maxEnergy) {
            double p = this.maxEnergy - this.energy;
            this.energy += (p);
            return amount - (p);
        } else {
            this.energy += amount;
        }
        return 0.0D;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getShort("progress");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("progress", this.progress);
    }

    public double getProgress() {
        return this.guiProgress;
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating())
            setOverclockRates();
    }

    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setOverclockRates();
    }

    public void updateEntityServer() {
        super.updateEntityServer();

        boolean needsInvUpdate = false;
        RecipeOutput output = getOutput();
        if (output != null && this.energy >= this.energyConsume) {
            setActive(true);
            if (this.progress == 0)
                IC2.network.get().initiateTileEntityEvent(this, 0, true);
            this.progress = (short) (this.progress + 1);
            this.energy -= this.energyConsume;
            double k = this.progress;

            this.guiProgress = (k / this.operationLength);
            if (this.progress >= this.operationLength) {
                this.guiProgress = 0;
                operate(output);
                needsInvUpdate = true;
                this.progress = 0;
                IC2.network.get().initiateTileEntityEvent(this, 2, true);
            }
        } else {
            if (this.progress != 0 && getActive())
                IC2.network.get().initiateTileEntityEvent(this, 1, true);
            if (output == null)
                this.progress = 0;
            setActive(false);
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

    public void setOverclockRates() {
        this.upgradeSlot.onChanged();
        double previousProgress = (double) this.progress / (double) this.operationLength;
        double stackOpLen = (this.defaultOperationLength + this.upgradeSlot.extraProcessTime) * 64.0D
                * this.upgradeSlot.processTimeMultiplier;
        this.operationsPerTick = (int) Math.min(Math.ceil(64.0D / stackOpLen), 2.147483647E9D);
        this.operationLength = (int) Math.round(stackOpLen * this.operationsPerTick / 64.0D);
        this.energyConsume = applyModifier(this.defaultEnergyConsume, this.upgradeSlot.extraEnergyDemand,
                this.upgradeSlot.energyDemandMultiplier);
        setTier(applyModifier(this.defaultTier, this.upgradeSlot.extraTier, 1.0D));
        this.maxEnergy = applyModifier(this.defaultEnergyStorage,
                this.upgradeSlot.extraEnergyStorage + this.operationLength * this.energyConsume,
                this.upgradeSlot.energyStorageMultiplier);
        if (this.operationLength < 1)
            this.operationLength = 1;
        this.progress = (short) (int) Math.floor(previousProgress * this.operationLength + 0.1D);
    }

    public void operate(RecipeOutput output) {
        for (int i = 0; i < this.operationsPerTick; i++) {
            List<ItemStack> processResult = output.items;
            for (int j = 0; j < this.upgradeSlot.size(); j++) {
                ItemStack stack = this.upgradeSlot.get(j);
                if (stack != null && stack.getItem() instanceof IUpgradeItem)
                    ((IUpgradeItem) stack.getItem()).onProcessEnd(stack, this, processResult);
            }
            operateOnce(output, processResult);

            output = getOutput();
            if (output == null)
                break;
        }
    }

    public abstract void operateOnce(RecipeOutput output, List<ItemStack> processResult);

    public RecipeOutput getOutput() {
        if (this.inputSlotA.isEmpty())
            return null;

        RecipeOutput output = this.inputSlotA.process();

        if (output == null)
            return null;
        if (this.outputSlot.canAdd(output.items))
            return output;

        return null;
    }

    public String getInventoryName() {
        return name;
    }

    public ContainerBase<? extends TileEntityDoubleElectricMachine> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityDoubleElectricMachine>) new ContainerDoubleElectricMachine(entityPlayer, this, type);
    }

    public String getStartSoundFile() {
        return null;
    }

    public String getInterruptSoundFile() {
        return null;
    }

    public void onNetworkEvent(int event) {
        if (this.audioSource == null && getStartSoundFile() != null)
            this.audioSource = IUCore.audioManager.createSource(this, getStartSoundFile());
        switch (event) {
            case 0:
                if (this.audioSource != null)
                    this.audioSource.play();
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (getInterruptSoundFile() != null)
                        IUCore.audioManager.playOnce(this, getInterruptSoundFile());
                }
                break;
            case 2:
                if (this.audioSource != null)
                    this.audioSource.stop();
                break;
        }
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        }
        return false;
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }
}
