package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public class TileEntityBlock<T extends EffectTileEntity> extends BaseEntityBlock implements BlockEntityTicker<T> {

    private final Supplier<BlockEntityType<T>> type;
    private final BiFunction<BlockPos, BlockState, T> factory;

    public TileEntityBlock(Supplier<BlockEntityType<T>> type, BiFunction<BlockPos, BlockState, T> factory, Properties properties) {
        super(properties);
        this.factory = factory;
        this.type = type;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <E extends BlockEntity> BlockEntityTicker<E> getTicker(Level world, BlockState state, BlockEntityType<E> type) {
        return createTickerHelper(type, this.type.get(), this);
    }

    @Nullable
    @Override
    public T newBlockEntity(BlockPos pos, BlockState state) {
        return factory.apply(pos, state);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state, T te) {
        te.tick(level, pos, state);
    }
}
