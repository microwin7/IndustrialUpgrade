package com.denfop.api.space.colonies;

import com.denfop.api.space.IBody;
import com.denfop.api.space.fakebody.FakePlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColonieNet implements IColonieNet {

    Map<FakePlayer, List<IColonie>> fakePlayerListMap;
    List<IColonie> colonieList;

    public ColonieNet() {
        this.fakePlayerListMap = new HashMap<>();
        this.colonieList = new ArrayList<>();
    }

    @Override
    public Map<FakePlayer, List<IColonie>> getMap() {
        return this.fakePlayerListMap;
    }

    @Override
    public boolean canAddColonie(final IBody body, final FakePlayer player) {
        if (!fakePlayerListMap.containsKey(player)) {
            return true;
        }
        List<IColonie> list = fakePlayerListMap.get(player);
        for (IColonie colonie : list) {
            if (colonie.matched(body)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void addColonie(final IBody body, final FakePlayer player) {
        if (canAddColonie(body, player)) {
            if (!this.fakePlayerListMap.containsKey(player)) {
                List<IColonie> colonieList = new ArrayList<>();
                final Colonie colonie = new Colonie(body);
                colonieList.add(colonie);
                fakePlayerListMap.put(player, colonieList);
            } else {
                List<IColonie> colonieList = fakePlayerListMap.get(player);
                colonieList.add(new Colonie(body));
            }
        }
    }

    @Override
    public void working() {

    }

    @Override
    public List<IColonie> getColonies() {
        return this.colonieList;
    }

}
