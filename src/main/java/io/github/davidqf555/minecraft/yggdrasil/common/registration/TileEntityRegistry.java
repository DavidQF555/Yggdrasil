package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.FireEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.IceEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.RunicTeleporterTileEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class TileEntityRegistry {

    public static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Yggdrasil.ID);

    private TileEntityRegistry() {
    }

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> type) {
        return TYPES.register(name, type);
    }

    public static final RegistryObject<BlockEntityType<IceEffectTileEntity>> ICE_EFFECT = register("ice_effect", () -> BlockEntityType.Builder.of(IceEffectTileEntity::new, BlockRegistry.NIFLIUM_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<FireEffectTileEntity>> FIRE_EFFECT = register("fire_effect", () -> BlockEntityType.Builder.of(FireEffectTileEntity::new, BlockRegistry.MUSPELLIUM_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<RunicTeleporterTileEntity>> RUNIC_TELEPORTER = register("runic_teleporter", () -> BlockEntityType.Builder.of(RunicTeleporterTileEntity::new, BlockRegistry.RUNIC_TELEPORTER.get()).build(null));

}
