package com.denfop.tiles.base;

import com.denfop.IUItem;
import com.denfop.container.ContainerSolarGeneratorEnergy;
import com.denfop.gui.GUISolarGeneratorEnergy;
import com.denfop.invslot.InvSlotGenSunarrium;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.InvSlotOutput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;

import java.util.List;

public class TileSolarGeneratorEnergy extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener {
    public final InvSlotOutput outputSlot;
    public final ItemStack itemstack = new ItemStack(IUItem.sunnarium, 1, 4);
    public final double maxSunEnergy;
    public final double cof;
    public final String name;
    public final InvSlotGenSunarrium input;
    public AudioSource audioSource;
    public double sunenergy;

    public TileSolarGeneratorEnergy(double cof, String name) {
        super(0, 10, 0);
        this.sunenergy = 0D;
        this.maxSunEnergy = 2500;
        this.cof = cof;
        this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
        this.name = name;
        this.input =  new InvSlotGenSunarrium(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRender() {

    }

    @Override
    public void onNetworkUpdate(String field) {

    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public void updateEntityServer() {

        super.updateEntityServer();


        if (this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.worldObj.provider.hasNoSky) {
            energy();
            if (this.energy >= this.maxSunEnergy)
                if (this.outputSlot.canAdd(itemstack)) {
                    this.outputSlot.add(itemstack);
                    this.energy -= this.maxSunEnergy;
                }
        }

    }

    public void energy() {
        double k = 0;
        List<Double> lst = input.coefday();
        //TODO: start code GC
        if(this.worldObj.provider.isDaytime() ) {
            float angle = this.worldObj.getCelestialAngle(1.0F) - 0.784690560F < 0 ? 1.0F - 0.784690560F : -0.784690560F;
            float celestialAngle = (this.worldObj.getCelestialAngle(1.0F) + angle) * 360.0F;

            celestialAngle %= 360;
            celestialAngle += 12;
            //TODO: end code GC
            if (celestialAngle <= 90)
                k = celestialAngle / 90;
            else if (celestialAngle > 90 && celestialAngle < 180) {
                celestialAngle -= 90;
                k = 1 - celestialAngle / 90;
            }
            k *= 30 * this.cof * (1 + lst.get(0));
        }

        if(lst.get(2) > 0 && !this.worldObj.provider.isDaytime() ) {
            float angle = this.worldObj.getCelestialAngle(1.0F) - 0.784690560F < 0 ? 1.0F - 0.784690560F : -0.784690560F;
            float celestialAngle = (this.worldObj.getCelestialAngle(1.0F) + angle) * 360.0F;

            celestialAngle %= 360;
            celestialAngle += 12;
            //TODO: end code GC
             if (celestialAngle > 180 && celestialAngle < 270) {
                celestialAngle -= 180;
                k = celestialAngle / 90;
            } else if (celestialAngle > 270 && celestialAngle < 360) {
                celestialAngle -= 270;
                k = 1 - celestialAngle / 90;
            }
            k *= 30 * this.cof * (lst.get(2)-1) * (1+lst.get(1));
        }
        this.energy += k ;
        if (this.energy >= this.maxSunEnergy)
            this.energy = this.maxSunEnergy;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.sunenergy = nbttagcompound.getDouble("sunenergy");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("sunenergy", this.sunenergy);

    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
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

    public ContainerBase<? extends TileSolarGeneratorEnergy> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerSolarGeneratorEnergy(entityPlayer, this);
    }


    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUISolarGeneratorEnergy(new ContainerSolarGeneratorEnergy(entityPlayer, this));
    }

}
