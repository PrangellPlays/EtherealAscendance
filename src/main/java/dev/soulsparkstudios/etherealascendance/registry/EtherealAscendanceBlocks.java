package dev.soulsparkstudios.etherealascendance.registry;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.block.glass.GlassBlock;
import dev.soulsparkstudios.etherealascendance.block.privacy_glass.PrivacyGlassBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class EtherealAscendanceBlocks {
    public static final Block STELLARYX = registerBlockWithoutBlockItem("stellaryx", new FluidBlock(EtherealAscendanceFluids.STELLARYX_STILL, FabricBlockSettings.copyOf(Blocks.WATER).replaceable().noCollision().strength(100.0f).pistonBehavior(PistonBehavior.DESTROY).dropsNothing().liquid().solid().sounds(BlockSoundGroup.ANCIENT_DEBRIS)));

    //Glass
    public static final Block GLASS = registerBlock("glass", new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));

    //Privacy Glass
    public static final Block PRIVACY_GLASS = registerBlock("privacy_glass", new PrivacyGlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).nonOpaque()));

    private static Block registerBlockWithoutBlockItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(EtherealAscendance.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(EtherealAscendance.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        Item item = Registry.register(Registries.ITEM, new Identifier(EtherealAscendance.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
        return item;
    }

    public static void init() {
        EtherealAscendance.LOGGER.info("Registering ModBlocks for " + EtherealAscendance.MOD_ID);
    }
}
