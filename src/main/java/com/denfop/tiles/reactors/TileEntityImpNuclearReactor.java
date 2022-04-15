package com.denfop.tiles.reactors;

import ic2.api.Direction;
import ic2.api.reactor.IReactorComponent;
import ic2.core.ExplosionIC2;
import ic2.core.IC2;
import ic2.core.init.MainConfig;
import ic2.core.util.ConfigUtil;
import ic2.core.util.LogCategory;
import ic2.core.util.Util;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

public class TileEntityImpNuclearReactor extends TileEntityBaseNuclearReactorElectric {
    public TileEntityImpNuclearReactor() {
        super(11, 6, "textures/gui/GUIImpNuclearReaktor.png", 1.4);
    }

    public void setblock() {

        for (Direction direction : Direction.directions) {
            TileEntity target = direction.applyToTileEntity(this);
            if (target instanceof TileEntityImpReactorChamberElectric) {
                this.worldObj.setBlockToAir(target.xCoord, target.yCoord, target.zCoord);
            }
        }

        this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
    }

    public void explode() {
        float boomPower = 10.0F;
        float boomMod = 1.0F;

        for (int i = 0; i < this.reactorSlot.size(); ++i) {
            ItemStack stack = this.reactorSlot.get(i);
            if (stack != null && stack.getItem() instanceof IReactorComponent) {
                float f = ((IReactorComponent) stack.getItem()).influenceExplosion(this, stack);
                if (f > 0.0F && f < 1.0F) {
                    boomMod *= f;
                } else {
                    boomPower += f;
                }
            }

            this.reactorSlot.put(i, null);
        }

        boomPower *= this.hem * boomMod;
        IC2.log.log(LogCategory.PlayerActivity, Level.INFO, "Nuclear Reactor at %s melted (raw explosion power %f)", Util.formatPosition(this), boomPower);
        boomPower = Math.min(boomPower, ConfigUtil.getFloat(MainConfig.get(), "protection/reactorExplosionPowerLimit"));
        Direction[] var8 = Direction.directions;

        for (Direction direction : var8) {
            TileEntity target = direction.applyToTileEntity(this);
            if (target instanceof TileEntityImpReactorChamberElectric) {
                this.worldObj.setBlockToAir(target.xCoord, target.yCoord, target.zCoord);
            }
        }

        this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
        ExplosionIC2 explosion = new ExplosionIC2(this.worldObj, null, this.xCoord, this.yCoord, this.zCoord, boomPower, 0.01F, ExplosionIC2.Type.Nuclear);
        explosion.doExplosion();
    }

    public short getReactorSize() {
        if (this.worldObj == null) {
            return (short) sizeX;
        } else {
            short cols = (short) (this.sizeX - 6);

            Direction[] var2 = Direction.directions;


            for (Direction direction : var2) {
                TileEntity target = direction.applyToTileEntity(this);
                if (target instanceof TileEntityImpReactorChamberElectric) {
                    cols++;
                }
            }

            return cols;
        }
    }

    public List<TileEntity> getSubTiles() {
        if (this.subTiles == null) {
            this.subTiles = new ArrayList();
            this.subTiles.add(this);
            Direction[] var1 = Direction.directions;

            for (Direction dir : var1) {
                TileEntity te = dir.applyToTileEntity(this);
                if (te instanceof TileEntityImpReactorChamberElectric && !te.isInvalid()) {
                    this.subTiles.add(te);
                }
            }
        }

        return this.subTiles;
    }
}
