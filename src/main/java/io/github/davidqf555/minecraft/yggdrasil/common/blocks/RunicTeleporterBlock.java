package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.common.items.WorldPointerItem;
import io.github.davidqf555.minecraft.yggdrasil.common.registration.TileEntityRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class RunicTeleporterBlock extends BaseEntityBlock {

    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    private static final VoxelShape SHAPE = Shapes.or(Block.box(0, 0, 0, 16, 4, 16), Block.box(4, 4, 4, 12, 9, 12), Block.box(2, 9, 2, 14, 12, 14), Block.box(6, 12, 6, 10, 16, 10));

    public RunicTeleporterBlock(Properties properties) {
        super(properties);
        registerDefaultState(getStateDefinition().any().setValue(ACTIVE, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RunicTeleporterTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide() ? null : createTickerHelper(type, TileEntityRegistry.RUNIC_TELEPORTER.get(), RunicTeleporterTileEntity::tick);
    }

    @SuppressWarnings("deprecation")
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult trace) {
        ItemStack stack = player.getItemInHand(hand);
        if (!state.getValue(ACTIVE) && !stack.isEmpty() && stack.getItem() instanceof WorldPointerItem) {
            WorldPointerItem item = ((WorldPointerItem) stack.getItem());
            ResourceKey<Level> target = item.getTarget();
            if (!target.equals(world.dimension())) {
                if (!player.isCreative()) {
                    stack.shrink(1);
                }
                BlockEntity te = world.getBlockEntity(pos);
                if (te instanceof RunicTeleporterTileEntity) {
                    ((RunicTeleporterTileEntity) te).setTarget(target);
                    ((RunicTeleporterTileEntity) te).setTimer(item.getDelay());
                    world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
                }
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getShape(BlockState p_60555_, BlockGetter p_60556_, BlockPos p_60557_, CollisionContext p_60558_) {
        return SHAPE;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (state.getValue(ACTIVE)) {
            Vec3 center = Vec3.upFromBottomCenterOf(pos, 0.875);
            for (int i = 0; i < 20; i++) {
                double x = center.x() + random.nextDouble() * 0.5 - 0.25;
                double y = center.y() + random.nextDouble() * 0.5 - 0.25;
                double z = center.z() + random.nextDouble() * 0.5 - 0.25;
                world.addParticle(ParticleTypes.ENCHANT, x, y, z, 0, 0, 0);
            }
        }
    }
}
