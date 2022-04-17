package com.denfop.tiles.base;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.container.ContainerSolarPanels;
import com.denfop.invslot.InvSlotPanel;
import com.denfop.item.modules.AdditionModule;
import com.denfop.item.modules.EnumModule;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.tiles.overtimepanel.EnumType;
import com.denfop.utils.ModUtils;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.block.TileEntityInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;

enum GenerationState {
    DAY, NIGHT, RAINDAY, RAINNIGHT, NETHER, END, NONE
}

public class TileEntitySolarPanel extends TileEntityInventory
        implements IEnergyTile, INetworkDataProvider, INetworkUpdateListener, IWrenchable, IEnergySource,
        IEnergyHandler, INetworkClientTileEntityEventListener, IEnergyReceiver {

    public final InvSlotPanel inputslot;
    public final EnumSolarPanels solarpanels;
    public final String panelName;
    public final double p;
    public final double k;
    public final double m;
    public final double u;
    public final double o;
    public final double maxStorage2;
    public double generating;
    public double genDay;
    public double genNight;
    public boolean initialized;
    public boolean sunIsUp;
    public boolean skyIsVisible;
    public boolean noSunWorld;
    public boolean wetBiome;
    public int machineTire;
    public boolean addedToEnergyNet;
    public boolean personality = false;
    public double storage;
    public int solarType;
    public double production;
    public double maxStorage;
    public double tier;
    public boolean getmodulerf = false;
    public String player = null;
    public int time;
    public int time1;
    public int time2;
    public double storage2;
    public boolean rain;
    public GenerationState active;
    public double progress;
    public EnumType type;
    public int wireless = 0;
    public boolean work = true;
    public boolean work1 = true;
    public boolean work2 = true;
    public boolean charge;
    public boolean rf = true;

    public TileEntitySolarPanel(final String gName, final int tier, final double gDay,
                                final double gOutput, final double gmaxStorage, EnumSolarPanels type) {

        this.solarType = 0;
        this.genDay = gDay;
        this.genNight = gDay / 2;
        this.storage = 0;
        this.panelName = gName;
        this.sunIsUp = false;
        this.skyIsVisible = false;
        this.maxStorage = gmaxStorage;
        this.p = gmaxStorage;
        this.k = gDay;
        this.m = gDay / 2;
        this.maxStorage2 = this.maxStorage * Config.coefficientrf;
        this.initialized = false;
        this.production = gOutput;
        this.u = gOutput;
        this.time = 28800;
        this.time1 = 14400;
        this.time2 = 14400;
        this.machineTire = tier;
        this.tier = tier;
        this.o = tier;
        this.rain = false;
        this.type = EnumType.DEFAULT;
        this.inputslot = new InvSlotPanel(this, 3);
        this.solarpanels = type;
    }

    public TileEntitySolarPanel(EnumSolarPanels solarpanels) {
        this(solarpanels.name1, solarpanels.tier, solarpanels.genday, solarpanels.producing, solarpanels.maxstorage, solarpanels);

    }

    public EnumSolarPanels getPanels() {
        return this.solarpanels;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public boolean wrenchCanRemove(final EntityPlayer entityPlayer) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) instanceof TileEntitySolarPanel) {
            List<String> list = new ArrayList<>();
            list.add(player);
            for (int h = 0; h < 9; h++) {
                if (inputslot.get(h) != null && inputslot.get(h).getItem() instanceof AdditionModule
                        && inputslot.get(h).getItemDamage() == 0) {
                    for (int m = 0; m < 9; m++) {
                        NBTTagCompound nbt = ModUtils.nbt(inputslot.get(h));
                        String name = "player_" + m;
                        if (!nbt.getString(name).isEmpty())
                            list.add(nbt.getString(name));
                    }
                    break;
                }
            }
            if (this.personality) {
                if (list.contains(entityPlayer.getDisplayName()) || entityPlayer.capabilities.isCreativeMode) {
                    return true;
                } else {
                    entityPlayer.addChatMessage(new ChatComponentTranslation(
                            StatCollector.translateToLocal("iu.error")));
                    return false;
                }
            } else {
                return true;

            }
        }
        return true;
    }


    public void intialize() {
        this.wetBiome = (this.worldObj.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord)
                .getIntRainfall() > 0);
        this.noSunWorld = this.worldObj.provider.hasNoSky;

        this.updateVisibility();
        this.initialized = true;

    }

    public double extractEnergy1(double maxExtract, boolean simulate) {
        double temp;

        temp = this.storage2;

        if (temp > 0) {
            double energyExtracted = Math.min(temp, maxExtract);
            if (!simulate &&
                    this.storage2 - temp >= 0.0D) {
                this.storage2 -= temp;
                if (energyExtracted > 0) {
                    temp -= energyExtracted;
                    this.storage2 += temp;
                }
                return energyExtracted;
            }
        }
        return 0;
    }

    public void updateEntityServer() {

        super.updateEntityServer();
        if (this.getWorldObj().provider.getWorldTime() % 20 == 0) {
            this.inputslot.time();
        }
        if (this.getmodulerf)
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ) == null)
                    continue;
                TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ);
                if (!(tile instanceof TileEntitySolarPanel)) {

                    if (tile instanceof IEnergyReceiver)
                        extractEnergy(side.getOpposite(), ((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(),
                                extractEnergy(side.getOpposite(), (int) this.storage2, true), false), false);
                }
            }
        if (this.charge)
            this.inputslot.charge();
        if (this.charge && this.getmodulerf)
            this.inputslot.rfcharge();

        if (this.storage2 >= this.maxStorage2) {
            this.storage2 = this.maxStorage2;
        } else if (this.storage2 < 0) {
            this.storage2 = 0;
        }
        if (this.getWorldObj().provider.getWorldTime() % 20 == 0)
            updateTileEntityField();
        this.inputslot.wirelessmodule();

        gainFuel();

        if (this.generating > 0) {
            if (getmodulerf) {
                if (!rf) {
                    if (this.storage + this.generating <= this.maxStorage) {
                        this.storage += this.generating;
                    } else {
                        this.storage = this.maxStorage;
                    }
                } else {

                    if ((this.storage2 + (this.generating * Config.coefficientrf)) <= this.maxStorage2) {
                        this.storage2 += (this.generating * Config.coefficientrf);
                    } else {
                        this.storage2 = this.maxStorage2;

                    }
                }

            } else {
                if (this.storage + this.generating <= this.maxStorage) {
                    this.storage += this.generating;
                } else {
                    this.storage = this.maxStorage;
                }
            }
        }

        this.progress = Math.min(1, this.storage / this.maxStorage);

    }

    private void updateTileEntityField() {
        IC2.network.get().updateTileEntityField(this, "solarType");
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
        this.intialize();

    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        if (IC2.platform.isRendering()) {
            IC2.audioManager.removeSources(this);

        }

        super.onUnloaded();
    }

    public void gainFuelMachine() {
        double coefpollution;
        coefpollution = 1 - (0.25 * (1 - (double) this.time / 28800)) - (0.25 * (1 - (double) this.time1 / 14400)) - (0.25 * (1 - (double) this.time2 / 14400));


        switch (this.active) {
            case DAY:
                this.generating = type.coefficient_day * this.genDay;
                break;
            case NIGHT:
                this.generating = type.coefficient_night * this.genNight;
                break;
            case RAINDAY:
                this.generating = type.equals(EnumType.RAIN) ? type.coefficient_rain : (1 - 0.35 * Math.min(1, this.worldObj.getRainStrength(1.0F))) * type.coefficient_day * this.genDay;
                break;
            case RAINNIGHT:
                this.generating = type.equals(EnumType.RAIN) ? type.coefficient_rain : (1 - 0.35 * Math.min(1, this.worldObj.getRainStrength(1.0F))) * type.coefficient_night * this.genNight;
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
        double coefficient_phase;
        double moonPhase = 1;
        coefficient_phase = experimental_generating();
        if (this.active == GenerationState.NIGHT || this.active == GenerationState.RAINNIGHT)
            for (int i = 0; i < this.inputslot.size(); i++) {
                if (this.inputslot.get(i) != null && IUItem.modules.get(this.inputslot.get(i).getItem()) != null) {
                    EnumModule module = IUItem.modules.get(this.inputslot.get(i).getItem());
                    com.denfop.item.modules.EnumType type = module.type;
                    if (type == com.denfop.item.modules.EnumType.MOON_LINSE) {
                        moonPhase = module.percent;
                        break;
                    }

                }
            }


        this.generating *= coefpollution * coefficient_phase * moonPhase;
    }

    public void gainFuel() {
        double coefpollution;
        coefpollution = 1 - (0.25 * (1 - (double) this.time / 28800)) - (0.25 * (1 - (double) this.time1 / 14400)) - (0.25 * (1 - (double) this.time2 / 14400));

        switch (this.active) {
            case DAY:
                this.generating = type.coefficient_day * this.genDay;
                break;
            case NIGHT:
                this.generating = type.coefficient_night * this.genNight;
                break;
            case RAINDAY:
                this.generating = type.equals(EnumType.RAIN) ? type.coefficient_rain : (1 - 0.35 * Math.min(1, this.worldObj.getRainStrength(1.0F))) * type.coefficient_day * this.genDay;
                break;
            case RAINNIGHT:
                this.generating = type.equals(EnumType.RAIN) ? type.coefficient_rain : (1 - 0.35 * Math.min(1, this.worldObj.getRainStrength(1.0F))) * type.coefficient_night * this.genNight;
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
        double coefficient_phase;
        double moonPhase = 1;
        coefficient_phase = experimental_generating();
        if (this.active == GenerationState.NIGHT || this.active == GenerationState.RAINNIGHT)
            for (int i = 0; i < this.inputslot.size(); i++) {
                if (this.inputslot.get(i) != null && IUItem.modules.get(this.inputslot.get(i).getItem()) != null) {
                    EnumModule module = IUItem.modules.get(this.inputslot.get(i).getItem());
                    com.denfop.item.modules.EnumType type = module.type;
                    if (type == com.denfop.item.modules.EnumType.MOON_LINSE) {
                        moonPhase = module.percent;
                        break;
                    }

                }
            }


        this.generating *= coefpollution * coefficient_phase * moonPhase;
    }

    private double experimental_generating() {

        double k = 1;
        //TODO: start code GC
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
        } else if (celestialAngle > 180 && celestialAngle < 270) {
            celestialAngle -= 180;
            k = celestialAngle / 90;
        } else if (celestialAngle > 270 && celestialAngle < 360) {
            celestialAngle -= 270;
            k = 1 - celestialAngle / 90;
        }


        double coef = 0;
        for (int i = 0; i < this.inputslot.size(); i++) {
            if (this.inputslot.get(i) != null && IUItem.modules.get(this.inputslot.get(i).getItem()) != null) {
                EnumModule module = IUItem.modules.get(this.inputslot.get(i).getItem());
                com.denfop.item.modules.EnumType type = module.type;
                if (type == com.denfop.item.modules.EnumType.PHASE) {
                    coef = module.percent;
                    break;
                }

            }
        }
        return Math.max(coef, k);

    }

    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return extractEnergy((int) Math.min(this.production * Config.coefficientrf, maxExtract), simulate);
    }

    public int extractEnergy(int paramInt, boolean paramBoolean) {
        int i = (int) Math.min(this.storage2, Math.min(this.production * Config.coefficientrf, paramInt));
        if (!paramBoolean)
            this.storage2 -= i;
        return i;
    }

    public void updateVisibility() {

        this.rain = this.wetBiome && (this.worldObj.isRaining() || this.worldObj.isThundering());
        this.sunIsUp = this.worldObj.isDaytime();
        this.skyIsVisible = this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.noSunWorld;
        if (this.skyIsVisible) {
            if (this.sunIsUp) {
                if (!(this.worldObj.isRaining() || this.worldObj.isThundering()))
                    this.active = GenerationState.DAY;
                else
                    this.active = GenerationState.RAINDAY;
            } else {
                if (!(this.worldObj.isRaining() || this.worldObj.isThundering()))
                    this.active = GenerationState.NIGHT;
                else
                    this.active = GenerationState.RAINNIGHT;
            }
        } else this.active = GenerationState.NONE;
        if (this.getWorldObj().provider.dimensionId == 1)
            this.active = GenerationState.END;
        if (this.getWorldObj().provider.dimensionId == -1)
            this.active = GenerationState.NETHER;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.storage = nbttagcompound.getDouble("storage");
        this.time = nbttagcompound.getInteger("time");
        this.time1 = nbttagcompound.getInteger("time1");
        this.time2 = nbttagcompound.getInteger("time2");
        this.production = nbttagcompound.getDouble("production");
        this.generating = nbttagcompound.getDouble("generating");
        this.tier = nbttagcompound.getDouble("tier");
        this.machineTire = nbttagcompound.getInteger("machineTire");
        this.wireless = nbttagcompound.getInteger("wireless");
        if (nbttagcompound.getInteger("solarType") != 0)
            this.solarType = nbttagcompound.getInteger("solarType");
        if (nbttagcompound.getBoolean("getmodulerf"))
            this.getmodulerf = nbttagcompound.getBoolean("getmodulerf");
        if (getmodulerf)
            this.rf = nbttagcompound.getBoolean("rf");
        if (nbttagcompound.getString("player") != null)
            this.player = nbttagcompound.getString("player");
        if (nbttagcompound.getBoolean("getmodulerf"))
            this.storage2 = nbttagcompound.getDouble("storage2");
    }


    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        if (getmodulerf) nbttagcompound.setBoolean("getmodulerf", this.getmodulerf);
        nbttagcompound.setInteger("time", this.time);
        nbttagcompound.setInteger("time1", this.time1);
        nbttagcompound.setInteger("time2", this.time2);
        nbttagcompound.setInteger("machineTire", this.machineTire);
        nbttagcompound.setInteger("wireless", this.wireless);
        if (player != null) nbttagcompound.setString("player", player);
        nbttagcompound.setInteger("solarType", this.solarType);
        nbttagcompound.setDouble("storage", this.storage);
        if (this.getmodulerf) {
            nbttagcompound.setDouble("storage2", this.storage2);
            nbttagcompound.setBoolean("rf", this.rf);
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

    public float gaugeEnergyScaled2(final float i) {
        if ((this.storage2 * i / this.maxStorage2) > 24)
            return 24;

        return (float) (this.storage2 * i / (this.maxStorage2));

    }

    public boolean canConnectEnergy(ForgeDirection arg0) {
        return true;
    }

    public int getEnergyStored(ForgeDirection from) {
        return (int) this.storage2;
    }

    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) this.maxStorage2;
    }


    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {

        return super.getWrenchDrop(entityPlayer);
    }

    public short getFacing() {
        return 5;
    }

    public ContainerBase<? extends TileEntitySolarPanel> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerSolarPanels(entityPlayer, this);
    }

    public void onNetworkUpdate(final String field) {
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("generating");
        ret.add("storage");
        ret.add("maxStorage");
        ret.add("production");
        ret.add("solarType");
        ret.add("tier");
        ret.add("type");
        return ret;
    }

    public boolean emitsEnergyTo(final TileEntity receiver, final ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {

        return Math.min(this.production, this.storage);
    }

    public void drawEnergy(final double amount) {
        this.storage -= (int) amount;
    }

    public int getSourceTier() {
        return this.machineTire;
    }


    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {


        return 0;

    }

    public EnumType getType() {
        return this.type;
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


    @Override
    public void onNetworkEvent(EntityPlayer player, int event) {
        this.rf = !this.rf;

    }

    public String getInventoryName() {
        return "Super Solar Panel";
    }


    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return false;
    }

    public void closeInventory() {

    }


}