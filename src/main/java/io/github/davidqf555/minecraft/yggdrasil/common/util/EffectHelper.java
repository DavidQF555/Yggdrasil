package io.github.davidqf555.minecraft.yggdrasil.common.util;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.MathHelper;

public final class EffectHelper {

    private EffectHelper() {
    }

    public static void applyFreeze(LivingEntity target, float damage) {
        EffectInstance slow = target.getEffect(Effects.MOVEMENT_SLOWDOWN);
        int duration = MathHelper.ceil(damage * 20);
        if (slow != null && slow.getDuration() > duration) {
            duration = slow.getDuration();
        }
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, slow == null ? 0 : slow.getAmplifier() + 1));
    }

    public static void applyFire(LivingEntity target, float damage) {
        target.setSecondsOnFire(MathHelper.ceil(damage));
    }
}
