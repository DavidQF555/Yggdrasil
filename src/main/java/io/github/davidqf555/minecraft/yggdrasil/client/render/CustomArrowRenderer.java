package io.github.davidqf555.minecraft.yggdrasil.client.render;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;

public class CustomArrowRenderer<T extends AbstractArrow> extends ArrowRenderer<T> {

    private final ResourceLocation texture;

    public CustomArrowRenderer(EntityRendererProvider.Context manager, ResourceLocation texture) {
        super(manager);
        this.texture = texture;
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
