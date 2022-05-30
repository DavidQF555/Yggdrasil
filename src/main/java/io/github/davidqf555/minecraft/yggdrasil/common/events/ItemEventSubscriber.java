package io.github.davidqf555.minecraft.yggdrasil.common.events;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.TagRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
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
            ItemStack hand = ((LivingEntity) entity).getItemInHand(InteractionHand.MAIN_HAND);
            float damage = event.getAmount();
            LivingEntity target = event.getEntityLiving();
            if (hand.is(TagRegistry.MUSPELLIUM_TOOLS)) {
                EffectHelper.applyFire(target, damage);
            }
            if (hand.is(TagRegistry.NIFLIUM_TOOLS)) {
                EffectHelper.applyFreeze(target, damage);
            }
        }
    }
}
