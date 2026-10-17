package net.slimerealm.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.slimerealm.registry.ModItems;

public class SlimeBootsItem extends ArmorItem {
    public SlimeBootsItem(ArmorMaterial material, Settings settings) {
        super(material, Type.BOOTS, settings);
    }

    public static void handlePlayerTick(PlayerEntity player) {
        ItemStack boots = player.getEquippedStack(EquipmentSlot.FEET);
        if (boots.getItem() instanceof SlimeBootsItem) {
            player.fallDistance = 0.0F;
            if (player.isSprinting()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 10, 1, true, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 0, true, false, true));
            }
        }

        // Full Slime Armor Set Bonus: Regeneration I & Resistance I
        ItemStack helmet = player.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack legs = player.getEquippedStack(EquipmentSlot.LEGS);
        if (helmet.isOf(ModItems.SLIME_HELMET) && chest.isOf(ModItems.SLIME_CHESTPLATE)
                && legs.isOf(ModItems.SLIME_LEGGINGS) && boots.isOf(ModItems.SLIME_BOOTS)) {
            if (player.age % 40 == 0) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 60, 0, true, false, true));
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 0, true, false, true));
            }
        }
    }
}
