package com.denfop.tiles.base;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.gui.GUIUpgradeBlock;
import com.denfop.item.modules.UpgradeModule;
import com.denfop.recipemanager.DoubleMachineRecipeManager;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.denfop.events.IUEventHandler.getUpgradeItem;

public class TileEntityUpgradeBlock extends TileEntityDoubleElectricMachine {

    public TileEntityUpgradeBlock() {
        super(1, 300, 1, StatCollector.translateToLocal("blockUpgrade.name"), EnumDoubleElectricMachine.UPGRADE);
    }

    public static void init() {
        Recipes.upgrade = new DoubleMachineRecipeManager();
        addupgrade(IUItem.nanodrill, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.nanodrill, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.nanodrill, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.nanodrill, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumdrill, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumdrill, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.quantumdrill, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.quantumdrill, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectraldrill, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.spectraldrill, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.spectraldrill, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectraldrill, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.nanoaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.nanoaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.nanoaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.nanoaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.quantumaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.quantumaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.spectralaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.spectralaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.nanopickaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.nanopickaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.nanopickaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.nanopickaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumpickaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumpickaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.quantumpickaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.quantumpickaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralpickaxe, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.spectralpickaxe, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.spectralpickaxe, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralpickaxe, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.nanoshovel, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.nanoshovel, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.nanoshovel, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.nanoshovel, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumshovel, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.quantumshovel, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.quantumshovel, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.quantumshovel, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralshovel, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.spectralshovel, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.spectralshovel, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.spectralshovel, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");
        addupgrade(IUItem.ultDDrill, new ItemStack(IUItem.upgrademodule, 1, 13), "AOE_dig");
        addupgrade(IUItem.ultDDrill, new ItemStack(IUItem.upgrademodule, 1, 3), "speed");
        addupgrade(IUItem.ultDDrill, new ItemStack(IUItem.upgrademodule, 1, 16), "energy");
        addupgrade(IUItem.ultDDrill, new ItemStack(IUItem.upgrademodule, 1, 6), "dig_depth");

        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 0), "genday");
        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 1), "gennight");
        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 15), "storage");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 0), "genday");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 1), "gennight");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 15), "storage");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 0), "genday");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 1), "gennight");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 15), "storage");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 0), "genday");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 1), "gennight");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 15), "storage");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 0), "genday");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 1), "gennight");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 15), "storage");

        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 7), "fireResistance");
        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 14), "flyspeed");

        addupgrade(IUItem.quantumHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.quantumHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");

        addupgrade(IUItem.quantumLeggings, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.quantumLeggings, new ItemStack(IUItem.upgrademodule, 1, 9), "moveSpeed");

        addupgrade(IUItem.quantumBoots, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.quantumBoots, new ItemStack(IUItem.upgrademodule, 1, 10), "jump");

        addupgrade(IUItem.nano_bow, new ItemStack(IUItem.upgrademodule, 1, 4), "bowenergy");
        addupgrade(IUItem.nano_bow, new ItemStack(IUItem.upgrademodule, 1, 11), "bowdamage");
        addupgrade(IUItem.quantum_bow, new ItemStack(IUItem.upgrademodule, 1, 4), "bowenergy");
        addupgrade(IUItem.quantum_bow, new ItemStack(IUItem.upgrademodule, 1, 11), "bowdamage");
        addupgrade(IUItem.spectral_bow, new ItemStack(IUItem.upgrademodule, 1, 4), "bowenergy");
        addupgrade(IUItem.spectral_bow, new ItemStack(IUItem.upgrademodule, 1, 11), "bowdamage");

        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 5), "saberenergy");
        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 12), "saberdamage");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 5), "saberenergy");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 12), "saberdamage");

        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 17), "vampires");
        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 19), "poison");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 17), "vampires");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 19), "poison");
        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 20), "wither");
        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 23), "loot");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 20), "wither");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 23), "loot");
        addupgrade(IUItem.spectralSaber, new ItemStack(IUItem.upgrademodule, 1, 24), "fire");
        addupgrade(IUItem.quantumSaber, new ItemStack(IUItem.upgrademodule, 1, 24), "fire");


        addupgrade(IUItem.nanodrill, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.quantumdrill, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.spectraldrill, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.nanopickaxe, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.quantumpickaxe, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.spectralpickaxe, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.nanoshovel, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.quantumshovel, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.spectralshovel, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");
        addupgrade(IUItem.ultDDrill, new ItemStack(IUItem.upgrademodule, 1, 21), "silk");

        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.quantumHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.quantumLeggings, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.quantumBoots, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.quantumHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.quantumLeggings, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.quantumBoots, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.quantumBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.quantumHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.quantumLeggings, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.quantumBoots, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");

        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");


        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.NanoHelmet, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.NanoLeggings, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.NanoBoots, new ItemStack(IUItem.upgrademodule, 1, 18), "resistance");
        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.NanoHelmet, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.NanoLeggings, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.NanoBoots, new ItemStack(IUItem.upgrademodule, 1, 22), "invisibility");
        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.NanoHelmet, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.NanoLeggings, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.NanoBoots, new ItemStack(IUItem.upgrademodule, 1, 25), "repaired");
        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 7), "fireResistance");
        addupgrade(IUItem.NanoBodyarmor, new ItemStack(IUItem.upgrademodule, 1, 14), "flyspeed");

        addupgrade(IUItem.NanoHelmet, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.NanoHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");

        addupgrade(IUItem.advancedSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");
        addupgrade(IUItem.hybridSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");
        addupgrade(IUItem.ultimateSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");
        addupgrade(IUItem.spectralSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");
        addupgrade(IUItem.singularSolarHelmet, new ItemStack(IUItem.upgrademodule, 1, 8), "waterBreathing");


        addupgrade(IUItem.NanoLeggings, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.NanoLeggings, new ItemStack(IUItem.upgrademodule, 1, 9), "moveSpeed");

        addupgrade(IUItem.NanoBoots, new ItemStack(IUItem.upgrademodule, 1, 2), "protect");
        addupgrade(IUItem.NanoBoots, new ItemStack(IUItem.upgrademodule, 1, 10), "jump");

    }

    public static void addupgrade(Item container, ItemStack fill, String mode) {
        NBTTagCompound nbt = ModUtils.nbt();
        nbt.setString("mode_module", mode);
        Recipes.upgrade.addRecipe(new RecipeInputItemStack(new ItemStack(container, 1, OreDictionary.WILDCARD_VALUE)), new RecipeInputItemStack(fill), nbt, new ItemStack(container, 1, OreDictionary.WILDCARD_VALUE));

    }

    public boolean shouldRenderInPass(int pass) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIUpgradeBlock(new ContainerDoubleElectricMachine(entityPlayer, this, type));
    }

    public void operateOnce(RecipeOutput output, List<ItemStack> processResult) {

        ItemStack stack1 = getUpgradeItem(this.inputSlotA.get(0)) ? this.inputSlotA.get(0) : this.inputSlotA.get(1);
        ItemStack module = getUpgradeItem(this.inputSlotA.get(0)) ? this.inputSlotA.get(1) : this.inputSlotA.get(0);


        NBTTagCompound nbt1 = ModUtils.nbt(stack1);
        if (!nbt1.getString("mode_module" + 3).isEmpty()) {
            this.energy += energyConsume * operationLength;
            return;
        }
        EnumInfoUpgradeModules type = UpgradeModule.getType(module.getItemDamage());
        int min = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt1.getString("mode_module" + i).equals(type.name))
                min++;
        }
        if (min >= type.max) {
            this.energy += energyConsume * operationLength;
            return;
        }
        int Damage = stack1.getItemDamage();
        double newCharge = ElectricItem.manager.getCharge(stack1);
        Map enchantmentMap = EnchantmentHelper.getEnchantments(stack1);
        this.inputSlotA.consume();
        this.outputSlot.add(processResult);
        ItemStack stack = this.outputSlot.get();
        stack.setTagCompound(nbt1);
        NBTTagCompound nbt = ModUtils.nbt(stack);
        String mode = output.metadata.getString("mode_module");

        int k = 0;
        for (int i = 0; i < 4; i++) {
            if (nbt.getString("mode_module" + i).isEmpty()) {
                k = i;
                break;
            }
        }
        nbt.setString("mode_module" + k, mode);
        ElectricItem.manager.charge(stack, newCharge, Integer.MAX_VALUE, true, false);
        EnchantmentHelper.setEnchantments(enchantmentMap, stack);
        stack.setItemDamage(Damage);
    }
    public RecipeOutput getOutput() {
        if (this.inputSlotA.isEmpty())
            return null;

        RecipeOutput output = this.inputSlotA.process();

        if (output == null)
            return null;
        if (this.outputSlot.canAdd(output.items))
            return output;

        return null;
    }

    public String getStartSoundFile() {
        return "Machines/upgrade_block.ogg";
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
