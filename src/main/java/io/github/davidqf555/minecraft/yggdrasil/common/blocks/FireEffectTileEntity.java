package io.github.davidqf555.minecraft.yggdrasil.common.blocks;

import io.github.davidqf555.minecraft.yggdrasil.registration.TileEntityRegistry;
import io.github.davidqf555.minecraft.yggdrasil.common.util.EffectHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class FireEffectTileEntity extends EffectTileEntity {

    public FireEffectTileEntity() {
        this(TileEntityRegistry.FIRE_EFFECT.get());
    }

    protected FireEffectTileEntity(TileEntityType<?> type) {
        super(type);
    }

    @Override
    protected double getRange() {
        return 8;
    }

    @Override
    protected int getPeriod() {
        return 100;
    }

    @Override
    protected void applyEntityEffect(Entity entity) {
        EffectHelper.applyFire(entity, 5);
    }

    @Override
    protected void applyBlockEffect(World world, BlockPos pos) {
        if (level.isEmptyBlock(pos)) {
            level.setBlockAndUpdate(pos, Blocks.FIRE.getStateForPlacement(new BlockItemUseContext(level, null, Hand.MAIN_HAND, ItemStack.EMPTY, BlockRayTraceResult.miss(Vector3d.atCenterOf(pos), Direction.NORTH, pos))));
        }
    }
}
