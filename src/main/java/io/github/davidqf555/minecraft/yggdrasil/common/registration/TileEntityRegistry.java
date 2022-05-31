package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.FireEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.IceEffectTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class TileEntityRegistry {

    public static final DeferredRegister<TileEntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Yggdrasil.ID);

    private TileEntityRegistry() {
    }    public static final RegistryObject<TileEntityType<IceEffectTileEntity>> ICE_EFFECT = register("ice_effect", () -> TileEntityType.Builder.of(IceEffectTileEntity::new, BlockRegistry.NIFLIUM_BLOCK.get()).build(null));

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<TileEntityType<T>> type) {
        return TYPES.register(name, type);
    }    public static final RegistryObject<TileEntityType<FireEffectTileEntity>> FIRE_EFFECT = register("fire_effect", () -> TileEntityType.Builder.of(FireEffectTileEntity::new, BlockRegistry.MUSPELLIUM_BLOCK.get()).build(null));




}
