package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import mekanism.api.chemical.IChemicalTank;
import mekanism.common.capabilities.holder.chemical.IChemicalTankHolder;
import com.nuclearmekanism.nuclearentangloporter.content.frequency.NuclearInventoryFrequency;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Chemical holder that mirrors Mekanism's implementation for the quantum entangloporter.
 */
public class NuclearEntangloporterChemicalTankHolder extends NuclearEntangloporterConfigHolder<IChemicalTank> implements IChemicalTankHolder {

    private final BiFunction<NuclearInventoryFrequency, Direction, List<IChemicalTank>> tankResolver;
    private final TransmissionType transmissionType;

    public NuclearEntangloporterChemicalTankHolder(TileEntityNuclearEntangloporter entangloporter, TransmissionType transmissionType,
          BiFunction<NuclearInventoryFrequency, Direction, List<IChemicalTank>> tankResolver) {
        super(entangloporter);
        this.transmissionType = transmissionType;
        this.tankResolver = tankResolver;
    }

    @Override
    protected TransmissionType getTransmissionType() {
        return transmissionType;
    }

    @NotNull
    @Override
    public List<IChemicalTank> getTanks(@Nullable Direction side) {
        return entangloporter.hasFrequency() ? tankResolver.apply(entangloporter.getFreq(), side) : Collections.emptyList();
    }
}
