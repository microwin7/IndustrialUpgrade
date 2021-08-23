package com.denfop.tiles.base;

import com.denfop.IUItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Map;
import java.util.Random;

public class TileEntityQuarryOil extends TileEntityElectricMachine implements IHasGui,  INetworkUpdateListener, INetworkDataProvider, INetworkClientTileEntityEventListener {


    public  int progress;
    public int number;
    public  boolean analysis;


    public TileEntityQuarryOil() {
        super(100000, 14, 1);
        analysis = true;
        this.number = 0;
        this.progress = 0;
    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        if(!this.analysis){
            Map map = this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).chunkTileEntityMap;

            for (Object o : map.values()) {
                TileEntity tile = (TileEntity) o;
                if (tile instanceof TileOilBlock) {
                    TileOilBlock tile1 = (TileOilBlock) tile;
                    if (tile1.change) {
                        number = tile1.number;
                        return;
                    }
                }

            }
        }
        if (this.analysis && this.energy >= 5) {
            progress++;
            this.useEnergy(5);
            if (progress >= 1200) {
                this.analysis = false;
                Map map = this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).chunkTileEntityMap;
                boolean getOilBlock = false;
                for (Object o : map.values()) {
                    TileEntity tile = (TileEntity) o;
                    if (tile instanceof TileOilBlock) {
                        TileOilBlock tile1 = (TileOilBlock) tile;
                        if (tile1.change) {
                            number = tile1.number;
                            return;
                        }
                    }

                }
                if (!getOilBlock) {
                    int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
                    int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;

                    for (int x = chunkx; x < chunkx + 16; x++)
                        for (int z = chunkz; z < chunkz + 16; z++)
                            for (int y = 0; y < 4; y++) {
                                if (worldObj.getBlock(x, y, z) != null) {
                                    if (worldObj.getBlock(x, y, z).equals(Blocks.bedrock)) {
                                        worldObj.setBlock(x, y, z, IUItem.oilblock);
                                        TileOilBlock oil = (TileOilBlock) worldObj.getTileEntity(x, y, z);
                                        oil.change = true;
                                        getnumber(oil);
                                        number = oil.number;
                                        return;
                                    }
                                }
                            }
                }
            }
        }
    }

    private void getnumber(TileOilBlock tile) {
        BiomeGenBase biome = worldObj.getBiomeGenForCoords(tile.xCoord,tile.zCoord);
        Random rand = new Random();

        if (BiomeGenBase.desert.equals(biome)) {
            int random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(50000) + 20000;
            } else {
                tile.number = 0;
            }
        } else if (biome.biomeID == 130) {
            int random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(50000) + 20000;
            } else {
                tile.number = 0;
            }
        } else if (BiomeGenBase.ocean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 65) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.number = 0;
            }
        }else if (BiomeGenBase.deepOcean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.number = 0;
            }
        }else if (BiomeGenBase.frozenOcean.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 65) {
                tile.number = rand.nextInt(80000);
            } else {
                tile.number = 0;
            }
        }
        else if (BiomeGenBase.desertHills.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 40) {
                tile.number = rand.nextInt(60000)+ 20000;
            } else {
                tile.number = 0;
            }
        } else if (BiomeGenBase.river.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 55) {
                tile.number = rand.nextInt(20000);
            } else {
                tile.number = 0;
            }
        }
        else if (BiomeGenBase.savanna.equals(biome)) {
            int random;
            random = rand.nextInt(100);
            if (random > 55) {
                tile.number = rand.nextInt(40000);
            } else {
                tile.number = 0;
            }
        } else {
            int random;
            random = rand.nextInt(100);
            if (random > 75) {
                tile.number = rand.nextInt(20000);
            } else {
                tile.number = 0;
            }
        }
        tile.max=tile.number;
    }
    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }
    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {

        if (amount == 0.0D)
            return 0.0D;
        if (this.energy >= this.maxEnergy)
            return amount;
        if (this.energy + amount >= this.maxEnergy) {
            double p = this.maxEnergy - this.energy;
            this.energy += p;
            return amount - p;
        }
        this.energy += amount;
        return 0.0D;
    }



    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.analysis=nbttagcompound.getBoolean("analysis");
        this.progress = nbttagcompound.getInteger("progress");
        this.number= nbttagcompound.getInteger("number");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
       nbttagcompound.setBoolean("analysis",this.analysis);
        nbttagcompound.setInteger("progress",this.progress);
        nbttagcompound.setInteger("number",this.number);

    }


    public void useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
        }
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
       return  null;
    }

    public ContainerBase<? extends TileEntityAnalyzer> getGuiContainer(EntityPlayer entityPlayer) {
        return  null;
    }



    public void onNetworkEvent(EntityPlayer player, int event) {

    }



    public float getWrenchDropRate() {
        return 0.85F;
    }



    public void onGuiClosed(EntityPlayer arg0) {}

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAnaluzer.name");
    }
}
