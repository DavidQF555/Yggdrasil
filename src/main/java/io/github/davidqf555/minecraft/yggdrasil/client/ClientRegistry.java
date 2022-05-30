package io.github.davidqf555.minecraft.yggdrasil.client;

import io.github.davidqf555.minecraft.yggdrasil.client.render.CustomArrowRenderer;
import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.EntityRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientRegistry {

    private static final ResourceLocation NIFLIUM_ARROW_TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/niflium_arrow.png");
    private static final ResourceLocation MUSPELLIUM_ARROW_TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/muspellium_arrow.png");

    private ClientRegistry() {
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.NIFLIUM_ARROW.get(), manager -> new CustomArrowRenderer<>(manager, NIFLIUM_ARROW_TEXTURE));
        event.registerEntityRenderer(EntityRegistry.MUSPELLIUM_ARROW.get(), manager -> new CustomArrowRenderer<>(manager, MUSPELLIUM_ARROW_TEXTURE));
    }

}
