package com.nuclearmekanism.nuclearentangloporter.content.item;

import mekanism.common.block.prefab.BlockTile.BlockTileModel;
import mekanism.common.item.block.ItemBlockChemicalTank;
import net.minecraft.world.item.Item;

/** Reuses Mekanism's chemical tank tooltip and item behavior for the radioactive-safe variants. */
@SuppressWarnings({"rawtypes", "unchecked"})
public class ItemBlockRadioactiveChemicalTank extends ItemBlockChemicalTank {

    public ItemBlockRadioactiveChemicalTank(BlockTileModel block, Item.Properties properties) {
        super(block, properties);
    }
}
