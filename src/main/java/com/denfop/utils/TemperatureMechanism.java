package com.denfop.utils;

import com.denfop.api.ITemperature;
import com.denfop.api.ITemperatureMechanism;

public class TemperatureMechanism implements ITemperatureMechanism {
    @Override
    public boolean process(ITemperature tile) {
        if (tile.getTile().energy <= 0 && tile.getFluid() == null)
            return false;
        short temp = tile.getTemperature();
        if (temp >= tile.getMaxTemperature())
            return false;
        if (tile.isFluidTemperature()) {
            if (tile.getFluid().amount >= 5) {
                tile.setTemperature((short) (temp + 5));
                tile.getFluid().amount -= 5;
                return true;
            }
        } else {
            if (tile.getTile().energy >= 50) {
                tile.setTemperature((short) (temp + 5));
                tile.getTile().energy -= 50;
                return true;
            }
        }
        return false;
    }

    @Override
    public void transfer(ITemperature receiver, ITemperature extract) {
        if (extract.getTemperature() <= 0 || receiver.getTemperature() >= receiver.getMaxTemperature())
            return;
        if (receiver.equals(extract))
            return;
        short temp = (short) Math.min(extract.getTemperature(), receiver.getMaxTemperature() - receiver.getTemperature());
        extract.setTemperature((short) (extract.getTemperature() - temp));
        receiver.setTemperature((short) (receiver.getTemperature() + temp));

    }

    @Override
    public void work(ITemperature tile) {
        if (tile.getTemperature() - 5 > 0)
            tile.setTemperature((short) (tile.getTemperature() - 5));
    }
}
