package io.github.davidqf555.minecraft.yggdrasil.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public final class TagRegistry {

    public static final ITag.INamedTag<Item> MUSPELLIUM_TOOLS = ItemTags.bind(new ResourceLocation(Yggdrasil.ID, "muspellium_tools").toString());
    public static final ITag.INamedTag<Item> NIFLIUM_TOOLS = ItemTags.bind(new ResourceLocation(Yggdrasil.ID, "niflium_tools").toString());

    private TagRegistry() {
    }

}
