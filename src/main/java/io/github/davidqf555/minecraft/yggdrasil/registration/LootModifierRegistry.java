package io.github.davidqf555.minecraft.yggdrasil.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.items.SmeltLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class LootModifierRegistry {

    public static final DeferredRegister<GlobalLootModifierSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Yggdrasil.ID);

    public static final RegistryObject<SmeltLootModifier.Serializer> SMELT = register("smelt", SmeltLootModifier.Serializer::new);

    private LootModifierRegistry() {
    }

    private static <T extends GlobalLootModifierSerializer<?>> RegistryObject<T> register(String name, Supplier<T> serializer) {
        return SERIALIZERS.register(name, serializer);
    }
}
