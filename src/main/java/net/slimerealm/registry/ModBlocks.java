package net.slimerealm.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.slimerealm.SlimeRealmMod;
import net.slimerealm.block.SlimeGrassBlock;
import net.slimerealm.block.SlimeLeavesBlock;
import net.slimerealm.block.SlimePortalBlock;
import net.slimerealm.world.tree.SlimeSaplingGenerator;

public class ModBlocks {
    // Dimension Terrain Blocks
    public static final Block SLIME_GRASS_BLOCK = registerBlock("slime_grass_block",
            new SlimeGrassBlock(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).mapColor(MapColor.EMERALD_GREEN).strength(0.6f).sounds(BlockSoundGroup.SLIME)));
    public static final Block SLIME_DIRT = registerBlock("slime_dirt",
            new Block(FabricBlockSettings.copyOf(Blocks.DIRT).mapColor(MapColor.PALE_GREEN).strength(0.5f).sounds(BlockSoundGroup.SLIME)));
    public static final Block GELSTONE = registerBlock("gelstone",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.DARK_GREEN).strength(1.8f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block GELSTONE_BRICKS = registerBlock("gelstone_bricks",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).mapColor(MapColor.DARK_GREEN).strength(2.2f, 6.0f).sounds(BlockSoundGroup.STONE)));
    public static final Block SLIME_LANTERN = registerBlock("slime_lantern",
            new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN).mapColor(MapColor.EMERALD_GREEN).strength(0.8f).luminance(state -> 15).sounds(BlockSoundGroup.GLASS)));

    // Wood Set
    public static final Block SLIME_LOG = registerBlock("slime_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).mapColor(MapColor.PALE_GREEN).sounds(BlockSoundGroup.WOOD)));
    public static final Block STRIPPED_SLIME_LOG = registerBlock("stripped_slime_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.PALE_GREEN).sounds(BlockSoundGroup.WOOD)));
    public static final Block SLIME_PLANKS = registerBlock("slime_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).mapColor(MapColor.PALE_GREEN).sounds(BlockSoundGroup.WOOD)));
    public static final Block SLIME_LEAVES = registerBlock("slime_leaves",
            new SlimeLeavesBlock(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).nonOpaque().sounds(BlockSoundGroup.SLIME)));
    public static final Block SLIME_SAPLING = registerBlock("slime_sapling",
            new SaplingBlock(new SlimeSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).nonOpaque().noCollision().sounds(BlockSoundGroup.GRASS)));

    // Wooden Decorative Blocks
    public static final Block SLIME_SLAB = registerBlock("slime_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_SLAB).sounds(BlockSoundGroup.WOOD)));
    public static final Block SLIME_STAIRS = registerBlock("slime_stairs",
            new StairsBlock(ModBlocks.SLIME_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_STAIRS).sounds(BlockSoundGroup.WOOD)));
    public static final Block SLIME_FENCE = registerBlock("slime_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE).sounds(BlockSoundGroup.WOOD)));
    public static final Block SLIME_FENCE_GATE = registerBlock("slime_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_FENCE_GATE), WoodType.OAK));

    // Ores
    public static final Block SLIME_ORE = registerBlock("slime_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.IRON_ORE).strength(3.0f, 3.0f), UniformIntProvider.create(2, 5)));
    public static final Block DEEPSLATE_SLIME_ORE = registerBlock("deepslate_slime_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_IRON_ORE).strength(4.5f, 3.0f), UniformIntProvider.create(2, 5)));

    // Portal
    public static final Block SLIME_PORTAL = Registry.register(Registries.BLOCK,
            new Identifier(SlimeRealmMod.MOD_ID, "slime_portal"),
            new SlimePortalBlock(FabricBlockSettings.copyOf(Blocks.NETHER_PORTAL).noCollision().ticksRandomly().strength(-1.0f).sounds(BlockSoundGroup.SLIME).luminance(state -> 12)));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(SlimeRealmMod.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(SlimeRealmMod.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        SlimeRealmMod.LOGGER.info("Registering Mod Blocks for " + SlimeRealmMod.MOD_ID);
    }
}
