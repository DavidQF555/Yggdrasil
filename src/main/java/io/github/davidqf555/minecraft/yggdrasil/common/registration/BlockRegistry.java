package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.FireEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.IceEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.TileEntityBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yggdrasil.ID);

    public static final RegistryObject<OreBlock> NIFLIUM_ORE = register("niflium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3, 3)));
    public static final RegistryObject<OreBlock> MUSPELLIUM_ORE = register("muspellium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE).requiresCorrectToolForDrops().strength(3, 3)));

    public static final RegistryObject<TileEntityBlock> NIFLIUM_BLOCK = register("niflium_block", () -> new TileEntityBlock((state, world) -> new IceEffectTileEntity(), AbstractBlock.Properties.of(Material.METAL, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));
    public static final RegistryObject<TileEntityBlock> MUSPELLIUM_BLOCK = register("muspellium_block", () -> new TileEntityBlock((state, world) -> new FireEffectTileEntity(), AbstractBlock.Properties.of(Material.METAL, DyeColor.ORANGE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

}
