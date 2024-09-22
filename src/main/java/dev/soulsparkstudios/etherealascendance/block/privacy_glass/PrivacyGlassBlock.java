package dev.soulsparkstudios.etherealascendance.block.privacy_glass;

import net.minecraft.block.*;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PrivacyGlassBlock extends Block implements PrivacyBlock {
    public PrivacyGlassBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) super.getDefaultState().with(PRIVATE, false)).with(INTERACTION_COOLDOWN, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{PRIVATE, INTERACTION_COOLDOWN});
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.shouldCancelInteraction() && !player.getStackInHand(hand).isOf(this.asItem()) && this.canInteract(state, pos, world, player, hand)) {
            this.toggle(state, world, pos);
            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hand, hit);
        }
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.toggle(state, world, pos);
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
        boolean bl = (Boolean)state.get(PRIVATE);
        if (bl) {
            return VoxelShapes.fullCube();
        } else {
            return VoxelShapes.empty();
        }
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
        //tooltip.add(Text.translatable("block.etherealascendance.privacy_glass.description_2").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
