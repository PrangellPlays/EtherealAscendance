package dev.soulsparkstudios.etherealascendance.block.space_helmet.type;

import dev.soulsparkstudios.etherealascendance.block.space_helmet.SpaceHelmetBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpaceHelemtRatBlock extends SpaceHelmetBlock {
    public SpaceHelemtRatBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(Text.translatable("block.etherealascendance.space_helmet_rat_desc").formatted(Formatting.DARK_GRAY));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
