package com.nuclearmekanism.nuclearentangloporter.content;

import com.nuclearmekanism.nuclearentangloporter.NuclearEntangloporter;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import com.refinedmods.refinedstorage.api.resource.ResourceKey;
import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.StorageType;
import com.refinedmods.refinedstorage.common.storage.SameTypeStorageType;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import com.refinedmods.refinedstorage.common.support.resource.ResourceCodecs;
import java.util.Objects;
import mekanism.common.registration.impl.CreativeTabDeferredRegister;
import mekanism.common.registration.impl.ItemDeferredRegister;
import mekanism.common.registration.impl.ItemRegistryObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;

/** Refined Storage-backed radioactive disks. Kept isolated so Refined Storage remains optional. */
public final class RefinedStorageRadioactiveDisks {

    private static final ItemDeferredRegister ITEMS = new ItemDeferredRegister(NuclearEntangloporter.MODID);
    private static StorageType storageType;
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_1K = disk("rs_radioactive_disk_1k", 1_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_4K = disk("rs_radioactive_disk_4k", 4_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_16K = disk("rs_radioactive_disk_16k", 16_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_64K = disk("rs_radioactive_disk_64k", 64_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_256K = disk("rs_radioactive_disk_256k", 256_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_1M = disk("rs_radioactive_disk_1m", 1_000_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_4M = disk("rs_radioactive_disk_4m", 4_000_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_16M = disk("rs_radioactive_disk_16m", 16_000_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_64M = disk("rs_radioactive_disk_64m", 64_000_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_256M = disk("rs_radioactive_disk_256m", 256_000_000);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_1024M = disk("rs_radioactive_disk_1024m", 1_024_000_000L);
    public static final ItemRegistryObject<RadioactiveStorageDisk> RS_RADIOACTIVE_DISK_1048M = disk("rs_radioactive_disk_1048m", 1_048_000_000L);

    private RefinedStorageRadioactiveDisks() {
    }

    public static void register(IEventBus modEventBus) {
        storageType = new SameTypeStorageType<ItemResource>(ResourceCodecs.ITEM_CODEC, RefinedStorageRadioactiveDisks::isActiveCapsule,
              ItemResource.class::cast, 1, 64);
        RefinedStorageApi.INSTANCE.getStorageTypeRegistry().register(
              ResourceLocation.fromNamespaceAndPath(NuclearEntangloporter.MODID, "radioactive_item"), storageType);
        ITEMS.register(modEventBus);
    }

    public static StorageType storageType() {
        return Objects.requireNonNull(storageType, "Refined Storage radioactive storage type was not registered");
    }

    public static void addToDisplay(CreativeModeTab.Output output) {
        CreativeTabDeferredRegister.addToDisplay(output, RS_RADIOACTIVE_DISK_1K, RS_RADIOACTIVE_DISK_4K, RS_RADIOACTIVE_DISK_16K,
              RS_RADIOACTIVE_DISK_64K, RS_RADIOACTIVE_DISK_256K, RS_RADIOACTIVE_DISK_1M, RS_RADIOACTIVE_DISK_4M,
              RS_RADIOACTIVE_DISK_16M, RS_RADIOACTIVE_DISK_64M, RS_RADIOACTIVE_DISK_256M, RS_RADIOACTIVE_DISK_1024M,
              RS_RADIOACTIVE_DISK_1048M);
    }

    private static ItemRegistryObject<RadioactiveStorageDisk> disk(String name, long capacity) {
        return ITEMS.registerItem(name, properties -> new RadioactiveStorageDisk(properties, capacity));
    }

    private static boolean isActiveCapsule(ResourceKey resource) {
        return resource instanceof ItemResource item && RadioisotopeCapsules.isActive(item.toItemStack());
    }
}
