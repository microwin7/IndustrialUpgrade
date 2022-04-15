package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerHandlerHeavyOre;
import com.denfop.gui.GUIHandlerHeavyOre;
import com.denfop.tiles.base.TileEntityBaseHandlerHeavyOre;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityHandlerHeavyOre extends TileEntityBaseHandlerHeavyOre {

    public TileEntityHandlerHeavyOre() {
        super(1, 300, 3);
        this.inputSlotA = new InvSlotProcessableGeneric(this, "inputA", 0, 1, Recipes.handlerore);
    }

    public static void init() {
        Recipes.handlerore = new BasicMachineRecipeManager();
        addhandlerore(new ItemStack(IUItem.heavyore), new ItemStack[]{new ItemStack(Blocks.iron_ore), new ItemStack(Blocks.gold_ore)}, (short) 1500);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 1), new ItemStack[]{new ItemStack(IUItem.ore, 1, 9), new ItemStack(Blocks.gold_ore), Ic2Items.copperOre}, (short) 3000);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 2), new ItemStack[]{new ItemStack(IUItem.ore, 1, 14), Ic2Items.leadOre}, (short) 5000);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 3), new ItemStack[]{new ItemStack(IUItem.ore, 1, 8), new ItemStack(IUItem.ore, 1, 10)}, (short) 4000);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 4), new ItemStack[]{new ItemStack(Blocks.iron_ore), new ItemStack(IUItem.ore, 1, 6)}, (short) 2500);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 5), new ItemStack[]{new ItemStack(Blocks.quartz_ore), new ItemStack(IUItem.ore, 1, 15)}, (short) 2500);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 6), new ItemStack[]{Ic2Items.uraniumOre, new ItemStack(IUItem.toriyore)}, (short) 4500);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 7), new ItemStack[]{Ic2Items.copperOre, new ItemStack(Blocks.lapis_ore), new ItemStack(Blocks.redstone_ore)}, (short) 2000);

        addhandlerore(new ItemStack(IUItem.heavyore, 1, 8), new ItemStack[]{new ItemStack(IUItem.ore, 1, 7), new ItemStack(IUItem.ore1, 1, 0), new ItemStack(Blocks.iron_ore)}, (short) 3000);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 9), new ItemStack[]{new ItemStack(IUItem.ore, 1, 8), new ItemStack(IUItem.ore, 1, 6)}, (short) 3500);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 10), new ItemStack[]{new ItemStack(IUItem.ore, 1, 10), new ItemStack(IUItem.toriyore), Ic2Items.uraniumOre}, (short) 3000);
        addhandlerore(new ItemStack(IUItem.heavyore, 1, 11), new ItemStack[]{new ItemStack(IUItem.ore, 1, 15), new ItemStack(Blocks.coal_ore)}, (short) 4000);

    }

    public static void addhandlerore(ItemStack container, ItemStack[] output, short temperature) {
        NBTTagCompound nbt = ModUtils.nbt();
        nbt.setShort("temperature", temperature);
        Recipes.handlerore.addRecipe(new RecipeInputItemStack(container), nbt, output);

    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.handler.name");
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIHandlerHeavyOre(new ContainerHandlerHeavyOre(entityPlayer, this));
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
