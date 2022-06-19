package io.github.davidqf555.minecraft.yggdrasil.common.world;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.BlockRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.POIRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.village.PointOfInterest;
import net.minecraft.village.PointOfInterestManager;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.DimensionType;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.function.Function;

public class RunicTeleporter implements ITeleporter {

    private static final int CLEAR_WIDTH = 5, CLEAR_HEIGHT = 3;
    private final BlockPos teleporter;

    public RunicTeleporter(BlockPos teleporter) {
        this.teleporter = teleporter;
    }

    @Nullable
    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
        DimensionType target = destWorld.dimensionType();
        double scale = DimensionType.getTeleportationScale(entity.level.dimensionType(), target);
        BlockPos scaled = new BlockPos(teleporter.getX() * scale, teleporter.getY(), teleporter.getZ() * scale);
        WorldBorder border = destWorld.getWorldBorder();
        BlockPos clamped = new BlockPos(MathHelper.clamp(scaled.getX(), border.getMinX(), border.getMaxX()), MathHelper.clamp(scaled.getY(), 1, target.logicalHeight()), MathHelper.clamp(scaled.getZ(), border.getMinZ(), border.getMaxZ()));
        return new PortalInfo(Vector3d.atBottomCenterOf(getOrCreatePortal(destWorld, entity, clamped, 128)), entity.getDeltaMovement(), entity.yRot, entity.xRot);
    }

    private BlockPos getOrCreatePortal(ServerWorld dest, Entity entity, BlockPos center, int range) {
        PointOfInterestManager manager = dest.getPoiManager();
        manager.ensureLoadedAndValid(dest, center, range);
        PointOfInterestType poi = POIRegistry.RUNIC_TELEPORTER.get();
        return manager.getInSquare(poi::equals, center, range, PointOfInterestManager.Status.ANY)
                .map(PointOfInterest::getPos)
                .min(Comparator.comparingDouble(center::distSqr))
                .orElseGet(() -> createPortal(dest, entity, center));
    }

    private BlockPos createPortal(ServerWorld world, Entity entity, BlockPos center) {
        world.getChunk(center);
        BlockPos surface = world.getHeightmapPos(Heightmap.Type.WORLD_SURFACE, center);
        BlockState air = Blocks.AIR.defaultBlockState();
        BlockState def = Blocks.STONE.defaultBlockState();
        int roundWidth = CLEAR_WIDTH / 2;
        for (int y = -1; y < CLEAR_HEIGHT; y++) {
            for (int x = -roundWidth; x <= roundWidth; x++) {
                for (int z = -roundWidth; z <= roundWidth; z++) {
                    BlockPos pos = surface.offset(x, y, z);
                    BlockState state = world.getBlockState(pos);
                    if (state.canEntityDestroy(world, pos, entity)) {
                        if (y == -1) {
                            if (!state.getMaterial().isSolidBlocking()) {
                                world.setBlockAndUpdate(pos, def);
                            }
                        } else {
                            world.setBlockAndUpdate(pos, air);
                        }
                    }
                }
            }
        }
        world.setBlockAndUpdate(surface, BlockRegistry.RUNIC_TELEPORTER.get().defaultBlockState());
        return surface;
    }

}
