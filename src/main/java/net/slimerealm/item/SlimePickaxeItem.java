package net.slimerealm.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ItemScatterer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SlimePickaxeItem extends PickaxeItem {
    public SlimePickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient() && state.getHardness(world, pos) > 0.0F) {
            // 25% chance to drop 1-2 extra slime balls upon mining
            if (world.random.nextFloat() < 0.25F) {
                int count = 1 + world.random.nextInt(2);
                ItemScatterer.spawn(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Items.SLIME_BALL, count));
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
