package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.FireArrowEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.FireZombieEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.IceArrowEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EntityRegistry {

    public static final DeferredRegister<EntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Yggdrasil.ID);

    public static final RegistryObject<EntityType<IceArrowEntity>> NIFLIUM_ARROW = register("niflium_arrow", EntityType.Builder.of((EntityType.IFactory<IceArrowEntity>) (type, world) -> new IceArrowEntity(type, world, ItemRegistry.NIFLIUM_ARROW.get().getDefaultInstance()), EntityClassification.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20));
    public static final RegistryObject<EntityType<FireArrowEntity>> MUSPELLIUM_ARROW = register("muspellium_arrow", EntityType.Builder.of((EntityType.IFactory<FireArrowEntity>) (type, world) -> new FireArrowEntity(type, world, ItemRegistry.MUSPELLIUM_ARROW.get().getDefaultInstance()), EntityClassification.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20));

    public static final RegistryObject<EntityType<FireZombieEntity>> TORCHED = register("torched", EntityType.Builder.of(FireZombieEntity::new, EntityClassification.MONSTER).fireImmune().sized(0.6f, 1.95f));


    private EntityRegistry() {
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return TYPES.register(name, () -> builder.build(name));
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(TORCHED.get(), FireZombieEntity.createTorchedAttributes().build());
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(TORCHED.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, FireZombieEntity::canSpawn);
        });
    }
}
