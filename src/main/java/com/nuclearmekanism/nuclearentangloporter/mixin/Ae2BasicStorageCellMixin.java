package com.nuclearmekanism.nuclearentangloporter.mixin;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import com.nuclearmekanism.nuclearentangloporter.content.RadioactiveStorageCell;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;

/** Reject active capsules from every stock AE2 item cell; addon cells override this method and remain eligible. */
@Pseudo
@Mixin(targets = "appeng.items.storage.BasicStorageCell", remap = false)
public abstract class Ae2BasicStorageCellMixin {

    public boolean isBlackListed(ItemStack cellItem, AEKey requestedAddition) {
        return !((Object) this instanceof RadioactiveStorageCell) && requestedAddition instanceof AEItemKey item
               && RadioisotopeCapsules.isActive(item.getReadOnlyStack());
    }
}
