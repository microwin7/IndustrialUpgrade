package com.denfop.tiles.reactors;

import com.denfop.IUItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.Direction;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.reactor.IReactorChamber;
import ic2.api.tile.IWrenchable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityImpReactorChamberElectric extends TileEntity implements IHasGui, IWrenchable, IInventory, IReactorChamber, IEnergyEmitter, IFluidHandler {
    public boolean redpowert = false;
    private short ticker = 0;
    private boolean loaded = false;

    public TileEntityImpReactorChamberElectric() {
    }

    public void validate() {
        super.validate();
        IC2.tickHandler.addSingleTickCallback(this.worldObj, world -> {
            if (!TileEntityImpReactorChamberElectric.this.isInvalid() && world.blockExists(TileEntityImpReactorChamberElectric.this.xCoord, TileEntityImpReactorChamberElectric.this.yCoord, TileEntityImpReactorChamberElectric.this.zCoord)) {
                TileEntityImpReactorChamberElectric.this.onLoaded();
                if (TileEntityImpReactorChamberElectric.this.enableUpdateEntity()) {
                    world.loadedTileEntityList.add(TileEntityImpReactorChamberElectric.this);
                }
            }
        });
    }

    public void onLoaded() {
        if (IC2.platform.isSimulating()) {
            TileEntityImpNuclearReactor te = this.getReactor();
            if (te != null) {
                te.refreshChambers();
            }
        }

        this.loaded = true;
    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.worldObj.blockExists(this.xCoord, this.yCoord, this.zCoord)) {
            TileEntityImpNuclearReactor te = this.getReactor();
            if (te != null) {
                te.refreshChambers();
            }
        }

        this.loaded = false;
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public void invalidate() {
        super.invalidate();
        if (this.loaded) {
            this.onUnloaded();
        }

    }

    public void onChunkUnload() {
        super.onChunkUnload();
        if (this.loaded) {
            this.onUnloaded();
        }

    }

    public void updateEntity() {
        super.updateEntity();
        if (this.ticker == 19) {
            if (this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord)) {
                if (!this.redpowert) {
                    this.redpowert = true;
                    this.setRedstoneSignal(true);
                }
            } else if (this.redpowert) {
                this.redpowert = false;
                this.setRedstoneSignal(false);
            }

            this.ticker = 0;
        }

        ++this.ticker;
    }

    public final boolean canUpdate() {
        return true;
    }

    public boolean enableUpdateEntity() {
        return true;
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    public short getFacing() {
        return 0;
    }

    public void setFacing(short facing) {
    }

    public boolean wrenchCanRemove(EntityPlayer entityPlayer) {
        return true;
    }

    public float getWrenchDropRate() {
        return 0.8F;
    }

    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return  new ItemStack(IUItem.impchamberblock);
    }

    public int getSizeInventory() {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? 0 : reactor.getSizeInventory();
    }

    public ItemStack getStackInSlot(int i) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.getStackInSlot(i);
    }

    public ItemStack decrStackSize(int i, int j) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.decrStackSize(i, j);
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        if (reactor != null) {
            reactor.setInventorySlotContents(i, itemstack);
        }
    }

    public String getInventoryName() {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? "Nuclear Reactor" : reactor.getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? 64 : reactor.getInventoryStackLimit();
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor != null && reactor.isUseableByPlayer(entityplayer);
    }

    public void openInventory() {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        if (reactor != null) {
            reactor.openInventory();
        }
    }

    public void closeInventory() {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        if (reactor != null) {
            reactor.closeInventory();
        }
    }

    public ItemStack getStackInSlotOnClosing(int var1) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.getStackInSlotOnClosing(var1);
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor != null && reactor.isItemValidForSlot(i, itemstack);
    }

    public TileEntityImpNuclearReactor getReactor() {
        Direction[] var1 = Direction.directions;


        for (Direction value : var1) {
            TileEntity te = value.applyToTileEntity(this);
            if (te instanceof TileEntityImpNuclearReactor) {
                return (TileEntityImpNuclearReactor) te;
            }
        }

        Block blk = this.getBlockType();
        if (blk != null) {
            blk.onNeighborBlockChange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, blk);
        }

        return null;
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? 0 : reactor.fill(from, resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.drain(from, resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.drain(from, maxDrain, doDrain);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor != null && reactor.canFill(from, fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor != null && reactor.canDrain(from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.getTankInfo(from);
    }

    public ContainerBase<?> getGuiContainer(EntityPlayer entityPlayer) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.getGuiContainer(entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        return reactor == null ? null : reactor.getGui(entityPlayer, isAdmin);
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        if (reactor != null) {
            reactor.onGuiClosed(entityPlayer);
        }
    }

    public void setRedstoneSignal(boolean redstone) {
        TileEntityImpNuclearReactor reactor = this.getReactor();
        if (reactor != null) {
            reactor.setRedstoneSignal(redstone);
        }
    }
}
