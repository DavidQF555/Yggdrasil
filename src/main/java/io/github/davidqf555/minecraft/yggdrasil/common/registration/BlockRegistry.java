package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.FireEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.IceEffectTileEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.RunicTeleporterBlock;
import io.github.davidqf555.minecraft.yggdrasil.common.blocks.TileEntityBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public final class BlockRegistry {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Yggdrasil.ID);

    public static final RegistryObject<OreBlock> NIFLIUM_ORE = register("niflium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<OreBlock> MUSPELLIUM_ORE = register("muspellium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE)));

    public static final RegistryObject<TileEntityBlock<IceEffectTileEntity>> NIFLIUM_BLOCK = register("niflium_block", () -> new TileEntityBlock<>(TileEntityRegistry.ICE_EFFECT, IceEffectTileEntity::new, BlockBehaviour.Properties.of(Material.METAL, DyeColor.LIGHT_BLUE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));
    public static final RegistryObject<TileEntityBlock<FireEffectTileEntity>> MUSPELLIUM_BLOCK = register("muspellium_block", () -> new TileEntityBlock<>(TileEntityRegistry.FIRE_EFFECT, FireEffectTileEntity::new, BlockBehaviour.Properties.of(Material.METAL, DyeColor.ORANGE).requiresCorrectToolForDrops().strength(5, 6).sound(SoundType.METAL)));

    public static final RegistryObject<RunicTeleporterBlock> RUNIC_TELEPORTER = register("runic_teleporter", () -> new RunicTeleporterBlock(BlockBehaviour.Properties.of(Material.STONE).strength(50, 1200)));

    public static final RegistryObject<OreBlock> BLACKSTONE_COAL_ORE = register("blackstone_coal_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6)));
    public static final RegistryObject<OreBlock> BLACKSTONE_IRON_ORE = register("blackstone_iron_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6)));
    public static final RegistryObject<OreBlock> BLACKSTONE_MUSPELLIUM_ORE = register("blackstone_muspellium_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).requiresCorrectToolForDrops().strength(1.5f, 6)));

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

}
