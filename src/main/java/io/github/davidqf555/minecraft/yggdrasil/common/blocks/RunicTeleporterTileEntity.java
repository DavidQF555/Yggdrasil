package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.world.RunicTeleporter;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Random;

public class RunicTeleporterTileEntity extends TileEntity implements ITickableTileEntity {

    private static final int RANGE = 5;
    private RegistryKey<World> target;
    private int timer;

    protected RunicTeleporterTileEntity(TileEntityType<?> type) {
        super(type);
    }

    public RunicTeleporterTileEntity() {
        this(TileEntityRegistry.RUNIC_TELEPORTER.get());
    }

    @Override
    public void tick() {
        if (hasLevel() && target != null) {
            setTimer(timer - 1);
            if (timer <= 0) {
                teleport();
                addParticles();
                setTarget(null);
                BlockPos pos = getBlockPos();
                World world = getLevel();
                world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(RunicTeleporterBlock.ACTIVE, false));
            }
        }
    }

    public void setTarget(RegistryKey<World> target) {
        this.target = target;
        setChanged();
    }

    public void setTimer(int timer) {
        this.timer = timer;
        setChanged();
    }

    private void teleport() {
        BlockPos pos = getBlockPos();
        World world = getLevel();
        AxisAlignedBB bounds = AxisAlignedBB.ofSize(RANGE, RANGE, RANGE).move(pos);
        if (world instanceof ServerWorld) {
            RunicTeleporter teleporter = new RunicTeleporter(pos);
            ServerWorld server = ((ServerWorld) world).getServer().getLevel(target);
            for (Entity entity : world.getLoadedEntitiesOfClass(Entity.class, bounds)) {
                entity.changeDimension(server, teleporter);
            }
        }
    }

    private void addParticles() {
        World world = getLevel();
        if (world.isClientSide()) {
            Vector3d center = Vector3d.atCenterOf(getBlockPos());
            Random rand = world.getRandom();
            int rounded = MathHelper.ceil(RANGE / 2.0);
            for (int dY = -rounded; dY <= rounded; dY++) {
                for (int dX = -rounded; dX <= rounded; dX++) {
                    for (int dZ = -rounded; dZ <= rounded; dZ++) {
                        Vector3d pos = center.add(dX, dY, dZ);
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
    public CompoundNBT save(CompoundNBT tag) {
        CompoundNBT nbt = super.save(tag);
        nbt.putInt("Timer", timer);
        if (target != null) {
            nbt.putString("Target", target.location().toString());
        }
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        if (tag.contains("Timer", Constants.NBT.TAG_INT)) {
            setTimer(tag.getInt("Timer"));
        }
        if (tag.contains("Target", Constants.NBT.TAG_STRING)) {
            setTarget(RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(tag.getString("Target"))));
        }
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        deserializeNBT(pkt.getTag());
    }
}
