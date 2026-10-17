package net.slimerealm.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.slimerealm.SlimeRealmMod;

public class ModItemGroups {
    public static final RegistryKey<ItemGroup> SLIME_REALM_GROUP_KEY = RegistryKey.of(
            RegistryKeys.ITEM_GROUP, new Identifier(SlimeRealmMod.MOD_ID, "slime_realm_group"));

    public static final ItemGroup SLIME_REALM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModBlocks.SLIME_LANTERN))
            .displayName(Text.translatable("itemGroup.slime_realm.slime_realm_group"))
            .entries((displayContext, entries) -> {
                // Dimension & Terrain Blocks
                entries.add(ModBlocks.SLIME_GRASS_BLOCK);
                entries.add(ModBlocks.SLIME_DIRT);
                entries.add(ModBlocks.GELSTONE);
                entries.add(ModBlocks.GELSTONE_BRICKS);
                entries.add(ModBlocks.SLIME_LANTERN);

                // Wood Set
                entries.add(ModBlocks.SLIME_LOG);
                entries.add(ModBlocks.STRIPPED_SLIME_LOG);
                entries.add(ModBlocks.SLIME_PLANKS);
                entries.add(ModBlocks.SLIME_LEAVES);
                entries.add(ModBlocks.SLIME_SAPLING);
                entries.add(ModBlocks.SLIME_SLAB);
                entries.add(ModBlocks.SLIME_STAIRS);
                entries.add(ModBlocks.SLIME_FENCE);
                entries.add(ModBlocks.SLIME_FENCE_GATE);

                // Ores
                entries.add(ModBlocks.SLIME_ORE);
                entries.add(ModBlocks.DEEPSLATE_SLIME_ORE);

                // Items & Portal Igniter
                entries.add(ModItems.SLIME_GEM);
                entries.add(ModItems.SLIME_INGOT);
                entries.add(ModItems.SLIME_CORE_IGNITER);

                // Tools
                entries.add(ModItems.SLIME_SWORD);
                entries.add(ModItems.SLIME_PICKAXE);
                entries.add(ModItems.SLIME_AXE);
                entries.add(ModItems.SLIME_SHOVEL);
                entries.add(ModItems.SLIME_HOE);

                // Full Armor Set
                entries.add(ModItems.SLIME_HELMET);
                entries.add(ModItems.SLIME_CHESTPLATE);
                entries.add(ModItems.SLIME_LEGGINGS);
                entries.add(ModItems.SLIME_BOOTS);

                // Spawn Eggs
                entries.add(ModItems.SLIME_GOLEM_SPAWN_EGG);
                entries.add(ModItems.GELATINOUS_CREEPER_SPAWN_EGG);
            })
            .build();

    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP, SLIME_REALM_GROUP_KEY, SLIME_REALM_GROUP);
    }
}
