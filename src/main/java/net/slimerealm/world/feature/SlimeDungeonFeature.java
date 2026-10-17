package net.slimerealm.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.slimerealm.entity.GelatinousCreeperEntity;
import net.slimerealm.registry.ModBlocks;
import net.slimerealm.registry.ModEntities;
import net.slimerealm.registry.ModItems;

public class SlimeDungeonFeature extends Feature<DefaultFeatureConfig> {
    public SlimeDungeonFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();

        int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ());
        if (y < 63 || y > 130) return false;

        BlockPos center = new BlockPos(origin.getX(), y, origin.getZ());
        BlockState bricks = ModBlocks.GELSTONE_BRICKS.getDefaultState();
        BlockState log = ModBlocks.SLIME_LOG.getDefaultState();
        BlockState lantern = ModBlocks.SLIME_LANTERN.getDefaultState();
        BlockState slime = Blocks.SLIME_BLOCK.getDefaultState();

        // Build a 7x10x7 Battle Tower (2 floors + battlements)
        for (int dy = -2; dy <= 9; dy++) {
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos p = center.add(dx, dy, dz);
                    boolean isWall = Math.abs(dx) == 3 || Math.abs(dz) == 3;
                    boolean isCorner = Math.abs(dx) == 3 && Math.abs(dz) == 3;

                    if (dy <= 0) {
                        world.setBlockState(p, bricks, 2);
                    } else if (dy == 5) {
                        // Second floor ceiling/floor with central bouncy elevator hole
                        if (Math.abs(dx) <= 1 && Math.abs(dz) <= 1) {
                            world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                        } else {
                            world.setBlockState(p, bricks, 2);
                        }
                    } else if (dy == 9) {
                        // Battlements
                        if (isWall && (dx + dz) % 2 == 0) {
                            world.setBlockState(p, bricks, 2);
                        } else if (isCorner) {
                            world.setBlockState(p, lantern, 2);
                        } else {
                            world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                        }
                    } else {
                        if (isCorner) {
                            world.setBlockState(p, log, 2);
                        } else if (isWall) {
                            // Entrance doorway on ground floor
                            if ((dy == 1 || dy == 2) && dx == 0 && dz == 3) {
                                world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                            } else if ((dy == 3 || dy == 7) && (dx == 0 || dz == 0)) {
                                world.setBlockState(p, ModBlocks.SLIME_FENCE.getDefaultState(), 2);
                            } else {
                                world.setBlockState(p, bricks, 2);
                            }
                        } else {
                            world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
            }
        }

        // Central bouncy trampoline pad on ground floor
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                world.setBlockState(center.add(dx, 0, dz), slime, 2);
            }
        }
        world.setBlockState(center, lantern, 2);

        // Place Dungeon Loot Chests on Floor 1 and Floor 2
        placeDungeonChest(world, center.add(-2, 1, -2), random, false);
        placeDungeonChest(world, center.add(2, 6, -2), random, true);

        // Spawn Gelatinous Creeper guardians
        for (int i = 0; i < 2; i++) {
            GelatinousCreeperEntity creeper = ModEntities.GELATINOUS_CREEPER.create(world.toServerWorld());
            if (creeper != null) {
                creeper.refreshPositionAndAngles(center.getX() + (i == 0 ? -1.5 : 1.5), center.getY() + 1, center.getZ() + 0.5, 0.0F, 0.0F);
                creeper.setPersistent();
                world.spawnEntity(creeper);
            }
        }

        return true;
    }

    private void placeDungeonChest(StructureWorldAccess world, BlockPos pos, Random random, boolean isTopFloor) {
        world.setBlockState(pos, Blocks.CHEST.getDefaultState(), 2);
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ChestBlockEntity chest) {
            chest.setStack(0, new ItemStack(ModItems.SLIME_GEM, 3 + random.nextInt(5)));
            chest.setStack(1, new ItemStack(ModItems.SLIME_INGOT, 2 + random.nextInt(4)));
            chest.setStack(2, new ItemStack(Items.SLIME_BALL, 8 + random.nextInt(12)));
            chest.setStack(4, new ItemStack(Items.GOLDEN_APPLE, 1 + random.nextInt(2)));
            chest.setStack(13, new ItemStack(isTopFloor ? ModItems.SLIME_SWORD : ModItems.SLIME_CORE_IGNITER, 1));
            if (isTopFloor) {
                chest.setStack(14, new ItemStack(ModItems.SLIME_BOOTS, 1));
                chest.setStack(15, new ItemStack(Items.DIAMOND, 1 + random.nextInt(3)));
            } else {
                chest.setStack(14, new ItemStack(ModItems.SLIME_PICKAXE, 1));
            }
        }
    }
}
