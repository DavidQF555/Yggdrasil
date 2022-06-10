package io.github.davidqf555.minecraft.yggdrasil.common.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class FireZombieEntity extends ZombieEntity {

    public FireZombieEntity(EntityType<? extends ZombieEntity> type, World world) {
        super(type, world);
        setPathfindingMalus(PathNodeType.LAVA, 8);
        setPathfindingMalus(PathNodeType.DANGER_FIRE, 0);
        setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0);
        setPathfindingMalus(PathNodeType.WATER, -1);
    }

    public static AttributeModifierMap.MutableAttribute createTorchedAttributes() {
        return createAttributes();
    }

    public static boolean canSpawn(EntityType<FireZombieEntity> type, IServerWorld world, SpawnReason reason, BlockPos pos, Random rand) {
        return world.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(type, world, reason, pos, rand);
    }

    @Override
    public boolean checkSpawnRules(IWorld world, SpawnReason reason) {
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
            Vector3d pos = position();
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
