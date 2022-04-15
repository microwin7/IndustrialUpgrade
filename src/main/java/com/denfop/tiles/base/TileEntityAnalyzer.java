package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerAnalyzer;
import com.denfop.gui.GUIAnalyzer;
import com.denfop.invslot.InvSlotAnalyzer;
import com.denfop.tiles.mechanism.TileEntityBaseQuantumQuarry;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.Direction;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.network.INetworkUpdateListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileEntityAnalyzer extends TileEntityElectricMachine implements IHasGui, INetworkUpdateListener, INetworkDataProvider, INetworkClientTileEntityEventListener, INetworkTileEntityEventListener {
    public final InvSlotAnalyzer inputslot;
    public final InvSlotAnalyzer inputslotA;
    public int breakblock;
    public int numberores;
    public double sum;
    public int sum1;
    public boolean analysis;
    public int xTempChunk;
    public int zTempChunk;
    public int xChunk;
    public int zChunk;
    public int xendChunk;
    public int zendChunk;
    public int[] listnumberore1;
    public List<String> listore;
    public List<Integer> listnumberore;
    public List<Integer> yore;
    public List<Double> middleheightores;
    public int[][] chunksx;
    public int[][] chunksz;
    public int xcoord;
    public int zcoord;
    public int xendcoord;
    public int zendcoord;
    public boolean start = true;
    public AudioSource audioSource;
    List<Integer> y1;
    private boolean quarry;
    private int y;

    public TileEntityAnalyzer() {
        super(100000, 14, 1);
        this.listore = new ArrayList<>();

        this.listnumberore = new ArrayList<>();
        this.yore = new ArrayList<>();
        this.middleheightores = new ArrayList<>();
        this.analysis = false;
        this.sum = 0;
        this.sum1 = 0;
        this.numberores = 0;
        this.breakblock = 0;
        this.quarry = false;
        this.inputslot = new InvSlotAnalyzer(this, 3, "input", 3, 0);
        this.inputslotA = new InvSlotAnalyzer(this, 2, "input1", 1, 1);
        this.y1 = new ArrayList<>();
        this.y = 257;
    }

    public double getProgress() {

        double temp = xChunk - xendChunk;
        double temp1 = zChunk - zendChunk;
        if (temp < 0) {
            temp *= -1;
        }
        if (temp1 < 0) {
            temp1 *= -1;
        }
        return Math.min(((temp * temp1 * this.y) / (temp * temp1 * 256)), 1);
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
        int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;
        int size = this.inputslot.getChunksize();
        this.xChunk = chunkx - 16 * size;
        this.zChunk = chunkz - 16 * size;
        this.xendChunk = chunkx + 16 + 16 * size;
        this.zendChunk = chunkz + 16 + 16 * size;
        if (this.analysis) {
            analyze();
        }
        if (this.quarry) {

            setActive(true);
            if (this.inputslot.getwirelessmodule()) {
                List list6 = this.inputslot.wirelessmodule();
                int xx = (int) list6.get(0);
                int yy = (int) list6.get(1);
                int zz = (int) list6.get(2);
                if (this.worldObj.getTileEntity(xx, yy, zz) != null && this.worldObj.getTileEntity(xx, yy, zz) instanceof TileEntityBaseQuantumQuarry) {
                    TileEntityBaseQuantumQuarry target1 = (TileEntityBaseQuantumQuarry) this.worldObj.getTileEntity(xx, yy, zz);
                    quarry(target1);
                }

            } else {
                for (Direction direction : Direction.directions) {
                    TileEntity target = direction.applyToTileEntity(this);
                    if (target instanceof TileEntityBaseQuantumQuarry) {
                        TileEntityBaseQuantumQuarry target1 = (TileEntityBaseQuantumQuarry) target;
                        quarry(target1);
                    }
                }
            }


        }

    }

    public void analyze() {


        if (this.y >= 257 && this.start) {
            int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
            int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;
            this.y = 0;
            this.breakblock = 0;
            this.numberores = 0;
            this.sum = 0;
            this.sum1 = 0;
            setActive(true);
            this.yore = new ArrayList<>();
            this.listore = new ArrayList<>();
            this.listnumberore = new ArrayList<>();
            this.y1 = new ArrayList<>();
            this.middleheightores = new ArrayList<>();
            int size = this.inputslot.getChunksize();
            int size1 = size * 2 + 1;
            this.xTempChunk = chunkx - 16 * size;
            this.zTempChunk = chunkz - 16 * size;
            this.chunksx = new int[size1][size1];
            this.chunksz = new int[size1][size1];
            this.xcoord = 0;
            this.zcoord = 0;
            xendcoord = size1;
            zendcoord = size1;
            for (int i = 0; i < size1; i++)
                for (int j = 0; j < size1; j++) {
                    int m1 = 1;
                    int m2 = 1;
                    if (i < size)
                        m1 = -1;
                    if (j < size)
                        m2 = -1;
                    m1 = i == size1 ? 0 : m1;
                    m2 = j == size1 ? 0 : m2;
                    this.chunksx[i][j] = chunkx + 16 * i * m1;
                    this.chunksz[i][j] = chunkz + 16 * j * m2;
                }
            this.xChunk = chunkx - 16 * size;
            this.zChunk = chunkz - 16 * size;

            this.xendChunk = chunkx + 16 + 16 * size;
            this.zendChunk = chunkz + 16 + 16 * size;
            this.start = false;

        }
        int tempx = this.chunksx[this.xcoord][this.zcoord];
        int tempz = this.chunksz[this.xcoord][this.zcoord];
        List<String> blacklist = this.inputslot.getblacklist();
        List<String> whitelist = this.inputslot.getwhitelist();
        if (this.worldObj.provider.getWorldTime() % 4 == 0)
            for (int x = tempx; x < tempx + 16; x++) {
                for (int z = tempz; z < tempz + 16; z++) {
                    if (this.energy < 1)
                        break;
                    this.energy -= 1;
                    initiate(0);
                    if (!this.worldObj.isAirBlock(x, this.y, z))
                        if (this.worldObj.getBlock(x, this.y, z) != null) {
                            this.breakblock++;

                            if (this.worldObj.getBlock(x, this.y, z).getMaterial() == Material.iron || this.worldObj.getBlock(x, this.y, z).getMaterial() == Material.rock) {
                                Block block = this.worldObj.getBlock(x, this.y, z);
                                ItemStack stack = new ItemStack(block, 1, this.worldObj.getBlockMetadata(x, this.y, z));
                                int id = OreDictionary.getOreID(stack);
                                String name = OreDictionary.getOreName(id);
                                if (name.startsWith("ore")) {
                                    if (!this.inputslot.CheckBlackList(blacklist, name) && this.inputslot.CheckWhiteList(whitelist, name)) {

                                        if (listore.isEmpty()) {
                                            listore.add(name);
                                            listnumberore.add(1);
                                            yore.add(y);
                                            this.y1.add(this.y);
                                            this.numberores = listore.size();
                                            listnumberore1 = new int[listnumberore.size()];
                                            for (int i = 0; i < listnumberore.size(); i++)
                                                listnumberore1[i] = listnumberore.get(i);

                                            this.sum = ModUtils.getsum1(listnumberore) - listnumberore.size();
                                            this.sum1 = ModUtils.getsum1(this.y1);
                                            this.middleheightores = new ArrayList<>();
                                            for (int i = 0; i < this.listore.size(); i++)
                                                this.middleheightores.add((this.yore.get(i) / (double) this.listnumberore.get(i)));

                                        }

                                        if (!listore.contains(name)) {


                                            listore.add(name);
                                            listnumberore.add(1);
                                            yore.add(y);
                                            this.y1.add(this.y);
                                            this.numberores = listore.size();
                                            listnumberore1 = new int[listnumberore.size()];
                                            for (int i = 0; i < listnumberore.size(); i++)
                                                listnumberore1[i] = listnumberore.get(i);

                                            this.sum = ModUtils.getsum1(listnumberore) - listnumberore.size();
                                            this.sum1 = ModUtils.getsum1(this.y1);
                                            this.middleheightores = new ArrayList<>();
                                            for (int i = 0; i < this.listore.size(); i++)
                                                this.middleheightores.add((this.yore.get(i) / (double) this.listnumberore.get(i)));

                                        }
                                        if (listore.contains(name)) {
                                            yore.set(listore.indexOf(name), yore.get(listore.indexOf(name)) + y);

                                            listnumberore.set(listore.indexOf(name), listnumberore.get(listore.indexOf(name)) + 1);
                                            this.y1.add(this.y);
                                            this.numberores = listore.size();
                                            listnumberore1 = new int[listnumberore.size()];
                                            for (int i = 0; i < listnumberore.size(); i++)
                                                listnumberore1[i] = listnumberore.get(i);

                                            this.sum = ModUtils.getsum1(listnumberore) - listnumberore.size();
                                            this.sum1 = ModUtils.getsum1(this.y1);
                                            this.middleheightores = new ArrayList<>();
                                            for (int i = 0; i < this.listore.size(); i++)
                                                this.middleheightores.add((this.yore.get(i) / (double) this.listnumberore.get(i)));

                                        }


                                    }
                                }
                            }
                        }
                }
            }

        if (this.worldObj.provider.getWorldTime() % 4 == 0) {
            this.y++;


            if (this.y >= 257) {
                zcoord++;
                this.y = 0;
                if (zcoord == zendcoord) {
                    xcoord++;
                    zcoord = 0;
                    if (xcoord == xendcoord)
                        zcoord = zendcoord;
                }

            }

            if (xcoord == xendcoord && zcoord == zendcoord) {
                this.analysis = false;
                this.setActive(false);
                xTempChunk = this.chunksx[this.xcoord - 1][this.zcoord - 1];
                zTempChunk = this.chunksz[this.xcoord - 1][this.zcoord - 1];
                this.y = 257;
                this.start = true;
                this.middleheightores = new ArrayList<>();
                for (int i = 0; i < this.listore.size(); i++)
                    this.middleheightores.add((this.yore.get(i) / (double) this.listnumberore.get(i)));
                initiate(2);
            }
        }
    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }

    public void quarry(TileEntityBaseQuantumQuarry target1) {
        if (this.y >= 257 && this.start) {
            int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
            int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;
            this.y = 0;
            this.breakblock = 0;
            this.numberores = 0;
            this.sum = 0;
            this.sum1 = 0;
            setActive(true);
            int size = this.inputslot.getChunksize();
            int size1 = size * 2 + 1;
            this.xTempChunk = chunkx - 16 * size;
            this.zTempChunk = chunkz - 16 * size;
            this.chunksx = new int[size1][size1];
            this.chunksz = new int[size1][size1];
            this.xcoord = 0;
            this.zcoord = 0;
            xendcoord = size1;
            zendcoord = size1;
            for (int i = 0; i < size1; i++)
                for (int j = 0; j < size1; j++) {
                    int m1 = 1;
                    int m2 = 1;
                    if (i < size)
                        m1 = -1;
                    if (j < size)
                        m2 = -1;
                    m1 = i == size1 ? 0 : m1;
                    m2 = j == size1 ? 0 : m2;
                    this.chunksx[i][j] = chunkx + 16 * i * m1;
                    this.chunksz[i][j] = chunkz + 16 * j * m2;
                }
            this.xChunk = chunkx - 16 * size;
            this.zChunk = chunkz - 16 * size;

            this.xendChunk = chunkx + 16 + 16 * size;
            this.zendChunk = chunkz + 16 + 16 * size;
            this.start = false;
        }
        int tempx = this.chunksx[this.xcoord][this.zcoord];
        int tempz = this.chunksz[this.xcoord][this.zcoord];
        List<String> blacklist = this.inputslot.getblacklist();
        List<String> whitelist = this.inputslot.getwhitelist();
        if (this.worldObj.provider.getWorldTime() % 4 == 0)
            for (int x = tempx; x < tempx + 16; x++) {
                for (int z = tempz; z < tempz + 16; z++) {
                    if (this.energy < 1)
                        break;
                    this.energy -= 1;
                    initiate(0);
                    if (!this.worldObj.isAirBlock(x, this.y, z))
                        if (this.worldObj.getBlock(x, this.y, z) != null) {
                            this.breakblock++;

                            if (this.worldObj.getBlock(x, this.y, z).getMaterial() == Material.iron || this.worldObj.getBlock(x, this.y, z).getMaterial() == Material.rock) {
                                Block block = this.worldObj.getBlock(x, this.y, z);
                                ItemStack stack = new ItemStack(block, 1, this.worldObj.getBlockMetadata(x, y, z));
                                int id = OreDictionary.getOreID(stack);
                                String name = OreDictionary.getOreName(id);
                                if (name.startsWith("ore")) {
                                    if (!(!this.inputslot.CheckBlackList(blacklist, name) && this.inputslot.CheckWhiteList(whitelist, name)))
                                        continue;
                                    double energycost = this.inputslot.getenergycost(target1);
                                    String temp = name.substring(3);

                                    if (temp.startsWith("Infused"))
                                        temp = name.substring("Infused".length() + 3);

                                    if (!name.equals("oreRedstone") && (OreDictionary.getOres("gem" + temp) == null || OreDictionary.getOres("gem" + temp).size() < 1) && (OreDictionary.getOres("shard" + temp) == null || OreDictionary.getOres("shard" + temp).size() < 1)) {

                                        boolean furnace = this.inputslot.getFurnaceModule();
                                        if (!furnace) {
                                            if (!TileEntityBaseQuantumQuarry.list(target1, stack))
                                                if (target1.energy >= energycost &&
                                                        target1.outputSlot.canAdd(stack)) {
                                                    target1.outputSlot.add(stack);
                                                    this.worldObj.setBlockToAir(x, y, z);
                                                    target1.energy -= energycost;
                                                    target1.getblock++;
                                                }
                                        } else {
                                            temp = name.substring(3);
                                            temp = "ingot" + temp;
                                            if (OreDictionary.getOres(temp).isEmpty()) {
                                                if (!TileEntityBaseQuantumQuarry.list(target1, stack))
                                                    if (target1.energy >= energycost &&
                                                            target1.outputSlot.canAdd(stack)) {
                                                        target1.outputSlot.add(stack);
                                                        this.worldObj.setBlockToAir(x, y, z);
                                                        target1.energy -= energycost;
                                                        target1.getblock++;
                                                    }
                                            } else {

                                                ItemStack stack1 = OreDictionary.getOres(temp).get(0);
                                                if (!TileEntityBaseQuantumQuarry.list(target1, stack))

                                                    if (target1.energy >= energycost &&
                                                            target1.outputSlot.canAdd(stack1)) {
                                                        target1.outputSlot.add(stack1);
                                                        this.worldObj.setBlockToAir(x, y, z);
                                                        target1.energy -= energycost;
                                                        target1.getblock++;
                                                    }
                                            }
                                        }
                                    } else {
                                        ItemStack gem = null;

                                        if (OreDictionary.getOres("gem" + temp).size() != 0)
                                            gem = OreDictionary.getOres("gem" + temp).get(0);
                                        else if (OreDictionary.getOres("shard" + temp).size() != 0)
                                            gem = OreDictionary.getOres("shard" + temp).get(0);
                                        else if (OreDictionary.getOres("dust" + temp).size() != 0)
                                            gem = OreDictionary.getOres("dust" + temp).get(0);
                                        int chance2 = this.inputslot.lucky();


                                        List<Boolean> get = new ArrayList<>();
                                        if (!TileEntityBaseQuantumQuarry.list(target1, stack))
                                            if (target1.energy >= energycost)
                                                for (int j = 0; j < chance2 + 1; j++) {
                                                    if (target1.outputSlot.canAdd(gem)) {
                                                        target1.outputSlot.add(gem);
                                                        get.add(true);
                                                    } else {
                                                        get.add(false);
                                                    }
                                                }
                                        if (ModUtils.Boolean(get)) {
                                            this.worldObj.setBlockToAir(x, y, z);
                                            target1.energy -= energycost;
                                            target1.getblock++;
                                        }

                                    }
                                }
                            }
                        }
                }
            }

        if (this.worldObj.provider.getWorldTime() % 4 == 0) {
            this.y++;


            if (this.y >= 257) {
                zcoord++;
                this.y = 0;
                if (zcoord == zendcoord) {
                    xcoord++;
                    zcoord = 0;
                    if (xcoord == xendcoord)
                        zcoord = zendcoord;
                }

            }

            if (xcoord == xendcoord && zcoord == zendcoord) {
                this.setActive(false);
                xTempChunk = this.chunksx[this.xcoord - 1][this.zcoord - 1];
                zTempChunk = this.chunksz[this.xcoord - 1][this.zcoord - 1];
                this.y = 257;
                this.start = true;
                this.quarry = false;
                this.analysis = true;
                initiate(2);
                this.analyze();
            }
        }
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


        this.start = nbttagcompound.getBoolean("start");
        this.xChunk = nbttagcompound.getInteger("xChunk");
        this.zChunk = nbttagcompound.getInteger("zChunk");
        this.xendChunk = nbttagcompound.getInteger("xendChunk");
        this.zendChunk = nbttagcompound.getInteger("zendChunk");
        this.sum = nbttagcompound.getDouble("sum");
        this.sum1 = nbttagcompound.getInteger("sum1");
        this.breakblock = nbttagcompound.getInteger("breakblock");
        this.numberores = nbttagcompound.getInteger("numberores");

        int size4 = nbttagcompound.getInteger("size4");
        int size = nbttagcompound.getInteger("size");
        int size1 = nbttagcompound.getInteger("size1");
        int size2 = nbttagcompound.getInteger("size2");
        int size3 = nbttagcompound.getInteger("size3");
        for (int i = 0; i < size; i++)
            this.listore.add(nbttagcompound.getString("ore" + i));
        for (int i = 0; i < size1; i++)
            this.listnumberore.add(nbttagcompound.getInteger("number" + i));
        for (int i = 0; i < size2; i++)
            this.y1.add(nbttagcompound.getInteger("y" + i));
        for (int i = 0; i < size4; i++)
            this.yore.add(nbttagcompound.getInteger("yore" + i));

        for (int i = 0; i < size3; i++)
            this.middleheightores.add(nbttagcompound.getDouble("middleheightores" + i));


        this.analysis = nbttagcompound.getBoolean("analysis");
        this.quarry = nbttagcompound.getBoolean("quarry");

        this.xcoord = nbttagcompound.getInteger("xcoord");
        this.xendcoord = nbttagcompound.getInteger("xendcoord");
        this.zcoord = nbttagcompound.getInteger("zcoord");
        this.zendcoord = nbttagcompound.getInteger("zendcoord");

        this.xTempChunk = nbttagcompound.getInteger("xTempChunk");
        this.zTempChunk = nbttagcompound.getInteger("zTempChunk");
        this.chunksx = new int[this.xendcoord][this.zendcoord];
        this.chunksz = new int[this.xendcoord][this.zendcoord];
        for (int i = 0; i < this.xendcoord; i++)
            for (int j = 0; j < this.zendcoord; j++) {
                this.chunksx[i][j] = nbttagcompound.getInteger("chunksx" + i + j);
                this.chunksz[i][j] = nbttagcompound.getInteger("chunksz" + i + j);
            }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);


        for (int i = 0; i < this.xendcoord; i++)
            for (int j = 0; j < this.zendcoord; j++) {
                nbttagcompound.setInteger(("chunksx" + i + j), this.chunksx[i][j]);
                nbttagcompound.setInteger(("chunksz" + i + j), this.chunksz[i][j]);
            }


        nbttagcompound.setInteger("size4", this.yore.size());
        nbttagcompound.setBoolean("start", this.start);
        nbttagcompound.setInteger("xcoord", this.xcoord);
        nbttagcompound.setInteger("xendcoord", this.xendcoord);
        nbttagcompound.setInteger("zcoord", this.zcoord);
        nbttagcompound.setInteger("zendcoord", this.zendcoord);

        nbttagcompound.setInteger("xTempChunk", this.xTempChunk);
        nbttagcompound.setInteger("zTempChunk", this.zTempChunk);


        nbttagcompound.setInteger("xChunk", this.xChunk);
        nbttagcompound.setInteger("zChunk", this.zChunk);
        nbttagcompound.setInteger("xendChunk", this.xendChunk);
        nbttagcompound.setInteger("zendChunk", this.zendChunk);
        nbttagcompound.setDouble("sum", this.sum);
        nbttagcompound.setInteger("sum1", this.sum1);
        nbttagcompound.setInteger("breakblock", this.breakblock);
        nbttagcompound.setInteger("numberores", this.numberores);
        nbttagcompound.setInteger("size", this.listore.size());
        nbttagcompound.setInteger("size1", this.listnumberore.size());
        nbttagcompound.setInteger("size2", this.y1.size());
        nbttagcompound.setInteger("size3", this.middleheightores.size());
        nbttagcompound.setBoolean("analysis", this.analysis);
        nbttagcompound.setBoolean("quarry", this.quarry);

        for (int i = 0; i < this.yore.size(); i++)
            nbttagcompound.setInteger(("yore" + i), this.yore.get(i));
        for (int i = 0; i < this.listore.size(); i++)
            nbttagcompound.setString(("ore" + i), this.listore.get(i));
        for (int i = 0; i < this.listnumberore.size(); i++)
            nbttagcompound.setInteger(("number" + i), this.listnumberore.get(i));
        for (int i = 0; i < this.middleheightores.size(); i++)
            nbttagcompound.setDouble(("middleheightores" + i), this.middleheightores.get(i));
        for (int i = 0; i < this.y1.size(); i++)
            nbttagcompound.setInteger(("y" + i), this.y1.get(i));
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIAnalyzer(new ContainerAnalyzer(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityAnalyzer> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityAnalyzer>) new ContainerAnalyzer(entityPlayer, this);
    }

    public void onNetworkEvent(EntityPlayer player, int event) {

        if (event == 1 && this.inputslot.quarry() && !this.analysis)
            this.quarry = !this.quarry;
        if (event == 0 && this.y >= 256 && !this.quarry)
            this.analysis = !this.analysis;
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public String getStartSoundFile() {
        return "Machines/analyzer.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
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

    public void onGuiClosed(EntityPlayer arg0) {
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAnaluzer.name");
    }
}
