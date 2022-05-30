package io.github.davidqf555.minecraft.yggdrasil.common;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.*;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("yggdrasil")
public class Yggdrasil {

    public static final String ID = "yggdrasil";
    public static final CreativeModeTab GROUP = new CreativeModeTab(ID) {
        @Override
        public ItemStack makeIcon() {
            return Items.OAK_SAPLING.getDefaultInstance();
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
        EntityRegistry.TYPES.register(bus);
        FeatureRegistry.CONFIGURED.register(bus);
        FeatureRegistry.PLACED.register(bus);
    }

}
