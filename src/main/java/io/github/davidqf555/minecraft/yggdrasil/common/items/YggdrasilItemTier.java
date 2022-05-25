package io.github.davidqf555.minecraft.yggdrasil.common.items;

import io.github.davidqf555.minecraft.yggdrasil.common.registration.ItemRegistry;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum YggdrasilItemTier implements IItemTier {

    NIFLIUM(1561, 8, 3, 3, 10, Ingredient.of(ItemRegistry.NIFLIUM.get())),
    MUSPELLIUM(1561, 8, 3, 3, 10, Ingredient.of(ItemRegistry.MUSPELLIUM.get()));

    private final int uses, level, enchant;
    private final float speed, damage;
    private final Ingredient repair;

    YggdrasilItemTier(int uses, float speed, float damage, int level, int enchant, Ingredient repair) {
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.level = level;
        this.enchant = enchant;
        this.repair = repair;
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return damage;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchant;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repair;
    }
}
