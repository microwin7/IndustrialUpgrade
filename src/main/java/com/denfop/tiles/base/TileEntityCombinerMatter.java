package com.denfop.tiles.base;

import com.denfop.container.ContainerCombinerMatter;
import com.denfop.gui.GUICombinerMatter;
import com.denfop.invslot.InvSlotMatter;
import com.denfop.tiles.neutroniumgenerator.TileEntityLiquidTankElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeOutput;
import ic2.api.recipe.Recipes;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.comp.Redstone;
import ic2.core.block.invslot.*;
import ic2.core.init.BlocksItems;
import ic2.core.init.InternalName;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

public class TileEntityCombinerMatter extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock {
    public final InvSlotMatter inputSlot;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotProcessableGeneric amplifierSlot;
    public final InvSlotOutput outputSlot;
    public final InvSlotConsumableLiquid containerslot;
    protected final Redstone redstone;
    public int scrap;
    private double energycost;
    private int state, prevState;
    private AudioSource audioSource, audioSourceScrap;


    public TileEntityCombinerMatter() {
        super(0, 14, -1, 0);
        this.energycost = 0;
        this.amplifierSlot = new InvSlotProcessableGeneric(this, "scrap", 0, 1, Recipes.matterAmplifier);
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.containerslot = new InvSlotConsumableLiquidByList(this, "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill, BlocksItems.getFluid(InternalName.fluidUuMatter));
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.inputSlot = new InvSlotMatter(this, 8);

        this.redstone = addComponent(new Redstone(this));
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
        this.fluidTank.readFromNBT(nbttagcompound.getCompoundTag("fluidTank"));

        this.maxEnergy = nbttagcompound.getDouble("maxEnergy");
        this.energy = nbttagcompound.getDouble("energy");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("scrap", this.scrap);
        NBTTagCompound fluidTankTag = new NBTTagCompound();
        this.fluidTank.writeToNBT(fluidTankTag);
        if (fluidTankTag != null)
            nbttagcompound.setTag("fluidTank", fluidTankTag);
        nbttagcompound.setInteger("amount", fluidTank.getFluidAmount());
        nbttagcompound.setInteger("maxamount", fluidTank.getCapacity());
        nbttagcompound.setDouble("maxEnergy", this.inputSlot.getMaxEnergy());
        nbttagcompound.setDouble("energy", this.energy);


    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = onUpdateUpgrade();
        this.maxEnergy = this.inputSlot.getMaxEnergy();
        this.fluidTank.setCapacity(this.inputSlot.getFluidTank(this.inputSlot));
        this.energycost = this.inputSlot.getcostEnergy(this.inputSlot);
        if (this.redstone.hasRedstoneInput() || this.energy <= 0.0D) {
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
            if (fluidTank.getFluidAmount() > fluidTank.getCapacity())
                fluidTank.getFluid().amount = fluidTank.getCapacity();


            MutableObject<ItemStack> output = new MutableObject<>();
            if (this.containerslot.transferFromTank(this.fluidTank, output, true)
                    && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                this.containerslot.transferFromTank(this.fluidTank, output, false);
                if (output.getValue() != null)
                    this.outputSlot.add(output.getValue());
            }

            if (needsInvUpdate && this.worldObj.provider.getWorldTime() % 5 == 0)
                markDirty();

            if (this.energy > this.maxEnergy)
                this.energy = this.maxEnergy;
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
        this.fluidTank.setCapacity(this.inputSlot.getFluidTank(this.inputSlot));
        if (this.fluidTank.getFluidAmount() + 1 > this.fluidTank.getCapacity())
            return false;
        m = this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
        if (k > m) {
            fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), m), true);
            this.energy -= (this.energycost * m);
            return true;
        } else if (m > k) {
            fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), k), true);
            this.energy -= (this.energycost * k);
            return true;
        } else {
            fill(null, new FluidStack(BlocksItems.getFluid(InternalName.fluidUuMatter), k), true);
            this.energy -= (this.energycost * k);
            return true;
        }

    }

    public double getDemandedEnergy() {
        if (this.redstone.hasRedstoneInput())
            return 0.0D;
        return this.maxEnergy - this.energy;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        this.maxEnergy = this.inputSlot.getMaxEnergy();
        if (this.energy >= maxEnergy || this.redstone.hasRedstoneInput())
            return amount;
        int bonus = Math.min((int) amount, this.scrap);
        this.scrap -= bonus;

        if (this.energy + amount >= maxEnergy) {

            double temp = (maxEnergy - this.energy);
            this.energy += temp;
            return amount - temp;
        } else {
            this.energy += amount + (5 * bonus);
            return 0;
        }
    }

    public ContainerBase<TileEntityCombinerMatter> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerCombinerMatter(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUICombinerMatter(new ContainerCombinerMatter(entityPlayer, this));
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockCombMatter.name");
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
        setTier(applyModifier(this.getSinkTier(), this.upgradeSlot.extraTier));
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
