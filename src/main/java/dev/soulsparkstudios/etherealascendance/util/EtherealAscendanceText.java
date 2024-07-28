package dev.soulsparkstudios.etherealascendance.util;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.util.Identifier;

public abstract class EtherealAscendanceText {
    public static Identifier deorbitify(Identifier original) {
        if (original.getNamespace().equals(EtherealAscendance.MOD_ID)) {
            String[] splits = original.getPath().split("_");
            return new Identifier(EtherealAscendance.MOD_ID, splits[0]);
        }
        return original;
    }

    public static String deorbitify(String original) {
        if(original.contains("_")) {
            String[] splits = original.split("_");
            return splits[0];
        }
        return original;
    }

    public static Identifier orbitify(Identifier original) {
        if(!original.getPath().contains(EtherealAscendance.ORBIT_SUFFIX)) {
            String newPath = original.getPath() + EtherealAscendance.ORBIT_SUFFIX;
            return new Identifier(EtherealAscendance.MOD_ID, newPath);
        }
        return original;
    }

    public static String orbitify(String original) {
        if(!original.contains(EtherealAscendance.ORBIT_SUFFIX)) {
            return original + EtherealAscendance.ORBIT_SUFFIX;
        }
        return original;
    }
}