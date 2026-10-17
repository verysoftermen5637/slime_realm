package net.slimerealm.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.slimerealm.SlimeRealmMod;
import net.slimerealm.item.*;

public class ModItems {
    // Raw & Ingot Resources
    public static final Item SLIME_GEM = registerItem("slime_gem",
            new Item(new FabricItemSettings()));
    public static final Item SLIME_INGOT = registerItem("slime_ingot",
            new Item(new FabricItemSettings()));

    // Portal Igniter
    public static final Item SLIME_CORE_IGNITER = registerItem("slime_core_igniter",
            new SlimeCoreIgniterItem(new FabricItemSettings().maxDamage(64)));

    // Tools
    public static final Item SLIME_SWORD = registerItem("slime_sword",
            new SlimeSwordItem(ModToolMaterial.SLIME, 4, -2.2F, new FabricItemSettings()));
    public static final Item SLIME_PICKAXE = registerItem("slime_pickaxe",
            new SlimePickaxeItem(ModToolMaterial.SLIME, 2, -2.8F, new FabricItemSettings()));
    public static final Item SLIME_AXE = registerItem("slime_axe",
            new AxeItem(ModToolMaterial.SLIME, 6.0F, -3.0F, new FabricItemSettings()));
    public static final Item SLIME_SHOVEL = registerItem("slime_shovel",
            new ShovelItem(ModToolMaterial.SLIME, 1.5F, -3.0F, new FabricItemSettings()));
    public static final Item SLIME_HOE = registerItem("slime_hoe",
            new HoeItem(ModToolMaterial.SLIME, -2, -1.0F, new FabricItemSettings()));

    // Full Slime Armor Set
    public static final Item SLIME_HELMET = registerItem("slime_helmet",
            new ArmorItem(ModArmorMaterial.SLIME, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item SLIME_CHESTPLATE = registerItem("slime_chestplate",
            new ArmorItem(ModArmorMaterial.SLIME, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item SLIME_LEGGINGS = registerItem("slime_leggings",
            new ArmorItem(ModArmorMaterial.SLIME, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item SLIME_BOOTS = registerItem("slime_boots",
            new SlimeBootsItem(ModArmorMaterial.SLIME, new FabricItemSettings()));

    // Spawn Eggs
    public static final Item SLIME_GOLEM_SPAWN_EGG = registerItem("slime_golem_spawn_egg",
            new SpawnEggItem(ModEntities.SLIME_GOLEM, 0x2E8B57, 0x78F096, new FabricItemSettings()));
    public static final Item GELATINOUS_CREEPER_SPAWN_EGG = registerItem("gelatinous_creeper_spawn_egg",
            new SpawnEggItem(ModEntities.GELATINOUS_CREEPER, 0x1C5826, 0xB4FFC8, new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(SlimeRealmMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        SlimeRealmMod.LOGGER.info("Registering Mod Items for " + SlimeRealmMod.MOD_ID);
    }
}
