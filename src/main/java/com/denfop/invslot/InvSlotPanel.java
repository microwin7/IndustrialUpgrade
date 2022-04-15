package com.denfop.invslot;

import cofh.api.energy.IEnergyContainerItem;
import com.denfop.IUItem;
import com.denfop.api.module.IModulPanel;
import com.denfop.item.modules.*;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import com.denfop.utils.ModUtils;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class InvSlotPanel extends InvSlot {

    private int stackSizeLimit;

    public InvSlotPanel(TileEntitySolarPanel base1, int oldStartIndex1) {
        super(base1, "input2", oldStartIndex1, InvSlot.Access.IO, 9, InvSlot.InvSide.TOP);
        this.stackSizeLimit = 1;
    }

    @Override
    public void put(final int index, final ItemStack content) {
        super.put(index, content);
        this.getrfmodule();
        this.personality();
        this.checkmodule();
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        tile.solarType = this.solartype();
    }

    public boolean accepts(ItemStack itemStack) {
        return itemStack.getItem() instanceof ModuleBase
                || itemStack.getItem() instanceof ItemWirelessModule
                || itemStack.getItem() instanceof ModuleType
                || itemStack.getItem() instanceof ModuleTypePanel

                || (itemStack.getItem() instanceof AdditionModule && (itemStack.getItemDamage() == 9 || itemStack.getItemDamage() < 5))
                || (itemStack.getItem() instanceof IElectricItem)
                || (itemStack.getItem() instanceof IEnergyContainerItem);
    }

    public void checkmodule() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;

        double temp_day = tile.k;
        double temp_night = tile.m;
        double temp_storage = tile.p;
        double temp_producing = tile.u;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null && this.get(i).getItem() instanceof IModulPanel) {
                int g = this.get(i).getItemDamage();

                if (tile.machineTire >= g + 1) {
                    EnumSolarPanels solar = ModuleTypePanel.getSolarType(g);
                    temp_day += solar.genday;
                    temp_night += solar.gennight;
                    temp_storage += solar.maxstorage;
                    temp_producing += solar.producing;

                }

            }
        }


        tile.machineTire = (int) tile.o;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null && IUItem.modules.get(this.get(i).getItem()) != null) {
                EnumModule module = IUItem.modules.get(this.get(i).getItem());
                EnumType type = module.type;
                double percent = module.percent;
                switch (type) {
                    case DAY:
                        temp_day += tile.k * percent;
                        break;
                    case NIGHT:
                        temp_night += tile.m * percent;
                        break;
                    case STORAGE:
                        temp_storage += tile.p * percent;
                        break;
                    case OUTPUT:
                        temp_producing += tile.u * percent;
                        break;
                }
            }


            if (this.get(i) != null && this.get(i).getItem() instanceof AdditionModule) {
                int kk = get(i).getItemDamage();
                if (kk == 1) {
                    tile.machineTire++;
                } else if (kk == 2) {
                    tile.machineTire--;
                } else if (kk == 3) {
                    tile.charge = true;

                }
            }


        }

        tile.genDay = temp_day;
        tile.genNight = temp_night;
        tile.maxStorage = temp_storage;
        tile.production = temp_producing;


    }

    public void getrfmodule() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) != null && this.get(i).getItemDamage() == 4 && this.get(i).getItem() instanceof AdditionModule) {
                tile.getmodulerf = true;
                return;
            }
        tile.getmodulerf = false;
    }

    public void time() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) != null && this.get(i).getItemDamage() == 9 && this.get(i).getItem() instanceof AdditionModule) {
                tile.time = 14400 * 2;
                tile.time1 = 14400;
                tile.time2 = 14400;
                tile.work = true;
                tile.work1 = true;
                tile.work2 = true;

                return;
            }

        if (tile.time > 0) {
            tile.time--;
        }
        if (tile.time <= 0) {
            tile.work = false;
        }
        if (tile.time1 > 0 && !tile.work) {
            tile.time1--;
        }
        if (tile.time1 <= 0) {
            tile.work1 = false;
        }
        if (tile.time2 > 0 && !tile.work && !tile.work1) {
            tile.time2--;
        }
        if (tile.time2 <= 0) {
            tile.work2 = false;
        }

    }

    public int getStackSizeLimit() {
        return this.stackSizeLimit;
    }

    public void setStackSizeLimit(int stackSizeLimit) {
        this.stackSizeLimit = stackSizeLimit;
    }


    public void charge() {
        double sentPacket;
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        for (int j = 0; j < this.size(); j++) {

            if (this.get(j) != null && this.get(j).getItem() instanceof ic2.api.item.IElectricItem
                    && tile.storage > 0.0D) {
                sentPacket = ElectricItem.manager.charge(this.get(j), tile.storage, 2147483647, true,
                        false);
                if (sentPacket > 0.0D)
                    tile.storage -= (int) sentPacket;
            }
        }
    }

    public void rfcharge() {

        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        for (int jj = 0; jj < this.size(); jj++) {
            if (this.get(jj) != null && this.get(jj).getItem() instanceof IEnergyContainerItem
                    && tile.storage2 > 0.0D) {
                IEnergyContainerItem item = (IEnergyContainerItem) this.get(jj).getItem();
                double sent = 0;
                double energy_temp = tile.storage2;
                if (item.getEnergyStored(this.get(jj)) < item.getMaxEnergyStored(this.get(jj))
                        && tile.storage2 > 0) {
                    sent = (sent + tile.extractEnergy1(
                            item.receiveEnergy(this.get(jj), (int) Math.min(tile.storage2, 2147000000), false), false));

                }
                energy_temp -= (sent * 2);
                tile.storage2 = energy_temp;

            }

        }
    }

    public void personality() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        for (int m = 0; m < this.size(); m++) {
            if (this.get(m) != null && this.get(m).getItem() instanceof AdditionModule) {
                int kk = get(m).getItemDamage();
                if (kk == 0) {
                    tile.personality = true;
                    break;
                } else {
                    tile.personality = false;
                }
            } else {
                tile.personality = false;
            }
        }
    }

    public int solartype() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;

        List<Integer> list1 = new ArrayList<>();
        for (int i = 0; i < this.size(); i++)
            if (this.get(i) != null && this.get(i).getItem() instanceof ModuleType)
                list1.add(get(i).getItemDamage() + 1);
            else
                list1.add(0);
        com.denfop.tiles.overtimepanel.EnumType type = IUItem.type.get(ModUtils.slot(list1));

        return tile.setSolarType(type);
    }

    public void wirelessmodule() {
        TileEntitySolarPanel tile = (TileEntitySolarPanel) base;
        tile.wireless = 0;
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i) != null && this.get(i).getItem() instanceof ItemWirelessModule) {

                tile.wireless = 1;
                int x;
                int y;
                int z;
                NBTTagCompound nbttagcompound = ModUtils.nbt(this.get(i));

                x = nbttagcompound.getInteger("Xcoord");
                y = nbttagcompound.getInteger("Ycoord");
                z = nbttagcompound.getInteger("Zcoord");

                if (tile.getWorldObj().getTileEntity(x, y, z) != null
                        && tile.getWorldObj().getTileEntity(x, y, z) instanceof IEnergySink && x != 0
                        && y != 0 && z != 0) {
                    IEnergySink tile2 = (IEnergySink) tile.getWorldObj().getTileEntity(x, y, z);
                    double energy = Math.min(tile2.getDemandedEnergy(), tile.storage);
                    energy -= tile2.injectEnergy(null, energy, 0);
                    tile.storage -= energy;
                }
            }


        }

    }
}
