package io.github.davidqf555.minecraft.yggdrasil.common.world;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraft.world.gen.surfacebuilders.ValleySurfaceBuilder;

public class MuspelheimSurfaceBuilder extends ValleySurfaceBuilder {

    private static final ImmutableList<BlockState> BLOCKS = ImmutableList.of(Blocks.BASALT.defaultBlockState(), Blocks.MAGMA_BLOCK.defaultBlockState(), Blocks.BLACKSTONE.defaultBlockState());
    private static final BlockState PATCH = Blocks.BLACKSTONE.defaultBlockState();

    public MuspelheimSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }

    @Override
    protected ImmutableList<BlockState> getFloorBlockStates() {
        return BLOCKS;
    }

    @Override
    protected ImmutableList<BlockState> getCeilingBlockStates() {
        return BLOCKS;
    }

    @Override
    protected BlockState getPatchBlockState() {
        return PATCH;
    }
}
