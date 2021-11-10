package com.denfop.tiles.base;

import ic2.core.block.TileEntityBlock;
import ic2.core.block.TileEntityInventory;

public class TileEntityError extends TileEntityBlock {

    public void updateEntityServer() {
        if (!(this.worldObj.getTileEntity(this.xCoord, this.yCoord - 1, this.zCoord) instanceof TileEntityInventory))
            this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        return true;
    }
}
