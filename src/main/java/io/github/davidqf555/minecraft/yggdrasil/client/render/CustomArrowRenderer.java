package io.github.davidqf555.minecraft.yggdrasil.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.util.ResourceLocation;

public class CustomArrowRenderer<T extends AbstractArrowEntity> extends ArrowRenderer<T> {

    private final ResourceLocation texture;

    public CustomArrowRenderer(EntityRendererManager manager, ResourceLocation texture) {
        super(manager);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
