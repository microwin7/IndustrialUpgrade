package com.denfop.tiles.base;

import com.denfop.api.Recipes;
import com.denfop.container.ContainerConverterSolidMatter;
import com.denfop.gui.GUIConverterSolidMatter;
import com.denfop.invslot.InvSlotConverterSolidMatter;
import com.denfop.invslot.InvSlotProcessableConverterSolidMatter;
import com.denfop.recipemanager.ConverterSolidMatterRecipeManager;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.audio.AudioSource;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.block.invslot.InvSlotProcessable;
import ic2.core.block.invslot.InvSlotUpgrade;
import ic2.core.upgrade.IUpgradableBlock;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityConverterSolidMatter extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, IUpgradableBlock {

    private double progress;

    public AudioSource audioSource;


    public final double[] quantitysolid = new double[8];


    private final int defaultTier;


    private final InvSlotUpgrade upgradeSlot;

    public final InvSlotConverterSolidMatter MatterSlot;
    public final InvSlotProcessable inputSlot;
    public final InvSlotOutput outputSlot;


    private double guiProgress = 0;


    public TileEntityConverterSolidMatter() {
        super(50000, 12, 0);
        this.MatterSlot = new InvSlotConverterSolidMatter(this, "input", 1);
        this.upgradeSlot = new InvSlotUpgrade(this, "upgrade", 3, 1);
        this.outputSlot = new InvSlotOutput(this, "output", 2, 1);
        this.inputSlot = new InvSlotProcessableConverterSolidMatter(this, "inputA", 4, 1, Recipes.matterrecipe);
        this.progress = 0;
        defaultTier = 12;
    }

    public static void init() {
        Recipes.matterrecipe = new ConverterSolidMatterRecipeManager();
        addrecipe(new ItemStack(Blocks.stone), 0.5, 0, 0, 0, 0, 0.25, 0, 0);
        addrecipe(new ItemStack(Blocks.grass), 0.5, 0, 0, 0, 0, 0.25, 0, 0);
        addrecipe(new ItemStack(Blocks.gravel), 0.5, 0, 0, 0, 0, 0.25, 0, 0);
        addrecipe(new ItemStack(Blocks.gold_ore), 1, 0, 0, 0, 0, 4, 0, 0);
        addrecipe(new ItemStack(Blocks.coal_ore), 1, 0, 0, 0, 0, 2, 0, 0);
        addrecipe(new ItemStack(Blocks.gold_block), 45, 0, 0, 0, 0, 0, 0, 0);
        addrecipe(new ItemStack(Blocks.glowstone), 1, 0, 0, 3, 0, 0, 0, 0);
        addrecipe(new ItemStack(Blocks.glass), 2, 0, 0.5, 0, 0, 0, 0, 0);
        addrecipe(new ItemStack(Blocks.furnace), 2, 0, 0, 0, 0, 1, 0, 0);
        addrecipe(new ItemStack(Blocks.end_stone), 0.5, 0, 0, 0, 0, 0, 0.25, 0);

    }

    public static void addrecipe(ItemStack stack, double matter, double sunmatter, double aquamatter, double nethermatter, double nightmatter, double earthmatter, double endmatter, double aermatter) {
        NBTTagCompound nbt = new NBTTagCompound();
        double[] quantitysolid = {matter, sunmatter, aquamatter, nethermatter, nightmatter, earthmatter, endmatter, aermatter};
        for (int i = 0; i < quantitysolid.length; i++)
            ModUtils.SetDoubleWithoutItem(nbt, ("quantitysolid_" + i), quantitysolid[i]);

        Recipes.matterrecipe.addRecipe(new RecipeInputItemStack(stack), nbt, stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRender() {

    }

    @Override
    public void onNetworkUpdate(String field) {

    }

    public boolean shouldRenderInPass(int pass) {
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

    public double getProgress() {
        return this.guiProgress;
    }

    public void updateEntityServer() {

        super.updateEntityServer();

        this.MatterSlot.getmatter();
        RecipeOutput output = getOutput();
        if (output != null) {
            setActive(true);

            if (this.energy == 0)
                IC2.network.get().initiateTileEntityEvent(this, 0, true);
            if (this.useEnergy(2, false) && this.getrequiredmatter(output)) {
                this.progress++;
                this.useEnergy(2, true);
            }

            double p = (this.progress / 100);


            if (p <= 1)
                this.guiProgress = p;
            if (p > 1)
                this.guiProgress = 1;
            if (progress >= 100 && this.getrequiredmatter(output)) {

                operate(output);
                this.progress = 0;


                IC2.network.get().initiateTileEntityEvent(this, 2, true);
            }
        } else {
            if (getActive())
                IC2.network.get().initiateTileEntityEvent(this, 1, true);

            setActive(false);
        }

    }

    private void useMatter(RecipeOutput output) {
        if (inputSlot.isEmpty())
            return;
        ItemStack stack = this.inputSlot.get(0);

        double quantity = ModUtils.NBTGetDouble(stack, "quantity");
        if (quantity == 0)
            quantity = 1;
        quantity = quantity / 2;
        if (quantity < 0.0009765625) {
            return;
        }
        NBTTagCompound nbt = output.metadata;
        double[] outputmatter = new double[9];
        for (int i = 0; i < this.quantitysolid.length; i++)
            outputmatter[i] = nbt.getDouble(("quantitysolid_" + i));
        for (int i = 0; i < this.quantitysolid.length; i++)
            this.quantitysolid[i] -= outputmatter[i];
    }

    public boolean getrequiredmatter(RecipeOutput output) {
        NBTTagCompound nbt = output.metadata;
        double[] outputmatter = new double[9];

        for (int i = 0; i < this.quantitysolid.length; i++)
            outputmatter[i] = nbt.getDouble(("quantitysolid_" + i));

        for (int i = 0; i < this.quantitysolid.length; i++)
            if (!(this.quantitysolid[i] >= outputmatter[i]))
                return false;

        return true;
    }


    public void operate(RecipeOutput output) {

        List<ItemStack> processResult = output.items;

        operateOnce(processResult, output);


    }

    public void operateOnce(List<ItemStack> processResult, RecipeOutput output) {
        useMatter(output);
        this.outputSlot.add(processResult);
    }


    public RecipeOutput getOutput() {
        if (this.inputSlot.isEmpty())
            return null;
        RecipeOutput output = this.inputSlot.process();
        if (output == null)
            return null;
        if (this.outputSlot.canAdd(output.items)) {
            return output;
        }
        return null;
    }


    public void markDirty() {
        super.markDirty();
        if (IC2.platform.isSimulating())
            setUpgradestat();
    }

    private void setUpgradestat() {
        this.upgradeSlot.onChanged();
        if (this.upgradeSlot.extraTier != 0)
            setTier(this.defaultTier + this.upgradeSlot.extraTier);


    }

    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(UpgradableProperty.Transformer,
                UpgradableProperty.ItemProducing);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        for (int i = 0; i < this.quantitysolid.length; i++)
            this.quantitysolid[i] = nbttagcompound.getDouble(("quantitysolid_" + i));
        this.progress = nbttagcompound.getDouble("progress");

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        for (int i = 0; i < this.quantitysolid.length; i++)
            nbttagcompound.setDouble(("quantitysolid_" + i), this.quantitysolid[i]);
        nbttagcompound.setDouble("progress", this.progress);

    }

    public double getEnergy() {
        return this.energy;
    }

    public boolean useEnergy(double amount, boolean consume) {
        if (this.energy >= amount) {
            if (consume)
                this.energy -= amount;
            return true;
        }
        return false;
    }

    public boolean useEnergy(double amount) {
        if (this.energy >= amount) {
            this.energy -= amount;
            return true;
        }
        return false;
    }

    public boolean isItemValidForSlot(final int i, final ItemStack itemstack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIConverterSolidMatter(new ContainerConverterSolidMatter(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityConverterSolidMatter> getGuiContainer(EntityPlayer entityPlayer) {
        return new ContainerConverterSolidMatter(entityPlayer, this);
    }


    public void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IC2.audioManager.removeSources(this);
            this.audioSource = null;
        }
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

    @Override
    public void onNetworkEvent(int event) {
        if (this.audioSource == null && getStartSoundFile() != null)
            this.audioSource = IC2.audioManager.createSource(this, getStartSoundFile());
        switch (event) {
            case 0:
                if (this.audioSource != null)
                    this.audioSource.play();
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (getInterruptSoundFile() != null)
                        IC2.audioManager.playOnce(this, getInterruptSoundFile());
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
        // TODO Auto-generated method stub
        return null;
    }


}
