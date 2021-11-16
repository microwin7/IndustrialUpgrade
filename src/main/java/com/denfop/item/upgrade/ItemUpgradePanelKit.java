package com.denfop.item.upgrade;

import com.denfop.Config;
import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.integration.avaritia.AvaritiaIntegration;
import com.denfop.integration.botania.BotaniaIntegration;
import com.denfop.integration.de.DraconicIntegration;
import com.denfop.integration.thaumcraft.ThaumcraftIntegration;
import com.denfop.integration.thaumtinker.ThaumTinkerIntegration;
import com.denfop.tiles.base.TileEntitySolarPanel;
import com.denfop.tiles.overtimepanel.EnumSolarPanels;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.core.IC2;
import ic2.core.Ic2Items;
import ic2.core.block.generator.tileentity.TileEntitySolarGenerator;
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

public class ItemUpgradePanelKit extends Item {
    private final List<String> itemNames;
    private IIcon[] IIconsList;


    public ItemUpgradePanelKit() {
        this.itemNames = new ArrayList<>();

        this.setHasSubtypes(true);
        this.setCreativeTab(IUCore.tabssp3);
        this.setMaxStackSize(64);
        this.addItemsNames();
        GameRegistry.registerItem(this, "upgradekitPanelIU");
    }


    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!IC2.platform.isSimulating()) {
            return false;
        } else {
            int meta = stack.getItemDamage();
            TileEntity tileEntity = world.getTileEntity(x, y, z);
            if ((tileEntity instanceof TileEntitySolarPanel) && stack.getItemDamage() > 0) {

                TileEntitySolarPanel tile = (TileEntitySolarPanel) tileEntity;
                if (tile.getPanels() == null)
                    return false;
                EnumSolarPanels oldpanel = tile.getPanels();
                EnumSolarPanels kit = IUItem.map1.get(meta);
                if (!kit.solarold.equals(oldpanel))
                    return false;
                ItemStack[] items = new ItemStack[tile.getSizeInventory()];
                for (int i = 0; i < items.length; ++i) {
                    items[i] = tile.getStackInSlot(i);
                }

                if (kit.register) {
                    for (int i = 0; i < items.length; ++i) {
                        tile.setInventorySlotContents(i, null);
                    }
                    if (meta < 14)
                        world.setBlock(x, y, z, kit.block, kit.meta, 2);
                    if (meta > 13 && meta < 17)
                        world.setBlock(x, y, z, DraconicIntegration.blockDESolarPanel, kit.meta, 2);
                    if (meta > 16 && meta < 20)
                        world.setBlock(x, y, z, BotaniaIntegration.blockBotSolarPanel, kit.meta, 2);
                    if (meta > 19 && meta < 22)
                        world.setBlock(x, y, z, AvaritiaIntegration.blockAvSolarPanel, kit.meta, 2);
                    if (meta > 21 && meta < 24)
                        world.setBlock(x, y, z, ThaumcraftIntegration.blockThaumSolarPanel, kit.meta, 2);
                    if (meta > 23)
                        world.setBlock(x, y, z, ThaumTinkerIntegration.blockThaumTinkerSolarPanel, kit.meta, 2);


                    --stack.stackSize;
                    TileEntity newtileEntity = world.getTileEntity(x, y, z);
                    TileEntitySolarPanel tilenew = (TileEntitySolarPanel) newtileEntity;
                    for (int i = 0; i < items.length; ++i) {
                        tilenew.setInventorySlotContents(i, items[i]);
                    }
                    return true;
                }

            } else if (tileEntity instanceof TileEntitySolarGenerator) {
                if (stack.getItemDamage() == 0 && world.getBlockMetadata(x, y, z) == Ic2Items.solarPanel.getItemDamage()) {
                    world.setBlock(x, y, z, IUItem.blockpanel, 0, 2);
                    --stack.stackSize;
                }
                return true;
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
        this.itemNames.add("upgradepanelkit");
        this.itemNames.add("upgradepanelkit1");
        this.itemNames.add("upgradepanelkit2");
        this.itemNames.add("upgradepanelkit3");
        this.itemNames.add("upgradepanelkit4");
        this.itemNames.add("upgradepanelkit5");
        this.itemNames.add("upgradepanelkit6");
        this.itemNames.add("upgradepanelkit7");
        this.itemNames.add("upgradepanelkit8");
        this.itemNames.add("upgradepanelkit9");
        this.itemNames.add("upgradepanelkit10");
        this.itemNames.add("upgradepanelkit11");
        this.itemNames.add("upgradepanelkit12");
        this.itemNames.add("upgradepanelkit13");
        this.itemNames.add("upgradepanelkit14");
        this.itemNames.add("upgradepanelkit15");
        this.itemNames.add("upgradepanelkit16");
        this.itemNames.add("upgradepanelkit17");
        this.itemNames.add("upgradepanelkit18");
        this.itemNames.add("upgradepanelkit19");
        this.itemNames.add("upgradepanelkit20");
        this.itemNames.add("upgradepanelkit21");
        this.itemNames.add("upgradepanelkit22");
        this.itemNames.add("upgradepanelkit23");
        this.itemNames.add("upgradepanelkit24");
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(final IIconRegister IIconRegister) {
        this.IIconsList = new IIcon[itemNames.size()];
        for (int i = 0; i < itemNames.size(); i++)
            this.IIconsList[i] = IIconRegister.registerIcon(Constants.TEXTURES_MAIN + itemNames.get(i));

    }

    public void getSubItems(final Item item, final CreativeTabs tabs, final List itemList) {
        for (int meta = 0; meta <= this.itemNames.size() - 1; ++meta) {
            if (meta == 0) {
                final ItemStack stack = new ItemStack(this, 1, meta);
                itemList.add(stack);
            } else {
                if (EnumSolarPanelsKit.values()[meta - 1].register) {
                    final ItemStack stack = new ItemStack(this, 1, meta);
                    itemList.add(stack);
                }
            }
        }
    }

    public enum EnumSolarPanelsKit {
        HYBRID(EnumSolarPanels.HYBRID_SOLAR_PANEL, 1, true),
        PERFECT(EnumSolarPanels.PERFECT_SOLAR_PANEL, 2, true),
        QUANTUM(EnumSolarPanels.QUANTUM_SOLAR_PANEL, 3, true),
        SPECTRAL(EnumSolarPanels.SPECTRAL_SOLAR_PANEL, 4, true),
        PROTON(EnumSolarPanels.PROTON_SOLAR_PANEL, 5, true),
        SINGULAR(EnumSolarPanels.SINGULAR_SOLAR_PANEL, 6, true),
        DIFFRACTION(EnumSolarPanels.DIFFRACTION_SOLAR_PANEL, 7, true),
        PHOTON(EnumSolarPanels.PHOTONIC_SOLAR_PANEL, 8, true),
        NEUTRONIUM(EnumSolarPanels.NEUTRONIUN_SOLAR_PANEL, 9, true),
        BARION(EnumSolarPanels.BARION_SOLAR_PANEL, 10, true),
        HADRON(EnumSolarPanels.HADRON_SOLAR_PANEL, 11, true),
        GRAVITON(EnumSolarPanels.GRAVITON_SOLAR_PANEL, 12, true),
        KVARK(EnumSolarPanels.KVARK_SOLAR_PANEL, 13, true),
        DRACONIC(EnumSolarPanels.DRACONIC_SOLAR_PANEL, 14, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic),
        AWAKENED(EnumSolarPanels.AWAKENED_SOLAR_PANEL, 15, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic),
        CHAOTIC(EnumSolarPanels.AWAKENED_SOLAR_PANEL, 16, Config.registerDraconicPanels && Config.DraconicLoaded && Config.Draconic),
        MANASTEEL(EnumSolarPanels.MANASTEEL_SOLAR_PANEL, 17, Config.BotaniaLoaded && Config.Botania),
        ELEMENTIUM(EnumSolarPanels.ELEMENTUM_SOLAR_PANEL, 18, Config.BotaniaLoaded && Config.Botania),
        TERRASTEEL(EnumSolarPanels.TERRASTEEL_SOLAR_PANEL, 19, Config.BotaniaLoaded && Config.Botania),
        NEUTRONIUM_AVARITIA(EnumSolarPanels.NEUTRONIUM_SOLAR_PANEL_AVARITIA, 20, Config.AvaritiaLoaded && Config.Avaritia),
        INFINITY(EnumSolarPanels.INFINITY_SOLAR_PANEL, 21, Config.AvaritiaLoaded && Config.Avaritia),
        THAUM(EnumSolarPanels.THAUM_SOLAR_PANEL, 22, Config.thaumcraft && Config.Thaumcraft),
        VOID(EnumSolarPanels.VOID_SOLAR_PANEL, 23, Config.thaumcraft && Config.Thaumcraft),
        ICHOR(EnumSolarPanels.IHOR_SOLAR_PANEL, 24, Config.thaumcraft && Config.Thaumcraft && Loader.isModLoaded("ThaumicTinkerer")),
        ;

        public final int item_meta;
        public final EnumSolarPanels solarpanel_new;
        public final boolean register;

        EnumSolarPanelsKit(EnumSolarPanels solarpanel_new, int item_meta, boolean register) {
            this.item_meta = item_meta;
            this.solarpanel_new = solarpanel_new;
            this.register = register;

        }

        public static void registerkit() {
            for (EnumSolarPanelsKit machine : EnumSolarPanelsKit.values()) {
                IUItem.map1.put(machine.item_meta, machine.solarpanel_new);

            }
        }
    }
}
