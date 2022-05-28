package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class IceArrowEntity extends CustomArrowEntity {

    private static final int SNOW_RADIUS = 5, ICE_RADIUS = 3;
    private static final BlockParticleData PARTICLE = new BlockParticleData(ParticleTypes.BLOCK, Blocks.PACKED_ICE.defaultBlockState());

    public IceArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world, ItemStack pickup) {
        super(type, world, pickup, PARTICLE);
    }

    public IceArrowEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity owner, World world) {
        super(type, owner, world, PARTICLE);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity target) {
        super.doPostHurtEffects(target);
        double damage = MathHelper.ceil(MathHelper.clamp(getDeltaMovement().length() * getBaseDamage(), 0, 2.147483647E9));
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
    protected void onHitBlock(BlockRayTraceResult result) {
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
                BlockState snow = state.setValue(SnowBlock.LAYERS, Math.min(state.getValue(SnowBlock.LAYERS) + 1, 8));
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
