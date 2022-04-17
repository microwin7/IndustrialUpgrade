package com.denfop.tiles.mechanism;

import com.denfop.Config;
import com.denfop.Ic2Items;
import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.EnumMultiMachine;
import com.denfop.tiles.base.TileEntityMultiMachine;
import com.denfop.tiles.panels.entity.TileEntitySolarPanel;
import ic2.api.recipe.RecipeOutput;
import ic2.core.init.Localization;
import net.minecraft.block.material.MapColor;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.Random;

public class TileEntityTripleRecycler extends TileEntityMultiMachine {

    public TileEntityTripleRecycler() {
        super(
                EnumMultiMachine.TRIPLE_RECYCLER.usagePerTick,
                EnumMultiMachine.TRIPLE_RECYCLER.lenghtOperation,
                1,
                2,
                true,
                1
        );
    }
    protected void updateEntityServer() {
        boolean needsInvUpdate = false;
        if (solartype != null) {
            if (this.energy.getEnergy() < this.energy.getCapacity() || (this.energy2 < this.maxEnergy2 && this.rf)) {
                TileEntitySolarPanel panel = new TileEntitySolarPanel(solartype);
                if (panel.getWorld() != this.getWorld()) {
                    panel.setWorld(this.getWorld());
                }
                panel.skyIsVisible = this.world.canBlockSeeSky(this.pos.up()) &&
                        (this.world.getBlockState(this.pos.up()).getMaterial().getMaterialMapColor() ==
                                MapColor.AIR) && !panel.noSunWorld;
                panel.wetBiome = panel.getWorld().getBiome(this.pos).getRainfall() > 0.0F;
                panel.rain = panel.wetBiome && (this.world.isRaining() || this.world.isThundering());
                panel.sunIsUp = this.getWorld().isDaytime();

                if (panel.active == null || this.getWorld().provider.getWorldTime() % 40 == 0) {
                    updateVisibility(panel);
                }
                panel.gainFuel();
                if (this.energy.getEnergy() < this.energy.getCapacity()) {
                    this.energy.addEnergy(Math.min(panel.generating, energy.getFreeEnergy()));
                } else if (this.energy2 < this.maxEnergy2 && this.rf) {
                    this.energy2 += Math.min(panel.generating, (this.maxEnergy2 - this.energy2) / Config.coefficientrf);
                }
            }
        }
        int quickly = 1;

        boolean isActive = false;
        if (this.world.provider.getWorldTime() % 10 == 0) {
            if (this.modulestorage && !this.inputSlots.isEmpty()) {
                final ItemStack stack = this.inputSlots.get();
                int size = 0;
                int col = 0;
                for (int i = 0; i < sizeWorkingSlot; i++) {
                    ItemStack stack1 = this.inputSlots.get(i);

                    if (stack1.isItemEqual(stack)) {
                        size += stack1.getCount();
                    }

                    if (stack1.isItemEqual(stack) || stack1.isEmpty()) {
                        col++;
                    }
                }
                int count = size / col;
                int count1 = size - (count * col);
                for (int i = 0; i < sizeWorkingSlot; i++) {
                    ItemStack stack1 = this.inputSlots.get(i);
                    if ((stack1.isItemEqual(stack)) || stack1.isEmpty()) {
                        ItemStack stack2 = stack.copy();
                        int dop = 0;
                        int prom = 64 - count;
                        if (prom > 0) {
                            if (count1 > prom) {
                                dop += prom;
                                count1 -= prom;
                            } else {
                                dop += count1;
                                count1 = 0;
                            }

                        }

                        stack2.setCount(count + dop);
                        this.inputSlots.put(i, stack2);

                    }

                }


            }
        }

        for (int i = 0; i < sizeWorkingSlot; i++) {
            RecipeOutput output = this.output[i];

            if (this.quickly) {
                quickly = 100;
            }
            int size = 1;
            if (this.output[i] != null) {
                if (this.modulesize) {
                    for (int j = 0; ; j++) {
                        ItemStack stack = new ItemStack(
                                this.inputSlots.get(i).getItem(),
                                j,
                                this.inputSlots.get(i).getItemDamage()
                        );
                        if (recipe != null) {
                            if (Recipes.recipes.getRecipeOutput(this.recipe.getName(), false, stack)  != null) {
                                size = j;
                                break;
                            }
                        }
                    }
                    size = (int) Math.floor((float) this.inputSlots.get(i).getCount() / size);
                    int size1 = 0;

                    for (int ii = 0; ii < sizeWorkingSlot; ii++) {
                        if (this.outputSlot.get(ii) != null) {
                            size1 += (64 - this.outputSlot.get(ii).getCount());
                        } else {
                            size1 += 64;
                        }
                    }
                    if (output != null) {
                        size1 = size1 / output.items.get(0).getCount();
                    }
                    size = Math.min(size1, size);
                }
            }
            if (output != null && this.outputSlot.canAdd(output.items) && (this.energy.canUseEnergy(this.energyConsume * quickly * size) || this.energy2 >= Math.abs(this.energyConsume * 4 * quickly * size))) {
                setActive(true);
                if (this.progress[i] == 0) {
                    initiate(0);
                    col[i] = this.inputSlots.get(i).getCount();
                }
                this.cold.addEnergy(0.1);
                if (this.inputSlots.get(i).getCount() != col[i] && this.modulesize) {
                    this.progress[i] = (short) (col[i] * this.progress[i] / this.inputSlots.get(i).getCount());
                    col[i] = this.inputSlots.get(i).getCount();
                }
                if (this.energy.getEnergy() >= this.energyConsume * quickly * size) {
                    this.energy.useEnergy(this.energyConsume * quickly * size);
                } else if (this.energy2 >= Math.abs(this.energyConsume * 4 * quickly * size)) {
                    this.energy2 -= Math.abs(this.energyConsume * 4 * quickly * size);
                } else {
                    return;
                }
                isActive = true;
                this.progress[i]++;

                this.guiProgress[i] = (double) this.progress[i] / this.operationLength;

                if (this.progress[i] >= this.operationLength || this.quickly) {
                    this.guiProgress[i] = 0;
                    this.progress[i] = 0;
                    if (this.expstorage < 5000) {
                        Random rand = new Random();

                        int exp = rand.nextInt(3) + 1;
                        this.expstorage = this.expstorage + exp;
                        if (this.expstorage >= 5000) {
                            expstorage = 5000;
                        }
                    }

                    operate(i, output, size);
                    needsInvUpdate = true;
                    initiate(2);
                }
            } else {
                if (this.progress[i] != 0 && getActive()) {
                    initiate(1);
                }
                if (output == null) {
                    this.progress[i] = 0;
                }

            }

        }

        if (getActive() != isActive) {
            setActive(isActive);
        }
        if(Config.coolingsystem) {
            final double fillratio = this.cold.getFillRatio();
            if (fillratio >= 0.75 && fillratio < 1) {
                this.operationLength = this.defaultOperationLength * 2;
            }
            if (fillratio >= 1) {
                this.operationLength = Integer.MAX_VALUE;
            }
            if (fillratio >= 0.5 && fillratio < 0.75) {
                this.operationLength = (int) (this.defaultOperationLength * 1.5);
            }
            if (fillratio < 0.5) {
                this.operationLength = this.defaultOperationLength;
            }
        }
        needsInvUpdate |= this.upgradeSlot.tickNoMark();
        if (needsInvUpdate) {
            super.markDirty();
        }
    }
    public void operate(int slotId, RecipeOutput output, int size) {
        for (int i = 0; i < this.operationsPerTick; i++) {

            operateOnce(slotId, output.items, size, output);
            output = this.output[slotId];
            if (output == null) {
                break;
            }
        }
    }


    public void operateOnce(int slotId, List<ItemStack> processResult, int size, RecipeOutput output) {

        for (int i = 0; i < size; i++) {
            if (!random) {
                this.inputSlots.consume(slotId);
                this.outputSlot.add(processResult);
            } else {
                Random rand = new Random();
                if (rand.nextInt(max + 1) <= min) {
                    this.inputSlots.consume(slotId);
                    this.outputSlot.add(processResult);
                }
            }
            this.getOutput(slotId);
        }

    }

    public RecipeOutput getOutput(int slotId) {

        if (this.inputSlots.isEmpty(slotId)) {
            this.output[slotId] = null;
            return null;
        }
        this.output[slotId] = this.inputSlots.process(slotId);
        if (output[slotId] == null) {
            output[slotId] =  new RecipeOutput(null, Ic2Items.scrap);
        }
        if (this.outputSlot.canAdd(output[slotId].items)) {
            return output[slotId];
        }

        return null;
    }
    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.TRIPLE_RECYCLER;
    }

    public String getInventoryName() {
        return Localization.translate("iu.blockRecycler1.name");
    }

    public String getStartSoundFile() {
        return "Machines/RecyclerOp.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }


}
