package dev.soulsparkstudios.etherealascendance.item.canisters;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceBlocks;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceFluids;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;

public class EmptyCanister extends Item {
    public EmptyCanister(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos blockPos = context.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        PlayerEntity player = context.getPlayer();
        //STELLARYX
        if (player.getMainHandStack().isOf(this)) {
            if (world.getBlockState(blockPos.add(1, 0, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.MAIN_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(-1, 0, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.MAIN_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 0, 1)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.MAIN_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 0, -1)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 0, -1), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.MAIN_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 1, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 1, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.MAIN_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            }
        } else if (player.getOffHandStack().isOf(this)) {
            if (world.getBlockState(blockPos.add(1, 0, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(1, 0, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.OFF_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(-1, 0, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(-1, 0, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.OFF_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 0, 1)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 0, 1), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.OFF_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 0, -1)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 0, -1), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.OFF_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            } else if (world.getBlockState(blockPos.add(0, 1, 0)).isOf(EtherealAscendanceBlocks.STELLARYX)) {
                world.setBlockState(blockPos.add(0, 1, 0), Blocks.AIR.getDefaultState());
                player.setStackInHand(Hand.OFF_HAND, EtherealAscendanceItems.STELLARYX_CANISTER.getDefaultStack());
            }
        }
        return super.useOnBlock(context);
    }
}
