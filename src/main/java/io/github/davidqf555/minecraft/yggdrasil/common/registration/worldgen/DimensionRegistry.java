package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class DimensionRegistry {

    public static final RegistryKey<World> MUSPELHEIM = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Yggdrasil.ID, "muspelheim"));

    private DimensionRegistry() {
    }
}
