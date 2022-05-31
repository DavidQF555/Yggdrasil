package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class EffectTileEntity extends TileEntity implements ITickableTileEntity {

    protected EffectTileEntity(TileEntityType<?> type) {
        super(type);
    }

    @Override
    public void tick() {
        if (hasLevel()) {
            World world = getLevel();
            if (world.getGameTime() % getPeriod() == 0) {
                double range = getRange();
                BlockPos pos = getBlockPos();
                Vector3d center = Vector3d.atCenterOf(pos);
                AxisAlignedBB bounds = AxisAlignedBB.ofSize(range * 2, range * 2, range * 2).move(center);
                for (Entity entity : world.getLoadedEntitiesOfClass(Entity.class, bounds)) {
                    if (!hasPath(world, center, entity.getEyePosition(1))) {
                        applyEntityEffect(entity);
                    }
                }
                int roundRange = MathHelper.ceil(range);
                for (int y = -roundRange; y <= roundRange; y++) {
                    double xRadius = Math.sqrt(roundRange * roundRange - y * y);
                    int xRounded = (int) xRadius;
                    for (int x = -xRounded; x <= xRounded; x++) {
                        int zRounded = (int) Math.sqrt(xRadius * xRadius - x * x);
                        for (int z = -zRounded; z <= zRounded; z++) {
                            BlockPos offset = pos.offset(x, y, z);
                            if (hasPath(world, center, Vector3d.atCenterOf(offset))) {
                                applyBlockEffect(world, offset);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean hasPath(World world, Vector3d start, Vector3d end) {
        if (end.distanceToSqr(start) > 128 * 128) {
            return false;
        }
        return world.clip(new RayTraceContext(start, end, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, null)).getType() == RayTraceResult.Type.MISS;
    }

    protected abstract double getRange();

    protected abstract int getPeriod();

    protected abstract void applyEntityEffect(Entity entity);

    protected abstract void applyBlockEffect(World world, BlockPos pos);
}
