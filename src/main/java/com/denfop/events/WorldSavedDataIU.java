package com.denfop.events;

import com.denfop.Constants;
import com.denfop.api.space.IBody;
import com.denfop.api.space.SpaceNet;
import com.denfop.api.space.fakebody.FakePlayer;
import com.denfop.api.space.fakebody.SpaceOperation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WorldSavedDataIU extends WorldSavedData {

    public int col;
    private NBTTagCompound tagCompound = new NBTTagCompound();

    public WorldSavedDataIU() {
        super(Constants.MOD_ID);
    }

    @Override
    public void readFromNBT(final NBTTagCompound compound) {
        this.col = compound.getInteger("col");
        for (int i = 0; i < this.col; i++) {
            final NBTTagCompound nbt = compound.getCompoundTag(String.valueOf(i));
            final String name = nbt.getString("name");
            final NBTTagCompound tag = (NBTTagCompound) nbt.getTag("tag");
            FakePlayer player = new FakePlayer(name, tag);
            SpaceNet.instance.getFakeSpaceSystem().loadDataFromPlayer(player);
            final NBTTagCompound nbt_operation = nbt.getCompoundTag("operation");
            List<SpaceOperation> spaceOperations = new ArrayList<>();
            for (Map.Entry<String, IBody> map : SpaceNet.instance.getBodyMap().entrySet()) {
                if (nbt_operation.hasKey(map.getKey())) {
                    final SpaceOperation spaceOperation = new SpaceOperation(nbt_operation.getCompoundTag(map.getKey()));
                    spaceOperations.add(spaceOperation);

                }
            }
            SpaceNet.instance.getFakeSpaceSystem().loadSpaceOperation(spaceOperations, player);

        }
    }

    public NBTTagCompound getTagCompound() {
        return this.tagCompound;
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(@Nonnull final NBTTagCompound compound) {
        List<FakePlayer> list = SpaceNet.instance.getFakeSpaceSystem().getFakePlayers();
        int i = 0;
        for (FakePlayer player : list) {
            final NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("name", player.getName());
            nbt.setTag("tag", player.getTag());
            final NBTTagCompound nbt_operation = new NBTTagCompound();
            final List<SpaceOperation> list1 = SpaceNet.instance
                    .getFakeSpaceSystem()
                    .getSpaceOperationMap(player);
            for (SpaceOperation spaceOperation : list1) {
                spaceOperation.writeTag(nbt_operation);
            }
            nbt.setTag("operation", nbt_operation);
            compound.setTag(String.valueOf(i), compound);
            i++;
        }
        compound.setInteger("col", i);
        this.tagCompound = compound;
        return compound;
    }

}
