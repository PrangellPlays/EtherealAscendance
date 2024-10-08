package dev.soulsparkstudios.etherealascendance.dimension;

import com.google.common.collect.ImmutableMap;
import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

import java.util.Map;
import java.util.OptionalLong;

public class EtherealAscendanceDimensions {
    public static final RegistryKey<World> SPACE;

    public EtherealAscendanceDimensions() {
    }

    static {
        SPACE = RegistryKey.of(RegistryKeys.WORLD, EtherealAscendance.path("space"));
    }
}
