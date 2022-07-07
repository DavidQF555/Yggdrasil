package io.github.davidqf555.minecraft.yggdrasil.common.world.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class TemplateConfig implements FeatureConfiguration {

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
