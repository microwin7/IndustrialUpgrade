package com.denfop.tiles.mechanism;

import com.denfop.IUItem;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.gui.GUIEnriched;
import com.denfop.recipemanager.DoubleMachineRecipeManager;
import com.denfop.tiles.base.EnumDoubleElectricMachine;
import com.denfop.tiles.base.TileEntityDoubleElectricMachine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.RecipeOutput;
import ic2.core.Ic2Items;
import ic2.core.upgrade.UpgradableProperty;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class TileEntityEnrichment extends TileEntityDoubleElectricMachine {

    public TileEntityEnrichment() {
        super(1, 300, 1, StatCollector.translateToLocal("iu.enrichment.name"), EnumDoubleElectricMachine.ENRICH);
    }

    public static void init() {
        Recipes.enrichment = new DoubleMachineRecipeManager();
        addenrichment(new ItemStack(IUItem.toriy), new ItemStack(Items.glowstone_dust), new ItemStack(IUItem.radiationresources, 1, 4));
        addenrichment(new ItemStack(IUItem.itemIU, 1, 2), new ItemStack(Blocks.glowstone, 1), new ItemStack(IUItem.itemIU, 1, 0));
        addenrichment(new ItemStack(IUItem.itemIU, 1, 0), Ic2Items.reinforcedGlass, new ItemStack(IUItem.itemIU, 2, 1));
        addenrichment(new ItemStack(IUItem.Helium, 1), new ItemStack(IUItem.cell_all, 1), new ItemStack(IUItem.cell_all, 4, 2));
        addenrichment(new ItemStack(IUItem.sunnarium, 1, 3), new ItemStack(IUItem.itemIU, 1, 0), new ItemStack(IUItem.sunnarium, 1, 0));

    }

    public static void addenrichment(ItemStack container, ItemStack fill, ItemStack output) {
        Recipes.enrichment.addRecipe(new RecipeInputItemStack(container), new RecipeInputItemStack(fill), null, output);

    }

    public String getInventoryName() {

        return StatCollector.translateToLocal("iu.enrichment.name");
    }

    @Override
    public void operateOnce(RecipeOutput output, List<ItemStack> processResult) {
        this.inputSlotA.consume();
        this.outputSlot.add(processResult);
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIEnriched(new ContainerDoubleElectricMachine(entityPlayer, this, type));
    }

    public String getStartSoundFile() {
        return "Machines/enrichment.ogg";
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
