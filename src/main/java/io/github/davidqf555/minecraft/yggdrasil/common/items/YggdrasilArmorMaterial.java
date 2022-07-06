package io.github.davidqf555.minecraft.yggdrasil.common.items;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.registration.ItemRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public enum YggdrasilArmorMaterial implements IArmorMaterial {

    NIFLIUM(33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2, 0, Ingredient.of(ItemRegistry.NIFLIUM.get())),
    MUSPELLIUM(33, new int[]{3, 6, 8, 3}, 10, SoundEvents.ARMOR_EQUIP_DIAMOND, 2, 0, Ingredient.of(ItemRegistry.MUSPELLIUM.get()));

    private static final int[] DURABILITY_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durability;
    private final int[] defense;
    private final int enchant;
    private final float toughness, knockback;
    private final SoundEvent equip;
    private final Ingredient repair;

    YggdrasilArmorMaterial(int durability, int[] defense, int enchant, SoundEvent equip, float toughness, float knockback, Ingredient repair) {
        this.toughness = toughness;
        this.knockback = knockback;
        this.durability = durability;
        this.defense = defense;
        this.enchant = enchant;
        this.equip = equip;
        this.repair = repair;
        name = new ResourceLocation(Yggdrasil.ID, name().toLowerCase()).toString();
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlotType type) {
        return DURABILITY_PER_SLOT[type.getIndex()] * durability;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlotType type) {
        return defense[type.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchant;
    }

    @Override
    public SoundEvent getEquipSound() {
        return equip;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repair;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getToughness() {
        return toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return knockback;
    }
}
