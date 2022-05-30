package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.function.Consumer;

public class IceArrowEntity extends CustomArrowEntity {

    private static final int SNOW_RADIUS = 5, ICE_RADIUS = 3;
    private static final BlockParticleOption PARTICLE = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState());

    public IceArrowEntity(EntityType<? extends AbstractArrow> type, Level world, ItemStack pickup) {
        super(type, world, pickup, PARTICLE);
    }

    public IceArrowEntity(EntityType<? extends AbstractArrow> type, LivingEntity owner, Level world) {
        super(type, owner, world, PARTICLE);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        double damage = Mth.ceil(Mth.clamp(getDeltaMovement().length() * getBaseDamage(), 0, 2.147483647E9));
        EffectHelper.applyFreeze(target, (float) damage);
    }

    @Override
    protected void doWaterSplashEffect() {
        super.doWaterSplashEffect();
        BlockPos center = blockPosition();
        applySphere(center, ICE_RADIUS, pos -> {
            BlockState state = level.getBlockState(pos);
            if (state.getFluidState().is(FluidTags.WATER)) {
                level.setBlockAndUpdate(pos, Blocks.ICE.defaultBlockState());
            }
        });
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos center = result.getBlockPos();
        if (!result.isInside()) {
            center = center.relative(result.getDirection());
        }
        applySphere(center, SNOW_RADIUS, pos -> {
            BlockState state = level.getBlockState(pos);
            if (level.isEmptyBlock(pos)) {
                BlockState snow = Blocks.SNOW.defaultBlockState();
                if (snow.canSurvive(level, pos)) {
                    level.setBlockAndUpdate(pos, snow);
                }
            } else if (state.is(Blocks.SNOW)) {
                BlockState snow = state.setValue(SnowLayerBlock.LAYERS, Math.min(state.getValue(SnowLayerBlock.LAYERS) + 1, 8));
                if (snow.canSurvive(level, pos)) {
                    level.setBlockAndUpdate(pos, snow);
                }
            }
        });
    }

    private void applySphere(BlockPos center, int radius, Consumer<BlockPos> effect) {
        for (int y = -radius; y <= radius; y++) {
            double xRadius = Math.sqrt(radius * radius - y * y);
            int xRounded = (int) xRadius;
            for (int x = -xRounded; x <= xRounded; x++) {
                int zRounded = (int) Math.sqrt(xRadius * xRadius - x * x);
                for (int z = -zRounded; z <= zRounded; z++) {
                    effect.accept(center.offset(x, y, z));
                }
            }
        }
    }
}
