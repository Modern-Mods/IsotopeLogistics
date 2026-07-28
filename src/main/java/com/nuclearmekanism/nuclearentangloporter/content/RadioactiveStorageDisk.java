package com.nuclearmekanism.nuclearentangloporter.content;

import com.refinedmods.refinedstorage.common.api.RefinedStorageApi;
import com.refinedmods.refinedstorage.common.api.storage.AbstractStorageContainerItem;
import com.refinedmods.refinedstorage.common.api.storage.SerializableStorage;
import com.refinedmods.refinedstorage.common.api.storage.StorageRepository;
import com.refinedmods.refinedstorage.common.content.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Refined Storage disk whose registered storage type rejects every non-active capsule. */
public class RadioactiveStorageDisk extends AbstractStorageContainerItem {

    private final long capacity;

    public RadioactiveStorageDisk(Item.Properties properties, long capacity) {
        super(properties.stacksTo(1).fireResistant(), RefinedStorageApi.INSTANCE.getStorageContainerItemHelper());
        this.capacity = capacity;
    }

    @Override
    protected Long getCapacity() {
        return capacity;
    }

    @Override
    protected String formatAmount(long amount) {
        return Long.toString(amount);
    }

    @Override
    protected SerializableStorage createStorage(StorageRepository storageRepository) {
        return RefinedStorageRadioactiveDisks.storageType().create(capacity, storageRepository::markAsChanged);
    }

    @Override
    protected ItemStack createPrimaryDisassemblyByproduct(int count) {
        return new ItemStack(Items.INSTANCE.getStorageHousing(), count);
    }

    @Override
    protected ItemStack createSecondaryDisassemblyByproduct(int count) {
        return ItemStack.EMPTY;
    }
}
