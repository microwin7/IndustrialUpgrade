//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.denfop.integration.neiresources;

import com.denfop.IUItem;
import com.denfop.register.RegisterOreDict;
import cpw.mods.fml.common.Optional.Method;
import neresources.api.distributions.DistributionSquare;
import neresources.api.messages.RegisterOreMessage;
import neresources.compatibility.CompatBase;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class NeiResourcesIntegration extends CompatBase {
    final Random random = new Random();


    public NeiResourcesIntegration() {

    }

    public void init() {
        this.registerIUOres();

    }

    @Method(
            modid = "industrialupgrade"
    )
    private void registerIUOres() {
        genMikhail();
        genAluminium();
        genChromium();
        genCobalt();
        genGermanium();
        genIridium();
        genMagnesium();
        genManganese();
        genNickel();
        genPlatium();
        genSilver();
        genSpinel();
        genTitanium();
        genTungsten();
        genVanady();
        genZinc();
        genHeavyOre();
        genAmericium();
        genNeptunium();
        genCurium();
        genToriy();
        genPrecious();
    }

    private void genMikhail() {

        ItemStack ore = new ItemStack(IUItem.ore, 1, 0);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(veinSize, numVeins, minY, maxY)));
    }

    private void genAluminium() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 1);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genVanady() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 2);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genTungsten() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 3);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genCobalt() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 4);
        int numVeins = 3 + random.nextInt(6);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genMagnesium() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 7);
        int numVeins = 3 + random.nextInt(7);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genNickel() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 6);
        int numVeins = 3 + random.nextInt(8);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genPlatium() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 9);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genTitanium() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 10);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genChromium() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 11);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genSpinel() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 12);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genSilver() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 14);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genZinc() {
        ItemStack ore = new ItemStack(IUItem.ore, 1, 15);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genManganese() {
        ItemStack ore = new ItemStack(IUItem.ore1, 1, 0);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genIridium() {
        ItemStack ore = new ItemStack(IUItem.ore1, 1, 1);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }

    private void genGermanium() {
        ItemStack ore = new ItemStack(IUItem.ore1, 1, 2);
        int numVeins = 3 + random.nextInt(3);
        int minY = 0;
        int maxY = 70;
        int veinSize = 9;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
    }


    private void genHeavyOre() {
        for (int i = 0; i < RegisterOreDict.list_heavyore.size(); i++) {
            ItemStack ore = new ItemStack(IUItem.heavyore, 1, i);
            int numVeins = 3 + random.nextInt(2);
            int minY = 10;
            int maxY = 70;
            int veinSize = 9;
            this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
        }
    }

    private void genAmericium() {
        ItemStack ore = new ItemStack(IUItem.radiationore, 1, 0);
        int numVeins = 2 + random.nextInt(2);
        int minY = 10;
        int maxY = 70;
        int veinSize = 14;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(veinSize, numVeins, minY, maxY)));
    }

    private void genNeptunium() {
        ItemStack ore = new ItemStack(IUItem.radiationore, 1, 1);
        int numVeins = 2 + random.nextInt(2);
        int minY = 10;
        int maxY = 70;
        int veinSize = 16;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(veinSize, numVeins, minY, maxY)));
    }

    private void genCurium() {
        ItemStack ore = new ItemStack(IUItem.radiationore, 1, 2);
        int numVeins = 2 + random.nextInt(2);
        int minY = 10;
        int maxY = 70;
        int veinSize = 10;

        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(veinSize, numVeins, minY, maxY)));
    }

    private void genToriy() {
        ItemStack ore = new ItemStack(IUItem.toriyore, 1, 0);
        int numVeins = 3 + random.nextInt(2);
        int minY = 10;
        int maxY = 70;
        int veinSize = 11;
        this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(veinSize, numVeins, minY, maxY)));
    }

    private void genPrecious() {
        for (int i = 0; i < 3; i++) {
            ItemStack ore = new ItemStack(IUItem.preciousore, 1, i);
            int numVeins = 3 + random.nextInt(2);
            int minY = 10;
            int maxY = 70;
            int veinSize = 8;
            this.registerOre(new RegisterOreMessage(ore, new DistributionSquare(numVeins, veinSize, minY, maxY)));
        }
    }
}
