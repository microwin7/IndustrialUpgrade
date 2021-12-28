package com.denfop.tiles.base;

import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyReceiver;
import com.denfop.Config;
import com.denfop.container.ContainerElectricBlock;
import com.denfop.gui.GUIElectricBlock;
import com.denfop.invslot.InvSlotElectricBlock;
import com.denfop.item.modules.AdditionModule;
import com.denfop.tiles.wiring.EnumElectricBlock;
import com.denfop.utils.ModUtils;
import com.denfop.utils.NBTData;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.tile.IEnergyStorage;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.Ic2Items;
import ic2.core.block.TileEntityInventory;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.EntityIC2FX;
import ic2.core.util.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TileEntityElectricBlock extends TileEntityInventory implements IEnergySink, IEnergySource,
        IHasGui, INetworkClientTileEntityEventListener, IEnergyStorage, IEnergyHandler, IEnergyReceiver {
    public final double tier;
    public final boolean chargepad;
    public final String name;
    public final double maxStorage;
    public final double maxStorage2;
    public final double l;
    public final InvSlotElectricBlock inputslotA;
    public final InvSlotElectricBlock inputslotB;
    public final InvSlotElectricBlock inputslotC;
    public EntityPlayer player;
    public double output;
    public double energy;
    public String UUID = null;
    public double energy2;
    public boolean rf;
    public boolean rfeu = false;
    public boolean needsInvUpdate = false;
    public boolean movementcharge = false;
    public boolean movementchargerf = false;
    public boolean movementchargeitemrf = false;
    public double output_plus;
    public short temp;
    public boolean addedToEnergyNet;
    public boolean movementchargeitem = false;
    public boolean personality = false;

    public TileEntityElectricBlock(double tier1, double output1, double maxStorage1, boolean chargepad, String name) {
        this.energy = 0.0D;
        this.energy2 = 0.0D;
        this.addedToEnergyNet = false;
        this.tier = tier1;
        this.output = output1;
        this.maxStorage = maxStorage1;
        this.player = null;
        this.maxStorage2 = maxStorage1 * Config.coefficientrf;
        this.chargepad = chargepad;
        this.rf = false;
        this.name = name;
        this.inputslotA = new InvSlotElectricBlock(this, 1, "input", 1);
        this.inputslotB = new InvSlotElectricBlock(this, 2, "input1", 1);
        this.inputslotC = new InvSlotElectricBlock(this, 3, "input2", 2);
        this.output_plus = 0;
        this.temp = 0;
        this.l = output1;
    }

    public TileEntityElectricBlock(EnumElectricBlock electricBlock) {
        this(electricBlock.tier, electricBlock.producing, electricBlock.maxstorage, electricBlock.chargepad, electricBlock.name1);

    }

    public static void module_charge(EntityPlayer entityPlayer, TileEntityElectricBlock tile) {

        if (tile.movementcharge) {

            for (ItemStack armorcharged : entityPlayer.inventory.armorInventory) {
                if (armorcharged != null) {
                    if (armorcharged.getItem() instanceof IElectricItem && tile.energy > 0) {
                        double sent = ElectricItem.manager.charge(armorcharged, tile.energy, 2147483647, true,
                                false);
                        entityPlayer.inventoryContainer.detectAndSendChanges();
                        tile.energy -= sent;

                        tile.needsInvUpdate = (sent > 0.0D);
                        if (sent > 0) {

                            entityPlayer
                                    .addChatMessage(
                                            new ChatComponentTranslation(
                                                    StatCollector.translateToLocal("successfully.charged")
                                                            + armorcharged.getDisplayName()
                                                            + StatCollector.translateToLocal("iu.sendenergy")
                                                            + ModUtils.getString(sent) + " EU"
                                            ));

                        }
                    }

                }

            }

        }
        if (tile.movementchargerf) {

            for (ItemStack charged : entityPlayer.inventory.armorInventory) {
                if (charged != null) {

                    if (charged.getItem() instanceof IEnergyContainerItem && tile.energy2 > 0) {
                        double sent = 0;

                        IEnergyContainerItem item = (IEnergyContainerItem) charged.getItem();
                        double energy_temp = tile.energy2;
                        while (item.getEnergyStored(charged) < item.getMaxEnergyStored(charged)
                                && tile.energy2 > 0) {
                            sent = (sent + tile.extractEnergy1(
                                    item.receiveEnergy(charged, (int) tile.energy2, false), false));

                        }
                        energy_temp -= (sent);
                        tile.energy2 = energy_temp;
                        if (sent > 0) {

                            entityPlayer
                                    .addChatMessage(
                                            new ChatComponentTranslation(
                                                    StatCollector.translateToLocal("successfully.charged")
                                                            + charged.getDisplayName()
                                                            + StatCollector.translateToLocal("iu.sendenergy")
                                                            + ModUtils.getString(sent) + " RF"
                                            ));

                        }
                        entityPlayer.inventoryContainer.detectAndSendChanges();

                    }

                }
            }
        }
        if (tile.movementchargeitem) {
            for (ItemStack charged : entityPlayer.inventory.mainInventory) {
                if (charged != null) {
                    if (charged.getItem() instanceof IElectricItem && tile.energy > 0) {
                        double sent = ElectricItem.manager.charge(charged, tile.energy, 2147483647, true, false);

                        tile.energy -= sent;
                        entityPlayer.inventoryContainer.detectAndSendChanges();
                        tile.needsInvUpdate = (sent > 0.0D);
                        if (sent > 0) {

                            entityPlayer
                                    .addChatMessage(
                                            new ChatComponentTranslation(
                                                    StatCollector.translateToLocal("successfully.charged")
                                                            + charged.getDisplayName()
                                                            + StatCollector.translateToLocal("iu.sendenergy")
                                                            + ModUtils.getString(sent) + " EU"
                                            ));

                        }
                    }

                }

            }

        }
        if (tile.movementchargeitemrf) {
            for (ItemStack charged : entityPlayer.inventory.mainInventory) {
                if (charged != null) {

                    if (charged.getItem() instanceof IEnergyContainerItem && tile.energy2 > 0) {
                        double sent = 0;

                        IEnergyContainerItem item = (IEnergyContainerItem) charged.getItem();
                        double energy_temp = tile.energy2;
                        while (item.getEnergyStored(charged) < item.getMaxEnergyStored(charged)
                                && tile.energy2 > 0) {
                            sent = (sent + tile.extractEnergy1(
                                    item.receiveEnergy(charged, (int) tile.energy2, false), false));

                        }
                        energy_temp -= (sent);
                        tile.energy2 = energy_temp;
                        if (sent > 0) {

                            entityPlayer
                                    .addChatMessage(
                                            new ChatComponentTranslation(
                                                    StatCollector.translateToLocal("successfully.charged")
                                                            + charged.getDisplayName()
                                                            + StatCollector.translateToLocal("iu.sendenergy")
                                                            + ModUtils.getString(sent) + " RF"
                                            ));

                        }
                        entityPlayer.inventoryContainer.detectAndSendChanges();

                    }

                }
            }
        }
    }

    protected void getItems(EntityPlayer player) {
        List<String> list = new ArrayList<>();
        list.add(UUID);
        for (int h = 0; h < 2; h++) {
            if (inputslotC.get(h) != null && inputslotC.get(h).getItem() instanceof AdditionModule
                    && inputslotC.get(h).getItemDamage() == 0) {
                for (int m = 0; m < 9; m++) {
                    NBTTagCompound nbt = ModUtils.nbt(inputslotC.get(h));
                    String name = "player_" + m;
                    if (!nbt.getString(name).isEmpty())
                        list.add(nbt.getString(name));
                }
                break;
            }

        }


        if (player != null) {
            if (personality) {
                if (!(list.contains(player.getDisplayName()) || player.capabilities.isCreativeMode)) {
                    player.addChatMessage(
                            new ChatComponentTranslation(StatCollector.translateToLocal("iu.error")));
                    return;
                }
            }
            for (ItemStack current : player.inventory.armorInventory) {
                if (current != null)
                    chargeitems(current, this.output);
            }
            for (ItemStack current : player.inventory.mainInventory) {
                if (current != null)
                    chargeitems(current, this.output);
            }
            player.inventoryContainer.detectAndSendChanges();

        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles(World world, int blockX, int blockY, int blockZ, Random rand) {
        if (getActive()) {
            EffectRenderer effect = (FMLClientHandler.instance().getClient()).effectRenderer;
            for (int particles = 20; particles > 0; particles--) {
                double x = (blockX + 0.0F + rand.nextFloat());
                double y = (blockY + 0.9F + rand.nextFloat());
                double z = (blockZ + 0.0F + rand.nextFloat());
                effect.addEffect(new EntityIC2FX(world, x, y, z, 60, new double[]{0.0D, 0.1D, 0.0D},
                        new float[]{0.2F, 0.2F, 1.0F}));
            }
        }
    }

    protected void chargeitems(ItemStack itemstack, double chargefactor) {
        if (!(itemstack.getItem() instanceof ic2.api.item.IElectricItem || itemstack.getItem() instanceof IEnergyContainerItem))
            return;
        if (itemstack.getItem() == Ic2Items.debug.getItem())
            return;
        if (this.energy2 > 0 && itemstack.getItem() instanceof IEnergyContainerItem) {
            double sent = 0;

            IEnergyContainerItem item = (IEnergyContainerItem) itemstack.getItem();
            double energy_temp = this.energy2;
            if (item.getEnergyStored(itemstack) < item.getMaxEnergyStored(itemstack)
                    && this.energy2 > 0) {
                sent = (sent + this.extractEnergy1(
                        item.receiveEnergy(itemstack, (int) this.energy2, false), false));

            }
            energy_temp -= (sent * 2);
            this.energy2 = energy_temp;


        }
        double freeamount = ElectricItem.manager.charge(itemstack, Double.POSITIVE_INFINITY, (int) this.tier, true, true);
        double charge;
        if (freeamount >= 0.0D) {
            charge = Math.min(freeamount, chargefactor);
            if (this.energy < charge)
                charge = this.energy;
            this.energy -= ElectricItem.manager.charge(itemstack, charge, (int) this.tier, true, false);
        }

    }

    public void playerstandsat(EntityPlayer entity) {
        if (this.player == null) {
            this.player = entity;
        } else if (this.player.getUniqueID() != entity.getUniqueID()) {
            this.player = entity;
        }
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        if (nbttagcompound.getString("UUID") != null)
            this.UUID = nbttagcompound.getString("UUID");
        this.temp = nbttagcompound.getShort("temp");

        if (((temp >> 6) & 1) == 1)
            this.movementchargeitemrf = true;
        if (((temp >> 5) & 1) == 1)
            this.movementchargeitem = true;
        if (((temp >> 4) & 1) == 1)
            this.movementcharge = true;
        if (((temp >> 3) & 1) == 1)
            this.movementchargerf = true;
        if ((temp & 1) == 1)
            this.personality = true;
        if (((temp >> 2) & 1) == 1)
            this.rfeu = true;

        if (((temp >> 1) & 1) == 1)
            this.rf = true;


        this.energy2 = Util.limit(nbttagcompound.getDouble("energy2"), 0.0D,
                this.maxStorage2);

        this.energy = Util.limit(nbttagcompound.getDouble("energy"), 0.0D,
                this.maxStorage);

    }

    public String getInventoryName() {
        return name;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        if (energy > 0)
            nbttagcompound.setDouble("energy", this.energy);
        if (energy2 > 0)
            nbttagcompound.setDouble("energy2", this.energy2);

        this.temp = (short) (this.movementchargeitemrf ? 1 : 0);
        this.temp = (short) ((this.temp << 1) + (short) (this.movementchargeitem ? 1 : 0));
        this.temp = (short) ((this.temp << 1) + (short) (this.movementcharge ? 1 : 0));
        this.temp = (short) ((this.temp << 1) + (short) (this.movementchargerf ? 1 : 0));
        this.temp = (short) ((this.temp << 1) + (short) (this.rfeu ? 1 : 0));
        this.temp = (short) ((this.temp << 1) + (short) (this.rf ? 1 : 0));
        this.temp = (short) ((this.temp << 1) + (short) (this.personality ? 1 : 0));
        nbttagcompound.setShort("temp", temp);


        if (this.UUID != null)
            nbttagcompound.setString("UUID", this.UUID);
    }

    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
    }

    public void onUnloaded() {
        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }
        super.onUnloaded();
    }

    public boolean acceptsEnergyFrom(TileEntity emitter, ForgeDirection direction) {
        return !facingMatchesDirection(direction);
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return facingMatchesDirection(direction);
    }

    public boolean facingMatchesDirection(ForgeDirection direction) {
        return (direction.ordinal() == getFacing());
    }

    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

        return receiveEnergy(maxReceive, simulate);

    }

    public int receiveEnergy(int paramInt, boolean paramBoolean) {
        int i = (int) Math.min(this.maxStorage2 - this.energy2, Math.min(this.output * Config.coefficientrf, paramInt));
        if (!paramBoolean)
            this.energy2 += i;
        return i;
    }

    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return extractEnergy((int) Math.min(this.output * Config.coefficientrf, maxExtract), simulate);
    }

    public int extractEnergy(int paramInt, boolean paramBoolean) {
        int i = (int) Math.min(this.energy2, Math.min(this.output * Config.coefficientrf, paramInt));
        if (!paramBoolean)
            this.energy2 -= i;
        return i;
    }

    public float getChargeLevel() {

        float ret = (float) ((float) this.energy / (this.maxStorage));

        if (ret > 1.0F)
            ret = 1.0F;
        return ret;
    }

    public float getChargeLevel1() {

        float ret = (float) ((float) this.energy2 / (this.maxStorage2));

        if (ret > 1.0F)
            ret = 1.0F;
        return ret;
    }

    public boolean canConnectEnergy(ForgeDirection arg0) {
        return true;
    }

    public int getEnergyStored(ForgeDirection from) {
        return (int) this.energy2;
    }

    public int getMaxEnergyStored(ForgeDirection from) {
        return (int) this.maxStorage2;
    }

    private void updateTileEntityField() {
        IC2.network.get().updateTileEntityField(this, "movementcharge");
        IC2.network.get().updateTileEntityField(this, "movementchargeitem");
        IC2.network.get().updateTileEntityField(this, "movementchargeitemrf");
        IC2.network.get().updateTileEntityField(this, "movementchargerf");
        IC2.network.get().updateTileEntityField(this, "energy");
        IC2.network.get().updateTileEntityField(this, "energy2");

    }

    public void updateEntityServer() {
        super.updateEntityServer();
        if (getWorldObj().provider.getWorldTime() % 20 == 0)
            updateTileEntityField();
        this.inputslotC.wirelessmodule();

        if (chargepad)
            if (this.player != null && this.energy >= 1.0D) {
                if (!getActive())
                    setActive(true);
                getItems(this.player);
                this.player = null;
                needsInvUpdate = true;
            } else if (getActive()) {
                setActive(false);
                needsInvUpdate = true;
            }

        if (this.UUID != null)
            personality = this.inputslotC.personality();

        this.output_plus = this.inputslotC.output_plus(this.l);
        this.output = this.l + this.output_plus;
        if (this.inputslotC != null) {
            this.movementcharge = this.inputslotC.getstats().get(0);
            this.movementchargeitem = this.inputslotC.getstats().get(1);
            this.movementchargerf = this.inputslotC.getstats().get(2);
            this.movementchargeitemrf = this.inputslotC.getstats().get(3);

            this.rf = this.inputslotC.getstats().get(4);
        }
        if (this.rf) {
            if (!this.rfeu) {
                if (energy > 0 && energy2 < maxStorage2) {

                    energy2 += energy * Config.coefficientrf;
                    energy -= energy;

                }
                if (energy2 > maxStorage2) {
                    double rf = (energy2 - maxStorage2);
                    energy += rf / Config.coefficientrf;
                    energy2 = maxStorage2;
                }
            } else {

                if (energy2 > 0 && energy < maxStorage) {

                    energy += (energy2 / Config.coefficientrf);
                    energy2 -= energy2;

                }
                if (energy > maxStorage) {
                    double rf = (energy - maxStorage);
                    energy2 += rf * Config.coefficientrf;
                    energy = maxStorage;
                }
            }
        }
        IEnergyContainerItem item;
        if (this.energy2 >= 1.0D && this.inputslotA.get(0) != null
                && this.inputslotA.get(0).getItem() instanceof IEnergyContainerItem) {
            item = (IEnergyContainerItem) this.inputslotA.get(0).getItem();
            if (item.getEnergyStored(this.inputslotA.get(0)) < item.getMaxEnergyStored(this.inputslotA.get(0)))
                extractEnergy1(item.receiveEnergy(this.inputslotA.get(0), (int) this.energy2, false),
                        false);
        }

        if (this.energy >= this.maxStorage) {
            this.energy = this.maxStorage;
        }
        if (this.energy < 0) {
            this.energy = 0;
        }
        if (this.energy2 < 0) {
            this.energy2 = 0;
        }
        if (this.energy2 >= this.maxStorage2) {
            this.energy2 = this.maxStorage2;
        }
        if (!this.inputslotA.isEmpty())
            if (this.inputslotA.charge(this.energy > 1D ? this.energy : 0, this.inputslotA.get(0), true) != 0) {
                this.energy -= this.inputslotA.charge(this.energy > 1D ? this.energy : 0, this.inputslotA.get(0), false);
                needsInvUpdate = ((this.energy > 1D ? this.energy : 0) > 0.0D);
            }
        if (this.inputslotB.get(0) != null)
            if (this.inputslotB.discharge(this.energy < this.maxStorage ? this.energy : 0, this.inputslotB.get(0), true) != 0) {

                this.energy += this.inputslotB.discharge(this.energy < this.maxStorage ? this.energy : 0, this.inputslotB.get(0), false);
                needsInvUpdate = ((this.energy > 1D ? this.energy : 0) > 0.0D);
            }
        if (this.rf) {
            for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
                if (this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ) == null)
                    continue;
                TileEntity tile = this.worldObj.getTileEntity(this.xCoord + side.offsetX, this.yCoord + side.offsetY,
                        this.zCoord + side.offsetZ);
                if (!(tile instanceof TileEntitySolarPanel)) {
                    if (tile instanceof IEnergyReceiver)
                        extractEnergy(side.getOpposite(), ((IEnergyReceiver) tile).receiveEnergy(side.getOpposite(),
                                extractEnergy(side.getOpposite(), (int) this.energy2, true), false), false);
                    else if (tile instanceof IEnergyHandler)
                        extractEnergy(side.getOpposite(), ((IEnergyHandler) tile).receiveEnergy(side.getOpposite(),
                                extractEnergy(side.getOpposite(), (int) this.energy2, true), false), false);
                }
            }
        }
        if (needsInvUpdate)
            markDirty();
    }

    public double getOfferedEnergy() {
        if (this.energy >= (this.output + this.output_plus))
            return Math.min(this.energy, (this.output + this.output_plus));
        return 0.0D;
    }

    public void drawEnergy(double amount) {
        this.energy -= amount;
    }

    public double getDemandedEnergy() {
        return this.maxStorage - this.energy;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (amount == 0D)
            return 0;
        if (this.energy >= this.maxStorage)
            return amount;
        if (this.energy + amount >= this.maxStorage) {
            double p = this.maxStorage - this.energy;
            this.energy += (p);
            return amount - (p);
        } else {
            this.energy += amount;
        }
        return 0.0D;
    }

    public int getSourceTier() {
        return (int) this.tier;
    }

    public int getSinkTier() {
        return (int) this.tier;
    }

    public ContainerBase<? extends TileEntityElectricBlock> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerElectricBlock(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIElectricBlock(new ContainerElectricBlock(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return (getFacing() != side);
    }

    public void setFacing(short facing) {
        if (this.addedToEnergyNet)
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        super.setFacing(facing);
        if (this.addedToEnergyNet) {
            this.addedToEnergyNet = false;
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }
    }

    public void onNetworkEvent(EntityPlayer player, int event) {
        this.rfeu = !this.rfeu;

    }

    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack ret = super.getWrenchDrop(entityPlayer);
        float energyRetainedInStorageBlockDrops = ConfigUtil.getFloat(MainConfig.get(),
                "balance/energyRetainedInStorageBlockDrops");
        if (energyRetainedInStorageBlockDrops > 0.0F) {

            NBTTagCompound nbttagcompound = NBTData.getOrCreateNbtData(ret);
            nbttagcompound.setDouble("energy", this.energy * energyRetainedInStorageBlockDrops);
            nbttagcompound.setDouble("energy2", this.energy2 * energyRetainedInStorageBlockDrops);
        }
        return ret;
    }

    public int getStored() {
        return (int) this.energy;
    }

    public void setStored(int energy1) {
        this.energy = energy1;
    }

    public boolean wrenchCanRemove(final EntityPlayer entityPlayer) {
        if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) instanceof TileEntityElectricBlock) {
            TileEntityElectricBlock tile = (TileEntityElectricBlock) this.worldObj.getTileEntity(this.xCoord,
                    this.yCoord, this.zCoord);
            List<String> list = new ArrayList<>();
            list.add(tile.UUID);
            for (int h = 0; h < 2; h++) {
                if (tile.inputslotC.get(h) != null && tile.inputslotC.get(h).getItem() instanceof AdditionModule
                        && tile.inputslotC.get(h).getItemDamage() == 0) {
                    for (int m = 0; m < 9; m++) {
                        NBTTagCompound nbt = ModUtils.nbt(tile.inputslotC.get(h));
                        String name = "player_" + m;
                        if (!nbt.getString(name).isEmpty())
                            list.add(nbt.getString(name));
                    }
                    break;
                }

            }

            if (tile.personality) {
                if (list.contains(entityPlayer.getDisplayName()) || entityPlayer.capabilities.isCreativeMode) {
                    return true;
                } else {
                    entityPlayer.addChatMessage(
                            new ChatComponentTranslation(StatCollector.translateToLocal("iu.error")));
                    return false;
                }

            } else {
                return true;

            }
        }
        return true;
    }

    public int getCapacity() {
        return (int) this.maxStorage;
    }

    public int getOutput() {
        return (int) (this.output + this.output_plus);
    }

    public double getOutputEnergyUnitsPerTick() {
        return this.output + this.output_plus;
    }

    public int addEnergy(int amount) {
        this.energy += amount;
        return amount;
    }

    public double extractEnergy1(double maxExtract, boolean simulate) {
        double temp;

        temp = this.energy2;

        if (temp > 0) {
            double energyExtracted = Math.min(temp, maxExtract);
            if (!simulate &&
                    this.energy2 - temp >= 0.0D) {
                this.energy2 -= temp;
                if (energyExtracted > 0) {
                    temp -= energyExtracted;
                    this.energy2 += temp;
                }
                return energyExtracted;
            }
        }
        return 0;
    }

    public boolean isTeleporterCompatible(ForgeDirection side) {
        return true;
    }

}
