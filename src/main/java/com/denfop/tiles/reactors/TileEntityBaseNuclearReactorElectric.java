package com.denfop.tiles.reactors;

import com.denfop.Config;
import com.denfop.container.ContainerBaseNuclearReactor;
import com.denfop.damagesource.IUDamageSource;
import com.denfop.gui.GUINuclearReactor;
import com.denfop.invslot.InvSlotReactor;
import com.denfop.item.armour.ItemArmorAdvHazmat;
import com.denfop.tiles.base.TileEntityRadiationPurifier;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IMetaDelegate;
import ic2.api.reactor.IReactor;
import ic2.api.reactor.IReactorComponent;
import ic2.core.*;
import ic2.core.audio.AudioSource;
import ic2.core.audio.PositionSpec;
import ic2.core.block.TileEntityInventory;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public abstract class TileEntityBaseNuclearReactorElectric extends TileEntityInventory implements IHasGui, IReactor, IEnergySource, IMetaDelegate {
    public final int sizeX;
    public final int sizeY;
    public final double coef;
    public final InvSlotReactor reactorSlot;
    public boolean getblock;
    public float output = 0.0F;
    public int updateTicker;
    public int heat = 0;
    public int maxHeat = 10000;
    public float hem = 1.0F;
    public String background;
    public AudioSource audioSourceMain;
    public AudioSource audioSourceGeiger;
    public boolean addedToEnergyNet = false;
    protected boolean redstone = false;
    protected float lastOutput = 0.0F;
    protected List<TileEntity> subTiles;

    public TileEntityBaseNuclearReactorElectric(int sizeX, int sizeY, String background, double coef) {
        this.updateTicker = IC2.random.nextInt(this.getTickRate());
        this.reactorSlot = new InvSlotReactor(this, "reactor", 0, sizeX * sizeY);
        this.getblock = false;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.background = background;
        this.coef = coef;
    }

    abstract void setblock();


    public void onLoaded() {
        super.onLoaded();
        if (IC2.platform.isSimulating() && !this.isFluidCooled()) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

    }

    public void onUnloaded() {
        if (IC2.platform.isRendering()) {
            IC2.audioManager.removeSources(this);
            this.audioSourceMain = null;
            this.audioSourceGeiger = null;
        }

        if (IC2.platform.isSimulating() && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        super.onUnloaded();
    }

    public String getInventoryName() {
        return "Nuclear Reactor";
    }

    public int gaugeHeatScaled(int i) {
        return i * this.heat / (this.maxHeat / 100 * 85);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.heat = nbttagcompound.getInteger("heat");
        this.output = (float) nbttagcompound.getDouble("output");
        getblock = nbttagcompound.getBoolean("getblock");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("heat", this.heat);
        nbttagcompound.setDouble("output", this.getReactorEnergyOutput());
        nbttagcompound.setBoolean("getblock", this.getblock);

    }

    public void setRedstoneSignal(boolean redstone) {
        this.redstone = redstone;
    }

    public void drawEnergy(double amount) {
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return this.getReactorEnergyOutput() * 5.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/nuclear");
    }

    public int getSourceTier() {
        return 4;
    }

    public double getReactorEUEnergyOutput() {
        return this.getOfferedEnergy();
    }


    public void refreshChambers() {
        if (this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
        }

        this.subTiles = null;
        if (this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
        }

    }

    public void updateEntityServer() {
        super.updateEntityServer();

        if (this.getActive())
            if (this.worldObj.provider.getWorldTime() % 200 == 0) {
                for (int x = this.xCoord - 1; x <= this.xCoord + 1; x++) {
                    for (int z = this.zCoord - 1; z <= this.zCoord + 1; z++) {
                        for (int y = this.yCoord - 1; y <= this.yCoord + 1; y++) {
                            if (getWorld().getTileEntity(x, y, z) instanceof TileEntityRadiationPurifier) {
                                TileEntityRadiationPurifier tile = (TileEntityRadiationPurifier) getWorld().getTileEntity(x, y, z);
                                if (tile.getActive()) {
                                    getblock = true;
                                    return;
                                } else {
                                    getblock = false;
                                }
                            } else {
                                getblock = false;

                            }
                        }
                    }
                }
            }

        if (this.getActive())
            if (!getblock) {
                int radius = 5;
                AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius, this.xCoord + radius, this.yCoord + radius, this.zCoord + radius);
                List<EntityPlayer> list = getWorld().getEntitiesWithinAABB(EntityPlayer.class, axisalignedbb);
                for (EntityPlayer player : list) {
                    if (!ItemArmorAdvHazmat.hasCompleteHazmat(player))
                        player.attackEntityFrom(IUDamageSource.radiation, 1.0F);
                }
            }
        if (this.updateTicker++ % this.getTickRate() == 0) {
            if (!this.worldObj.doChunksNearChunkExist(this.xCoord, this.yCoord, this.zCoord, 2)) {
                this.output = 0.0F;
            } else {


                this.output = 0.0F;
                this.maxHeat = 10000;
                this.hem = 1.0F;
                this.processChambers();


                if (this.calculateHeatEffects()) {
                    return;
                }

                this.setActive(this.heat >= 1000 || this.output > 0.0F);
                this.markDirty();
            }

            IC2.network.get().updateTileEntityField(this, "output");
        }
    }

    public boolean isUsefulItem(ItemStack stack) {
        Item item = stack.getItem();
        if (item == null) {
            return false;
        } else if (item instanceof IReactorComponent) {
            return true;
        } else {
            return item == Ic2Items.TritiumCell.getItem() || item == Ic2Items.reactorDepletedUraniumSimple.getItem() || item == Ic2Items.reactorDepletedUraniumDual.getItem() || item == Ic2Items.reactorDepletedUraniumQuad.getItem() || item == Ic2Items.reactorDepletedMOXSimple.getItem() || item == Ic2Items.reactorDepletedMOXDual.getItem() || item == Ic2Items.reactorDepletedMOXQuad.getItem();
        }
    }

    public boolean calculateHeatEffects() {
        if (this.heat >= 4000 && IC2.platform.isSimulating() && !(ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit") <= 0.0F)) {
            float power = (float) this.heat / (float) this.maxHeat;
            if (power >= 1.0F) {
                if (Config.explode) {
                    this.explode();
                } else {
                    setblock();
                }
                return true;
            } else {
                int[] coord;
                Block block;
                Material mat;
                if (power >= 0.85F && this.worldObj.rand.nextFloat() <= 0.2F * this.hem) {
                    coord = this.getRandCoord(2);
                    if (coord != null) {
                        block = this.worldObj.getBlock(coord[0], coord[1], coord[2]);
                        if (block.isAir(this.worldObj, coord[0], coord[1], coord[2])) {
                            this.worldObj.setBlock(coord[0], coord[1], coord[2], Blocks.fire, 0, 7);
                        } else if (block.getBlockHardness(this.worldObj, coord[0], coord[1], coord[2]) >= 0.0F && this.worldObj.getTileEntity(coord[0], coord[1], coord[2]) == null) {
                            mat = block.getMaterial();
                            if (mat != Material.rock && mat != Material.iron && mat != Material.lava && mat != Material.ground && mat != Material.clay) {
                                this.worldObj.setBlock(coord[0], coord[1], coord[2], Blocks.fire, 0, 7);
                            } else {
                                this.worldObj.setBlock(coord[0], coord[1], coord[2], Blocks.flowing_lava, 15, 7);
                            }
                        }
                    }
                }

                if (power >= 0.7F) {
                    List list1 = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(this.xCoord - 3, this.yCoord - 3, this.zCoord - 3, this.xCoord + 4, this.yCoord + 4, this.zCoord + 4));

                    for (Object o : list1) {
                        Entity ent = (Entity) o;
                        ent.attackEntityFrom(IC2DamageSource.radiation, (float) ((int) ((float) this.worldObj.rand.nextInt(4) * this.hem)));
                    }
                }

                if (power >= 0.5F && this.worldObj.rand.nextFloat() <= this.hem) {
                    coord = this.getRandCoord(2);
                    if (coord != null) {
                        block = this.worldObj.getBlock(coord[0], coord[1], coord[2]);
                        if (block.getMaterial() == Material.water) {
                            this.worldObj.setBlockToAir(coord[0], coord[1], coord[2]);
                        }
                    }
                }

                if (power >= 0.4F && this.worldObj.rand.nextFloat() <= this.hem) {
                    coord = this.getRandCoord(2);
                    if (coord != null && this.worldObj.getTileEntity(coord[0], coord[1], coord[2]) == null) {
                        block = this.worldObj.getBlock(coord[0], coord[1], coord[2]);
                        mat = block.getMaterial();
                        if (mat == Material.wood || mat == Material.leaves || mat == Material.cloth) {
                            this.worldObj.setBlock(coord[0], coord[1], coord[2], Blocks.fire, 0, 7);
                        }
                    }
                }

                return false;
            }
        } else {
            return false;
        }
    }

    public int[] getRandCoord(int radius) {
        if (radius <= 0) {
            return null;
        } else {
            int[] c = new int[]{this.xCoord + this.worldObj.rand.nextInt(2 * radius + 1) - radius, this.yCoord + this.worldObj.rand.nextInt(2 * radius + 1) - radius, this.zCoord + this.worldObj.rand.nextInt(2 * radius + 1) - radius};
            return c[0] == this.xCoord && c[1] == this.yCoord && c[2] == this.zCoord ? null : c;
        }
    }

    public void processChambers() {
        int size = this.getReactorSize();
//TODO y <7 this height of the reactor is 7 maximum, for the rest change 7 to your value
        for (int pass = 0; pass < 2; ++pass) {
            for (int y = 0; y < sizeY; ++y) {
                for (int x = 0; x < size; ++x) {
                    ItemStack stack = this.reactorSlot.get(x, y);
                    if (stack != null && stack.getItem() instanceof IReactorComponent) {
                        IReactorComponent comp = (IReactorComponent) stack.getItem();
                        comp.processChamber(this, stack, x, y, pass == 0);
                    }
                }
            }
        }

    }

    public boolean produceEnergy() {
        return this.receiveredstone() && ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator") > 0.0F;
    }

    public boolean receiveredstone() {
        return this.worldObj.isBlockIndirectlyGettingPowered(this.xCoord, this.yCoord, this.zCoord) || this.redstone;
    }

    public abstract short getReactorSize();


    public int getTickRate() {
        return 20;
    }

    public ContainerBase<TileEntityBaseNuclearReactorElectric> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerBaseNuclearReactor(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUINuclearReactor(new ContainerBaseNuclearReactor(entityPlayer, this));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public void onNetworkUpdate(String field) {
        if (field.equals("output")) {
            if (this.output > 0.0F) {
                if (this.lastOutput <= 0.0F) {
                    if (this.audioSourceMain == null) {
                        this.audioSourceMain = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/NuclearReactorLoop.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    }

                    if (this.audioSourceMain != null) {
                        this.audioSourceMain.play();
                    }
                }

                if (this.output < 40.0F) {
                    if (this.lastOutput <= 0.0F || this.lastOutput >= 40.0F) {
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.remove();
                        }

                        this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerLowEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.play();
                        }
                    }
                } else if (this.output < 80.0F) {
                    if (this.lastOutput < 40.0F || this.lastOutput >= 80.0F) {
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.remove();
                        }

                        this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerMedEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                        if (this.audioSourceGeiger != null) {
                            this.audioSourceGeiger.play();
                        }
                    }
                } else if (this.output >= 80.0F && this.lastOutput < 80.0F) {
                    if (this.audioSourceGeiger != null) {
                        this.audioSourceGeiger.remove();
                    }

                    this.audioSourceGeiger = IC2.audioManager.createSource(this, PositionSpec.Center, "Generators/NuclearReactor/GeigerHighEU.ogg", true, false, IC2.audioManager.getDefaultVolume());
                    if (this.audioSourceGeiger != null) {
                        this.audioSourceGeiger.play();
                    }
                }
            } else if (this.lastOutput > 0.0F) {
                if (this.audioSourceMain != null) {
                    this.audioSourceMain.stop();
                }

                if (this.audioSourceGeiger != null) {
                    this.audioSourceGeiger.stop();
                }
            }

            this.lastOutput = this.output;
        }

        super.onNetworkUpdate(field);
    }

    public float getWrenchDropRate() {
        return 1;
    }


    public ChunkCoordinates getPosition() {
        return new ChunkCoordinates(this.xCoord, this.yCoord, this.zCoord);
    }

    public World getWorld() {
        return this.worldObj;
    }

    public int getHeat() {
        return this.heat;
    }

    public void setHeat(int heat1) {
        this.heat = heat1;
    }

    public int addHeat(int amount) {
        this.heat += amount;
        return this.heat;
    }

    public ItemStack getItemAt(int x, int y) {
        return x >= 0 && x < this.getReactorSize() && y >= 0 && y < this.sizeY ? this.reactorSlot.get(x, y) : null;
    }


    public void setItemAt(int x, int y, ItemStack item) {
        if (x >= 0 && x < this.getReactorSize() && y >= 0 && y <  this.sizeY) {
            this.reactorSlot.put(x, y, item);
        }
    }

    public abstract void explode();


    public void addEmitHeat(int heat) {

    }

    public int getMaxHeat() {
        return this.maxHeat;
    }

    public void setMaxHeat(int newMaxHeat) {
        this.maxHeat = newMaxHeat;
    }

    public float getHeatEffectModifier() {
        return this.hem;
    }

    public void setHeatEffectModifier(float newHEM) {
        this.hem = newHEM;
    }

    public float getReactorEnergyOutput() {
        return (float) (this.output * this.coef);
    }

    public float addOutput(float energy) {
        return this.output += energy;
    }


    public boolean isFluidCooled() {
        return false;
    }


    public int getInventoryStackLimit() {
        return 1;
    }
}
