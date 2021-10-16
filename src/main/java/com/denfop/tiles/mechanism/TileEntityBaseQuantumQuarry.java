package com.denfop.tiles.mechanism;

import com.denfop.Config;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.audio.AudioSource;
import com.denfop.container.ContainerQuantumQuarry;
import com.denfop.gui.GUIQuantumQuarry;
import com.denfop.invslot.InvSlotQuantumQuarry;
import com.denfop.item.modules.EnumQuarryModules;
import com.denfop.item.modules.EnumQuarryType;
import com.denfop.tiles.base.TileEntityElectricMachine;
import com.denfop.tiles.base.TileEntityVein;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotOutput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Random;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class TileEntityBaseQuantumQuarry extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener {

    private static boolean analyzer;
    private final String name;
    private int progress;

    public AudioSource audioSource;

    public static final Random rand = new Random();

    public final int energyconsume;

    public double getblock;
    public final InvSlotQuantumQuarry inputslotB;
    public final InvSlotOutput outputSlot;
    public final InvSlotQuantumQuarry inputslot;

    public final InvSlotQuantumQuarry inputslotA;

    public TileEntityBaseQuantumQuarry(String name, int coef) {
        super(5E7D, 14, 1);
        this.progress = 0;
        this.name = name;
        this.getblock = 0;
        this.energyconsume = Config.enerycost * coef;

        this.outputSlot = new InvSlotOutput(this, "output", 2, 24);
        this.inputslot = new InvSlotQuantumQuarry(this, 3,"input",0);
        this.inputslotA = new InvSlotQuantumQuarry(this, 4,"input1",1);
        this.inputslotB = new InvSlotQuantumQuarry(this, 5,"input2",2);
        analyzer = false;
    }


    @Override
    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        ItemStack ret = super.getWrenchDrop(entityPlayer);
        if (this.inputslot.get() != null) {
            double var8 = 0.7D;
            double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
            EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                    this.inputslot.get());
            var16.delayBeforeCanPickup = 10;
            worldObj.spawnEntityInWorld(var16);
        }
        for (int i = 0; i < 24; i++)
            if (this.outputSlot.get(i) != null) {
                double var8 = 0.7D;
                double var10 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var12 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                double var14 = (double) this.worldObj.rand.nextFloat() * var8 + (1.0D - var8) * 0.5D;
                EntityItem var16 = new EntityItem(this.worldObj, (double) this.xCoord + var10, (double) this.yCoord + var12, (double) this.zCoord + var14,
                        this.outputSlot.get(i));
                var16.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(var16);
            }
        return ret;
    }

    public void updateEntityServer() {

        super.updateEntityServer();

        double proccent = this.energyconsume;
        if (worldObj.provider.getWorldTime() % 20 == 0)
            analyzer = !this.inputslotB.isEmpty();
        boolean vein = false;
        if(analyzer && !this.inputslotB.isEmpty()){
            NBTTagCompound nbt = ModUtils.nbt(this.inputslotB.get());
            vein = nbt.getBoolean("vein");
        }
        if(analyzer && vein){
            NBTTagCompound nbt = ModUtils.nbt(this.inputslotB.get());
            int x = nbt.getInteger("x");
            int y = nbt.getInteger("y");
            int z = nbt.getInteger("z");
            if(this.worldObj.getTileEntity(x,y,z) != null && this.worldObj.getTileEntity(x,y,z) instanceof TileEntityVein){
                TileEntityVein  tile = (TileEntityVein) this.worldObj.getTileEntity(x,y,z);
                 if(tile.number > 0){
                     if (this.inputslot.get() != null) {
                         EnumQuarryModules module = IUItem.quarry_modules.get(this.inputslot.get().getItemDamage());
                         EnumQuarryType type = module.type;

                         if (type == EnumQuarryType.SPEED) {
                             proccent = this.energyconsume - this.energyconsume * 0.05 * module.efficiency;
                         }

                     }
                     if (this.energy >= proccent && this.outputSlot.canAdd(new ItemStack(IUItem.heavyore,1,this.worldObj.getBlockMetadata(x,y,z)))) {
                         this.setActive(true);
                         this.energy -= proccent;
                         this.getblock++;
                         this.outputSlot.add(new ItemStack(IUItem.heavyore,1,this.worldObj.getBlockMetadata(x,y,z)));
                         tile.number--;
                     }

                 }
            }



        }
        if (analyzer && !vein) {
            double col = 1;
            int chance2 = 0;
            boolean furnace = false;
            if (this.inputslot.get() != null) {
                EnumQuarryModules module = IUItem.quarry_modules.get(this.inputslot.get().getItemDamage());
                EnumQuarryType type = module.type;

                switch (type) {
                    case SPEED:
                        proccent = this.energyconsume - this.energyconsume * 0.05 * module.efficiency;
                        break;
                    case DEPTH:
                        col = module.efficiency * module.efficiency;
                        proccent = this.energyconsume * Math.pow(1.1, module.meta - 8);
                        break;
                    case LUCKY:
                        chance2 = module.efficiency;
                        break;
                    case FURNACE:
                        furnace = true;
                        break;

                }
            }
            EnumQuarryModules list_check = null;
            if (this.inputslotA.get() != null) {
                EnumQuarryModules module = IUItem.quarry_modules.get(this.inputslotA.get().getItemDamage());
                EnumQuarryType type = module.type;

                switch (type) {
                    case WHITELIST:
                    case BLACKLIST:
                        list_check = module;
                        break;

                }
            }
            for (double i = 0; i < col; i++)
                if (this.energy >= proccent) {
                    this.setActive(true);
                    this.energy -= proccent;
                    int chance = rand.nextInt(100) + 1;
                    this.getblock++;
                    initiate(0);
                    if (chance > 95) {
                        if (checkinventoy())
                            return;
                        if (furnace) {
                            List<ItemStack> list = IUCore.get_ingot;
                            int num = list.size();
                            int chance1 = rand.nextInt(num);
                            if (!list(list_check, list.get(chance1)))
                                if (this.outputSlot.canAdd(list.get(chance1)))
                                    this.outputSlot.add(list.get(chance1));
                        } else {
                            List<ItemStack> list = IUCore.list;
                            int num = list.size();
                            int chance1 = rand.nextInt(num);
                            if (!list(list_check, list.get(chance1)))
                                if ((!OreDictionary.getOreName(OreDictionary.getOreID(list.get(chance1))).startsWith("gem") && !OreDictionary.getOreName(OreDictionary.getOreID(list.get(chance1))).startsWith("shard")
                                        && list.get(chance1).getItem() != Items.redstone && list.get(chance1).getItem() != Items.dye && list.get(chance1).getItem() != Items.coal && list.get(chance1).getItem() != Items.glowstone_dust) && chance2 >= 0) {
                                    if (this.outputSlot.canAdd(list.get(chance1)))
                                        this.outputSlot.add(list.get(chance1));
                                } else {
                                    for (int j = 0; j < chance2 + 1; j++)
                                        if (this.outputSlot.canAdd(list.get(chance1)))
                                            this.outputSlot.add(list.get(chance1));
                                }
                        }
                    }
                } else {
                    this.setActive(false);
                    initiate(2);
                }
        } else {
            initiate(2);
            this.setActive(false);
        }
        if (this.worldObj.provider.getWorldTime() % 200 == 0)
            initiate(2);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean list(EnumQuarryModules type, ItemStack stack1) {
        if (type == null)
            return false;
        String stack = OreDictionary.getOreName(OreDictionary.getOreID(stack1));
        if (type.type == EnumQuarryType.BLACKLIST) {
            for (int j = 0; j < 9; j++) {
                String l = "number_" + j;
                String temp = ModUtils.NBTGetString(this.inputslotA.get(), l);
                if (temp.startsWith("ore") || temp.startsWith("gem") || temp.startsWith("dust") || temp.startsWith("shard")) {
                    if (temp.equals(stack))
                        return true;

                }
            }

            return false;


        } else if (type.type == EnumQuarryType.WHITELIST) {
            for (int j = 0; j < 9; j++) {
                String l = "number_" + j;
                String temp = ModUtils.NBTGetString(this.inputslotA.get(), l);
                if (temp.startsWith("ore") || temp.startsWith("gem") || temp.startsWith("dust") || temp.startsWith("shard")) {

                    if (temp.equals(stack))
                        return false;

                }

            }
            return true;
        }
        return false;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean list(TileEntityBaseQuantumQuarry tile, ItemStack stack1) {
        if (tile.inputslotA.isEmpty())
            return false;
        EnumQuarryModules module = IUItem.quarry_modules.get(tile.inputslotA.get().getItemDamage());
        EnumQuarryType type = module.type;
        String stack = OreDictionary.getOreName(OreDictionary.getOreID(stack1));
        if (type == EnumQuarryType.BLACKLIST) {
            for (int j = 0; j < 9; j++) {
                String l = "number_" + j;
                String temp = ModUtils.NBTGetString(tile.inputslotA.get(), l);
                if (temp.startsWith("ore") || temp.startsWith("gem") || temp.startsWith("dust") || temp.startsWith("shard")) {
                    if (temp.equals(stack))
                        return true;

                }
            }

            return false;


        } else if (type == EnumQuarryType.WHITELIST) {
            for (int j = 0; j < 9; j++) {
                String l = "number_" + j;
                String temp = ModUtils.NBTGetString(tile.inputslotA.get(), l);
                if (temp.startsWith("ore") || temp.startsWith("gem") || temp.startsWith("dust") || temp.startsWith("shard")) {

                    if (temp.equals(stack))
                        return false;

                }

            }
            return true;
        }
        return false;
    }

    private boolean checkinventoy() {
        for (int i = 0; i < this.outputSlot.size(); i++) {
            if (this.outputSlot.get(i) == null)
                return false;
            if (this.outputSlot.get(i).stackSize != 64) {

                return false;
            }
        }
        return true;
    }

    public double injectEnergy(ForgeDirection directionFrom, double amount, double voltage) {
        if (amount == 0D)
            return 0;
        if (this.energy >= this.maxEnergy)
            return amount;
        if (this.energy + amount >= this.maxEnergy) {
            double p = this.maxEnergy - this.energy;
            this.energy += (p);
            return amount - (p);
        } else {
            this.energy += amount;
        }
        return 0.0D;
    }

    public double getDemandedEnergy() {

        return this.maxEnergy - this.energy;

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getInteger("progress");
        this.getblock = nbttagcompound.getDouble("getblock");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setDouble("getblock", this.getblock);
        nbttagcompound.setInteger("progress", this.progress);

    }

    public int getSizeInventory() {
        return 24;
    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIQuantumQuarry(new ContainerQuantumQuarry(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityBaseQuantumQuarry> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityBaseQuantumQuarry>) new ContainerQuantumQuarry(entityPlayer, this);
    }


    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }
    }

    public String getStartSoundFile() {
        return "Machines/quarry.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    private void initiate(int soundEvent) {
        IC2.network.get().initiateTileEntityEvent(this, soundEvent, true);
    }

    @Override
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

    @Override
    public void onGuiClosed(EntityPlayer arg0) {
    }

    @Override
    public String getInventoryName() {

        return StatCollector.translateToLocal(name);
    }

}
