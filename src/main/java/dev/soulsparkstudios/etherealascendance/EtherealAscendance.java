package dev.soulsparkstudios.etherealascendance;

import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceBlocks;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceFluids;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceItems;
import dev.soulsparkstudios.etherealascendance.registry.EtherealAscendanceItemGroups;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class EtherealAscendance implements ModInitializer {
	public static final String MOD_ID = "etherealascendance";
	public static final Logger LOGGER = LoggerFactory.getLogger("etherealascendance");

	public static final String ORBIT_SUFFIX = "_orbit";
	public static final SoundEvent EXPERIENCE_VIAL_DEPOSIT = SoundEvent.of(new Identifier("etherealascendance", "sounds.experience_vial.deposit"));
	public static final SoundEvent EXPERIENCE_VIAL_WITHDRAW = SoundEvent.of(new Identifier("etherealascendance", "sounds.experience_vial.withdraw"));

	//public static final String DEVELOPER = "etherealascendance.developer";
	//private static final boolean isDevelopmentEnvironment = FabricLoader.getInstance().isDevelopmentEnvironment();

	@Override
	public void onInitialize() {
		EtherealAscendanceBlocks.init();
		EtherealAscendanceItems.init();
		EtherealAscendanceItemGroups.init();

		Registry.register(Registries.SOUND_EVENT, EXPERIENCE_VIAL_DEPOSIT.getId(), EXPERIENCE_VIAL_DEPOSIT);
		Registry.register(Registries.SOUND_EVENT, EXPERIENCE_VIAL_WITHDRAW.getId(), EXPERIENCE_VIAL_WITHDRAW);

		ModelPredicateProviderRegistry.register(EtherealAscendanceItems.EXPERIENCE_VIAL, new Identifier("experience"), (itemStack, clientWorld, livingEntity, seed) -> {
			NbtCompound nbt = itemStack.getNbt();
			return nbt != null && nbt.contains("experience") ? (float)nbt.getInt("experience") / 1395.0F : 0.0F;
		});

		FluidRenderHandlerRegistry.INSTANCE.register(EtherealAscendanceFluids.STELLARYX_STILL, EtherealAscendanceFluids.STELLARYX_FLOWING,
				new SimpleFluidRenderHandler(
						new Identifier("etherealascendance:fluid/stellaryx/stellaryx_still"),
						new Identifier("etherealascendance:fluid/stellaryx/stellaryx_flowing"),
						0xf722d7
				));
	}

	public static Identifier path(String path) {
		return new Identifier(MOD_ID, path);
	}

	/*static boolean shouldRegisterExamples() {
		return isDevelopmentEnvironment && !Boolean.getBoolean(DEVELOPER);
	}*/
}