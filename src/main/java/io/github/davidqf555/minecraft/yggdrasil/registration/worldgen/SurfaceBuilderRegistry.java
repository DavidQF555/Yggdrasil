package io.github.davidqf555.minecraft.yggdrasil.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.world.MuspelheimSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class SurfaceBuilderRegistry {

    public static final DeferredRegister<SurfaceBuilder<?>> BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, Yggdrasil.ID);

    public static final RegistryObject<MuspelheimSurfaceBuilder> MUSPELHEIM = register("muspelheim", () -> new MuspelheimSurfaceBuilder(SurfaceBuilderConfig.CODEC));

    private SurfaceBuilderRegistry() {
    }

    private static <T extends SurfaceBuilder<?>> RegistryObject<T> register(String name, Supplier<T> builder) {
        return BUILDERS.register(name, builder);
    }
}
