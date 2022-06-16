package io.github.davidqf555.minecraft.yggdrasil.common.entities.giants;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.merchant.IMerchant;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MerchantOffer;
import net.minecraft.item.MerchantOffers;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.UUID;

public abstract class AbstractGiantEntity extends CreatureEntity implements IMerchant, IAngerable {

    protected static final UUID SIZE_MODIFIER = UUID.fromString("5d4673d6-5641-4fba-ac63-75f11167db13");
    private static final DataParameter<Float> SIZE = EntityDataManager.defineId(AbstractGiantEntity.class, DataSerializers.FLOAT);
    private static final RangedInteger ANGER_RANGE = TickRangeConverter.rangeOfSeconds(20, 39);
    private PlayerEntity trading;
    private MerchantOffers offers;
    private int angerTime;
    private UUID angerTarget;

    protected AbstractGiantEntity(EntityType<? extends CreatureEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute createGiantAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.MOVEMENT_SPEED, 0.4);
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
        ILivingEntityData out = super.finalizeSpawn(world, difficulty, reason, data, tag);
        setSizeFactor(getInitialSize(difficulty));
        initializeGear(difficulty);
        return out;
    }

    protected void initializeGear(DifficultyInstance difficulty) {
        float chance = getGearChance(difficulty);
        Multimap<EquipmentSlotType, ItemStack> possible = getPossibleGear();
        for (EquipmentSlotType slot : possible.keySet()) {
            Collection<ItemStack> gear = possible.get(slot);
            if (gear.size() > 0 && random.nextFloat() < chance) {
                setItemSlot(slot, new ArrayList<>(gear).get(random.nextInt(gear.size())));
            }
        }
    }

    protected float getInitialSize(DifficultyInstance difficulty) {
        return getRandom().nextFloat() * difficulty.getEffectiveDifficulty() / 6.75f * 20 + 1.5f;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        getEntityData().define(SIZE, 1f);
    }

    public float getSizeFactor() {
        return getEntityData().get(SIZE);
    }

    public void setSizeFactor(float size) {
        getEntityData().set(SIZE, size);
        if (!level.isClientSide()) {
            AttributeModifierManager manager = getAttributes();
            manager.addTransientAttributeModifiers(getSizeModifiers());
        }
    }

    protected Multimap<Attribute, AttributeModifier> getSizeModifiers() {
        float size = getSizeFactor();
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.MAX_HEALTH, new AttributeModifier(SIZE_MODIFIER, "Size", size - 1, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(SIZE_MODIFIER, "Size", 1 - 1 / size, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SIZE_MODIFIER, "Size", 0.8 / size - 0.8, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(SIZE_MODIFIER, "Size", size - 1, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(SIZE_MODIFIER, "Size", 0.8 / size - 0.8, AttributeModifier.Operation.MULTIPLY_BASE));
        builder.put(Attributes.ATTACK_KNOCKBACK, new AttributeModifier(SIZE_MODIFIER, "Size", size - 1, AttributeModifier.Operation.MULTIPLY_BASE));
        return builder.build();
    }

    protected Multimap<EquipmentSlotType, ItemStack> getPossibleGear() {
        return ImmutableMultimap.of();
    }

    protected float getGearChance(DifficultyInstance difficulty) {
        return difficulty.getEffectiveDifficulty() / 6.75f;
    }

    @Override
    public float getScale() {
        return super.getScale() * getSizeFactor();
    }

    @Override
    public void onSyncedDataUpdated(DataParameter<?> parameter) {
        super.onSyncedDataUpdated(parameter);
        if (SIZE.equals(parameter)) {
            refreshDimensions();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (level instanceof ServerWorld) {
            updatePersistentAnger((ServerWorld) level, true);
        }
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return angerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        angerTime = time;
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return angerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID id) {
        angerTarget = id;
    }

    @Override
    public void startPersistentAngerTimer() {
        setRemainingPersistentAngerTime(ANGER_RANGE.randomValue(getRandom()));
    }

    @Nullable
    @Override
    public PlayerEntity getTradingPlayer() {
        return trading;
    }

    @Override
    public void setTradingPlayer(@Nullable PlayerEntity player) {
        trading = player;
    }

    @Override
    public MerchantOffers getOffers() {
        if (offers == null) {
            setOffers(new MerchantOffers());
            updateOffers();
        }
        return offers;
    }

    public void setOffers(MerchantOffers offers) {
        this.offers = offers;
    }

    protected void updateOffers() {
        MerchantOffers offers = getOffers();
        Random rand = getRandom();
        for (VillagerTrades.ITrade trade : getTrades()) {
            offers.add(trade.getOffer(this, rand));
        }
    }

    protected abstract VillagerTrades.ITrade[] getTrades();

    @Override
    public void overrideOffers(@Nullable MerchantOffers offers) {
    }

    @Override
    public void notifyTrade(MerchantOffer offer) {
        offer.increaseUses();
        ambientSoundTime = -getAmbientSoundInterval();
    }

    @Override
    public void notifyTradeUpdated(ItemStack stack) {
        if (!level.isClientSide && ambientSoundTime > -getAmbientSoundInterval() + 20) {
            ambientSoundTime = -getAmbientSoundInterval();
        }
    }

    @Override
    public World getLevel() {
        return level;
    }

    @Override
    public int getVillagerXp() {
        return 0;
    }

    @Override
    public void overrideXp(int xp) {

    }

    @Override
    protected ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (isAlive() && getTradingPlayer() == null && !isSleeping() && !player.isSecondaryUseActive()) {
            if (!level.isClientSide() && !getOffers().isEmpty()) {
                setTradingPlayer(player);
                openTradingScreen(player, getDisplayName(), 0);
            }
            return ActionResultType.sidedSuccess(level.isClientSide());
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public boolean showProgressBar() {
        return true;
    }

    @Override
    public SoundEvent getNotifyTradeSound() {
        return SoundEvents.VILLAGER_TRADE;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        if (level instanceof ServerWorld) {
            readPersistentAngerSaveData((ServerWorld) level, tag);
        }
        if (tag.contains("Size", Constants.NBT.TAG_FLOAT)) {
            setSizeFactor(tag.getFloat("Size"));
        }
        if (tag.contains("Offers", Constants.NBT.TAG_COMPOUND)) {
            setOffers(new MerchantOffers(tag.getCompound("Offers")));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        addPersistentAngerSaveData(tag);
        tag.putFloat("Size", getSizeFactor());
        MerchantOffers offers = getOffers();
        if (!offers.isEmpty()) {
            tag.put("Offers", offers.createTag());
        }
    }

    public static class CombinationTrade implements VillagerTrades.ITrade {

        private final VillagerTrades.ITrade[] trades;

        public CombinationTrade(VillagerTrades.ITrade... trades) {
            this.trades = trades;
            if (trades.length < 1) {
                throw new RuntimeException("Combination trade must have length of at least 1");
            }
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            return Util.getRandom(trades, random).getOffer(entity, random);
        }
    }

    public static class GenericTrade implements VillagerTrades.ITrade {

        private final ItemStack cost1, cost2, result;
        private final int minCost1, minCost2, maxCost1, maxCost2, minResult, maxResult, uses, maxUses, xp, demand;
        private final float priceMultiplier;

        public GenericTrade(ItemStack cost1, int minCost1, int maxCost1, ItemStack cost2, int minCost2, int maxCost2, ItemStack result, int minResult, int maxResult, int uses, int maxUses, int xp, int demand, float priceMultiplier) {
            this.cost1 = cost1;
            this.minCost1 = minCost1;
            this.maxCost1 = maxCost1;
            this.cost2 = cost2;
            this.minCost2 = minCost2;
            this.maxCost2 = maxCost2;
            this.result = result;
            this.minResult = minResult;
            this.maxResult = maxResult;
            this.uses = uses;
            this.maxUses = maxUses;
            this.xp = xp;
            this.demand = demand;
            this.priceMultiplier = priceMultiplier;
        }

        @Nullable
        @Override
        public MerchantOffer getOffer(Entity entity, Random random) {
            ItemStack cost1 = this.cost1.copy();
            cost1.setCount(random.nextInt(maxCost1 - minCost1 + 1) + minCost1);
            ItemStack cost2 = this.cost2.copy();
            cost2.setCount(random.nextInt(maxCost2 - minCost2 + 1) + minCost2);
            ItemStack result = this.result.copy();
            result.setCount(random.nextInt(maxResult - minResult + 1) + minResult);
            return new MerchantOffer(cost1, cost2, result, uses, maxUses, xp, priceMultiplier, demand);
        }
    }
}
