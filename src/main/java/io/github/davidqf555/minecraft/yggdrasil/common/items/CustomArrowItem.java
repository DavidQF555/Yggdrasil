package io.github.davidqf555.minecraft.yggdrasil.common.items;

import io.github.davidqf555.minecraft.yggdrasil.common.entities.CustomArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.BiFunction;

public class CustomArrowItem extends ArrowItem {

    private final BiFunction<Level, LivingEntity, CustomArrowEntity> factory;

    public CustomArrowItem(BiFunction<Level, LivingEntity, CustomArrowEntity> factory, Properties properties) {
        super(properties);
        this.factory = factory;
    }

    @Override
    public CustomArrowEntity createArrow(Level world, ItemStack stack, LivingEntity entity) {
        CustomArrowEntity arrow = factory.apply(world, entity);
        arrow.setPickup(getDefaultInstance());
        return arrow;
    }
}
