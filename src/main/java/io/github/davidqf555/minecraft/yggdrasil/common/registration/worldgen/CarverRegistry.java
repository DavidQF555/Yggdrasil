package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.world.MuspelheimCaveCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class CarverRegistry {

    public static final DeferredRegister<WorldCarver<?>> CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, Yggdrasil.ID);

    public static final RegistryObject<MuspelheimCaveCarver> MUSPELHEIM_CAVE = register("muspelheim_cave", () -> new MuspelheimCaveCarver(ProbabilityConfig.CODEC, 256));

    private CarverRegistry() {
    }

    private static <T extends WorldCarver<?>> RegistryObject<T> register(String name, Supplier<T> carver) {
        return CARVERS.register(name, carver);
    }
}
