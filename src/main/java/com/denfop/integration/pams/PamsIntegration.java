package com.denfop.integration.pams;

import com.denfop.api.Recipes;
import com.pam.harvestcraft.BlockRegistry;
import com.pam.harvestcraft.ItemRegistry;
import ic2.api.recipe.RecipeInputItemStack;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class PamsIntegration {
    public static void init() {
        addrecipe(ItemRegistry.apricotItem, BlockRegistry.pamapricotSapling);
        addrecipe(ItemRegistry.dateItem, BlockRegistry.pamdateSapling);
        addrecipe(ItemRegistry.papayaItem, BlockRegistry.pampapayaSapling);
        addrecipe(ItemRegistry.pistachioItem, BlockRegistry.pampistachioSapling);
        addrecipe(ItemRegistry.chestnutItem, BlockRegistry.pamchestnutSapling);
        addrecipe(ItemRegistry.bananaItem, BlockRegistry.pambananaSapling);
        addrecipe(ItemRegistry.limeItem, BlockRegistry.pamlimeSapling);
        addrecipe(ItemRegistry.figItem, BlockRegistry.pamfigSapling);
        addrecipe(ItemRegistry.cashewItem, BlockRegistry.pamcashewSapling);
        addrecipe(Items.apple, BlockRegistry.pamappleSapling);
        addrecipe(ItemRegistry.orangeItem, BlockRegistry.pamorangeSapling);
        addrecipe(ItemRegistry.pearItem, BlockRegistry.pampearSapling);
        addrecipe(ItemRegistry.pomegranateItem, BlockRegistry.pampomegranateSapling);
        addrecipe(ItemRegistry.cherryItem, BlockRegistry.pamcherrySapling);
        addrecipe(ItemRegistry.mangoItem, BlockRegistry.pammangoSapling);
        addrecipe(ItemRegistry.nutmegItem, BlockRegistry.pamnutmegSapling);
        addrecipe(ItemRegistry.dragonfruitItem, BlockRegistry.pamdragonfruitSapling);
        addrecipe(ItemRegistry.oliveItem, BlockRegistry.pamoliveSapling);
        addrecipe(ItemRegistry.coconutItem, BlockRegistry.pamcoconutSapling);
        addrecipe(ItemRegistry.peppercornItem, BlockRegistry.pampeppercornSapling);
        addrecipe(ItemRegistry.avocadoItem, BlockRegistry.pamavocadoSapling);
        addrecipe(ItemRegistry.almondItem, BlockRegistry.pamalmondSapling);
        addrecipe(ItemRegistry.plumItem, BlockRegistry.pamplumSapling);
        addrecipe(ItemRegistry.pecanItem, BlockRegistry.pampecanSapling);
        addrecipe(ItemRegistry.persimmonItem, BlockRegistry.pampersimmonSapling);
        addrecipe(ItemRegistry.lemonItem, BlockRegistry.pamlemonSapling);
        addrecipe(ItemRegistry.cinnamonItem, BlockRegistry.pamcinnamonSapling);
        addrecipe(ItemRegistry.peachItem, BlockRegistry.pampeachSapling);
        addrecipe(ItemRegistry.walnutItem, BlockRegistry.pamwalnutSapling);
        addrecipe(ItemRegistry.gooseberryItem, BlockRegistry.pamgooseberrySapling);
        addrecipe(ItemRegistry.grapefruitItem, BlockRegistry.pamgrapefruitSapling);
        addrecipe(ItemRegistry.starfruitItem, BlockRegistry.pamstarfruitSapling);
        addrecipe(ItemRegistry.artichokeItem, ItemRegistry.artichokeseedItem);
        addrecipe(ItemRegistry.peanutItem, ItemRegistry.peanutseedItem);
        addrecipe(ItemRegistry.onionItem, ItemRegistry.onionseedItem);
        addrecipe(ItemRegistry.okraItem, ItemRegistry.okraseedItem);
        addrecipe(ItemRegistry.raspberryItem, ItemRegistry.raspberryseedItem);
        addrecipe(ItemRegistry.sesameseedsItem, ItemRegistry.sesameseedsseedItem);
        addrecipe(ItemRegistry.rutabagaItem, ItemRegistry.rutabagaseedItem);
        addrecipe(ItemRegistry.riceItem, ItemRegistry.riceseedItem);
        addrecipe(ItemRegistry.ryeItem, ItemRegistry.ryeseedItem);
        addrecipe(ItemRegistry.leekItem, ItemRegistry.leekseedItem);
        addrecipe(ItemRegistry.gingerItem, ItemRegistry.gingerseedItem);
        addrecipe(ItemRegistry.barleyItem, ItemRegistry.barleyseedItem);
        addrecipe(ItemRegistry.oatsItem, ItemRegistry.oatsseedItem);
        addrecipe(ItemRegistry.broccoliItem, ItemRegistry.broccoliseedItem);
        addrecipe(ItemRegistry.radishItem, ItemRegistry.radishseedItem);
        addrecipe(ItemRegistry.soybeanItem, ItemRegistry.soybeanseedItem);
        addrecipe(ItemRegistry.cantaloupeItem, ItemRegistry.cantaloupeseedItem);
        addrecipe(ItemRegistry.scallionItem, ItemRegistry.scallionseedItem);
        addrecipe(ItemRegistry.zucchiniItem, ItemRegistry.zucchiniseedItem);
        addrecipe(ItemRegistry.peasItem, ItemRegistry.peasseedItem);
        addrecipe(ItemRegistry.cactusfruitItem, ItemRegistry.cactusfruitseedItem);
        addrecipe(ItemRegistry.bambooshootItem, ItemRegistry.bambooshootseedItem);
        addrecipe(ItemRegistry.asparagusItem, ItemRegistry.asparagusseedItem);
        addrecipe(ItemRegistry.lettuceItem, ItemRegistry.lettuceseedItem);
        addrecipe(ItemRegistry.cauliflowerItem, ItemRegistry.cauliflowerseedItem);
        addrecipe(ItemRegistry.spiceleafItem, ItemRegistry.spiceleafseedItem);
        addrecipe(ItemRegistry.tomatoItem, ItemRegistry.tomatoseedItem);
        addrecipe(ItemRegistry.cucumberItem, ItemRegistry.cucumberseedItem);
        addrecipe(ItemRegistry.garlicItem, ItemRegistry.garlicseedItem);
        addrecipe(ItemRegistry.chilipepperItem, ItemRegistry.chilipepperseedItem);
        addrecipe(ItemRegistry.spinachItem, ItemRegistry.spinachseedItem);
        addrecipe(ItemRegistry.seaweedItem, ItemRegistry.seaweedseedItem);
        addrecipe(ItemRegistry.candleberryItem, ItemRegistry.candleberryseedItem);
        addrecipe(ItemRegistry.beetItem, ItemRegistry.beetseedItem);
        addrecipe(ItemRegistry.coffeeItem, ItemRegistry.coffeeseedItem);
        addrecipe(ItemRegistry.eggplantItem, ItemRegistry.eggplantseedItem);
        addrecipe(ItemRegistry.cranberryItem, ItemRegistry.cranberryseedItem);
        addrecipe(ItemRegistry.grapeItem, ItemRegistry.grapeseedItem);
        addrecipe(ItemRegistry.beanItem, ItemRegistry.beanseedItem);
        addrecipe(ItemRegistry.parsnipItem, ItemRegistry.parsnipseedItem);
        addrecipe(ItemRegistry.rhubarbItem, ItemRegistry.rhubarbseedItem);
        addrecipe(ItemRegistry.brusselsproutItem, ItemRegistry.brusselsproutseedItem);
        addrecipe(ItemRegistry.whitemushroomItem, ItemRegistry.whitemushroomseedItem);
        addrecipe(ItemRegistry.teaItem, ItemRegistry.teaseedItem);
        addrecipe(ItemRegistry.celeryItem, ItemRegistry.celeryseedItem);
        addrecipe(ItemRegistry.wintersquashItem, ItemRegistry.wintersquashseedItem);
        addrecipe(ItemRegistry.pineappleItem, ItemRegistry.pineappleseedItem);
        addrecipe(ItemRegistry.kiwiItem, ItemRegistry.kiwiseedItem);
        addrecipe(ItemRegistry.cabbageItem, ItemRegistry.cabbageseedItem);
        addrecipe(ItemRegistry.cornItem, ItemRegistry.cornseedItem);
        addrecipe(ItemRegistry.bellpepperItem, ItemRegistry.bellpepperseedItem);
        addrecipe(ItemRegistry.mustardItem, ItemRegistry.mustardseedItem);
        addrecipe(ItemRegistry.sweetpotatoItem, ItemRegistry.sweetpotatoseedItem);
        addrecipe(ItemRegistry.waterchestnutItem, ItemRegistry.waterchestnutseedItem);
        addrecipe(ItemRegistry.curryleafItem, ItemRegistry.curryleafseedItem);
        addrecipe(ItemRegistry.turnipItem, ItemRegistry.turnipseedItem);
        addrecipe(ItemRegistry.cottonItem, ItemRegistry.cottonseedItem);
    }

    public static void addrecipe(Item input, Block output) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(input)), null, new ItemStack(Item.getItemFromBlock(output)));
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(Item.getItemFromBlock(output))), null, new ItemStack(input, 2));

    }

    public static void addrecipe(Item input, Item output) {
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(input)), null, new ItemStack(output));
        Recipes.fermer.addRecipe(new RecipeInputItemStack(new ItemStack(output)), null, new ItemStack(input, 2));

    }
}
