package com.denfop;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;

import java.util.HashMap;

public class IUAchievements {
    public final HashMap<String, Achievement> achievementList = new HashMap();
    public final HashMap<String, String> achievementList1 = new HashMap();


    public IUAchievements() {
        //TODO ores
        this.registerAchievement("mikhailore", 2, 0, new ItemStack(IUItem.ore, 1, 0), AchievementList.openInventory, false);
        this.registerAchievement("aluminiumore", 3, 0, new ItemStack(IUItem.ore, 1, 1), AchievementList.openInventory, false);
        this.registerAchievement("vanadyore", 1, 0, new ItemStack(IUItem.ore, 1, 2), AchievementList.openInventory, false);
        this.registerAchievement("wolframore", 1, 1, new ItemStack(IUItem.ore, 1, 3), AchievementList.openInventory, false);
        this.registerAchievement("cobaltore", 1, -1, new ItemStack(IUItem.ore, 1, 6), AchievementList.openInventory, false);
        this.registerAchievement("magnesiumore", 2, -1, new ItemStack(IUItem.ore, 1, 7), AchievementList.openInventory, false);
        this.registerAchievement("nickelore", 3, -1, new ItemStack(IUItem.ore, 1, 8), AchievementList.openInventory, false);
        this.registerAchievement("platiumore", 2, 1, new ItemStack(IUItem.ore, 1, 9), AchievementList.openInventory, false);
        this.registerAchievement("titaniumore", 0, 0, new ItemStack(IUItem.ore, 1, 10), AchievementList.openInventory, false);
        this.registerAchievement("chromiumore", 0, -1, new ItemStack(IUItem.ore, 1, 11), AchievementList.openInventory, false);
        this.registerAchievement("spinelore", 0, 1, new ItemStack(IUItem.ore, 1, 12), AchievementList.openInventory, false);
        this.registerAchievement("silverore", -1, 0, new ItemStack(IUItem.ore, 1, 14), AchievementList.openInventory, false);
        this.registerAchievement("zincore", -1, 1, new ItemStack(IUItem.ore, 1, 15), AchievementList.openInventory, false);
        this.registerAchievement("manganeseore", -1, -1, new ItemStack(IUItem.ore1, 1, 0), AchievementList.openInventory, false);
        this.registerAchievement("iridiumore", 3, 1, new ItemStack(IUItem.ore1, 1, 1), AchievementList.openInventory, false);
        this.registerAchievement("germaniumore", -1, -2, new ItemStack(IUItem.ore1, 1, 2), AchievementList.openInventory, false);
        this.registerAchievement("americiumore", 0, -2, new ItemStack(IUItem.radiationore, 1, 0), AchievementList.openInventory, false);
        this.registerAchievement("neptuniumore", 1, -2, new ItemStack(IUItem.radiationore, 1, 1), AchievementList.openInventory, false);
        this.registerAchievement("curiumore", 2, -2, new ItemStack(IUItem.radiationore, 1, 2), AchievementList.openInventory, false);
        this.registerAchievement("toriyore", 5, -2, new ItemStack(IUItem.toriyore, 1), new ItemStack(IUItem.toriy, 1), AchievementList.openInventory, false);
        //TODO part 1
        this.registerAchievement("SE_generator", 5, -1, new ItemStack(IUItem.blockSE), "toriyore", false);
        this.registerAchievement("sunnarium_part", 6, -1, new ItemStack(IUItem.sunnarium, 1, 4), "SE_generator", false);
        this.registerAchievement("Adv_SE_generator", 5, 0, new ItemStack(IUItem.AdvblockSE), "SE_generator", false);
        this.registerAchievement("Imp_SE_generator", 5, 1, new ItemStack(IUItem.ImpblockSE), "Adv_SE_generator", false);
        this.registerAchievement("sunnarium_maker", 7, -1, new ItemStack(IUItem.sunnariummaker), "sunnarium_part", false);
        this.registerAchievement("sunnarium", 8, -1, new ItemStack(IUItem.sunnarium, 1, 3), "sunnarium_maker", false);
        this.registerAchievement("sunnarium_plate", 8, -2, new ItemStack(IUItem.sunnarium, 1, 2), "sunnarium", false);
        this.registerAchievement("enrichment", 9, -1, new ItemStack(IUItem.basemachine, 1, 10), "sunnarium", false);
        this.registerAchievement("sunnarium_enrich", 11, -1, new ItemStack(IUItem.sunnarium, 1, 0), "enrichment", false);
        this.registerAchievement("uranium_enrich", 11, -2, new ItemStack(IUItem.itemIU, 1, 0), "enrichment", false);
        this.registerAchievement("glass_enrich", 12, -2, new ItemStack(IUItem.itemIU, 1, 1), "uranium_enrich", false);
        this.registerAchievement("toriy_enrich", 11, 0, new ItemStack(IUItem.radiationresources, 1, 4), "enrichment", false);

        this.registerAchievement("sunnarium_makerpanel", 12, -1, new ItemStack(IUItem.sunnariumpanelmaker, 1), "sunnarium_enrich", false);
        this.registerAchievement("molecular_transfromer", 13, -1, new ItemStack(IUItem.blockmolecular, 1), "sunnarium_makerpanel", false);
        this.registerAchievement("double_molecular_transfromer", 14, -1, new ItemStack(IUItem.blockdoublemolecular, 1), "sunnarium_makerpanel", false);

        //TODO Core
        this.registerAchievement("adv_core", 13, 4, new ItemStack(IUItem.core, 1), "molecular_transfromer", false);
        this.registerAchievement("hyb_core", 14, 4, new ItemStack(IUItem.core, 1, 1), "adv_core", false);
        this.registerAchievement("ult_core", 15, 4, new ItemStack(IUItem.core, 1, 2), "hyb_core", false);
        this.registerAchievement("qua_core", 16, 4, new ItemStack(IUItem.core, 1, 3), "ult_core", false);
        this.registerAchievement("spe_core", 17, 4, new ItemStack(IUItem.core, 1, 4), "qua_core", false);
        this.registerAchievement("pro_core", 18, 4, new ItemStack(IUItem.core, 1, 5), "spe_core", false);
        this.registerAchievement("sin_core", 19, 4, new ItemStack(IUItem.core, 1, 6), "pro_core", false);
        this.registerAchievement("dif_core", 20, 4, new ItemStack(IUItem.core, 1, 7), "sin_core", false);
        this.registerAchievement("pho_core", 21, 4, new ItemStack(IUItem.core, 1, 8), "dif_core", false);
        this.registerAchievement("neu_core", 22, 4, new ItemStack(IUItem.core, 1, 9), "pho_core", true);
        this.registerAchievement("bar_core", 23, 4, new ItemStack(IUItem.core, 1, 10), "neu_core", true);
        this.registerAchievement("had_core", 24, 4, new ItemStack(IUItem.core, 1, 11), "bar_core", true);
        this.registerAchievement("gra_core", 25, 4, new ItemStack(IUItem.core, 1, 12), "had_core", true);
        this.registerAchievement("kvr_core", 26, 4, new ItemStack(IUItem.core, 1, 13), "gra_core", true);

        //TODO Panel
        this.registerAchievement("adv_panel", 13, 5, new ItemStack(IUItem.blockpanel, 1), "adv_core", false);
        this.registerAchievement("hyb_panel", 14, 5, new ItemStack(IUItem.blockpanel, 1, 1), "adv_panel", false);
        this.registerAchievement("ult_panel", 15, 5, new ItemStack(IUItem.blockpanel, 1, 2), "hyb_panel", false);
        this.registerAchievement("qua_panel", 16, 5, new ItemStack(IUItem.blockpanel, 1, 3), "ult_panel", false);
        this.registerAchievement("spe_panel", 17, 5, new ItemStack(IUItem.blockpanel, 1, 4), "qua_panel", false);
        this.registerAchievement("pro_panel", 18, 5, new ItemStack(IUItem.blockpanel, 1, 5), "spe_panel", false);
        this.registerAchievement("sin_panel", 19, 5, new ItemStack(IUItem.blockpanel, 1, 6), "pro_panel", false);
        this.registerAchievement("dif_panel", 20, 5, new ItemStack(IUItem.blockpanel, 1, 7), "sin_panel", false);
        this.registerAchievement("pho_panel", 21, 5, new ItemStack(IUItem.blockpanel, 1, 8), "dif_panel", false);
        this.registerAchievement("neu_panel", 22, 5, new ItemStack(IUItem.blockpanel, 1, 9), "pho_panel", true);
        this.registerAchievement("bar_panel", 23, 5, new ItemStack(IUItem.blockpanel, 1, 10), "neu_panel", true);
        this.registerAchievement("had_panel", 24, 5, new ItemStack(IUItem.blockpanel, 1, 11), "bar_panel", true);
        this.registerAchievement("gra_panel", 25, 5, new ItemStack(IUItem.blockpanel, 1, 12), "had_panel", true);
        this.registerAchievement("kvr_panel", 26, 5, new ItemStack(IUItem.blockpanel, 1, 13), "gra_panel", true);

        this.registerAchievement("oil_bucket", 0, 4, new ItemStack(IUItem.bucket, 1, 2), AchievementList.acquireIron, false);
        this.registerAchievement("oil_drilling_rig", 0, 5, new ItemStack(IUItem.oilgetter, 1), "oil_bucket", false);
        this.registerAchievement("oil_quarry", 0, 6, new ItemStack(IUItem.oilquarry), "oil_drilling_rig", false);
        this.registerAchievement("oil_refiner", 0, 7, new ItemStack(IUItem.oilrefiner), "oil_quarry", false);
        this.registerAchievement("petrol", -1, 7, new ItemStack(IUItem.cell_all, 1, 4), "oil_refiner", false);
        this.registerAchievement("petrol_generator", -2, 7, new ItemStack(IUItem.basemachine1, 1, 5), "petrol", false);
        this.registerAchievement("diesel", 1, 7, new ItemStack(IUItem.cell_all, 1, 5), "oil_refiner", false);
        this.registerAchievement("diesel_generator", 2, 7, new ItemStack(IUItem.basemachine1, 1, 4), "diesel", false);
        this.registerAchievement("adv_oil_refiner", 0, 8, new ItemStack(IUItem.oiladvrefiner), "oil_refiner", false);
        this.registerAchievement("plastic_creator", 0, 9, new ItemStack(IUItem.basemachine1, 1, 11), "adv_oil_refiner", false);
        this.registerAchievement("elecrolyzer", 0, 10, new ItemStack(IUItem.basemachine1, 1, 15), "plastic_creator", false);
        this.registerAchievement("anode", 1, 10, new ItemStack(IUItem.anode), "elecrolyzer", false);
        this.registerAchievement("cathode", -1, 10, new ItemStack(IUItem.cathode), "elecrolyzer", false);
        this.registerAchievement("oxygen", 0, 11, new ItemStack(IUItem.cell_all, 1, 8), "elecrolyzer", false);
        this.registerAchievement("hydrogen", -1, 11, new ItemStack(IUItem.cell_all, 1, 9), "oxygen", false);
        this.registerAchievement("hydrogen_generator", -2, 11, new ItemStack(IUItem.basemachine1, 1, 9), "hydrogen", false);
        this.registerAchievement("plastic_plater_creator", 0, 12, new ItemStack(IUItem.basemachine1, 1, 13), "oxygen", false);
        this.registerAchievement("plastic_plater", 0, 13, new ItemStack(IUItem.plastic_plate), "plastic_plater_creator", false);


        AchievementPage.registerAchievementPage(new AchievementPage("IndustrialUpgrade", this.achievementList.values().toArray(new Achievement[0])));
        MinecraftForge.EVENT_BUS.register(this);
        FMLCommonHandler.instance().bus().register(this);
    }

    public void registerAchievement(String textId, int x, int y, ItemStack icon, Achievement requirement, boolean special) {
        Achievement achievement = new Achievement("iu." + textId, textId, -4 + x, -5 + y, icon, requirement);
        if (special) {
            achievement.setSpecial();
        }

        achievement.registerStat();
        this.achievementList1.put(icon.getUnlocalizedName(), textId);
        this.achievementList.put(textId, achievement);

    }

    public void registerAchievement(String textId, int x, int y, ItemStack icon, ItemStack item, Achievement requirement, boolean special) {
        Achievement achievement = new Achievement("iu." + textId, textId, -4 + x, -5 + y, icon, requirement);
        if (special) {
            achievement.setSpecial();
        }

        achievement.registerStat();
        this.achievementList1.put(item.getUnlocalizedName(), textId);
        this.achievementList.put(textId, achievement);


    }

    public void issueAchievement(EntityPlayer entityplayer, String textId) {
        if (this.achievementList.containsKey(textId)) {
            entityplayer.triggerAchievement(this.achievementList.get(textId));
        }

    }

    public void registerAchievement(String textId, int x, int y, ItemStack icon, String requirement, boolean special) {
        Achievement achievement = new Achievement("iu." + textId, textId, -4 + x, -5 + y, icon, this.getAchievement(requirement));
        if (special) {
            achievement.setSpecial();
        }

        achievement.registerStat();
        this.achievementList1.put(icon.getUnlocalizedName(), textId);
        this.achievementList.put(textId, achievement);


    }

    public Achievement getAchievement(String textId) {
        return this.achievementList.getOrDefault(textId, null);
    }

    @SubscribeEvent
    public void onCrafting(ItemCraftedEvent event) {
        EntityPlayer player = event.player;
        ItemStack stack = event.crafting;
        if (player != null) {
            if (achievementList1.containsKey(stack.getUnlocalizedName())) {
                this.issueAchievement(player, achievementList1.get(stack.getUnlocalizedName()));

            }
        }
    }


    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        if (achievementList1.containsKey(event.item.getEntityItem().getUnlocalizedName())) {
            this.issueAchievement(event.entityPlayer, achievementList1.get(event.item.getEntityItem().getUnlocalizedName()));

        }
    }

}
