package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public final class DimensionRegistry {

    public static final ResourceKey<Level> MUSPELHEIM = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Yggdrasil.ID, "muspelheim"));

    private DimensionRegistry() {
    }
}
