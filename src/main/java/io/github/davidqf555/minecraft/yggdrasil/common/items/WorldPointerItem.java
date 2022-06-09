package io.github.davidqf555.minecraft.yggdrasil.common.items;

import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

public class WorldPointerItem extends Item {

    private final RegistryKey<World> target;

    public WorldPointerItem(RegistryKey<World> target, Properties properties) {
        super(properties);
        this.target = target;
    }

    public RegistryKey<World> getTarget() {
        return target;
    }

}
