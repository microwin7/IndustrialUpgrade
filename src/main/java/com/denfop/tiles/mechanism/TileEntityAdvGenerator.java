package com.denfop.tiles.mechanism;

import com.denfop.container.ContainerGenerator;
import com.denfop.gui.GUIGenerator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.ContainerBase;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

public class TileEntityAdvGenerator extends TileEntityBaseGenerator {
    private final String name;
    public int itemFuelTime = 0;

    public TileEntityAdvGenerator(double coef, int maxstorage, String name) {
        super((int) (Math.round(10.0F * ConfigUtil.getFloat(MainConfig.get(), "balance/energy/generator/generator")) * coef), 2, maxstorage);
        this.name = name;
    }

    public int gaugeFuelScaled(int i) {
        if (this.fuel <= 0) {
            return 0;
        } else {
            if (this.itemFuelTime <= 0) {
                this.itemFuelTime = this.fuel;
            }

            return Math.min(this.fuel * i / this.itemFuelTime, i);
        }
    }

    public boolean gainFuel() {
        int fuelValue = this.fuelSlot.consumeFuel() / 4;
        if (fuelValue == 0) {
            return false;
        } else {
            this.fuel += fuelValue;
            this.itemFuelTime = fuelValue;
            return true;
        }
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal(this.name);
    }

    public boolean isConverting() {
        return this.fuel > 0;
    }

    public String getOperationSoundFile() {
        return "Generators/GeneratorLoop.ogg";
    }

    public ContainerBase<TileEntityAdvGenerator> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerGenerator(entityPlayer, this);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIGenerator(new ContainerGenerator(entityPlayer, this));
    }
}
