package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class EffectTileEntity extends BlockEntity {

    protected EffectTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        if (world.getGameTime() % getPeriod() == 0) {
            double range = getRange();
            Vec3 center = Vec3.atCenterOf(pos);
            AABB bounds = AABB.ofSize(center, range * 2, range * 2, range * 2);
            for (Entity entity : world.getEntitiesOfClass(Entity.class, bounds)) {
                if (!hasPath(world, center, entity.getEyePosition(1))) {
                    applyEntityEffect(entity);
                }
            }
            int roundRange = Mth.ceil(range);
            for (int y = -roundRange; y <= roundRange; y++) {
                double xRadius = Math.sqrt(roundRange * roundRange - y * y);
                int xRounded = (int) xRadius;
                for (int x = -xRounded; x <= xRounded; x++) {
                    int zRounded = (int) Math.sqrt(xRadius * xRadius - x * x);
                    for (int z = -zRounded; z <= zRounded; z++) {
                        BlockPos offset = pos.offset(x, y, z);
                        if (hasPath(world, center, Vec3.atCenterOf(offset))) {
                            applyBlockEffect(world, offset);
                        }
                    }
                }
            }
        }
    }

    private boolean hasPath(Level world, Vec3 start, Vec3 end) {
        if (end.distanceToSqr(start) > 128 * 128) {
            return false;
        }
        return world.clip(new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, null)).getType() == HitResult.Type.MISS;
    }

    protected abstract double getRange();

    protected abstract int getPeriod();

    protected abstract void applyEntityEffect(Entity entity);

    protected abstract void applyBlockEffect(Level world, BlockPos pos);
}
