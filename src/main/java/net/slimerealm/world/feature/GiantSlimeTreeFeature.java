package net.slimerealm.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.slimerealm.registry.ModBlocks;

public class GiantSlimeTreeFeature extends Feature<DefaultFeatureConfig> {
    public GiantSlimeTreeFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();

        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, origin.getX(), origin.getZ());
        BlockPos pos = new BlockPos(origin.getX(), y, origin.getZ());
        BlockState ground = world.getBlockState(pos.down());

        if (!ground.isOf(ModBlocks.SLIME_GRASS_BLOCK) && !ground.isOf(ModBlocks.SLIME_DIRT)
                && !ground.isOf(Blocks.GRASS_BLOCK) && !ground.isOf(Blocks.DIRT)) {
            return false;
        }

        if (!world.getBlockState(pos).isAir()) {
            return false;
        }

        BlockState log = ModBlocks.SLIME_LOG.getDefaultState();
        BlockState leaves = ModBlocks.SLIME_LEAVES.getDefaultState().with(LeavesBlock.PERSISTENT, true);
        BlockState lantern = ModBlocks.SLIME_LANTERN.getDefaultState();

        boolean isGiant = random.nextInt(3) == 0;
        int height = isGiant ? (9 + random.nextInt(4)) : (5 + random.nextInt(3));

        // Build trunk
        for (int dy = 0; dy < height; dy++) {
            world.setBlockState(pos.up(dy), log, 2);
        }

        // If giant, add root buttresses at base
        if (isGiant) {
            world.setBlockState(pos.north(), log, 2);
            world.setBlockState(pos.south(), log, 2);
            world.setBlockState(pos.east(), log, 2);
            world.setBlockState(pos.west(), log, 2);
        }

        // Build bouncy mushroom/umbrella canopy
        int canopyRadius = isGiant ? 3 : 2;
        BlockPos top = pos.up(height);

        for (int cy = -1; cy <= 1; cy++) {
            int r = (cy == 1) ? canopyRadius - 1 : canopyRadius;
            for (int dx = -r; dx <= r; dx++) {
                for (int dz = -r; dz <= r; dz++) {
                    if (Math.abs(dx) == r && Math.abs(dz) == r && cy != 0) continue;
                    BlockPos leafPos = top.add(dx, cy, dz);
                    if (world.getBlockState(leafPos).isAir()) {
                        world.setBlockState(leafPos, leaves, 2);
                    }
                }
            }
        }

        // Place glowing Slime Lantern cores underneath canopy
        world.setBlockState(top.add(1, -1, 0), lantern, 2);
        world.setBlockState(top.add(-1, -1, 0), lantern, 2);
        if (isGiant) {
            world.setBlockState(top.add(0, -1, 1), lantern, 2);
            world.setBlockState(top.add(0, -1, -1), lantern, 2);
        }

        return true;
    }
}
