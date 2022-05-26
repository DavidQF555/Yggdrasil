package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.OreBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yggdrasil.ID);

    public static final RegistryObject<OreBlock> NIFLIUM_ORE = register("niflium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<OreBlock> MUSPELLIUM_ORE = register("muspellium_ore", () -> new OreBlock(AbstractBlock.Properties.of(Material.STONE)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

}
