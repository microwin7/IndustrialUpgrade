package com.denfop.api.se;

import net.minecraft.world.chunk.Chunk;

import java.util.List;

public interface ISEWirelessSource extends ISESource {

    List<ISESink> getList();

    void setList(List<ISESink> lst);

    Chunk getChunk();

}
