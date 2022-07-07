package io.github.davidqf555.minecraft.yggdrasil.common.world;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.carver.CaveCarverConfiguration;
import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;
import net.minecraft.world.level.material.Fluids;

public class MuspelheimCaveCarver extends CaveWorldCarver {

    public MuspelheimCaveCarver(Codec<CaveCarverConfiguration> codec) {
        super(codec);
        replaceableBlocks = ImmutableSet.of(Blocks.MAGMA_BLOCK, Blocks.BASALT, Blocks.BLACKSTONE);
        liquids = ImmutableSet.of(Fluids.LAVA, Fluids.WATER);
    }

}
