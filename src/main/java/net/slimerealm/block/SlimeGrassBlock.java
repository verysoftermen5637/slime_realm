package net.slimerealm.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class SlimeGrassBlock extends Block {
    public SlimeGrassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            // Soften fall damage on slime grass by 80%
            entity.handleFallDamage(fallDistance, 0.2F, world.getDamageSources().fall());
        }
    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        if (entity.bypassesLandingEffects()) {
            super.onEntityLand(world, entity);
        } else {
            Vec3d v = entity.getVelocity();
            if (v.y < -0.5) {
                double factor = entity instanceof LivingEntity ? 0.65 : 0.5;
                entity.setVelocity(v.x, -v.y * factor, v.z);
                entity.velocityModified = true;
            } else {
                super.onEntityLand(world, entity);
            }
        }
    }
}
