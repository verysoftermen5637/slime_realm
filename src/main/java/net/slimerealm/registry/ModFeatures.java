package net.slimerealm.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.slimerealm.SlimeRealmMod;
import net.slimerealm.world.feature.*;

public class ModFeatures {
    public static final Feature<DefaultFeatureConfig> SLIME_TERRAIN = registerFeature("slime_terrain",
            new SlimeTerrainFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> GIANT_SLIME_TREE = registerFeature("giant_slime_tree",
            new GiantSlimeTreeFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_SPIRE = registerFeature("slime_spire",
            new SlimeSpireFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_DUNGEON = registerFeature("slime_dungeon",
            new SlimeDungeonFeature(DefaultFeatureConfig.CODEC));
    public static final Feature<DefaultFeatureConfig> SLIME_VILLAGE = registerFeature("slime_village",
            new SlimeVillageFeature(DefaultFeatureConfig.CODEC));

    private static <C extends net.minecraft.world.gen.feature.FeatureConfig, F extends Feature<C>> F registerFeature(String name, F feature) {
        return Registry.register(Registries.FEATURE, new Identifier(SlimeRealmMod.MOD_ID, name), feature);
    }

    public static void registerModFeatures() {
        SlimeRealmMod.LOGGER.info("Registering Mod Worldgen Features for " + SlimeRealmMod.MOD_ID);
    }
}
