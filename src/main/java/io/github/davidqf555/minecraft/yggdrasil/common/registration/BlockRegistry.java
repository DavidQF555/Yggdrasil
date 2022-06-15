package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.FireEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.IceEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.TileEntityBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yggdrasil.ID);

    public static final RegistryObject<DropExperienceBlock> NIFLIUM_ORE = register("niflium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<DropExperienceBlock> MUSPELLIUM_ORE = register("muspellium_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<TileEntityBlock<IceEffectTileEntity>> NIFLIUM_BLOCK = register("niflium_block", () -> new TileEntityBlock<>(TileEntityRegistry.ICE_EFFECT, IceEffectTileEntity::new, BlockBehaviour.Properties.of(Material.METAL, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));
    public static final RegistryObject<TileEntityBlock<FireEffectTileEntity>> MUSPELLIUM_BLOCK = register("muspellium_block", () -> new TileEntityBlock<>(TileEntityRegistry.FIRE_EFFECT, FireEffectTileEntity::new, BlockBehaviour.Properties.of(Material.METAL, DyeColor.ORANGE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

}
