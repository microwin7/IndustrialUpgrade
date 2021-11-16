package com.denfop.tiles.base;

import com.denfop.container.ContainerCombinerSolidMatter;
import com.denfop.gui.GUICombinerSolidMatter;
import com.denfop.invslot.InvSlotSolidMatter;
import com.denfop.item.mechanism.ItemSolidMatter;
import com.denfop.tiles.solidmatter.EnumSolidMatter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityCombinerSolidMatter extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, IUpgradableBlock {


    public final InvSlotSolidMatter inputSlot;
    public final InvSlotUpgrade upgradeSlot;
    public final InvSlotOutput outputSlot;

    public TileEntityCombinerSolidMatter() {
        super(0, 14, 0);
        this.inputSlot = new InvSlotSolidMatter(this, 8);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 4);
        this.outputSlot = new InvSlotOutput(this, "output", 2, 9);

    }

    private static int applyModifier(int base, int extra) {
        double ret = Math.round((base + extra) * 1.0);
        return (ret > 2.147483647E9D) ? Integer.MAX_VALUE : (int) ret;
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        boolean update = onUpdateUpgrade();
        this.maxEnergy = this.inputSlot.getMaxEnergy();
        EnumSolidMatter[] solid = new EnumSolidMatter[0];
        for (int i = 0; i < this.inputSlot.size(); i++) {
            if (this.inputSlot.get(i) != null) {
                EnumSolidMatter[] solid1 = solid;
                solid = new EnumSolidMatter[solid.length + 1];
                solid[solid.length - 1] = ItemSolidMatter.getsolidmatter(this.inputSlot.get(i).getItemDamage());
                System.arraycopy(solid1, 0, solid, 0, solid1.length);
            }
        }
        if (this.energy == this.maxEnergy) {
            if (solid.length > 0) {
                for (EnumSolidMatter enumSolidMatter : solid) {
                    if (!this.outputSlot.canAdd(enumSolidMatter.stack))
                        continue;

                    this.outputSlot.add(enumSolidMatter.stack);

                    this.energy = 0;
                }
            }
        }
        if (update)
            markDirty();

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

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.maxEnergy = nbttagcompound.getDouble("maxEnergy");
        this.energy = nbttagcompound.getDouble("energy");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("maxEnergy", this.inputSlot.getMaxEnergy());
        nbttagcompound.setDouble("energy", this.energy);


    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @Override
    public double getEnergy() {
        return 0;
    }

    @Override
    public boolean useEnergy(double v) {
        return false;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer,
                UpgradableProperty.ItemProducing);
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        this.maxEnergy = this.inputSlot.getMaxEnergy();
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

    public boolean onUpdateUpgrade() {
        for (int i = 0; i < this.upgradeSlot.size(); i++) {
            ItemStack stack = this.upgradeSlot.get(i);
            if (stack != null)
                return true;
        }
        for (int i = 0; i < this.inputSlot.size(); i++) {
            ItemStack stack = this.inputSlot.get(i);
            if (stack != null)
                return true;
        }
        return false;
    }

    @Override
    public void onNetworkEvent(int event) {

    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUICombinerSolidMatter(new ContainerCombinerSolidMatter(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityCombinerSolidMatter> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerCombinerSolidMatter(entityPlayer, this);
    }

    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }
}
