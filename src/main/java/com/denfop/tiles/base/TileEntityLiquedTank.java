package com.denfop.tiles.base;

import com.denfop.Constants;
import com.denfop.container.ContainerTank;
import com.denfop.gui.GUITank;
import com.denfop.tiles.neutroniumgenerator.TileEntityLiquidTankElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotConsumableLiquid;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.IUpgradeItem;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityLiquedTank extends TileEntityLiquidTankElectricMachine implements IHasGui, IUpgradableBlock {
    public final InvSlotUpgrade upgradeSlot;

    public final InvSlotOutput outputSlot;

    public final com.denfop.invslot.InvSlotConsumableLiquidByList containerslot;
    public final com.denfop.invslot.InvSlotConsumableLiquidByList containerslot1;
    public final ResourceLocation texture;
    private final String name;


    public TileEntityLiquedTank(String name, int tanksize, String texturename) {
        super(1000, 1, -1, tanksize);

        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.containerslot = new com.denfop.invslot.InvSlotConsumableLiquidByList(this,
                "containerslot", 2, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Fill
        );
        this.containerslot1 = new com.denfop.invslot.InvSlotConsumableLiquidByList(this,
                "containerslot1", 3, InvSlot.Access.I, 1, InvSlot.InvSide.TOP, InvSlotConsumableLiquid.OpType.Drain
        );
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 4, 4);
        this.texture = new ResourceLocation(Constants.TEXTURES,
                "textures/models/" + texturename + ".png");
        this.name = name;
    }

    private static int applyModifier(int extra) {
        double ret = Math.round((14 + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("energy");
        ret.add("fluidTank");
        return ret;
    }

    public boolean needsFluid() {
        return this.getFluidTank().getFluidAmount() <= this.getFluidTank().getCapacity();
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean needsInvUpdate = false;
        IC2.network.get().updateTileEntityField(this, "fluidTank");

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
        if (this.needsFluid()) {
            output = new MutableObject();
            if (this.containerslot1.transferToTank(this.fluidTank, output, true) && (output.getValue() == null || this.outputSlot.canAdd(output.getValue()))) {
                needsInvUpdate = this.containerslot1.transferToTank(this.fluidTank, output, false);
                if (output.getValue() != null) {
                    this.outputSlot.add(output.getValue());
                }
            }
        }
        if (needsInvUpdate)
            markDirty();

    }

    @Override
    public boolean canFill(ForgeDirection paramForgeDirection, Fluid paramFluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection paramForgeDirection, Fluid paramFluid) {
        return true;
    }

    @Override
    public String getInventoryName() {
        return StatCollector.translateToLocal(this.name);
    }

    public ContainerBase<TileEntityLiquedTank> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerTank(entityPlayer, this);
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.canFill(from, resource.getFluid()) ? this.getFluidTank().fill(resource, doFill) : 0;
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return !this.canDrain(from, null) ? null : this.getFluidTank().drain(maxDrain, doDrain);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUITank(new ContainerTank(entityPlayer, this));
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);


    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    public void setUpgradestat() {
        this.upgradeSlot.onChanged();
        setTier(applyModifier(this.upgradeSlot.extraTier));
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

    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }


    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.RedstoneSensitive, UpgradableProperty.Transformer,
                UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing, UpgradableProperty.FluidProducing);
    }
}
