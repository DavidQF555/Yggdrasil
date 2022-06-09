package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.FireArrowEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.entities.IceArrowEntity;
import io.github.davidqf555.minecraft.yggdrasil.common.items.CustomArrowItem;
import io.github.davidqf555.minecraft.yggdrasil.common.items.WorldPointerItem;
import io.github.davidqf555.minecraft.yggdrasil.common.items.YggdrasilArmorMaterial;
import io.github.davidqf555.minecraft.yggdrasil.common.items.YggdrasilItemTier;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.worldgen.DimensionRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Yggdrasil.ID);

    public static final RegistryObject<Item> NIFLIUM = register("niflium", () -> new Item(new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<Item> MUSPELLIUM = register("muspellium", () -> new Item(new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));

    public static final RegistryObject<BlockItem> NIFLIUM_ORE = register("niflium_ore", () -> new BlockItem(BlockRegistry.NIFLIUM_ORE.get(), new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<BlockItem> MUSPELLIUM_ORE = register("muspellium_ore", () -> new BlockItem(BlockRegistry.MUSPELLIUM_ORE.get(), new Item.Properties().tab(Yggdrasil.GROUP)));

    public static final RegistryObject<BlockItem> NIFLIUM_BLOCK = register("niflium_block", () -> new BlockItem(BlockRegistry.NIFLIUM_BLOCK.get(), new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<BlockItem> MUSPELLIUM_BLOCK = register("muspellium_block", () -> new BlockItem(BlockRegistry.MUSPELLIUM_BLOCK.get(), new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));

    public static final RegistryObject<SwordItem> MUSPELLIUM_SWORD = register("muspellium_sword", () -> new SwordItem(YggdrasilItemTier.MUSPELLIUM, 3, -2.4f, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<ShovelItem> MUSPELLIUM_SHOVEL = register("muspellium_shovel", () -> new ShovelItem(YggdrasilItemTier.MUSPELLIUM, 1.5f, -3, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<PickaxeItem> MUSPELLIUM_PICKAXE = register("muspellium_pickaxe", () -> new PickaxeItem(YggdrasilItemTier.MUSPELLIUM, 1, -2.8f, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<AxeItem> MUSPELLIUM_AXE = register("muspellium_axe", () -> new AxeItem(YggdrasilItemTier.MUSPELLIUM, 5, -3, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<HoeItem> MUSPELLIUM_HOE = register("muspellium_hoe", () -> new HoeItem(YggdrasilItemTier.MUSPELLIUM, -3, 0, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<SwordItem> NIFLIUM_SWORD = register("niflium_sword", () -> new SwordItem(YggdrasilItemTier.NIFLIUM, 3, -2.4f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ShovelItem> NIFLIUM_SHOVEL = register("niflium_shovel", () -> new ShovelItem(YggdrasilItemTier.NIFLIUM, 1.5f, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<PickaxeItem> NIFLIUM_PICKAXE = register("niflium_pickaxe", () -> new PickaxeItem(YggdrasilItemTier.NIFLIUM, 1, -2.8f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<AxeItem> NIFLIUM_AXE = register("niflium_axe", () -> new AxeItem(YggdrasilItemTier.NIFLIUM, 5, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<HoeItem> NIFLIUM_HOE = register("niflium_hoe", () -> new HoeItem(YggdrasilItemTier.NIFLIUM, -3, 0, new Item.Properties().tab(Yggdrasil.GROUP)));

    public static final RegistryObject<CustomArrowItem> NIFLIUM_ARROW = register("niflium_arrow", () -> new CustomArrowItem((world, entity) -> new IceArrowEntity(EntityRegistry.NIFLIUM_ARROW.get(), entity, world), new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<CustomArrowItem> MUSPELLIUM_ARROW = register("muspellium_arrow", () -> new CustomArrowItem((world, entity) -> new FireArrowEntity(EntityRegistry.MUSPELLIUM_ARROW.get(), entity, world), new Item.Properties().tab(Yggdrasil.GROUP)));

    public static final RegistryObject<ArmorItem> NIFLIUM_HELMET = register("niflium_helmet", () -> new ArmorItem(YggdrasilArmorMaterial.NIFLIUM, EquipmentSlotType.HEAD, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ArmorItem> NIFLIUM_CHESTPLATE = register("niflium_chestplate", () -> new ArmorItem(YggdrasilArmorMaterial.NIFLIUM, EquipmentSlotType.CHEST, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ArmorItem> NIFLIUM_LEGGINGS = register("niflium_leggings", () -> new ArmorItem(YggdrasilArmorMaterial.NIFLIUM, EquipmentSlotType.LEGS, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ArmorItem> NIFLIUM_BOOTS = register("niflium_boots", () -> new ArmorItem(YggdrasilArmorMaterial.NIFLIUM, EquipmentSlotType.FEET, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ArmorItem> MUSPELLIUM_HELMET = register("muspellium_helmet", () -> new ArmorItem(YggdrasilArmorMaterial.MUSPELLIUM, EquipmentSlotType.HEAD, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<ArmorItem> MUSPELLIUM_CHESTPLATE = register("muspellium_chestplate", () -> new ArmorItem(YggdrasilArmorMaterial.MUSPELLIUM, EquipmentSlotType.CHEST, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<ArmorItem> MUSPELLIUM_LEGGINGS = register("muspellium_leggings", () -> new ArmorItem(YggdrasilArmorMaterial.MUSPELLIUM, EquipmentSlotType.LEGS, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));
    public static final RegistryObject<ArmorItem> MUSPELLIUM_BOOTS = register("muspellium_boots", () -> new ArmorItem(YggdrasilArmorMaterial.MUSPELLIUM, EquipmentSlotType.FEET, new Item.Properties().tab(Yggdrasil.GROUP).fireResistant()));

    public static final RegistryObject<WorldPointerItem> MUSPELHEIM_RUNE = register("muspelheim_rune", () -> new WorldPointerItem(DimensionRegistry.MUSPELHEIM, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<WorldPointerItem> OVERWORLD_RUNE = register("overworld_rune", () -> new WorldPointerItem(World.OVERWORLD, new Item.Properties().tab(Yggdrasil.GROUP)));

    public static final RegistryObject<BlockItem> BLACKSTONE_MUSPELLIUM_ORE = register("blackstone_muspellium_ore", () -> new BlockItem(BlockRegistry.BLACKSTONE_MUSPELLIUM_ORE.get(), new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<BlockItem> BLACKSTONE_COAL_ORE = register("blackstone_coal_ore", () -> new BlockItem(BlockRegistry.BLACKSTONE_COAL_ORE.get(), new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<BlockItem> BLACKSTONE_IRON_ORE = register("blackstone_iron_ore", () -> new BlockItem(BlockRegistry.BLACKSTONE_IRON_ORE.get(), new Item.Properties().tab(Yggdrasil.GROUP)));

    private ItemRegistry() {
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
