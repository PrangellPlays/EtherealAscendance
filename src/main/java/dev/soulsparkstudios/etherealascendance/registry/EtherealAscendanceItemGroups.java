package dev.soulsparkstudios.etherealascendance.registry;

import dev.soulsparkstudios.etherealascendance.EtherealAscendance;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EtherealAscendanceItemGroups {
    public static final ItemGroup ETHEREAL_ASCENDANCE_ITEMS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EtherealAscendance.MOD_ID, "items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.etherealascendance.items")).icon(() -> new ItemStack(EtherealAscendanceItems.EMPTY_CANISTER)).entries((displayContext, entries) -> {
                entries.add(EtherealAscendanceItems.EMPTY_CANISTER);
                entries.add(EtherealAscendanceItems.STELLARYX_CANISTER);
                entries.add(EtherealAscendanceItems.STELLARYX_BUCKET);
                entries.add(EtherealAscendanceItems.EXPERIENCE_VIAL);
                entries.add(EtherealAscendanceItems.RATIONS);
            }).build());
    public static final ItemGroup ETHEREAL_ASCENDANCE_BLOCKS = Registry.register(Registries.ITEM_GROUP,
            new Identifier(EtherealAscendance.MOD_ID, "blocks"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.etherealascendance.blocks")).icon(() -> new ItemStack(EtherealAscendanceBlocks.GLASS)).entries((displayContext, entries) -> {
                entries.add(EtherealAscendanceBlocks.GLASS);
                entries.add(EtherealAscendanceBlocks.PRIVACY_GLASS);
            }).build());

    public static void init() {
        EtherealAscendance.LOGGER.info("Registering Item Groups for " + EtherealAscendance.MOD_ID);
    }
}
