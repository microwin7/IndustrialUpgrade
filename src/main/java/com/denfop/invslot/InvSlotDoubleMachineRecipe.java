package com.denfop.invslot;

import com.denfop.api.IDoubleMachineRecipeManager;
import com.denfop.item.modules.UpgradeModule;
import com.denfop.tiles.base.TileEntityDoubleElectricMachine;
import com.denfop.tiles.base.TileEntityUpgradeBlock;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import ic2.api.recipe.RecipeOutput;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotProcessable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.denfop.events.IUEventHandler.getUpgradeItem;

public class InvSlotDoubleMachineRecipe extends InvSlotProcessable {

    public final IDoubleMachineRecipeManager recipes;

    public InvSlotDoubleMachineRecipe(TileEntityInventory base1, String name1, int oldStartIndex1, int count, IDoubleMachineRecipeManager recipes) {
        super(base1, name1, oldStartIndex1, count);
        this.recipes = recipes;
    }

    public Map<IDoubleMachineRecipeManager.Input, RecipeOutput> getRecipeList() {
        return recipes.getRecipes();
    }

    public boolean accepts(ItemStack itemStack) {
        for (Map.Entry<IDoubleMachineRecipeManager.Input, RecipeOutput> entry : getRecipeList().entrySet()) {
            if ((entry.getKey()).container.matches(itemStack)
                    || (entry.getKey()).fill.matches(itemStack))
                return itemStack != null || !(itemStack.getItem() instanceof ic2.core.item.ItemUpgradeModule);
        }
        return false;

    }

    protected RecipeOutput getOutput(ItemStack container, ItemStack fill, boolean adjustInput) {

        return recipes.getOutputFor(container, fill, adjustInput, false);

    }

    protected RecipeOutput getOutputFor(ItemStack input, ItemStack input1, boolean adjustInput) {
        return getOutput(input, input1, adjustInput);
    }

    public RecipeOutput process() {
        ItemStack input = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(1);
        if (input == null)
            return null;
        if (input1 == null)
            return null;
        RecipeOutput output = getOutputFor(input, input1, false);
        if (output == null)
            return null;
        if(this.base instanceof TileEntityUpgradeBlock){
            ItemStack stack1 = getUpgradeItem(((TileEntityUpgradeBlock) this.base).inputSlotA.get(0)) ?((TileEntityUpgradeBlock) this.base).inputSlotA.get(0) : ((TileEntityUpgradeBlock) this.base).inputSlotA.get(1);
            ItemStack module = !getUpgradeItem(((TileEntityUpgradeBlock) this.base).inputSlotA.get(1)) ? ((TileEntityUpgradeBlock) this.base).inputSlotA.get(1) : ((TileEntityUpgradeBlock) this.base).inputSlotA.get(0);
            boolean allow = true;
            NBTTagCompound nbt1 = ModUtils.nbt(stack1);
            if (!nbt1.getString("mode_module" + 3).isEmpty()) {
                allow = false;
            }
            EnumInfoUpgradeModules type = UpgradeModule.getType(module.getItemDamage());
            int min = 0;
            for (int i = 0; i < 4; i++) {
                if (nbt1.getString("mode_module" + i).equals(type.name))
                    min++;
            }
            if (min >= type.max) {
                allow = false;
            }
            if(!allow)
                return null;
        }
        List<ItemStack> itemsCopy = new ArrayList<>(output.items.size());
        itemsCopy.addAll(output.items);
        return new RecipeOutput(output.metadata, itemsCopy);
    }

    public void consume() {

        ItemStack input = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(0);
        ItemStack input1 = ((TileEntityDoubleElectricMachine) this.base).inputSlotA.get(1);
        getOutputFor(input, input1, true);

        if (input != null && input.stackSize <= 0)
            ((TileEntityDoubleElectricMachine) this.base).inputSlotA.put(0, null);
        if (input1 != null && input1.stackSize <= 0)
            ((TileEntityDoubleElectricMachine) this.base).inputSlotA.put(1, null);


    }


}
