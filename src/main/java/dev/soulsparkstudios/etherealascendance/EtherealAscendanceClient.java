package dev.soulsparkstudios.etherealascendance;

import dev.soulsparkstudios.etherealascendance.client.render.world.SpaceSky;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceBlocks;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceDimensions;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceFluids;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.post.PostPipeline;
import foundry.veil.api.client.render.post.PostProcessingManager;
import foundry.veil.api.event.VeilRenderLevelStageEvent;
import foundry.veil.platform.VeilEventPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.Iterator;

@SuppressWarnings("unused")
public class EtherealAscendanceClient implements ClientModInitializer {
	public static final SpaceSky SPACE_SKY = new SpaceSky();

	@Override
	public void onInitializeClient() {
		ClientTickEvents.START_WORLD_TICK.register(SPACE_SKY);
		DimensionRenderingRegistry.registerDimensionEffects(EtherealAscendanceDimensions.SPACE.getValue(), new SpaceSky.Effects());
		DimensionRenderingRegistry.registerSkyRenderer(EtherealAscendanceDimensions.SPACE, SPACE_SKY);
		DimensionRenderingRegistry.registerCloudRenderer(EtherealAscendanceDimensions.SPACE, c -> {});

		FluidRenderHandlerRegistry.INSTANCE.register(EtherealAscendanceFluids.STELLARYX_STILL, EtherealAscendanceFluids.STELLARYX_FLOWING, new SimpleFluidRenderHandler(
				new Identifier("etherealascendance:block/fluid/stellaryx_still"),
				new Identifier("etherealascendance:block/fluid/stellaryx_flowing"),
				null
		));
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), EtherealAscendanceFluids.STELLARYX_STILL, EtherealAscendanceFluids.STELLARYX_FLOWING);

		BlockRenderLayerMap.INSTANCE.putBlock(EtherealAscendanceBlocks.GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(EtherealAscendanceBlocks.PRIVACY_GLASS, RenderLayer.getTranslucent());
		}

	public static boolean shouldMultiplyStars() {
		return true;
	}

	public static int multiplyStars() {
		return 10;
	}
}