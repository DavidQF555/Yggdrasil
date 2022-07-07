package io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.world.structures.TemplateStructure;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class StructureRegistry {

    public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Yggdrasil.ID);

    public static final RegistryObject<TemplateStructure> SURFACE_TEMPLATE = register("surface_template", TemplateStructure::createSurfaceStructure);

    public static StructurePieceType TEMPLATE_PIECE_TYPE = null;

    private StructureRegistry() {
    }

    private static <T extends StructureFeature<?>> RegistryObject<T> register(String id, Supplier<T> structure) {
        return STRUCTURES.register(id, structure);
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            TEMPLATE_PIECE_TYPE = Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(Yggdrasil.ID, "template"), TemplateStructure.Piece::new);
        });
    }

}
