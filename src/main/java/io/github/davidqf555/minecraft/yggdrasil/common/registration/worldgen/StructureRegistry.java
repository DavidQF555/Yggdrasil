package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.world.structures.SurfaceTemplateStructure;
import io.github.davidqf555.minecraft.yggdrasil.common.world.structures.TemplateConfig;
import io.github.davidqf555.minecraft.yggdrasil.common.world.structures.TemplateStructure;
import net.minecraft.command.impl.LocateCommand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class StructureRegistry {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Yggdrasil.ID);
    private static final List<StructureData<?>> DATA = new ArrayList<>();

    public static final RegistryObject<SurfaceTemplateStructure> RUNIC_TELEPORTER = register("runic_teleporter", SurfaceTemplateStructure::new, 40, 15, 219396827, false);

    public static StructureFeature<?, ?> CONFIGURED_RUNIC_TELEPORTER = null;
    public static IStructurePieceType TEMPLATE_PIECE_TYPE = null;


    private StructureRegistry() {
    }

    private static <T extends Structure<?>> RegistryObject<T> register(String id, Supplier<T> structure, int spacing, int separation, int salt, boolean blend) {
        RegistryObject<T> object = STRUCTURES.register(id, structure);
        DATA.add(new StructureData<>(object, new StructureSeparationSettings(spacing, separation, salt), blend));
        return object;
    }

    public static class StructureData<T extends Structure<?>> {

        public final RegistryObject<T> structure;
        public final StructureSeparationSettings spread;
        private final boolean blend;

        private StructureData(RegistryObject<T> structure, StructureSeparationSettings spread, boolean blend) {
            this.structure = structure;
            this.spread = spread;
            this.blend = blend;
        }
    }

    @Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBus {

        private ModBus() {
        }

        @SubscribeEvent
        public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                setupStructures();
                TEMPLATE_PIECE_TYPE = Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Yggdrasil.ID, "template"), TemplateStructure.Piece::new);
                CONFIGURED_RUNIC_TELEPORTER = WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, new ResourceLocation(Yggdrasil.ID, "runic_teleporter"), RUNIC_TELEPORTER.get().configured(new TemplateConfig(new ResourceLocation(Yggdrasil.ID, "runic_teleporter"))));
            });
        }

        private static void setupStructures() {
            for (StructureData<?> data : DATA) {
                Structure<?> structure = data.structure.get();
                Structure.STRUCTURES_REGISTRY.put(data.structure.getId().toString(), structure);
                if (data.blend) {
                    Structure.NOISE_AFFECTING_FEATURES =
                            ImmutableList.<Structure<?>>builder()
                                    .addAll(Structure.NOISE_AFFECTING_FEATURES)
                                    .add(structure)
                                    .build();
                }
                DimensionStructuresSettings.DEFAULTS =
                        ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                                .putAll(DimensionStructuresSettings.DEFAULTS)
                                .put(structure, data.spread)
                                .build();
                WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
                    Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
                    if (structureMap instanceof ImmutableMap) {
                        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                        tempMap.put(structure, data.spread);
                        settings.getValue().structureSettings().structureConfig = tempMap;
                    } else {
                        structureMap.put(structure, data.spread);
                    }
                });
            }
        }

    }

    @Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeBus {

        private ForgeBus() {
        }

        @SubscribeEvent
        public static void onBiomeLoading(BiomeLoadingEvent event) {
            if (event.getName() != null) {
                RegistryKey<Biome> biomeKey = RegistryKey.create(Registry.BIOME_REGISTRY, event.getName());
                if (BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.OVERWORLD) && !BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.OCEAN) && !BiomeDictionary.hasType(biomeKey, BiomeDictionary.Type.MOUNTAIN)) {
                    event.getGeneration().getStructures().add(() -> CONFIGURED_RUNIC_TELEPORTER);
                }
            }
        }
    }

}
