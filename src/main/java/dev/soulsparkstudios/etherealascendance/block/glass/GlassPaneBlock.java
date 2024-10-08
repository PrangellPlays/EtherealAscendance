package dev.soulsparkstudios.etherealascendance.block.glass;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class GlassPaneBlock extends HorizontalConnectingBlock {
    public GlassPaneBlock(AbstractBlock.Settings settings) {
        super(1.0F, 1.0F, 16.0F, 16.0F, 16.0F, settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(NORTH, false)).with(EAST, false)).with(SOUTH, false)).with(WEST, false)).with(WATERLOGGED, false));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockPos blockPos2 = blockPos.north();
        BlockPos blockPos3 = blockPos.south();
        BlockPos blockPos4 = blockPos.west();
        BlockPos blockPos5 = blockPos.east();
        BlockState blockState = blockView.getBlockState(blockPos2);
        BlockState blockState2 = blockView.getBlockState(blockPos3);
        BlockState blockState3 = blockView.getBlockState(blockPos4);
        BlockState blockState4 = blockView.getBlockState(blockPos5);
        return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with(NORTH, this.connectsTo(blockState, blockState.isSideSolidFullSquare(blockView, blockPos2, Direction.SOUTH)))).with(SOUTH, this.connectsTo(blockState2, blockState2.isSideSolidFullSquare(blockView, blockPos3, Direction.NORTH)))).with(WEST, this.connectsTo(blockState3, blockState3.isSideSolidFullSquare(blockView, blockPos4, Direction.EAST)))).with(EAST, this.connectsTo(blockState4, blockState4.isSideSolidFullSquare(blockView, blockPos5, Direction.WEST)))).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return direction.getAxis().isHorizontal() ? (BlockState)state.with((Property)FACING_PROPERTIES.get(direction), this.connectsTo(neighborState, neighborState.isSideSolidFullSquare(world, neighborPos, direction.getOpposite()))) : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.isOf(this)) {
            if (!direction.getAxis().isHorizontal()) {
                return true;
            }

            if ((Boolean)state.get((Property)FACING_PROPERTIES.get(direction)) && (Boolean)stateFrom.get((Property)FACING_PROPERTIES.get(direction.getOpposite()))) {
                return true;
            }
        }

        return super.isSideInvisible(state, stateFrom, direction);
    }

    public final boolean connectsTo(BlockState state, boolean sideSolidFullSquare) {
        return !cannotConnect(state) && sideSolidFullSquare || state.getBlock() instanceof PaneBlock || state.isIn(BlockTags.WALLS);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{NORTH, EAST, WEST, SOUTH, WATERLOGGED});
    }
}
