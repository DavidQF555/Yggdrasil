package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BiomeRegistry {

    public static final ResourceKey<Biome> BURNING_PLAINS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "burning_plains"));
    public static final ResourceKey<Biome> FLAMING_OCEAN = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "flaming_ocean"));
    public static final ResourceKey<Biome> VOLCANIC_BOGS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "volcanic_bogs"));
    public static final ResourceKey<Biome> VOLCANIC_HILLS = ResourceKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "volcanic_hills"));

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            BiomeDictionary.addTypes(BURNING_PLAINS, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.HOT);
            BiomeDictionary.addTypes(FLAMING_OCEAN, BiomeDictionary.Type.OCEAN, BiomeDictionary.Type.HOT);
            BiomeDictionary.addTypes(VOLCANIC_BOGS, BiomeDictionary.Type.SWAMP, BiomeDictionary.Type.HOT);
            BiomeDictionary.addTypes(VOLCANIC_HILLS, BiomeDictionary.Type.HILLS, BiomeDictionary.Type.HOT);
        });
    }
}

