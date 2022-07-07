package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class IceEffectTileEntity extends EffectTileEntity {

    public IceEffectTileEntity(BlockPos pos, BlockState state) {
        this(TileEntityRegistry.ICE_EFFECT.get(), pos, state);
    }

    protected IceEffectTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected double getRange() {
        return 8;
    }

    @Override
    protected int getPeriod() {
        return 300;
    }

    @Override
    protected void applyEntityEffect(Entity entity) {
        if (entity instanceof LivingEntity) {
            EffectHelper.applyFreeze((LivingEntity) entity, 15);
        }
    }

    @Override
    protected void applyBlockEffect(Level world, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getFluidState().is(FluidTags.WATER)) {
            level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
        } else if (level.isEmptyBlock(pos)) {
            BlockState snow = Blocks.SNOW.defaultBlockState();
            if (snow.canSurvive(level, pos)) {
                level.setBlockAndUpdate(pos, snow);
            }
        } else if (state.is(Blocks.SNOW)) {
            BlockState snow = state.setValue(SnowLayerBlock.LAYERS, Math.min(state.getValue(SnowLayerBlock.LAYERS) + 1, 8));
            if (snow.canSurvive(level, pos)) {
                level.setBlockAndUpdate(pos, snow);
            }
        }
    }
}
