package com.nuclearmekanism.nuclearentangloporter.mixin;

import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import com.refinedmods.refinedstorage.api.resource.ResourceKey;
import com.refinedmods.refinedstorage.common.storage.StorageTypes;
import com.refinedmods.refinedstorage.common.support.resource.ItemResource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Stock RS item disks reject active capsules; addon radioactive disks use their own storage type and remain eligible. */
@Pseudo
@Mixin(targets = "com.refinedmods.refinedstorage.common.storage.SameTypeStorageType", remap = false)
public abstract class RefinedStorageItemStorageMixin {

    @Inject(method = "isAllowed", at = @At("HEAD"), cancellable = true)
    private void nuclear$rejectActiveCapsules(ResourceKey resource, CallbackInfoReturnable<Boolean> callback) {
        if ((Object) this == StorageTypes.ITEM && resource instanceof ItemResource item && RadioisotopeCapsules.isActive(item.toItemStack())) {
            callback.setReturnValue(false);
        }
    }
}
