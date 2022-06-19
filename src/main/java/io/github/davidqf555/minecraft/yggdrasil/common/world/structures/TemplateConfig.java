package io.github.davidqf555.minecraft.yggdrasil.common.world.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class TemplateConfig implements IFeatureConfig {

    public static final Codec<TemplateConfig> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ResourceLocation.CODEC.fieldOf("template").forGetter(TemplateConfig::getTemplate)
    ).apply(builder, TemplateConfig::new));
    private final ResourceLocation template;

    public TemplateConfig(ResourceLocation template) {
        this.template = template;
    }

    public ResourceLocation getTemplate() {
        return template;
    }
}
