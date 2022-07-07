package io.github.davidqf555.minecraft.yggdrasil.common.world;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.BlockRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.POIRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiRecord;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;
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
    public PortalInfo getPortalInfo(Entity entity, ServerLevel destWorld, Function<ServerLevel, PortalInfo> defaultPortalInfo) {
        DimensionType target = destWorld.dimensionType();
        double scale = DimensionType.getTeleportationScale(entity.level.dimensionType(), target);
        BlockPos scaled = new BlockPos(teleporter.getX() * scale, teleporter.getY(), teleporter.getZ() * scale);
        WorldBorder border = destWorld.getWorldBorder();
        BlockPos clamped = new BlockPos(Mth.clamp(scaled.getX(), border.getMinX(), border.getMaxX()), Mth.clamp(scaled.getY(), 1, target.logicalHeight()), Mth.clamp(scaled.getZ(), border.getMinZ(), border.getMaxZ()));
        return new PortalInfo(Vec3.atBottomCenterOf(getOrCreatePortal(destWorld, entity, clamped, 128)), entity.getDeltaMovement(), entity.getYRot(), entity.getXRot());
    }

    private BlockPos getOrCreatePortal(ServerLevel dest, Entity entity, BlockPos center, int range) {
        PoiManager manager = dest.getPoiManager();
        manager.ensureLoadedAndValid(dest, center, range);
        PoiType poi = POIRegistry.RUNIC_TELEPORTER.get();
        return manager.getInSquare(poi::equals, center, range, PoiManager.Occupancy.ANY)
                .map(PoiRecord::getPos)
                .min(Comparator.comparingDouble(center::distSqr))
                .orElseGet(() -> createPortal(dest, entity, center));
    }

    private BlockPos createPortal(ServerLevel world, Entity entity, BlockPos center) {
        world.getChunk(center);
        BlockPos surface = world.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, center);
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
