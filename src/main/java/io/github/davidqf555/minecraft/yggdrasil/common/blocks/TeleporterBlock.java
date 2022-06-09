package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.items.WorldPointerItem;
import io.github.davidqf555.minecraft.yggdrasil.common.world.RunicTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class TeleporterBlock extends Block {

    private static final int RANGE = 5;

    public TeleporterBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        ItemStack stack = player.getItemInHand(hand);
        if (!stack.isEmpty() && stack.getItem() instanceof WorldPointerItem) {
            RegistryKey<World> target = ((WorldPointerItem) stack.getItem()).getTarget();
            if (!target.equals(world.dimension())) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                AxisAlignedBB bounds = AxisAlignedBB.ofSize(RANGE, RANGE, RANGE).move(pos);
                if (world instanceof ServerWorld) {
                    RunicTeleporter teleporter = new RunicTeleporter(pos);
                    ServerWorld server = ((ServerWorld) world).getServer().getLevel(target);
                    for (Entity entity : world.getLoadedEntitiesOfClass(Entity.class, bounds)) {
                        entity.changeDimension(server, teleporter);
                    }
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

}
