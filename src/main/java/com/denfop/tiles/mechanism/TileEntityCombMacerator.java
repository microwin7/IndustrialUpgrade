package com.denfop.tiles.mechanism;

import com.denfop.api.Recipes;
import com.denfop.invslot.InvSlotProcessableMultiGeneric;
import com.denfop.tiles.base.TileEntityMultiMachine;
import ic2.api.recipe.RecipeInputOreDict;
import ic2.core.BasicMachineRecipeManager;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.Set;

public class TileEntityCombMacerator extends TileEntityMultiMachine {
    public TileEntityCombMacerator() {
        super(EnumMultiMachine.COMB_MACERATOR.usagePerTick,EnumMultiMachine.COMB_MACERATOR.lenghtOperation, Recipes.macerator,1);
        this.inputSlots = new InvSlotProcessableMultiGeneric(this, "input", 2, Recipes.macerator);
    }
    public static void init(){
        Recipes.macerator = new BasicMachineRecipeManager();
        for(String name :OreDictionary.getOreNames()){

            if(name.startsWith("crushed") && !name.startsWith("crushedPurified") ) {

              String name1=   name.substring("crushed".length());

              name1= "ore"+name1;

              if(OreDictionary.getOres(name1) != null){
                  addrecipe(name1,name,3);
              }

            }
        }
    }

    public static void addrecipe(String input, String output, int n){
        ItemStack stack;
        if(!output.equals("crushedSilver"))
            stack = OreDictionary.getOres(output).get(0);
        else
            stack = OreDictionary.getOres(output).get(1);


        stack.stackSize = n;
        Recipes.macerator.addRecipe(new RecipeInputOreDict(input),null, stack);
    }

    @Override
    public EnumMultiMachine getMachine() {
        return EnumMultiMachine.COMB_MACERATOR;
    }

    public String getInventoryName() {
        return StatCollector.translateToLocal("iu.blockCombMacerator.name");
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
