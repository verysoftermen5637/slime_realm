package net.slimerealm.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.slimerealm.registry.ModItems;

public class SlimeGolemEntity extends IronGolemEntity {
    public SlimeGolemEntity(EntityType<? extends IronGolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean result = super.damage(source, amount);
        if (result && source.getAttacker() instanceof LivingEntity attacker) {
            Vec3d diff = attacker.getPos().subtract(this.getPos()).normalize().multiply(1.8);
            attacker.setVelocity(diff.x, 0.65, diff.z);
            attacker.velocityModified = true;
            this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_FALL, 1.5F, 1.0F);
        }
        return result;
    }

    @Override
    public boolean tryAttack(net.minecraft.entity.Entity target) {
        boolean success = super.tryAttack(target);
        if (success && target instanceof LivingEntity living) {
            living.setVelocity(living.getVelocity().x, 1.15, living.getVelocity().z);
            living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 140, 2), this);
            living.velocityModified = true;
            this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.2F, 0.8F);
        }
        return success;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (!this.getWorld().isClient()) {
            this.dropStack(new ItemStack(ModItems.SLIME_INGOT, 2 + this.random.nextInt(3)));
            this.dropStack(new ItemStack(Items.SLIME_BALL, 3 + this.random.nextInt(5)));
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }
}
