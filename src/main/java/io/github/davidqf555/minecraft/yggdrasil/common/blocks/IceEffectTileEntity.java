package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IceEffectTileEntity extends EffectTileEntity {

    public IceEffectTileEntity() {
        this(TileEntityRegistry.ICE_EFFECT.get());
    }

    protected IceEffectTileEntity(TileEntityType<?> type) {
        super(type);
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
    protected void applyBlockEffect(World world, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        if (state.getFluidState().is(FluidTags.WATER)) {
            level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
        } else if (level.isEmptyBlock(pos)) {
            BlockState snow = Blocks.SNOW.defaultBlockState();
            if (snow.canSurvive(level, pos)) {
                level.setBlockAndUpdate(pos, snow);
            }
        } else if (state.is(Blocks.SNOW)) {
            BlockState snow = state.setValue(SnowBlock.LAYERS, Math.min(state.getValue(SnowBlock.LAYERS) + 1, 8));
            if (snow.canSurvive(level, pos)) {
                level.setBlockAndUpdate(pos, snow);
            }
        }
    }
}
