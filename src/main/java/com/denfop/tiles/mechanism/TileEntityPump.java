package com.denfop.tiles.mechanism;

import com.denfop.container.ContainerPump;
import com.denfop.gui.GUIPump;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.Direction;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityLiquidTankElectricMachine;
import ic2.core.block.invslot.InvSlot.Access;
import ic2.core.block.invslot.InvSlot.InvSide;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquid.OpType;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityPump extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock {
    public final int defaultTier;
    public final int defaultEnergyStorage;
    public final int defaultEnergyConsume;
    public final int defaultOperationLength;
    public final InvSlotConsumableLiquid containerSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotUpgrade upgradeSlot;
    private final String name;
    public int energyConsume;
    public int operationsPerTick;
    public short progress = 0;
    public int operationLength;
    public float guiProgress;
    private AudioSource audioSource;

    public TileEntityPump(String name, int size, int operationLength) {
        super(operationLength, 1, 1, size);
        this.containerSlot = new InvSlotConsumableLiquid(this, "containerSlot", 1, Access.I, 1, InvSide.TOP, OpType.Fill);
        this.outputSlot = new InvSlotOutput(this, "output", 2, 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.defaultEnergyConsume = this.energyConsume = 1;
        this.defaultOperationLength = this.operationLength = operationLength;
        this.defaultTier = 1;
        this.name = name;
        this.defaultEnergyStorage = this.operationLength;
    }

    private static int applyModifier(int base, int extra, double multiplier) {
        double ret = (double) Math.round(((double) base + (double) extra) * multiplier);
        return ret > 2.147483647E9D ? 2147483647 : (int) ret;
    }

    public void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }

        super.onUnloaded();
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.energy >= (double) (this.energyConsume * this.operationLength)) {

            if (this.progress < this.operationLength) {
                ++this.progress;
                this.energy -= this.energyConsume;
            } else {
                if (this.canoperate()) {
                    this.progress = 0;
                    this.operate(false);
                }
            }
        }

        MutableObject<ItemStack> output = new MutableObject();
        if (this.containerSlot.transferFromTank(this.fluidTank, output, true) && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
            this.containerSlot.transferFromTank(this.fluidTank, output, false);
            if (output.getValue() != null) {
                this.outputSlot.add(output.getValue());
            }
        }

        for (int i = 0; i < this.upgradeSlot.size(); ++i) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null && stack.getItem() instanceof IUpgradeItem && ((IUpgradeItem) stack.getItem()).onTick(stack, this)) {
                needsInvUpdate = true;
            }
        }

        this.guiProgress = (float) this.progress / (float) this.operationLength;
        if (needsInvUpdate) {
            super.markDirty();
        }

    }

    public String getInventoryName() {
        return StatCollector.translateToLocal(name);
    }

    public boolean canoperate() {
        return this.operate(true);
    }

    public boolean operate(boolean sim) {
        FluidStack liquid;
        List<FluidStack> liquid_list = new ArrayList<>();
        for(int i = this.xCoord-1;i < this.xCoord+1;i++)
            for(int j = this.zCoord-1;j < this.zCoord+1;j++)
                for(int k = this.yCoord-1;k < this.yCoord+1;k++)
        for (Direction dir : Direction.directions) {
            liquid = this.pump(i + dir.xOffset, k+ dir.yOffset, j + dir.zOffset, sim);
            if (liquid != null)
                liquid_list.add(liquid);
        }
        boolean canoperate = false;
        for (FluidStack stack : liquid_list)
        if (!liquid_list.isEmpty()) {

            if (!sim) {
                if(this.getFluidTank().fill(stack, false) > 0) {
                    this.getFluidTank().fill(stack, true);
                    canoperate = true;
                }
            }else if(this.getFluidTank().fill(stack, false) > 0)
            return true;


        } else {
            return false;
        }
        return canoperate;
    }

    public FluidStack pump(int x, int y, int z, boolean sim) {
        FluidStack ret = null;
        int freespace = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();

        if (freespace >= 1000 &&  !this.worldObj.isAirBlock(x,y,z)) {
            Block block = this.worldObj.getBlock(x, y, z);
            if (block.getMaterial().isLiquid()) {


                if (block instanceof IFluidBlock) {
                    IFluidBlock liquid = (IFluidBlock) block;
                    if (liquid.canDrain(this.worldObj, x, y, z)) {
                        if (!sim) {
                            ret = liquid.drain(this.worldObj, x, y, z, true);
                            this.worldObj.setBlockToAir(x, y, z);
                        } else {
                            ret = new FluidStack(liquid.getFluid(), 1000);
                        }
                    }
                } else {
                    if (this.worldObj.getBlockMetadata(x, y, z) != 0) {
                        return null;
                    }

                    ret = new FluidStack(FluidRegistry.getFluid(block.getUnlocalizedName().substring(5)), 1000);


                    if (!sim && ret != null) {
                        this.worldObj.setBlockToAir(x, y, z);
                    }
                }
            }
        }


        return ret;
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return this.getFacing() != side;
    }

    public void setFacing(short side) {
        super.setFacing(side);
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            this.setUpgradestat();
        }

    }

    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating()) {
            this.setUpgradestat();
        }

    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        double previousProgress = (double) this.progress / (double) this.operationLength;
        double stackOpLen = ((double) this.defaultOperationLength + (double) this.upgradeSlot.extraProcessTime) * 64.0D * this.upgradeSlot.processTimeMultiplier;
        this.operationsPerTick = (int) Math.min(Math.ceil(64.0D / stackOpLen), 2.147483647E9D);
        this.operationLength = (int) Math.round(stackOpLen * (double) this.operationsPerTick / 64.0D);
        this.energyConsume = applyModifier(this.defaultEnergyConsume, this.upgradeSlot.extraEnergyDemand, 1);
        this.setTier(applyModifier(this.defaultTier, this.upgradeSlot.extraTier, 1.0D));
        this.maxEnergy = applyModifier(this.defaultEnergyStorage, this.upgradeSlot.extraEnergyStorage + this.operationLength * this.energyConsume, this.upgradeSlot.energyStorageMultiplier);
        if (this.operationLength < 1) {
            this.operationLength = 1;
        }

        this.progress = (short) ((int) Math.floor(previousProgress * (double) this.operationLength + 0.1D));
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        } else {
            return false;
        }
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getShort("progress");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setShort("progress", this.progress);
    }

    public ContainerBase<TileEntityPump> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerPump(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIPump(new ContainerPump(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public void onNetworkUpdate(String field) {
        if (field.equals("active") && this.prevActive != this.getActive()) {
            if (this.audioSource == null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Machines/PumpOp.ogg", true, false, IC2.audioManager.getDefaultVolume());
            }

            if (this.getActive()) {
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
            } else if (this.audioSource != null) {
                this.audioSource.stop();
            }
        }

        super.onNetworkUpdate(field);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer, UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }
}
