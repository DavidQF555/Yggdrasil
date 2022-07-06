package io.github.davidqf555.minecraft.yggdrasil.common;

import io.github.davidqf555.minecraft.yggdrasil.registration.*;
import io.github.davidqf555.minecraft.yggdrasil.registration.worldgen.CarverRegistry;
import io.github.davidqf555.minecraft.yggdrasil.registration.worldgen.StructureRegistry;
import io.github.davidqf555.minecraft.yggdrasil.registration.worldgen.SurfaceBuilderRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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
            return Items.OAK_SAPLING.getDefaultInstance();
        }
    };

    public Yggdrasil() {
        addRegistries(FMLJavaModLoadingContext.get().getModEventBus());
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addRegistries(IEventBus bus) {
        StructureRegistry.STRUCTURES.register(bus);
        ItemRegistry.ITEMS.register(bus);
        LootModifierRegistry.SERIALIZERS.register(bus);
        BlockRegistry.BLOCKS.register(bus);
        EntityRegistry.TYPES.register(bus);
        TileEntityRegistry.TYPES.register(bus);
        POIRegistry.TYPES.register(bus);
        SurfaceBuilderRegistry.BUILDERS.register(bus);
        CarverRegistry.CARVERS.register(bus);
    }

}
