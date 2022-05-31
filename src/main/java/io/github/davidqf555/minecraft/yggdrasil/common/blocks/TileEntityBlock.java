package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

public class TileEntityBlock extends Block {

    private final BiFunction<BlockState, IBlockReader, TileEntity> factory;

    public TileEntityBlock(BiFunction<BlockState, IBlockReader, TileEntity> factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return factory.apply(state, world);
    }
}
