package com.denfop.api.space.colonies;

import com.denfop.api.space.IBody;
import com.denfop.api.space.fakebody.FakePlayer;

import java.util.List;
import java.util.Map;

public interface IColonieNet {

    Map<FakePlayer, List<IColonie>> getMap();

    boolean canAddColonie(IBody body, FakePlayer player);

    void addColonie(IBody body, FakePlayer player);

    void working();

    List<IColonie> getColonies();

}
