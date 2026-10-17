package net.slimerealm.item;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.slimerealm.registry.ModBlocks;

public class SlimeCoreIgniterItem extends Item {
    public SlimeCoreIgniterItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos clickedPos = context.getBlockPos();
        Direction side = context.getSide();
        BlockPos targetPos = clickedPos.offset(side);

        if (!world.getBlockState(targetPos).isAir()) {
            return ActionResult.PASS;
        }

        // Try creating portal along X axis or Z axis
        if (tryCreatePortal(world, targetPos, Direction.Axis.X) || tryCreatePortal(world, targetPos, Direction.Axis.Z)) {
            world.playSound(player, targetPos, SoundEvents.BLOCK_SLIME_BLOCK_PLACE, SoundCategory.BLOCKS, 1.0F, 1.2F);
            if (player != null) {
                context.getStack().damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
            }
            return ActionResult.SUCCESS;
        }

        return ActionResult.PASS;
    }

    private boolean tryCreatePortal(World world, BlockPos origin, Direction.Axis axis) {
        // Check frame dimensions: 4 wide x 5 high with slime blocks, interior 2 wide x 3 high
        Direction step = (axis == Direction.Axis.X) ? Direction.EAST : Direction.SOUTH;

        for (int offset = -2; offset <= 1; offset++) {
            BlockPos bottomStart = origin.offset(step, offset);
            if (validateAndFill(world, bottomStart, step, axis)) {
                return true;
            }
        }
        return false;
    }

    private boolean validateAndFill(World world, BlockPos bottomLeftInterior, Direction step, Direction.Axis axis) {
        // Interior is 2 wide (step, 0 to 1) and 3 high (y, 0 to 2)
        // Check bottom border (y = -1, step = -1 to 2)
        for (int i = -1; i <= 2; i++) {
            if (!isFrameBlock(world.getBlockState(bottomLeftInterior.offset(step, i).down()))) return false;
            if (!isFrameBlock(world.getBlockState(bottomLeftInterior.offset(step, i).up(3)))) return false;
        }
        // Check side borders (y = 0 to 2, step = -1 and step = 2)
        for (int y = 0; y < 3; y++) {
            if (!isFrameBlock(world.getBlockState(bottomLeftInterior.offset(step, -1).up(y)))) return false;
            if (!isFrameBlock(world.getBlockState(bottomLeftInterior.offset(step, 2).up(y)))) return false;
        }
        // Check interior is air
        for (int y = 0; y < 3; y++) {
            for (int i = 0; i < 2; i++) {
                BlockPos p = bottomLeftInterior.offset(step, i).up(y);
                if (!world.getBlockState(p).isAir() && !world.getBlockState(p).isOf(ModBlocks.SLIME_PORTAL)) {
                    return false;
                }
            }
        }
        // Fill interior with Slime Portal
        BlockState portalState = ModBlocks.SLIME_PORTAL.getDefaultState().with(Properties.HORIZONTAL_AXIS, axis);
        for (int y = 0; y < 3; y++) {
            for (int i = 0; i < 2; i++) {
                BlockPos p = bottomLeftInterior.offset(step, i).up(y);
                world.setBlockState(p, portalState, 3);
            }
        }
        return true;
    }

    private boolean isFrameBlock(BlockState state) {
        return state.isOf(Blocks.SLIME_BLOCK);
    }
}
