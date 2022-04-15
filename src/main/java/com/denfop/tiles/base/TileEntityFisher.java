package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerFisher;
import com.denfop.gui.GUIFisher;
import com.denfop.invslot.InvSlotFisher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotOutput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.FishingHooks;
import net.minecraftforge.common.util.ForgeDirection;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@SuppressWarnings("ALL")
public class TileEntityFisher extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener {


    private static Field _Random_seed = null;

    static {
        try {
            Field var0 = Random.class.getDeclaredField("seed");
            var0.setAccessible(true);
            _Random_seed = var0;
        } catch (Throwable ignored) {
        }

    }

    public final int energyconsume;
    public final InvSlotOutput outputSlot;
    public final InvSlotFisher inputslot;
    public int progress;
    public AudioSource audioSource;
    protected Random _rand = null;
    protected float _next = (float) (Double.NaN);
    private boolean checkwater;


    public TileEntityFisher() {
        super(1E4, 14, 1);
        this.progress = 0;
        this.energyconsume = 100;
        this.checkwater = false;
        this.outputSlot = new InvSlotOutput(this, "output", 2, 9);
        this.inputslot = new InvSlotFisher(this, 3);
    }

    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack ret = super.getWrenchDrop(entityPlayer);
        if (this.inputslot.get() != null) {
            double var8 = 0.7D;
            double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                    this.inputslot.get());
            var16.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(var16);
        }
        for (int i = 0; i < 9; i++)
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
        if (this._rand == null) {
            this._rand = new Random(super.worldObj.getSeed() ^ super.worldObj.rand.nextLong());
            this._next = this._rand.nextFloat();
        }


        if (this.worldObj.provider.getWorldTime() % 100 == 0) {
            checkwater = checkwater();
        }
        if (checkwater && !this.inputslot.isEmpty()) {
            if (progress < 100) {
                if (this.energy >= this.energyconsume) {
                    progress++;

                    initiate(0);
                    this.setActive(true);
                } else {
                    initiate(2);
                    this.setActive(false);
                }
            }
        } else {
            initiate(2);
            this.setActive(false);
        }
        if (worldObj.provider.getWorldTime() % 60 == 0)
            initiate(2);
        if (checkwater && progress >= 100) {
            if (!this.inputslot.isEmpty()) {
                ItemStack stack = this.inputslot.get();
                byte _luck = (byte) EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151370_z.effectId, stack);
                byte _speed = (byte) EnchantmentHelper.getEnchantmentLevel(Enchantment.field_151369_A.effectId, stack);
                ItemStack var1 = FishingHooks.getRandomFishable(this._rand, this._next, _luck, _speed);
                if (this.outputSlot.canAdd(var1)) {
                    this.energy -= (this.energyconsume * 10);
                    outputSlot.add(var1);
                }
                this._next = this._rand.nextFloat();
                progress = 0;
                this.inputslot.get().setItemDamage(this.inputslot.get().getItemDamage() + 1);
                if (this.inputslot.get().getItemDamage() >= this.inputslot.get().getMaxDamage())
                    this.inputslot.consume(1);
            }
        }

    }

    private boolean checkwater() {
        int x1 = this.xCoord;
        int y1 = this.yCoord - 2;
        int z1 = this.zCoord;
        for (int i = x1 - 1; i <= x1 + 1; i++)
            for (int j = z1 - 1; j <= z1 + 1; j++)
                for (int k = y1 - 1; k <= y1 + 1; k++)
                    if (this.worldObj.getBlock(i, k, j) != Blocks.water)
                        return false;

        return true;
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
        this.progress = nbttagcompound.getInteger("progress");
        if (nbttagcompound.hasKey("seed")) {
            this._rand = new Random(nbttagcompound.getLong("seed"));
        }

        if (nbttagcompound.hasKey("next")) {
            this._next = nbttagcompound.getFloat("next");
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setFloat("next", this._next);

        nbttagcompound.setInteger("progress", this.progress);
        if (_Random_seed != null) {
            try {
                nbttagcompound.setLong("seed", ((AtomicLong) _Random_seed.get(this._rand)).get());
            } catch (Throwable ignored) {
            }
        }
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
        return new GUIFisher(new ContainerFisher(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityFisher> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityFisher>) new ContainerFisher(entityPlayer, this);
    }

    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    public String getStartSoundFile() {
        return "Machines/fisher.ogg";
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

    @Override
    public void onGuiClosed(EntityPlayer arg0) {
    }

    @Override
    public String getInventoryName() {
        // TODO Auto-generated method stub
        return null;
    }
}
