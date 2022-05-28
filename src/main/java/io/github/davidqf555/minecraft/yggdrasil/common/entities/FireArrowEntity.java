package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class FireArrowEntity extends CustomArrowEntity {

    public FireArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world, ItemStack pickup) {
        super(type, world, pickup, ParticleTypes.FLAME);
    }

    public FireArrowEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity owner, World world) {
        super(type, owner, world, ParticleTypes.FLAME);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        double damage = MathHelper.ceil(MathHelper.clamp(getDeltaMovement().length() * getBaseDamage(), 0, 2.147483647E9));
        EffectHelper.applyFire(target, (float) damage);
    }

    @Override
    protected void onHitBlock(BlockRayTraceResult trace) {
        super.onHitBlock(trace);
        BlockPos pos = trace.getBlockPos();
        if (!trace.isInside()) {
            pos = pos.relative(trace.getDirection());
        }
        if (level.isEmptyBlock(pos)) {
            level.setBlockAndUpdate(pos, Blocks.FIRE.getStateForPlacement(new BlockItemUseContext(level, null, Hand.MAIN_HAND, ItemStack.EMPTY, trace)));
        }
    }
}
