package net.slimerealm.registry;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.slimerealm.SlimeRealmMod;
import net.slimerealm.entity.GelatinousCreeperEntity;
import net.slimerealm.entity.SlimeGolemEntity;

public class ModEntities {
    public static final EntityType<SlimeGolemEntity> SLIME_GOLEM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(SlimeRealmMod.MOD_ID, "slime_golem"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, SlimeGolemEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4F, 2.7F))
                    .build()
    );

    public static final EntityType<GelatinousCreeperEntity> GELATINOUS_CREEPER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(SlimeRealmMod.MOD_ID, "gelatinous_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GelatinousCreeperEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6F, 1.7F))
                    .build()
    );

    public static void registerModEntities() {
        SlimeRealmMod.LOGGER.info("Registering Mod Entities for " + SlimeRealmMod.MOD_ID);
    }
}
