package com.denfop.tiles.base;


import cofh.api.energy.IEnergyHandler;
import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.container.ContainerSinSolarPanel;
import com.denfop.invslot.InvSlotSintezator;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.tiles.overtimepanel.EnumType;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class TileSintezator extends TileEntityInventory implements
        IEnergyTile, IWrenchable, IEnergyHandler, IEnergySource, IInventory, INetworkDataProvider, INetworkUpdateListener {
    public final InvSlotSintezator inputslot;
    public final InvSlotSintezator inputslotA;
    public int solartype;
    public double generating;
    public double genDay;
    public double genNight;
    public boolean initialized;
    public boolean sunIsUp;
    public boolean skyIsVisible;
    public short facing;
    public boolean noSunWorld;
    public int machineTire;
    public boolean addedToEnergyNet;
    public double storage;
    public double production;
    public double maxStorage;

    public boolean rain = false;
    public boolean getmodulerf = false;
    public double progress;
    public double storage2;
    public double maxStorage2;
    public double progress2;
    public boolean wetBiome;
    public EnumType type;
    public GenerationState active;

    public TileSintezator() {
        this.facing = 2;
        this.storage = 0;
        this.storage2 = 0;
        this.sunIsUp = false;
        this.skyIsVisible = false;
        this.genNight = 0;
        this.genDay = 0;
        this.maxStorage = 0;
        this.maxStorage2 = 0;
        this.machineTire = 0;
        this.inputslot = new InvSlotSintezator(this, 12, "input", 0, 9);
        this.inputslotA = new InvSlotSintezator(this, 4, "input1", 1, 4);
        this.solartype = 0;
        this.type = EnumType.DEFAULT;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }


    public void intialize() {
        this.noSunWorld = this.worldObj.provider.hasNoSky;
        this.updateVisibility();


    }

    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean) {
        return extractEnergy((int) Math.min(this.production * Config.coefficientrf, paramInt), paramBoolean);
    }

    public int extractEnergy(int paramInt, boolean paramBoolean) {
        int i = (int) Math.min(this.storage2, Math.min(this.production * Config.coefficientrf, paramInt));
        if (!paramBoolean)
            this.storage2 -= i;
        return i;
    }

    public int getEnergyStored(ForgeDirection from) {
        return (int) this.storage2;
    }

    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) this.maxStorage2;
    }


    public void updateEntityServer() {

        super.updateEntityServer();

        updateTileEntityField();
        if (this.getmodulerf)
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ) == null)
                    continue;
                TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ);
                if (tile instanceof IEnergyHandler)
                    extractEnergy(((IEnergyHandler) tile).receiveEnergy(side.getOpposite(),
                            extractEnergy((int) (this.storage2 >= 2000000000 ? 2000000000 : this.storage2), true), false), false);

            }
        double[] tire_massive = new double[9];
        double[] myArray1 = new double[4];
        if (this.worldObj.provider.getWorldTime() % 20 == 0) {
            for (int i = 0; i < inputslot.size(); i++) {
                if (this.inputslot.get(i) != null && IUItem.map2.get(inputslot.get(i).getUnlocalizedName() + ".name") != null) {
                    int p = Math.min(inputslot.get(i).stackSize, Config.limit);
                    ItemStack stack = inputslot.get(i);
                    EnumSolarPanels solar;
                    solar = IUItem.map2.get(stack.getUnlocalizedName() + ".name");

                    if (solar != null) {


                        myArray1[0] += (solar.genday * p);
                        myArray1[1] += (solar.gennight * p);
                        myArray1[2] += (solar.maxstorage * p);
                        myArray1[3] += (solar.producing * p);
                        tire_massive[i] = solar.tier;
                    }
                } else if (this.inputslot.get(i) != null && IUItem.panel_list.get(inputslot.get(i).getUnlocalizedName() + ".name") != null) {
                    int p = Math.min(inputslot.get(i).stackSize, Config.limit);
                    ItemStack stack = inputslot.get(i);
                    List solar;
                    solar = IUItem.panel_list.get(stack.getUnlocalizedName() + ".name");

                    if (solar != null) {


                        myArray1[0] += ((double) solar.get(0) * p);
                        myArray1[1] += ((double) solar.get(1) * p);
                        myArray1[2] += ((double) solar.get(2) * p);
                        myArray1[3] += ((double) solar.get(3) * p);
                        tire_massive[i] = (int) solar.get(4);
                    }
                }
            }
            this.inputslotA.getrfmodule();
        }
        if (this.worldObj.provider.getWorldTime() % 20 == 0) {
            double max = tire_massive[0];
            for (double v : tire_massive)
                if (v > max)
                    max = v;

            this.machineTire = (int) max;
            this.solartype = this.inputslotA.solartype();
            this.genDay = myArray1[0];
            this.genNight = myArray1[1];
            this.maxStorage = myArray1[2];
            this.maxStorage2 = myArray1[2] * 4;
            this.production = myArray1[3];
        }
        if (this.worldObj.provider.getWorldTime() % 20 == 0)
            this.inputslotA.checkmodule();
        this.gainFuel();
        if (this.generating > 0D) {
            if (!this.getmodulerf) {
                if (((this.storage + this.generating)) <= (this.maxStorage)) {
                    this.storage += this.generating;
                } else {
                    this.storage = (this.maxStorage);
                }
            } else {
                if (((this.storage2 + this.generating * 4)) <= (this.maxStorage2)) {
                    this.storage2 += this.generating * 4;
                } else {
                    this.storage2 = (this.maxStorage2);
                }
            }
        }
        this.progress2 = Math.min(1, this.storage2 / this.maxStorage2);

        this.progress = Math.min(1, this.storage / this.maxStorage);
        if (this.storage < 0D) {
            this.storage = 0D;
        }
        if (this.maxStorage <= 0D) {
            this.storage = 0D;
        }
        if (this.storage2 < 0D) {
            this.storage2 = 0D;
        }
        if (this.maxStorage2 <= 0D) {
            this.storage2 = 0D;
        }
    }

    public void gainFuel() {


        if (this.getWorldObj().provider.getWorldTime() % 80 == 0)
            this.updateVisibility();


        switch (this.active) {
            case DAY:
                this.generating = type.coefficient_day * this.genDay;
                break;
            case NIGHT:
                this.generating = type.coefficient_night * this.genNight;
                break;
            case RAINDAY:
                this.generating = type.coefficient_rain * type.coefficient_day * this.genDay;
                break;
            case RAINNIGHT:
                this.generating = type.coefficient_rain * type.coefficient_night * this.genNight;
                break;
            case NETHER:
                this.generating = type.coefficient_nether * this.genDay;
                break;
            case END:
                this.generating = type.coefficient_end * this.genDay;
                break;
            case NONE:
                this.generating = 0;
                break;

        }

    }

    private void updateTileEntityField() {

        IC2.network.get().updateTileEntityField(this, "solartype");

    }

    public void updateVisibility() {
        this.wetBiome = (this.worldObj.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord)
                .getIntRainfall() > 0);
        this.rain = this.wetBiome && (this.worldObj.isRaining() || this.worldObj.isThundering());
        this.sunIsUp = this.worldObj.isDaytime();
        this.skyIsVisible = this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.noSunWorld;
        if (!this.skyIsVisible)
            this.active = GenerationState.NONE;
        if (this.sunIsUp && this.skyIsVisible) {
            if (!(this.worldObj.isRaining() || this.worldObj.isThundering()))
                this.active = GenerationState.DAY;
            else
                this.active = GenerationState.RAINDAY;

        }
        if (!this.sunIsUp && this.skyIsVisible) {
            if (!(this.worldObj.isRaining() || this.worldObj.isThundering()))
                this.active = GenerationState.NIGHT;
            else
                this.active = GenerationState.RAINNIGHT;
        }
        if (this.getWorldObj().provider.dimensionId == 1)
            this.active = GenerationState.END;
        if (this.getWorldObj().provider.dimensionId == -1)
            this.active = GenerationState.NETHER;

    }

    public void readFromNBT(final NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound.getDouble("storage2") > 0)
            this.storage2 = nbttagcompound.getDouble("storage2");
        if (nbttagcompound.getInteger("solarType") != 0)
            this.solartype = nbttagcompound.getInteger("solarType");

        if (nbttagcompound.getDouble("storage") > 0)
            this.storage = nbttagcompound.getDouble("storage");
        if (nbttagcompound.getDouble("maxStorage") > 0) {
            this.genDay = nbttagcompound.getDouble("genDay");
            this.genNight = nbttagcompound.getDouble("genNight");
            this.maxStorage = nbttagcompound.getDouble("maxStorage");
            this.production = nbttagcompound.getDouble("production");
            this.maxStorage2 = nbttagcompound.getDouble("maxStorage2");

        }
    }

    public void writeToNBT(final NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        if (storage2 > 0)
            nbttagcompound.setDouble("storage2", this.storage2);
        nbttagcompound.setInteger("solarType", this.solartype);

        if (storage > 0)
            nbttagcompound.setDouble("storage", this.storage);
        if (maxStorage > 0) {
            nbttagcompound.setDouble("genDay", this.genDay);
            nbttagcompound.setDouble("genNight", this.genNight);
            nbttagcompound.setDouble("production", this.production);

            nbttagcompound.setDouble("maxStorage", this.maxStorage);
            if (maxStorage2 > 0) {
                nbttagcompound.setDouble("maxStorage2", this.maxStorage2);

            }
        }

    }

    public double gaugeEnergyScaled(final float i) {

        return progress * i;

    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) == this
                && player.getDistance((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }


    @Override
    public short getFacing() {
        return this.facing;
    }

    @Override
    public void setFacing(final short facing) {
        this.facing = facing;
    }

    @Override
    public boolean wrenchCanSetFacing(final EntityPlayer entityplayer, final int i) {
        return false;
    }

    @Override
    public boolean wrenchCanRemove(final EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public float getWrenchDropRate() {
        return 1.0f;
    }

    @Override
    public ItemStack getWrenchDrop(final EntityPlayer entityPlayer) {
        return new ItemStack(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord), 1,
                this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
    }

    public void setType(EnumType type) {
        this.type = type;
    }

    public int setSolarType(EnumType type) {
        if (type == null) {
            setType(EnumType.DEFAULT);
            return 0;
        }
        setType(type);
        switch (type) {
            case AIR:
                if (this.yCoord >= 130)
                    return 1;
                break;
            case EARTH:
                if (this.yCoord <= 40)
                    return 2;
                break;
            case NETHER:
                if (this.worldObj.provider.dimensionId == -1)
                    return 3;
                break;
            case END:
                if (this.worldObj.provider.dimensionId == 1)
                    return 4;
                break;
            case NIGHT:
                if (!this.sunIsUp)
                    return 5;
                break;
            case DAY:
                if (this.sunIsUp)
                    return 6;
                break;
            case RAIN:
                if ((this.worldObj.isRaining() || this.worldObj.isThundering()))
                    return 7;
                break;

        }
        setType(EnumType.DEFAULT);
        return 0;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("blockSintezator.name");
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public ContainerBase<? extends TileSintezator> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileSintezator>) new ContainerSinSolarPanel(entityPlayer, this);
    }

    public void onNetworkUpdate(final String field) {
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("generating");
        ret.add("genDay");
        ret.add("genNight");
        ret.add("storage");
        ret.add("maxStorage");
        ret.add("production");
        ret.add("machineTire");
        ret.add("solartype");
        ret.add("inputslot");
        ret.add("inputslotA");
        return ret;
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
        intialize();

    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        super.onUnloaded();
    }

    public double getOfferedEnergy() {
        return Math.min(this.storage, this.production);
    }

    public void drawEnergy(final double amount) {
        this.storage -= amount;
    }

    public int getSourceTier() {
        return this.machineTire;
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection paramForgeDirection) {
        return true;
    }

    public double gaugeEnergyScaled1(int i) {
        return progress2 * i;
    }

    enum GenerationState {
        DAY, NIGHT, RAINDAY, RAINNIGHT, NETHER, END, NONE
    }
}
