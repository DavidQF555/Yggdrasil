package io.github.davidqf555.minecraft.yggdrasil.common.util;

import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public final class EffectHelper {

    private EffectHelper() {
    }

    public static void applyFreeze(LivingEntity target, float damage) {
        MobEffectInstance slow = target.getEffect(MobEffects.MOVEMENT_SLOWDOWN);
        int duration = Mth.ceil(damage * 20);
        if (slow != null && slow.getDuration() > duration) {
            duration = slow.getDuration();
        }
        target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, slow == null ? 0 : slow.getAmplifier() + 1));
    }

    public static void applyFire(LivingEntity target, float damage) {
        target.setSecondsOnFire(Mth.ceil(damage));
    }
}
