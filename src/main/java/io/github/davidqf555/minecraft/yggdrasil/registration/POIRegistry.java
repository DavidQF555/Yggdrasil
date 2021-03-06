package io.github.davidqf555.minecraft.yggdrasil.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.block.BlockState;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.function.Supplier;

public final class POIRegistry {

    public static final DeferredRegister<PointOfInterestType> TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Yggdrasil.ID);

    public static final RegistryObject<PointOfInterestType> RUNIC_TELEPORTER = register("runic_teleporter", 0, 1, () -> PointOfInterestType.getBlockStates(BlockRegistry.RUNIC_TELEPORTER.get()));

    private POIRegistry() {
    }

    private static RegistryObject<PointOfInterestType> register(String name, int maxTickets, int validRange, Supplier<Set<BlockState>> states) {
        return TYPES.register(name, () -> new PointOfInterestType(name, states.get(), maxTickets, validRange));
    }
}
