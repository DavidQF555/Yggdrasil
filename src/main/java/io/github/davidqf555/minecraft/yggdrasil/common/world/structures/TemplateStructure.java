package io.github.davidqf555.minecraft.yggdrasil.common.world.structures;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen.StructureRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Optional;
import java.util.Random;

public class TemplateStructure extends StructureFeature<TemplateConfig> {

    private final GenerationStep.Decoration step;

    public TemplateStructure(GenerationStep.Decoration step, BlockSelector selector) {
        super(TemplateConfig.CODEC, cont -> Optional.of((builder, context) -> {
            ResourceLocation loc = context.config().getTemplate();
            StructureTemplate template = context.structureManager().getOrCreate(loc);
            Vec3i bounds = template.getSize();
            int x = context.chunkPos().getBlockX(bounds.getX() / 2);
            int z = context.chunkPos().getBlockZ(bounds.getY() / 2);
            BlockPos pos = selector.getPos(context.chunkGenerator(), context.heightAccessor(), template, x, z);
            Random rand = context.random();
            builder.addPiece(new Piece(context.structureManager(), loc, pos, Util.getRandom(Mirror.values(), rand), Util.getRandom(Rotation.values(), rand)));
        }));
        this.step = step;
    }

    public static TemplateStructure createSurfaceStructure() {
        return new TemplateStructure(GenerationStep.Decoration.SURFACE_STRUCTURES, (gen, accessor, template, x, z) -> {
            Vec3i size = template.getSize();
            return new BlockPos(x, gen.getFirstFreeHeight(x + size.getX() / 2, z + size.getZ() / 2, Heightmap.Types.WORLD_SURFACE_WG, accessor), z);
        });
    }

    @Override
    public GenerationStep.Decoration step() {
        return step;
    }

    public interface BlockSelector {

        BlockPos getPos(ChunkGenerator gen, LevelHeightAccessor accessor, StructureTemplate template, int x, int z);

    }

    public static class Piece extends TemplateStructurePiece {

        protected Piece(StructureManager manager, ResourceLocation template, BlockPos pos, Mirror mirror, Rotation rotation) {
            super(StructureRegistry.TEMPLATE_PIECE_TYPE, 0, manager, template, template.toString(), new StructurePlaceSettings().setRotation(rotation).setMirror(mirror).setRotationPivot(pos), pos);
        }

        public Piece(StructurePieceSerializationContext context, CompoundTag tag) {
            this(context.structureManager(), new ResourceLocation(tag.getString("Template")), new BlockPos(tag.getInt("TPX"), tag.getInt("TPY"), tag.getInt("TPZ")), Mirror.values()[tag.getInt("Mirror")], Rotation.values()[tag.getInt("Rotation")]);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putInt("Mirror", getMirror().ordinal());
            tag.putInt("Rotation", getRotation().ordinal());
        }

        @Override
        protected void handleDataMarker(String p_73683_, BlockPos p_73684_, ServerLevelAccessor p_73685_, Random p_73686_, BoundingBox p_73687_) {
        }

    }

}
