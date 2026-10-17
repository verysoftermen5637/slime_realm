package net.slimerealm.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.slimerealm.entity.SlimeGolemEntity;
import net.slimerealm.registry.ModBlocks;
import net.slimerealm.registry.ModEntities;
import net.slimerealm.registry.ModItems;

public class SlimeVillageFeature extends Feature<DefaultFeatureConfig> {
    public SlimeVillageFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();

        int y = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ());
        if (y < 63 || y > 125) return false;

        BlockPos center = new BlockPos(origin.getX(), y, origin.getZ());
        BlockState planks = ModBlocks.SLIME_PLANKS.getDefaultState();
        BlockState log = ModBlocks.SLIME_LOG.getDefaultState();
        BlockState bricks = ModBlocks.GELSTONE_BRICKS.getDefaultState();
        BlockState fence = ModBlocks.SLIME_FENCE.getDefaultState();
        BlockState lantern = ModBlocks.SLIME_LANTERN.getDefaultState();

        // Build Slime Alchemist Guild House (7x6x7)
        for (int dy = -2; dy <= 5; dy++) {
            for (int dx = -3; dx <= 3; dx++) {
                for (int dz = -3; dz <= 3; dz++) {
                    BlockPos p = center.add(dx, dy, dz);
                    boolean isWall = Math.abs(dx) == 3 || Math.abs(dz) == 3;
                    boolean isCorner = Math.abs(dx) == 3 && Math.abs(dz) == 3;

                    if (dy <= 0) {
                        world.setBlockState(p, dy == 0 ? planks : bricks, 2);
                    } else if (dy <= 3) {
                        if (isCorner) {
                            world.setBlockState(p, log, 2);
                        } else if (isWall) {
                            if ((dy == 1 || dy == 2) && dx == 0 && dz == 3) {
                                world.setBlockState(p, Blocks.AIR.getDefaultState(), 2); // Doorway
                            } else if (dy == 2 && (dx == 0 || dz == 0)) {
                                world.setBlockState(p, fence, 2); // Windows
                            } else {
                                world.setBlockState(p, planks, 2);
                            }
                        } else {
                            world.setBlockState(p, Blocks.AIR.getDefaultState(), 2);
                        }
                    } else if (dy == 4) {
                        world.setBlockState(p, ModBlocks.SLIME_SLAB.getDefaultState(), 2);
                    } else if (dy == 5 && Math.abs(dx) <= 2 && Math.abs(dz) <= 2) {
                        world.setBlockState(p, planks, 2);
                    }
                }
            }
        }

        // Interior furnishings: Chandelier, Brewing Stand (Alchemist Workstation), and Village Chest
        world.setBlockState(center.up(3), lantern, 2);
        world.setBlockState(center.add(-2, 1, -2), log, 2);
        world.setBlockState(center.add(-2, 2, -2), Blocks.BREWING_STAND.getDefaultState(), 2);

        BlockPos chestPos = center.add(2, 1, -2);
        world.setBlockState(chestPos, Blocks.CHEST.getDefaultState(), 2);
        BlockEntity be = world.getBlockEntity(chestPos);
        if (be instanceof ChestBlockEntity chest) {
            chest.setStack(0, new ItemStack(Items.EMERALD, 4 + random.nextInt(6)));
            chest.setStack(1, new ItemStack(ModItems.SLIME_GEM, 2 + random.nextInt(4)));
            chest.setStack(2, new ItemStack(ModBlocks.SLIME_PLANKS, 16));
            chest.setStack(3, new ItemStack(ModBlocks.SLIME_SAPLING, 4));
            chest.setStack(4, new ItemStack(Items.BREAD, 6));
            chest.setStack(13, new ItemStack(ModItems.SLIME_INGOT, 2 + random.nextInt(3)));
        }

        // Spawn 2 Villagers inside/near the house
        for (int i = 0; i < 2; i++) {
            VillagerEntity villager = EntityType.VILLAGER.create(world.toServerWorld());
            if (villager != null) {
                villager.refreshPositionAndAngles(center.getX() + (i == 0 ? -0.5 : 1.0), center.getY() + 1, center.getZ() + 0.5, 0.0F, 0.0F);
                villager.setPersistent();
                world.spawnEntity(villager);
            }
        }

        // Spawn 1 defending Slime Golem outside
        SlimeGolemEntity golem = ModEntities.SLIME_GOLEM.create(world.toServerWorld());
        if (golem != null) {
            golem.refreshPositionAndAngles(center.getX() + 0.5, center.getY() + 1, center.getZ() + 4.5, 0.0F, 0.0F);
            golem.setPersistent();
            world.spawnEntity(golem);
        }

        return true;
    }
}
