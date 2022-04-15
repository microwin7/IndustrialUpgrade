package com.denfop.integration.thaumcraft;

import com.denfop.Config;
import com.denfop.container.ContainerAspectGenerator;
import com.denfop.gui.GUIAspectGenerator;
import com.denfop.tiles.base.TileEntityElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.*;

public class TileEntityAspectGenerator extends TileEntityElectricMachine implements IAspectSource, IEssentiaTransport, IHasGui {
    public final ThaumSlot inputSlot;
    public final int cost;
    public final int maxAmount = 640;
    public int amount = 0;
    public Aspect aspect = null;
    public Aspect aspectFilter = null;

    public TileEntityAspectGenerator() {
        super(100000, 14, -1);
        this.inputSlot = new ThaumSlot(this);
        this.cost = Config.cost_aspect;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.aspect = Aspect.getAspect(nbttagcompound.getString("Aspect"));
        this.aspectFilter = Aspect.getAspect(nbttagcompound.getString("AspectFilter"));
        this.amount = nbttagcompound.getShort("Amount");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("energy", this.energy);
        if (this.aspect != null) {
            nbttagcompound.setString("Aspect", this.aspect.getTag());
        }

        if (this.aspectFilter != null) {
            nbttagcompound.setString("AspectFilter", this.aspectFilter.getTag());
        }

        nbttagcompound.setShort("Amount", (short) this.amount);
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        if (this.aspect == null)
            this.amount = 0;
        if (!this.inputSlot.isEmpty()) {
            IEssentiaContainerItem item = (IEssentiaContainerItem) this.inputSlot.get().getItem();
            this.aspect = item.getAspects(this.inputSlot.get()).getAspects()[0];
        } else {
            this.aspect = null;
        }

        if (this.amount < this.maxAmount) {
            this.fillJar();
        }
        if (this.energy >= this.cost && amount < maxAmount) {
            this.amount++;
            this.energy -= this.cost;
        }
    }

    void fillJar() {
        TileEntity te = ThaumcraftApiHelper.getConnectableTile(this.worldObj, this.xCoord, this.yCoord, this.zCoord, ForgeDirection.UP);
        if (te != null) {
            IEssentiaTransport ic = (IEssentiaTransport) te;
            if (!ic.canOutputTo(ForgeDirection.DOWN)) {
                return;
            }

            Aspect ta = null;
            if (this.aspectFilter != null) {
                ta = this.aspectFilter;
            } else if (this.aspect != null && this.amount > 0) {
                ta = this.aspect;
            } else if (ic.getEssentiaAmount(ForgeDirection.DOWN) > 0 && ic.getSuctionAmount(ForgeDirection.DOWN) < this.getSuctionAmount(ForgeDirection.UP) && this.getSuctionAmount(ForgeDirection.UP) >= ic.getMinimumSuction()) {
                ta = ic.getEssentiaType(ForgeDirection.DOWN);
            }

            if (ta != null && ic.getSuctionAmount(ForgeDirection.DOWN) < this.getSuctionAmount(ForgeDirection.UP)) {
                this.addToContainer(ta, ic.takeEssentia(ta, 1, ForgeDirection.DOWN));
            }
        }

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public AspectList getAspects() {
        AspectList al = new AspectList();
        if (this.aspect != null && this.amount > 0) {
            al.add(this.aspect, this.amount);
        }

        return al;
    }

    @Override
    public void setAspects(AspectList aspectList) {

    }

    public Aspect getEssentiaType(ForgeDirection loc) {
        return this.aspect;
    }

    public int getEssentiaAmount(ForgeDirection loc) {
        return this.amount;
    }

    public boolean takeFromContainer(AspectList ot) {
        return false;
    }

    public boolean doesContainerContainAmount(Aspect tag, int amt) {
        return this.amount >= amt && tag == this.aspect;
    }

    public boolean doesContainerContain(AspectList ot) {
        Aspect[] arr$ = ot.getAspects();

        for (Aspect tt : arr$) {
            if (this.amount > 0 && tt == this.aspect) {
                return true;
            }
        }

        return false;
    }

    public int containerContains(Aspect tag) {
        return 0;
    }

    public boolean doesContainerAccept(Aspect tag) {
        return this.aspectFilter == null || tag.equals(this.aspectFilter);
    }

    public boolean isConnectable(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    public boolean canInputFrom(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }

    public boolean canOutputTo(ForgeDirection face) {
        return face == ForgeDirection.UP;
    }


    @Override
    public int addToContainer(Aspect aspect, int i) {
        return i;
    }

    public Aspect getSuctionType(ForgeDirection loc) {
        return this.aspectFilter != null ? this.aspectFilter : this.aspect;
    }

    public int getSuctionAmount(ForgeDirection loc) {
        if (this.amount < this.maxAmount) {
            return this.aspectFilter != null ? 64 : 32;
        } else {
            return 0;
        }
    }

    @Override
    public boolean takeFromContainer(Aspect tt, int am) {
        if (this.amount >= am && tt == this.aspect) {
            this.amount -= am;
            if (this.amount <= 0) {
                this.aspect = null;
                this.amount = 0;
            }

            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    public int takeEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canOutputTo(face) && this.takeFromContainer(aspect, amount) ? amount : 0;
    }

    public int addEssentia(Aspect aspect, int amount, ForgeDirection face) {
        return this.canInputFrom(face) ? amount - this.addToContainer(aspect, amount) : 0;
    }


    @Override
    public void setSuction(Aspect aspect, int i) {

    }

    public int getMinimumSuction() {
        return this.aspectFilter != null ? 64 : 32;
    }


    @Override
    public boolean renderExtendedTube() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIAspectGenerator(new ContainerAspectGenerator(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityAspectGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityAspectGenerator>) new ContainerAspectGenerator(entityPlayer, this);
    }

    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {

    }
}
