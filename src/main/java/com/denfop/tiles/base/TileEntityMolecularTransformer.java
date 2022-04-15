package com.denfop.tiles.base;

import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseMolecular;
import com.denfop.gui.GUIMolecularTransformer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.Ic2Items;
import ic2.core.block.invslot.InvSlotProcessableGeneric;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class TileEntityMolecularTransformer extends TileEntityBaseMolecular
        implements INetworkClientTileEntityEventListener {
    public byte redstoneMode;


    public TileEntityMolecularTransformer() {
        super();
        this.inputSlot = new InvSlotProcessableGeneric(this, "input", 0, 1, Recipes.molecular);
        this.redstoneMode = 0;
    }

    public static void init() {
        Recipes.molecular = new BasicMachineRecipeManager();


        addrecipe(new ItemStack(Items.skull, 1, 0), new ItemStack(Items.skull, 1, 1), Config.molecular);

        addrecipe(new ItemStack(Items.skull, 1, 1), new ItemStack(Items.nether_star, 1), Config.molecular1);

        addrecipe(new ItemStack(Items.iron_ingot, 1, 0), Ic2Items.iridiumOre, Config.molecular2);

        addrecipe(Ic2Items.Plutonium, new ItemStack(IUItem.proton, 1), Config.molecular3);

        addrecipe("ingotSpinel", OreDictionary.getOres("ingotIridium").get(0), Config.molecular4);

        addrecipe(new ItemStack(IUItem.photoniy), new ItemStack(IUItem.photoniy_ingot), Config.molecular5);

        addrecipe(new ItemStack(Blocks.netherrack), new ItemStack(Items.gunpowder, 2), Config.molecular6);

        addrecipe(new ItemStack(Blocks.sand), new ItemStack(Blocks.gravel, 1), Config.molecular7);

        addrecipe("dustCoal", new ItemStack(Items.diamond), Config.molecular8);

        addrecipe("ingotCopper", OreDictionary.getOres("ingotNickel").get(0), Config.molecular9);

        addrecipe("ingotLead", OreDictionary.getOres("ingotGold").get(0), Config.molecular10);

        if (OreDictionary.getOres("ingotSilver").size() >= 1)
            addrecipe("ingotTin", OreDictionary.getOres("ingotSilver").get(0), Config.molecular11);

        if (OreDictionary.getOres("ingotSilver").size() >= 1)
            addrecipe("ingotSilver",
                    OreDictionary.getOres("ingotTungsten").get(0), Config.molecular12);

        addrecipe("ingotTungsten",
                OreDictionary.getOres("ingotSpinel").get(0), Config.molecular13);

        addrecipe("ingotChromium",
                OreDictionary.getOres("ingotMikhail").get(0), Config.molecular14);

        addrecipe("ingotPlatinum",
                OreDictionary.getOres("ingotChromium").get(0), Config.molecular15);

        addrecipe("ingotGold", OreDictionary.getOres("ingotPlatinum").get(0), Config.molecular16);

        addrecipe("ingotIridium", new ItemStack(IUItem.core, 1, 0), Config.molecular17);

        addrecipe(new ItemStack(IUItem.core, 4, 0), new ItemStack(IUItem.core, 1, 1), Config.molecular18);

        addrecipe(new ItemStack(IUItem.core, 4, 1), new ItemStack(IUItem.core, 1, 2), Config.molecular19);

        addrecipe(new ItemStack(IUItem.core, 4, 2), new ItemStack(IUItem.core, 1, 3), Config.molecular20);

        addrecipe(new ItemStack(IUItem.core, 4, 3), new ItemStack(IUItem.core, 1, 4), Config.molecular21);

        addrecipe(new ItemStack(IUItem.core, 4, 4), new ItemStack(IUItem.core, 1, 5), Config.molecular22);

        addrecipe(new ItemStack(IUItem.core, 4, 5), new ItemStack(IUItem.core, 1, 6), Config.molecular23);

        addrecipe(new ItemStack(IUItem.core, 4, 6), new ItemStack(IUItem.core, 1, 7), Config.molecular24);

        addrecipe(new ItemStack(IUItem.core, 4, 7), new ItemStack(IUItem.core, 1, 8), Config.molecular25);

        addrecipe(new ItemStack(IUItem.core, 4, 8), new ItemStack(IUItem.core, 1, 9), Config.molecular26);
        addrecipe(new ItemStack(IUItem.core, 4, 9), new ItemStack(IUItem.core, 1, 10), Config.molecular38);

        addrecipe(new ItemStack(IUItem.core, 4, 10), new ItemStack(IUItem.core, 1, 11), Config.molecular39);
        addrecipe(new ItemStack(IUItem.core, 4, 11), new ItemStack(IUItem.core, 1, 12), Config.molecular40);
        addrecipe(new ItemStack(IUItem.core, 4, 12), new ItemStack(IUItem.core, 1, 13), Config.molecular41);

        //

        addrecipe(new ItemStack(IUItem.matter, 1, 1), new ItemStack(IUItem.lens, 1, 5), Config.molecular27);

        addrecipe(new ItemStack(IUItem.matter, 1, 2), new ItemStack(IUItem.lens, 1, 6), Config.molecular28);

        addrecipe(new ItemStack(IUItem.matter, 1, 3), new ItemStack(IUItem.lens, 1, 2), Config.molecular29);

        addrecipe(new ItemStack(IUItem.matter, 1, 4), new ItemStack(IUItem.lens, 1, 4), Config.molecular30);

        addrecipe(new ItemStack(IUItem.matter, 1, 5), new ItemStack(IUItem.lens, 1, 1), Config.molecular31);

        addrecipe(new ItemStack(IUItem.matter, 1, 6), new ItemStack(IUItem.lens, 1, 3), Config.molecular32);

        addrecipe(new ItemStack(IUItem.matter, 1, 7), new ItemStack(IUItem.lens, 1), Config.molecular33);

        addrecipe(Ic2Items.iridiumOre, new ItemStack(IUItem.photoniy), Config.molecular34);

        addrecipe("ingotMikhail",
                OreDictionary.getOres("ingotMagnesium").get(0), Config.molecular35);

        addrecipe("ingotMagnesium", OreDictionary.getOres("ingotCaravky").get(0), Config.molecular36);


        ItemStack stack = OreDictionary.getOres("ingotIridium").get(0);
        stack.stackSize = 4;
        ItemStack stack1 = Ic2Items.iridiumShard;
        stack1.stackSize = 9;
        addrecipe(stack1, stack, Config.molecular37);
        addrecipe("ingotCaravky", new ItemStack(IUItem.iuingot, 1, 18), 600000);
        addrecipe("ingotCobalt", new ItemStack(IUItem.iuingot, 1, 16), 350000);
        addrecipe(new ItemStack(IUItem.iuingot, 1, 16), new ItemStack(IUItem.iuingot, 1, 15), 300000);

    }

    public static void addrecipe(ItemStack stack, ItemStack stack1, double energy) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("energy", energy);
        Recipes.molecular.addRecipe(new RecipeInputItemStack(stack), nbt, stack1);
    }

    public static void addrecipe(String stack, ItemStack stack1, double energy) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("energy", energy);
        Recipes.molecular.addRecipe(new RecipeInputOreDict(stack), nbt, stack1);
    }

    public List<String> getNetworkedFields() {
        List<String> ret = super.getNetworkedFields();
        ret.add("guiProgress");
        ret.add("queue");
        ret.add("redstoneMode");
        ret.add("maxEnergy");
        ret.add("energy");
        ret.add("perenergy");
        ret.add("differenceenergy");
        ret.add("time");

        return ret;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    public String getInventoryName() {
        return "Molecular Transformer";
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);


        this.queue = nbttagcompound.getBoolean("queue");
        this.redstoneMode = nbttagcompound.getByte("redstoneMode");


    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setByte("redstoneMode", this.redstoneMode);
        nbttagcompound.setBoolean("queue", this.queue);

    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIMolecularTransformer(new ContainerBaseMolecular(entityPlayer, this));
    }


    public void onNetworkEvent(EntityPlayer player, int event) {

        if (event == 0) {
            this.redstoneMode = (byte) (this.redstoneMode + 1);
            if (this.redstoneMode >= 8)
                this.redstoneMode = 0;
            this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
        }
        if (event == 1) {
            this.queue = !this.queue;
            setOverclockRates();
        }
    }


    public float getWrenchDropRate() {
        return 0.85F;
    }

}
