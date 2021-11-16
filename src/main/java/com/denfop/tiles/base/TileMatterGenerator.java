package com.denfop.tiles.base;

import com.denfop.Config;
import com.denfop.container.ContainerSolidMatter;
import com.denfop.gui.GUISolidMatter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.Set;

public class TileMatterGenerator extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, IUpgradableBlock {

    public final InvSlotOutput outputSlot;
    public final ItemStack itemstack;
    public final InvSlotUpgrade upgradeSlot;
    private final int defaultTier;
    private final String name;
    public AudioSource audioSource;
    private double progress;


    public TileMatterGenerator(ItemStack itemstack, String name) {
        super(Config.SolidMatterStorage, 10, 0);
        this.itemstack = itemstack;
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.progress = 0;
        defaultTier = 10;
        this.name = name;
    }


    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {

        if (this.energy >= maxEnergy)
            return amount;

        if (this.energy + amount >= maxEnergy) {

            double temp = (maxEnergy - this.energy);
            this.energy += temp;
            return amount - temp;
        } else {
            this.energy += amount;
            return 0;
        }
    }

    public void updateEntityServer() {

        super.updateEntityServer();

        boolean needsInvUpdate = false;
        if (this.energy > 0) {
            this.progress = this.energy / this.maxEnergy;
            if (this.energy >= this.maxEnergy) {
                if (this.outputSlot.canAdd(itemstack)) {
                    this.outputSlot.add(itemstack);
                    this.energy = 0;
                    this.progress = 0;
                }
            }

        }
        for (int i = 0; i < this.upgradeSlot.size(); i++) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null && stack.getItem() instanceof IUpgradeItem && (
                    (IUpgradeItem) stack.getItem()).onTick(stack, this))
                needsInvUpdate = true;
        }

        if (needsInvUpdate && this.worldObj.provider.getWorldTime() % 10 == 0)
            markDirty();
    }


    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    private void setUpgradestat() {
        this.upgradeSlot.onChanged();
        if (this.upgradeSlot.extraTier != 0)
            setTier(this.defaultTier + this.upgradeSlot.extraTier);


    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer,
                UpgradableProperty.ItemProducing);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getDouble("progress");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setDouble("progress", this.progress);

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

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUISolidMatter(new ContainerSolidMatter(entityPlayer, this));
    }

    public ContainerBase<? extends TileMatterGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileMatterGenerator>) new ContainerSolidMatter(entityPlayer, this);
    }


    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public String getStartSoundFile() {
        return null;
    }

    public String getInterruptSoundFile() {
        return null;
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

        return StatCollector.translateToLocal(this.name);
    }


    public double getProgress() {

        return this.progress;
    }

}
