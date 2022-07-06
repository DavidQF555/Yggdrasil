package io.github.davidqf555.minecraft.yggdrasil.common.entities.giants;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import io.github.davidqf555.minecraft.yggdrasil.registration.ItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

public class FireGiantEntity extends AbstractGiantEntity {

    public FireGiantEntity(EntityType<? extends AbstractGiantEntity> type, World world) {
        super(type, world);
        setPathfindingMalus(PathNodeType.LAVA, 8);
        setPathfindingMalus(PathNodeType.DANGER_FIRE, 0);
        setPathfindingMalus(PathNodeType.DAMAGE_FIRE, 0);
        setPathfindingMalus(PathNodeType.WATER, -1);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new SwimGoal(this));
        goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(2, new RandomWalkingGoal(this, 0.6));
        goalSelector.addGoal(3, new LookAtGoal(this, PlayerEntity.class, 3, 1));
        goalSelector.addGoal(4, new LookAtGoal(this, MobEntity.class, 8));
        targetSelector.addGoal(0, new HurtByTargetGoal(this, FireGiantEntity.class).setAlertOthers());
        targetSelector.addGoal(1, new ResetAngerGoal<>(this, false));
    }

    @Override
    protected Multimap<EquipmentSlotType, ItemStack> getPossibleGear() {
        ImmutableMultimap.Builder<EquipmentSlotType, ItemStack> builder = ImmutableMultimap.builder();
        builder.putAll(EquipmentSlotType.HEAD, Items.IRON_HELMET.getDefaultInstance(), ItemRegistry.MUSPELLIUM_HELMET.get().getDefaultInstance());
        builder.putAll(EquipmentSlotType.CHEST, Items.IRON_CHESTPLATE.getDefaultInstance(), ItemRegistry.MUSPELLIUM_CHESTPLATE.get().getDefaultInstance());
        builder.putAll(EquipmentSlotType.LEGS, Items.IRON_LEGGINGS.getDefaultInstance(), ItemRegistry.MUSPELLIUM_LEGGINGS.get().getDefaultInstance());
        builder.putAll(EquipmentSlotType.FEET, Items.IRON_BOOTS.getDefaultInstance(), ItemRegistry.MUSPELLIUM_BOOTS.get().getDefaultInstance());
        builder.putAll(EquipmentSlotType.MAINHAND, Items.IRON_SWORD.getDefaultInstance(), Items.IRON_AXE.getDefaultInstance(), Items.IRON_PICKAXE.getDefaultInstance(), ItemRegistry.MUSPELLIUM_SWORD.get().getDefaultInstance(), ItemRegistry.MUSPELLIUM_AXE.get().getDefaultInstance(), ItemRegistry.MUSPELLIUM_PICKAXE.get().getDefaultInstance());
        return builder.build();
    }

    @Override
    protected VillagerTrades.ITrade[] getTrades() {
        return new VillagerTrades.ITrade[]{
                new GenericTrade(Items.IRON_INGOT.getDefaultInstance(), 3, 6, ItemStack.EMPTY, 0, 0, ItemRegistry.MUSPELLIUM.get().getDefaultInstance(), 1, 1, 0, 10, 0, 1, 0),
                new GenericTrade(Items.BUCKET.getDefaultInstance(), 1, 1, ItemStack.EMPTY, 0, 0, Items.LAVA_BUCKET.getDefaultInstance(), 1, 1, 0, 10, 0, 1, 0),
                new CombinationTrade(
                        new GenericTrade(Items.BLAZE_POWDER.getDefaultInstance(), 3, 4, ItemStack.EMPTY, 0, 0, Items.BLAZE_ROD.getDefaultInstance(), 1, 1, 0, 5, 0, 1, 0),
                        new GenericTrade(Items.SLIME_BALL.getDefaultInstance(), 3, 6, ItemStack.EMPTY, 0, 0, Items.MAGMA_CREAM.getDefaultInstance(), 1, 1, 0, 5, 0, 1, 0)
                ),
                new CombinationTrade(
                        new GenericTrade(Items.STONE.getDefaultInstance(), 10, 20, Items.IRON_INGOT.getDefaultInstance(), 1, 3, ItemRegistry.MANNAZ_RUNE.get().getDefaultInstance(), 1, 1, 0, 5, 0, 1, 0),
                        new GenericTrade(Items.STONE.getDefaultInstance(), 10, 20, ItemRegistry.MUSPELLIUM.get().getDefaultInstance(), 1, 3, ItemRegistry.KENAZ_RUNE.get().getDefaultInstance(), 1, 1, 0, 5, 0, 1, 0)
                )
        };
    }

    @Override
    public float getBrightness() {
        return 1;
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
            float width = getBbWidth();
            float height = getBbHeight();
            float volume = width * width * height;
            for (int i = 0; i < volume / 5 + 1; ++i) {
                double x = pos.x() + (rand.nextDouble() - 0.5) * width;
                double y = pos.y() + rand.nextDouble() * height;
                double z = pos.z() + (rand.nextDouble() - 0.5) * width;
                level.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0, 0, 0);
            }
        }
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
