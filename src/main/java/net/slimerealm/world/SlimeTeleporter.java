package net.slimerealm.world;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.slimerealm.registry.ModBlocks;
import net.slimerealm.registry.ModDimensions;

public class SlimeTeleporter {
    public static void teleport(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        boolean inSlimeRealm = player.getWorld().getRegistryKey().equals(ModDimensions.SLIME_REALM_WORLD);
        RegistryKey<World> targetKey = inSlimeRealm ? World.OVERWORLD : ModDimensions.SLIME_REALM_WORLD;
        ServerWorld destinationWorld = server.getWorld(targetKey);

        if (destinationWorld == null) return;

        BlockPos playerPos = player.getBlockPos();
        // Force chunk load at destination
        destinationWorld.getChunk(playerPos.getX() >> 4, playerPos.getZ() >> 4);

        // 1. Search for an existing Slime Portal nearby (within 24 blocks)
        BlockPos existingPortal = findExistingPortal(destinationWorld, playerPos, 24);
        if (existingPortal != null) {
            BlockPos standPos = findSafeStandNearPortal(destinationWorld, existingPortal);
            player.teleport(destinationWorld, standPos.getX() + 0.5, standPos.getY(), standPos.getZ() + 0.5, player.getYaw(), player.getPitch());
            player.fallDistance = 0.0F;
            return;
        }

        // 2. Otherwise build a full Spawn Sanctuary Platform with an active Return Portal
        BlockPos spawnPos = buildSanctuaryAndPortal(destinationWorld, playerPos);
        player.teleport(destinationWorld, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, player.getYaw(), player.getPitch());
        player.fallDistance = 0.0F;
    }

    private static BlockPos findExistingPortal(ServerWorld world, BlockPos center, int radius) {
        int minY = Math.max(world.getBottomY() + 5, 40);
        int maxY = Math.min(world.getTopY() - 5, 160);
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                for (int y = maxY; y >= minY; y--) {
                    BlockPos p = new BlockPos(center.getX() + dx, y, center.getZ() + dz);
                    if (world.getBlockState(p).isOf(ModBlocks.SLIME_PORTAL)) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    private static BlockPos findSafeStandNearPortal(ServerWorld world, BlockPos portalPos) {
        BlockPos stand = portalPos.add(0, 0, 2);
        if (!world.getBlockState(stand.down()).isSolidBlock(world, stand.down())) {
            world.setBlockState(stand.down(), ModBlocks.GELSTONE_BRICKS.getDefaultState(), 3);
        }
        world.setBlockState(stand, Blocks.AIR.getDefaultState(), 3);
        world.setBlockState(stand.up(), Blocks.AIR.getDefaultState(), 3);
        return stand;
    }

    private static BlockPos buildSanctuaryAndPortal(ServerWorld world, BlockPos origin) {
        int x = origin.getX();
        int z = origin.getZ();

        int topY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        if (topY <= world.getBottomY() + 10 || topY < 63 || topY > 200) {
            topY = 72;
        }

        BlockPos center = new BlockPos(x, topY, z);

        // Build 7x7 Gelstone Bricks platform and clear 7x6x7 air space above
        for (int dx = -3; dx <= 3; dx++) {
            for (int dz = -3; dz <= 3; dz++) {
                BlockPos floorPos = center.add(dx, -1, dz);
                if (Math.abs(dx) == 3 || Math.abs(dz) == 3) {
                    world.setBlockState(floorPos, ModBlocks.SLIME_PLANKS.getDefaultState(), 3);
                } else {
                    world.setBlockState(floorPos, ModBlocks.GELSTONE_BRICKS.getDefaultState(), 3);
                }
                // Support foundation underneath
                BlockPos subPos = center.add(dx, -2, dz);
                if (!world.getBlockState(subPos).isSolidBlock(world, subPos)) {
                    world.setBlockState(subPos, ModBlocks.GELSTONE.getDefaultState(), 3);
                }
                // Clear space above platform
                for (int dy = 0; dy <= 5; dy++) {
                    world.setBlockState(center.add(dx, dy, dz), Blocks.AIR.getDefaultState(), 3);
                }
            }
        }

        // Corner glowing lantern pillars
        int[][] corners = {{-3, -3}, {3, -3}, {-3, 3}, {3, 3}};
        for (int[] c : corners) {
            world.setBlockState(center.add(c[0], 0, c[1]), ModBlocks.SLIME_LOG.getDefaultState(), 3);
            world.setBlockState(center.add(c[0], 1, c[1]), ModBlocks.SLIME_LOG.getDefaultState(), 3);
            world.setBlockState(center.add(c[0], 2, c[1]), ModBlocks.SLIME_LANTERN.getDefaultState(), 3);
        }

        // Build 4x5 Return Portal at dz = -2 (X from -1 to +2, Y from 0 to 4)
        BlockState frame = Blocks.SLIME_BLOCK.getDefaultState();
        BlockState portal = ModBlocks.SLIME_PORTAL.getDefaultState().with(Properties.HORIZONTAL_AXIS, Direction.Axis.X);
        for (int px = -1; px <= 2; px++) {
            world.setBlockState(center.add(px, 0, -2), frame, 3);
            world.setBlockState(center.add(px, 4, -2), frame, 3);
        }
        for (int py = 1; py <= 3; py++) {
            world.setBlockState(center.add(-1, py, -2), frame, 3);
            world.setBlockState(center.add(2, py, -2), frame, 3);
            world.setBlockState(center.add(0, py, -2), portal, 3);
            world.setBlockState(center.add(1, py, -2), portal, 3);
        }

        // Return safe player stand position in front of the portal (dz = +1)
        return center.add(0, 0, 1);
    }
}
