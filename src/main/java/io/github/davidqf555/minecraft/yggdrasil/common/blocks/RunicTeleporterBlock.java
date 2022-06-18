package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.items.WorldPointerItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

public class RunicTeleporterBlock extends TileEntityBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static final VoxelShape SHAPE = VoxelShapes.or(Block.box(0, 0, 0, 16, 4, 16), Block.box(4, 4, 4, 12, 9, 12), Block.box(2, 9, 2, 14, 12, 14), Block.box(6, 12, 6, 10, 16, 10));

    public RunicTeleporterBlock(Properties properties) {
        super((state, reader) -> new RunicTeleporterTileEntity(), properties);
        registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false));
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult trace) {
        ItemStack stack = player.getItemInHand(hand);
        if (!state.getValue(ACTIVE) && !stack.isEmpty() && stack.getItem() instanceof WorldPointerItem) {
            WorldPointerItem item = ((WorldPointerItem) stack.getItem());
            RegistryKey<World> target = item.getTarget();
            if (!target.equals(world.dimension())) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                TileEntity te = world.getBlockEntity(pos);
                if (te instanceof RunicTeleporterTileEntity) {
                    ((RunicTeleporterTileEntity) te).setTarget(target);
                    ((RunicTeleporterTileEntity) te).setTimer(item.getDelay());
                    world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.getValue(ACTIVE)) {
            Vector3d center = Vector3d.upFromBottomCenterOf(pos, 0.875);
            for (int i = 0; i < 20; i++) {
                double x = center.x() + random.nextDouble() * 0.5 - 0.25;
                double y = center.y() + random.nextDouble() * 0.5 - 0.25;
                double z = center.z() + random.nextDouble() * 0.5 - 0.25;
                world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
            }
        }
    }
}
