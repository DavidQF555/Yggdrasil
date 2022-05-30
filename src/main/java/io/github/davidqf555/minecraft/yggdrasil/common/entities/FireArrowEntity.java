package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;

public class FireArrowEntity extends CustomArrowEntity {

    public FireArrowEntity(EntityType<? extends AbstractArrow> type, Level world, ItemStack pickup) {
        super(type, world, pickup, ParticleTypes.FLAME);
    }

    public FireArrowEntity(EntityType<? extends AbstractArrow> type, LivingEntity owner, Level world) {
        super(type, owner, world, ParticleTypes.FLAME);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        double damage = Mth.ceil(Mth.clamp(getDeltaMovement().length() * getBaseDamage(), 0, 2.147483647E9));
        EffectHelper.applyFire(target, (float) damage);
    }

    @Override
    protected void onHitBlock(BlockHitResult trace) {
        super.onHitBlock(trace);
        BlockPos pos = trace.getBlockPos();
        if (!trace.isInside()) {
            pos = pos.relative(trace.getDirection());
        }
        if (level.isEmptyBlock(pos)) {
            level.setBlockAndUpdate(pos, Blocks.FIRE.getStateForPlacement(new BlockPlaceContext(level, null, InteractionHand.MAIN_HAND, ItemStack.EMPTY, trace)));
        }
    }
}
