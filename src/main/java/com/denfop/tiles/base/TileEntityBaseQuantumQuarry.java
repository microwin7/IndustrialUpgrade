package com.denfop.tiles.base;

import com.denfop.Config;
import com.denfop.IUCore;
import com.denfop.IUItem;
import com.denfop.Ic2Items;
import com.denfop.audio.AudioSource;
import com.denfop.audio.PositionSpec;
import com.denfop.componets.QEComponent;
import com.denfop.container.ContainerQuantumQuarry;
import com.denfop.gui.GUIQuantumQuarry;
import com.denfop.invslot.InvSlotQuantumQuarry;
import com.denfop.items.modules.EnumQuarryModules;
import com.denfop.items.modules.EnumQuarryType;
import com.denfop.utils.ModUtils;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.api.upgrade.IUpgradableBlock;
import ic2.api.upgrade.IUpgradeItem;
import ic2.api.upgrade.UpgradableProperty;
import ic2.core.ContainerBase;
import ic2.core.IC2;
import ic2.core.IHasGui;
import ic2.core.block.TileEntityInventory;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.init.Localization;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TileEntityBaseQuantumQuarry extends TileEntityInventory implements IHasGui, INetworkTileEntityEventListener,
        IUpgradableBlock {

    public final Random rand = new Random();
    public final int energyconsume;
    public final InvSlotQuantumQuarry inputslotB;
    public final InvSlotOutput outputSlot;
    public final InvSlotQuantumQuarry inputslot;
    public final InvSlotQuantumQuarry inputslotA;
    private final String name;
    public double consume;
    public boolean furnace;
    public int chance;
    public int col;
    public boolean vein;
    public List<ItemStack> list;
    public AudioSource audioSource;
    public double getblock;
    public QEComponent energy;
    public boolean analyzer;
    public int progress;
    public EnumQuarryModules list_modules;

    public TileEntityBaseQuantumQuarry(String name, int coef) {

        this.progress = 0;
        this.name = name;
        this.getblock = 0;
        this.energyconsume = 2000 * coef;
        this.energy = this.addComponent(QEComponent.asBasicSink(this, 5E7D, 14));
        this.inputslot = new InvSlotQuantumQuarry(this, 25, "input", 0);
        this.inputslotA = new InvSlotQuantumQuarry(this, 26, "input1", 1);
        this.inputslotB = new InvSlotQuantumQuarry(this, 27, "input2", 2);
        this.outputSlot = new InvSlotOutput(this, "output", 24);
        this.list = new ArrayList<>();
        this.analyzer = false;
        this.vein = false;
        this.chance = 0;
        this.col = 1;
        this.furnace = false;
        this.list_modules = null;
        this.consume = this.energyconsume;
    }


    public boolean list(TileEntityBaseQuantumQuarry tile, ItemStack stack1) {
        if (tile.list_modules == null) {
            return false;
        }
        return list(tile.list_modules, stack1);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.progress = nbttagcompound.getInteger("progress");
        this.col = nbttagcompound.getInteger("col");
        this.chance = nbttagcompound.getInteger("chance");
        this.getblock = nbttagcompound.getDouble("getblock");
        this.vein = nbttagcompound.getBoolean("vein");
        this.furnace = nbttagcompound.getBoolean("furnace");
        int type = nbttagcompound.getInteger("list_modules");
        if (type != -1) {
            this.list_modules = EnumQuarryModules.getFromID(type);
        }
        if (!this.inputslotA.isEmpty()) {
            if (this.list.isEmpty()) {
                this.list = ModUtils.getListFromModule(this.inputslotA.get());
            }
        }

    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setBoolean("vein", this.vein);
        nbttagcompound.setBoolean("furnace", this.furnace);
        nbttagcompound.setDouble("getblock", this.getblock);
        nbttagcompound.setInteger("progress", this.progress);
        nbttagcompound.setInteger("chance", this.chance);
        nbttagcompound.setInteger("col", this.col);

        if (this.list_modules != null) {
            nbttagcompound.setInteger("list_modules", this.list_modules.ordinal());
        } else {
            nbttagcompound.setInteger("list_modules", -1);

        }

        return nbttagcompound;
    }

    protected void initiate(int soundEvent) {
        IC2.network.get(true).initiateTileEntityEvent(this, soundEvent, true);
    }

    protected void onLoaded() {
        super.onLoaded();
        this.inputslot.update();
        this.inputslotA.update();
        this.inputslotB.update();
    }

    protected void onUnloaded() {
        super.onUnloaded();
        if (IC2.platform.isRendering() && this.audioSource != null) {
            IUCore.audioManager.removeSources(this);
            this.audioSource = null;
        }

    }


    @Override
    public void onPlaced(final ItemStack stack, final EntityLivingBase placer, final EnumFacing facing) {
        super.onPlaced(stack, placer, facing);
        int chunkx =
                this.getWorld().getChunkFromBlockCoords(this.pos).x * 16;
        int chunkz = this.getWorld().getChunkFromBlockCoords(this.pos).z * 16;
        if (getWorld().getTileEntity(new BlockPos(chunkx, 0, chunkz)) != null && getWorld().getTileEntity(new BlockPos(
                chunkx,
                0,
                chunkz
        )) instanceof TileEntityVein) {
            this.vein = true;
        }
    }

    protected void updateEntityServer() {
        super.updateEntityServer();
        double proccent = this.consume;


        if (this.analyzer && this.vein) {

            int chunkx =
                    this.getWorld().getChunkFromBlockCoords(this.pos).x * 16;
            int chunkz = this.getWorld().getChunkFromBlockCoords(this.pos).z * 16;

            if (this.getWorld().getTileEntity(new BlockPos(chunkx, 0, chunkz)) != null && this
                    .getWorld()
                    .getTileEntity(new BlockPos(chunkx, 0, chunkz)) instanceof TileEntityVein) {
                TileEntityVein tile = (TileEntityVein) this.getWorld().getTileEntity(new BlockPos(chunkx, 0, chunkz));
                assert tile != null;
                if (tile.number > 0) {
                    if (this.energy.getEnergy() >= proccent && this.outputSlot.canAdd(new ItemStack(IUItem.heavyore, 1,
                                    this
                                            .getWorld()
                                            .getBlockState(new BlockPos(chunkx, 0, chunkz))
                                            .getBlock()
                                            .getMetaFromState(this.getWorld().getBlockState(new BlockPos(chunkx, 0, chunkz)))
                            )
                    )) {
                        final ItemStack stack = new ItemStack(IUItem.heavyore, 1,
                                this
                                        .getWorld()
                                        .getBlockState(new BlockPos(chunkx, 0, chunkz))
                                        .getBlock()
                                        .getMetaFromState(this.getWorld().getBlockState(new BlockPos(chunkx, 0, chunkz)))
                        );
                        if (!list(this.list_modules, stack)) {
                            this.setActive(true);
                            this.energy.useEnergy(proccent);
                            this.getblock++;
                            this.outputSlot.add(stack);
                            tile.number--;
                        }
                    }

                }
            }


        }
        if (this.analyzer && !vein && !Config.enableonlyvein) {
            double col = this.col;
            int chance2 = this.chance;
            boolean furnace = this.furnace;
            EnumQuarryModules list_check = this.list_modules;

            int coble = rand.nextInt((int) col + 1);
            this.getblock += coble;
            col -= coble;
            for (double i = 0; i < col; i++) {
                if (this.energy.getEnergy() >= proccent) {
                    if(!this.getActive()) {
                        this.setActive(true);
                        initiate(0);
                    }
                    this.energy.useEnergy(proccent);
                    this.getblock++;


                    if (furnace) {
                        List<ItemStack> list = IUCore.get_ingot;
                        int num = list.size();
                        int chance1 = rand.nextInt(num);
                        if (!list(list_check, list.get(chance1))) {
                            if (this.outputSlot.canAdd(list.get(chance1))) {
                                this.outputSlot.add(list.get(chance1));
                            }
                        }
                    } else {
                        List<ItemStack> list = IUCore.list;
                        int num = list.size();
                        int chance1 = rand.nextInt(num);
                        if (!list(list_check, list.get(chance1))) {
                            if (OreDictionary.getOreIDs(list.get(chance1)).length > 0) {
                                if ((!OreDictionary
                                        .getOreName(OreDictionary.getOreIDs(list.get(chance1))[0])
                                        .startsWith("gem") && !OreDictionary
                                        .getOreName(OreDictionary.getOreIDs(list.get(chance1))[0])
                                        .startsWith("shard")
                                        && list.get(chance1).getItem() != Items.REDSTONE && list
                                        .get(chance1)
                                        .getItem() != Items.DYE && list.get(chance1).getItem() != Items.COAL && list
                                        .get(chance1)
                                        .getItem() != Items.GLOWSTONE_DUST) && chance2 >= 0) {
                                    if (this.outputSlot.canAdd(list.get(chance1))) {
                                        this.outputSlot.add(list.get(chance1));
                                    }
                                } else {
                                    for (int j = 0; j < chance2 + 1; j++) {
                                        if (this.outputSlot.canAdd(list.get(chance1))) {
                                            this.outputSlot.add(list.get(chance1));
                                        }
                                    }
                                }
                            }
                        }
                    }

                } else {
                    this.setActive(false);
                    initiate(2);
                }
            }
        } else {
            initiate(2);
            this.setActive(false);
        }

        if (getActive()) {
            if (this.world.getWorldTime() % 2 == 0) {
                ItemStack stack3 = Ic2Items.ejectorUpgrade;
                ((IUpgradeItem) stack3.getItem()).onTick(stack3, this);
            }
        }
    }

    public ContainerBase<? extends TileEntityBaseQuantumQuarry> getGuiContainer(EntityPlayer player) {
        return new ContainerQuantumQuarry(player, this);

    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer player, boolean isAdmin) {

        return new GUIQuantumQuarry(new ContainerQuantumQuarry(player, this));
    }

    public String getStartSoundFile() {
        return "Machines/quarry.ogg";
    }

    public String getInterruptSoundFile() {
        return "Machines/InterruptOne.ogg";
    }

    public void onNetworkEvent(int event) {
        if (this.audioSource == null && this.getStartSoundFile() != null) {
            this.audioSource = IUCore.audioManager.createSource(this, this.getStartSoundFile());
        }

        switch (event) {
            case 0:
                if (this.audioSource != null) {
                    this.audioSource.play();
                }
                break;
            case 1:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                    if (this.getInterruptSoundFile() != null) {
                        IUCore.audioManager.playOnce(
                                this,
                                PositionSpec.Center,
                                this.getInterruptSoundFile(),
                                false,
                                IUCore.audioManager.getDefaultVolume()
                        );
                    }
                }
                break;
            case 2:
                if (this.audioSource != null) {
                    this.audioSource.stop();
                }
            case 3:
        }

    }

    public void onGuiClosed(EntityPlayer player) {
    }

    public boolean list(EnumQuarryModules type, ItemStack stack1) {
        if (type == null) {
            return false;
        }
        if (type.type == EnumQuarryType.BLACKLIST) {

            return this.list.contains(stack1);


        } else if (type.type == EnumQuarryType.WHITELIST) {

            return !this.list.contains(stack1);

        }
        return false;
    }

    public String getInventoryName() {

        return Localization.translate(name);
    }

    @Override
    public double getEnergy() {
        return 0;
    }

    @Override
    public boolean useEnergy(final double v) {
        return false;
    }

    @Override
    public Set<UpgradableProperty> getUpgradableProperties() {
        return EnumSet.of(
                UpgradableProperty.ItemProducing
        );
    }

}
