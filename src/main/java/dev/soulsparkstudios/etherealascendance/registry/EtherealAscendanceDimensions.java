package dev.soulsparkstudios.etherealascendance.registry;

import com.google.common.collect.ImmutableMap;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public class EtherealAscendanceDimensions {
    public static final RegistryKey<World> SPACE;

    static {
        SPACE = RegistryKey.of(RegistryKeys.WORLD, EtherealAscendance.path("space"));
    }
}