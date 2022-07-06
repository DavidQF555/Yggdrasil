package io.github.davidqf555.minecraft.yggdrasil.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BiomeRegistry {

    public static final RegistryKey<Biome> BURNING_PLAINS = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "burning_plains"));
    public static final RegistryKey<Biome> FLAMING_OCEAN = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "flaming_ocean"));
    public static final RegistryKey<Biome> VOLCANIC_BOGS = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "volcanic_bogs"));
    public static final RegistryKey<Biome> VOLCANIC_HILLS = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Yggdrasil.ID, "volcanic_hills"));

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

