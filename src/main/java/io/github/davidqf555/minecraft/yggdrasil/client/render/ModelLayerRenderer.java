package io.github.davidqf555.minecraft.yggdrasil.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

public class ModelLayerRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    private final M model;
    private final ResourceLocation texture;

    public ModelLayerRenderer(IEntityRenderer<T, M> renderer, M model, ResourceLocation texture) {
        super(renderer);
        this.model = model;
        this.texture = texture;
    }

    @Override
    public void render(MatrixStack p_225628_1_, IRenderTypeBuffer p_225628_2_, int p_225628_3_, T p_225628_4_, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        coloredCutoutModelCopyLayerRender(getParentModel(), model, getTextureLocation(p_225628_4_), p_225628_1_, p_225628_2_, p_225628_3_, p_225628_4_, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_, p_225628_7_, 1, 1, 1);
    }

    @Override
    protected ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
