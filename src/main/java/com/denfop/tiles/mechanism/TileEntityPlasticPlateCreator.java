package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.block.base.BlocksItems;
import com.denfop.container.ContainerPlasticPlateCreator;
import com.denfop.gui.GUIPlasticPlateCreator;
import com.denfop.invslot.InvSlotProcessablePlasticPlate;
import com.denfop.recipemanager.PlasticPlateRecipeManager;
import com.denfop.tiles.base.TileEntityBasePlasticPlateCreator;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.ContainerBase;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityPlasticPlateCreator extends TileEntityBasePlasticPlateCreator {

    public TileEntityPlasticPlateCreator() {
        super(1, 300, 1);
        this.inputSlotA = new InvSlotProcessablePlasticPlate(this, "inputA", 0, 1);
    }

    public static void init() {
        Recipes.plasticplate = new PlasticPlateRecipeManager();
        Recipes.plasticplate.addRecipe(new RecipeInputItemStack(new ItemStack(IUItem.plast)), new FluidStack(BlocksItems.getFluid("fluidoxy"), 1000), new ItemStack(IUItem.plastic_plate));
    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.blockPlasticPlateCreator.name");
    }

    public int gaugeLiquidScaled(int i) {
        return this.getFluidTank().getFluidAmount() <= 0 ? 0 : this.getFluidTank().getFluidAmount() * i / this.getFluidTank().getCapacity();
    }


    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIPlasticPlateCreator(new ContainerPlasticPlateCreator(entityPlayer, this));

    }

    public ContainerBase<? extends TileEntityPlasticPlateCreator> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityPlasticPlateCreator>) new ContainerPlasticPlateCreator(entityPlayer, this);

    }

    public String getStartSoundFile() {
        return "Machines/MaceratorOp.ogg";
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
