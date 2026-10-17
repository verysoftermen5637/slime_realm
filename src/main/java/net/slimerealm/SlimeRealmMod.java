package net.slimerealm;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.slimerealm.entity.GelatinousCreeperEntity;
import net.slimerealm.entity.SlimeGolemEntity;
import net.slimerealm.item.SlimeBootsItem;
import net.slimerealm.registry.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlimeRealmMod implements ModInitializer {
    public static final String MOD_ID = "slime_realm";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing Slime Realm 2.0 Mod...");

        // Order matters: Entities & Blocks before Items & ItemGroups
        ModEntities.registerModEntities();
        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModFeatures.registerModFeatures();
        ModItemGroups.registerItemGroups();
        ModDimensions.registerDimensions();
        ModTrades.registerTrades();

        // Register axe stripping for Slime Log
        StrippableBlockRegistry.register(ModBlocks.SLIME_LOG, ModBlocks.STRIPPED_SLIME_LOG);

        // Register flammability so wood behaves like real wood
        FlammableBlockRegistry flammable = FlammableBlockRegistry.getDefaultInstance();
        flammable.add(ModBlocks.SLIME_LOG, 5, 5);
        flammable.add(ModBlocks.STRIPPED_SLIME_LOG, 5, 5);
        flammable.add(ModBlocks.SLIME_PLANKS, 5, 20);
        flammable.add(ModBlocks.SLIME_SLAB, 5, 20);
        flammable.add(ModBlocks.SLIME_STAIRS, 5, 20);
        flammable.add(ModBlocks.SLIME_FENCE, 5, 20);
        flammable.add(ModBlocks.SLIME_FENCE_GATE, 5, 20);
        flammable.add(ModBlocks.SLIME_LEAVES, 30, 60);

        // Register furnace fuels
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_LOG, 300);
        FuelRegistry.INSTANCE.add(ModBlocks.STRIPPED_SLIME_LOG, 300);
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_PLANKS, 300);
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_SLAB, 150);
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_STAIRS, 300);
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_FENCE, 300);
        FuelRegistry.INSTANCE.add(ModBlocks.SLIME_FENCE_GATE, 300);

        // Entity Attributes
        FabricDefaultAttributeRegistry.register(ModEntities.SLIME_GOLEM, SlimeGolemEntity.createIronGolemAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.GELATINOUS_CREEPER, GelatinousCreeperEntity.createCreeperAttributes());

        // Server tick listener for Slime Armor & Slime Boots
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            server.getPlayerManager().getPlayerList().forEach(SlimeBootsItem::handlePlayerTick);
        });

        LOGGER.info("Slime Realm 2.0 initialized successfully!");
    }
}
