package io.github.davidqf555.minecraft.yggdrasil.common.items;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class WorldPointerItem extends Item {

    private final ResourceKey<Level> target;
    private final int delay;

    public WorldPointerItem(ResourceKey<Level> target, int delay, Properties properties) {
        super(properties);
        this.target = target;
        this.delay = delay;
    }

    public ResourceKey<Level> getTarget() {
        return target;
    }

    public int getDelay() {
        return delay;
    }

}
