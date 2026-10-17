package net.slimerealm.registry;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.slimerealm.SlimeRealmMod;

public class ModDimensions {
    public static final RegistryKey<World> SLIME_REALM_WORLD = RegistryKey.of(
            RegistryKeys.WORLD, new Identifier(SlimeRealmMod.MOD_ID, "slime_realm"));

    public static final RegistryKey<DimensionType> SLIME_REALM_DIM_TYPE = RegistryKey.of(
            RegistryKeys.DIMENSION_TYPE, new Identifier(SlimeRealmMod.MOD_ID, "slime_realm"));

    public static final RegistryKey<ConfiguredFeature<?, ?>> SLIME_TREE_KEY = RegistryKey.of(
            RegistryKeys.CONFIGURED_FEATURE, new Identifier(SlimeRealmMod.MOD_ID, "slime_tree"));

    public static void registerDimensions() {
        SlimeRealmMod.LOGGER.info("Registering Dimensions for " + SlimeRealmMod.MOD_ID);
    }
}
