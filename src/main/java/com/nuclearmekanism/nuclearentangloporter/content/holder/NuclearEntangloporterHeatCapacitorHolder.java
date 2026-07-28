package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.Collections;
import java.util.List;
import mekanism.api.heat.IHeatCapacitor;
import mekanism.common.capabilities.holder.heat.IHeatCapacitorHolder;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Heat capacitor holder that mirrors Mekanism's quantum entangloporter implementation.
 */
public class NuclearEntangloporterHeatCapacitorHolder extends NuclearEntangloporterConfigHolder<IHeatCapacitor> implements IHeatCapacitorHolder {

    public NuclearEntangloporterHeatCapacitorHolder(TileEntityNuclearEntangloporter entangloporter) {
        super(entangloporter);
    }

    @Override
    protected TransmissionType getTransmissionType() {
        return TransmissionType.HEAT;
    }

    @NotNull
    @Override
    public List<IHeatCapacitor> getHeatCapacitors(@Nullable Direction side) {
        return entangloporter.hasFrequency() ? entangloporter.getFreq().getHeatCapacitors(side) : Collections.emptyList();
    }
}
