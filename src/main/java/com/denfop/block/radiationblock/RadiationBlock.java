package com.denfop.block.radiationblock;

import java.util.Random;

import com.denfop.Constants;
import com.denfop.IUCore;
import com.denfop.IUItem;

import com.denfop.item.radionblock.ItemToriyOre;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class RadiationBlock extends Block  {
	

	public RadiationBlock(String name) {
		super(Material.iron);
		this.setHarvestLevel("pickaxe", 2);
		setHardness(2F);
		setCreativeTab(IUCore.tabssp4);
		setBlockTextureName(Constants.TEXTURES_MAIN+name);
		setHardness(0.6f);
	    setStepSound(Block.soundTypeStone);
	    setBlockName(name);
		GameRegistry.registerBlock(this, ItemToriyOre.class, name);

	}

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return IUItem.toriy;
	}



	
	public int quantityDropped(Random random) {
		return 1;
	}

	public int damageDropped(int i) {
		return i;
	}


}
