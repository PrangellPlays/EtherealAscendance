package dev.soulsparkstudios.etherealascendance.init;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import dev.soulsparkstudios.etherealascendance.item.ExperienceVialItem;
import dev.soulsparkstudios.etherealascendance.item.RationsItem;
import dev.soulsparkstudios.etherealascendance.item.canisters.EmptyCanister;
import dev.soulsparkstudios.etherealascendance.item.canisters.StellaryxCanister;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

public class EtherealAscendanceItems {
    protected static final Map<Item, Identifier> ITEMS = new LinkedHashMap();
    public static final Item STELLARYX_BUCKET;
    public static final Item EMPTY_CANISTER;
    public static final Item STELLARYX_CANISTER;
    public static final Item EXPERIENCE_VIAL;
    public static final Item RATIONS;

    public static void init() {
        ITEMS.forEach((item, id) -> {
            Registry.register(Registries.ITEM, id, item);
        });
    }

    protected static <T extends Item> T register(String name, T item) {
        ITEMS.put(item, EtherealAscendance.path(name));
        return item;
    }

    public EtherealAscendanceItems() {
    }

    static {
        STELLARYX_BUCKET = register((String) "stellaryx_bucket", (Item) (new BucketItem(EtherealAscendanceFluids.STELLARYX_STILL, new FabricItemSettings().maxCount(1))));
        EMPTY_CANISTER = register((String) "empty_canister", (Item) (new EmptyCanister(new FabricItemSettings().maxCount(1))));
        STELLARYX_CANISTER = register((String) "stellaryx_canister", (Item) (new StellaryxCanister(new FabricItemSettings().maxCount(1))));
        EXPERIENCE_VIAL = register((String) "experience_vial", (Item) (new ExperienceVialItem(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE))));
        RATIONS = register((String) "rations", (Item) (new RationsItem(new FabricItemSettings().food(EtherealAscendanceFoodComponents.RATIONS).maxCount(64))));
    }
}
