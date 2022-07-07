package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;
import java.util.function.Supplier;

public final class POIRegistry {

    public static final DeferredRegister<PoiType> TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Yggdrasil.ID);

    public static final RegistryObject<PoiType> RUNIC_TELEPORTER = register("runic_teleporter", 0, 1, () -> PoiType.getBlockStates(BlockRegistry.RUNIC_TELEPORTER.get()));

    private POIRegistry() {
    }

    private static RegistryObject<PoiType> register(String name, int maxTickets, int validRange, Supplier<Set<BlockState>> states) {
        return TYPES.register(name, () -> new PoiType(name, states.get(), maxTickets, validRange));
    }
}
