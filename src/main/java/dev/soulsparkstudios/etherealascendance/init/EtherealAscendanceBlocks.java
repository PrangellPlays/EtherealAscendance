package dev.soulsparkstudios.etherealascendance.init;

import com.google.common.collect.UnmodifiableIterator;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.block.glass.GlassBlock;
import dev.soulsparkstudios.etherealascendance.block.privacy_glass.PrivacyGlassBlock;
import dev.soulsparkstudios.etherealascendance.block.space_helmet.type.SpaceHelemtRatBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class EtherealAscendanceBlocks {
    protected static final Map<Block, Identifier> BLOCKS = new LinkedHashMap();
    public static final Block STELLARYX;
    public static final Block GLASS;
    public static final Block PRIVACY_GLASS;
    public static final Block SPACE_HELMET_RAT;

    public static void init() {
        BLOCKS.forEach((block, id) -> {
            Registry.register(Registries.BLOCK, id, block);
        });
    }

    protected static <T extends Block> T register(String name, T block) {
        BLOCKS.put(block, EtherealAscendance.path(name));
        return block;
    }

    protected static <T extends Block> T registerWithItem(String name, T block) {
        return registerWithItem(name, block, new FabricItemSettings());
    }

    protected static <T extends Block> T registerWithItem(String name, T block, FabricItemSettings settings) {
        return registerWithItem(name, block, (b) -> {
            return new BlockItem(b, settings);
        });
    }

    protected static <T extends Block> T registerWithItem(String name, T block, Function<T, BlockItem> itemGenerator) {
        EtherealAscendanceItems.register(name, (BlockItem)itemGenerator.apply(block));
        return register(name, block);
    }

    public EtherealAscendanceBlocks() {
    }

    static {
        STELLARYX = register("stellaryx", new FluidBlock(EtherealAscendanceFluids.STELLARYX_STILL, FabricBlockSettings.copyOf(Blocks.WATER).replaceable().noCollision().strength(100.0f).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().solid().sounds(BlockSoundGroup.ANCIENT_DEBRIS)));
        GLASS = registerWithItem("glass", new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));
        PRIVACY_GLASS = registerWithItem("privacy_glass", new PrivacyGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));
        SPACE_HELMET_RAT = registerWithItem("space_helmet_rat", new SpaceHelemtRatBlock(AbstractBlock.Settings.create().dynamicBounds().nonOpaque().breakInstantly().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.NETHERITE)), (FabricItemSettings)(new FabricItemSettings()).maxCount(1).fireproof());
        Iterator var0 = Registries.BLOCK.iterator();

        while(var0.hasNext()) {
            Block block = (Block)var0.next();
            UnmodifiableIterator var2 = block.getStateManager().getStates().iterator();

            while(var2.hasNext()) {
                BlockState blockState = (BlockState)var2.next();
                Block.STATE_IDS.add(blockState);
                blockState.initShapeCache();
            }

            block.getLootTableId();
        }
    }
}
