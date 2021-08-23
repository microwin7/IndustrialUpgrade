package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.recipemanager.BasicMachineRecipeManager;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityFermer extends TileEntityMultiMachine {
    public TileEntityFermer() {
        super(EnumMultiMachine.Fermer.usagePerTick,EnumMultiMachine.Fermer.lenghtOperation, Recipes.fermer,3);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", sizeWorkingSlot, Recipes.fermer);
    }

    public static void init(){
        Recipes.fermer = new BasicMachineRecipeManager();
        addrecipe(Items.wheat_seeds,Items.wheat,2);
        addrecipe(Items.wheat,Items.wheat_seeds,1);
        addrecipe(Items.carrot,Items.carrot,2);
        addrecipe(Items.potato,Items.potato,2);
        addrecipe(Item.getItemFromBlock(Blocks.pumpkin),Items.pumpkin_seeds,1);

        addrecipe(Items.pumpkin_seeds,Item.getItemFromBlock(Blocks.pumpkin),2);
        addrecipe(Items.melon_seeds,Items.melon,2);
        addrecipe(Items.melon,Items.melon_seeds,1);
        addrecipe(Item.getItemFromBlock(Blocks.cocoa),Item.getItemFromBlock(Blocks.cocoa),2);
       for(int i = 0; i < 4; i++)
        addrecipe(new ItemStack(Blocks.sapling,1,i),new ItemStack(Blocks.log,2,i));
        for(int i = 0; i < 2; i++)
            addrecipe(new ItemStack(Blocks.sapling,1,i+4),new ItemStack(Blocks.log2,2,i));
        for(int i = 0; i < 4; i++)
            addrecipe(new ItemStack(Blocks.log,1,i),new ItemStack(Blocks.sapling,1,i));
        for(int i = 0; i < 2; i++)
            addrecipe(new ItemStack(Blocks.log2,1,i),new ItemStack(Blocks.sapling,1,i+4));

    }

    public static void addrecipe(ItemStack input, ItemStack output){
             Recipes.fermer.addRecipe(new RecipeInputItemStack(input),null, output);
    }
    public static void addrecipe(Item input, Item output , int n){
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(input)),null, new ItemStack(output,n));
    }
    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.Fermer;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockFermer.name");
    }

    public String getStartSoundFile() {
        return "Machines/Fermer.ogg";
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
