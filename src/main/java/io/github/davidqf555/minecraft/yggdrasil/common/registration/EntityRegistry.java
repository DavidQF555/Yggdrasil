package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.FireArrowEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.IceArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Yggdrasil.ID);

    public static final RegistryObject<EntityType<IceArrowEntity>> NIFLIUM_ARROW = register("niflium_arrow", EntityType.Builder.of((EntityType.IFactory<IceArrowEntity>) (type, world) -> new IceArrowEntity(type, world, ItemRegistry.NIFLIUM_ARROW.get().getDefaultInstance()), EntityClassification.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<FireArrowEntity>> MUSPELLIUM_ARROW = register("muspellium_arrow", EntityType.Builder.of((EntityType.IFactory<FireArrowEntity>) (type, world) -> new FireArrowEntity(type, world, ItemRegistry.MUSPELLIUM_ARROW.get().getDefaultInstance()), EntityClassification.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20));

    private EntityRegistry() {
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return TYPES.register(name, () -> builder.build(name));
    }
}
