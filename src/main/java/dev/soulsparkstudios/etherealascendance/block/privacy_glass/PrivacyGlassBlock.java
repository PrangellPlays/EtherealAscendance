package dev.soulsparkstudios.etherealascendance.block.privacy_glass;

import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceBlocks;
import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrivacyGlassBlock extends Block {
    public static final BooleanProperty UP = BooleanProperty.of("up");
    public static final BooleanProperty DOWN = BooleanProperty.of("down");
    public static final BooleanProperty NORTH = BooleanProperty.of("north");
    public static final BooleanProperty SOUTH = BooleanProperty.of("south");
    public static final BooleanProperty EAST = BooleanProperty.of("east");
    public static final BooleanProperty WEST = BooleanProperty.of("west");
    public static final BooleanProperty PRIVATE;

    public PrivacyGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState()
                .with(UP, false)
                .with(DOWN, false)
                .with(NORTH, false)
                .with(SOUTH, false)
                .with(EAST, false)
                .with(WEST, false)
                .with(PRIVATE, false)
        );
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(UP, DOWN, NORTH, SOUTH, EAST, WEST, PRIVATE);
    }

    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient) {
            boolean bl = (Boolean)state.get(PRIVATE);
            if (pos.isWithinDistance(sourcePos, 1) && world.getBlockState(pos).isOf(EtherealAscendanceBlocks.PRIVACY_GLASS)) {
                world.setBlockState(sourcePos, state.cycle(PRIVATE));
            }
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient() && hand == Hand.MAIN_HAND) {
            world.setBlockState(pos, state.cycle(PRIVATE));
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(PRIVATE, ctx.getWorld().isReceivingRedstonePower(ctx.getBlockPos()));
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return state
                .with(UP, world.getBlockState(pos.up()).getBlock() == this)
                .with(DOWN, world.getBlockState(pos.up()).getBlock() == this)
                .with(NORTH, world.getBlockState(pos.north()).getBlock() == this)
                .with(SOUTH, world.getBlockState(pos.south()).getBlock() == this)
                .with(EAST, world.getBlockState(pos.east()).getBlock() == this)
                .with(WEST, world.getBlockState(pos.west()).getBlock() == this);
    }

    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        boolean bl = (Boolean)state.get(PRIVATE);
        if (bl) {
            return false;
        } else {
            return stateFrom.isOf(this) ? true : super.isSideInvisible(state, stateFrom, direction);
        }
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        boolean bl = (Boolean)state.get(PRIVATE);
        if (bl) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        boolean bl = (Boolean)state.get(PRIVATE);
        if (bl) {
            return world.getMaxLightLevel();
        } else {
            return 0;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.etherealascendance.privacy_glass.description_1", new Object[]{Text.keybind("key.use")}).formatted(Formatting.GRAY));
        tooltip.add(Text.translatable("block.etherealascendance.privacy_glass.description_2").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }

    static {
        PRIVATE = BooleanProperty.of("private");
    }
}
