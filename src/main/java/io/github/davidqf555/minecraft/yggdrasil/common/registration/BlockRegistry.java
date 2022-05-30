package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yggdrasil.ID);

    public static final RegistryObject<OreBlock> NIFLIUM_ORE = register("niflium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<OreBlock> MUSPELLIUM_ORE = register("muspellium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

}
