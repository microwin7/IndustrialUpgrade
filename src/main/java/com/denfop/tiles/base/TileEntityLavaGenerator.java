package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerLavaGenerator;
import com.denfop.gui.GUILavaGenerator;
import com.denfop.tiles.neutroniumgenerator.TileEntityLiquidTankElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.*;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class TileEntityLavaGenerator extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock, INetworkTileEntityEventListener {
    public final int defaultTier;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotConsumableLiquid containerslot;
    private final double costenergy;
    private int state;
    private int prevState;
    private AudioSource audioSource;


    public TileEntityLavaGenerator() {
        super(500, 14, -1, 12);

        this.costenergy = 20;
        this.state = 0;
        this.prevState = 0;
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.containerslot = new InvSlotConsumableLiquidByList(this,
                "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill,
                FluidRegistry.LAVA);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.defaultTier = 3;
    }

    private static int applyModifier(int base, int extra) {
        double ret = Math.round((base + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
    }

    public String getInventoryName() {
        return null;
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        for (int i = 0; i < this.upgradeSlot.size(); i++) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null && stack.getItem() instanceof IUpgradeItem)
                if (((IUpgradeItem) stack.getItem()).onTick(stack, this))
                    needsInvUpdate = true;
        }
        if (this.energy <= 0.0D) {
            setState();
            setActive(false);
        } else {

            setActive(true);

            if (this.energy >= this.costenergy) {
                needsInvUpdate = attemptGeneration();
                initiate(0);
            } else {
                initiate(2);
            }
            MutableObject<ItemStack> output = new MutableObject();
            if (this.containerslot.transferFromTank(this.fluidTank, output, true)
                    && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                this.containerslot.transferFromTank(this.fluidTank, output, false);
                if (output.getValue() != null)
                    this.outputSlot.add(output.getValue());
            }
            if (needsInvUpdate)
                markDirty();
        }
    }

    public void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
    }

    public boolean attemptGeneration() {
        //
        int k = (int) (this.energy / this.costenergy);
        int m;

        if (this.fluidTank.getFluidAmount() + 1 > this.fluidTank.getCapacity())
            return false;
        m = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
        if (k > m) {
            fill(null, new FluidStack(FluidRegistry.LAVA, m), true);
            this.energy -= (this.costenergy * m);
            return true;
        } else if (m > k) {
            fill(null, new FluidStack(FluidRegistry.LAVA, k), true);
            this.energy -= (this.costenergy * k);
            return true;
        } else {
            fill(null, new FluidStack(FluidRegistry.LAVA, k), true);
            this.energy -= (this.costenergy * k);
            return true;
        }


    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (this.energy >= this.maxEnergy)
            return amount;

        this.energy += amount;
        return 0.0D;
    }

    public String getProgressAsString() {
        int p = Math.min((int) (this.energy * 100.0D / this.costenergy), 100);
        return "" + p + "%";
    }

    public ContainerBase<TileEntityLavaGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerLavaGenerator(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUILavaGenerator(new ContainerLavaGenerator(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    private void setState() {
        this.state = 0;
        if (this.prevState != this.state)
            IC2.network.get().updateTileEntityField(this, "state");
        this.prevState = this.state;
    }

    public List<String> getNetworkedFields() {
        List<String> ret = new Vector<>(1);
        ret.add("state");
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/gen_lava.ogg";
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
        return 0.7F;
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (fluid == FluidRegistry.LAVA);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        setTier(applyModifier(this.defaultTier, this.upgradeSlot.extraTier));
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
