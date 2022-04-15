package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.block.base.BlocksItems;
import com.denfop.container.ContainerOilGetter;
import com.denfop.gui.GUIOilGetter;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.*;

public class TileEntityOilGetter extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock, INetworkTileEntityEventListener {
    public static int heading;
    public final int defaultTier;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotConsumableLiquid containerslot;
    public int number;
    public int max;
    public boolean notoil = true;
    private AudioSource audioSource;

    public TileEntityOilGetter() {
        super(50000, 14, -1, 20);

        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.containerslot = new InvSlotConsumableLiquidByList(this,
                "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill,
                BlocksItems.getFluid("fluidneft"));
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.defaultTier = 3;
    }

    private static int applyModifier(int base, int extra) {
        double ret = Math.round((base + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.fluidTank.readFromNBT(nbttagcompound.getCompoundTag("fluidTank"));
        this.number = nbttagcompound.getInteger("number");
        this.max = nbttagcompound.getInteger("max");
        this.notoil = nbttagcompound.getBoolean("notoil");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        nbttagcompound.setTag("fluidTank", fluidTankTag);
        nbttagcompound.setInteger("number", number);
        nbttagcompound.setInteger("max", max);
        nbttagcompound.setBoolean("notoil", notoil);
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
        MutableObject<ItemStack> output = new MutableObject();
        if (this.containerslot.transferFromTank(this.fluidTank, output, true)
                && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
            this.containerslot.transferFromTank(this.fluidTank, output, false);

            if (output.getValue() != null)
                this.outputSlot.add(output.getValue());
        }
        get_oil_max();
        if (this.energy >= 10 && !notoil) {
            get_oil();
            initiate(0);
        } else {
            initiate(2);
        }
        if (worldObj.provider.getWorldTime() % 60 == 0)
            initiate(2);
        if (needsInvUpdate)
            markDirty();

    }

    private void get_oil_max() {
        Map map = this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).chunkTileEntityMap;
        for (Object o : map.values()) {
            TileEntity tile = (TileEntity) o;
            if (tile instanceof TileOilBlock) {
                TileOilBlock tile1 = (TileOilBlock) tile;
                this.max = tile1.max;
                this.number = tile1.number;
                notoil = false;
                return;
            } else {
                notoil = true;
            }
        }
    }

    private void get_oil() {
        Map map = this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).chunkTileEntityMap;
        for (Object o : map.values()) {
            TileEntity tile = (TileEntity) o;
            if (tile instanceof TileOilBlock) {
                TileOilBlock tile1 = (TileOilBlock) tile;
                if (tile1.number >= 1) {
                    if (this.fluidTank.getFluidAmount() + 1 <= this.fluidTank.getCapacity()) {
                        fill(null, new FluidStack(BlocksItems.getFluid("fluidneft"), 1), true);
                        tile1.number -= 1;
                        this.energy -= 10;
                    }
                }
            }

        }
    }

    public void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
        super.onUnloaded();
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

    public ContainerBase<TileEntityOilGetter> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerOilGetter(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIOilGetter(new ContainerOilGetter(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public List<String> getNetworkedFields() {
        List<String> ret = new Vector<>(1);
        ret.addAll(super.getNetworkedFields());
        return ret;
    }

    public void onNetworkUpdate(String field) {

        super.onNetworkUpdate(field);
    }

    public float getWrenchDropRate() {
        return 0.7F;
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (fluid == BlocksItems.getFluid("fluidneft"));
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

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/oilgetter.ogg";
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

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer,
                UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }
}
