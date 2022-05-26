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
            LivingEntity target = event.getEntityLiving();
            if (TagRegistry.MUSPELLIUM_TOOLS.contains(hand)) {
                target.setSecondsOnFire(5);
            }
            if (TagRegistry.NIFLIUM_TOOLS.contains(hand)) {
                EffectInstance slow = target.getEffect(Effects.MOVEMENT_SLOWDOWN);
                target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, slow == null ? 0 : slow.getAmplifier() + 1));
            }
        }
    }
}
