package com.denfop.tiles.base;

import com.denfop.container.ContainerGeoGenerator;
import com.denfop.gui.GUIGeoGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityLiquidTankInventory;
import ic2.core.block.invslot.InvSlotCharge;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotConsumableLiquidByList;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidEvent;
import net.minecraftforge.fluids.FluidEvent.FluidSpilledEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

public class TileEntityGeoGenerator extends TileEntityLiquidTankInventory implements IEnergySource, IHasGui {
    public final InvSlotCharge chargeSlot = new InvSlotCharge(this, 0, 1);
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput outputSlot;
    public final double maxStorage;
    public final int production = Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/geothermal"));
    private final double coef;
    private final String name;
    public double storage = 0.0D;
    public boolean addedToEnergyNet = false;
    public AudioSource audioSource;

    public TileEntityGeoGenerator(int size, double coef, String name) {
        super(size);
        this.coef = coef;
        this.name = name;
        maxStorage = 24000 * coef;
        this.fluidSlot = new InvSlotConsumableLiquidByList(this, "fluidSlot", 1, 1, FluidRegistry.LAVA);
        this.outputSlot = new InvSlotOutput(this, "output", 2, 1);
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal(name);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        try {
            this.storage = nbttagcompound.getDouble("storage");
        } catch (Exception var3) {
            this.storage = nbttagcompound.getShort("storage");
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("storage", this.storage);
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        if (this.needsFluid()) {
            MutableObject<ItemStack> output = new MutableObject<>();
            if (this.fluidSlot.transferToTank(this.fluidTank, output, true) && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                needsInvUpdate = this.fluidSlot.transferToTank(this.fluidTank, output, false);
                if (output.getValue() != null) {
                    this.outputSlot.add(output.getValue());
                }
            }
        }

        boolean newActive = this.gainEnergy();
        if (this.storage > this.maxStorage) {
            this.storage = this.maxStorage;
        }

        if (this.storage >= 1.0D && this.chargeSlot.get() != null) {
            double used = ElectricItem.manager.charge(this.chargeSlot.get(), this.storage, 1, false, false);
            this.storage -= used;
            if (used > 0.0D) {
                needsInvUpdate = true;
            }
        }

        if (needsInvUpdate) {
            this.markDirty();
        }

        if (this.getActive() != newActive) {
            this.setActive(newActive);
        }

    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }

        super.onUnloaded();
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return Math.min(this.storage, EnergyNet.instance.getPowerFromTier(this.getSourceTier()));
    }

    public int getSourceTier() {
        return 1;
    }

    public void drawEnergy(double amount) {
        this.storage -= amount;
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public void onNetworkUpdate(String field) {
        if (field.equals("active") && this.prevActive != this.getActive()) {
            if (this.audioSource == null && this.getOperationSoundFile() != null) {
                this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, this.getOperationSoundFile(), true, false, IC2.audioManager.getDefaultVolume());
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

    public float getWrenchDropRate() {
        return 0.9F;
    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            this.storage += this.production * coef;
            this.getFluidTank().drain(2, true);
            return true;
        } else {
            return false;
        }
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == FluidRegistry.LAVA;
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    public boolean isConverting() {
        return this.getTankAmount() > 0 && this.storage + (double) this.production <= this.maxStorage;
    }

    public int gaugeStorageScaled(int i) {
        return (int) (this.storage * (double) i / this.maxStorage);
    }


    public String getOperationSoundFile() {
        return "Generators/GeothermalLoop.ogg";
    }

    public ContainerBase<TileEntityGeoGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerGeoGenerator(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIGeoGenerator(new ContainerGeoGenerator(entityPlayer, this));
    }

    public void onBlockBreak(Block block, int meta) {
        FluidEvent.fireEvent(new FluidSpilledEvent(new FluidStack(FluidRegistry.LAVA, 1000), this.worldObj, this.xCoord, this.yCoord, this.zCoord));
    }
}
