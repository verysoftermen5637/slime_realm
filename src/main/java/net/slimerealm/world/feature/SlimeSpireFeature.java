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

public class SlimeSpireFeature extends Feature<DefaultFeatureConfig> {
    public SlimeSpireFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        BlockPos origin = context.getOrigin();

        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, origin.getX(), origin.getZ());
        BlockPos base = new BlockPos(origin.getX(), y - 1, origin.getZ());

        if (y < 62) return false;

        int height = 7 + random.nextInt(6);
        for (int dy = 0; dy < height; dy++) {
            BlockPos current = base.up(dy);
            BlockState core = (dy % 3 == 0) ? Blocks.SLIME_BLOCK.getDefaultState() : ModBlocks.GELSTONE.getDefaultState();
            world.setBlockState(current, core, 2);

            if (dy < height / 2) {
                world.setBlockState(current.north(), ModBlocks.GELSTONE.getDefaultState(), 2);
                world.setBlockState(current.south(), ModBlocks.GELSTONE.getDefaultState(), 2);
                world.setBlockState(current.east(), ModBlocks.GELSTONE.getDefaultState(), 2);
                world.setBlockState(current.west(), ModBlocks.GELSTONE.getDefaultState(), 2);
            }
        }
        world.setBlockState(base.up(height), ModBlocks.SLIME_LANTERN.getDefaultState(), 2);
        return true;
    }
}
