package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class FireEffectTileEntity extends EffectTileEntity {

    public FireEffectTileEntity(BlockPos pos, BlockState state) {
        this(TileEntityRegistry.FIRE_EFFECT.get(), pos, state);
    }

    protected FireEffectTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected double getRange() {
        return 8;
    }

    @Override
    protected int getPeriod() {
        return 100;
    }

    @Override
    protected void applyEntityEffect(Entity entity) {
        EffectHelper.applyFire(entity, 5);
    }

    @Override
    protected void applyBlockEffect(Level world, BlockPos pos) {
        if (level.isEmptyBlock(pos)) {
            level.setBlockAndUpdate(pos, Blocks.FIRE.getStateForPlacement(new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, BlockHitResult.miss(Vec3.atCenterOf(pos), Direction.NORTH, pos))));
        }
    }
}
