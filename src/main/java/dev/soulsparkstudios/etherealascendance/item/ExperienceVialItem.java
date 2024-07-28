package dev.soulsparkstudios.etherealascendance.item;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ExperienceVialItem extends Item {
    public static final int MAX_XP = 1395;
    public ExperienceVialItem(Settings settings) {
        super(settings);
    }

    protected int getVialExperience(ItemStack itemStack) {
        if (itemStack.getNbt() == null) {
            itemStack.setNbt(new NbtCompound());
        }

        NbtCompound nbt = itemStack.getNbt();
        if (!nbt.contains("experience")) {
            nbt.putInt("experience", 0);
        }

        return nbt.getInt("experience");
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        int vialXP = this.getVialExperience(stack);
        NbtCompound nbt = stack.getNbt();
        nbt.putInt("experience", 0);
        world.playSound((PlayerEntity) null, user.getBlockPos(), EtherealAscendance.EXPERIENCE_VIAL_WITHDRAW, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
        if (user instanceof PlayerEntity player) {
            player.addExperience(vialXP);
        }

        return stack;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        int vialXP = this.getVialExperience(itemStack);
        int playerXP = getPlayerTotalExperience(user);
        NbtCompound nbt = itemStack.getNbt();
        if (user.isSneaking() && vialXP <= 1395 && (playerXP > 0 || user.isCreative())) {
            int freeSpace = 1395 - vialXP;
            int addXp = user.isCreative() ? freeSpace : Math.min(playerXP, freeSpace);
            if (addXp > 0) {
                if (user instanceof ServerPlayerEntity && !user.isCreative()) {
                    user.addExperience(-addXp);
                }

                nbt.putInt("experience", vialXP + addXp);
                world.playSound((PlayerEntity) null, user.getBlockPos(), EtherealAscendance.EXPERIENCE_VIAL_DEPOSIT, SoundCategory.PLAYERS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                return new TypedActionResult(ActionResult.SUCCESS, itemStack);
            }
        } else if (!user.isSneaking() && vialXP > 0) {
            return ItemUsage.consumeHeldItem(world, user, hand);
        }

        return new TypedActionResult(ActionResult.PASS, itemStack);
    }

    public UseAction getUseAction(ItemStack stack) {
        return this.getVialExperience(stack) > 0 ? UseAction.DRINK : UseAction.NONE;
    }

    public SoundEvent getDrinkSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public SoundEvent getEatSound() {
        return SoundEvents.ITEM_HONEY_BOTTLE_DRINK;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    public boolean hasGlint(ItemStack stack) {
        return this.getVialExperience(stack) > 0;
    }

    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(itemStack, world, tooltip, context);
        NbtCompound nbt = itemStack.getNbt();
        if (nbt != null) {
            tooltip.add(Text.translatable("item.etherealascendance.experience_vial.levels", new Object[]{Text.literal(Objects.toString(nbt.getInt("experience")) + "/1395").formatted(Formatting.GREEN)}).formatted(Formatting.GRAY));
        }

        if (world != null && world.isClient) {
            tooltip.add(Text.translatable("item.etherealascendance.experience_vial.usage.fill", new Object[]{Text.keybind("key.sneak"), Text.keybind("key.use")}).formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("item.etherealascendance.experience_vial.usage.drain", new Object[]{Text.keybind("key.use")}).formatted(Formatting.GRAY));
        }

    }

    private static int getLevelUpExperience(int level) {
        if (level >= 30) {
            return 112 + (level - 30) * 9;
        } else {
            return level >= 15 ? 37 + (level - 15) * 5 : 7 + level * 2;
        }
    }

    private static int getPlayerTotalExperience(PlayerEntity user) {
        int levels = user.experienceLevel;

        int points;
        for(points = 0; levels > 0; points += getLevelUpExperience(levels)) {
            --levels;
        }

        points += Math.round((float)user.getNextLevelExperience() * user.experienceProgress);
        return points;
    }
}
