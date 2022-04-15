package com.denfop.gui;

import com.denfop.Constants;
import com.denfop.api.Recipes;
import com.denfop.container.ContainerDoubleElectricMachine;
import com.denfop.item.modules.UpgradeModule;
import com.denfop.tiles.base.TileEntityUpgradeBlock;
import com.denfop.utils.EnumInfoUpgradeModules;
import com.denfop.utils.ModUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.recipe.RecipeOutput;
import ic2.core.GuiIC2;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import static com.denfop.events.IUEventHandler.getUpgradeItem;

@SideOnly(Side.CLIENT)
public class GUIUpgradeBlock extends GuiIC2 {
    public final ContainerDoubleElectricMachine<? extends TileEntityUpgradeBlock> container;

    public GUIUpgradeBlock(ContainerDoubleElectricMachine<? extends TileEntityUpgradeBlock> container1) {
        super(container1);
        this.container = container1;
    }

    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        super.drawGuiContainerForegroundLayer(par1, par2);

    }

    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        super.drawGuiContainerBackgroundLayer(f, x, y);
        int chargeLevel = (int) (14.0F * this.container.base.getChargeLevel());
        int progress = (int) (31 * this.container.base.getProgress());
        int progress1 = (int) (27 * this.container.base.getProgress());

        if (chargeLevel > 0)
            drawTexturedModalRect(this.xoffset + 24, this.yoffset + 56 + 14 - chargeLevel, 176, 14 - chargeLevel,
                    14, chargeLevel);

        RecipeOutput output = Recipes.upgrade.getOutputFor(this.container.base.inputSlotA.get(0), this.container.base.inputSlotA.get(1), false, false);

        if (output != null) {
            ItemStack stack1 = getUpgradeItem(this.container.base.inputSlotA.get(0)) ? this.container.base.inputSlotA.get(0) : this.container.base.inputSlotA.get(1);
            ItemStack module = !getUpgradeItem(this.container.base.inputSlotA.get(1)) ? this.container.base.inputSlotA.get(1) : this.container.base.inputSlotA.get(0);

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
            if (allow) {
                if (progress > 0)
                    drawTexturedModalRect(this.xoffset + 31, this.yoffset + 36, 176, 17, progress + 1, 11);
                if (progress1 > 0)
                    drawTexturedModalRect(this.xoffset + 81, this.yoffset + 34, 176, 29, progress1 + 1, 15);
            } else {
                drawTexturedModalRect(this.xoffset + 31, this.yoffset + 34, 176, 48, 31, 15);
                drawTexturedModalRect(this.xoffset + 81, this.yoffset + 34, 177, 69, 27, 16);


            }
        }
    }

    public String getName() {
        return this.container.base.getInventoryName();
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation(Constants.TEXTURES, "textures/gui/GUIUpgradeBlock.png");
    }
}
