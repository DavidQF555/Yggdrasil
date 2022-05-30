package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class CustomArrowEntity extends AbstractArrow {

    private final ParticleOptions particle;
    private ItemStack pickup;

    public CustomArrowEntity(EntityType<? extends AbstractArrow> type, Level world, ItemStack pickup, @Nullable ParticleOptions particle) {
        super(type, world);
        this.pickup = pickup;
        this.particle = particle;
    }

    public CustomArrowEntity(EntityType<? extends AbstractArrow> type, LivingEntity owner, Level world, @Nullable ParticleOptions particle) {
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
                Vec3 vel = getDeltaMovement();
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
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.contains("Pickup Item", Tag.TAG_COMPOUND)) {
            pickup = ItemStack.of(nbt.getCompound("Pickup Item"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.put("Pickup Item", pickup.serializeNBT());
    }

    @Override
    protected ItemStack getPickupItem() {
        return pickup;
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
