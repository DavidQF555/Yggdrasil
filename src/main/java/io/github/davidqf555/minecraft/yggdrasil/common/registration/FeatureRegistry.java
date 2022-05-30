package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class FeatureRegistry {

    public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED = DeferredRegister.create(Registry.CONFIGURED_FEATURE_REGISTRY, Yggdrasil.ID);
    public static final DeferredRegister<PlacedFeature> PLACED = DeferredRegister.create(Registry.PLACED_FEATURE_REGISTRY, Yggdrasil.ID);

    public static final RegistryObject<ConfiguredFeature<?, ?>> NIFLIUM_ORE_CONFIG = registerConfigured("niflium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockRegistry.NIFLIUM_ORE.get().defaultBlockState())), 4, 0.5f)));
    public static final RegistryObject<PlacedFeature> NIFLIUM_ORE_PLACED = registerPlaced("niflium_ore", () -> new PlacedFeature(NIFLIUM_ORE_CONFIG.getHolder().get(), List.of(CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)), BiomeFilter.biome())));
    public static final RegistryObject<ConfiguredFeature<?, ?>> MUSPELLIUM_ORE_CONFIG = registerConfigured("muspellium_ore", () -> new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(List.of(OreConfiguration.target(OreFeatures.STONE_ORE_REPLACEABLES, BlockRegistry.MUSPELLIUM_ORE.get().defaultBlockState())), 4, 0.5f)));
    public static final RegistryObject<PlacedFeature> MUSPELLIUM_ORE_PLACED = registerPlaced("muspellium_ore", () -> new PlacedFeature(MUSPELLIUM_ORE_CONFIG.getHolder().get(), List.of(CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80)), BiomeFilter.biome())));

    private FeatureRegistry() {
    }

    private static <T extends ConfiguredFeature<?, ?>> RegistryObject<T> registerConfigured(String name, Supplier<T> feature) {
        return CONFIGURED.register(name, feature);
    }

    private static <T extends PlacedFeature> RegistryObject<T> registerPlaced(String name, Supplier<T> feature) {
        return PLACED.register(name, feature);
    }

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        ResourceLocation name = event.getName();
        if (name != null) {
            ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, name);
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.COLD)) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, NIFLIUM_ORE_PLACED.getHolder().get());
            }
            if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT)) {
                event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, MUSPELLIUM_ORE_PLACED.getHolder().get());
            }
        }
    }

}
