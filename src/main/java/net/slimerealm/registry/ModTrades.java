package net.slimerealm.registry;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;

public class ModTrades {
    public static void registerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.SLIME_BALL, 8),
                    new ItemStack(Items.EMERALD, 2),
                    16, 4, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 1),
                    new ItemStack(ModBlocks.SLIME_PLANKS, 8),
                    16, 2, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 3),
                    new ItemStack(ModItems.SLIME_GEM, 2),
                    12, 8, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.SLIME_GEM, 3),
                    new ItemStack(Items.EMERALD, 4),
                    12, 8, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.SLIME_CORE_IGNITER, 1),
                    6, 15, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 5),
                    new ItemStack(ModItems.SLIME_INGOT, 2),
                    12, 10, 0.05F
            ));
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.CLERIC, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 14),
                    new ItemStack(ModItems.SLIME_BOOTS, 1),
                    4, 20, 0.05F
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.SLIME_SWORD, 1),
                    4, 20, 0.05F
            ));
        });
    }
}
