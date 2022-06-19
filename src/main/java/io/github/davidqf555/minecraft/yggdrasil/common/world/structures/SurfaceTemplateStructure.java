package io.github.davidqf555.minecraft.yggdrasil.common.world.structures;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.Template;

public class SurfaceTemplateStructure extends TemplateStructure {

    public SurfaceTemplateStructure() {
        super(GenerationStage.Decoration.SURFACE_STRUCTURES);
    }

    @Override
    protected BlockPos getPos(ChunkGenerator gen, Template template, int x, int z) {
        BlockPos size = template.getSize();
        return new BlockPos(x, gen.getFirstFreeHeight(x + size.getX() / 2, z + size.getZ() / 2, Heightmap.Type.WORLD_SURFACE_WG), z);
    }
}
