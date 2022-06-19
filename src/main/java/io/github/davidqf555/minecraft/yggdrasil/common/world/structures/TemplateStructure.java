package io.github.davidqf555.minecraft.yggdrasil.common.world.structures;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen.StructureRegistry;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.SectionPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public abstract class TemplateStructure extends Structure<TemplateConfig> {

    private final GenerationStage.Decoration step;

    public TemplateStructure(GenerationStage.Decoration step) {
        super(TemplateConfig.CODEC);
        this.step = step;
    }

    @Override
    public IStartFactory<TemplateConfig> getStartFactory() {
        return Start::new;
    }

    @Override
    public GenerationStage.Decoration step() {
        return step;
    }

    protected abstract BlockPos getPos(ChunkGenerator gen, Template template, int x, int z);

    public static class Piece extends TemplateStructurePiece {

        private final ResourceLocation template;

        protected Piece(TemplateManager manager, ResourceLocation template, BlockPos pos, Mirror mirror, Rotation rotation) {
            super(StructureRegistry.TEMPLATE_PIECE_TYPE, 0);
            this.template = template;
            PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(mirror).setRotationPivot(pos);
            setup(manager.getOrCreate(template), pos, settings);
        }

        public Piece(TemplateManager manager, CompoundNBT nbt) {
            this(manager, new ResourceLocation(nbt.getString("Template")), new BlockPos(nbt.getInt("TPX"), nbt.getInt("TPY"), nbt.getInt("TPZ")), Mirror.values()[nbt.getInt("Mirror")], Rotation.values()[nbt.getInt("Rotation")]);
        }

        @Override
        protected void addAdditionalSaveData(CompoundNBT nbt) {
            super.addAdditionalSaveData(nbt);
            nbt.putString("Template", template.toString());
            nbt.putInt("Mirror", placeSettings.getMirror().ordinal());
            nbt.putInt("Rotation", placeSettings.getRotation().ordinal());
        }

        @Override
        protected void handleDataMarker(String p_186175_1_, BlockPos p_186175_2_, IServerWorld p_186175_3_, Random p_186175_4_, MutableBoundingBox p_186175_5_) {
        }
    }

    private class Start extends StructureStart<TemplateConfig> {

        private Start(Structure<TemplateConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int references, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, references, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator gen, TemplateManager templates, int chunkX, int chunkZ, Biome biome, TemplateConfig config) {
            int x = SectionPos.sectionToBlockCoord(chunkX);
            int z = SectionPos.sectionToBlockCoord(chunkZ);
            ResourceLocation loc = config.getTemplate();
            Template template = templates.getOrCreate(loc);
            BlockPos blockpos = getPos(gen, template, x, z);
            pieces.add(new Piece(templates, loc, blockpos, Util.getRandom(Mirror.values(), random), Util.getRandom(Rotation.values(), random)));
            this.calculateBoundingBox();
        }
    }
}
