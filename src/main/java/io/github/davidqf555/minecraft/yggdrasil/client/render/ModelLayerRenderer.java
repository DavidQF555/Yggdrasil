package io.github.davidqf555.minecraft.yggdrasil.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class ModelLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final M model;
    private final ResourceLocation texture;

    public ModelLayerRenderer(RenderLayerParent<T, M> renderer, M model, ResourceLocation texture) {
        super(renderer);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(PoseStack p_225628_1_, MultiBufferSource p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        coloredCutoutModelCopyLayerRender(getParentModel(), model, getTextureLocation(p_225628_4_), p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1, 1, 1);
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
