package dev.soulsparkstudios.etherealascendance.dimension;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.util.EtherealAscendanceText;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;

public abstract class EtherealAscendanceDimensions {
    public static final RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, EtherealAscendance.path("space"));

    public static boolean isEtherealAscendanceDimension(World world) {
        return isEtherealAscendanceDimension(world.getRegistryKey());
    }

    public static boolean isEtherealAscendanceDimension(RegistryKey<World> world) {
        return world.getValue().getNamespace().equals(EtherealAscendance.MOD_ID) || world.getValue().getPath().contains(EtherealAscendance.MOD_ID);
        // if you want to add your own planet, add "etherealascendance" to its path (e.g., mymod:kerbin_etherealascendance)
    }

    public static boolean isOrbit(World world) {
        return isOrbit(world.getRegistryKey());
    }

    public static boolean isOrbit(RegistryKey<World> world) {
        return (world.getValue().getPath().contains(EtherealAscendance.ORBIT_SUFFIX) && isEtherealAscendanceDimension(world));
    }

    public static boolean hasAtmosphere(World world, boolean getHost) {
        if (isEtherealAscendanceDimension(world)) {
            if (isOrbit(world) && !getHost) {
                return false;
            } else {
                switch (EtherealAscendanceText.deorbitify(world.getRegistryKey().getValue().getPath())) {
                    //case "halyus":
                        //return true;
                    default:
                        return false; //fixme worldIsIn(world, AstralTags.HAS_ATMOSPHERE);
                }
            }
        }
        return true;
    }
}
