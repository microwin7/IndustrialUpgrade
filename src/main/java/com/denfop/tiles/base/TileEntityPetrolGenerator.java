package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.api.IFluidItem;
import com.denfop.audio.AudioSource;
import com.denfop.block.base.BlocksItems;
import com.denfop.container.ContainerPetrolGenerator;
import com.denfop.gui.GUIPetrolGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
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
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

public class TileEntityPetrolGenerator extends TileEntityLiquidTankInventory implements IEnergySource, IHasGui, INetworkTileEntityEventListener {
    public final InvSlotCharge chargeSlot = new InvSlotCharge(this, 0, 1);
    public final InvSlotConsumableLiquid fluidSlot;
    public final InvSlotOutput outputSlot;
    public final double maxStorage;
    public final int production = Math.round(20.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/geothermal"));
    private final double coef;
    private final String name = null;
    public double storage = 0.0D;
    public boolean addedToEnergyNet = false;
    public AudioSource audioSource;

    public TileEntityPetrolGenerator() {
        super(12);
        this.coef = 1;
        maxStorage = 32000 * coef;
        this.fluidSlot = new InvSlotConsumableLiquidByList(this, "fluidSlot", 1, 1, BlocksItems.getFluid("fluidbenz"));
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
                ItemStack stack = this.fluidSlot.get();
                needsInvUpdate = this.fluidSlot.transferToTank(this.fluidTank, output, false);
                if (output.getValue() != null) {
                    this.outputSlot.add(output.getValue());
                } else if (stack.getItem() instanceof IFluidItem)
                    if (this.outputSlot.canAdd(((IFluidItem) stack.getItem()).getItemEmpty()))
                        this.outputSlot.add(((IFluidItem) stack.getItem()).getItemEmpty());
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
        if (worldObj.provider.getWorldTime() % 300 == 0)
            initiate(2);
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
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }

        super.onUnloaded();
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return Math.min(this.storage, 4096);
    }

    public int getSourceTier() {
        return 1;
    }

    public void drawEnergy(double amount) {
        this.storage -= amount;
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/petrol_generator.ogg";
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

    public boolean gainEnergy() {
        if (this.isConverting()) {
            this.storage += this.production * coef;
            this.getFluidTank().drain(2, true);
            initiate(0);
            return true;
        } else {
            initiate(2);
            return false;
        }
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == BlocksItems.getFluid("fluidbenz");
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


    public ContainerBase<TileEntityPetrolGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerPetrolGenerator(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIPetrolGenerator(new ContainerPetrolGenerator(entityPlayer, this));
    }

    public void onBlockBreak(Block block, int meta) {
        FluidEvent.fireEvent(new FluidSpilledEvent(new FluidStack(BlocksItems.getFluid("fluidbenz"), 1000), this.worldObj, this.xCoord, this.yCoord, this.zCoord));
    }
}
