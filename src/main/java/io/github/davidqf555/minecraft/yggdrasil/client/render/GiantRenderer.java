package io.github.davidqf555.minecraft.yggdrasil.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.giants.AbstractGiantEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.util.ResourceLocation;

public class GiantRenderer<T extends AbstractGiantEntity> extends BipedRenderer<T, BipedModel<T>> {

    private final ResourceLocation texture;
    private final float baseShadow;

    public GiantRenderer(EntityRendererManager manager, ResourceLocation texture, ResourceLocation layer) {
        super(manager, new BipedModel<>(RenderType::entityCutoutNoCull, 0, 0, 64, 64), 0);
        baseShadow = 0.5f;
        this.texture = texture;
        addLayer(new BipedArmorLayer<>(this, new BipedModel<>(0.5f), new BipedModel<>(1)));
        addLayer(new ModelLayerRenderer<>(this, new BipedModel<>(RenderType::entityCutoutNoCull, 0.25f, 0, 64, 64), layer));
    }

    @Override
    public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        float scale = p_225623_1_.getScale();
        shadowRadius = baseShadow * scale;
        p_225623_4_.pushPose();
        p_225623_4_.scale(scale, scale, scale);
        super.render(p_225623_1_, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
        p_225623_4_.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return texture;
    }
}
