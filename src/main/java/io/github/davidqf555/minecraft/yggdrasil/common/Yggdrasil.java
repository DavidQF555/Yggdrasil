package io.github.davidqf555.minecraft.yggdrasil.common;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.BlockRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.ItemRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.LootModifierRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("yggdrasil")
public class Yggdrasil {

    public static final String ID = "yggdrasil";
    public static final ItemGroup GROUP = new ItemGroup(ID) {
        @Override
        public ItemStack makeIcon() {
            return ItemRegistry.MUSPELLIUM.get().getDefaultInstance();
        }
    };

    public Yggdrasil() {
        addRegistries(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addRegistries(IEventBus bus) {
        ItemRegistry.ITEMS.register(bus);
        LootModifierRegistry.SERIALIZERS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
    }

}
