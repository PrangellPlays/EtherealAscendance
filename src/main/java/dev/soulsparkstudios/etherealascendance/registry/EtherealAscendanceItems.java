package dev.soulsparkstudios.etherealascendance.registry;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.item.ExperienceVialItem;
import dev.soulsparkstudios.etherealascendance.item.canisters.EmptyCanister;
import dev.soulsparkstudios.etherealascendance.item.canisters.StellaryxCanister;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class EtherealAscendanceItems {
    public static final Item STELLARYX_BUCKET = registerItem("stellaryx_bucket", new BucketItem(EtherealAscendanceFluids.STELLARYX_STILL, new FabricItemSettings().maxCount(1)));
    public static final Item EMPTY_CANISTER = registerItem("empty_canister", new EmptyCanister(new FabricItemSettings().maxCount(1)));
    public static final Item STELLARYX_CANISTER = registerItem("stellaryx_canister", new StellaryxCanister(new FabricItemSettings().maxCount(1)));
    public static final Item EXPERIENCE_VIAL = registerItem("experience_vial", new ExperienceVialItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(EtherealAscendance.MOD_ID, name), item);
    }

    public static void init() {
        EtherealAscendance.LOGGER.info("Registering Mod Items for " + EtherealAscendance.MOD_ID);
    }
}
