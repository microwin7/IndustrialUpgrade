package com.denfop.tiles.base;

import com.denfop.api.Recipes;
import com.denfop.container.ContainerObsidianGenerator;
import com.denfop.gui.GUIObsidianGenerator;
import com.denfop.invslot.InvSlotObsidianGenerator;
import com.denfop.recipemanager.ObsidianRecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityObsidianGenerator extends TileEntityBaseObsidianGenerator {

    public TileEntityObsidianGenerator() {
        super(1, 300, 1);
        this.inputSlotA = new InvSlotObsidianGenerator(this, "inputA", 0, 2);
    }

    public static void init() {
        Recipes.obsidianGenerator = new ObsidianRecipeManager();
        Recipes.obsidianGenerator.addRecipe(new FluidStack(FluidRegistry.WATER, 1000), new FluidStack(FluidRegistry.LAVA, 1000), new ItemStack(Blocks.obsidian));
    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.blockObsGen.name");
    }


    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIObsidianGenerator(new ContainerObsidianGenerator(entityPlayer, this));
    }

    public String getStartSoundFile() {
        return "Machines/gen_obsidiant.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public float getWrenchDropRate() {
        return 0.85F;
    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Processing, UpgradableProperty.Transformer,
                UpgradableProperty.EnergyStorage, UpgradableProperty.ItemConsuming, UpgradableProperty.ItemProducing);
    }

}
