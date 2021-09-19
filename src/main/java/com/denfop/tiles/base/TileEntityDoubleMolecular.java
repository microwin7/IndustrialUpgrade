package com.denfop.tiles.base;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerBaseDoubleMolecular;
import com.denfop.gui.GuiDoubleMolecularTransformer;
import com.denfop.invslot.InvSlotProcessableDoubleMolecular;
import com.denfop.recipemanager.DoubleMolecularRecipeManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.network.INetworkClientTileEntityEventListener;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.Ic2Items;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

public class TileEntityDoubleMolecular extends TileEntityBaseDoubleMolecular
        implements INetworkClientTileEntityEventListener {
    public byte redstoneMode;


    public TileEntityDoubleMolecular() {
        super(500);
        this.inputSlot = new InvSlotProcessableDoubleMolecular(this, "input", 0, 2);
        this.redstoneMode = 0;
        this.setTier(12);
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
        return ret;
    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }


    public static void init() {
        Recipes.doublemolecular = new DoubleMolecularRecipeManager();

        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.module1, 1), new ItemStack(IUItem.upgrademodule, 1, 0), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.module2, 1), new ItemStack(IUItem.upgrademodule, 1, 1), 500000);

        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.alloysdoubleplate, 1, 8), new ItemStack(IUItem.upgrademodule, 1, 2), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.alloysdoubleplate, 1, 0), new ItemStack(IUItem.upgrademodule, 1, 3), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.alloysdoubleplate, 1, 4), new ItemStack(IUItem.upgrademodule, 1, 4), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.radiationresources, 4, 1), new ItemStack(IUItem.upgrademodule, 1, 5), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.radiationresources, 4, 2), new ItemStack(IUItem.upgrademodule, 1, 6), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Items.potionitem, 1, 8227), new ItemStack(IUItem.upgrademodule, 1, 7), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Items.potionitem, 1, 8237), new ItemStack(IUItem.upgrademodule, 1, 8), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Items.potionitem, 1, 8194), new ItemStack(IUItem.upgrademodule, 1, 9), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.alloysdoubleplate, 1, 6), new ItemStack(IUItem.upgrademodule, 1, 10), 500000);

        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Items.potionitem, 1, 8201), new ItemStack(IUItem.upgrademodule, 1, 11), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.radiationresources, 2, 3), new ItemStack(IUItem.upgrademodule, 1, 13), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Items.potionitem, 1, 8258), new ItemStack(IUItem.upgrademodule, 1, 14), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(IUItem.module3, 1), new ItemStack(IUItem.upgrademodule, 1, 15), 500000);
        addrecipe(new ItemStack(IUItem.module_schedule, 1), new ItemStack(Ic2Items.energyCrystal.getItem(), 1, OreDictionary.WILDCARD_VALUE), new ItemStack(IUItem.upgrademodule, 1, 16), 500000);

        addrecipe(new ItemStack(IUItem.module1, 1), new ItemStack(IUItem.module1, 1), new ItemStack(IUItem.genmodule), 5000000);
        addrecipe(new ItemStack(IUItem.genmodule, 1), new ItemStack(IUItem.genmodule, 1), new ItemStack(IUItem.genmodule1), 7500000);
        addrecipe(new ItemStack(IUItem.module2, 1), new ItemStack(IUItem.module2, 1), new ItemStack(IUItem.gennightmodule), 5000000);
        addrecipe(new ItemStack(IUItem.gennightmodule, 1), new ItemStack(IUItem.gennightmodule, 1), new ItemStack(IUItem.gennightmodule1), 7500000);
        addrecipe(new ItemStack(IUItem.module3, 1), new ItemStack(IUItem.module3, 1), new ItemStack(IUItem.storagemodule), 5000000);
        addrecipe(new ItemStack(IUItem.storagemodule, 1), new ItemStack(IUItem.storagemodule, 1), new ItemStack(IUItem.storagemodule1), 7500000);
        addrecipe(new ItemStack(IUItem.module4, 1), new ItemStack(IUItem.module4, 1), new ItemStack(IUItem.outputmodule), 5000000);
        addrecipe(new ItemStack(IUItem.outputmodule, 1), new ItemStack(IUItem.outputmodule, 1), new ItemStack(IUItem.outputmodule1), 7500000);
        addrecipe(new ItemStack(IUItem.entitymodules, 1, 1), new ItemStack(IUItem.entitymodules, 1, 1), new ItemStack(IUItem.spawnermodules, 1, 6), 20000000);
        addrecipe(new ItemStack(IUItem.spawnermodules, 1, 6), new ItemStack(IUItem.spawnermodules, 1, 6), new ItemStack(IUItem.spawnermodules, 1, 7), 20000000);

    }

    public static void addrecipe(ItemStack stack, ItemStack stack2, ItemStack stack1, double energy) {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setDouble("energy", energy);
        Recipes.doublemolecular.addRecipe(new RecipeInputItemStack(stack), new RecipeInputItemStack(stack2), nbt, stack1);
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
        return new GuiDoubleMolecularTransformer(new ContainerBaseDoubleMolecular(entityPlayer, this));
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
        }
    }


    public float getWrenchDropRate() {
        return 0.85F;
    }

}
