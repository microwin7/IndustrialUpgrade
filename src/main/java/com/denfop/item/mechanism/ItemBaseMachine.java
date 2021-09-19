package com.denfop.item.mechanism;

import com.denfop.IUCore;
import com.denfop.block.mechanism.BlockBaseMachine;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBaseMachine extends ItemBlock {
    public ItemBaseMachine(Block block) {
        super(block);
        setMaxDamage(0);
        setHasSubtypes(true);
        setCreativeTab(IUCore.tabssp);
    }

    @Override
    public int getMetadata(int i) {
        return i;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        int meta = itemstack.getItemDamage();
        switch (meta) {

            case 1:
                return "iu.blockMatter1";

            case 2:
                return "iu.blockMatter2";
            case 3:
                return "iu.blockMatter3";

            case 4:
                return "iu.Alloymachine";
            case 5:
                return "iu.blockMatter";
            case 6:
                return "iu.blockGenerationMicrochip";
            case 7:
                return "iu.blockGenerationStone";
            case 8:
                return "iu.blockQuantumQuarry";
            case 9:
                return "iu.blockModuleMachine";
            case 10:
                return "iu.blockAdvgenerator";
            case 11:
                return "iu.blockImpgenerator";
            case 12:
                return "iu.blockPergenerator";
            case 13:
                return "iu.blockQuantumQuarry1";
            case 14:
                return "iu.blockQuantumQuarry2";
            case 15:
                return "iu.blockQuantumQuarry3";

        }
        return null;
    }


    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int i = 1; i < BlockBaseMachine.names.length + 1; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }
}
