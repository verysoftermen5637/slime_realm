package net.slimerealm.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.slimerealm.registry.ModItems;

import java.util.List;

public class GelatinousCreeperEntity extends CreeperEntity {
    public GelatinousCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        if (this.isAlive() && !this.getWorld().isClient()) {
            if (this.getClientFuseTime(1.0F) >= 0.9F) {
                this.explodeGel();
                return;
            }
        }
        super.tick();
    }

    public void explodeGel() {
        if (!this.getWorld().isClient()) {
            ServerWorld serverWorld = (ServerWorld) this.getWorld();
            double x = this.getX();
            double y = this.getY();
            double z = this.getZ();

            serverWorld.spawnParticles(ParticleTypes.ITEM_SLIME, x, y + 0.5, z, 160, 2.5, 1.0, 2.5, 0.15);
            this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_BREAK, 2.0F, 0.7F);
            this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.5F, 1.4F);

            Box area = new Box(x - 6, y - 2, z - 6, x + 6, y + 4, z + 6);
            List<LivingEntity> targets = serverWorld.getNonSpectatingEntities(LivingEntity.class, area);

            for (LivingEntity target : targets) {
                if (target != this) {
                    Vec3d push = target.getPos().subtract(this.getPos()).normalize().multiply(1.5);
                    target.setVelocity(push.x, 1.2, push.z);
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 3), this);
                    target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 100, 0), this);
                    target.velocityModified = true;
                }
            }
            this.discard();
        }
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.getWorld().isClient()) {
            this.dropStack(new ItemStack(ModItems.SLIME_GEM, 1 + this.random.nextInt(3)));
            this.dropStack(new ItemStack(Items.SLIME_BALL, 2 + this.random.nextInt(4)));
        }
    }
}
