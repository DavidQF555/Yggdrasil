package io.github.davidqf555.minecraft.yggdrasil.client.render;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Zombie;

public class TorchedRenderer extends ZombieRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/torched.png");

    public TorchedRenderer(EntityRendererProvider.Context manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(Zombie entity) {
        return TEXTURE;
    }
}
