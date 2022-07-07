package io.github.davidqf555.minecraft.yggdrasil.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.giants.AbstractGiantEntity;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class GiantRenderer<T extends AbstractGiantEntity> extends HumanoidMobRenderer<T, HumanoidModel<T>> {

    private final ResourceLocation texture;
    private final float baseShadow;

    public GiantRenderer(EntityRendererProvider.Context manager, ResourceLocation texture, ResourceLocation layer) {
        super(manager, new HumanoidModel<>(manager.bakeLayer(ModelLayers.ZOMBIE)), 0);
        baseShadow = 0.5f;
        this.texture = texture;
        addLayer(new HumanoidArmorLayer<>(this, new HumanoidModel<>(manager.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new HumanoidModel<>(manager.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR))));
        addLayer(new ModelLayerRenderer<>(this, new HumanoidModel<>(manager.bakeLayer(ModelLayers.DROWNED_OUTER_LAYER)), layer));
    }

    @Override
    public void render(T p_225623_1_, float p_225623_2_, float p_225623_3_, PoseStack p_225623_4_, MultiBufferSource p_225623_5_, int p_225623_6_) {
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
