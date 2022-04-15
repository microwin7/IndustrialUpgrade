package com.denfop.tiles.base;

import cofh.api.energy.IEnergyHandler;
import com.denfop.Config;
import com.denfop.IUItem;
import com.denfop.container.ContainerAutoSpawner;
import com.denfop.gui.GUIAutoSpawner;
import com.denfop.invslot.InvSlotBook;
import com.denfop.invslot.InvSlotModules;
import com.denfop.invslot.InvSlotUpgradeModule;
import com.denfop.item.modules.EnumSpawnerModules;
import com.denfop.item.modules.EnumSpawnerType;
import com.denfop.item.modules.ItemEntityModule;
import com.denfop.item.modules.SpawnerModules;
import com.denfop.tiles.mechanism.TileEntityMagnet;
import com.denfop.utils.Enchant;
import com.denfop.utils.FakePlayerSpawner;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.Direction;
import ic2.api.network.INetworkTileEntityEventListener;
import ic2.core.ContainerBase;
import ic2.core.IHasGui;
import ic2.core.block.invslot.InvSlot;
import ic2.core.block.invslot.InvSlotOutput;
import ic2.core.util.StackUtil;
import ic2.core.util.Util;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class TileEntityAutoSpawner extends TileEntityElectricMachine
        implements IHasGui, INetworkTileEntityEventListener, IEnergyHandler {
    public final InvSlotOutput outputSlot;
    public final InvSlotModules module_slot;
    public final InvSlotBook book_slot;
    public final int expmaxstorage;
    public final int maxprogress;
    public final InvSlotUpgradeModule module_upgrade;
    public final int tempcostenergy;
    public final int[] progress;
    public final double maxEnergy2;
    public int costenergy;
    public int tempprogress;
    public int expstorage;
    public FakePlayerSpawner player;
    public double energy2;

    public TileEntityAutoSpawner() {
        super(150000, 14, -1);
        this.outputSlot = new InvSlotOutput(this, "output", 2, 27);
        this.module_slot = new InvSlotModules(this);
        this.book_slot = new InvSlotBook(this);
        this.module_upgrade = new InvSlotUpgradeModule(this);
        progress = new int[module_slot.size()];
        this.maxEnergy2 = 50000 * Config.coefficientrf;
        this.expmaxstorage = 15000;
        this.maxprogress = 100;
        this.tempprogress = 100;
        this.tempcostenergy = 900;
        this.costenergy = 900;
    }

    private static List<StackUtil.AdjacentInv> getTargetInventories(TileEntity parent) {
        return StackUtil.getAdjacentInventories(parent);
    }

    public static int transfer(IInventory src, IInventory dst, Direction dir, int amount) {
        int[] srcSlots = StackUtil.getInventorySlots(src, dir, false, true);
        int[] dstSlots = StackUtil.getInventorySlots(dst, dir.getInverse(), true, false);
        ISidedInventory dstSided = dst instanceof ISidedInventory ? (ISidedInventory) dst : null;
        int dstVanillaSide = dir.getInverse().toSideValue();
        label126:
        for (int srcSlot : srcSlots) {
            ItemStack srcStack = src.getStackInSlot(srcSlot);
            if (srcStack != null && !(srcStack.getItem() instanceof ItemEntityModule || srcStack.getItem() instanceof ItemEnchantedBook || srcStack.getItem() instanceof SpawnerModules)) {

                int srcTransfer = Math.min(amount, srcStack.stackSize);

                assert srcTransfer > 0;

                for (int pass = 0; pass < 2; ++pass) {
                    for (int i = 0; i < dstSlots.length; ++i) {

                        int dstSlot = dstSlots[i];
                        if (dstSlot >= 0) {
                            ItemStack dstStack = dst.getStackInSlot(dstSlot);
                            if ((pass != 0 || dstStack != null && StackUtil.isStackEqualStrict(srcStack, dstStack)) && (pass != 1 || dstStack == null) && dst.isItemValidForSlot(dstSlot, srcStack) && (dstSided == null || dstSided.canInsertItem(dstSlot, srcStack, dstVanillaSide))) {
                                assert srcTransfer > 0;

                                int transfer;
                                if (dstStack == null) {
                                    transfer = Math.min(srcTransfer, dst.getInventoryStackLimit());
                                    dst.setInventorySlotContents(dstSlot, StackUtil.copyWithSize(srcStack, transfer));
                                } else {
                                    transfer = Math.min(srcTransfer, Math.min(dstStack.getMaxStackSize(), dst.getInventoryStackLimit()) - dstStack.stackSize);
                                    if (transfer <= 0) {
                                        dstSlots[i] = -1;
                                        continue;
                                    }

                                    dstStack.stackSize += transfer;
                                }

                                assert transfer > 0;

                                srcStack.stackSize -= transfer;
                                amount -= transfer;
                                srcTransfer -= transfer;
                                if (srcTransfer <= 0) {
                                    if (srcStack.stackSize <= 0) {
                                        src.setInventorySlotContents(srcSlot, null);
                                    }

                                    if (amount <= 0) {
                                        break label126;
                                    }
                                    continue label126;
                                }

                                assert srcStack.stackSize > 0;

                                assert amount > 0;
                            }
                        }
                    }
                }
            }
        }

        amount -= amount;

        assert amount >= 0;

        if (amount > 0) {
            src.markDirty();
            dst.markDirty();
        }

        return amount;
    }

    private InvSlot getInvSlot(int index) {
        InvSlot invSlot;
        for (Iterator var2 = this.invSlots.iterator(); var2.hasNext(); index -= invSlot.size()) {
            invSlot = (InvSlot) var2.next();
            if (index < invSlot.size()) {
                return invSlot;
            }
        }

        return null;
    }

    public boolean canExtractItem(int index, ItemStack itemStack, int side) {
        if (index >= 28)
            return false;
        InvSlot targetSlot = getInvSlot(index);

        if (targetSlot == null) {
            return false;
        }

        if (!targetSlot.canOutput()) {
            return false;
        } else {
            boolean correctSide = targetSlot.preferredSide.matches(side);
            if (targetSlot.preferredSide != InvSlot.InvSide.ANY && correctSide) {
                return true;
            } else {
                Iterator var6 = this.invSlots.iterator();

                InvSlot invSlot;
                do {
                    do {
                        do {
                            if (!var6.hasNext()) {
                                return true;
                            }

                            invSlot = (InvSlot) var6.next();
                        } while (invSlot == targetSlot);
                    } while (invSlot.preferredSide == InvSlot.InvSide.ANY && correctSide);
                } while (!invSlot.preferredSide.matches(side) || !invSlot.canOutput());

                return false;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public GuiScreen getGui(EntityPlayer entityPlayer, boolean isAdmin) {
        return new GUIAutoSpawner(new ContainerAutoSpawner(entityPlayer, this));
    }

    public ContainerBase<? extends TileEntityAutoSpawner> getGuiContainer(EntityPlayer entityPlayer) {
        return (ContainerBase<? extends TileEntityAutoSpawner>) new ContainerAutoSpawner(entityPlayer, this);
    }

    public void updateEntityServer() {

        super.updateEntityServer();
        if (worldObj.provider.getWorldTime() % 200 == 0) {
            for (Direction direction : Direction.directions) {
                TileEntity target = direction.applyToTileEntity(this);
                if (target instanceof TileEntityStorageExp) {
                    TileEntityStorageExp target1 = (TileEntityStorageExp) target;
                    if (target1.storage + this.expstorage > target1.maxStorage) {
                        expstorage -= (target1.maxStorage - target1.storage);
                        target1.storage = target1.maxStorage;

                    } else if (target1.storage1 + this.expstorage > target1.maxStorage && !target1.inputSlot.isEmpty()) {
                        expstorage -= (target1.maxStorage - target1.storage1);
                        target1.storage1 = target1.maxStorage;
                    } else {
                        if (target1.storage < target1.maxStorage) {
                            target1.storage += expstorage;
                            expstorage = 0;
                        } else if (target1.storage1 < target1.maxStorage && !target1.inputSlot.isEmpty()) {
                            target1.storage1 += expstorage;
                            expstorage = 0;
                        }
                    }
                }
            }
        }

        if (worldObj.provider.getWorldTime() % 200 == 0) {
            int amount;
            Iterator var6;
            StackUtil.AdjacentInv inv;
            int cAmount;
            amount = Util.saturatedCast(this.energy) / 20;
            for (int i = 0; i < this.outputSlot.size(); i++) {
                if (this.outputSlot.get(i) == null)
                    continue;
                var6 = getTargetInventories(this).iterator();

                while (var6.hasNext()) {
                    inv = (StackUtil.AdjacentInv) var6.next();
                    cAmount = transfer(this, inv.inv, inv.dir, amount);
                    if (cAmount > 0) {
                        amount -= cAmount;
                    }
                }
            }
            this.outputSlot.onChanged();
        }
        int speed = 0;
        int chance = 0;
        int spawn = 1;
        int experience = 0;
        this.costenergy = this.tempcostenergy;
        for (int i = 0; i < module_upgrade.size(); i++) {
            if (module_upgrade.get(i) != null) {
                EnumSpawnerModules module = IUItem.map4.get(module_upgrade.get(i).getItemDamage());
                EnumSpawnerType type = module.type;
                switch (type) {
                    case SPAWN:
                        spawn += module.percent;
                        if (spawn <= 4)
                            this.costenergy *= module.percent;
                        break;
                    case LUCKY:
                        chance += module.percent;
                        if (chance <= 3)
                            this.costenergy += module.percent * this.costenergy * 0.2;
                        break;
                    case SPEED:
                        speed += module.percent;
                        if (speed <= 80)
                            this.costenergy += module.percent * this.costenergy / 100;
                        break;
                    case EXPERIENCE:
                        experience += module.percent;
                        if (experience <= 100)
                            this.costenergy += (module.percent * this.costenergy) / 100;
                        break;
                }
            }
        }
        chance = Math.min(3, chance);
        spawn = Math.min(4, spawn);
        speed = Math.min(80, speed);
        experience = Math.min(100, experience);
        for (int i = 0; i < module_slot.size(); i++) {
            if (module_slot.get(i) != null) {
                if (this.energy >= costenergy || this.energy2 >= costenergy * Config.coefficientrf) {
                    progress[i]++;
                    if (this.energy >= costenergy)
                        this.energy -= costenergy;
                    else {
                        this.energy2 -= costenergy * Config.coefficientrf;
                    }
                }
                tempprogress = maxprogress - speed;
                if (progress[i] >= tempprogress) {
                    progress[i] = 0;
                    if (this.player == null)
                        this.player = new FakePlayerSpawner(getWorldObj());
                    String name = module_slot.get(i).stackTagCompound.getString("id");

                    if (Config.EntityList.contains(name))
                        return;
                    Entity entity = EntityList.createEntityByName(name, this.worldObj);
                    if (module_slot.get(i).stackTagCompound.getInteger("type") != 0)
                        if (entity instanceof EntitySheep)
                            ((EntitySheep) entity).setFleeceColor(module_slot.get(i).stackTagCompound.getInteger("type"));
                    if (entity instanceof EntitySkeleton)
                        ((EntitySkeleton) entity).setSkeletonType(module_slot.get(i).stackTagCompound.getInteger("type"));
                    if (Loader.isModLoaded("EnderIO"))
                        if (entity instanceof EntitySkeleton)
                            entity = SkeletonFix.init((EntitySkeleton) entity);
                    if (!Config.SkeletonType)
                        if (entity instanceof EntitySkeleton) {
                            ((EntitySkeleton) entity).setSkeletonType(0);
                        }

                    for (int j = 0; j < spawn; j++) {
                        entity.setWorld(this.worldObj);

                        entity.setLocationAndAngles((xCoord), (yCoord), (zCoord), (getWorldObj()).rand.nextFloat() * 360.0F, 0.0F);
                        int fireAspect = getEnchant(20);
                        int loot = getEnchant(21);
                        int reaper = getEnchant(180);
                        ItemStack stack = new ItemStack(Items.enchanted_book);
                        if (Config.DraconicLoaded)
                            Enchant.addEnchant(stack, reaper);
                        this.player.fireAspect = fireAspect;
                        this.player.loot = loot;
                        this.player.loot += chance;
                        stack.addEnchantment(Enchantment.looting, this.player.loot);
                        stack.addEnchantment(Enchantment.fireAspect, this.player.fireAspect);

                        this.player.setCurrentItemOrArmor(0, stack);
                        if (entity instanceof EntityBlaze) {
                            Random rand = worldObj.rand;
                            int m = rand.nextInt(2 + this.player.loot);

                            for (int k = 0; k < m; ++k) {
                                entity.dropItem(Items.blaze_rod, 1);
                            }

                        }

                        ((EntityLivingBase) entity).onDeath(DamageSource.causePlayerDamage(this.player));
                        int exp = 1 + worldObj.rand.nextInt(3);
                        if (expstorage + exp >= expmaxstorage) {
                            expstorage = expmaxstorage;
                        } else {
                            expstorage += (exp + experience * exp / 100);
                        }

                        if (worldObj.provider.getWorldTime() % 10 == 0)
                            for (int x = this.xCoord - 10; x <= this.xCoord + 10; x++)
                                for (int y = this.yCoord - 10; y <= this.yCoord + 10; y++)
                                    for (int z = this.zCoord - 10; z <= this.zCoord + 10; z++)
                                        if (worldObj.getTileEntity(x, y, z) != null)
                                            if (worldObj.getTileEntity(x, y, z) instanceof TileEntityMagnet)
                                                if (worldObj.getTileEntity(x, y, z) != this)
                                                    return;

                    }
                    int radius = 2;
                    AxisAlignedBB axisalignedbb = AxisAlignedBB.getBoundingBox(this.xCoord - radius, this.yCoord - radius, this.zCoord - radius, this.xCoord + radius, this.yCoord + radius, this.zCoord + radius);
                    List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, axisalignedbb);
                    for (EntityItem item : list) {

                        ItemStack drop = item.getEntityItem();
                        ItemStack smelt = null;
                        if (player.fireAspect > 0) {
                            smelt = FurnaceRecipes.smelting().getSmeltingResult(drop);
                            if (smelt != null)
                                smelt.stackSize = drop.stackSize;
                        }
                        if (smelt == null) {
                            if (this.outputSlot.canAdd(drop)) {
                                this.outputSlot.add(drop);
                            }
                        } else {
                            if (this.outputSlot.canAdd(smelt)) {
                                this.outputSlot.add(smelt);
                            }
                        }
                        item.setDead();
                    }

                }
            }
        }
    }

    @Override
    public void onNetworkEvent(int i) {

    }

    public int getEnchant(int enchantID) {
        for (int i = 0; i < this.book_slot.size(); i++) {
            if (this.book_slot.get(i) == null)
                continue;
            ItemStack stack = this.book_slot.get(i);
            if (stack.getItem() instanceof ItemEnchantedBook && stack.stackTagCompound != null) {
                NBTTagList bookNBT = ((ItemEnchantedBook) stack.getItem()).func_92110_g(stack);
                if (bookNBT.tagCount() == 1) {
                    short id = bookNBT.getCompoundTagAt(0).getShort("id");
                    if (id == enchantID)
                        return bookNBT.getCompoundTagAt(0).getShort("lvl");
                }
            }
        }
        return 0;
    }


    @Override
    public void onGuiClosed(EntityPlayer entityPlayer) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection paramForgeDirection) {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

        if (this.energy2 >= this.maxEnergy2)
            return 0;
        if (this.energy2 + maxReceive > this.maxEnergy2) {
            int energyReceived = (int) (this.maxEnergy2 - this.energy2);
            if (!simulate) {
                this.energy2 = this.maxEnergy2;
            }
            return energyReceived;
        }
        if (!simulate) {

            this.energy2 += maxReceive;
        }
        return maxReceive;
    }

    @Override
    public int extractEnergy(ForgeDirection paramForgeDirection, int paramInt, boolean paramBoolean) {
        return 0;
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        nbttagcompound.setDouble("energy2", energy2);
        nbttagcompound.setInteger("expstorage", expstorage);
        for (int i = 0; i < 4; i++)
            nbttagcompound.setInteger("progress" + i, progress[i]);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        for (int i = 0; i < 4; i++)
            progress[i] = nbttagcompound.getInteger("progress" + i);
        expstorage = nbttagcompound.getInteger("expstorage");
    }

    @Override
    public int getEnergyStored(ForgeDirection paramForgeDirection) {
        return (int) energy2;
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection paramForgeDirection) {
        return (int) maxEnergy2;
    }
}
