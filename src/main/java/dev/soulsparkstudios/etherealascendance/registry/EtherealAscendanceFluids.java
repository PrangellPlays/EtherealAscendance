package dev.soulsparkstudios.etherealascendance.registry;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.fluid.StellaryxFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EtherealAscendanceFluids {
    public static final FlowableFluid STELLARYX_STILL = register("stellaryx_still", new StellaryxFluid.Still());
    public static final FlowableFluid STELLARYX_FLOWING = register("stellaryx_flowing", new StellaryxFluid.Flowing());

    public static boolean isStellaryx(FluidState state) {
        return state.isOf(EtherealAscendanceFluids.STELLARYX_STILL) || state.isOf(EtherealAscendanceFluids.STELLARYX_FLOWING);
    }

    private static FlowableFluid register(String name, FlowableFluid flowableFluid) {
        return Registry.register(Registries.FLUID, new Identifier(EtherealAscendance.MOD_ID, name), flowableFluid);
    }
}