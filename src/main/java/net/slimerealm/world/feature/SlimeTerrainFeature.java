package net.slimerealm.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.slimerealm.registry.ModBlocks;

public class SlimeTerrainFeature extends Feature<DefaultFeatureConfig> {
    public SlimeTerrainFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos origin = context.getOrigin();
        Random random = context.getRandom();

        int chunkStartX = (origin.getX() >> 4) << 4;
        int chunkStartZ = (origin.getZ() >> 4) << 4;

        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (int dx = 0; dx < 16; dx++) {
            for (int dz = 0; dz < 16; dz++) {
                int x = chunkStartX + dx;
                int z = chunkStartZ + dz;
                int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z) - 1;

                for (int y = topY; y >= Math.max(world.getBottomY() + 1, topY - 12); y--) {
                    mutable.set(x, y, z);
                    BlockState state = world.getBlockState(mutable);

                    if (state.isOf(Blocks.GRASS_BLOCK) || state.isOf(Blocks.PODZOL) || state.isOf(Blocks.MYCELIUM)) {
                        world.setBlockState(mutable, ModBlocks.SLIME_GRASS_BLOCK.getDefaultState(), 2);
                        // Occasional glowing crystal node or bouncy pad on surface
                        if (random.nextInt(140) == 0 && world.getBlockState(mutable.up()).isAir()) {
                            world.setBlockState(mutable.up(), ModBlocks.SLIME_LANTERN.getDefaultState(), 2);
                        }
                    } else if (state.isOf(Blocks.DIRT) || state.isOf(Blocks.COARSE_DIRT) || state.isOf(Blocks.ROOTED_DIRT)) {
                        world.setBlockState(mutable, ModBlocks.SLIME_DIRT.getDefaultState(), 2);
                    } else if (state.isOf(Blocks.SAND) || state.isOf(Blocks.RED_SAND) || state.isOf(Blocks.GRAVEL) || state.isOf(Blocks.CLAY)) {
                        if (y == topY && random.nextInt(8) == 0) {
                            world.setBlockState(mutable, ModBlocks.SLIME_LANTERN.getDefaultState(), 2);
                        } else {
                            world.setBlockState(mutable, Blocks.SLIME_BLOCK.getDefaultState(), 2);
                        }
                    } else if (state.isOf(Blocks.STONE) || state.isOf(Blocks.GRANITE) || state.isOf(Blocks.DIORITE) || state.isOf(Blocks.ANDESITE)) {
                        world.setBlockState(mutable, ModBlocks.GELSTONE.getDefaultState(), 2);
                    }
                }
            }
        }
        return true;
    }
}
