package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;

/** AE2-backed radioactive cells. Kept isolated so AE2 remains optional. */
public final class Ae2RadioactiveStorageCells {

    private static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(NuclearEntangloporter.MODID);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_1K = cell("ae2_radioactive_disk_1k", 1);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_4K = cell("ae2_radioactive_disk_4k", 4);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_16K = cell("ae2_radioactive_disk_16k", 16);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_64K = cell("ae2_radioactive_disk_64k", 64);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_256K = cell("ae2_radioactive_disk_256k", 256);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_1M = cell("ae2_radioactive_disk_1m", 1_024);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_4M = cell("ae2_radioactive_disk_4m", 4_096);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_16M = cell("ae2_radioactive_disk_16m", 16_384);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_64M = cell("ae2_radioactive_disk_64m", 65_536);
    public static final ItemRegistryObject<RadioactiveStorageCell> AE2_RADIOACTIVE_DISK_256M = cell("ae2_radioactive_disk_256m", 262_144);

    private Ae2RadioactiveStorageCells() {
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static void addToDisplay(CreativeModeTab.Output output) {
        CreativeTabDeferredRegister.addToDisplay(output, AE2_RADIOACTIVE_DISK_1K, AE2_RADIOACTIVE_DISK_4K, AE2_RADIOACTIVE_DISK_16K,
              AE2_RADIOACTIVE_DISK_64K, AE2_RADIOACTIVE_DISK_256K, AE2_RADIOACTIVE_DISK_1M, AE2_RADIOACTIVE_DISK_4M,
              AE2_RADIOACTIVE_DISK_16M, AE2_RADIOACTIVE_DISK_64M, AE2_RADIOACTIVE_DISK_256M);
    }

    private static ItemRegistryObject<RadioactiveStorageCell> cell(String name, int kibibytes) {
        return ITEMS.registerItem(name, properties -> new RadioactiveStorageCell(properties, kibibytes));
    }
}
