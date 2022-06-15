package io.github.davidqf555.minecraft.yggdrasil.common.items;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.stream.Collectors;

public class SmeltLootModifier extends LootModifier {

    protected SmeltLootModifier(LootItemCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        ServerLevel world = context.getLevel();
        RecipeManager manager = world.getRecipeManager();
        return new ObjectArrayList<>(generatedLoot.stream().map(stack -> {
            SimpleContainer inv = new SimpleContainer(stack);
            Optional<SmeltingRecipe> recipe = manager.getRecipeFor(RecipeType.SMELTING, inv, world);
            return recipe.map(furnaceRecipe -> furnaceRecipe.assemble(inv)).orElse(stack);
        }).collect(Collectors.toList()));
    }

    public static class Serializer extends GlobalLootModifierSerializer<SmeltLootModifier> {

        @Override
        public SmeltLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new SmeltLootModifier(conditions);
        }

        @Override
        public JsonObject write(SmeltLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
