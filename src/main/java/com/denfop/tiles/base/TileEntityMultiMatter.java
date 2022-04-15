package com.denfop.tiles.base;

import com.denfop.container.ContainerMultiMatter;
import com.denfop.gui.GUIMultiMatter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityLiquidTankElectricMachine;
import ic2.core.block.invslot.*;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import ic2.core.init.MainConfig;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public abstract class TileEntityMultiMatter extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock, INetworkClientTileEntityEventListener {
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotProcessableGeneric amplifierSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotConsumableLiquid containerslot;
    private final float energycost;
    public int scrap;
    public boolean work;
    private int state, prevState;
    private AudioSource audioSource, audioSourceScrap;


    public TileEntityMultiMatter(float storageEnergy, int sizeTank, float maxtempEnergy) {
        super(Math.round(maxtempEnergy * ConfigUtil.getFloat(MainConfig.get(), "balance/uuEnergyFactor")), 3, -1, sizeTank);

        this.energycost = storageEnergy;
        this.amplifierSlot = new InvSlotProcessableGeneric(this, "scrap", 0, 1, Recipes.matterAmplifier);
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.containerslot = new InvSlotConsumableLiquidByList(this, "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, BlocksItems.getFluid(InternalName.fluidUuMatter));
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);

        this.work = true;
    }

    private static int applyModifier(int base, int extra) {
        double ret = Math.round((base + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        try {
            this.scrap = nbttagcompound.getInteger("scrap");
        } catch (Throwable e) {
            this.scrap = nbttagcompound.getShort("scrap");
        }
        this.work = nbttagcompound.getBoolean("work");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("scrap", this.scrap);
        nbttagcompound.setBoolean("work", this.work);

    }

    public void updateEntityServer() {
        super.updateEntityServer();

        boolean needsInvUpdate = onUpdateUpgrade();

        if (!this.work || this.energy <= 0.0D) {
            setState(0);
            setActive(false);
        } else {
            setState((this.scrap > 0) ? 2 : 1);
            setActive(true);

            if (this.scrap < 10000) {
                RecipeOutput amplifier = this.amplifierSlot.process();
                if (amplifier != null) {
                    this.amplifierSlot.consume();
                    this.scrap += amplifier.metadata.getInteger("amplification");
                }
            }

            if (this.energy >= this.energycost)
                needsInvUpdate = attemptGeneration();

            MutableObject<ItemStack> output = new MutableObject<>();
            if (this.containerslot.transferFromTank(this.fluidTank, output, true)
                    && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                this.containerslot.transferFromTank(this.fluidTank, output, false);
                if (output.getValue() != null)
                    this.outputSlot.add(output.getValue());
            }

            if (needsInvUpdate && this.worldObj.provider.getWorldTime() % 10 == 0)
                markDirty();
        }
    }

    public boolean onUpdateUpgrade() {
        for (int i = 0; i < this.upgradeSlot.size(); i++) {

            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null)
                return ((IUpgradeItem) stack.getItem()).onTick(stack, this);
        }
        return false;
    }

    public void onUnloaded() {
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
            this.audioSourceScrap = null;
        }
        super.onUnloaded();
    }

    public boolean attemptGeneration() {
        int k = (int) (this.energy / this.energycost);
        int m;

        if (this.fluidTank.getFluidAmount() + 1 > this.fluidTank.getCapacity())
            return false;
        m = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();

        fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), Math.min(m, k)), true);
        this.energy -= (this.energycost * Math.min(m, k));
        return true;


    }

    public double getDemandedEnergy() {
        if (!this.work)
            return 0.0D;
        return this.maxEnergy - this.energy;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (this.energy >= this.maxEnergy && !this.work)
            return amount;
        int bonus = Math.min((int) amount, this.scrap);
        this.scrap -= bonus;

        if (this.energy + amount >= this.maxEnergy) {

            double temp = (this.maxEnergy - this.energy);
            this.energy += temp;
            return amount - temp;
        } else {
            this.energy += amount + (5 * bonus);
            return 0;
        }
    }

    public String getProgressAsString() {
        int p = Math.min((int) (this.energy * 100.0D / this.energycost), 100);
        return "" + p + "%";
    }

    public ContainerBase<TileEntityMultiMatter> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerMultiMatter(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIMultiMatter(new ContainerMultiMatter(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    private void setState(int aState) {
        this.state = aState;
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

    public void onNetworkUpdate(String field) {
        if (field.equals("state") && this.prevState != this.state) {
            switch (this.state) {
                case 0:
                    if (this.audioSource != null)
                        this.audioSource.stop();
                    if (this.audioSourceScrap != null)
                        this.audioSourceScrap.stop();
                    break;
                case 1:
                    if (this.audioSource == null)
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    if (this.audioSource != null)
                        this.audioSource.play();
                    if (this.audioSourceScrap != null)
                        this.audioSourceScrap.stop();
                    break;
                case 2:
                    if (this.audioSource == null)
                        this.audioSource = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    if (this.audioSourceScrap == null)
                        this.audioSourceScrap = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/MassFabricator/MassFabScrapSolo.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    if (this.audioSource != null)
                        this.audioSource.play();
                    if (this.audioSourceScrap != null)
                        this.audioSourceScrap.play();
                    break;
            }
            this.prevState = this.state;
        }
        super.onNetworkUpdate(field);
    }

    public float getWrenchDropRate() {
        return 0.7F;
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (fluid == BlocksItems.getFluid(InternalName.fluidUuMatter));
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
        setTier(applyModifier(3, this.upgradeSlot.extraTier));
    }

    public double getEnergy() {
        return this.energy;
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return !this.canDrain(from, null) ? null : this.getFluidTank().drain(maxDrain, doDrain);
    }

    public boolean useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        }
        return false;
    }

    public void onNetworkEvent(EntityPlayer player, int event) {

        this.work = !this.work;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer,
                UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }

}
