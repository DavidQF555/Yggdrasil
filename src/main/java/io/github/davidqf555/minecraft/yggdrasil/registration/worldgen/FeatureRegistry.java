package io.github.davidqf555.minecraft.yggdrasil.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.registration.BlockRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public final class FeatureRegistry {

    public static ConfiguredFeature<?, ?> NIFLIUM_ORE = null;
    public static ConfiguredFeature<?, ?> MUSPELLIUM_ORE = null;

    private FeatureRegistry() {
    }

    @Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static final class ModBus {
        private ModBus() {
        }

        @SubscribeEvent
        public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                NIFLIUM_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.NIFLIUM_ORE.get().defaultBlockState(), 8)).range(16).squared();
                MUSPELLIUM_ORE = Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, BlockRegistry.NIFLIUM_ORE.get().defaultBlockState(), 8)).range(16).squared();
            });
        }
    }

    @Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static final class ForgeBus {

        private ForgeBus() {
        }

        @SubscribeEvent
        public static void onBiomeLoading(BiomeLoadingEvent event) {
            ResourceLocation name = event.getName();
            if (name != null) {
                RegistryKey<Biome> biome = RegistryKey.create(Registry.BIOME_REGISTRY, name);
                if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)) {
                    event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, NIFLIUM_ORE);
                }
                if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT)) {
                    event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MUSPELLIUM_ORE);
                }
            }
        }

    }
}
