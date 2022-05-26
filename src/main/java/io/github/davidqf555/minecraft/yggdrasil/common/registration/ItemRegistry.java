package io.github.davidqf555.minecraft.yggdrasil.common.registration;

import io.github.davidqf555.minecraft.yggdrasil.common.Yggdrasil;
import io.github.davidqf555.minecraft.yggdrasil.common.items.YggdrasilItemTier;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public final class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Yggdrasil.ID);

    public static final RegistryObject<Item> NIFLIUM = register("niflium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MUSPELLIUM = register("muspellium", () -> new Item(new Item.Properties()));

    public static final RegistryObject<SwordItem> MUSPELLIUM_SWORD = register("muspellium_sword", () -> new SwordItem(YggdrasilItemTier.MUSPELLIUM, 3, -2.4f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ShovelItem> MUSPELLIUM_SHOVEL = register("muspellium_shovel", () -> new ShovelItem(YggdrasilItemTier.MUSPELLIUM, 1.5f, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<PickaxeItem> MUSPELLIUM_PICKAXE = register("muspellium_pickaxe", () -> new PickaxeItem(YggdrasilItemTier.MUSPELLIUM, 1, -2.8f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<AxeItem> MUSPELLIUM_AXE = register("muspellium_axe", () -> new AxeItem(YggdrasilItemTier.MUSPELLIUM, 5, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<HoeItem> MUSPELLIUM_HOE = register("muspellium_hoe", () -> new HoeItem(YggdrasilItemTier.MUSPELLIUM, -3, 0, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<SwordItem> NIFLIUM_SWORD = register("niflium_sword", () -> new SwordItem(YggdrasilItemTier.NIFLIUM, 3, -2.4f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<ShovelItem> NIFLIUM_SHOVEL = register("niflium_shovel", () -> new ShovelItem(YggdrasilItemTier.NIFLIUM, 1.5f, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<PickaxeItem> NIFLIUM_PICKAXE = register("niflium_pickaxe", () -> new PickaxeItem(YggdrasilItemTier.NIFLIUM, 1, -2.8f, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<AxeItem> NIFLIUM_AXE = register("niflium_axe", () -> new AxeItem(YggdrasilItemTier.NIFLIUM, 5, -3, new Item.Properties().tab(Yggdrasil.GROUP)));
    public static final RegistryObject<HoeItem> NIFLIUM_HOE = register("niflium_hoe", () -> new HoeItem(YggdrasilItemTier.NIFLIUM, -3, 0, new Item.Properties().tab(Yggdrasil.GROUP)));

    private ItemRegistry() {
    }

    private static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
