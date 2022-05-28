package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.IParticleData;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class CustomArrowEntity extends AbstractArrowEntity {

    private final IParticleData particle;
    private ItemStack pickup;

    public CustomArrowEntity(EntityType<? extends AbstractArrowEntity> type, World world, ItemStack pickup, @Nullable IParticleData particle) {
        super(type, world);
        this.pickup = pickup;
        this.particle = particle;
    }

    public CustomArrowEntity(EntityType<? extends AbstractArrowEntity> type, LivingEntity owner, World world, @Nullable IParticleData particle) {
        super(type, owner, world);
        pickup = ItemStack.EMPTY;
        this.particle = particle;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide() && particle != null) {
            for (int i = 0; i < 2; i++) {
                level.addParticle(particle, getRandomX(0.5), getRandomY(), getRandomZ(0.5), 0, 0, 0);
            }
            if (!inGround) {
                Vector3d vel = getDeltaMovement();
                for (int i = 0; i < 4; ++i) {
                    level.addParticle(particle, getX() + vel.x() * i / 4, getY() + vel.y() * i / 4, getZ() + vel.z() * i / 4, -vel.x(), -vel.y(), -vel.z());
                }
            }
        }
    }

    public void setPickup(ItemStack pickup) {
        this.pickup = pickup;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("Pickup Item", Constants.NBT.TAG_COMPOUND)) {
            pickup = ItemStack.of(nbt.getCompound("Pickup Item"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("Pickup Item", pickup.serializeNBT());
    }

    @Override
    protected ItemStack getPickupItem() {
        return pickup;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
