package com.denfop.tiles.mechanism;

import com.denfop.container.ContainerElectricLather;
import com.denfop.gui.GUIElectricLather;
import com.denfop.invslot.InvSlotLatheUpgrade;
import com.denfop.tiles.base.TileEntityElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ILatheItem;
import ic2.api.item.ILatheItem.ILatheTool;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlotConsumableClass;
import ic2.core.block.invslot.InvSlotOutput;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class TileEntityElectricLather extends TileEntityElectricMachine implements IHasGui, INetworkClientTileEntityEventListener {
    public final InvSlotConsumableClass toolSlot = new InvSlotConsumableClass(this, "slotTool", 0, 1, ILatheTool.class);
    public final InvSlotConsumableClass latheSlot = new InvSlotConsumableClass(this, "lathe", 1, 1, ILatheItem.class);
    public final InvSlotOutput outputSlot = new InvSlotOutput(this, "dusts", 2, 1);
    public final InvSlotLatheUpgrade inputslot;


    public TileEntityElectricLather() {
        super(1000, 14, -1);
        this.inputslot = new InvSlotLatheUpgrade(this, 5);
    }

    public void updateEntityServer() {
        super.updateEntityServer();
        this.setActive(this.energy > 25);
        if (!inputslot.isEmpty()) {
            int[] state = getCurrentState(inputslot.get());
            if (this.worldObj.provider.getWorldTime() % 20 == 0)
                process(state);
        }
    }

    public int[] getCurrentState(ItemStack stack) {
        if (stack == null) {
            return new int[0];
        } else {
            int[] ret = new int[5];
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound();

                for (int i = 0; i < ret.length; ++i) {
                    if (tag.hasKey("l" + i)) {
                        ret[i] = tag.getInteger("l" + i);
                    } else {
                        ret[i] = 5;
                    }
                }
            } else {
                Arrays.fill(ret, 5);
            }

            return ret;
        }
    }


    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
    }

    public ContainerBase<TileEntityElectricLather> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerElectricLather(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIElectricLather((ContainerElectricLather) this.getGuiContainer(entityPlayer));
    }

    public void onGuiClosed(EntityPlayer entityPlayer) {
    }

    public String getInventoryName() {
        return "Lathe";
    }

    public void process(int[] position1) {
        if (!this.canWork(false)) {
        } else {
            for (int position = 0; position < position1.length; position++) {
                ILatheItem l = (ILatheItem) this.latheSlot.get().getItem();
                ILatheTool t = (ILatheTool) this.toolSlot.get().getItem();
                if (!this.outputSlot.canAdd(l.getOutputItem(this.latheSlot.get(), position))) {
                    return;
                } else {
                    int[] currentState = l.getCurrentState(this.latheSlot.get());
                    if (currentState[position] <= 1) {
                        return;
                    } else {
                        if (currentState[position] > position1[position]) {
                            l.setState(this.latheSlot.get(), position, currentState[position] - 1);
                            if (this.worldObj.rand.nextFloat() < l.getOutputChance(this.latheSlot.get(), position)) {
                                this.outputSlot.add(l.getOutputItem(this.latheSlot.get(), position));
                            }

                            t.setCustomDamage(this.toolSlot.get(), t.getCustomDamage(this.toolSlot.get()) + 1);
                            if (t.getCustomDamage(this.toolSlot.get()) >= t.getMaxCustomDamage(this.toolSlot.get())) {
                                this.toolSlot.put(null);
                            }

                            this.energy -= 25;
                            break;
                        }
                    }
                }


            }
        }
    }

    public boolean canWork(boolean power) {
        if (this.toolSlot.get() != null && this.toolSlot.get().getItem() instanceof ILatheTool) {
            if (this.latheSlot.get() != null && this.latheSlot.get().getItem() instanceof ILatheItem) {
                if (this.energy < 25 && !power) {
                    return false;
                } else {
                    ILatheItem l = (ILatheItem) this.latheSlot.get().getItem();
                    ILatheTool t = (ILatheTool) this.toolSlot.get().getItem();
                    return t.getHardness(this.toolSlot.get()) > l.getHardness(this.latheSlot.get());
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void onNetworkEvent(EntityPlayer player, int event) {

    }

    public boolean wrenchCanSetFacing(EntityPlayer entityPlayer, int side) {
        return side != this.getFacing();
    }
}
