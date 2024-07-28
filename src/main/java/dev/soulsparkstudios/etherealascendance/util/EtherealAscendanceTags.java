package dev.soulsparkstudios.etherealascendance.util;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EtherealAscendanceTags {
    public static class Fluids {
        public static final TagKey<Fluid> STELLARYX =
                createTag("stellaryx");

        private static TagKey<Fluid> createTag(String name){
            return TagKey.of(RegistryKeys.FLUID, new Identifier(EtherealAscendance.MOD_ID, name));
        }
    }
}
