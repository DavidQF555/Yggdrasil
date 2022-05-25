package io.github.davidqf555.minecraft.yggdrasil.common;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.ItemRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.LootModifierRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("yggdrasil")
public class Yggdrasil {

    public static final String ID = "yggdrasil";

    public Yggdrasil() {
        addRegistries(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addRegistries(IEventBus bus) {
        ItemRegistry.ITEMS.register(bus);
        LootModifierRegistry.SERIALIZERS.register(bus);
    }

}
