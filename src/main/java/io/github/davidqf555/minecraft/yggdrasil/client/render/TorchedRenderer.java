package io.github.davidqf555.minecraft.yggdrasil.client.render;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.util.ResourceLocation;

public class TorchedRenderer extends ZombieRenderer {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Yggdrasil.ID, "textures/entity/torched.png");

    public TorchedRenderer(EntityRendererManager manager) {
        super(manager);
    }

    @Override
    public ResourceLocation getTextureLocation(ZombieEntity entity) {
        return TEXTURE;
    }
}
