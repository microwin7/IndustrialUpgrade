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

public class TileEntityAdvReactorChamberElectric extends TileEntity implements IHasGui, IWrenchable, IInventory, IReactorChamber, IEnergyEmitter {
    public boolean redpowert = false;
    private short ticker = 0;
    private boolean loaded = false;

    public TileEntityAdvReactorChamberElectric() {
    }

    public void validate() {
        super.validate();
        IC2.tickHandler.addSingleTickCallback(this.worldObj, world -> {
            if (!TileEntityAdvReactorChamberElectric.this.isInvalid() && world.blockExists(TileEntityAdvReactorChamberElectric.this.xCoord, TileEntityAdvReactorChamberElectric.this.yCoord, TileEntityAdvReactorChamberElectric.this.zCoord)) {
                TileEntityAdvReactorChamberElectric.this.onLoaded();
                if (TileEntityAdvReactorChamberElectric.this.enableUpdateEntity()) {
                    world.loadedTileEntityList.add(TileEntityAdvReactorChamberElectric.this);
                }

            }
        });
    }

    public void onLoaded() {
        if (IC2.platform.isSimulating()) {
            TileEntityAdvNuclearReactorElectric te = this.getReactor();
            if (te != null) {
                te.refreshChambers();
            }
        }

        this.loaded = true;
    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.worldObj.blockExists(this.xCoord, this.yCoord, this.zCoord)) {
            TileEntityAdvNuclearReactorElectric te = this.getReactor();
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
        return new ItemStack(IUItem.advchamberblock);
    }

    public int getSizeInventory() {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? 0 : reactor.getSizeInventory();
    }

    public ItemStack getStackInSlot(int i) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? null : reactor.getStackInSlot(i);
    }

    public ItemStack decrStackSize(int i, int j) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? null : reactor.decrStackSize(i, j);
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        if (reactor != null) {
            reactor.setInventorySlotContents(i, itemstack);
        }
    }

    public String getInventoryName() {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? "Nuclear Reactor" : reactor.getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? 64 : reactor.getInventoryStackLimit();
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor != null && reactor.isUseableByPlayer(entityplayer);
    }

    public void openInventory() {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        if (reactor != null) {
            reactor.openInventory();
        }
    }

    public void closeInventory() {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        if (reactor != null) {
            reactor.closeInventory();
        }
    }

    public ItemStack getStackInSlotOnClosing(int var1) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? null : reactor.getStackInSlotOnClosing(var1);
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor != null && reactor.isItemValidForSlot(i, itemstack);
    }

    public TileEntityAdvNuclearReactorElectric getReactor() {
        Direction[] var1 = Direction.directions;

        for (Direction value : var1) {
            TileEntity te = value.applyToTileEntity(this);
            if (te instanceof TileEntityAdvNuclearReactorElectric) {
                return (TileEntityAdvNuclearReactorElectric) te;
            }
        }

        Block blk = this.getBlockType();
        if (blk != null) {
            blk.onNeighborBlockChange(this.worldObj, this.xCoord, this.yCoord, this.zCoord, blk);
        }

        return null;
    }


    public ContainerBase<?> getGuiContainer(EntityPlayer entityPlayer) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? null : reactor.getGuiContainer(entityPlayer);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        return reactor == null ? null : reactor.getGui(entityPlayer, isAdmin);
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        if (reactor != null) {
            reactor.onGuiClosed(entityPlayer);
        }
    }

    public void setRedstoneSignal(boolean redstone) {
        TileEntityAdvNuclearReactorElectric reactor = this.getReactor();
        if (reactor != null) {
            reactor.setRedstoneSignal(redstone);
        }
    }
}
