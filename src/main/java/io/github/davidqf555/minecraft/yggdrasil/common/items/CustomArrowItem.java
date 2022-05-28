package io.github.davidqf555.minecraft.yggdrasil.common.items;

import io.github.davidqf555.minecraft.yggdrasil.common.entities.CustomArrowEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.function.BiFunction;

public class CustomArrowItem extends ArrowItem {

    private final BiFunction<World, LivingEntity, CustomArrowEntity> factory;

    public CustomArrowItem(BiFunction<World, LivingEntity, CustomArrowEntity> factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    @Override
    public CustomArrowEntity createArrow(World world, ItemStack stack, LivingEntity entity) {
        CustomArrowEntity arrow = factory.apply(world, entity);
        arrow.setPickup(getDefaultInstance());
        return arrow;
    }
}
