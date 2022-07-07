package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.world.RunicTeleporter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.Random;

public class RunicTeleporterTileEntity extends BlockEntity {

    private static final int RANGE = 5;
    private ResourceKey<Level> target;
    private int timer;

    protected RunicTeleporterTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public RunicTeleporterTileEntity(BlockPos pos, BlockState state) {
        this(TileEntityRegistry.RUNIC_TELEPORTER.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, RunicTeleporterTileEntity te) {
        if (te.target != null) {
            te.setTimer(te.timer - 1);
            if (te.timer <= 0) {
                te.teleport();
                te.addParticles();
                te.setTarget(null);
                world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(RunicTeleporterBlock.ACTIVE, false));
            }
        }
    }

    public void setTarget(ResourceKey<Level> target) {
        this.target = target;
        setChanged();
    }

    public void setTimer(int timer) {
        this.timer = timer;
        setChanged();
    }

    private void teleport() {
        BlockPos pos = getBlockPos();
        Level world = getLevel();
        AABB bounds = AABB.ofSize(Vec3.atLowerCornerOf(pos), RANGE, RANGE, RANGE);
        if (world instanceof ServerLevel) {
            RunicTeleporter teleporter = new RunicTeleporter(pos);
            ServerLevel server = ((ServerLevel) world).getServer().getLevel(target);
            for (Entity entity : world.getEntitiesOfClass(Entity.class, bounds)) {
                entity.changeDimension(server, teleporter);
            }
        }
    }

    private void addParticles() {
        Level world = getLevel();
        if (world.isClientSide()) {
            Vec3 center = Vec3.atCenterOf(getBlockPos());
            Random rand = world.getRandom();
            int rounded = Mth.ceil(RANGE / 2.0);
            for (int dY = -rounded; dY <= rounded; dY++) {
                for (int dX = -rounded; dX <= rounded; dX++) {
                    for (int dZ = -rounded; dZ <= rounded; dZ++) {
                        Vec3 pos = center.add(dX, dY, dZ);
                        for (int i = 0; i < 20; i++) {
                            double x = pos.x() + rand.nextDouble() - 0.5;
                            double y = pos.y() + rand.nextDouble() - 0.5;
                            double z = pos.z() + rand.nextDouble() - 0.5;
                            world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Timer", timer);
        if (target != null) {
            tag.putString("Target", target.location().toString());
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("Timer", Tag.TAG_INT)) {
            setTimer(tag.getInt("Timer"));
        }
        if (tag.contains("Target", Tag.TAG_STRING)) {
            setTarget(ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("Target"))));
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return serializeNBT();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

}
