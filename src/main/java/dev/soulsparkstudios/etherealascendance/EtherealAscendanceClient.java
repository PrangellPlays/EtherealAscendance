package dev.soulsparkstudios.etherealascendance;

import dev.soulsparkstudios.etherealascendance.dimension.EtherealAscendanceDimensions;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceBlocks;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceFluids;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

@SuppressWarnings("unused")
public class EtherealAscendanceClient implements ClientModInitializer {

	public static Identifier renderAsMoon(String path) {
		return EtherealAscendance.path("textures/environment/phases/" + path + ".png");
	}
	public static Identifier renderAsFloorBody(String path) {
		return EtherealAscendance.path("textures/environment/body/" + path + ".png");
	}

	public static final Identifier SKYBOX_UP = EtherealAscendance.path("textures/environment/sky/space/space1.png");
	public static final Identifier SKYBOX_DOWN = EtherealAscendance.path("textures/environment/sky/space/space2.png");
	public static final Identifier SKYBOX_LEFT = EtherealAscendance.path("textures/environment/sky/space/space3.png");
	public static final Identifier SKYBOX_RIGHT = EtherealAscendance.path("textures/environment/sky/space/space4.png");
	public static final Identifier SKYBOX_FRONT = EtherealAscendance.path("textures/environment/sky/space/space5.png");
	public static final Identifier SKYBOX_BACK = EtherealAscendance.path("textures/environment/sky/space/space6.png");
	public static final Identifier SUN = EtherealAscendance.path("textures/environment/sun.png");
	public static final Identifier MOON = EtherealAscendance.path("textures/environment/moon.png");

	@Override
	public void onInitializeClient() {
		FluidRenderHandlerRegistry.INSTANCE.register(EtherealAscendanceFluids.STELLARYX_STILL, EtherealAscendanceFluids.STELLARYX_FLOWING, new SimpleFluidRenderHandler(
				new Identifier("etherealascendance:block/fluid/stellaryx_still"),
				new Identifier("etherealascendance:block/fluid/stellaryx_flowing"),
				null
		));
		BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), EtherealAscendanceFluids.STELLARYX_STILL, EtherealAscendanceFluids.STELLARYX_FLOWING);

		BlockRenderLayerMap.INSTANCE.putBlock(EtherealAscendanceBlocks.GLASS, RenderLayer.getTranslucent());
		BlockRenderLayerMap.INSTANCE.putBlock(EtherealAscendanceBlocks.PRIVACY_GLASS, RenderLayer.getTranslucent());
	}

	public static Identifier getMoon(ClientWorld world) {
		boolean orbit = EtherealAscendanceDimensions.isOrbit(world);
		if (orbit) {
			switch (world.getRegistryKey().getValue().getPath()) {
				case "overworld_orbit":
					return MOON;
				case "sylene_orbit":
					return renderAsMoon("overworld");
			}

		} else {
			// no if statement here for later expansion
			switch (world.getRegistryKey().getValue().getPath()) {
				case "sylene":
					return renderAsMoon("overworld");
				case "psidon":
					return renderAsFloorBody("ouran");
			}
		}
		return renderAsMoon("none");
	}

	public static boolean shouldMultiplyStars() {
		return true;
	}

	public static int multiplyStars() {
		return 10;
	}
}