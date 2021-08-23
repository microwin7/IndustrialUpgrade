package com.denfop.block.base;


import com.denfop.IUCore;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockSSP extends Block {
	public BlockSSP(Material material) {
		super(material);
		this.setCreativeTab(IUCore.tabssp);
		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(0.3F);
		this.setLightLevel(0.3F);
		this.setStepSound(Block.soundTypeGlass);
	}
}
