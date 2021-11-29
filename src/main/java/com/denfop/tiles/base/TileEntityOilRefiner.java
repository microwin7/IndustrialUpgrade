package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.api.IFluidItem;
import com.denfop.audio.AudioSource;
import com.denfop.block.base.BlocksItems;
import com.denfop.container.ContainerOilRefiner;
import com.denfop.gui.GUIOilRefiner;
import com.denfop.tiles.neutroniumgenerator.TileEntityElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.*;
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
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityOilRefiner extends TileEntityElectricMachine implements IFluidHandler, IHasGui, IUpgradableBlock, INetworkTileEntityEventListener, INetworkDataProvider {
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotOutput outputSlot1;
    public final FluidTank fluidTank;
    public final FluidTank fluidTank1;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotConsumableLiquid containerslot;
    public final InvSlotConsumableLiquid containerslot1;
    public final FluidTank fluidTank2;
    public double storage = 0.0D;
    public AudioSource audioSource;

    public TileEntityOilRefiner() {
        super(24000, 14, 0);

        this.fluidSlot = new InvSlotConsumableLiquidByList(this, "fluidSlot", 1, 1, BlocksItems.getFluid("fluidneft"));

        fluidTank = new FluidTank(1000 * 12);
        fluidTank1 = new FluidTank(1000 * 12);
        fluidTank2 = new FluidTank(1000 * 12);
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.outputSlot1 = new InvSlotOutput(this, "output1", 4, 1);

        this.containerslot = new InvSlotConsumableLiquidByList(this,
                "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill,
                BlocksItems.getFluid("fluidbenz"));
        this.containerslot1 = new InvSlotConsumableLiquidByList(this,
                "containerslot1", 3, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill,
                BlocksItems.getFluid("fluiddizel"));

        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 12, 4);


    }

    private static int applyModifier(int extra) {
        double ret = Math.round((14 + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energy");
        ret.add("fluidTank");
        ret.add("fluidTank1");
        ret.add("fluidTank2");
        return ret;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("");
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
        this.fluidTank.readFromNBT(nbttagcompound.getCompoundTag("fluidTank"));
        this.fluidTank1.readFromNBT(nbttagcompound.getCompoundTag("fluidTank1"));
        this.fluidTank2.readFromNBT(nbttagcompound.getCompoundTag("fluidTank2"));

        try {
            this.storage = nbttagcompound.getDouble("storage");
        } catch (Exception var3) {
            this.storage = nbttagcompound.getShort("storage");
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("storage", this.storage);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        nbttagcompound.setTag("fluidTank", fluidTankTag);
        NBTTagCompound fluidTankTag1 = new NBTTagCompound();
        this.fluidTank1.writeToNBT(fluidTankTag1);
        nbttagcompound.setTag("fluidTank1", fluidTankTag1);
        NBTTagCompound fluidTankTag2 = new NBTTagCompound();
        this.fluidTank2.writeToNBT(fluidTankTag2);
        nbttagcompound.setTag("fluidTank2", fluidTankTag2);

    }

    public boolean onUpdateUpgrade() {
        for (int i = 0; i < this.upgradeSlot.size(); i++) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null)
                return ((IUpgradeItem) stack.getItem()).onTick(stack, this);
        }
        return false;
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate;
        needsInvUpdate = onUpdateUpgrade();
        if (this.needsFluid()) {
            MutableObject<ItemStack> output = new MutableObject<>();
            if (this.fluidSlot.transferToTank(this.fluidTank, output, true) && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                ItemStack stack = this.fluidSlot.get();
                needsInvUpdate = this.fluidSlot.transferToTank(this.fluidTank, output, false);
                if (output.getValue() != null) {
                    this.outputSlot.add(output.getValue());
                } else if (stack.getItem() instanceof IFluidItem)
                    if (this.outputSlot.canAdd(((IFluidItem) stack.getItem()).getItemEmpty()))
                        this.outputSlot.add(((IFluidItem) stack.getItem()).getItemEmpty());
            }
        }
        if (worldObj.provider.getWorldTime() % 60 == 0)
            initiate(2);
        boolean drain = false;
        boolean drain1 = false;
        this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        this.markDirty();
        if (this.getFluidTank().getFluidAmount() >= 5 && this.energy >= 25) {
            needsInvUpdate = true;
            if (this.fluidTank1.getFluidAmount() + 3 <= this.fluidTank1.getCapacity()) {
                fill1(new FluidStack(BlocksItems.getFluid("fluidbenz"), 3), true);
                drain = true;

            }
            if (this.fluidTank2.getFluidAmount() + 2 <= this.fluidTank2.getCapacity()) {
                fill2(new FluidStack(BlocksItems.getFluid("fluiddizel"), 2), true);
                drain1 = true;
            }
            if (drain || drain1) {
                if(drain)
                    this.getFluidTank().drain(3, true);
                if(drain1)
                    this.getFluidTank().drain(2, true);
                needsInvUpdate = drain || drain1;
                initiate(0);
                this.useEnergy(25);
                IC2.network.get().updateTileEntityField(this, "fluidTank");
                IC2.network.get().updateTileEntityField(this, "fluidTank1");
                IC2.network.get().updateTileEntityField(this, "fluidTank2");
                setActive(true);
            } else {
                initiate(2);
                setActive(false);
            }
        }
        MutableObject<ItemStack> output = new MutableObject();
        if (this.containerslot.transferFromTank(this.fluidTank1, output, true)
                && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
            this.containerslot.transferFromTank(this.fluidTank1, output, false);

            if (output.getValue() != null)
                this.outputSlot.add(output.getValue());
        }

        if (this.containerslot1.transferFromTank(this.fluidTank2, output, true)
                && (output.getValue() == null || this.outputSlot1.canAdd(output.getValue()))) {
            this.containerslot1.transferFromTank(this.fluidTank2, output, false);

            if (output.getValue() != null)
                this.outputSlot1.add(output.getValue());
        }
        if (this.energy > this.maxEnergy) {
            this.energy = this.maxEnergy;
        }

        if (needsInvUpdate) {
            this.markDirty();
        }


    }

    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        setTier(applyModifier(this.upgradeSlot.extraTier));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/oilrefiner.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
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

    public float getWrenchDropRate() {
        return 0.9F;
    }


    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == BlocksItems.getFluid("fluidneft") || fluid == BlocksItems.getFluid("fluidbenz") || fluid == BlocksItems.getFluid("fluiddizel");
    }

    public boolean canFill1(Fluid fluid) {
        return fluid == BlocksItems.getFluid("fluidbenz");
    }

    public boolean canFill2(Fluid fluid) {
        return fluid == BlocksItems.getFluid("fluiddizel");
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }


    public ContainerBase<TileEntityOilRefiner> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerOilRefiner(entityPlayer, this);

    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIOilRefiner(new ContainerOilRefiner(entityPlayer, this));

    }

    public void onBlockBreak(Block block, int meta) {
        FluidEvent.fireEvent(new FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, 1000), this.worldObj, this.xCoord, this.yCoord, this.zCoord));
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidTank getFluidTank1() {
        return this.fluidTank1;
    }

    public FluidTank getFluidTank2() {
        return this.fluidTank2;
    }

    public FluidStack getFluidStackfromTank() {
        return this.getFluidTank().getFluid();
    }

    public FluidStack getFluidStackfromTank1() {
        return this.getFluidTank1().getFluid();
    }

    public FluidStack getFluidStackfromTank2() {
        return this.getFluidTank2().getFluid();
    }

    public int getTankAmount() {
        return this.getFluidTank().getFluidAmount();
    }

    public int getTankAmount1() {
        return this.getFluidTank1().getFluidAmount();
    }

    public int getTankAmount2() {
        return this.getFluidTank2().getFluidAmount();
    }

    public int gaugeLiquidScaled(int i) {
        return this.getFluidTank().getFluidAmount() <= 0 ? 0 : this.getFluidTank().getFluidAmount() * i / this.getFluidTank().getCapacity();
    }

    public double gaugeLiquidScaled(double i) {
        return this.getFluidTank().getFluidAmount() <= 0 ? 0 : this.getFluidTank().getFluidAmount() * i / this.getFluidTank().getCapacity();
    }

    public double gaugeLiquidScaled1(double i) {
        return this.getFluidTank1().getFluidAmount() <= 0 ? 0 : this.getFluidTank1().getFluidAmount() * i / this.getFluidTank1().getCapacity();
    }

    public double gaugeLiquidScaled2(double i) {
        return this.getFluidTank2().getFluidAmount() <= 0 ? 0 : this.getFluidTank2().getFluidAmount() * i / this.getFluidTank2().getCapacity();
    }

    public int gaugeLiquidScaled1(int i) {
        return this.getFluidTank1().getFluidAmount() <= 0 ? 0 : this.getFluidTank1().getFluidAmount() * i / this.getFluidTank1().getCapacity();
    }

    public int gaugeLiquidScaled2(int i) {
        return this.getFluidTank2().getFluidAmount() <= 0 ? 0 : this.getFluidTank2().getFluidAmount() * i / this.getFluidTank2().getCapacity();
    }

    public boolean needsFluid() {
        return this.getFluidTank().getFluidAmount() <= this.getFluidTank().getCapacity();
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.canFill(from, resource.getFluid()) ? this.getFluidTank().fill(resource, doFill) : 0;
    }

    public void fill1(FluidStack resource, boolean doFill) {
        if (this.canFill1(resource.getFluid())) {
            this.getFluidTank1().fill(resource, doFill);
        }
    }

    public void fill2(FluidStack resource, boolean doFill) {
        if (this.canFill2(resource.getFluid())) {
            this.getFluidTank2().fill(resource, doFill);
        }
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource != null && resource.isFluidEqual(this.getFluidTank1().getFluid()) && this.getFluidTank1().getFluidAmount() > 0) {
            return !this.canDrain(from, resource.getFluid()) ? null : this.getFluidTank1().drain(resource.amount, doDrain);
        } else if (resource != null && resource.isFluidEqual(this.getFluidTank2().getFluid()) && this.getFluidTank2().getFluidAmount() > 0) {
            return !this.canDrain(from, resource.getFluid()) ? null : this.getFluidTank2().drain(resource.amount, doDrain);
        } else {
            return null;
        }
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        if (this.getFluidTank1().getFluidAmount() > 0)
            return !this.canDrain(from, null) ? null : this.getFluidTank1().drain(maxDrain, doDrain);

        return !this.canDrain(from, null) ? null : this.getFluidTank2().drain(maxDrain, doDrain);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{this.getFluidTank().getInfo(), this.getFluidTank1().getInfo(), this.getFluidTank2().getInfo()};
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

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer,
                UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }
}
