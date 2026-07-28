package com.nuclearmekanism.nuclearentangloporter.content;

import appeng.api.stacks.AEItemKey;
import appeng.api.stacks.AEKey;
import appeng.api.stacks.AEKeyType;
import appeng.items.storage.BasicStorageCell;
import com.nuclearmekanism.nuclearentangloporter.content.chemical.RadioisotopeCapsules;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** AE2 cell that only accepts validated active radioisotope capsules. */
public class RadioactiveStorageCell extends BasicStorageCell {

    public RadioactiveStorageCell(Item.Properties properties, int kibibytes) {
        super(properties.stacksTo(1), 0.5, kibibytes, Math.max(8, kibibytes * 8), 63, AEKeyType.items());
    }

    @Override
    public boolean isBlackListed(ItemStack cellItem, AEKey requestedAddition) {
        return !(requestedAddition instanceof AEItemKey itemKey) || !RadioisotopeCapsules.isActive(itemKey.getReadOnlyStack());
    }
}
