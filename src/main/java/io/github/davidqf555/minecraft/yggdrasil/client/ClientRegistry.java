package io.github.davidqf555.minecraft.yggdrasil.client;

import io.github.davidqf555.minecraft.yggdrasil.client.render.CustomArrowRenderer;
import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.EntityRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientRegistry {

    private static final ResourceLocation NIFLIUM_ARROW_TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/niflium_arrow.png");
    private static final ResourceLocation MUSPELLIUM_ARROW_TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/muspellium_arrow.png");

    private ClientRegistry() {
    }

    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.NIFLIUM_ARROW.get(), manager -> new CustomArrowRenderer<>(manager, NIFLIUM_ARROW_TEXTURE));
        RenderingRegistry.registerEntityRenderingHandler(EntityRegistry.MUSPELLIUM_ARROW.get(), manager -> new CustomArrowRenderer<>(manager, MUSPELLIUM_ARROW_TEXTURE));
    }
}
