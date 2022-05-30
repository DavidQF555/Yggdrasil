package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class TagRegistry {

    public static final TagKey<Item> MUSPELLIUM_TOOLS = ItemTags.create(new ResourceLocation(Yggdrasil.ID, "muspellium_tools"));
    public static final TagKey<Item> NIFLIUM_TOOLS = ItemTags.create(new ResourceLocation(Yggdrasil.ID, "niflium_tools"));

    private TagRegistry() {
    }

}
