package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseGenerationChipMachine;
import com.denfop.gui.GUIGenerationMicrochip;
import com.denfop.invslot.InvSlotProcessableGenerationMicrochip;
import com.denfop.item.resources.ItemIngot;
import com.denfop.recipemanager.MicrochipRecipeManager;
import com.denfop.tiles.base.TileEntityBaseGenerationMicrochip;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityGenerationMicrochip extends TileEntityBaseGenerationMicrochip {

    public TileEntityGenerationMicrochip() {
        super(1, 300, 1);
        this.inputSlotA = new InvSlotProcessableGenerationMicrochip(this, "inputA", 0, 5
        );

    }

    public static void init() {
        Recipes.GenerationMicrochip = new MicrochipRecipeManager();
        add(new ItemStack(Items.flint), new ItemStack(Items.dye, 1, 4), new ItemStack(Items.iron_ingot), new ItemStack(IUItem.iuingot, 1, 11), new ItemStack(IUItem.iuingot, 1, 15), new ItemStack(IUItem.basecircuit), false);
        add(new ItemStack(IUItem.iuingot, 1, 1), new ItemStack(Items.redstone, 1), new ItemStack(Items.gold_ingot), new ItemStack(IUItem.iuingot, 1, 7), new ItemStack(IUItem.iuingot, 1, 14), new ItemStack(IUItem.basecircuit, 1, 1), true);
        add(new ItemStack(IUItem.iuingot, 1, 18), new ItemStack(Items.redstone, 1), new ItemStack(Items.diamond), new ItemStack(IUItem.iuingot, 1, 0), new ItemStack(IUItem.iuingot, 1, 5), new ItemStack(IUItem.basecircuit, 1, 2), true);
        add(new ItemStack(IUItem.iuingot, 1, 18), new ItemStack(Items.redstone, 1), new ItemStack(Items.emerald), new ItemStack(IUItem.iuingot, 1, 0), new ItemStack(IUItem.iuingot, 1, 5), new ItemStack(IUItem.basecircuit, 1, 2), true);


        add(new ItemStack(IUItem.iuingot, 1, 2), new ItemStack(IUItem.iuingot, 1, 3), new ItemStack(IUItem.basecircuit, 1, 0), new ItemStack(IUItem.basecircuit, 1, 3), new ItemStack(IUItem.basecircuit, 1, 6), new ItemStack(IUItem.QuantumItems9, 1), true);

        add(new ItemStack(IUItem.iuingot, 1, 8), new ItemStack(IUItem.iuingot, 1, 6), new ItemStack(IUItem.basecircuit, 1, 1), new ItemStack(IUItem.basecircuit, 1, 4), new ItemStack(IUItem.basecircuit, 1, 7), new ItemStack(IUItem.cirsuitQuantum, 1), true);
        add(new ItemStack(IUItem.iuingot, 1, 2), new ItemStack(IUItem.iuingot, 1, 10), new ItemStack(IUItem.basecircuit, 1, 2), new ItemStack(IUItem.basecircuit, 1, 5), new ItemStack(IUItem.basecircuit, 1, 8), new ItemStack(IUItem.circuitSpectral, 1), true);

    }

    public static void add(ItemStack first, ItemStack second, ItemStack three, ItemStack four, ItemStack five, ItemStack output, boolean check) {
        IRecipeInput first1;
        IRecipeInput second1;
        IRecipeInput three1;
        IRecipeInput four1;
        IRecipeInput five1;

        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setShort("temperature", (short) 4500);
        if (check) {
            if (!OreDictionary.getOreName(OreDictionary.getOreID(first)).isEmpty() && first.getItem() instanceof ItemIngot)
                first1 = new RecipeInputOreDict(OreDictionary.getOreName(OreDictionary.getOreID(first)));
            else {
                first1 = new RecipeInputItemStack(first);
            }
            if (!OreDictionary.getOreName(OreDictionary.getOreID(second)).isEmpty() && second.getItem() instanceof ItemIngot)
                second1 = new RecipeInputOreDict(OreDictionary.getOreName(OreDictionary.getOreID(second)));
            else {
                second1 = new RecipeInputItemStack(second);
            }
            if (!OreDictionary.getOreName(OreDictionary.getOreID(three)).isEmpty() && three.getItem() instanceof ItemIngot)
                three1 = new RecipeInputOreDict(OreDictionary.getOreName(OreDictionary.getOreID(three)));
            else {
                three1 = new RecipeInputItemStack(three);
            }
            if (!OreDictionary.getOreName(OreDictionary.getOreID(four)).isEmpty() && four.getItem() instanceof ItemIngot)
                four1 = new RecipeInputOreDict(OreDictionary.getOreName(OreDictionary.getOreID(four)));
            else {
                four1 = new RecipeInputItemStack(four);
            }
            if (!OreDictionary.getOreName(OreDictionary.getOreID(five)).isEmpty() && five.getItem() instanceof ItemIngot)
                five1 = new RecipeInputOreDict(OreDictionary.getOreName(OreDictionary.getOreID(five)));
            else {
                five1 = new RecipeInputItemStack(five);
            }
            Recipes.GenerationMicrochip.addRecipe(first1, second1, three1, four1, five1, output, nbt);
        } else {
            Recipes.GenerationMicrochip.addRecipe(new RecipeInputItemStack(first), new RecipeInputItemStack(second), new RecipeInputItemStack(three), new RecipeInputItemStack(four), new RecipeInputItemStack(five), output, nbt);

        }
    }

    public String getInventoryName() {

        return "Generation Microchip";
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIGenerationMicrochip(new ContainerBaseGenerationChipMachine(entityPlayer, this));
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

    @Override
    public short getTemperature() {
        return this.temperature;
    }

    @Override
    public void setTemperature(short temperature) {
        this.temperature = temperature;
    }

    @Override
    public short getMaxTemperature() {
        return this.maxtemperature;
    }

    @Override
    public boolean isFluidTemperature() {
        return false;
    }

    @Override
    public FluidStack getFluid() {
        return null;
    }

    @Override
    public TileEntityElectricMachine getTile() {
        return this;
    }
}
