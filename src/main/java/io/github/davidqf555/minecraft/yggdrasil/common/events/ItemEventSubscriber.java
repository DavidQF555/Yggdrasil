package io.github.davidqf555.minecraft.yggdrasil.common.events;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.TagRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Yggdrasil.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class ItemEventSubscriber {

    private ItemEventSubscriber() {
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        DamageSource source = event.getSource();
        Entity entity = source.getEntity();
        if (entity instanceof LivingEntity && !source.isProjectile() && !source.getMsgId().equals("thrown") && !source.isMagic() && !source.isExplosion() && !source.isFire() && entity.equals(source.getDirectEntity())) {
            Item hand = ((LivingEntity) entity).getItemInHand(Hand.MAIN_HAND).getItem();
            float damage = event.getAmount();
            LivingEntity target = event.getEntityLiving();
            if (TagRegistry.MUSPELLIUM_TOOLS.contains(hand)) {
                target.setSecondsOnFire(MathHelper.ceil(damage));
            }
            if (TagRegistry.NIFLIUM_TOOLS.contains(hand)) {
                EffectInstance slow = target.getEffect(Effects.MOVEMENT_SLOWDOWN);
                int duration = MathHelper.ceil(damage * 20);
                if (slow != null && slow.getDuration() > duration) {
                    duration = slow.getDuration();
                }
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, duration, slow == null ? 0 : slow.getAmplifier() + 1));
            }
        }
    }
}
