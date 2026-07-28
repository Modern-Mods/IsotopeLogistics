package com.nuclearmekanism.nuclearentangloporter.mixin;

import com.refinedmods.refinedstorage.common.storage.DiskInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

/** Exposes RS's concrete disk inventory so loaded disk contents can be checked without client-side guesses. */
@Pseudo
@Mixin(targets = "com.refinedmods.refinedstorage.common.storage.AbstractDiskContainerBlockEntity", remap = false)
public interface RefinedStorageDiskInventoryAccessor {

    @Accessor("diskInventory")
    DiskInventory nuclear$getDiskInventory();
}
