package com.denfop.tiles.base;

import com.denfop.IUItem;
import com.denfop.container.ContainerSolarGeneratorEnergy;
import com.denfop.gui.GuiSolarGeneratorEnergy;
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

public class TileSolarGeneratorEnergy extends TileEntityElectricMachine
		implements IHasGui, INetworkTileEntityEventListener {
	public AudioSource audioSource;
	public final InvSlotOutput outputSlot;

	public final ItemStack itemstack = new ItemStack(IUItem.sunnarium,1,4);

	public double sunenergy;

	public final double maxSunEnergy;

	public final double cof;
	private final String name;

	public TileSolarGeneratorEnergy(double cof, String name) {
		super(0, 10, 0);
		this.sunenergy = 0D;
		this.maxSunEnergy = 7500D;
		this.cof=cof ;
		this.outputSlot = new InvSlotOutput(this, "output", 1, 1);
		this.name = name;
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
	
	protected void updateEntityServer() {

		super.updateEntityServer();
		if(!this.worldObj.provider.isDaytime())
		return;
		long tick = this.worldObj.provider.getWorldTime() % 24000L;
		if(this.worldObj.provider.isDaytime()  && this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.worldObj.provider.hasNoSky) {
        energy(tick);
		        if(this.energy >= 7500)
				if (this.outputSlot.canAdd(itemstack)) {
					this.outputSlot.add(itemstack);
					this.energy -= 7500;
				}
		}

	}
    public void energy(long tick) {
    	double k =0;
    	if(tick <= 1000L)
    		k= 5;
    	if(tick > 1000L && tick <= 4000L)
    		k= 10;
    	if(tick > 4000L && tick <= 8000L)
    		k= 30;
    	if(tick > 8000L && tick <= 11000L)
    		k= 10;
    	if(tick > 11000L)
    		k=5;
    	this.energy +=(k*this.cof);
    	if(this.energy >= this.maxSunEnergy)
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
		return new GuiSolarGeneratorEnergy(new ContainerSolarGeneratorEnergy(entityPlayer, this));
	}

}
