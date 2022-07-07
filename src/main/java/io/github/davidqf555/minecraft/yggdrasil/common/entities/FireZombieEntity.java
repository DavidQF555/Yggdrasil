package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class FireZombieEntity extends Zombie {

    public FireZombieEntity(EntityType<? extends Zombie> type, Level world) {
        super(type, world);
        setPathfindingMalus(BlockPathTypes.LAVA, 8);
        setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0);
        setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0);
        setPathfindingMalus(BlockPathTypes.WATER, -1);
    }

    public static AttributeSupplier.Builder createTorchedAttributes() {
        return createAttributes();
    }

    public static boolean canSpawn(EntityType<FireZombieEntity> type, ServerLevelAccessor world, MobSpawnType reason, BlockPos pos, Random rand) {
        return world.getDifficulty() != Difficulty.PEACEFUL && !world.canSeeSky(pos) && checkMobSpawnRules(type, world, reason, pos, rand);
    }

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType reason) {
        return true;
    }

    @Override
    public float getBrightness() {
        return 1;
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (level.isClientSide()) {
            Vec3 pos = position();
            Random rand = getRandom();
            for (int i = 0; i < 3; ++i) {
                double x = pos.x() + rand.nextDouble() - 0.5;
                double y = pos.y() + rand.nextDouble();
                double z = pos.z() + rand.nextDouble() - 0.5;
                level.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BLAZE_AMBIENT;
    }

    @Override
    protected boolean convertsInWater() {
        return false;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean out = super.doHurtTarget(target);
        if (out) {
            target.setSecondsOnFire(5);
        }
        return out;
    }
}
