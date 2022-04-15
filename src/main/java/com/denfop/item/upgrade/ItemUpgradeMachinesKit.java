package com.denfop.item.upgrade;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.tiles.base.TileEntityMultiMachine;
import com.denfop.tiles.mechanism.EnumMultiMachine;
import com.denfop.tiles.mechanism.EnumUpgradesMultiMachine;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.block.machine.tileentity.TileEntityElectricFurnace;
import ic2.core.block.machine.tileentity.TileEntityElectricMachine;
import ic2.core.block.machine.tileentity.TileEntityStandardMachine;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ItemUpgradeMachinesKit extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;


    public ItemUpgradeMachinesKit() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "upgradekitMachineIU");
    }


    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!IC2.platform.isSimulating()) {
            return false;
        } else {

            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityElectricMachine) {
                TileEntityElectricMachine tile = (TileEntityElectricMachine) tileEntity;
                String name = "ic2.block" + tile.getInventoryName();
                if (tileEntity instanceof TileEntityElectricFurnace)
                    name = "ElecFurnace";
                if (IUItem.map3.containsKey(name)) {
                    EnumUpgradesMultiMachine machine = IUItem.map3.get(name);
                    Block block = machine.block_new;
                    if (stack.getItemDamage() == machine.meta_item) {
                        ItemStack[] stack_list = new ItemStack[((TileEntityStandardMachine) tileEntity).upgradeSlot.size()];
                        for (int i = 0; i < ((TileEntityStandardMachine) tileEntity).upgradeSlot.size(); i++) {
                            stack_list[i] = ((TileEntityStandardMachine) tileEntity).upgradeSlot.get(i);
                        }
                        short facing = tile.getFacing();
                        int meta = machine.meta_new;
                        world.setBlock(x, y, z, block, meta, 3);
                        TileEntityMultiMachine tile1 = (TileEntityMultiMachine) world.getTileEntity(x, y, z);
                        tile1.setFacing(facing);
                        for (int i = 0; i < tile1.upgradeSlot.size(); i++) {
                            if (stack_list[i] != null)
                                tile1.upgradeSlot.put(i, stack_list[i]);
                        }
                        stack.stackSize--;
                        return true;
                    }
                } else {
                    if (tileEntity instanceof TileEntityMultiMachine) {
                        TileEntityMultiMachine tile1 = (TileEntityMultiMachine) tileEntity;

                        EnumMultiMachine type = tile1.getMachine();
                        if (type.upgrade == stack.getItemDamage()) {
                            if (type.block_new != null) {
                                ItemStack[] stack_list = new ItemStack[((TileEntityMultiMachine) tileEntity).upgradeSlot.size()];
                                for (int i = 0; i < ((TileEntityMultiMachine) tileEntity).upgradeSlot.size(); i++) {
                                    stack_list[i] = ((TileEntityMultiMachine) tileEntity).upgradeSlot.get(i);
                                }

                                Block block = type.block_new;
                                int meta = type.meta_new;
                                int module = tile1.module;
                                boolean rf = tile1.rf;
                                boolean quickly = tile1.quickly;
                                boolean modulesize = tile1.modulesize;
                                short facing = tile.getFacing();

                                world.setBlock(x, y, z, block, meta, 3);
                                TileEntityMultiMachine tile2 = (TileEntityMultiMachine) world.getTileEntity(x, y, z);
                                tile2.setFacing(facing);
                                for (int i = 0; i < tile2.upgradeSlot.size(); i++) {
                                    if (stack_list[i] != null)
                                        tile2.upgradeSlot.put(i, stack_list[i]);
                                }

                                tile2.rf = rf;
                                tile2.module = module;
                                tile2.quickly = quickly;
                                tile2.modulesize = modulesize;
                                stack.stackSize--;
                                return true;


                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public String getUnlocalizedName(final ItemStack stack) {
        return this.itemNames.get(stack.getItemDamage());
    }

    public IIcon getIconFromDamage(final int par1) {
        return this.IIconsList[par1];
    }

    public void addItemsNames() {
        this.itemNames.add("upgrademachinekit");
        this.itemNames.add("upgrademachinekit1");
        this.itemNames.add("upgrademachinekit2");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            final ItemStack stack = new ItemStack(this, 1, meta);
            itemList.add(stack);
        }
    }


}
