package com.denfop.tiles.base;

import com.denfop.IUItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class TileEntityQuarryVein extends TileEntityElectricMachine implements IHasGui, INetworkUpdateListener, INetworkDataProvider, INetworkClientTileEntityEventListener {


    public boolean empty;
    public int x;
    public int y;
    public int z;
    public int progress;
    public int number;
    public boolean analysis;


    public TileEntityQuarryVein() {
        super(20, 14, 1);
        this.analysis = true;
        this.number = 0;
        this.progress = 0;
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.empty = true;
    }

    private void updateTileEntityField() {

        IC2.network.get().updateTileEntityField(this, "x");
        IC2.network.get().updateTileEntityField(this, "y");
        IC2.network.get().updateTileEntityField(this, "z");
        IC2.network.get().updateTileEntityField(this, "analysis");
        IC2.network.get().updateTileEntityField(this, "empty");

    }

    public void updateEntityServer() {
        super.updateEntityServer();
        if (worldObj.provider.getWorldTime() % 40 == 0)
            updateTileEntityField();
        if (worldObj.provider.dimensionId != 0) {
            this.empty = true;
            return;
        }

        if (true) {
            int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
            int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;

            if (this.worldObj.getTileEntity(chunkx, 0, chunkz) != null && (this.worldObj.getTileEntity(chunkx, 0, chunkz) instanceof TileOilBlock || this.worldObj.getTileEntity(chunkx, 0, chunkz) instanceof TileEntityVein)) {
                if (this.worldObj.getTileEntity(chunkx, 0, chunkz) instanceof TileEntityVein) {
                    TileEntityVein tile1 = (TileEntityVein) this.worldObj.getTileEntity(chunkx, 0, chunkz);
                    if (tile1.change) {
                        number = tile1.number;
                        this.analysis = false;
                        this.empty = false;
                        progress = 1200;
                        this.x = chunkx;
                        this.y = 0;
                        this.z = chunkz;
                        return;
                    }
                } else if (this.worldObj.getTileEntity(chunkx, 0, chunkz) instanceof TileOilBlock) {
                    TileOilBlock tile1 = (TileOilBlock) this.worldObj.getTileEntity(chunkx, 0, chunkz);
                    if (tile1.change && !tile1.empty) {
                        number = tile1.number;
                        this.analysis = false;
                        progress = 1200;
                        this.x = chunkx;
                        this.y = 0;
                        this.z = chunkz;
                        this.empty = tile1.empty;
                        return;
                    } else {
                        this.empty = true;
                    }

                }
            }
        }
        if (this.analysis && this.energy >= 5) {
            progress++;
            this.energy -= 5;
            if (progress >= 1200) {
                this.analysis = false;

                int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
                int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;
                Random rand = new Random();
                int p = rand.nextInt(100);
                if (p >= 10) {

                    worldObj.setBlock(chunkx, 0, chunkz, IUItem.oilblock);
                    TileOilBlock oil = (TileOilBlock) worldObj.getTileEntity(chunkx, 0, chunkz);
                    oil.change = true;
                    getnumber(oil);
                    this.x = chunkx;
                    this.y = 0;
                    this.z = chunkz;
                    number = oil.number;

                } else if (worldObj.getBlock(chunkx, 0, chunkz) != null && p < 10) {
                    int k = rand.nextInt(12);
                    worldObj.setBlock(chunkx, 0, chunkz, IUItem.vein, k, 3);
                    TileEntityVein vein = (TileEntityVein) worldObj.getTileEntity(chunkx, 0, chunkz);
                    vein.change = true;
                    vein.meta = k;
                    number = vein.number;
                    this.x = chunkx;
                    this.y = 0;
                    this.z = chunkz;

                }
            }


        }
    }

    private void getnumber(TileOilBlock tile) {
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(tile.xCoord, tile.zCoord);
        Random rand = worldObj.rand;

        if (BiomeGenBase.desert.equals(biome)) {
            int random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(50000) + 20000;
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (biome.biomeID == 130) {
            int random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(50000) + 20000;
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.ocean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 65) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.deepOcean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.frozenOcean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 65) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.desertHills.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(60000) + 20000;
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.river.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 55) {
                tile.number = rand.nextInt(20000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else if (BiomeGenBase.savanna.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 55) {
                tile.number = rand.nextInt(40000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        } else {
            int random;
            random = rand.nextInt(100);
            if (random > 75) {
                tile.number = rand.nextInt(20000);
            } else {
                tile.empty = true;
                tile.number = 0;
            }
        }
        tile.max = tile.number;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }


    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.empty = nbttagcompound.getBoolean("empty");
        this.analysis = nbttagcompound.getBoolean("analysis");
        this.progress = nbttagcompound.getInteger("progress");
        this.number = nbttagcompound.getInteger("number");
        this.x = nbttagcompound.getInteger("x1");
        this.y = nbttagcompound.getInteger("y1");
        this.z = nbttagcompound.getInteger("z1");
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("empty", this.empty);
        nbttagcompound.setBoolean("analysis", this.analysis);
        nbttagcompound.setInteger("progress", this.progress);
        nbttagcompound.setInteger("number", this.number);
        nbttagcompound.setInteger("x1", this.x);
        nbttagcompound.setInteger("y1", this.y);
        nbttagcompound.setInteger("z1", this.z);
    }


    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return null;
    }

    public ContainerBase<? extends TileEntityAnalyzer> getGuiContainer(EntityPlayer entityPlayer) {
        return null;
    }


    public void onNetworkEvent(EntityPlayer player, int event) {

    }


    public float getWrenchDropRate() {
        return 0.85F;
    }


    public void onGuiClosed(EntityPlayer arg0) {
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAnaluzer.name");
    }
}
