package com.denfop.ssp.tiles.panels.rain;

import com.denfop.ssp.tiles.panels.entity.TileEntityRainPanel;

public class TileEntityAdvancedSolarRain extends TileEntityRainPanel {
	public static SolarConfig settings;

	public TileEntityAdvancedSolarRain() {
		super(TileEntityAdvancedSolarRain.settings);
	}
}
