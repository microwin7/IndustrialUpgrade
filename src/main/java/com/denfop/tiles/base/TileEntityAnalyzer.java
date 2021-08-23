package com.denfop.tiles.base;

import com.denfop.IUCore;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerAnalyzer;
import com.denfop.gui.GUIAnalyzer;
import com.denfop.invslot.InvSlotAnalyzer;
import com.denfop.invslot.InvSlotAnalyzer1;
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

public class TileEntityAnalyzer extends TileEntityElectricMachine implements IHasGui,  INetworkUpdateListener, INetworkDataProvider, INetworkClientTileEntityEventListener , INetworkTileEntityEventListener {
    public final InvSlotAnalyzer inputslot;
    public final InvSlotAnalyzer1 inputslotA;
    private boolean quarry;

    public int breakblock;

    public int numberores;

    public double sum;

    public int sum1;

    public boolean analysis;


    List<Integer> y1;


    public int xChunk;

    public int zChunk;

    public int xendChunk;

    public int zendChunk;
    public int[] listnumberore1;
    public List<String> listore;

    public List<Integer> listnumberore;
    public List<Integer> yore;
    public List<Double> middleheightores;
    public int middleheight;
    private int y;

    public TileEntityAnalyzer() {
        super(100000, 14, 1);
        this.listore = new ArrayList<>();
        this.middleheight = 0;
        this.listnumberore = new ArrayList<>();
        this.yore=new ArrayList<>();
        this.middleheightores=new ArrayList<>();
        this.analysis = false;
        this.sum = 0;
        this.sum1 = 0;
        this.numberores = 0;
        this.breakblock = 0;
        this.quarry = false;
        this.inputslot = new InvSlotAnalyzer(this, 3);
        this.inputslotA = new InvSlotAnalyzer1(this, 2);
        this.y1 = new ArrayList<>();
        this.y = 257;
    }
    public double getProgress(){

        double temp = xChunk-xendChunk;
        double temp1 = zChunk-zendChunk;
        if(temp < 0){
            temp*=-1;
        }
        if(temp1 < 0){
            temp1*=-1;
        }
        return Math.min (((temp*temp1*this.y)/(temp*temp1*256)),1);
    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        int chunkx = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkXPos * 16;
        int chunkz = (this.worldObj.getChunkFromBlockCoords(this.xCoord, this.zCoord).getChunkCoordIntPair()).chunkZPos * 16;
        int size = this.inputslot.getChunksize();
        this.xChunk = chunkx - 16 * size;
        this.zChunk = chunkz - 16 * size;
        this.xendChunk = chunkx + 16 + 16* size;
        this.zendChunk = chunkz + 16 + 16* size;
        if (this.analysis) {
            if(this.y >= 257) {
                this.y = 0;
                this.breakblock = 0;
                this.numberores = 0;
                this.sum = 0;
                this.sum1 = 0;
                setActive(true);
                this.yore=new ArrayList<>();
                this.listore = new ArrayList<>();
                this.listnumberore =  new ArrayList<>();
                this.y1 =  new ArrayList<>();
                this.middleheightores= new ArrayList<>();
            }
            List<String> blacklist = this.inputslot.getblacklist();
            List<String> whitelist = this.inputslot.getwhitelist();
            if(this.worldObj.provider.getWorldTime() % 3 == 0)
            for (int x = xChunk; x < xendChunk; x++) {
                for (int z = zChunk; z < zendChunk; z++) {
                    if(this.energy < 1)
                        break;
                    this.energy-=1;
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
                                          if(!this.inputslot.CheckBlackList(blacklist,name) && this.inputslot.CheckWhiteList(whitelist,name)) {

                                              if (listore.isEmpty()) {
                                                  listore.add(name);
                                                  listnumberore.add(1);
                                                  yore.add(y);
                                                  this.y1.add(this.y);
                                                  this.numberores = listore.size();
                                                  listnumberore1 = new int[listnumberore.size()];
                                                  for (int i = 0; i < listnumberore.size(); i++)
                                                      listnumberore1[i] = listnumberore.get(i);

                                                  this.sum = ModUtils.getsum1(listnumberore);
                                                  this.middleheight = (ModUtils.getsum1(this.y1) / ModUtils.getsum1(listnumberore));
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

                                                  this.sum = ModUtils.getsum1(listnumberore);
                                                  this.middleheight = (ModUtils.getsum1(this.y1) / ModUtils.getsum1(listnumberore));
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

                                                  this.sum = ModUtils.getsum1(listnumberore);
                                                  this.middleheight = (ModUtils.getsum1(this.y1) / ModUtils.getsum1(listnumberore));
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
            if(this.worldObj.provider.getWorldTime() % 120 == 0)
            initiate(2);
            if(this.worldObj.provider.getWorldTime() % 3 == 0) {
                this.y++;
                if (this.y >= 257) {
                    this.analysis = false;
                    this.setActive(false);
                    this.middleheightores = new ArrayList<>();
                    for (int i = 0; i < this.listore.size(); i++)
                        this.middleheightores.add((this.yore.get(i) / (double) this.listnumberore.get(i)));
                    initiate(2);
                }
            }
         }
        if (this.quarry) {
            if(this.y >= 257) {
                this.y = 0;
                setActive(true);
            }
            if(this.inputslot.getwirelessmodule()) {
                List list6 = this.inputslot.wirelessmodule();
                int xx = (int) list6.get(0);
                int yy = (int) list6.get(1);
                int zz = (int) list6.get(2);
                if(this.worldObj.getTileEntity(xx,yy,zz) != null&&this.worldObj.getTileEntity(xx,yy,zz) instanceof TileEntityBaseQuantumQuarry  ) {
                    TileEntityBaseQuantumQuarry target1 = (TileEntityBaseQuantumQuarry)this.worldObj.getTileEntity(xx,yy,zz);
                       quarry(target1);
                    }

            }else{
                for (Direction direction : Direction.directions) {
                    TileEntity target = direction.applyToTileEntity(this);
                    if (target instanceof TileEntityBaseQuantumQuarry) {
                        TileEntityBaseQuantumQuarry target1 = (TileEntityBaseQuantumQuarry)target;
                        quarry(target1);
                    }
                }
            }
            if(this.worldObj.provider.getWorldTime() % 3 == 0) {
                this.y++;
                if (this.y >= 257) {
                    this.quarry = false;
                    this.analysis = true;

                }
            }

        }

    }
    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }
    public void quarry(TileEntityBaseQuantumQuarry target1){
        List<String> blacklist = this.inputslot.getblacklist();
        List<String> whitelist = this.inputslot.getwhitelist();
        if(this.worldObj.provider.getWorldTime() % 3 == 0)
            for (int x = xChunk; x < xendChunk; x++) {
                for (int z = zChunk; z < zendChunk; z++) {
                    if (!this.worldObj.isAirBlock(x, y, z))
                        if (this.worldObj.getBlock(x, y, z) != null) {


                            if (this.worldObj.getBlock(x, y, z).getMaterial() == Material.iron || this.worldObj.getBlock(x, y, z).getMaterial() == Material.rock) {
                                Block block = this.worldObj.getBlock(x, y, z);
                                ItemStack stack = new ItemStack(block, 1, this.worldObj.getBlockMetadata(x, y, z));
                                int id = OreDictionary.getOreID(stack);
                                String name = OreDictionary.getOreName(id);
                                if (name.startsWith("ore")) {
                                    if(!(!this.inputslot.CheckBlackList(blacklist,name) && this.inputslot.CheckWhiteList(whitelist,name)))
                                        continue;
                                    double energycost = this.inputslot.getenergycost();
                                    String temp = name.substring(3);

                                    if(temp.startsWith("Infused"))
                                        temp = name.substring("Infused".length()+3);

                                    if (!name.equals("oreRedstone") && (OreDictionary.getOres("gem" + temp) == null || OreDictionary.getOres("gem" + temp).size() < 1) && (OreDictionary.getOres("shard" + temp) == null || OreDictionary.getOres("shard" + temp).size() < 1) ) {

                                        boolean furnace = this.inputslot.getFurnaceModule();
                                        if(!furnace) {
                                            if (!TileEntityBaseQuantumQuarry.list(target1,stack))
                                                if (target1.energy >= energycost &&
                                                        target1.outputSlot.canAdd(stack)) {
                                                    target1.outputSlot.add(stack);
                                                    this.worldObj.setBlockToAir(x, y, z);
                                                    target1.energy -= energycost;
                                                    target1.getblock++;
                                                }
                                        }else {
                                            temp = name.substring(3);
                                            temp = "ingot"+temp;
                                            if(OreDictionary.getOres(temp).isEmpty()){
                                                if (!TileEntityBaseQuantumQuarry.list(target1,stack))
                                                    if (target1.energy >= energycost &&
                                                            target1.outputSlot.canAdd(stack)) {
                                                        target1.outputSlot.add(stack);
                                                        this.worldObj.setBlockToAir(x, y, z);
                                                        target1.energy -= energycost;
                                                        target1.getblock++;
                                                    }
                                            }else{

                                                ItemStack stack1 = OreDictionary.getOres(temp).get(0);
                                                if (!TileEntityBaseQuantumQuarry.list(target1,stack))

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

                                        if(OreDictionary.getOres("gem" + temp).size()  != 0 )
                                            gem = OreDictionary.getOres("gem" + temp).get(0);
                                        else if(OreDictionary.getOres("shard" + temp).size() != 0)
                                            gem = OreDictionary.getOres("shard" + temp).get(0);
                                        else if(OreDictionary.getOres("dust" + temp).size() != 0)
                                            gem = OreDictionary.getOres("dust" + temp).get(0);
                                        int chance2 =  this.inputslot.lucky();


                                        List<Boolean> get = new ArrayList<>();
                                        if (!TileEntityBaseQuantumQuarry.list(target1,stack))
                                            if (target1.energy >=  energycost)
                                                for (int j = 0; j < chance2 + 1; j++) {
                                                    if (target1.outputSlot.canAdd(gem)) {
                                                        target1.outputSlot.add(gem);
                                                        get.add(true);
                                                    }else{
                                                        get.add(false);
                                                    }
                                                }
                                        if(ModUtils.Boolean(get)){
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

        this.xChunk=nbttagcompound.getInteger("xChunk");
        this.zChunk=nbttagcompound.getInteger("zChunk");
        this.xendChunk=nbttagcompound.getInteger("xendChunk");
        this.zendChunk=nbttagcompound.getInteger("zendChunk");
        this.sum=nbttagcompound.getDouble("sum");
        this.sum1=nbttagcompound.getInteger("sum1");
        this.breakblock=nbttagcompound.getInteger("breakblock");
        this.numberores=nbttagcompound.getInteger("numberores");
        int size = nbttagcompound.getInteger("size");
        int size1 = nbttagcompound.getInteger("size1");
        int size2 = nbttagcompound.getInteger("size2");
        int size3 = nbttagcompound.getInteger("size3");
        for(int i = 0; i < size;i++)
            this.listore.add(nbttagcompound.getString("ore"+i));
        for(int i = 0; i < size1;i++)
            this.listnumberore.add(nbttagcompound.getInteger("number"+i));
        for(int i = 0; i < size2;i++)
            this.y1.add(nbttagcompound.getInteger("y"+i));
        for(int i = 0; i < size3;i++)
            this.middleheightores.add(nbttagcompound.getDouble("middleheightores"+i));
        this.analysis=nbttagcompound.getBoolean("analysis");
                this.quarry=nbttagcompound.getBoolean("quarry");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setInteger("xChunk",this.xChunk);
        nbttagcompound.setInteger("xChunk",this.zChunk);
        nbttagcompound.setInteger("xChunk",this.xendChunk);
        nbttagcompound.setInteger("xChunk",this.zendChunk);
        nbttagcompound.setDouble("sum",this.sum);
        nbttagcompound.setInteger("sum1",this.sum1);
        nbttagcompound.setInteger("breakblock",this.breakblock);
        nbttagcompound.setInteger("numberores",this.numberores);
        nbttagcompound.setInteger("size",this.listore.size());
        nbttagcompound.setInteger("size1",this.listnumberore.size());
        nbttagcompound.setInteger("size2",this.y1.size());
        nbttagcompound.setInteger("size3",this.middleheightores.size());
        nbttagcompound.setBoolean("analysis",this.analysis);
        nbttagcompound.setBoolean("quarry",this.quarry);
        for(int i =0; i < this.listore.size(); i++)
            nbttagcompound.setString(("ore"+i),this.listore.get(i));
        for(int i =0; i < this.listnumberore.size(); i++)
            nbttagcompound.setInteger(("number"+i),this.listnumberore.get(i));
        for(int i =0; i < this.middleheightores.size(); i++)
            nbttagcompound.setDouble(("middleheightores"+i),this.middleheightores.get(i));
        for(int i =0; i < this.y1.size(); i++)
            nbttagcompound.setInteger(("y"+i),this.y1.get(i));
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

        if(event == 1 && this.inputslot.quarry() && !this.analysis )
        this.quarry = !this.quarry;
        if(event == 0 && this.y >= 256 && !this.quarry)
            this.analysis = !this.analysis;
    }



   public float getWrenchDropRate() {
        return 0.85F;
    }

    public String getStartSoundFile() {
        return "Machines/analyzer.ogg";
    }
    public AudioSource audioSource;
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
            public void onGuiClosed(EntityPlayer arg0) {}

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockAnaluzer.name");
    }
}
