package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.Collections;
import java.util.List;
import mekanism.api.inventory.IInventorySlot;
import mekanism.common.capabilities.holder.slot.IInventorySlotHolder;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inventory holder that mirrors Mekanism's quantum entangloporter implementation.
 */
public class NuclearEntangloporterInventorySlotHolder extends NuclearEntangloporterConfigHolder<IInventorySlot> implements IInventorySlotHolder {

    public NuclearEntangloporterInventorySlotHolder(TileEntityNuclearEntangloporter entangloporter) {
        super(entangloporter);
    }

    @Override
    protected TransmissionType getTransmissionType() {
        return TransmissionType.ITEM;
    }

    @NotNull
    @Override
    public List<IInventorySlot> getInventorySlots(@Nullable Direction side) {
        return entangloporter.hasFrequency() && entangloporter.hasInventory() ? entangloporter.getFreq().getInventorySlots(side) : Collections.emptyList();
    }
}
