package com.denfop.tiles.mechanism;

import com.denfop.container.ContainerMagnet;
import com.denfop.gui.GUIMagnet;
import com.denfop.tiles.base.TileEntityElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.InvSlotOutput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileEntityMagnet extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener {

    public final int energyconsume;
    public final InvSlotOutput outputSlot;
    public AudioSource audioSource;

    public TileEntityMagnet() {
        super(100000, 14, 1);
        this.energyconsume = 1000;

        this.outputSlot = new InvSlotOutput(this, "output", 2, 24);
    }


    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack ret = super.getWrenchDrop(entityPlayer);

        for (int i = 0; i < 24; i++)
            if (this.outputSlot.get(i) != null) {
                double var8 = 0.7D;
                double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                        this.outputSlot.get(i));
                var16.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(var16);
            }
        return ret;
    }

    public void updateEntityServer() {

        super.updateEntityServer();
        int radius = 10;
        AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius, this.xCoord + radius, this.yCoord + radius, this.zCoord + radius);
        List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, axisalignedbb);

        if (worldObj.provider.getWorldTime() % 10 == 0)
            for (int x = this.xCoord - 10; x <= this.xCoord + 10; x++)
                for (int y = this.yCoord - 10; y <= this.yCoord + 10; y++)
                    for (int z = this.zCoord - 10; z <= this.zCoord + 10; z++)
                        if (worldObj.getTileEntity(x, y, z) != null)
                            if (worldObj.getTileEntity(x, y, z) instanceof TileEntityMagnet)
                                if (worldObj.getTileEntity(x, y, z) != this)
                                    return;
        if (worldObj.provider.getWorldTime() % 10 == 0)
            for (EntityItem item : list) {


                if (this.energy >= this.energyconsume) {
                    ItemStack stack = item.getEntityItem();

                    if (this.outputSlot.canAdd(stack)) {
                        setActive(true);
                        this.energy -= this.energyconsume;
                        this.outputSlot.add(stack);
                        item.setDead();
                    }


                }
            }
        if (worldObj.provider.getWorldTime() % 40 == 0)
            if (getActive())
                setActive(false);


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

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

    }

    public int getSizeInventory() {
        return 24;
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIMagnet(new ContainerMagnet(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityMagnet> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityMagnet>) new ContainerMagnet(entityPlayer, this);
    }


    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    @Override
    public void onNetworkEvent(int event) {
        if (this.audioSource == null && getStartSoundFile() != null)
            this.audioSource = IC2.audioManager.createSource(this, getStartSoundFile());
        switch (event) {
            case 0:
                if (this.audioSource != null)
                    this.audioSource.play();
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (getInterruptSoundFile() != null)
                        IC2.audioManager.playOnce(this, getInterruptSoundFile());
                }
                break;
            case 2:
                if (this.audioSource != null)
                    this.audioSource.stop();
                break;
        }
    }

    @Override
    public void onGuiClosed(EntityPlayer arg0) {
    }

    @Override
    public String getInventoryName() {
        // TODO Auto-generated method stub
        return null;
    }

}
