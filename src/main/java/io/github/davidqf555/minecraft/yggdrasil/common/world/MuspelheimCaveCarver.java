package io.github.davidqf555.minecraft.yggdrasil.common.world;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

public class MuspelheimCaveCarver extends CaveWorldCarver {

    public MuspelheimCaveCarver(Codec<ProbabilityConfig> codec, int genHeight) {
        super(codec, genHeight);
        replaceableBlocks = ImmutableSet.of(Blocks.MAGMA_BLOCK, Blocks.BASALT, Blocks.BLACKSTONE);
        liquids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
    }

}
