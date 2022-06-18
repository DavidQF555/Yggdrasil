package io.github.davidqf555.minecraft.yggdrasil.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class WorldPointerItem extends Item {

    private final RegistryKey<World> target;
    private final int delay;

    public WorldPointerItem(RegistryKey<World> target, int delay, Properties properties) {
        super(properties);
        this.target = target;
        this.delay = delay;
    }

    public RegistryKey<World> getTarget() {
        return target;
    }

    public int getDelay() {
        return delay;
    }

}
