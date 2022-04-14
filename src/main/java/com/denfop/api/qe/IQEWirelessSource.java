package com.denfop.api.qe;

import net.minecraft.world.chunk.Chunk;

import java.util.List;

public interface IQEWirelessSource extends IQESource {

    List<IQESink> getList();

    void setList(List<IQESink> lst);

    Chunk getChunk();

}
