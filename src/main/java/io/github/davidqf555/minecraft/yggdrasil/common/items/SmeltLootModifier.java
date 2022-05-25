package io.github.davidqf555.minecraft.yggdrasil.common.items;

import com.google.gson.JsonObject;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SmeltLootModifier extends LootModifier {

    protected SmeltLootModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        ServerWorld world = context.getLevel();
        RecipeManager manager = world.getRecipeManager();
        return generatedLoot.stream().map(stack -> {
            Inventory inv = new Inventory(stack);
            Optional<FurnaceRecipe> recipe = manager.getRecipeFor(IRecipeType.SMELTING, inv, world);
            return recipe.map(furnaceRecipe -> furnaceRecipe.assemble(inv)).orElse(stack);
        }).collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<SmeltLootModifier> {

        @Override
        public SmeltLootModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new SmeltLootModifier(conditions);
        }

        @Override
        public JsonObject write(SmeltLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
