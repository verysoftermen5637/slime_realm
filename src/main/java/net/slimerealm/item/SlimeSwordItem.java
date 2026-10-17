package net.slimerealm.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;

public class SlimeSwordItem extends SwordItem {
    public SlimeSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // Apply Slowness II for 5 seconds (100 ticks)
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 1), attacker);

        // Elastic recoil knockback: push target away with extra bounce force
        double dx = target.getX() - attacker.getX();
        double dz = target.getZ() - attacker.getZ();
        target.takeKnockback(1.5, -dx, -dz);
        target.velocityModified = true;

        return super.postHit(stack, target, attacker);
    }
}
