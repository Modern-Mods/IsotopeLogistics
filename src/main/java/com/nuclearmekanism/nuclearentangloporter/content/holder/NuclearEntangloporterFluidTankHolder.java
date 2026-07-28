package com.nuclearmekanism.nuclearentangloporter.content.holder;

import com.nuclearmekanism.nuclearentangloporter.content.tile.TileEntityNuclearEntangloporter;
import java.util.Collections;
import java.util.List;
import mekanism.api.fluid.IExtendedFluidTank;
import mekanism.common.capabilities.holder.fluid.IFluidTankHolder;
import mekanism.common.lib.transmitter.TransmissionType;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Fluid holder that mirrors Mekanism's quantum entangloporter implementation.
 */
public class NuclearEntangloporterFluidTankHolder extends NuclearEntangloporterConfigHolder<IExtendedFluidTank> implements IFluidTankHolder {

    public NuclearEntangloporterFluidTankHolder(TileEntityNuclearEntangloporter entangloporter) {
        super(entangloporter);
    }

    @Override
    protected TransmissionType getTransmissionType() {
        return TransmissionType.FLUID;
    }

    @NotNull
    @Override
    public List<IExtendedFluidTank> getTanks(@Nullable Direction side) {
        return entangloporter.hasFrequency() ? entangloporter.getFreq().getFluidTanks(side) : Collections.emptyList();
    }
}
